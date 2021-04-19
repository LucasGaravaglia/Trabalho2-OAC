package src.hardware.control;
import src.utils.*;

/**
 * @author Levi
 */
public class Mux2X {
    private String value1;
    private String value2;
    private String bit;

    public Mux2X(){
        this.value1 = Binary.BITS_32_ZERO;
        this.value2 = Binary.BITS_32_ZERO;
        this.bit = "0";
    }

    /**
     * Function that return the value in the first input of the mux
     * @return String with 32 of length
     */
    public String getValue1(){
        return this.value1;
    }

    /**
     * Function that return the value in the first input of the mux
     * @return String with 32 of length
     */
    public String getValue2(){
        return this.value2;
    }

    /**
     * Function that set the first input of the mux
     * @param value String with length of 32
     */
    public void setValue1(String value){
        if(value.length() == 32){
            this.value1 = value;
        }else{
            this.value1 = Binary.BITS_32_ZERO;
        }
    }

    /**
     * Function that set the second input of the mux
     * @param value String with length of 32
     */
    public void setValue2(String value){
        if(value.length() == 32){
            this.value2 = value;
        }else{
            this.value2 = Binary.BITS_32_ZERO;
        }
    }

    /**
     * Function that returns the mux set bit
     * @return the current bit in the mux, "0" or "1"
     */
    public String getBit(){
        return this.bit;
    }

    /**
     * Function tha set the bit of the mux
     * @param value String "0" or "1"
     */
    public void setBit(String value){
        if(value.compareTo("1") == 0 || value.compareTo("0") == 0){
            this.bit = value;
        }        
    }

    /**
     * Function to obtain the result of the mux based in the given inputs
     * @return a 32 bit result
     */
    public String getResult(){
        if(this.bit.compareTo("1") == 0){
            return this.value2;
        }
        return this.value1;
    }

}
