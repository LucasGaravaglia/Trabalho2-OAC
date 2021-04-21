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
     * @return Array of strings containing all the cpu signals.
     */
    private String[] getSignals() {
        String[] sig = new String[7];
        sig[0] = this.control.getALUOp();
        sig[1] = this.control.getALUSrc();
        sig[2] = this.control.getBranch();
        sig[3] = this.control.getMemRead();
        sig[4] = this.control.getMemToReg();
        sig[5] = this.control.getMemWrite();
        sig[6] = this.control.getRegWrite();
        return sig;
    }

    /**
     * Clear all the values of cpu components.
     */
    private void cleanCpu() {
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
     * Clear the CPU, insert new instructions in the instruction memory and store
     * the new state of the cpu in the simulation.
     * @param instructions
     */
    public void setInstructions(String[] instructions) {
        this.cleanCpu();
        this.instructionMemory.setInstructionMemory(instructions);
        this.simulation.clearList();
        this.simulation.push(this.dataMemory.getMemory(), 
            this.registers.cloneRegisters(), 
            this.getSignals(), 
            this.pc.getValue());
    }

    /**
     * Return the actual state of the processor.
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
    }

    /**
     * Get all commands generated by the instruction memory and send them to 
     * their respective components.
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
    }

    /**
     * Set data in Alu and Data Memory.
     */
    private void executeRegisters() {
        this.alu.setData1(this.registers.getData1());
        this.registersAluMux.setValue1(this.registers.getData2());
        this.dataMemory.setValueToWrite(this.registers.getData2());
    }

    /**
     * Set value 2 in registersAluMux.
     * Set data 2 in second aux alu.
     */
    private void executeImmGen() {
        this.registersAluMux.setValue2(this.immGen.getOutput());
        this.secondAuxAlu.setData2(this.immGen.getOutput());
    }

    /**
     * Set data 2 in alu.
     */
    private void executeRegistersAluMux() {
        this.alu.setData2(this.registersAluMux.getResult());
    }

    /**
     * Set AluControl in alu.
     * Execute Alu.
     */
    private void executeAluControl() {
        this.alu.setALUControl(this.aluControl.getControl());
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
    }

    /**
     * Set zero flag in branch control from alu.
     * Set address in data memory from alu.
     * Set value 2 in data memory registers mux from alu.
     * Set MemRead and MemWrite in data memory.
     */
    @Deprecated
    private void executeAlu() {
        this.branchControl.setZero(new Integer(this.alu.getZeroFlag()).toString());
        this.dataMemory.setAddress(this.alu.getResult());
        this.dataMemoryRegistersMux.setValue1(this.alu.getResult());
        this.dataMemory.setMemRead(this.control.getMemRead());
        this.dataMemory.setMemWrite(this.control.getMemWrite());
    }

    /**
     * Set the bit of addrAluPcMux from branch control.
     */
    private void executeBranchControl() {
        this.addrAluPcMux.setBit(this.branchControl.getOutput());
    }

    /**
     * Set value 1 in data memory registers mux from data memory.
     */
    private void executeDataMemory() {
        this.dataMemoryRegistersMux.setValue2(this.dataMemory.memoryReadResult());
    }

    /**
     * Set write value from data memory registers mux.
     */
    private void executeDataMemoryRegistersMux() {
        this.registers.setWriteValue(this.dataMemoryRegistersMux.getResult());
        this.registers.setRegWrite(this.control.getRegWrite());
    }

    /**
     * Set pc value from addrAluPcMux.
     */
    private void executeAddrAluPcMux() {
        this.pc.setValue(this.addrAluPcMux.getResult());
    }

    private void testFirstAuxAlu() {
        System.out.println("Test First Aux Alu\n");
        System.out.println("Pc: " + this.pc.getValue());
        System.out.println("________________________________________________");
        System.out.println("Alu Data 0: " + this.firstAuxAlu.getData1());
        System.out.println("Alu Data 1: " + this.firstAuxAlu.getData2());
        System.out.println("Alu Control: " + this.firstAuxAlu.getALUControl());
        System.out.println("________________________________________________");
        System.out.println("Alu Result: " + this.firstAuxAlu.getResult());
    }

    private void testInstructionMemory() {
        System.out.println("Test Instruction Memory\n");
        System.out.println("Read Address: " + 
            this.instructionMemory.getReadAddress());
        System.out.println("________________________________________________");
        System.out.println("Control Instruction (12 + 6 - 0): " + 
            this.instructionMemory.get12and6to0());
        System.out.println("First Instruction Memory Instruction (19 - 15): " + 
            this.instructionMemory.get19to15());
        System.out.println("Second Instruction Memory Instruction (24 - 20): " + 
            this.instructionMemory.get24to20());
        System.out.println("Third Instruction Memory Instruction (11 - 7): " + 
            this.instructionMemory.get11to7());
        System.out.println("ImmGen Instruction (31 - 0): " + 
            this.instructionMemory.get31to0());
        System.out.println("Alu Control Instruction (30 + 14 - 12): " + 
            this.instructionMemory.get30and14to12());
    }

    private void testControl() {
        System.out.println("Test Control\n");
        System.out.println("Current Input: "+this.control.getCurrentInput());
        System.out.println("________________________________________________");
        System.out.println("Signal Branch: "+this.control.getBranch());
        System.out.println("Signal MemRead: "+this.control.getMemRead());
        System.out.println("Signal MemToReg: "+this.control.getMemToReg());
        System.out.println("Signal AluOp: "+this.control.getALUOp());
        System.out.println("Signal MemWrite: "+this.control.getMemWrite());
        System.out.println("Signal AluSrc: "+this.control.getALUSrc());
        System.out.println("Signal RegWrite: "+this.control.getRegWrite());
    }

    private void testRegisters() {
        System.out.println("Test Registers\n");
        System.out.println("Read Register 1: "+this.registers.getCurrentAddressA());
        System.out.println("Read Register 2: "+this.registers.getCurrentAddressB());
        System.out.println("Write register: "+this.registers.getCurrentWriteAddress());
        System.out.println("Write data: : "+this.registers.getCurrentWriteValue());
        System.out.println("________________________________________________");
        System.out.println("Register Read Data 1: "+this.registers.getData1());
        System.out.println("Register Read Data 2: "+this.registers.getData2());
        System.out.println("________________________________________________");
        System.out.println("Alu Read Data 1: "+this.alu.getData1());
        System.out.println("Register Alu Mux value 0: "+this.registersAluMux.getValue1());
        System.out.println("Write Data  Data Memory: "+this.dataMemory.getCurrentWriteValue());
    }

    private void testImmGen() {
        System.out.println("Test ImmGen\n");
        System.out.println("Instruction(31-0): "+this.immGen.getCurrentInputInstruction());
        System.out.println("ImmGen to Mux: "+this.registersAluMux.getValue2());
        System.out.println("ImmGen to Second AuxAlu: "+ this.secondAuxAlu.getData2());
    }

    private void testRegistersAluMux() {
        System.out.println("Test Registers Alu Mux\n");
        System.out.println("Data 1 Register to Alu Mux: "+this.registersAluMux.getValue1());
        System.out.println("Data 2 Register to Alu Mux: "+this.registersAluMux.getValue2());
        System.out.println("Result Register to Alu Mux: "+this.registersAluMux.getResult());
        System.out.println("Flag Mux Register to Alu "+this.registersAluMux.getBit());
        System.out.println("Input Alu, Register to Alu Mux"+this.alu.getData2());
    }

    private void testAluControl() {
        System.out.println("Test Alu Control\n");
        System.out.println("Alu Op: "+this.aluControl.getCurrentAluOp());
        System.out.println("Instruction: "+this.aluControl.getCurrentInstruction());
        System.out.println("Signal Control: "+this.aluControl.getControl());
        System.out.println("________________________________________________");
        System.out.println("Signal Alu Control OutPut: "+this.alu.getALUControl());
    }

    private void testSecondAuxAlu() {
        System.out.println("Test Second Aux Alu\n");
        System.out.println("Read 1 AuxAlu: "+this.secondAuxAlu.getData1());
        System.out.println("Read 2 AuxAlu: "+this.secondAuxAlu.getData2());
        System.out.println("Second Aux Alu result: " + 
            this.secondAuxAlu.getResult());
        System.out.println("Addr Alu Pc Mux Value 1: " + 
            this.addrAluPcMux.getValue2());
    }

    private void testAlu() {
        System.out.println("Test Alu\n");
        System.out.println("Alu Read Data 1: " + this.alu.getData1());
        System.out.println("Alu Read Data 2: " + this.alu.getData2());
        System.out.println("Alu Control flag: " + this.alu.getALUControl());
        System.out.println("________________________________________________");
        System.out.println("Alu Zero Flag: " + this.alu.getZeroFlag());
        System.out.println("Alu Result: " + this.alu.getResult());
        System.out.println("________________________________________________");
        System.out.println("Data Memory Address: " + 
            this.dataMemory.getCurrentAddress());
        System.out.println("Data Memory REgister Mux value 0: " + 
            this.dataMemoryRegistersMux.getValue1());
        System.out.println("Branch Control Zero Flag: " + 
            this.branchControl.getCurrentZeroInput());
    }

    private void testBranchControl() {
        System.out.println("Test Branch Control\n");
        System.out.println("Branch Control Flag Input: " + 
            this.branchControl.getCurrentBranchInput());
        System.out.println("Branch Control Zero Input: " + 
            this.branchControl.getCurrentZeroInput());
        System.out.println("Branch Control output: " + 
            this.branchControl.getOutput());
        System.out.println("________________________________________________");
        System.out.println("Addr Alu Pc Mux flag: " + 
            this.addrAluPcMux.getBit());
    }

    private void testDataMemory() {
        System.out.println("Test Data Memory\n");
        System.out.println("Data Memory Address: " + 
            this.dataMemory.getCurrentAddress());
        System.out.println("Data Memory Write Data: " + 
            this.dataMemory.getCurrentWriteValue());
        System.out.println("Data Memory Mem Write Flag: " + 
            this.dataMemory.getCurrentMemWrite());
        System.out.println("Data Memory Mem Read Flag: " + 
            this.dataMemory.getCurrentMemRead());
        System.out.println("________________________________________________");
        System.out.println("Data Memory Read Result: " + 
            this.dataMemory.memoryReadResult());
        System.out.println("Data Memory Register Mux value 1: " + 
            this.dataMemoryRegistersMux.getValue2());
    }

    private void testDataMemoryRegistersMux() {
        System.out.println("Test Data Memory Registers Mux\n");
        System.out.println("Data Memory Registers Mux flag: " + 
            this.dataMemoryRegistersMux.getBit());
        System.out.println("Data Memory Registers Mux value 0: " + 
            this.dataMemoryRegistersMux.getValue1());
        System.out.println("Data Memory Registers Mux value 1: " + 
            this.dataMemoryRegistersMux.getValue2());
        System.out.println("________________________________________________");
        System.out.println("Registers Write Data: " + 
            this.registers.getCurrentWriteValue());
    }

    private void testAddrAluPcMux() {
        System.out.println("Test Addr Alu Pc Mux\n");
        System.out.println("Addr Alu Pc Mux value 0: " + 
            this.addrAluPcMux.getValue1());
        System.out.println("Addr Alu Pc Mux value 1: " + 
            this.addrAluPcMux.getValue2());
        System.out.println("Addr Alu Pc Mux flag: " + 
            this.addrAluPcMux.getBit());
        System.out.println("________________________________________________");
        System.out.println("Addr Alu Pc Mux result: " + 
            this.addrAluPcMux.getResult());
        System.out.println("Pc Value: " + this.pc.getValue());
    }

    private void fillRegisters() {
        String[] reg = new String[32];
        for(int i = 0; i < 32; i++) {
            reg[i] = Binary.get32BitsStringValue(0);
        }
        reg[11] = Binary.get32BitsStringValue(14);
        reg[12] = Binary.get32BitsStringValue(16);
        this.registers.overwriteAlRegisters(reg);
    }

    private void fillMemory() {
        String[] memory = new String[256];
        for(int i = 0; i < memory.length; i++){
            memory[i] = Binary.BITS_32_ZERO;
        }
        memory[16] = Binary.get32BitsStringValue(32);
        this.dataMemory.setMemory(memory);
    }

    /**
     * Restore previous values of Memory, Registers and PC and do the clock
     * again.
     * @throws Exception
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
     * Runs a CPU clock, executing all components and storing the data for si-
     * mulation purpose.
     */
    public void doClock() throws Exception{
        this.executeFirstAuxAlu();

        //this.testFirstAuxAlu();

        this.executeInstructionMemory();

        //this.testInstructionMemory();

        this.executeControl();

        //this.testControl();
        
        this.fillRegisters();
        
        this.executeRegisters();
        
        //this.testRegisters();
        
        this.executeImmGen();

        //this.testImmGen();
        
        this.executeRegistersAluMux();

        //this.testRegistersAluMux();

        this.executeAluControl();

        //this.testAluControl();

        this.executeSecondAuxAlu();

        //this.testSecondAuxAlu();

        this.executeAlu();

        //this.testAlu();

        this.executeBranchControl();

        //this.testBranchControl();

        this.executeDataMemory();

        //this.testDataMemory();

        this.executeDataMemoryRegistersMux();

        //this.testDataMemoryRegistersMux();

        this.executeAddrAluPcMux();

        //this.testAddrAluPcMux();
        this.simulation.push(this.dataMemory.getMemory(),
            this.registers.cloneRegisters(), this.getSignals(),
            this.pc.getValue());
    }
}
