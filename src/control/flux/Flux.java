package src.control.flux;

import src.hardware.arithmetic.*;
import src.hardware.control.*;
import src.hardware.memory.*;
import src.control.simulation.Simulation;
import src.utils.Binary;
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
     * 
     * @param instructions Instructions to be loaded to the instruction memory.
     */
    public void setInstructions(String[] instructions) {
        this.instructionMemory = new InstructionMemory(instructions);
    }

    /**
     * 
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
     * Steps of execution for first auxiliary alu.
     * Set data 1, data 2 and ALUControl.
     * Set value 1 to addrAluPcMux.
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
    private void executeInstructionMemory() {
        String controlInstruction;
        String registersInstruction1;
        String registersInstruction2;
        String registersWriteDataInstruction;
        String immGenInstruction;
        String aluControlInstruction;

        controlInstruction = this.instructionMemory.get12and6to0();
        registersInstruction1 = this.instructionMemory.get19to15();
        registersInstruction2 = this.instructionMemory.get24to20();
        registersWriteDataInstruction = this.instructionMemory.get11to7();
        immGenInstruction = this.instructionMemory.get31to0();
        aluControlInstruction = this.instructionMemory.get30and14to12();

        // Instruction memory
        this.instructionMemory.setReadAddress(this.pc.getValue());

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
     * Send all cpu flag signals to their respective components.
     */
    private void executeControl() {
        this.branchControl.setBranch(this.control.getBranch());
        this.dataMemory.setMemRead(this.control.getMemRead());
        this.dataMemoryRegistersMux.setBit(this.control.getMemToReg());
        this.aluControl.setALUOp(this.control.getALUOp());
        this.dataMemory.setMemWrite(this.control.getMemWrite());
        this.registersAluMux.setBit(this.control.getALUSrc());
        this.registers.setRegWrite(this.control.getRegWrite());
    }

    /**
     * Set data 1 in alu. 
     * set value 1 in registersAluMux. 
     * set valueToWrite in data memory.
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
     * Set adress in data memory from alu.
     * Set value 2 in data memory registers mux from alu.
     */
    @Deprecated
    private void executeAlu() {
        this.branchControl.setZero(new Integer(this.alu.getZeroFlag()).toString());
        this.dataMemory.setAddress(this.alu.getResult());
        this.dataMemoryRegistersMux.setValue1(this.alu.getResult());
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
    }

    /**
     * Set pc value from addrAluPcMux.
     */
    private void executeAddrAluPcMux() {
        this.pc.setValue(this.addrAluPcMux.getResult());
    }

    private void test1() {
        System.out.println(this.firstAuxAlu.getData1());
        System.out.println(this.firstAuxAlu.getData2());
        System.out.println(this.firstAuxAlu.getALUControl());
        System.out.println("\n");
        System.out.println(this.firstAuxAlu.getResult());
    }

    private void test2() {
        System.out.println(this.instructionMemory.getReadAddress());
        System.out.println("\n");
        System.out.println(this.instructionMemory.get12and6to0());
        System.out.println(this.instructionMemory.get19to15());
        System.out.println(this.instructionMemory.get24to20());
        System.out.println(this.instructionMemory.get11to7());
        System.out.println(this.instructionMemory.get31to0());
        System.out.println(this.instructionMemory.get30and14to12());
    }

    private void test3() {
        System.out.println(this.control.getCurrentInput());
        System.out.println("\n");
        System.out.println(this.control.getBranch());
        System.out.println(this.control.getMemRead());
        System.out.println(this.control.getMemToReg());
        System.out.println(this.control.getALUOp());
        System.out.println(this.control.getMemWrite());
        System.out.println(this.control.getALUSrc());
        System.out.println(this.control.getRegWrite());
    }

    private void test4() {
        System.out.println(this.registers.getCurrentAddressA());
        System.out.println(this.registers.getCurrentAddressB());
        System.out.println(this.registers.getCurrentWriteAddress());
        System.out.println(this.registers.getCurrentWriteValue());
        System.out.println("\n");
        System.out.println(this.registers.getData1());
        System.out.println(this.registers.getData2());
        System.out.println("\n");
        System.out.println(this.alu.getData1());
        System.out.println(this.registersAluMux.getValue1());
        System.out.println(this.dataMemory.getCurrentWriteValue());
    }

    private void test5() {
        System.out.println(this.immGen.getCurrentInputInstruction());
        System.out.println(this.registersAluMux.getValue2());
        System.out.println(this.secondAuxAlu.getData2());
    }

    private void test6() {
        System.out.println(this.registersAluMux.getValue1());
        System.out.println(this.registersAluMux.getValue2());
        System.out.println(this.registersAluMux.getResult());
        System.out.println(this.registersAluMux.getBit());
        System.out.println(this.alu.getData2());
    }

    private void test7() {
        System.out.println(this.aluControl.getCurrentAluOp());
        System.out.println(this.aluControl.getCurrentInstruction());
        System.out.println(this.aluControl.getControl());
        System.out.println("\n");
        System.out.println(this.alu.getALUControl());
    }

    private void test8() {
        System.out.println(this.secondAuxAlu.getData1());
        System.out.println(this.secondAuxAlu.getData2());
        System.out.println(this.secondAuxAlu.getResult());
        System.out.println(this.addrAluPcMux.getValue2());
    }

    private void test9() {
        System.out.println(this.alu.getData1());
        System.out.println(this.alu.getData2());
        System.out.println(this.alu.getALUControl());
        System.out.println("\n");
        System.out.println(this.alu.getZeroFlag());
        System.out.println(this.alu.getResult());
        System.out.println("\n");
        System.out.println(this.dataMemory.getCurrentAddress());
        System.out.println(this.dataMemoryRegistersMux.getValue2());
    }

    private void test10() {
        System.out.println(this.branchControl.getCurrentBranchInput());
        System.out.println(this.branchControl.getCurrentZeroInput());
        System.out.println(this.branchControl.getOutput());
        System.out.println("\n");
        System.out.println(this.addrAluPcMux.getBit());
    }

    private void test11() {
        System.out.println(this.dataMemory.getCurrentAddress());
        System.out.println(this.dataMemory.getCurrentWriteValue());
        System.out.println(this.dataMemory.getCurrentMemWrite());
        System.out.println(this.dataMemory.getCurrentMemRead());
        System.out.println("\n");
        System.out.println(this.dataMemory.memoryReadResult());
        System.out.println(this.dataMemoryRegistersMux.getValue1());
    }

    private void test12() {
        System.out.println(this.dataMemoryRegistersMux.getBit());
        System.out.println(this.dataMemoryRegistersMux.getValue1());
        System.out.println(this.dataMemoryRegistersMux.getValue2());
        System.out.println("\n");
        System.out.println(this.registers.getCurrentWriteValue());
    }

    private void test13() {
        System.out.println(this.addrAluPcMux.getValue1());
        System.out.println(this.addrAluPcMux.getValue2());
        System.out.println(this.addrAluPcMux.getBit());
        System.out.println("\n");
        System.out.println(this.addrAluPcMux.getResult());
        System.out.println(this.pc.getValue());
    }

    /**
     * Runs a CPU clock, executing all components and storing the data for si-
     * mulation purpose.
     */
    public void doClock() {
        this.simulation.pushState(this.dataMemory.getMemory(),
            this.registers.cloneRegisters(), this.getSignals(),
            this.pc.getValue());

        this.executeFirstAuxAlu();

        //this.test1();

        this.executeInstructionMemory();

        //this.test2();

        this.executeControl();

        //this.test3();

        String[] a = new String[32];
        a[11] = Binary.get32BitsStringValue(5);
        a[12] = Binary.get32BitsStringValue(6);
        this.registers.overwriteAlRegisters(a);

        this.executeRegisters();
        
        //this.test4();
        
        this.executeImmGen();

        //this.test5();
        
        this.executeRegistersAluMux();

        //this.test6();

        this.executeAluControl();

        //this.test7();

        this.executeSecondAuxAlu();

        //this.test8();

        this.executeAlu();

        //this.test9();

        this.executeBranchControl();

        //this.test10();

        this.executeDataMemory();

        //this.test11();

        this.executeDataMemoryRegistersMux();

        //this.test12();

        this.executeAddrAluPcMux();

        //this.test13();
    }
}
