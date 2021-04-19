package src.hardware.arithmetic;
import src.utils.Binary;

/**
 * @author Levi
 */
public class ImmGen {
    private String input;
    private String immediate;
    
    /**
     * Constructor
     */
    public ImmGen() {
        this.input = Binary.BITS_32_ZERO;
        this.immediate = Binary.BITS_32_ZERO;
    }

    /**
     * Function that determines the input of the immediate gen unit
     * @param value String with 32 of length
     */
    public void setInput(String value){
        if(value.length() != 32){
            this.input = Binary.BITS_32_ZERO;
        }else{
            this.input = value;
        }
    }

    /**                                 
     *   Index of the String:        0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31
     *                               ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^ ^   ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^
     * Index of the instruction:    31 30 29 28 27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10  9  8  7  6  5  4  3  2  1  0
     */
    private void execute(){
        String opcode = this.input.substring(25);
        if(opcode.compareTo("1100011") == 0){//bFormat
            this.immediate = Binary.normalizeSizeWithSignal(this.input.substring(0, 1) + this.input.substring(24,25) + this.input.substring(1, 7) + this.input.substring(20, 24) + "0");
        }else if(opcode.compareTo("0000011") == 0){//lw
            this.immediate = Binary.normalizeSizeWithSignal(this.input.substring(0, 12));
        }else if(opcode.compareTo("0100011") == 0){//sw
            this.immediate = Binary.normalizeSizeWithSignal(this.input.substring(0, 7) + this.input.substring(20, 25));
        }else if(opcode.compareTo("0010011") == 0){//addi
            this.immediate = Binary.normalizeSizeWithSignal(this.input.substring(0, 12));
        }else {//rFormat
            this.immediate = Binary.BITS_32_ZERO;
        }        
    }

    /**
     * Function that returns the immediate
     * @return String with 32 of length
     */
    public String getOutput(){
        this.execute();
        return this.immediate;
    }
}
