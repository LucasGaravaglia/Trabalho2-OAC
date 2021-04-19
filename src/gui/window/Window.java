package src.gui.window;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.awt.*;

import src.control.flux.*;
import src.control.simulation.*;
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

  private JCheckBox AluOp;
  private JCheckBox AluSrc;
  private JCheckBox Branch;
  private JCheckBox MemRead;
  private JCheckBox MemToReg;
  private JCheckBox MemWrite;
  private JCheckBox RegWrite;

  private JScrollPane SPRegister;
  private JScrollPane SPMemory;

  private JList<String> ListRegister;
  private JList<String> ListMemory;

  private JButton NextButton;
  private JButton BackButton;
  private JButton LoadFile;

  private Flux flux;
  private Simulation simulation;
  private Load loadFile;

  private Data data;

  /**
   * Constructor
   */
  public Window() {
    super();

    this.simulation = new Simulation();
    this.flux = new Flux(this.simulation);
    this.loadFile = new Load();

    this.layout = new FlowLayout();

    this.initJFrame();
    this.initJPanel();
    this.initJLabel();
    this.initJCheckBox();
    this.initJScrollPane();
    this.initJButton();

    this.signalsPanel.add(this.Sinais);
    this.signalsPanel.add(this.AluOp);
    this.signalsPanel.add(this.AluSrc);
    this.signalsPanel.add(this.Branch);
    this.signalsPanel.add(this.MemRead);
    this.signalsPanel.add(this.MemToReg);
    this.signalsPanel.add(this.MemWrite);
    this.signalsPanel.add(this.RegWrite);
    this.signalsPanel.add(this.PC);
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
   * 
   * @exception Exception Error when to obtain file path
   */
  public void selectFile() {
    StringBuilder message = new StringBuilder();
    try {
      JFileChooser jFileChooser = new JFileChooser();
      int filePickerResponse = jFileChooser.showOpenDialog(LoadFile);
      if (filePickerResponse == JFileChooser.APPROVE_OPTION) {
        File selectFile = jFileChooser.getSelectedFile();
        this.flux.setInstructions(this.loadFile.loadFile(selectFile.getPath()));
      } else {
        message.append("Nenhum arquivo selecionado.");
        JOptionPane.showMessageDialog(null, message);
      }
    } catch (Exception e) {
      message.append("Cold not open file.\n" + e.getMessage());
      JOptionPane.showMessageDialog(null, message);
    }
  }

  /**
   * Responsible for set JList of Register.
   * 
   * @param model String array object.
   * @throws Exception Error set JList
   */
  public void handlerListRegisters(DefaultListModel<String> model) throws Exception {
    this.ListRegister.setModel(model);
  }

  /**
   * Responsible for set JList of Memory.
   * 
   * @param model String array object.
   * @throws Exception Error set JList
   */
  public void handlerListMemorys(DefaultListModel<String> model) throws Exception {
    this.ListMemory.setModel(model);
  }

  /**
   * Responsible for set JLabel of pc.
   * 
   * @param pc Pointer of PC.
   * @throws Exception Error set JLabel.
   */
  public void handlerPC(String pc) throws Exception {
    this.PC.setName("PC: " + pc);
  }

  /**
   * Method responsible for update signals.
   * 
   * @param signals Array Boolean the Signals is True
   * @throws Exception Error set JCheckBox of signals.
   */
  public void handlerSignals(Boolean[] signals) throws Exception {
    this.AluOp.setSelected(signals[0]);
    this.AluSrc.setSelected(signals[1]);
    this.Branch.setSelected(signals[2]);
    this.MemRead.setSelected(signals[3]);
    this.MemToReg.setSelected(signals[4]);
    this.MemWrite.setSelected(signals[5]);
    this.RegWrite.setSelected(signals[6]);
  }

  /**
   * Method responsible for set all components.
   * 
   * @param b Variable responsible for saying whether to go to the next
   *          instruction or back.
   */
  public void handlerAllComponents(Boolean b) {
    if (b) {
      try {
        this.flux.doClock();
        this.simulation.getCurrentState();
      } catch (Exception e) {
        System.out.println("Erro ao obter o estado atual do processador.");
      }
    } else {

    }

  }

  /**
   * Method responsible for instantiate the JButtons
   * 
   * @exception Exception Error when instantiating some JButtons
   */
  private void initJButton() {
    try {
      this.NextButton = new JButton("Proxima instrução");
      this.BackButton = new JButton("Voltar uma instrução");
      this.LoadFile = new JButton("Carregar arquivo");
    } catch (Exception e) {
      StringBuilder message = new StringBuilder();
      message.append("Can't instantiate the JButton.\n" + e.getMessage());
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
      message.append("Can't instantiate the JFrames.\n" + e.getMessage());
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
      this.Registradores = new JLabel("Registradoras:");
      this.Memoria = new JLabel("Memoria:");
      this.PC = new JLabel("PC: ");
    } catch (Exception e) {
      StringBuilder message = new StringBuilder();
      message.append("Can't instantiate the JLabel.\n" + e.getMessage());
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
      this.Branch = new JCheckBox("Branch", false);
      this.Branch.setEnabled(false);
      this.MemRead = new JCheckBox("MemRead", false);
      this.MemRead.setEnabled(false);
      this.MemToReg = new JCheckBox("MemToReg", false);
      this.MemToReg.setEnabled(false);
      this.AluOp = new JCheckBox("AluOp", false);
      this.AluOp.setEnabled(false);
      this.MemWrite = new JCheckBox("MemWrite", false);
      this.MemWrite.setEnabled(false);
      this.AluSrc = new JCheckBox("AluSrc", false);
      this.AluSrc.setEnabled(false);
      this.RegWrite = new JCheckBox("RegWrite", false);
      this.RegWrite.setEnabled(false);
    } catch (Exception e) {
      StringBuilder message = new StringBuilder();
      message.append("Can't instantiate the JCheckBox.\n" + e.getMessage());
      JOptionPane.showMessageDialog(null, message);
    }
  }

  /**
   * Method responsible for instantiate the JScrollPane
   * 
   * @exception Exception Error when instantiating some JScrollPane
   */
  private void initJScrollPane() {
    this.initList();
    try {
      this.SPRegister = new JScrollPane(this.ListRegister);
      this.SPMemory = new JScrollPane(this.ListMemory);
    } catch (Exception e) {
      StringBuilder message = new StringBuilder();
      message.append("Can't instantiate the JScrollPane.\n" + e.getMessage());
      JOptionPane.showMessageDialog(null, message);
    }
  }

  /**
   * Method responsible for instantiate the JList
   * 
   * @exception Exception Error when instantiating some JList
   */
  private void initList() {
    try {
      this.ListMemory = new JList<String>();
      this.ListRegister = new JList<String>();
    } catch (Exception e) {
      StringBuilder message = new StringBuilder();
      message.append("Can't instantiate the JList.\n" + e.getMessage());
      JOptionPane.showMessageDialog(null, message);
    }
  }

  /**
   * Method responsible for instantiate the JPanel
   * 
   * @exception Exception Error when instantiating some JPanel
   */
  private void initJPanel() {
    try {
      int widthPanel = 290;
      int heightPanel = this.height - 150;

      this.signalsPanel = new JPanel();
      this.RegisterPanel = new JPanel();
      this.MemoryPanel = new JPanel();
      this.buttonsPanel = new JPanel();

      this.signalsPanel.setLayout(new BoxLayout(signalsPanel, BoxLayout.Y_AXIS));
      this.RegisterPanel.setLayout(new BoxLayout(RegisterPanel, BoxLayout.Y_AXIS));
      this.MemoryPanel.setLayout(new BoxLayout(MemoryPanel, BoxLayout.Y_AXIS));

      this.signalsPanel.setPreferredSize(new Dimension(widthPanel, heightPanel));
      this.RegisterPanel.setPreferredSize(new Dimension(widthPanel, heightPanel));
      this.MemoryPanel.setPreferredSize(new Dimension(widthPanel, heightPanel));
      this.buttonsPanel.setPreferredSize(new Dimension(this.width, 100));
    } catch (Exception e) {
      StringBuilder message = new StringBuilder();
      message.append("Can't instantiate the JPanel.\n" + e.getMessage());
      JOptionPane.showMessageDialog(null, message);
    }
  }
}
