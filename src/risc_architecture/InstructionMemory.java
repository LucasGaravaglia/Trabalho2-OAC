package risc_architecture;

public class InstructionMemory {
    private String[] instructions;

    InstructionMemory(String[] instructions){
        this.instructions = instructions;
    }

    public String getInstruction(int adress){
        return this.InstructionMemory[adress];
    }
}