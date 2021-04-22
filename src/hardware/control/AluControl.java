package src.hardware.control;

import src.utils.*;
import src.log.Log;
/**
 * @author Levi
 */
public class AluControl {
    private String ALUOp;
    private String instruction;
    private String control;

    /**
     * Function that determines the value of que ALUop
     * @param value String with of length 2
     */
    public void setALUOp(String value){
        if(value.length() != 2){
            this.ALUOp = "00";
        }else{
            this.ALUOp = value;
        }
        this.execute();
    }

    /**
     * Function that returns the current aluOp
     * @return  String with length of 2
     */
    public String getCurrentAluOp(){
        return this.ALUOp;
    }

    /**
     * Function that determines the first input of the ALUControl 
     * @param value String with length of 4
     */
    public void setInstruction(String value){
        if(value.length() != 4){
            this.instruction = "0000";
        }else{
            this.instruction = value;
        }
    }

    /**
     * Function that return the current instruction input
     * @return String with 32 of length
     */
    public String getCurrentInstruction(){
        return this.instruction;
    }

    /**
     * Function that determines the output of tue aluControl
     */
    private void execute(){
        Boolean f12, f13, f14, f30, ALUop0, ALUop1;
        Boolean r0, r1, r2, r3;
        f30 = TypesConversion.getLogicValueFromChar(this.instruction.charAt(0));
        f14 = TypesConversion.getLogicValueFromChar(this.instruction.charAt(1));
        f13 = TypesConversion.getLogicValueFromChar(this.instruction.charAt(2));
        f12 = TypesConversion.getLogicValueFromChar(this.instruction.charAt(3));

        ALUop0 = TypesConversion.getLogicValueFromChar(this.ALUOp.charAt(1));
        ALUop1 = TypesConversion.getLogicValueFromChar(this.ALUOp.charAt(0));

        
        r0 = ALUop1 && (f13 && !f12);
        r1 = !ALUop1 || !f14;
        r2 = ALUop0 || (ALUop1 && f30);
        r3 = ALUop0 && !ALUop0;

        this.control = TypesConversion.boolToString(r3) + TypesConversion.boolToString(r2) + TypesConversion.boolToString(r1) + TypesConversion.boolToString(r0);

    }
    

    /**
     * Function that returns the output of ALUControl unit
     * @return String of length of 4
     */
    public String getControl(){        
        return this.control;
    }


    /**
     * Function that print all information of the component in the log file
     */
    public void doLog(){
        Log.doLog("********* AluControl *******");        
        Log.doLog("ALUOp: " + this.ALUOp);
        Log.doLog("instruction: " + this.instruction);
        Log.doLog("control: " + this.control);
    }

    /**
     * Function that print all information of the component in the log file
     */
    public void doLog(String m){
        Log.doLog("********* AluControl *******");   
        Log.doLog(m);     
        Log.doLog("ALUOp: " + this.ALUOp);
        Log.doLog("instruction: " + this.instruction);
        Log.doLog("control: " + this.control);
    }
}
