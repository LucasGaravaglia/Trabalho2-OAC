package src.hardware.memory;
import src.utils.*;

/**
 * @author Levi
 */
public class DataMemory {
    private String[] memory;
    private String address;
    private String valueToWrite;
    private String MemWrite;
    private String MemRead;
    private String dataRead;

    /**
     * Constructor
     */
    public DataMemory(){
        this.memory = new String[Defines.DATA_MEMORY_SIZE];
        for(int i = 0; i < this.memory.length; i++){
            this.memory[i] = Binary.BITS_32_ZERO;
        }
        this.address = "0";
        this.valueToWrite = "0";
        this.MemWrite = "0";
        this.MemRead = "0";
        this.dataRead = Binary.BITS_32_ZERO;
    }

    /**
     * Function that returns the current address int the memory
     * @return String with 32 of length
     */
    public String getCurrentAddress(){
        return this.address;
    }

    /**
     * Function that returns the current value to write in the memory 
     * @return String with 32 of length
     */
    public String getCurrentWriteValue(){
        return this.valueToWrite;
    }

    /**
     * Function that returns the current MemWrite flag
     * @return "0" or "1"
     */
    public String getCurrentMemWrite(){
        return this.MemWrite;
    }

    /**
     * Function that returns the current MemRead flag
     * @return "0" or "1"
     */
    public String getCurrentMemRead(){
        return this.MemRead;
    }

    /**
     * Function that overwrite all memory at once
     * @param newMemory an Array os Strings, each string with length o 32
     */
    public void setMemory(String[] newMemory){
        if(newMemory.length == Defines.DATA_MEMORY_SIZE){
            this.memory = newMemory.clone();
        }       
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
        for(int i = 0;i < this.memory.length;i++){
            text += "["+(i*4)+"]:\t" + this.memory[i] + "\n";
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
     * Function that perform the memory write internally 
     */
    private void writeMemory(){
        if(this.MemWrite.compareTo("1") == 0){
            int l = (int)Long.parseLong(this.address, 2);
            l = l/4;
            memory[l] = this.valueToWrite;
        }
    }

    /**
     * Function that set the bit that determines if the memory will be written 
     * @param bit String "1" or "0"
     */
    public void setMemWrite(String bit){
        if(bit.compareTo("1") == 0){
            this.MemWrite = bit;
            this.writeMemory();
        }else{
            this.MemWrite = "0";
        }
    }

    /**
     * Function that read data from memory internally 
     */
    private void readMemory(){
        if(this.MemRead.compareTo("1") == 0){
            int memoryAddress = (int) Long.parseLong(this.address, 2);
            memoryAddress = memoryAddress/4;
            this.dataRead = this.memory[memoryAddress];
        }
    }

    /**
     * Function that set the bit that determines if the memory will be read 
     * @param bit String "0" or "1"
     */
    public void setMemRead(String bit){
        if(bit.compareTo("1") == 0){
            this.MemRead = bit;
            this.readMemory();
        }else{
            this.MemRead = "0";
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
