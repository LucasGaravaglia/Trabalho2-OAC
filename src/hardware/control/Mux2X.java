package src.hardware.control;

public class Mux2X {
    private String value1;
    private String value2;

    public Mux2X(){
        this.value1 = Binary.BITS_32_ZERO;
        this.value2 = Binary.BITS_32_ZERO;
    }

    public void setValue1(String value_32_bits){
        if(value_32_bits.length() == 32){
            this.value1 = value_32_bits;
        }else{
            this.value1 = Binary.BITS_32_ZERO;
        }
    }

    public void setValue2(String value_32_bits){
        if(value_32_bits.length() == 32){
            this.value2 = value_32_bits;
        }else{
            this.value2 = Binary.BITS_32_ZERO;
        }
    }

    public String getResult(int bit){
        if(bit == 1){
            return this.value2;
        }
        return this.value1;
    }

}
