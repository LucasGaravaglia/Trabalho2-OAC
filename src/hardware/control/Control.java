package src.hardware.control;

/**
 * @author Levi
 */
public class Control {
    private String input;
    private String branch;
    private String MemRead;
    private String MemToReg;
    private String ALUOp;
    private String MemWrite;
    private String ALUSrc;
    private String RegWrite;

    /**
     * Function that determines the input of the control, bits 0-6 of the instruction
     * @param value String with length of 7
     */
    public void setInput(String value){
        if(value.length() == 7){
            this.input = value;
            this.execute();
        }else{
            this.input = "0000000";
        }
    }

    private void execute(){
        
    }


    /**
     * Function that bit of the flag branch
     * @return String "0"or "1"
     */
    public String getBranch(){
        return this.branch;
    }
    
    /**
     * Function that bit of the flag MemRead
     * @return String "0"or "1"
     */
    public String getMemRead(){
        return this.MemRead;
    }
    
    /**
     * Function that bit of the flag MemToReg
     * @return String "0"or "1"
     */
    public String getMemToReg(){
        return this.MemToReg;
    }
    
    /**
     * Function that bit of the flag ALUOp
     * @return String "00", "01", "10" or "11"
     */
    public String getALUOp(){
        return this.ALUOp;
    }
    
    /**
     * Function that bit of the flag MemWrite
     * @return String "0"or "1"
     */
    public String getMemWrite(){
        return this.MemWrite;
    }
    
    /**
     * Function that bit of the flag ALUSrc
     * @return String "0"or "1"
     */
    public String getALUSrc(){
        return this.ALUSrc;
    }
    
    /**
     * Function that bit of the flag RegWrite
     * @return String "0"or "1"
     */
    public String getRegWrite(){
        return this.RegWrite;
    }
    
    

}
