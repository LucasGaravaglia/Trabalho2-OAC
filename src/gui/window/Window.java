package src.gui.window;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;
import java.io.EOFException;
import java.io.File;
import java.awt.*;

import src.control.flux.*;
import src.gui.data.*;
import src.file.*;

/**
 * Class responsible for creating the graphical interface.
 * 
 * @author Lucas Garavaglia
 */
public class Window extends JFrame {

  private final int width = 900;
  private final int height = 700;

  private JPanel signalsPanel;
  private JPanel RegisterPanel;
  private JPanel MemoryPanel;
  private JPanel buttonsPanel;

  private FlowLayout layout;

  private JLabel Sinais;
  private JLabel Registradores;
  private JLabel Memoria;
  private JLabel PC;

  private JCheckBox AluOp0;
  private JCheckBox AluOp1;
  private JCheckBox AluSrc;
  private JCheckBox Branch0;
  private JCheckBox Branch1;
  private JCheckBox MemRead;
  private JCheckBox MemToReg;
  private JCheckBox MemWrite;
  private JCheckBox RegWrite;
  private JCheckBox flagZero;

  private JButton NextButton;
  private JButton BackButton;
  private JButton LoadFile;

  private Flux flux;
  private Load loadFile;

  private Data data;

  private JScrollPane SPRegister;
  private JScrollPane SPMemory;
  private JScrollPane SPInstructions;

  private JTable tableInstructions;
  private JTable tableMemory;
  private JTable tableRegister;

  private DefaultTableModel modelInstruction;
  private DefaultTableModel modelRegister;
  private DefaultTableModel modelMemory;

  /**
   * Constructor
   */
  public Window() {
    super();

    this.flux = new Flux();
    this.loadFile = new Load();

    this.layout = new FlowLayout();

    this.initJFrame();
    this.initJPanel();
    this.initJLabel();
    this.initJCheckBox();
    this.initJScrollPane();
    this.initJButton();
    this.initJTableInstruction();
    this.initJTableRegister();
    this.initJTableTableMemory();

    this.signalsPanel.add(this.Sinais);
    this.signalsPanel.add(this.AluOp0);
    this.signalsPanel.add(this.AluOp1);
    this.signalsPanel.add(this.AluSrc);
    this.signalsPanel.add(this.Branch0);
    this.signalsPanel.add(this.Branch1);
    this.signalsPanel.add(this.MemRead);
    this.signalsPanel.add(this.MemToReg);
    this.signalsPanel.add(this.MemWrite);
    this.signalsPanel.add(this.RegWrite);
    this.signalsPanel.add(this.flagZero);
    this.signalsPanel.add(this.PC);
    this.signalsPanel.add(this.SPInstructions);
    this.add(this.signalsPanel);

    this.RegisterPanel.add(this.Registradores);
    this.RegisterPanel.add(this.SPRegister);
    this.add(this.RegisterPanel);

    this.MemoryPanel.add(this.Memoria);
    this.MemoryPanel.add(this.SPMemory);
    this.add(this.MemoryPanel);

    this.buttonsPanel.add(this.BackButton);
    this.buttonsPanel.add(this.NextButton);
    this.buttonsPanel.add(this.LoadFile);
    this.add(this.buttonsPanel);

    this.NextButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        handlerAllComponents(true);
      }
    });
    this.BackButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        handlerAllComponents(false);
      }
    });
    this.LoadFile.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        selectFile();
      }
    });
  }

  /**
   * Method responsible for select and send path file for flux processor
   */
  public void selectFile() {
    StringBuilder message = new StringBuilder();
    String[] fileContent;
    try {
      JFileChooser jFileChooser = new JFileChooser();
      int filePickerResponse = jFileChooser.showOpenDialog(LoadFile);
      if (filePickerResponse == JFileChooser.APPROVE_OPTION) {
        File selectFile = jFileChooser.getSelectedFile();
        fileContent = this.loadFile.loadFile(selectFile.getPath());
        this.flux.setInstructions(fileContent);
        this.handlerListInstructions(fileContent);
        this.data = null;
        this.data = this.flux.getState();
        this.handlerSignals(this.data.getSignals());
        this.handlerMemories(this.data.getMemory());
        this.handlerRegisters(this.data.getRegister());
        this.handlerPC(this.data.getPc().toString());
        message.append("Arquivo aberto com sucesso!");
        JOptionPane.showMessageDialog(null, message);
      } else {
        message.append("Nenhum arquivo selecionado.");
        JOptionPane.showMessageDialog(null, message);
      }
    } catch (EOFException e) {
      message.append("Erro ao abrir o arquivo.\n" + e);
      JOptionPane.showMessageDialog(null, message);
    } catch (Exception e1) {
      message.append("Erro ao carregar os dados para a memória.\n" + e1);
      JOptionPane.showMessageDialog(null, message);
    }
  }

  /**
   * Responsible for set JTable of Instructions.
   * 
   * @param list String array of Instructions.
   * @throws Exception Error set JTable
   */
  public void handlerListInstructions(String[] list) throws Exception {
    this.modelInstruction.setRowCount(0);
    for (int i = 0; i < list.length; i++) {
      this.modelInstruction.addRow(new Object[] { "", i * 4, list[i] });
    }
  }

  /**
   * Responsible for set pointer JTable of Instructions.
   * 
   * @param address Address of actually instruction.
   * @throws Exception Error set JTable
   */
  public void handlerListInstructions(Integer address) throws Exception {
    int n = this.modelInstruction.getRowCount();
    for (int i = 0; i < n; i++) {
      this.modelInstruction.setValueAt("", i, 0);
    }
    if (n > address / 4)
      this.modelInstruction.setValueAt("*", address / 4, 0);
  }

  /**
   * Responsible for set JTable of Register.
   * 
   * @param model String array of Registers.
   * @throws Exception Error set JTable
   */
  public void handlerRegisters(String[] register) throws Exception {
    this.modelRegister.setRowCount(0);
    int n = register.length;
    for (Integer i = 0; i < n; i++) {
      this.modelRegister.addRow(new Object[] { "X" + i.toString(), src.utils.Binary.getInt(register[i]), register[i] });
    }
  }

  /**
   * Responsible for set JTable of Memory.
   * 
   * @param model String array of memories.
   * @throws Exception Error set JTable
   */
  public void handlerMemories(String[] memories) throws Exception {
    this.modelMemory.setRowCount(0);
    int n = memories.length;
    for (int i = 0; i < n; i++) {
      this.modelMemory.addRow(new Object[] { i * 4, src.utils.Binary.getInt(memories[i]), memories[i] });
    }
  }

  /**
   * Responsible for set JLabel of pc.
   * 
   * @param pc Pointer of PC.
   * @throws Exception Error set JLabel.
   */
  public void handlerPC(String pc) throws Exception {
    this.PC.setText("PC: " + pc);
    this.handlerListInstructions(Integer.parseInt(pc));
  }

  /**
   * Method responsible for update signals.
   * 
   * @param signals Array Boolean the Signals is True
   * @throws Exception Error set JCheckBox of signals.
   */
  public void handlerSignals(Boolean[] signals) throws Exception {
    this.AluOp0.setSelected(signals[0]);
    this.AluOp1.setSelected(signals[1]);
    this.AluSrc.setSelected(signals[2]);
    this.Branch0.setSelected(signals[3]);
    this.Branch1.setSelected(signals[4]);
    this.MemRead.setSelected(signals[5]);
    this.MemToReg.setSelected(signals[6]);
    this.MemWrite.setSelected(signals[7]);
    this.RegWrite.setSelected(signals[8]);
    this.flagZero.setSelected(signals[9]);
  }

  /**
   * Method responsible for set all components.
   * 
   * @param b Variable responsible for saying whether to go to the next
   *          instruction or back.
   */
  public void handlerAllComponents(Boolean b) {
    StringBuilder message = new StringBuilder();
    if (b) {
      try {
        this.flux.doClock();
        this.data = null;
        this.data = this.flux.getState();
        this.handlerSignals(this.data.getSignals());
        this.handlerMemories(this.data.getMemory());
        this.handlerRegisters(this.data.getRegister());
        this.handlerPC(this.data.getPc().toString());
      } catch (Exception e) {
        if (e.getMessage() == "End of instruction memory") {
          message.append("Fim das instruções.");
          JOptionPane.showMessageDialog(null, message);
        } else {
          message.append("Erro ao obter o estado atual do processador." + e);
          JOptionPane.showMessageDialog(null, message);
          System.out.println("Erro ao obter o estado atual do processador." + e);
        }
      }
    } else {
      try {
        this.flux.undoClock();
        this.data = null;
        this.data = this.flux.getState();
        this.handlerSignals(this.data.getSignals());
        this.handlerMemories(this.data.getMemory());
        this.handlerRegisters(this.data.getRegister());
        this.handlerPC(this.data.getPc().toString());
      } catch (Exception e) {
        if (e.getMessage() == "States list is empty") {
          message.append("Não é possível voltar mais uma instrução.");
          JOptionPane.showMessageDialog(null, message);
        } else {
          message.append("Erro ao obter o estado atual do processador." + e);
          JOptionPane.showMessageDialog(null, message);
          System.out.println("Erro ao obter o estado atual do processador." + e);
        }
      }
    }

  }

  public void initJTableInstruction() {
    this.modelInstruction = new DefaultTableModel();
    this.tableInstructions = new JTable(this.modelInstruction);
    this.modelInstruction.addColumn("CI");
    this.modelInstruction.addColumn("Address");
    this.modelInstruction.addColumn("Instruction");
    this.tableInstructions.getColumnModel().getColumn(0).setPreferredWidth(1);
    this.tableInstructions.getColumnModel().getColumn(1).setPreferredWidth(30);
    this.tableInstructions.getColumnModel().getColumn(2).setPreferredWidth(250);
    this.tableInstructions.setEnabled(false);
    this.SPInstructions.setViewportView(this.tableInstructions);
  }

  public void initJTableTableMemory() {
    this.modelMemory = new DefaultTableModel();
    this.tableMemory = new JTable(this.modelMemory);
    this.modelMemory.addColumn("Address");
    this.modelMemory.addColumn("Dc");
    this.modelMemory.addColumn("Content");
    this.tableMemory.getColumnModel().getColumn(0).setPreferredWidth(20);
    this.tableMemory.getColumnModel().getColumn(1).setPreferredWidth(1);
    this.tableMemory.getColumnModel().getColumn(2).setPreferredWidth(200);
    this.tableMemory.setEnabled(false);
    this.SPMemory.setViewportView(this.tableMemory);
  }

  public void initJTableRegister() {
    this.modelRegister = new DefaultTableModel();
    this.tableRegister = new JTable(this.modelRegister);
    this.modelRegister.addColumn("Rg");
    this.modelRegister.addColumn("Dc");
    this.modelRegister.addColumn("Content");
    this.tableRegister.getColumnModel().getColumn(0).setPreferredWidth(1);
    this.tableRegister.getColumnModel().getColumn(1).setPreferredWidth(1);
    this.tableRegister.getColumnModel().getColumn(2).setPreferredWidth(200);
    this.tableRegister.setEnabled(false);
    this.SPRegister.setViewportView(this.tableRegister);
  }

  /**
   * Method responsible for instantiate the JButtons
   * 
   * @exception Exception Error when instantiating some JButtons
   */
  private void initJButton() {
    try {
      this.NextButton = new JButton("Executar");
      this.BackButton = new JButton("Voltar");
      this.LoadFile = new JButton("Carregar arquivo");
    } catch (Exception e) {
      StringBuilder message = new StringBuilder();
      message.append("Can't instantiate the JButton.\n" + e);
      JOptionPane.showMessageDialog(null, message);
    }
  }

  /**
   * Method responsible for instantiate the JFrame
   * 
   * @exception Exception Error when instantiating some JFrame
   */
  private void initJFrame() {
    try {
      this.setTitle("Simulador");
      this.setSize(this.width, this.height);

      this.layout.setVgap(0);
      this.layout.setHgap(0);

      this.setLayout(this.layout);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } catch (Exception e) {
      StringBuilder message = new StringBuilder();
      message.append("Can't instantiate the JFrames.\n" + e);
      JOptionPane.showMessageDialog(null, message);
    }
  }

  /**
   * Method responsible for instantiate the JLabel
   * 
   * @exception Exception Error when instantiating some JLabel
   */
  private void initJLabel() {
    try {
      this.Sinais = new JLabel("Sinais de Controle:");
      this.Registradores = new JLabel("Registradores:");
      this.Memoria = new JLabel("Memoria:");
      this.PC = new JLabel("PC: ");
    } catch (Exception e) {
      StringBuilder message = new StringBuilder();
      message.append("Can't instantiate the JLabel.\n" + e);
      JOptionPane.showMessageDialog(null, message);
    }
  }

  /**
   * Method responsible for instantiate the JCheckBox
   * 
   * @exception Exception Error when instantiating some JCheckBox
   */
  private void initJCheckBox() {
    try {
      this.Branch0 = new JCheckBox("Branch0", false);
      this.Branch0.setEnabled(false);
      this.Branch1 = new JCheckBox("Branch1", false);
      this.Branch1.setEnabled(false);
      this.MemRead = new JCheckBox("MemRead", false);
      this.MemRead.setEnabled(false);
      this.MemToReg = new JCheckBox("MemToReg", false);
      this.MemToReg.setEnabled(false);
      this.AluOp0 = new JCheckBox("AluOp0", false);
      this.AluOp0.setEnabled(false);
      this.AluOp1 = new JCheckBox("AluOp1", false);
      this.AluOp1.setEnabled(false);
      this.MemWrite = new JCheckBox("MemWrite", false);
      this.MemWrite.setEnabled(false);
      this.AluSrc = new JCheckBox("AluSrc", false);
      this.AluSrc.setEnabled(false);
      this.RegWrite = new JCheckBox("RegWrite", false);
      this.RegWrite.setEnabled(false);
      this.flagZero = new JCheckBox("AluZero", false);
      this.flagZero.setEnabled(false);
    } catch (Exception e) {
      StringBuilder message = new StringBuilder();
      message.append("Can't instantiate the JCheckBox.\n" + e);
      JOptionPane.showMessageDialog(null, message);
    }
  }

  /**
   * Method responsible for instantiate the JScrollPane
   * 
   * @exception Exception Error when instantiating some JScrollPane
   */
  private void initJScrollPane() {
    this.SPRegister = new JScrollPane();
    this.SPMemory = new JScrollPane();
    this.SPInstructions = new JScrollPane();
  }

  /**
   * Method responsible for instantiate the JPanel
   * 
   * @exception Exception Error when instantiating some JPanel
   */
  private void initJPanel() {
    try {
      int widthPanel = 350;
      int heightPanel = this.height - 120;

      this.signalsPanel = new JPanel();
      this.RegisterPanel = new JPanel();
      this.MemoryPanel = new JPanel();
      this.buttonsPanel = new JPanel();

      this.signalsPanel.setLayout(new BoxLayout(signalsPanel, BoxLayout.Y_AXIS));
      this.RegisterPanel.setLayout(new BoxLayout(RegisterPanel, BoxLayout.Y_AXIS));
      this.MemoryPanel.setLayout(new BoxLayout(MemoryPanel, BoxLayout.Y_AXIS));

      this.signalsPanel.setPreferredSize(new Dimension(widthPanel + 50, heightPanel));
      this.RegisterPanel.setPreferredSize(new Dimension(widthPanel + 100, heightPanel));
      this.MemoryPanel.setPreferredSize(new Dimension(widthPanel + 100, heightPanel));
      this.buttonsPanel.setPreferredSize(new Dimension(this.width, 100));
    } catch (Exception e) {
      StringBuilder message = new StringBuilder();
      message.append("Can't instantiate the JPanel.\n" + e);
      JOptionPane.showMessageDialog(null, message);
    }
  }
}
