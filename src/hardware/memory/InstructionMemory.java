package src.hardware.memory;

/**
 * @author Levi
 */
public class InstructionMemory {
    private String[] instructions;

    /**
     * 
     * @param instructions 32 bits instructions 
     */
    public InstructionMemory(String[] instructions){
        this.setInstructionMemory(instructions);
    }

    /**
     * @param instructions 32 bits instructions 
     */
    public void setInstructionMemory(String[] instructions){
        this.instructions = instructions;
    }

    /**
     * @return String value of all instructions storaged
     */
    public String toString(){
        String text = "";
        for(String x: this.instructions){
            text += x + "\n";
        }        
        return text;
    }

    /**
     * 
     * @param adress_32_bits 32 bits adress
     * @return 32 bits instruction storaged
     */
    public String getInstruction(String adress_32_bits){
        String adress_16_bits = adress_32_bits.substring(16);
        int index = Integer.parseInt(adress_16_bits, 2);
        return this.instructions[index];
    }
}