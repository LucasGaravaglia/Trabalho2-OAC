package src.hardware.control;

import src.utils.Binary;

public class ALUControl {
    private String ALUOp;
    private String instruction;
    private String control;

    public void setALUOp(String value){
        if(value.length() != 2){
            this.ALUOp = "00";
        }else{
            this.ALUOp = value;
        }
    }

    public void setInstruction(String value){
        if(value.length() != 4){
            this.instruction = "0000";
        }else{
            this.instruction = value;
        }
    }

    private Boolean getLogicValue(char a){
        if(a == '1'){
            return true;
        }
        return false;
    }

    private String boolToString(Boolean x){
        if(x){
            return "1";
        }
        return "0";
    }

    public String getControl(){
        Boolean f0, f1, f2, f3, ALUop0, ALUop1;
        Boolean r0, r1, r2, r3;
        f0 = this.getLogicValue(this.instruction.charAt(0));
        f1 = this.getLogicValue(this.instruction.charAt(1));
        f2 = this.getLogicValue(this.instruction.charAt(2));
        f3 = this.getLogicValue(this.instruction.charAt(3));

        ALUop0 = this.getLogicValue(this.ALUOp.charAt(0));
        ALUop1 = this.getLogicValue(this.ALUOp.charAt(1));

        r0 = (f0 || f3) && ALUop1;
        r1 = !f2 || !ALUop1;
        r2 = (f1 && ALUop1) || ALUop0;
        r3 = ALUop0 && !ALUop0;

        this.control = this.boolToString(r0) + this.boolToString(r1) + this.boolToString(r2) + this.boolToString(r3);

        return this.control;
    }
}
