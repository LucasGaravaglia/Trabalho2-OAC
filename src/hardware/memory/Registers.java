package src.hardware.memory;
import src.utils.*;

/**
 * @author Levi
 */
public class Registers {
    private String addressOfReadRegisterA;
    private String addressOfReadRegisterB;
    private String addressOfWriteRegister;
    private String writeValue;
    private String[] registers;

    
    Registers(){
        this.registers = new String[32];
        for(String reg : this.registers){
            reg = Binary.BITS_32_ZERO;
        }
        this.addressOfReadRegisterA = Binary.BITS_5_ZERO;
        this.addressOfReadRegisterB = Binary.BITS_5_ZERO;
        this.addressOfWriteRegister = Binary.BITS_5_ZERO;
        this.writeValue = Binary.BITS_32_ZERO;
    }

    public String getAddressOfReadRegisterA (){
        return this.addressOfReadRegisterA;
    }         

    public String getAddressOfReadRegisterB (){
        return this.addressOfReadRegisterB;
    }     

    public String getAddressOfWriteRegister (){
        return this.addressOfWriteRegister;
    } 
   
    /**
     * 
     * @param address_5_bits address of register to be read
     */
    public void setAddressOfReadRegisterA(String address_5_bits){
        if(address_5_bits.length() == 5){
            this.addressOfReadRegisterA = address_5_bits;
        }
    }    

    /**
     * 
     * @param address_5_bits adress of register to be readed
     */
    public void setAdressOfReadRegisterB(String address_5_bits){
        if(address_5_bits.length() == 5){
            this.addressOfReadRegisterB = address_5_bits;
        }
    }

    /**
     * 
     * @param address_5_bits address of register to be write
     */
    public void setAddressOfWriteRegister(String address_5_bits){
        if(address_5_bits.length() == 5){
            this.addressOfWriteRegister = address_5_bits;
        }
    }

    /**
     * 
     * @param value_32_bits value to be stored in the write register
     */
    public void setWriteValue(String value_32_bits){
        this.writeValue = value_32_bits;
    }

    /**
     * 
     * @return value to be write in the register
     */
    public String getWriteValue(){
        return this.writeValue;
    }

    /**
     * Function responsible for write the value on the write register
     */
    public void writeRegister(){
        this.registers[Integer.parseInt(this.addressOfWriteRegister, 2)] = this.writeValue;
    }

    /**
     * Function tha read an value from register given this address
     * @param address_5_bits address of register to be read
     * @return value of the register
     */
    public String readRegister(String address_5_bits){
        return this.registers[Integer.parseInt(address_5_bits, 2)];
    }

    /**
     * Function that return the first output from registers
     * @return a 32 bits value (String)
     */
    public String getData1(){
        return this.readRegister(this.addressOfReadRegisterA);
    }

     /**
     * Function that return the second output from registers
     * @return a 32 bits value (String)
     */
    public String getData2(){
        return this.readRegister(this.addressOfReadRegisterB);
    }
}
