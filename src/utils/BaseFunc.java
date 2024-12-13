package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class BaseFunc {
      public static boolean isValidRupiah(String nilai) {
      
        String regex = "^Rp\\s?\\d{1,3}(?:\\.\\d{3})*(?:,\\d+)?$";
        
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(nilai).matches();
    }

      public static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

}
