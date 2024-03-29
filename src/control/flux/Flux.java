package src.control.flux;

import src.hardware.arithmetic.*;
import src.hardware.control.*;
import src.hardware.memory.*;
import src.control.simulation.Simulation;
import src.utils.Binary;
import src.control.simulation.State;
import src.gui.data.Data;
public class Flux {
    private Simulation simulation;
    private Alu alu;
    private Alu firstAuxAlu;
    private Alu secondAuxAlu;
    private ImmGen immGen;
    private AluControl aluControl;
    private BranchControl branchControl;
    private Control control;
    private Mux2X registersAluMux;
    private Mux2X dataMemoryRegistersMux;
    private Mux2X addrAluPcMux;
    private DataMemory dataMemory;
    private InstructionMemory instructionMemory;
    private Pc pc;
    private Registers registers;

    /**
     * Constructor.
     */
    public Flux() {
        this.simulation = new Simulation();
        this.alu = new Alu();
        this.firstAuxAlu = new Alu();
        this.secondAuxAlu = new Alu();
        this.immGen = new ImmGen();
        this.aluControl = new AluControl();
        this.branchControl = new BranchControl();
        this.control = new Control();
        this.registersAluMux = new Mux2X();
        this.dataMemoryRegistersMux = new Mux2X();
        this.addrAluPcMux = new Mux2X();
        this.dataMemory = new DataMemory();
        this.pc = new Pc();
        this.registers = new Registers();
    }

    /**
     * Create a string array with all cpu signals.
     * @return Array of strings containing all the cpu.control signals + 
     * alu zero flag.
     */
    private String[] getSignals() {
        String[] sig = new String[10];
        sig[0] = String.valueOf(this.control.getALUOp().charAt(0));
        sig[1] = String.valueOf(this.control.getALUOp().charAt(1));
        sig[2] = this.control.getALUSrc();
        sig[3] = String.valueOf(this.control.getBranch().charAt(0));
        sig[4] = String.valueOf(this.control.getBranch().charAt(1));
        sig[5] = this.control.getMemRead();
        sig[6] = this.control.getMemToReg();
        sig[7] = this.control.getMemWrite();
        sig[8] = this.control.getRegWrite();
        sig[9] = String.valueOf(this.alu.getZeroFlag());
        return sig;
    }

    /**
     * Set pc to 0, insert new instructions in the instruction memory and store
     * the new state of the cpu in the simulation.
     * @param instructions Instructions to be loaded in the cpu instruction
     * memory.
     */
    public void setInstructions(String[] instructions) {
        this.pc.setValue("0");
        this.instructionMemory = new InstructionMemory(instructions);
        this.simulation.clearList();
        this.simulation.push(this.dataMemory.getMemory(), 
            this.registers.cloneRegisters(), 
            this.getSignals(), 
            this.pc.getValue());
    }

    /**
     * Return the actual state of the processor.
     * @return Actual state of the processor formatted for interface data.
     */
    public Data getState() {
        Data d = new Data();
        d.setMemory(this.dataMemory.toString());
        d.setRegister(this.registers.toString());
        d.setPc(this.pc.getValue());
        d.setSignals(this.getSignals());
        return d;
    }

    /**
     * Steps of execution for first auxiliary alu.
     */
    private void executeFirstAuxAlu() {
        this.firstAuxAlu.setData1(this.pc.getValue());
        this.firstAuxAlu.setData2(Binary.get32BitsStringValue(4));
        this.firstAuxAlu.setALUControl("0010"); // add
        this.addrAluPcMux.setValue1(this.firstAuxAlu.getResult());
        this.firstAuxAlu.doLog("First Aux Alu");
    }

    /**
     * Get all commands generated by the instruction memory and send them to 
     * their respective components.
     * @throws Exception End of instruction memory.
     */
    private void executeInstructionMemory() throws Exception{
        String controlInstruction;
        String registersInstruction1;
        String registersInstruction2;
        String registersWriteDataInstruction;
        String immGenInstruction;
        String aluControlInstruction;

        // Instruction memory
        if(!this.instructionMemory.setReadAddress(this.pc.getValue()))
            throw new Exception("End of instruction memory");

        controlInstruction = this.instructionMemory.get12and6to0();
        registersInstruction1 = this.instructionMemory.get19to15();
        registersInstruction2 = this.instructionMemory.get24to20();
        registersWriteDataInstruction = this.instructionMemory.get11to7();
        immGenInstruction = this.instructionMemory.get31to0();
        aluControlInstruction = this.instructionMemory.get30and14to12();

        // Control
        this.control.setInput(controlInstruction);
        // Registers
        this.registers.setAddressOfReadRegisterA(registersInstruction1);
        this.registers.setAddressOfReadRegisterB(registersInstruction2);
        this.registers.setAddressOfWriteRegister(registersWriteDataInstruction);
        // immGen
        this.immGen.setInput(immGenInstruction);
        // Alu control
        this.aluControl.setInstruction(aluControlInstruction);
        this.instructionMemory.doLog();
    }

    /**
     * Send some cpu flag signals to their respective components.
     * Write and Read flags from registers and memory are not sent here.
     */
    private void executeControl() {
        this.branchControl.setBranch(this.control.getBranch());
        this.dataMemoryRegistersMux.setBit(this.control.getMemToReg());
        this.aluControl.setALUOp(this.control.getALUOp());
        this.registersAluMux.setBit(this.control.getALUSrc());
        this.control.doLog();
    }

    /**
     * Set data in Alu and Data Memory.
     */
    private void executeRegisters() {
        this.alu.setData1(this.registers.getData1());
        this.registersAluMux.setValue1(this.registers.getData2());
        this.dataMemory.setValueToWrite(this.registers.getData2());
        this.registers.doLog();
    }

    /**
     * Set value 2 in registersAluMux.
     * Set data 2 in second aux alu.
     */
    private void executeImmGen() {
        this.registersAluMux.setValue2(this.immGen.getOutput());
        this.secondAuxAlu.setData2(this.immGen.getOutput());
        this.immGen.doLog();
    }

    /**
     * Set data 2 in alu.
     */
    private void executeRegistersAluMux() {
        this.alu.setData2(this.registersAluMux.getResult());
        this.registersAluMux.doLog("Registers Alu Mux");
    }

    /**
     * Set AluControl in alu.
     * Execute Alu.
     */
    private void executeAluControl() {
        this.alu.setALUControl(this.aluControl.getControl());
        this.aluControl.doLog();
    }

    /**
     * Set data 1 in secondAuxAlu.
     * Set AluControl in secondAuxAlu.
     * Set value 2 in addrAluPcMux.
     */
    private void executeSecondAuxAlu() {
        this.secondAuxAlu.setData1(this.pc.getValue());
        this.secondAuxAlu.setALUControl("0010"); // add
        this.addrAluPcMux.setValue2(this.secondAuxAlu.getResult());
        this.secondAuxAlu.doLog("Second Aux Alu");
    }

    /**
     * Set zero flag in branch control from alu.
     * Set address in data memory from alu.
     * Set value 2 in data memory registers mux from alu.
     * Set MemRead and MemWrite in data memory(Execute).
     */
    @Deprecated
    private void executeAlu() {
        this.branchControl.setZero(new Integer(this.alu.getZeroFlag()).toString());
        this.dataMemory.setAddress(this.alu.getResult());
        this.dataMemoryRegistersMux.setValue1(this.alu.getResult());
        this.dataMemory.setMemRead(this.control.getMemRead());
        this.dataMemory.setMemWrite(this.control.getMemWrite());
        this.alu.doLog();
    }

    /**
     * Set the bit of addrAluPcMux from branch control.
     */
    private void executeBranchControl() {
        this.addrAluPcMux.setBit(this.branchControl.getOutput());
        this.branchControl.doLog();
    }

    /**
     * Set value 1 in data memory registers mux from data memory.
     */
    private void executeDataMemory() {
        this.dataMemoryRegistersMux.setValue2(this.dataMemory.memoryReadResult());
        this.dataMemory.doLog();
    }

    /**
     * Set write value from data memory registers mux.
     */
    private void executeDataMemoryRegistersMux() {
        this.registers.setWriteValue(this.dataMemoryRegistersMux.getResult());
        this.registers.setRegWrite(this.control.getRegWrite());
        this.dataMemoryRegistersMux.doLog("Data Memory Registers Mux");
    }

    /**
     * Set pc value from addrAluPcMux.
     */
    private void executeAddrAluPcMux() {
        this.pc.setValue(this.addrAluPcMux.getResult());
        this.addrAluPcMux.doLog("Addr Alu Pc Mux");
    }

    /**
     * Restore previous values of Memory, Registers and PC.
     * @throws Exception State list is empty.
     */
    public void undoClock() throws Exception{
        this.simulation.pop();
        State s = this.simulation.top();
        // Restoring the registers
        this.registers.overwriteAlRegisters(s.getRegisters());
        // Restoring memory
        this.dataMemory.setMemory(s.getMemory());
        // Restoring pc
        this.pc.setValue(s.getPc());
    }

    /**
     * Run a CPU clock, executing all components and storing the data for 
     * simulation purpose.
     * @throws Exception End of instruction memory.
     */
    public void doClock() throws Exception{
        this.executeFirstAuxAlu();
        this.executeInstructionMemory();
        this.executeControl();     
        this.executeRegisters();
        this.executeImmGen();
        this.executeRegistersAluMux();
        this.executeAluControl();
        this.executeSecondAuxAlu();
        this.executeAlu();
        this.executeBranchControl();
        this.executeDataMemory();
        this.executeDataMemoryRegistersMux();
        this.executeAddrAluPcMux();
        this.simulation.push(this.dataMemory.getMemory(),
            this.registers.cloneRegisters(), this.getSignals(),
            this.pc.getValue());
    }
}
