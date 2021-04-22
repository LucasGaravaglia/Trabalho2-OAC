package src.hardware.control;

import src.utils.TypesConversion;
import src.log.Log;
/**
 * @author Levi
 */
public class BranchControl {
    private String branch;
    private String zero;
    private String output;

    /**
     * Constructor
     */
    public BranchControl(){
        this.branch = "00";
        this.output = "0";
        this.zero = "0";
        this.execute();
    }

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
        this.execute();
    }

    /**
     * Function that returns the current input of branch
     * @return String with length of 2
     */
    public String getCurrentBranchInput(){
        return this.branch;
    }

    /**
     * Function that returns the current input of zero
     * @return String with length of 1
     */
    public String getCurrentZeroInput(){
        return this.zero;
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
        this.execute();
    }

    /**
     * Function that determines the result of the branchControl
     */
    private void execute(){
        boolean zero, b1, b0, result;
        zero = TypesConversion.getLogicValueFromString(this.zero);
        b0 = TypesConversion.getLogicValueFromChar(this.branch.charAt(1));
        b1 = TypesConversion.getLogicValueFromChar(this.branch.charAt(0));

        result = (zero && b1 && !b0) || (!zero && b1 && b0);
        this.output = TypesConversion.boolToString(result);        
    }

    /**
     * Function that return the branchControl output, if "1" branch, else not branch
     * @return String "1" or "0"
     */
    public String getOutput(){
        return this.output;
    }


    /**
     * Function that print all information of the component in the log file
     */
    public void doLog(){
        Log.doLog("********* BranchControl *******");        
        Log.doLog("branch: " + this.branch);
        Log.doLog("zero: " + this.zero);
        Log.doLog("output: " + this.output);
    }

    /**
     * Function that print all information of the component in the log file
     */
    public void doLog(String m){
        Log.doLog("********* BranchControl *******");  
        Log.doLog(m);      
        Log.doLog("branch: " + this.branch);
        Log.doLog("zero: " + this.zero);
        Log.doLog("output: " + this.output);
    }
}
