package com.github.christophelg.swinglayoutbuilder;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ApplicationGui extends JPanel {
  private static final long serialVersionUID = 7999442596780544357L;

  private JTextField envTextField = new JTextField(20);
  private JTextField asOfDateTextField = new JTextField();
  private JComboBox<String> dataTypeComboBox = new JComboBox<String>();
  private JTextField filepathTextField = new JTextField(20);
  private JTextField criteriaTextField = new JTextField(20);
  private JTextField sheetTextField = new JTextField(20);
  private JCheckBox appendToCheckBox = new JCheckBox();
  private JCheckBox lastVersionCheckBox = new JCheckBox();
  private JCheckBox exportDependenciesCheckBox = new JCheckBox();
  private JTextArea feedbackTextArea = new JTextArea(30, 20);

  public ApplicationGui() {
    init();
  }

  private void init() {
    LayoutBuilder builder = new LayoutBuilder();

    builder.addColumn(new JLabel("Calypso Env.:", JLabel.RIGHT)).addColumn(envTextField);
    builder.addRow();
    builder.addColumn(new JLabel("Env. AsOfDate:", JLabel.RIGHT)).addColumn(asOfDateTextField);
    builder.addRow();
    builder.addColumn(new JLabel("Data Type:", JLabel.RIGHT)).addColumn(dataTypeComboBox);
    builder.addRow();
    builder.addColumn(new JLabel("Criteria:", JLabel.RIGHT)).addColumn(criteriaTextField);
    builder.addRow();
    builder.addColumn(new JLabel("File Path:", JLabel.RIGHT)).addColumn(filepathTextField);
    builder.addRow();
    builder.addColumn(new JLabel("Sheet Name:", JLabel.RIGHT)).addColumn(sheetTextField);
    builder.addRow();
    builder.addColumn(new JLabel("Append To ?", JLabel.RIGHT)).addColumn(appendToCheckBox);
    builder.addRow();
    builder.addColumn(new JLabel("Last Version Only ?", JLabel.RIGHT)).addColumn(lastVersionCheckBox);
    builder.addRow();
    builder.addColumn(new JLabel("Export Dependencies ?", JLabel.RIGHT)).addColumn(exportDependenciesCheckBox);
    builder.addRow();
    builder.addColumn(new JScrollPane(feedbackTextArea));

    builder.addButton(new JButton("Export"));
    builder.addButton(new JButton("Import"));
    builder.build(this);
  }
}
