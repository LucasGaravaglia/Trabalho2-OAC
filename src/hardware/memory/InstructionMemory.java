package src.hardware.memory;

public class InstructionMemory {
    private String[] instructions;

    public InstructionMemory(String[] instructions){
        this.instructions = instructions;
    }

    public String toString(){
        String text = "";
        for(String x: this.instructions){
            text += x + "\n";
        }
        return text;
    }
    //Comment
    public String getInstruction(int adress){
        return this.instructions[adress];
    }
}