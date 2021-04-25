package src.utils;

public class Decompiler {
  /**
   * Receives instructions in binary and returns to instructions in assembly.
   * 
   * @param instructions Binary of instructions
   */
  public static String[] decompiler(String[] instructions) {
    String[] instructionAssembly = new String[instructions.length];
    int n = 0;
    String opcode = new String();
    String funct3 = new String();
    String funct7 = new String();
    String rd = new String();
    String rs1 = new String();
    String rs2 = new String();
    String imm = new String();
    for (String string : instructions) {
      opcode = getOpcode(string);
      if (opcode.compareTo("0110011") == 0) {// tipo R
        funct3 = getFunct3(string);
        funct7 = getFunct7(string);
        rd = getRd(string);
        rs1 = getRs1(string);
        rs2 = getRs2(string);
        if (funct3.compareTo("000") == 0) {// sub,add
          if (funct7.compareTo("0100000") == 0) {// sub
            instructionAssembly[n] = "sub X" + getDecimal(rd) + ", X" + getDecimal(rs1) + ", X" + getDecimal(rs2);
          } else if (funct7.compareTo("0000000") == 0) {// add
            instructionAssembly[n] = "add X" + getDecimal(rd) + ", X" + getDecimal(rs1) + ", X" + getDecimal(rs2);
          }
        } else if (funct3.compareTo("111") == 0) {// and
          instructionAssembly[n] = "and X" + getDecimal(rd) + ", X" + getDecimal(rs1) + ", X" + getDecimal(rs2);
        } else if (funct3.compareTo("110") == 0) {// or
          instructionAssembly[n] = "or X" + getDecimal(rd) + ", X" + getDecimal(rs1) + ", X" + getDecimal(rs2);
        }
      } else if (opcode.compareTo("0010011") == 0) {// tipo I/addi
        imm = getImmTypeI(string);
        rd = getRd(string);
        rs1 = getRs1(string);
        instructionAssembly[n] = "addi X" + getDecimal(rd) + ", X" + getDecimal(rs1) + ", " + getDecimalWithSignal(imm);
      } else if (opcode.compareTo("0000011") == 0) {// tipo I/lw
        imm = getImmTypeI(string);
        rd = getRd(string);
        rs1 = getRs1(string);
        instructionAssembly[n] = "lw X" + getDecimal(rd) + ", " + getDecimalWithSignal(imm) + "(X" + getDecimal(rs1)
            + ")";
      } else if (opcode.compareTo("0100011") == 0) {// tipo S/sw
        imm = getImmTypeS(string);
        rs1 = getRs1(string);
        rs2 = getRs2(string);
        instructionAssembly[n] = "sw X" + getDecimal(rs2) + ", " + getDecimalWithSignal(imm) + "(X" + getDecimal(rs1)
            + ")";
      } else if (opcode.compareTo("1100011") == 0) {// tipo B
        imm = getImmTypeB(string);
        rs1 = getRs1(string);
        rs2 = getRs2(string);
        funct3 = getFunct3(string);
        if (funct3.compareTo("000") == 0) {// beq
          instructionAssembly[n] = "beq X" + getDecimal(rs1) + ", X" + getDecimal(rs2) + ", "
              + getDecimalWithSignal(imm);
        } else if (funct3.compareTo("001") == 0) {// bne
          instructionAssembly[n] = "bne X" + getDecimal(rs1) + ", X" + getDecimal(rs2) + ", "
              + getDecimalWithSignal(imm);
        }
      } else {
        System.out.println("ERRO");
      }
      n++;
    }
    return instructionAssembly;
  }

  /**
   * Convert to Binary in Decimal.
   * 
   * @param binary Binary
   * @return Decimal
   */
  public static Integer getDecimal(String binary) {
    return Binary.getInt(Binary.normalizeSize(binary));
  }

  /**
   * Convert to Binary in Decimal with signal.
   * 
   * @param binary Binary
   * @return Decimal
   */
  public static Integer getDecimalWithSignal(String binary) {
    return Binary.getInt(Binary.normalizeSizeWithSignal(binary));
  }

  /**
   * Extracts the opcode the binary
   * 
   * @param binary Binary
   * @return opcode
   */
  public static String getOpcode(String instruction) {
    return instruction.substring(25, 32);
  }

  /**
   * Extracts the funct3 the binary
   * 
   * @param binary Binary
   * @return funct3
   */
  public static String getFunct3(String instruction) {
    return instruction.substring(17, 20);
  }

  /**
   * Extracts the funct7 the binary
   * 
   * @param binary Binary
   * @return funct7
   */
  public static String getFunct7(String instruction) {
    return instruction.substring(0, 7);
  }

  /**
   * Extracts the rd the binary
   * 
   * @param binary Binary
   * @return rdExtracts the funct3 the binary
   */
  public static String getRd(String instruction) {
    return instruction.substring(20, 25);
  }

  /**
   * Extracts the rs1 the binary
   * 
   * @param binary Binary
   * @return rs1
   */
  public static String getRs1(String instruction) {
    return instruction.substring(12, 17);
  }

  /**
   * Extracts the rs2 the binary
   * 
   * @param binary Binary
   * @return rs2
   */
  public static String getRs2(String instruction) {
    return instruction.substring(7, 12);
  }

  /**
   * Extracts the imm of type i the binary
   * 
   * @param binary Binary
   * @return imm
   */
  public static String getImmTypeI(String instruction) {
    return instruction.substring(0, 12);
  }

  /**
   * Extracts the imm of type S the binary
   * 
   * @param binary Binary
   * @return imm
   */
  public static String getImmTypeS(String instruction) {
    return instruction.substring(0, 7) + instruction.substring(20, 25);
  }

  /**
   * Extracts the imm of type B the binary
   * 
   * @param binary Binary
   * @return imm
   */
  public static String getImmTypeB(String instruction) {
    return instruction.substring(0, 1) + instruction.substring(24, 25) + instruction.substring(1, 7)
        + instruction.substring(20, 24);
  }

}
