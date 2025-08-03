package com.example.backend.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.example.backend.config.EncryptionProperties;

import jakarta.annotation.PostConstruct;

@Component
public class EncryptionUtil {
    
    private final EncryptionProperties encryptionProperties;
    private SecretKey secretKey;
    private final String algorithm = "AES";

    public EncryptionUtil(EncryptionProperties encryptionProperties) {
        this.encryptionProperties = encryptionProperties;
    }
    
    @PostConstruct
    public void init() {
        byte[] decodedKey = Base64.getDecoder().decode(encryptionProperties.getSecretKey());
        this.secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, algorithm);
    }

    public String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt data", e);
        }
    }

    public String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt data", e);
        }
    }

}
