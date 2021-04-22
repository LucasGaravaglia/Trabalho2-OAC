package src.control.simulation;

public class State {
    private String[] memory;
    private String[] registers;
    private String[] signals;
    private String pc;

    /**
     * Constructor.
     * @param memory Data memory.
     * @param registers Registers.
     * @param signals CPU.Control signals 6 + alu zero flag.
     * @param pc Program Counter.
     */
    public State(String[] memory, String[] registers, String[] signals, String pc) {
        this.memory = memory;
        this.registers = registers;
        this.signals = signals;
        this.pc = pc;
    }

    /**
     * Memory setter.
     * @param memory Data memory.
     */
    public void setMemory(String[] memory) {
        this.memory = memory.clone();
    }

    /**
     * Registers setter
     * @param registers Registers.
     */
    public void setRegisters(String[] registers) {
        this.registers = registers.clone();
    }

    /**
     * Signals setter.
     * @param signals CPU.Control signals 6 + alu zero flag.
     */
    public void setSignals(String[] signals) {
        this.signals = signals.clone();
    }

    /**
     * Pc setter.
     * @param pc Program Counter.
     */
    public void setPc(String pc) {
        this.pc = pc;
    }

    /**
     * Memory getter.
     * @return Data memory.
     */
    public String[] getMemory() {
        return this.memory.clone();
    }

    /**
     * Registers getter.
     * @return Registers.
     */
    public String[] getRegisters() {
        return this.registers.clone();
    }

    /**
     * Signals getter.
     * @return CPU.Control signals 6 + alu zero flag.
     */
    public String[] getSignals() {
        return this.signals.clone();
    }

    /**
     * Pc getter.
     * @return Program Counter.
     */
    public String getPc() {
        return this.pc;
    }
}
