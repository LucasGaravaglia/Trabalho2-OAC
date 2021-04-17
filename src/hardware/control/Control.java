package src.hardware.control;

import src.utils.*;

/**
 * @author Levi
 */
public class Control {
    private String input;    
    private String MemRead;
    private String MemToReg;
    private String ALUOp;
    private String MemWrite;
    private String ALUSrc;
    private String RegWrite;
    private String branch0
    private String branch1;

    /**
     * Function that determines the input of the control, bits 0-6 of the instruction
     * @param value String with length of 8
     */
    public void setInput(String value){
        if(value.length() == 8){
            this.input = value;
            this.execute();
        }else{
            this.input = "0000000";
        }
    }

    /**
     * Function that determines the value of the outputs of the control unit
     */
    private void execute(){
        Boolean o0, o1, o2, o3, o4, o5, o6, o7;
        Boolean rFormat, lw, sw, bFormat, iFormat;
        
        o7 = !TypesConversion.getLogicValueFromString(this.input.substring(0, 1));
        o6 = !TypesConversion.getLogicValueFromString(this.input.substring(1, 2));
        o5 = !TypesConversion.getLogicValueFromString(this.input.substring(2, 3));
        o4 = !TypesConversion.getLogicValueFromString(this.input.substring(3, 4));
        o3 = !TypesConversion.getLogicValueFromString(this.input.substring(4, 5));
        o2 = !TypesConversion.getLogicValueFromString(this.input.substring(5, 6));
        o1 = !TypesConversion.getLogicValueFromString(this.input.substring(6, 7));
        o0 = !TypesConversion.getLogicValueFromString(this.input.substring(7, 8));
        
        rFormat = o6 && (!o5) && (!o4) && o3 && o2 && (!o1) && (!o0);
        lw = o6 && o5 && o4 && o3 && o2 && (!o1) && (!o0);
        sw = o6 && (!o5) && o4 && o3 && o2 && (!o1) && (!o0);
        bFormat = (!o6) && (!o5) && o4 && o3 && o2 && (!o1) && (!o0);
        iFormat = o6 && o5 && (!o4) && o3 && o2 && (!o1) && (!o0);

        this.branch0 = TypesConversion.boolToString(bFormat);
        this.branch1 = TypesConversion.boolToString(bFormat && o7);
        this.MemRead = TypesConversion.boolToString(lw);
        this.MemToReg = TypesConversion.boolToString(lw);
        this.MemWrite = TypesConversion.boolToString(sw);
        this.RegWrite = TypesConversion.boolToString(rFormat || lw || iFormat);
        this.ALUSrc = TypesConversion.boolToString(lw || sw || iFormat);
        this.ALUOp = TypesConversion.boolToString(rFormat) + TypesConversion.boolToString(bFormat);
    }


    /**
     * Function that bit of the flag branch
     * @return String "0"or "1"
     */
    public String getBranch0(){
        return this.branch0;
    }

    /**
     * Function that bit of the flag branch
     * @return String "0"or "1"
     */
    public String getBranch1(){
        return this.branch1;
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
