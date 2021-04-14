package src.hardware.memory;

public class PC {
    private String value;

    PC(String value){
        this.value = value;
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}