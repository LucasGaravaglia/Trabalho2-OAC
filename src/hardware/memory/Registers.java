package src.hardware.memory;

public class Registers {
    private String adressOfReadRegisterA;
    private String adressOfReadRegisterB;
    private String adressOfWriteRegister;
    private String writeValue;
    private String[] registers;

    Registers(){
        this.registers = new String[32];
        for(String reg : this.registers){
            reg = Binary.BITS_32_ZERO;
        }
        this.adressOfReadRegisterA = Binary.BITS_5_ZERO;
        this.adressOfReadRegisterB = Binary.BITS_5_ZERO;
        this.adressOfWriteRegister = Binary.BITS_5_ZERO;
        this.writeValue = Binary.BITS_32_ZERO;
    }

    public String getAdressOfReadRegisterA (){
        return this.adressOfReadRegisterA;
    }         

    public String getAdressOfReadRegisterB (){
        return this.adressOfReadRegisterB;
    }     

    public String getAdressOfWriteRegister (){
        return this.adressOfWriteRegister;
    } 
   
    public void setAdressOfReadRegisterA(String value_5_bits){
        if(value_5_bits.length() == 5){
            this.adressOfReadRegisterA = value_5_bits;
        }
    }    

    public void setAdressOfReadRegisterB(String value_5_bits){
        if(value_5_bits.length() == 5){
            this.adressOfReadRegisterB = value_5_bits;
        }
    }

    public void setAdressOfWriteRegister(String value_5_bits){
        if(value_5_bits.length() == 5){
            this.adressOfWriteRegister = value_5_bits;
        }
    }

    public void setWriteValue(String value_32_bits){
        this.writeValue = value_32_bits;
    }

    public String getWriteValue(){
        return this.writeValue;
    }

    public void writeRegister(){
        this.registers[Integer.parseInt(this.adressOfWriteRegister, 2)] = this.writeValue;
    }

    public String readRegister(String adress_5_bits){
        return this.registers[Integer.parseInt(adress_5_bits, 2)];
    }

    public String getData1(){
        return this.readRegister(this.adressOfReadRegisterA);
    }

    public String getData2(){
        return this.readRegister(this.adressOfReadRegisterB);
    }
}
