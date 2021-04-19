package src.hardware.memory;



/**
 * @author Levi
 */
public class InstructionMemory {
    private String[] instructions;
    private String readAddress; 

    /**
     * Constructor
     * Each instruction per index
     * @param instructions 32 bits instructions, String with length 32 per index
     */
    public InstructionMemory(String[] instructions){
        this.setInstructionMemory(instructions);
        this.readAddress = "000000";
    }

    /**
     * Function that update all memory replacing it
     * @param instructions 32 bits instructions 
     */
    public void setInstructionMemory(String[] instructions){
        this.instructions = instructions.clone();
        for(int i = 0; i < this.instructions.length; i++){
            if(this.instructions[i].length() != 32){
               this.instructions[i] = src.utils.Binary.BITS_32_ZERO; 
            }
        }
    }
    
    /**
     * Function that clone the content of the memory
     * @return a String array, one instruction per index
     */
    public String[] getMemoryClone(){
        return this.instructions.clone();
    }

    /**
     * Function convert content to String
     * @return String value of all instructions stored
     */
    public String toString(){
        String text = "";
        for(String x: this.instructions){
            text += x + "\n";
        }        
        return text;
    }

    /**
     * Function that should be used for read a value of the memory instruction
     * @param address_32_bits 32 bits address
     * @return 32 bits instruction stored
     */
    public String getInstruction(String address_32_bits){
        String address_16_bits = address_32_bits.substring(16);
        int index = Integer.parseInt(address_16_bits, 2);
        return this.instructions[index];
    }

    /**
     * Function that determines which address will be read
     * @param readAddress binary value
     */
    public void setReadAddress(String readAddress){
        if(this.instructions.length >= Long.parseLong(readAddress, 2)){
            if(readAddress.length() >= 32){
                this.readAddress = readAddress.substring(1);
            }else{
                this.readAddress = readAddress;
            }            
        }else{
            this.readAddress = "0";
        }        
    }

    /**
     * Function that represents the output 6-0 of the instruction
     * Index of the String:        0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31
     *                             ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^ ^   ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^  ^
     * Index of the instruction:  31 30 29 28 27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10  9  8  7  6  5  4  3  2  1  0
     * @return
     */ 
    public String get12and6to0(){
        String value = this.instructions[Integer.parseInt(this.readAddress, 2)];
        return value.substring(19, 20) + value.substring(25);
    }

    /**
     * Function that represents the output 11-7 of the instruction
     * @return
     */
    public String get11to7(){
        String value = this.instructions[Integer.parseInt(this.readAddress, 2)];
        return value.substring(20, 25);
    }

    /**
     * Function that represents the output 14-12 of the instruction
     * @return
     */
    public String get14to12(){
        String value = this.instructions[Integer.parseInt(this.readAddress, 2)];
        return value.substring(17, 20);
    }

    /**
     * Function that represents the output 15-19 of the instruction
     * @return
     */
    public String get19to15(){
        String value = this.instructions[Integer.parseInt(this.readAddress, 2)];
        return value.substring(12, 17);
    }

    /**
     * Function that represents the output 20-24 of the instruction
     * @return
     */
    public String get24to20(){
        String value = this.instructions[Integer.parseInt(this.readAddress, 2)];
        return value.substring(7, 12);
    }

    /**
     * Function that represents the output 30,14-12
     * @return
     */
    public String get30and14to12(){
        String value = this.instructions[Integer.parseInt(this.readAddress, 2)];
        return value.substring(1, 2)+value.substring(17, 20);
    }

    /**
     * Function that represents the output 31-0
     * @return
     */
    public String get31to0(){
        String value = this.instructions[Integer.parseInt(this.readAddress, 2)];
        return value;
    }
}