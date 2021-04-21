package src.test;

import src.hardware.memory.*;

public class TestMemory {
    public static void testDataMemory(){
        DataMemory m = new DataMemory();
        String[] samples = {"00000000000000000000000000000000", "00000000000000000000000000000000"};
        System.out.println(m.toString());
        System.out.println(samples);
    }

    public static void testRegisters(){
        Registers r = new Registers();
        System.out.println(r.toString());
    }

    public static void testWriteMemoryData(){
        DataMemory m = new DataMemory();

        m.setAddress("100000");
        System.out.println(m.getCurrentAddress());

        m.setValueToWrite("00000000000001111111111110000000");
        m.setMemWrite("1");
        System.out.println(m.toString());
    }
}
