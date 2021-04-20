package src;

import src.control.flux.Flux;

public class Main {
	public static void show_bits(String bits) {
		for(int i = 0; i < bits.length(); i++) {
			if(i != bits.length()-1 && i < 22){
				System.out.print(" "+bits.charAt(i));
				System.out.print(" ");
			}
			else if(i != bits.length()){
				System.out.print(bits.charAt(i));
				System.out.print(" ");
			}
			else {
				System.out.print(bits.charAt(i));
			}
		}
		System.out.println("\n31 30 29 28 27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1 0");
	}

	public static void show_r(String bits) {
		String funct7,rs2,rs1,funct3,rd,opcode;
		funct7 = String.valueOf(bits.charAt(0)) +
				 String.valueOf(bits.charAt(1)) +
				 String.valueOf(bits.charAt(2)) +
				 String.valueOf(bits.charAt(3)) +
				 String.valueOf(bits.charAt(4)) +
				 String.valueOf(bits.charAt(5)) +
				 String.valueOf(bits.charAt(6));
		rs2 = String.valueOf(bits.charAt(7)) +
			  String.valueOf(bits.charAt(8)) +
			  String.valueOf(bits.charAt(9)) +
			  String.valueOf(bits.charAt(10)) +
			  String.valueOf(bits.charAt(11));

		rs1 = String.valueOf(bits.charAt(12)) +
			  String.valueOf(bits.charAt(13)) +
			  String.valueOf(bits.charAt(14)) +
			  String.valueOf(bits.charAt(15)) +
			  String.valueOf(bits.charAt(16));

		funct3 = String.valueOf(bits.charAt(17)) +
				 String.valueOf(bits.charAt(18)) +
				 String.valueOf(bits.charAt(19));

		rd = String.valueOf(bits.charAt(20)) +
			 String.valueOf(bits.charAt(21)) +
			 String.valueOf(bits.charAt(22)) +
			 String.valueOf(bits.charAt(23)) +
			 String.valueOf(bits.charAt(24));

		opcode = String.valueOf(bits.charAt(25)) +
				 String.valueOf(bits.charAt(26)) +
				 String.valueOf(bits.charAt(27)) +
				 String.valueOf(bits.charAt(28)) +
				 String.valueOf(bits.charAt(29)) +
				 String.valueOf(bits.charAt(30)) +
				 String.valueOf(bits.charAt(31));
		System.out.print("Funct7: " + funct7 + "\n");
		System.out.print("rs2: " + rs2 + "\n");
		System.out.print("rs1: " + rs1 + "\n");
		System.out.print("funct3: " + funct3 + "\n");
		System.out.print("rd: " + rd + "\n");
		System.out.print("opcode: " + opcode + "\n----------------------\n\n");
	}

	public static void main(String[] args) {
		Flux f = new Flux();
		String[] s = {
			"00000000110001011000011100110011",//add
			"01000000101101100000011110110011",//sub
			"00000000101101100111100010110011",//and
			"00000000110001011110100100110011",//or
			"00000000010101010010000000100011",//sw
			"00000000000001010010010110000011",//lw
			"00000000000100000000100000010011",//addi
			"00000001001010000000011001100011",//beq
		};
		f.setInstructions(s);

		show_bits("00000000110001011000011100110011");
		show_r("00000000110001011000011100110011");
		//System.out.println("funct7: 0000000\nrs2: 01100\nrs1: 01011\nfunct3: 000\nrd: 01110\nopcode: 0110011\n");
		f.doClock();
		System.out.println("\n\n");

		//System.out.println("0100000 01011 01100 000 01111 0110011");
		//f.doClock();
		//System.out.println("\n\n");
//
		//System.out.println("0000000 01011 01100 111 10001 0110011");
		//f.doClock();
		//System.out.println("\n\n");
//
		//System.out.println("0000000 01100 01011 110 10010 0110011");
		//f.doClock();
		//System.out.println("\n\n");
//
		//System.out.println("0000000 00101 01010 010 00000 0100011");
		//f.doClock();
		//System.out.println("\n\n");
//
		//System.out.println("0000000 00000 01010 010 01011 0000011");
		//f.doClock();
		//System.out.println("\n\n");
//
		//System.out.println("0000000 00001 00000 000 10000 0010011");
		//f.doClock();
		//System.out.println("\n\n");
		//
		//System.out.println("0000000 10010 10000 000 01100 1100011");
		//f.doClock();
		//System.out.println("\n\n");
	}
}