package com.I2I.I2IBaceknd.Commonfn;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

/**
 * Generic Functions
 */

@Service
public class commonfn {

    public static String GetJson(Object obj) {
        String json = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(obj);
            // System.out.println("JSON = " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static String GetMd5(String input) {
        try {
            // Static getInstance method is called with hashing SHA
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method called
            // to calculate message digest of an input
            // and return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown" + " for incorrect algorithm: " + e);
            return null;
        }
    }

    public static JSONObject createJSONObject(String request) {
        JSONObject jsonObject = new JSONObject();
        JSONParser jsonParser = new JSONParser();
    
        if (request != null && !request.isEmpty()) {
            try {
                jsonObject = (JSONObject) jsonParser.parse(request);
            } catch (Exception e) {
                // Log unexpected errors that may occur during parsing
                System.err.println("Failed to parse JSON: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            // Log a warning when the request string is null or empty
            System.err.println("Request string is null or empty");
        }
    
        return jsonObject;
    }
    
    

    public static String Encrypt(String txt) {
        String encoded = null;
        try {
            encoded = Base64.getEncoder().encodeToString(txt.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error :" + e.getMessage());
        }
        System.out.println("##" + Decrypt(encoded) ) ;
        return encoded;
    }

    public static String Decrypt(String base64encodedString)  {
        String decoded = null;
        try{
            byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);
            decoded = new String(base64decodedBytes, "utf-8");
        }
        catch(UnsupportedEncodingException e){
            System.out.println("Error :" + e.getMessage());
        }
        return decoded ;
    }

    public static String tryEncrypt(String txt) {
        String secret = "DevTechPool";
        String cipherText = "PoolTechDev";
        // Base64.getEncoder().encodeToString(cipherText.getBytes("utf-8"));
        // byte[] cipherData =
        // Base64.getEncoder().encodeToString(cipherText.getBytes("utf-8"));
        try {
            String base64encodedString = Base64.getEncoder().encodeToString(cipherText.getBytes("utf-8"));
            System.out.println("Base64 Encoded String (Basic) :" + base64encodedString);

            // Decode
            byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);
            System.out.println("Original String: " + new String(base64decodedBytes, "utf-8"));

            byte[] saltData = base64decodedBytes;

            MessageDigest md5 = MessageDigest.getInstance("MD5");
            final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8),
                    md5);
            SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
            IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

            System.out.println("Key: " + key.toString());
            System.out.println(" IV: " + iv.toString());

            Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
            aesCBC.init(Cipher.ENCRYPT_MODE, key, iv);

            // CryptoJs encrypted = CryptoJS.AES.encrypt(txt, secret);
            // encrypted = encrypted.toString();
            // console.log("Cipher text: " + encrypted);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | InvalidAlgorithmParameterException e) {
            System.out.println("Error :" + e.getMessage());
        }
        return null;
    }

    public static String tryDecrypt( String text){
        try{
            String secret = "DevTechPool";
            String cipherText = text; //"U2FsdGVkX1+tsmZvCEFa/iGeSA0K7gvgs9KXeZKwbCDNCs2zPo+BXjvKYLrJutMK+hxTwl/hyaQLOaD7LLIRo2I5fyeRMPnroo6k8N9uwKk=";

            byte[] cipherData = Base64.getDecoder().decode(cipherText);
            byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);

            MessageDigest md5 = MessageDigest.getInstance("MD5");
            final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
            SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
            IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

            byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
            Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
            aesCBC.init(Cipher.DECRYPT_MODE, key, iv);

            //byte[] decrypted = doFinal( Cipher.DECRYPT_MODE, key, iv, base64( ciphertext ) );
            byte[] decryptedData = aesCBC.doFinal(encrypted);
            String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);

            System.out.println(decryptedText);
            return decryptedText;
        }
        catch( Exception e){
            System.out.println(e);
            return null;
        }
       
    }


    /**
     * Generates a key and an initialization vector (IV) with the given salt and password.
     * <p>
     * This method is equivalent to OpenSSL's EVP_BytesToKey function
     * (see https://github.com/openssl/openssl/blob/master/crypto/evp/evp_key.c).
     * By default, OpenSSL uses a single iteration, MD5 as the algorithm and UTF-8 encoded password data.
     * </p>
     * @param keyLength the length of the generated key (in bytes)
     * @param ivLength the length of the generated IV (in bytes)
     * @param iterations the number of digestion rounds 
     * @param salt the salt data (8 bytes of data or <code>null</code>)
     * @param password the password data (optional)
     * @param md the message digest algorithm to use
     * @return an two-element array with the generated key and IV
     */
    private static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {

        int digestLength = md.getDigestLength();
        int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
        byte[] generatedData = new byte[requiredLength];
        int generatedLength = 0;

        try {
            md.reset();

            // Repeat process until sufficient data has been generated
            while (generatedLength < keyLength + ivLength) {

                // Digest data (last digest if available, password data, salt if available)
                if (generatedLength > 0)
                    md.update(generatedData, generatedLength - digestLength, digestLength);
                md.update(password);
                if (salt != null)
                    md.update(salt, 0, 8);
                md.digest(generatedData, generatedLength, digestLength);

                // additional rounds
                for (int i = 1; i < iterations; i++) {
                    md.update(generatedData, generatedLength, digestLength);
                    md.digest(generatedData, generatedLength, digestLength);
                }

                generatedLength += digestLength;
            }

            // Copy key and IV into separate byte arrays
            byte[][] result = new byte[2][];
            result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
            if (ivLength > 0)
                result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

            return result;

        } catch (DigestException e) {
            throw new RuntimeException(e);

        } finally {
            // Clean out temporary data
            Arrays.fill(generatedData, (byte)0);
        }
    }


    public static Date convertStringToSQLDate(String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dateString, formatter);
            return Date.valueOf(localDate);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + e.getMessage());
            return null; 
        }
    }



    // hashing password
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;

    public static String hashPassword(String password) {
        try {
            // Generate a random salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);

            // Generate the hash
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            // Combine salt and hash for storage
            byte[] hashWithSalt = new byte[salt.length + hash.length];
            System.arraycopy(salt, 0, hashWithSalt, 0, salt.length);
            System.arraycopy(hash, 0, hashWithSalt, salt.length, hash.length);

            // Return the combined salt and hash as a Base64 encoded string
            return Base64.getEncoder().encodeToString(hashWithSalt);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }


    public static boolean verifyPassword(String password, String storedHash) {
        // Decode the stored hash and salt
        byte[] hashWithSalt = Base64.getDecoder().decode(storedHash);
        byte[] salt = Arrays.copyOfRange(hashWithSalt, 0, SALT_LENGTH);
        byte[] storedHashBytes = Arrays.copyOfRange(hashWithSalt, SALT_LENGTH, hashWithSalt.length);

        // Hash the input password with the stored salt
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            // Compare the hashes
            return Arrays.equals(storedHashBytes, hash);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error verifying password", e);
        }
    }

 


}
