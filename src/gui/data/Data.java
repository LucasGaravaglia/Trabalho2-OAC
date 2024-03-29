package src.gui.data;

/**
 * Class responsible for data type the interface graphic.
 * 
 */
public class Data {
  private String[] Memory;
  private String[] Register;
  private Boolean[] signals;
  private Integer Pc;

  /**
   * Constructor of class
   */
  public Data() {
    this.signals = new Boolean[10];
    this.Pc = 0;
  }

  /**
   * Setter Pc
   * 
   * @param pc String Pc
   */
  public void setPc(String pc) {
    this.Pc = src.utils.Binary.getDecimalValue(pc);
  }

  /**
   * Setter Signals
   * 
   * @param signals String array the signals.
   */
  public void setSignals(String[] signals) {
    int i = 0;
    for (String boolean1 : signals) {
      this.signals[i] = src.utils.TypesConversion.getLogicValueFromString(boolean1);
      i++;
    }
  }

  /**
   * Setter ModelRegister
   * 
   * @param line Line that will be added to the Memory
   */
  public void setMemory(String line) {
    String[] strings = line.split("\n");
    this.Memory = new String[strings.length];
    int i = 0;
    for (String string : strings) {
      this.Memory[i++] = string;
    }
  }

  /**
   * Setter ModelRegister
   * 
   * @param line Line that will be added to the Registers
   */

  public void setRegister(String line) {
    String[] strings = line.split("\n");
    this.Register = new String[strings.length];
    int i = 0;
    for (String string : strings) {
      this.Register[i++] = string;
    }
  }

  /**
   * Getter Pc
   * 
   * @return Pc
   */
  public Integer getPc() {
    return this.Pc;
  }

  /**
   * Getter Signals
   * 
   * @return Signals
   */
  public Boolean[] getSignals() {
    return this.signals;
  }

  /**
   * Getter ModelMemory
   * 
   * @return ModelMemory
   */
  public String[] getMemory() {
    return this.Memory;
  }

  /**
   * Getter ModelRegister
   * 
   * @return ModelRegister
   */
  public String[] getRegister() {
    return this.Register;
  }
}
