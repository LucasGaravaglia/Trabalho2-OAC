package src;
import src.hardware.*;
import src.utils.*;

public class Main {
	public static void main(String[] args) {
		String[] instructions = {"Uma", "duas", "tress"};
		InstructionMemory haha = new InstructionMemory(instructions);
		System.out.println(haha.toString());
		System.out.print(Binary.BITS_32_ZERO);
	}	
}