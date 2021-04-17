package src.hardware.control;

import src.utils.TypesConversion;

/**
 * @author Levi
 */
public class BranchControl {
    private String branch;
    private String zero;
    private String output;

    /**
     * Function that determines the branch input of the branchControl
     * @param value String with length of 2
     */
    public void setBranch(String value){
        if(value.length() == 2){
            this.branch = value;
        }else{
            this.branch = "00";
        }
    }

    /**
     * Function that determines the "zero" input of the branchControl
     * @param value String with length of 1
     */
    public void setZero(String value){
        if(value.compareTo("0") == 0 || value.compareTo("1") == 0){
            this.zero = value;
        }else{
            this.zero = "0";
        }
    }

    /**
     * Function that determines the result of the branchControl
     */
    private void execute(){
        boolean zero, b1, b0, result;
        zero = TypesConversion.getLogicValueFromString(this.zero);
        b1 = TypesConversion.getLogicValueFromChar(this.branch.charAt(1));
        b0 = TypesConversion.getLogicValueFromChar(this.branch.charAt(0));

        result = (zero && b0 && !b1) || (!zero && b0 && b1);
        this.output = TypesConversion.boolToString(result);        
    }

    /**
     * Function that return the branchControl output, if "1" branch, else not branch
     * @return String "1" or "0"
     */
    public String getOutput(){
        this.execute();
        return this.output;
    }
}
