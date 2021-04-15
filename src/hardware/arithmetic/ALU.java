package src.hardware.arithmetic;

import src.utils.*;

/**
 * Levi
 */
public class ALU {
    private String data1;
    private String data2;
    private String result;
    private int zeroFlag;
    private String control;

    /**
     * Constructor
     */
    public ALU(){
        this.data1 = Binary.BITS_32_ZERO;
        this.data2 = Binary.BITS_32_ZERO;
        this.result = Binary.BITS_32_ZERO;
        this.zeroFlag = 0;
        this.control = "00";
    }

    /**
     * Function that determines the value of the first input of the ALU
     * @param data 32 bits value
     */
    public void setData1(String data){
        if(data.length() == 32){
            this.data1 = data;
        }else{
            this.data1 = Binary.BITS_32_ZERO;
        }
    }

    /**
     * Function that determines the value of the second input of the ALU
     * @param data 32 bits value
     */
    public void setData2(String data){
        if(data.length() == 32){
            this.data2 = data;
        }else{
            this.data2 = Binary.BITS_32_ZERO;
        }
    }

    /**
     * Function that should be used for get that current data of the first input of the ALU
     * @return the current data in the input1 of the ALU
     */
    public String getData1(){
        return this.data1;
    }

    /**
     * Function that should be used for get that current data of the second input of the ALU
     * @return the current data in the input2 of the ALU
     */
    public String getData2(){
        return this.data2;
    }

    /**
     * Function that determines the operation to be executed by ALU
     * @param value that control the behavior of the ALU
     */
    public void setALUControl(String value){
        this.control = value;
    }

    /**
     * Function that should be used for get that current data of the control input of the ALU
     * @return the current data in the control input of the ALU
     */
    public String getALUControl(){
        return this.getALUControl();
    }

    /**
     * Function that should be used for get the current value of the zero flag
     * @return the value of the zero flag of the ALU
     */
    public int getZeroFlag(){
        return this.zeroFlag;
    }

    /**
     * Function that execute que selected operation and generate the outputs and flags
     * @return the result of the ALU given the inputs and the control
     */
    public String getResult(){
        if(this.control == "0010"){//add
            this.result = Binary.addBinary_32_btis(this.data1, this.data2);
        }else if(this.control == "0110"){//sub
            this.result = Binary.subBinary_32_btis(this.data1, this.data2);
        }else if(this.control == "0000"){//and
            this.result = Binary.andBinary_32_btis(this.data1, this.data2);
        }else if(this.control == "0001"){//or
            this.result = Binary.orBinary_32_btis(this.data1, this.data2);
        }else {
            this.result = Binary.BITS_32_ZERO;
        }
        
        if(this.result == Binary.BITS_32_ZERO){
            this.zeroFlag = 1;
        }else{
            this.zeroFlag = 0;
        }
        return this.result;
    }
}
