package src;

public class InstructionMemory {
    private String[] instructions;

    InstructionMemory(String[] instructions){
        this.instructions = instructions;
    }
    //Comment
    public String getInstruction(int adress){
        return this.instructions[adress];
    }
}