package src.hardware.control;


/**
 * @author
 */
public class ALUControl {
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
     * Function that return the Boolean value of String 0 or 1
     * @param a char 0 or 1
     * @return Boolean
     */
    private Boolean getLogicValue(char a){
        if(a == '1'){
            return true;
        }
        return false;
    }

    /**
     * Function that return a String value given a Boolean
     * @param x -> true or false
     * @return "1" or "0"
     */
    private String boolToString(Boolean x){
        if(x){
            return "1";
        }
        return "0";
    }

    /**
     * Function that determines the output of ALUControl unit
     * @return String of length of 4
     */
    public String getControl(){
        Boolean f12, f13, f14, f30, ALUop0, ALUop1;
        Boolean r0, r1, r2, r3;
        f30 = this.getLogicValue(this.instruction.charAt(0));
        f14 = this.getLogicValue(this.instruction.charAt(1));
        f13 = this.getLogicValue(this.instruction.charAt(2));
        f12 = this.getLogicValue(this.instruction.charAt(3));

        ALUop0 = this.getLogicValue(this.ALUOp.charAt(1));
        ALUop1 = this.getLogicValue(this.ALUOp.charAt(0));

        
        r0 = ALUop1 && (f13 && !f12);
        r1 = !ALUop1 || !f14;
        r2 = ALUop0 || (ALUop1 && f30);
        r3 = ALUop0 && !ALUop0;
        

        System.out.println("30, 14-12: " + this.instruction);

        this.control = this.boolToString(r3) + this.boolToString(r2) + this.boolToString(r1) + this.boolToString(r0);

        return this.control;
    }
}
