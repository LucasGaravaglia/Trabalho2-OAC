package src.utils;

public class Binary {
    public static final String BITS_32_ZERO =  "00000000000000000000000000000000";
    public static final String BITS_5_ZERO =   "00000000000000000000000000000000";
    public static final String BITS_32_ALL_1 = "11111111111111111111111111111111";

    public static Integer getDecimalValue(String bits){
        return Integer.parseInt(bits, 2);
    }
    
    public static String get32BitsStringValue(Integer value){
        String binary = Integer.toBinaryString(value);

        if(binary.length() > 32){
            return BITS_32_ZERO;
        }

        if(binary.length() == 32){
            return binary;
        }else{
            String newBinary = "";
            for(int i = 0; i < binary.length();i++){
                newBinary += "0";
            }
            return newBinary + binary;
        }
    }
}
