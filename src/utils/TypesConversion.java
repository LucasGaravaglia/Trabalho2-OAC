package src.utils;

public class TypesConversion {
    
    /**
     * Function that returns the Boolean value of Char "0" or "1"
     * @param a char 0 or 1
     * @return Boolean
     */
    public static Boolean getLogicValueFromChar(char value){
        if(value.compareTo("1") == 0){
            return true;
        }
        return false;
    }

    /**
     * Function that returns the Boolean value of String "0" or "1"
     * @param a char 0 or 1
     * @return Boolean
     */
    public static Boolean getLogicValueFromString(String value){
        if(value.compareTo("1") == 0){
            return true;
        }
        return false;
    }

    /**
     * Function that return a String value given a Boolean
     * @param x -> true or false
     * @return "1" or "0"
     */
    public static String boolToString(Boolean x){
        if(x){
            return "1";
        }
        return "0";
    }
}
