package src.test;

import src.hardware.memory.*;

public class TestMemory {
    public static void testDatamemory(){
        DataMemory m = new DataMemory();
        String[] samples = {"00000000000000000000000000000000", "00000000000000000000000000000000"};
        System.out.println(m.toString());
        System.out.println(samples);
    }

    public static void testRegisters(){
        Registers r = new Registers();
        System.out.println(r.toString());
    }
}
