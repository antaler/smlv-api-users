package com.antaler.smlv.apis.users.services.totp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;
import java.util.stream.IntStream;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.antaler.smlv.apis.users.model.TOTPQrKey;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public final class TOTPServiceImpl implements TOTPService {
    /*
     * The Base32 encoding scheme, as defined in RFC 4648, intentionally omits
     * the numbers 0, 1, 8, and 9 to reduce the possibility of human
     * misinterpretation
     * and transcription errors.
     * Reference: https://datatracker.ietf.org/doc/html/rfc4648
     */
    private static final char[] BASE32_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toCharArray();

    // ASCII character set defines 128 unique values
    private static final int[] BITS_LOOKUP = new int[128];

    private static final String HMAC_ALGO = "HmacSHA1";
    private static final int TOTP_LENGTH = 6; // length of TOTP will be 6 digits
    private static final int TIME_STEP = 30; // 30 seconds

    static {
        for (int i = 0; i < BASE32_CHARS.length; i++) {
            BITS_LOOKUP[BASE32_CHARS[i]] = i;
        }
    }

    private static String encodeBase32(String input) {
        StringBuilder encoded = new StringBuilder();
        int buffer = 0;
        int bufferLength = 0;

        for (byte b : input.getBytes()) {
            buffer <<= 8; // Shift the buffer 8 bits to the left
            buffer |= b & 0xFF; // Add the byte to the buffer
            bufferLength += 8; // Increase the buffer length by 8 bits

            /*
             * Base32 encoding uses a 5-bit group from the available data.
             * So, we check if we have at least 5 bits in our buffer.
             * We then extract the top 5 bits for Base32 encoding.
             */
            while (bufferLength >= 5) {
                int index = (buffer >> (bufferLength - 5)) & 0x1F;
                encoded.append(BASE32_CHARS[index]);
                bufferLength -= 5; // Decrease the buffer length by 5 bits
            }
        }

        /*
         * Padding is necessary for Base32 encoding to ensure the encoded output
         * is a multiple of 8 characters, making it easier to decode later.
         * The '=' character is used as a padding character.
         */
        while (encoded.length() % 8 != 0) {
            encoded.append('=');
        }

        return encoded.toString();
    }

    private static String decodeBase32(String base32) {
        base32 = base32.toUpperCase().replaceAll("[=]", "");
        StringBuilder decoded = new StringBuilder();
        int buffer = 0;
        int bufferLength = 0;

        for (char c : base32.toCharArray()) {
            buffer <<= 5;
            buffer |= BITS_LOOKUP[c];
            bufferLength += 5;

            while (bufferLength >= 8) {
                byte b = (byte) (buffer >> (bufferLength - 8));
                decoded.append((char) b);
                bufferLength -= 8;
            }
        }

        return decoded.toString();
    }

    private static String generateTOTP(String secretKey, long timeInterval) {
        try {
            byte[] decodedKey = decodeBase32(secretKey).getBytes();
            byte[] timeIntervalBytes = new byte[8];

            /*
             * In RFC 4226, it's specified that the counter value
             * (which, in the case of TOTP, is derived from the current time)
             * should be represented in big-endian format when used as input for the HMAC
             * computation.
             * Reference: https://datatracker.ietf.org/doc/html/rfc4226
             */

            // Convert the timeInterval into its byte representation
            for (int i = 7; i >= 0; i--) {
                // Extract the least significant byte from timeInterval
                timeIntervalBytes[i] = (byte) (timeInterval & 0xFF);
                // Right shift to process the next byte
                timeInterval >>= 8;
            }

            Mac hmac = Mac.getInstance(HMAC_ALGO);
            hmac.init(new SecretKeySpec(decodedKey, HMAC_ALGO));
            byte[] hash = hmac.doFinal(timeIntervalBytes);

            /*
             * The line offset = hash[hash.length - 1] & 0xF; is used to determine the
             * offset into the HMAC hash
             * from which a 4-byte dynamic binary code will be extracted to generate the
             * TOTP.
             * This method of determining the offset is specified in the TOTP (RFC 6238) and
             * HOTP (RFC 4226) standards.
             */
            int offset = hash[hash.length - 1] & 0xF;

            /*
             * The expression hash[offset] & 0x7F uses the hexadecimal value 0x7F to mask
             * the most significant bit (MSB) of the byte at hash[offset],
             * ensuring it's set to 0. The reason for this is to make sure that the
             * resulting 32-bit integer
             * (binaryCode) is treated as a positive number. Reference TOTP (RFC 6238)
             */
            long mostSignificantByte = (hash[offset] & 0x7F) << 24;
            long secondMostSignificantByte = (hash[offset + 1] & 0xFF) << 16;
            long thirdMostSignificantByte = (hash[offset + 2] & 0xFF) << 8;
            long leastSignificantByte = hash[offset + 3] & 0xFF;

            long binaryCode = mostSignificantByte
                    | secondMostSignificantByte
                    | thirdMostSignificantByte
                    | leastSignificantByte;

            int totp = (int) (binaryCode % Math.pow(10, TOTP_LENGTH));
            return String.format("%0" + TOTP_LENGTH + "d", totp); // Making sure length is equal to TOTP_LENGTH

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate TOTP", e);
        }
    }

    private static String createQR(String data,
            int height, int width) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(
                data,
                BarcodeFormat.QR_CODE, width, height);
        var outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "jpg", outputStream);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    public Mono<TOTPQrKey> generateKey(String user) {
        return Mono.justOrEmpty(user).map(u -> {
            var key = encodeBase32(UUID.randomUUID().toString().replace("-", "")).substring(0,32);
            String qr;
            var uriTotp = "otpauth://totp/Smartlive:%s?secret=%s".formatted(user,key);
            try {
                qr = createQR(uriTotp, 300, 300);
    
            } catch (WriterException | IOException e) {
                log.error("{}", e);
                qr = null;
            }

            return new TOTPQrKey(key, qr);
        
        });
    }

    public Mono<Boolean> validate(String secretKey, String inputTOTP) {
       
        long timeInterval = System.currentTimeMillis() / 1000 / TIME_STEP;

        // Check TOTP for current, previous, and next intervals
        boolean matches = IntStream.of(-1, 0, 1)
                .anyMatch(i -> generateTOTP(secretKey, timeInterval + i).equals(inputTOTP));

        return Mono.just(matches);
    }
}
