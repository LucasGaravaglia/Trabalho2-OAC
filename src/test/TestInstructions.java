package src.test;

import src.hardware.memory.*;
import src.utils.Binary;
import src.hardware.control.Control;


public class TestInstructions{

    public static void testSw(){
        String test, result;
        Registers reg = new Registers();
        String[] valueReg = new String[32];
        for(int i=0;i<32;i++){
            valueReg[i] = Binary.BITS_32_ZERO;
        }
        reg.overwriteAlRegisters(valueReg);
        String[] sw = {
            "00000000010101010010000000100011", //sw  x5,0(x10) //Mem[20]=3 -> 0000000 00101 01010 010 00000 0100011
            "00000000011001010010001000100011", //sw x6,4(x10)//Mem[24]=10 -> 0000000 00110 01010 010 00100 0100011
        };
        InstructionMemory instMemory = new InstructionMemory(sw);
        Control control = new Control();

        instMemory.setReadAddress("0");
        System.out.println("Instruction 1");
        
        test = "00100011";
        result = instMemory.get12and6to0();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
        
        test = "01010";
        result = instMemory.get19to15();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
        
        test = "00101";
        result = instMemory.get24to20();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
        
        test = "00000";
        result = instMemory.get11to7();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
        
        test = "00000000010101010010000000100011";
        result = instMemory.get31to0();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
        
        test = "0010";
        result = instMemory.get30and14to12();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
    


        control.setInput(instMemory.get12and6to0());

        test = "00";
        result = control.getALUOp();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));

        test = "1";
        result = control.getALUSrc();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
    
        
        test = "00";
        result = control.getBranch();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
        
        test = "0";
        result = control.getMemRead();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
        
        test = "0";
        result = control.getMemToReg();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
        
        test = "1";
        result = control.getMemWrite();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
        
        test = "0";
        result = control.getRegWrite();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));



        instMemory.setReadAddress("1");

        System.out.println("Instruction 2");
            

        test = "00100011";
        result = instMemory.get12and6to0();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
        
        test = "01010";
        result = instMemory.get19to15();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
        
        test = "00110";
        result = instMemory.get24to20();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
        
        test = "00100";
        result = instMemory.get11to7();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
        
        test = "00000000011001010010001000100011";
        result = instMemory.get31to0();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
        
        test = "0010";
        result = instMemory.get30and14to12();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
    
    
        control.setInput(instMemory.get12and6to0());

        test = "00";
        result = control.getALUOp();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));

        test = "1";
        result = control.getALUSrc();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
    
        
        test = "00";
        result = control.getBranch();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
        
        test = "0";
        result = control.getMemRead();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
        
        test = "0";
        result = control.getMemToReg();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
        
        test = "1";
        result = control.getMemWrite();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
        
        test = "0";
        result = control.getRegWrite();
        System.out.println(test + " -> " + result + " -> " + (test.compareTo(result) == 0));
    
    }

}
