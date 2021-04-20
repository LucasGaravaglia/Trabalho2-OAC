package src.gui.data;

import javax.swing.DefaultListModel;

/**
 * Class responsible for data type the interface graphic.
 * 
 * @author Lucas Garavaglia
 */
public class Data {
  private DefaultListModel<String> modelMemory;
  private DefaultListModel<String> modelRegister;
  private Boolean[] signals;
  private Integer Pc;

  /**
   * Constructor of class
   */
  public Data() {
    this.modelMemory = new DefaultListModel<String>();
    this.modelRegister = new DefaultListModel<String>();
    this.signals = new Boolean[7];
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
      this.signals[i] = Boolean.parseBoolean(boolean1);
      i++;
    }
  }

  /**
   * Setter ModelRegister
   * 
   * @param line Line that will be added to the modelMemory
   */
  public void setModelMemory(String line) {
    this.modelMemory.addElement(line);
  }

  /**
   * Setter ModelRegister
   * 
   * @param line Line that will be added to the modelRegister
   */

  public void setModelRegister(String line) {
    this.modelRegister.addElement(line);
  }

  /**
   * Method responsible for clear the modelMemory.
   */
  public void clearModelMemory() {
    this.modelMemory.clear();
  }

  /**
   * Method responsible for clear the modelRegister.
   */
  public void clearModelRegister() {
    this.modelRegister.clear();
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
  public DefaultListModel<String> getModelMemory() {
    return this.modelMemory;
  }

  /**
   * Getter ModelRegister
   * 
   * @return ModelRegister
   */
  public DefaultListModel<String> getModelRegister() {
    return this.modelRegister;
  }
}
