package src.utils;

import src.utils.Binary;
import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Instructions {

    /**
     * Return a type I binary instruction.
     * @param rd Destine register.
     * @param rs1 Source register 1.
     * @param imm Immediate.
     * @param opcode Instruction opcode - 7 bits.
     * @param funct3 Instruction funct3 - 3 bits.
     * @return Binary instruction 32 bits.
     */
    private static String typeI(Integer rd, Integer rs1, Integer imm, String opcode, String funct3) {
        String sRd = src.utils.Binary.get32BitsStringValue(rd).substring(27, 32),
                sRs1 = src.utils.Binary.get32BitsStringValue(rs1).substring(27, 32),
                sImm = src.utils.Binary.get32BitsStringValue(imm).substring(20, 32);
        return sImm + sRs1 + funct3 + sRd + opcode;
    }

    /**
     * Return a type R binary instruction.
     * @param rd Destine register.
     * @param rs1 Source register 1.
     * @param rs2 Source register 2.
     * @param funct7 Instruction funct7 - 7 bits.
     * @param opcode Instruction opcode - 7 bits.
     * @param funct3 Instruction funct3 - 3 bits.
     * @return Binary instruction 32 bits.
     */
    private static String typeR(Integer rd, Integer rs1, Integer rs2, String funct7, String opcode, String funct3) {
        String sRd = src.utils.Binary.get32BitsStringValue(rd).substring(27, 32),
                sRs1 = src.utils.Binary.get32BitsStringValue(rs1).substring(27, 32),
                sRs2 = src.utils.Binary.get32BitsStringValue(rs2).substring(27, 32);
        return funct7 + sRs2 + sRs1 + funct3 + sRd + opcode;
    }

    /**
     * Return a type S binary instruction.
     * @param rs1 Source register 1.
     * @param rs2 Source register 2.
     * @param imm Immediate.
     * @param opcode Instruction opcode - 7 bits.
     * @param funct3 Instruction funct3 - 3 bits.
     * @return
     */
    private static String typeS(Integer rs1, Integer rs2, Integer imm, String opcode, String funct3) {
        String sRs1 = src.utils.Binary.get32BitsStringValue(rs1).substring(27, 32),
                sImm = src.utils.Binary.get32BitsStringValue(imm).substring(20, 32),
                sRs2 = src.utils.Binary.get32BitsStringValue(rs2).substring(27, 32);
        return sImm.substring(0, 7) + sRs2 + sRs1 + funct3 + sImm.substring(7, 12) + opcode; 
    }

    /**
     * Return a type B binary instruction.
     * @param rs1 Source register 1.
     * @param rs2 Source register 2.
     * @param imm Immediate.
     * @param opcode Instruction opcode - 7 bits.
     * @param funct3 Instruction funct3 - 3 bits.
     * @return
     */
    private static String typeB(Integer rs1, Integer rs2, Integer imm, String opcode, String funct3) {
        String sRs1 = src.utils.Binary.get32BitsStringValue(rs1).substring(27, 32),
                sImm = src.utils.Binary.get32BitsStringValue(imm).substring(20, 32),
                sRs2 = src.utils.Binary.get32BitsStringValue(rs2).substring(27, 32);
        return sImm.substring(0, 1) + sImm.substring(2, 8) + sRs2 + sRs1 + funct3 + sImm.substring(8, 12)
                + sImm.substring(1, 2) + opcode;
    }

    /**
     * Add assembly instruction.
     * @param rd Destine register.
     * @param rs1 Source register 1.
     * @param rs2 Source register 2.
     * @return Binary instruction for add 32 bits.
     */
    public static String add(Integer rd, Integer rs1, Integer rs2) {
        String opcode = "0110011", funct3 = "000", funct7 = "0000000";
        return typeR(rd, rs1, rs2, funct7, opcode, funct3);
    }

    /**
     * Sub assembly instruction.
     * @param rd Destine register.
     * @param rs1 Source register 1.
     * @param rs2 Source register 2.
     * @return Binary instruction for sub 32 bits.
     */
    public static String sub(Integer rd, Integer rs1, Integer rs2) {
        String opcode = "0110011", funct3 = "000", funct7 = "0100000";
        return typeR(rd, rs1, rs2, funct7, opcode, funct3);
    }

    /**
     * And assembly instruction.
     * @param rd Destine register.
     * @param rs1 Source register 1.
     * @param rs2 Source register 2.
     * @return Binary instruction for and 32 bits.
     */
    public static String and(Integer rd, Integer rs1, Integer rs2) {
        String opcode = "0110011", funct3 = "111", funct7 = "0000000";
        return typeR(rd, rs1, rs2, funct7, opcode, funct3);
    }

    /**
     * Or assembly instruction.
     * @param rd Destine register.
     * @param rs1 Source register 1.
     * @param rs2 Source register 2.
     * @return Binary instruction for or 32 bits.
     */
    public static String or(Integer rd, Integer rs1, Integer rs2) {
        String opcode = "0110011", funct3 = "110", funct7 = "0000000";
        return typeR(rd, rs1, rs2, funct7, opcode, funct3);
    }

    /**
     * Addi assembly instruction.
     * @param rd Destine register.
     * @param rs1 Source register 1.
     * @param imm Immediate.
     * @return Binary instruction for addi 32 bits.
     */
    public static String addi(Integer rd, Integer rs1, Integer imm) {
        String opcode = "0010011", funct3 = "000";
        return typeI(rd, rs1, imm, opcode, funct3);
    }

    /**
     * Lw assembly instruction.
     * @param rd Destine register.
     * @param rs1 Source register 1.
     * @param imm Immediate.
     * @return Binary instruction for lw 32 bits.
     */
    public static String lw(Integer rd, Integer rs1, Integer imm) {
        String opcode = "0000011", funct3 = "010";
        return typeI(rd, rs1, imm, opcode, funct3);
    }

    /**
     * Sw assembly instruction.
     * @param rs1 Source register 1.
     * @param rs2 Source register 2.
     * @param imm Immediate.
     * @return Binary instruction for sw 32 bits.
     */
    public static String sw(Integer rs1, Integer rs2, Integer imm) {
        String opcode = "0100011", funct3 = "010";
        return typeS(rs1, rs2, imm, opcode, funct3);
    }

    /**
     * Beq assembly instruction.
     * @param rs1 Source register 1.
     * @param rs2 Source register 2.
     * @param imm Immediate.
     * @return Binary instruction for beq 32 bits.
     */
    public static String beq(Integer rs1, Integer rs2, Integer imm) {
        String opcode = "1100011", funct3 = "000";
        return typeB(rs1, rs2, imm, opcode, funct3);
    }

    /**
     * Bne assembly instruction.
     * @param rs1 Source register 1.
     * @param rs2 Source register 2.
     * @param imm Immediate.
     * @return Binary instruction for bne 32 bits.
     */
    public static String bne(Integer rs1, Integer rs2, Integer imm) {
        String opcode = "1100011", funct3 = "001";
        return typeB(rs1, rs2, imm, opcode, funct3);
    }

    /**
     * Translate a assembly instruction to a binary 32 bits instruction.
     * @param asmInst Instruction in assembly format.
     * @return Binary instruction 32 bits.
     * @throws Exception Not supported operation.
     */
    public static String translateToBinary(String asmInst) throws Exception{
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
                throw new Exception("Not supported operation");
        }
    }

    /**
     * Load a assembly file.
     * @param path File path.
     * @return Array with file lines.
     */
    private static String[] loadFile(String path) {
        ArrayList<String> content = new ArrayList<String>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              content.add(data);
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        String[] arrayContent = new String[content.size()];
        for(int i = 0; i < content.size();i++) {
            arrayContent[i] = content.get(i);
        } 
        return arrayContent;
      }

    /**
     * Generate a array with binary instructions from a assembly file.
     * @param filePath Assembly file path.
     * @return Array with binary instructions.
     * @throws Exception Not supported operation.
     */
    public static String[] generateBinaryFromAsmFile(String filePath) throws Exception{
        String[] fileContent;
        fileContent = loadFile(filePath);
        String[] inst = new String[fileContent.length];
        for(int i = 0; i < fileContent.length; i++) {
           inst[i] = translateToBinary(fileContent[i]);
        }
        return inst;
    }
}
