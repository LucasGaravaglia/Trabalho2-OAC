package src.hardware.memory;


/**
 * @author Levi
 */
public class Pc {
    private String value;

    /**
     * Constructor
     */
    public Pc(){
        this.value = src.utils.Binary.BITS_32_ZERO;
    }

    /**
     * Function that allows to set the input value to the PC Register
     * @param value String with 32 of length
     */  
    public void setValue(String value){
        if(value.length() == 32){
            this.value = value;
        }else{
            this.value = src.utils.Binary.BITS_32_ZERO;
        }        
    }

    /**
     * Function that returns the current output od the pc Register
     * @return String with 32 of length
     */  
    public String getValue(){
        return this.value;
    }
}