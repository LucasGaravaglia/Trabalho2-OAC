package src.hardware.memory;
import src.utils.*;

/**
 * @author Levi
 */
public class DataMemory {
    private String[] memory;
    private String address;
    private String valueToWrite;
    private int MemWrite;
    private int MemRead;
    private String dataRead;

    /**
     * Constructor
     */
    public DataMemory(){
        this.memory = new String[Defines.DATA_MEMORY_SIZE];
        for(String x: this.memory){
            x = Binary.BITS_32_ZERO;
        }
        this.address = "0";
        this.valueToWrite = "0";
        this.MemWrite = 0;
        this.MemRead = 0;
        this.dataRead = Binary.BITS_32_ZERO;
    }

    /**
     * Function that overwrite all memory at once
     * @param newMemory an Array os Strings, each string with length o 32
     */
    public void setMemory(String[] newMemory){
        this.memory = newMemory.clone();
    }

    /**
     * Function that clone the current data in memory
     * @return an Array os Strings, each string with length o 32
     */
    public String[] getMemory(){
        return this.memory.clone();
    }

    /**
     * Function that return the data in memory in string format
     */
    public String toString(){
        String text = "";
        for(String x: this.memory){
            text += x + "\n";
        }
        return text;
    }

    /**
     * Function that change the current address for read/write in memory
     * @param address
     */
    public void setAddress(String address){
        this.address = address;
    }

    /**
     * Function that set the value that will be write in the memory
     * @param value String with length of 32
     */
    public void setValueToWrite(String value){
        if(value.length() == 32){
            this.valueToWrite = value;
        }else{
            this.valueToWrite = Binary.BITS_32_ZERO;
        }
    }

    /**
     * Function that set the bit that determines if the memory will be written 
     * @param bit 1 or 0
     */
    public void setMemWrite(int bit){
        if(bit == 1){
            this.MemWrite = bit;
            memory[Integer.parseInt(this.address, 2)] = this.valueToWrite;
        }else{
            this.MemWrite = 0;
        }
    }

    /**
     * Function that set the bit that determines if the memory will be read 
     * @param bit 1 or 0
     */
    public void setMemRead(int bit){
        if(bit == 1){
            this.MemRead = bit;
            int memoryAddress = (int) Long.parseLong(this.address, 2);
            this.dataRead = this.memory[memoryAddress];
        }else{
            this.MemRead = 0;
        }
           
    }

    /**
     * Function that returns the value read from memory
     * @return String of length 32
     */
    public String memoryReadResult(){
        return this.dataRead;
    }

}
