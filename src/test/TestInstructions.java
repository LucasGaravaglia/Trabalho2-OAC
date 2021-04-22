package src.test;

import src.hardware.memory.*;
import src.utils.Binary;
import src.hardware.control.Control;

public class TestInstructions {

    private static String typeI(Integer rd, Integer rs1, Integer imm, String opcode, String funct3) {
        String sRd = src.utils.Binary.get32BitsStringValue(rd).substring(27, 32),
                sRs1 = src.utils.Binary.get32BitsStringValue(rs1).substring(27, 32),
                sImm = src.utils.Binary.get32BitsStringValue(imm).substring(20, 32);
        return sImm + sRs1 + funct3 + sRd + opcode;
    }

    private static String typeR(Integer rd, Integer rs1, Integer rs2, String funct7, String opcode, String funct3) {
        String sRd = src.utils.Binary.get32BitsStringValue(rd).substring(27, 32),
                sRs1 = src.utils.Binary.get32BitsStringValue(rs1).substring(27, 32),
                sRs2 = src.utils.Binary.get32BitsStringValue(rs2).substring(27, 32);
        return funct7 + sRs2 + sRs1 + funct3 + sRd + opcode;
    }

    private static String typeS(Integer rs1, Integer rs2, Integer imm, String opcode, String funct3) {
        String sRs1 = src.utils.Binary.get32BitsStringValue(rs1).substring(27, 32),
                sImm = src.utils.Binary.get32BitsStringValue(imm).substring(20, 32),
                sRs2 = src.utils.Binary.get32BitsStringValue(rs2).substring(27, 32);
        return sImm.substring(0, 7) + sRs2 + sRs1 + funct3 + sImm.substring(7, 12) + opcode; 
    }

    private static String typeB(Integer rs1, Integer rs2, Integer imm, String opcode, String funct3) {
        String sRs1 = src.utils.Binary.get32BitsStringValue(rs1).substring(27, 32),
                sImm = src.utils.Binary.get32BitsStringValue(imm).substring(20, 32),
                sRs2 = src.utils.Binary.get32BitsStringValue(rs2).substring(27, 32);
        return sImm.substring(0, 1) + sImm.substring(2, 8) + sRs2 + sRs1 + funct3 + sImm.substring(8, 12)
                + sImm.substring(1, 2) + opcode;
    }

    // TYPE R
    public static String add(Integer rd, Integer rs1, Integer rs2) {
        String opcode = "0110011", funct3 = "000", funct7 = "0000000";
        return typeR(rd, rs1, rs2, funct7, opcode, funct3);
    }

    public static String sub(Integer rd, Integer rs1, Integer rs2) {
        String opcode = "0110011", funct3 = "000", funct7 = "0100000";
        return typeR(rd, rs1, rs2, funct7, opcode, funct3);
    }

    public static String and(Integer rd, Integer rs1, Integer rs2) {
        String opcode = "0110011", funct3 = "111", funct7 = "0000000";
        return typeR(rd, rs1, rs2, funct7, opcode, funct3);
    }

    public static String or(Integer rd, Integer rs1, Integer rs2) {
        String opcode = "0110011", funct3 = "110", funct7 = "0000000";
        return typeR(rd, rs1, rs2, funct7, opcode, funct3);
    }

    // TYPE I
    public static String addi(Integer rd, Integer rs1, Integer imm) {
        String opcode = "0010011", funct3 = "000";
        return typeI(rd, rs1, imm, opcode, funct3);
    }

    public static String lw(Integer rd, Integer rs1, Integer imm) {
        String opcode = "0000011", funct3 = "010";
        return typeI(rd, rs1, imm, opcode, funct3);
    }

    // TYPE S
    public static String sw(Integer rs1, Integer rs2, Integer imm) {
        String opcode = "0100011", funct3 = "010";
        return typeS(rs1, rs2, imm, opcode, funct3);
    }

    // TYPE B
    public static String beq(Integer rs1, Integer rs2, Integer imm) {
        String opcode = "1100011", funct3 = "000";
        return typeB(rs1, rs2, imm, opcode, funct3);
    }

    public static String bne(Integer rs1, Integer rs2, Integer imm) {
        String opcode = "1100011", funct3 = "001";
        return typeB(rs1, rs2, imm, opcode, funct3);
    }

    public static String translateToBinary(String asmInst) {
        String operation;
        Integer rd,rs1,rs2,imm;
        String[] values;
        String[] asm = asmInst.split(" ");
        operation = asm[0];
        values = asm[1].split(",");
        switch(operation) {
            case "add":
                rd = Integer.parseInt(values[0].split("x")[1]);
                rs1 = Integer.parseInt(values[1].split("x")[1]);
                rs2 = Integer.parseInt(values[2].split("x")[1]);
                return add(rd,rs1,rs2);
            case "sub":
                rd = Integer.parseInt(values[0].split("x")[1]);
                rs1 = Integer.parseInt(values[1].split("x")[1]);
                rs2 = Integer.parseInt(values[2].split("x")[1]);
                return sub(rd,rs1,rs2);
            case "and":
                rd = Integer.parseInt(values[0].split("x")[1]);
                rs1 = Integer.parseInt(values[1].split("x")[1]);
                rs2 = Integer.parseInt(values[2].split("x")[1]);
                return and(rd,rs1,rs2);
            case "or":
                rd = Integer.parseInt(values[0].split("x")[1]);
                rs1 = Integer.parseInt(values[1].split("x")[1]);
                rs2 = Integer.parseInt(values[2].split("x")[1]);
                return or(rd,rs1,rs2);
            case "addi":
                rd = Integer.parseInt(values[0].split("x")[1]);
                rs1 = Integer.parseInt(values[1].split("x")[1]);
                imm = Integer.parseInt(values[2]);
                return addi(rd, rs1, imm);
            case "lw":
                rd = Integer.parseInt(values[0].split("x")[1]);
                rs1 = Integer.parseInt(values[1].split("\\(")[1].split("\\)")[0].split("x")[1]);
                imm = Integer.parseInt(values[1].split("\\(")[0]);
                return lw(rd, rs1, imm);
            case "sw":
                rs2 = Integer.parseInt(values[0].split("x")[1]);
                rs1 = Integer.parseInt(values[1].split("\\(")[1].split("\\)")[0].split("x")[1]);
                imm = Integer.parseInt(values[1].split("\\(")[0]);
                return sw(rs1, rs2, imm);
            case "beq":
                rs1 = Integer.parseInt(values[0].split("x")[1]);
                rs2 = Integer.parseInt(values[1].split("x")[1]);
                imm = Integer.parseInt(values[2]);
                return beq(rs1, rs2, imm);
            case "bne":
                rs1 = Integer.parseInt(values[0].split("x")[1]);
                rs2 = Integer.parseInt(values[1].split("x")[1]);
                imm = Integer.parseInt(values[2]);
                return bne(rs1, rs2, imm);
            default:
                return "";
        }
    }

}
