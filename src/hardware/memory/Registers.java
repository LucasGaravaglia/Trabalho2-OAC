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
    private String RegWrite;

    /**
     * Constructor
     */
    Registers(){
        this.registers = new String[32];
        for(String reg : this.registers){
            reg = Binary.BITS_32_ZERO;
        }
        this.addressOfReadRegisterA = Binary.BITS_5_ZERO;
        this.addressOfReadRegisterB = Binary.BITS_5_ZERO;
        this.addressOfWriteRegister = Binary.BITS_5_ZERO;
        this.writeValue = Binary.BITS_32_ZERO;
        this.RegWrite = "0";
    }

    /**
     * Function that determines the value of the flag RegWrite
     * When tha value is "1", the values are read
     * @param value String "0" or "1"
     */
    public void setRegWrite(String value){
        if(value.length() == 1){
            this.RegWrite = value;
            if(this.RegWrite == "1"){
                this.writeRegister();
            }
        }
    }

    /**
     * Function thar allow clone the content of all registers at once
     * @return Array of Strings, each String with 32 of length
     */
    public String[] cloneRegisters(){
        return this.registers.clone();
    }

    /**
     * Function that allow to overwrite all register at once
     * @param value Array of Strings, each String with 32 of length
     */
    public void overwriteAlRegisters(String[] value){
        this.registers = value.clone();
    }

    /**
     * Function that determines the address of the first input of the registers
     * @param address_5_bits address of register to be read
     */
    public void setAddressOfReadRegisterA(String address_5_bits){
        if(address_5_bits.length() == 5){
            this.addressOfReadRegisterA = address_5_bits;
        }
    }    

    /**
     * Function that determines the address of the second input of the registers
     * @param address_5_bits address of register to be read
     */
    public void setAddressOfReadRegisterB(String address_5_bits){
        if(address_5_bits.length() == 5){
            this.addressOfReadRegisterB = address_5_bits;
        }
    }

    /**
     * Function that determines the address of the third input of the registers
     * @param address_5_bits address of register to be write
     */
    public void setAddressOfWriteRegister(String address_5_bits){
        if(address_5_bits.length() == 5){
            this.addressOfWriteRegister = address_5_bits;
        }
    }

    /**
     * Function that determines the value of the forth input of the registers
     * @param value_32_bits value to be stored in the write register
     */
    public void setWriteValue(String value_32_bits){
        this.writeValue = value_32_bits;
    }


    /**
     * Function responsible for write the value on the write register
     */
    private void writeRegister(){
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
