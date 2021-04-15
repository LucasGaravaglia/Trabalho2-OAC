package src.hardware.memory;
import src.utils.*;

/**
 * @author Levi
 */
public class DataMemory {
    private String[] memory;
    private String adress;
    private String valueToWrite;
    private int MemWrite;
    private int MemRead;
    private String dataRead;

    public DataMemory(){
        this.memory = new String[Defines.DATA_MEMORY_SIZE];
        for(String x: this.memory){
            x = Binary.BITS_32_ZERO;
        }
        this.adress = "0";
        this.valueToWrite = "0";
        this.MemWrite = 0;
        this.MemRead = 0;
        this.dataRead = Binary.BITS_32_ZERO;
    }

    public void setMemory(String[] newMemory){
        this.memory = newMemory;
    }

    public String[] getMemory(){
        return this.memory;
    }

    public String toString(){
        String text = "";
        for(String x: this.memory){
            text += x + "\n";
        }
        return text;
    }

    public String getAdress(){
        return this.adress;
    }

    public void setAdress(String adress){
        this.adress = adress;
    }

    public String getValueToWrite(){
        return this.valueToWrite;
    }

    public void setValueToWrite(String value){
        if(value.length() == 32){
            this.valueToWrite = value;
        }else{
            this.valueToWrite = Binary.BITS_32_ZERO;
        }
    }

    public void setMemWrite(int bit){
        if(bit == 1){
            this.MemWrite = bit;
            memory[Integer.parseInt(this.adress, 2)] = this.valueToWrite;
        }else{
            this.MemWrite = 0;
        }
    }

    public int getMemWrite(){
        return this.MemWrite;
    }

    public void setMemRead(int bit){
        if(bit == 1){
            this.MemRead = bit;
            this.dataRead = this.memory[Integer.parseInt(this.adress, 2)];
        }else{
            this.MemRead = 0;
        }
           
    }

    public String getReadData(){
        return this.dataRead;
    }

}
