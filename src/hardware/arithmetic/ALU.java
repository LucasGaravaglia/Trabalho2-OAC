package src.hardware.arithmetic;

import src.utils.*;

public class ALU {
    private String data1;
    private String data2;
    private String result;
    private int zeroFlag;
    private String control;

    public ALU(){
        this.data1 = Binary.BITS_32_ZERO;
        this.data2 = Binary.BITS_32_ZERO;
        this.result = Binary.BITS_32_ZERO;
        this.zeroFlag = 0;
        this.control = "00";
    }

    public void setData1(String data){
        if(data.length() == 32){
            this.data1 = data;
        }else{
            this.data1 = Binary.BITS_32_ZERO;
        }
    }

    public void setData2(String data){
        if(data.length() == 32){
            this.data2 = data;
        }else{
            this.data2 = Binary.BITS_32_ZERO;
        }
    }

    public String getData1(){
        return this.data1;
    }

    public String getData2(){
        return this.data2;
    }

    public void setALUControl(String value){
        this.control = value;
    }

    public String getALUControl(){
        return this.getALUControl();
    }

    public int getZeroFlag(){
        return this.zeroFlag;
    }

    public String getResult(){
        Long valueA = Long.parseLong(this.data1, 2);
        Long valueB = Long.parseLong(this.data2, 2);
        this.result = Long.toBinaryString(valueA + valueB);
        return this.result;
    }
}
