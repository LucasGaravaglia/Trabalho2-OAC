package src.utils;

/**
 * @author Levi
 */
public class Binary {
    public static final String BITS_32_ZERO =  "00000000000000000000000000000000";
    public static final String BITS_5_ZERO =   "00000000000000000000000000000000";
    public static final String BITS_32_ALL_1 = "11111111111111111111111111111111";

    /**
     * Function that transforms a binary string 32 bits into a decimal value 
     * @param bits string with 32 of length 
     * @return
     */
    public static Integer getDecimalValue(String bits){
        return Integer.parseInt(bits, 2);
    }
    
    /**
     * Function that transform an Integer into a 32 bits binary string
     * @param value
     */
    public static String get32BitsStringValue(Integer value){
        String binary = Integer.toBinaryString(value);

        if(binary.length() > 32){
            return BITS_32_ZERO;
        }

        if(binary.length() == 32){
            return binary;
        }else{
            String newBinary = "";
            for(int i = 0; i < (32 - binary.length());i++){
                newBinary += "0";
            }
            return newBinary + binary;
        }
    }

    /**
     * Function that add zeros at the left of the string to complete 32 bits
     * @param value a binary String positive
     * @return String with length 32
     */
    public static String normalizeSize(String value){
        if(value.length() > 32){
            return Binary.BITS_32_ZERO;
        }else if(value.length() == 32){
            return value;
        }
        
        String newString = "";
        for(int i = 1;i < (32 - value.length());i++){
            newString += 0;
        }
                
        return newString + value;
    }

    /**
     * Function that execute an and binary with the given values
     * @param value1 Binary String with length 32
     * @param value2 Binary String with length 32
     * @return Binary String with length 32
     */
    public static String andBinary_32_bits(String value1, String value2){
        int a = getInt(value1);
        int b = getInt(value2);
        int c = a & b;
        String result = Integer.toBinaryString(c);
        return Binary.normalizeSize(result);
    }

    /**
     * Function that execute an or binary with the given values
     * @param value1 Binary String with length 32
     * @param value2 Binary String with length 32
     * @return Binary String with length 32
     */
    public static String orBinary_32_bits(String value1, String value2){
        int a = getInt(value1);
        int b = getInt(value2);
        int c = a | b;
        String result = Integer.toBinaryString(c);
        return Binary.normalizeSize(result);
    }

    /**
     * Function that execute a sum binary with the given values
     * @param value1 Binary String with length 32
     * @param value2 Binary String with length 32
     * @return Binary String with length 32
     */
    public static String addBinary_32_bits(String value1, String value2){
        int a = getInt(value1);
        int b = getInt(value2);
        int c = a + b;
        String result = Integer.toBinaryString(c);
        return Binary.normalizeSize(result);
    }

    /**
     * Function that execute a subtraction binary with the given values
     * @param value1 Binary String with length 32
     * @param value2 Binary String with length 32
     * @return Binary String with length 32
     */
    public static String subBinary_32_bits(String value1, String value2){
        int a = getInt(value1);
        int b = getInt(value2);
        int c = a - b;
        String result = Integer.toBinaryString(c);
        return Binary.normalizeSize(result);
    }

    /**
     * Function that return an int given a String with length of 32
     * @param binary32 String with length 32
     * @return int negative or positive
     */
    public static int getInt(String binary32){
        if(binary32.charAt(0) == '1'){//Negative
            Integer a = Integer.parseInt(binary32.substring(1), 2);
            String result = Integer.toBinaryString((~a) + 1);
            return Integer.parseInt(result.substring(1), 2) * -1;
        }else{
            return (int) Long.parseLong(binary32, 2);
        }
    }
}
