package src.gui.window;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.awt.*;

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

  private JCheckBox Branch;
  private JCheckBox MemRead;
  private JCheckBox MemToReg;
  private JCheckBox AluOp;
  private JCheckBox MemWrite;
  private JCheckBox AluSrc;
  private JCheckBox RegWrite;

  private JScrollPane SPRegister;
  private JScrollPane SPMemory;

  private JList<String> ListRegister;
  private JList<String> ListMemory;

  private JButton NextButton;
  private JButton BackButton;
  private JButton LoadFile;

  public Window() {
    super();

    this.layout = new FlowLayout();

    this.initJFrame();
    this.initJPanel();
    this.initJLabel();
    this.initJCheckBox();
    this.initJScrollPane();
    this.initJButton();

    this.signalsPanel.add(this.Sinais);
    this.signalsPanel.add(this.Branch);
    this.signalsPanel.add(this.MemRead);
    this.signalsPanel.add(this.MemToReg);
    this.signalsPanel.add(this.AluOp);
    this.signalsPanel.add(this.MemWrite);
    this.signalsPanel.add(this.AluSrc);
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

  public void selectFile() {
    try {
      JFileChooser jFileChooser = new JFileChooser();
      int filePickerResponse = jFileChooser.showOpenDialog(LoadFile);
      if (filePickerResponse == JFileChooser.APPROVE_OPTION) {
        File selectFile = jFileChooser.getSelectedFile();
        System.out.println(selectFile.getAbsolutePath());
      } else {
        System.out.println("Nenhum arquivo selecionado.");
      }
    } catch (Exception e) {
      System.out.println("Cold not open file.\n" + e.getMessage());
    }
  }

  public void handlerListRegisters(DefaultListModel<String> model) throws Exception {
    this.ListRegister.setModel(model);
  }

  public void handlerListMemorys(DefaultListModel<String> model) throws Exception {
    this.ListMemory.setModel(model);
  }

  public void handlerPC(Integer pc) throws Exception {
    this.PC.setName("PC: " + pc.toString());
  }

  public void handlerSignals(Boolean[] signals) throws Exception {
    this.Branch.setSelected(signals[0]);
    this.MemRead.setSelected(signals[1]);
    this.MemToReg.setSelected(signals[2]);
    this.AluOp.setSelected(signals[3]);
    this.MemWrite.setSelected(signals[4]);
    this.AluSrc.setSelected(signals[5]);
    this.RegWrite.setSelected(signals[6]);
  }

  public void handlerAllComponents(Boolean b) {
  }

  private void initJButton() {
    try {
      this.NextButton = new JButton("Proxima instrução");
      this.BackButton = new JButton("Voltar uma instrução");
      this.LoadFile = new JButton("Carregar arquivo");
    } catch (Exception e) {
      System.out.println("Can't instantiate the JButton.\n" + e.getMessage());
    }
  }

  private void initJFrame() {
    try {
      this.setTitle("Simulador");
      this.setSize(this.width, this.height);

      this.layout.setVgap(0);
      this.layout.setHgap(0);

      this.setLayout(this.layout);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } catch (Exception e) {
      System.out.println("Can't instantiate the JFrames.\n" + e.getMessage());
    }
  }

  private void initJLabel() {
    try {
      this.Sinais = new JLabel("Sinais de Controle:");
      this.Registradores = new JLabel("Registradoras:");
      this.Memoria = new JLabel("Memoria:");
      this.PC = new JLabel("PC: ");
    } catch (Exception e) {
      System.out.println("Can't instantiate the JLabel.\n" + e.getMessage());
    }
  }

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
      System.out.println("Can't instantiate the JCheckBox.\n" + e.getMessage());
    }
  }

  private void initJScrollPane() {
    this.initList();
    try {
      this.SPRegister = new JScrollPane(this.ListRegister);
      this.SPMemory = new JScrollPane(this.ListMemory);
    } catch (Exception e) {
      System.out.println("Can't instantiate the JScrollPane.\n" + e.getMessage());
    }
  }

  private void initList() {
    try {
      this.ListMemory = new JList<String>();
      this.ListRegister = new JList<String>();
    } catch (Exception e) {
      System.out.println("Can't instantiate the JList.\n" + e.getMessage());
    }
  }

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
      System.out.println("Can't instantiate the JPanel.\n" + e.getMessage());
    }
  }
}
