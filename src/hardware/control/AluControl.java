package src.hardware.control;

import src.utils.*;

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
     * Function that determines the output of ALUControl unit
     * @return String of length of 4
     */
    public String getControl(){
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

        return this.control;
    }
}
