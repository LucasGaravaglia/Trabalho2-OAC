package src.control.simulation;

public class State {
    private String[] memory;
    private String[] registers;
    private String[] signals;
    private String pc;

    public State(String[] memory, String[] registers, String[] signals, String pc) {
        this.memory = memory;
        this.registers = registers;
        this.signals = signals;
        this.pc = pc;
    }

    public void setMemory(String[] memory) {
        this.memory = memory.clone();
    }

    public void setRegisters(String[] registers) {
        this.registers = registers.clone();
    }

    public void setSignals(String[] signals) {
        this.signals = signals.clone();
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String[] getMemory() {
        return this.memory.clone();
    }

    public String[] getRegisters() {
        return this.registers.clone();
    }

    public String[] getSignals() {
        return this.signals.clone();
    }

    public String getPc() {
        return this.pc;
    }
}
