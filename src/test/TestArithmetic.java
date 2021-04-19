package src.test;

import src.hardware.arithmetic.*;

public class TestArithmetic {
    public static void testIMMGen(){
        String[] inst = {
			"00000000110001011000011100110011",//add  -> add $14, $11, $12
			"01000000101101100000011110110011",//sub  -> sub $15, $12, $11
			"00000000101101100111100010110011",//and  -> and $17, $12,$11
			"00000000110001011110100100110011",//or   -> or $18, $11, $12
			"00000000010101010010000000100011",//sw   -> mem[20]= x5
			"00000000010001010010011000000011",//lw   -> reg(12)= Mem[24]
			"00000000001100000000001010010011",//addi -> addi x5,x0,3
			"00000001001010000000011001100011",//beq  -> beq x16, $18, 12
            "11111111000010000000100011100011",//beq  -> beq x16, x16, -16"
		};
        ImmGen immGen = new ImmGen();

        immGen.setInput(inst[0]);
        System.out.println("rFormat: " + immGen.getOutput());

        immGen.setInput(inst[1]);
        System.out.println("rFormat: " + immGen.getOutput());

        immGen.setInput(inst[2]);
        System.out.println("rFormat: " + immGen.getOutput());

        immGen.setInput(inst[3]);
        System.out.println("rFormat: " + immGen.getOutput());

        immGen.setInput(inst[4]);
        System.out.println("sw: " + immGen.getOutput());

        immGen.setInput(inst[5]);
        System.out.println("lw: " + immGen.getOutput());

        immGen.setInput(inst[6]);
        System.out.println("addi: " + immGen.getOutput());

        immGen.setInput(inst[7]);
        System.out.println("beq: " + immGen.getOutput());

        immGen.setInput(inst[8]);
        System.out.println("beq: " + immGen.getOutput());
    } 
}
