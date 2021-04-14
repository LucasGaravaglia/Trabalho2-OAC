package src.hardware.memory;

/**
 * @author Levi
 */
public class PC {
    private String value;

    /**
     * 
     * @param value 32 bits value
     */    
    PC(String value){
        this.value = value;
    }

    /**
     * 
     * @param value 32 bits value
     */  
    public void setValue(String value){
        this.value = value;
    }

    /**
     * 
     * @return 32 bits value
     */  
    public String getValue(){
        return this.value;
    }
}