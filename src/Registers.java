package src;

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
   
    public void setAdressOfReadRegisterA(String value){
        if(value.length() == 5){
            this.adressOfReadRegisterA = value;
        }
    }    

    public void setAdressOfReadRegisterB(String value){
        if(value.length() == 5){
            this.adressOfReadRegisterB = value;
        }
    }

    public void setAdressOfWriteRegister(String value){
        if(value.length() == 5){
            this.adressOfWriteRegister = value;
        }
    }

    public void setWriteValue(String value){
        this.writeValue = value;
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
