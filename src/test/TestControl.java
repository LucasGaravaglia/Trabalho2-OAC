package src.test;
import src.hardware.memory.*;
import src.hardware.control.*;

public class TestControl{
    public static void testAluControl(){
        String[] inst = {
			"00000000110001011000011100110011",//add
			"01000000101101100000011110110011",//sub
			"00000000101101100111100010110011",//and
			"00000000110001011110100100110011",//or
			"00000000010101010010000000100011",//sw
			"00000000000001010010010110000011",//lw
			"00000000000100000000100000010011",//addi
			"00000001001010000000011001100011",//beq
		};

		InstructionMemory instMemo = new InstructionMemory(inst);
		AluControl aluControl = new AluControl();
		Boolean result;
		String value = "";
		String[] address = {"000", "001", "010", "011", "100", "101", "110", "111"};
		String[] instructionResults = {"0010", "0110", "0000", "0001", "0010", "0010", "0110"};

        System.out.println("AluOp = 00 -> only add(0010):");
        aluControl.setALUOp("00");

		for(int i = 0;i < 7;i++){
			instMemo.setReadAddress(address[i]);
			aluControl.setInstruction(instMemo.get30and14to12());
			value = aluControl.getControl();
			result = value.compareTo("0010") == 0;
			System.out.println("Instruction" + address[i] +": " + value + " -> " + result);
		}

		System.out.println("AluOp = 01 -> only sub(0110):");
        aluControl.setALUOp("01");

		for(int i = 0;i < 7;i++){
			instMemo.setReadAddress(address[i]);
			aluControl.setInstruction(instMemo.get30and14to12());
			value = aluControl.getControl();
			result = value.compareTo("0110") == 0;
			System.out.println("Instruction" + address[i] +": " + value + " -> " + result);
		}

		System.out.println("AluOp = 10 -> defined by funct3 -> sum(0010), sub(0110), and(0000), or(0001) ");
        aluControl.setALUOp("10");

		for(int i = 0;i < 4;i++){
			instMemo.setReadAddress(address[i]);
			aluControl.setInstruction(instMemo.get30and14to12());
			value = aluControl.getControl();
			result = value.compareTo(instructionResults[i]) == 0;
			System.out.println("Instruction" + address[i] +": " + value + " -> " + result);
		}

		System.out.println("AluOp = 01 -> Beq -> sub(0110)");
        aluControl.setALUOp("01");

		
		instMemo.setReadAddress("111");
		aluControl.setInstruction(instMemo.get30and14to12());
		value = aluControl.getControl();
		result = value.compareTo(instructionResults[6]) == 0;
		System.out.println("Instruction" + address[6] +": " + value + " -> " + result);
		

		

    }
}
