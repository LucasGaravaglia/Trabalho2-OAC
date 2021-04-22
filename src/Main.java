package src;

import src.test.*;
import src.control.flux.*;
import src.gui.window.*;

public class Main {
    public static void main(String[] args) {
        /**
         * addi x10,x0,20 addi x11,x0,10 beq x0,x11,24 addi x11,x11,-1 or x13,x11,x10 sw
         * x13,0(x10) addi x10,x10,4 beq x0,x0,-20
         */
        String[] instruction = { TestInstructions.addi(10, 0, 20),
        TestInstructions.addi(11, 0, 10),
        TestInstructions.beq(0, 11, 24), TestInstructions.addi(11, 11, -1),
        TestInstructions.or(13, 11, 10),
        TestInstructions.sw(10, 13, 0), TestInstructions.addi(10, 10, 4),
        TestInstructions.beq(0, 0, -6) };
        for (String string : instruction) {
        System.out.println(string);
        }
        new Window().setVisible(true);

/**
         * addi x10,x0,20
         * addi x11,x0,10
         * beq x0,x11,24
         * addi x11,x11,-1
         * or x13,x11,x10
         * sw x13,0(x10)
         * addi x10,x10,4
         * beq x0,x0,-20
         */    }
}
