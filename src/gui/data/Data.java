package src.gui.data;

import javax.swing.DefaultListModel;

public class Data {
  private DefaultListModel<String> modelMemory;
  private DefaultListModel<String> modelRegister;
  private Boolean[] signals;
  private Integer Pc;


  public Data() {
    this.modelMemory = new DefaultListModel<String>();
    this.modelRegister = new DefaultListModel<String>();
    this.signals = new Boolean[8];
    this.Pc = 0;
  }

  public void setPc(String pc) {
    this.Pc = Integer.parseInt(pc);
  }

  public void setSignals(String[] signals) {
    int i = 0;
    for (String boolean1 : signals) {
      this.signals[i] = Boolean.parseBoolean(boolean1);
      i++;
    }
  }
  
  public void setModelMemory(String line) {
    this.modelMemory.addElement(line);
  }

  public void setModelRegister(String line) {
    this.modelRegister.addElement(line);
  }

  public void clearModelMemory() {
    this.modelMemory.clear();
  }

  public void clearModelRegister() {
    this.modelRegister.clear();
  }

  public Integer getPc() {
    return this.Pc;
  }

  public Boolean[] getSignals() {
    return this.signals;
  }

  public DefaultListModel<String> getModelMemory() {
    return this.modelMemory;
  }

  public DefaultListModel<String> getModelRegister() {
    return this.modelRegister;
  }
}
