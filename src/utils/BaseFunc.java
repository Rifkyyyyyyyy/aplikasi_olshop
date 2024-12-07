package utils;

import java.util.regex.Pattern;

public class BaseFunc {
      public static boolean isValidRupiah(String nilai) {
      
        String regex = "^Rp\\s?\\d{1,3}(?:\\.\\d{3})*(?:,\\d+)?$";
        
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(nilai).matches();
    }
}
