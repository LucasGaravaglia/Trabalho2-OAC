package src.hardware.memory;
import src.utils.*;

/**
 * @author Levi
 */
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
   
    /**
     * 
     * @param adress_5_bits adress of register to be readed
     */
    public void setAdressOfReadRegisterA(String adress_5_bits){
        if(adress_5_bits.length() == 5){
            this.adressOfReadRegisterA = adress_5_bits;
        }
    }    

    /**
     * 
     * @param adress_5_bits adress of register to be readed
     */
    public void setAdressOfReadRegisterB(String adress_5_bits){
        if(adress_5_bits.length() == 5){
            this.adressOfReadRegisterB = adress_5_bits;
        }
    }

    /**
     * 
     * @param adress_5_bits adress of register to be writed
     */
    public void setAdressOfWriteRegister(String adress_5_bits){
        if(adress_5_bits.length() == 5){
            this.adressOfWriteRegister = adress_5_bits;
        }
    }

    /**
     * 
     * @param value_32_bits value to be storaged in the write register
     */
    public void setWriteValue(String value_32_bits){
        this.writeValue = value_32_bits;
    }

    /**
     * 
     * @return value to be writed in the register
     */
    public String getWriteValue(){
        return this.writeValue;
    }

    /**
     * Function responsible for write the value on the write register
     */
    public void writeRegister(){
        this.registers[Integer.parseInt(this.adressOfWriteRegister, 2)] = this.writeValue;
    }

    /**
     * Function tha read an value from register given this adress
     * @param adress_5_bits adress of register to be read
     * @return value of the register
     */
    public String readRegister(String adress_5_bits){
        return this.registers[Integer.parseInt(adress_5_bits, 2)];
    }

    /**
     * Function that return the first output from registers
     * @return a 32 bits value (String)
     */
    public String getData1(){
        return this.readRegister(this.adressOfReadRegisterA);
    }

     /**
     * Function that return the second output from registers
     * @return a 32 bits value (String)
     */
    public String getData2(){
        return this.readRegister(this.adressOfReadRegisterB);
    }
}
