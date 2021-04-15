package src.hardware.control;
import src.utils.*;

/**
 * @author Levi
 */
public class Mux2X {
    private String value1;
    private String value2;
    private int bit;

    public Mux2X(){
        this.value1 = Binary.BITS_32_ZERO;
        this.value2 = Binary.BITS_32_ZERO;
        this.bit = 0;
    }

    /**
     * Function that set the first input of the mux
     * @param value_32_bits
     */
    public void setValue1(String value_32_bits){
        if(value_32_bits.length() == 32){
            this.value1 = value_32_bits;
        }else{
            this.value1 = Binary.BITS_32_ZERO;
        }
    }

    /**
     * Function that set the second input of the mux
     * @param value_32_bits
     */
    public void setValue2(String value_32_bits){
        if(value_32_bits.length() == 32){
            this.value2 = value_32_bits;
        }else{
            this.value2 = Binary.BITS_32_ZERO;
        }
    }

    /**
     * Function that returns the mux set bit
     * @return the currect bit setted in the mux
     */
    public int getBit(){
        return this.bit;
    }

    /**
     * Funtion tha set the bit of the mux
     * @param value 0 or 1
     */
    public void setBit(int value){
        if(value == 0 || value == 1){
            this.bit = value;
        }        
    }

    /**
     * Function to obtain the resulto of the mux based in the given inputs
     * @return a 32 bit result
     */
    public String getResult(){
        if(this.bit == 1){
            return this.value2;
        }
        return this.value1;
    }

}
