package com.github.christophelg.swinglayoutbuilder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


/**
 * Helper that will construct a GUI via a simple interface. <br/>
 * Typical usage will be: <br/>
 * 
 * <pre>
 public class MyProgram {
  
  public static void main(String args[]) {
    JFrame frame = new JFrame("Calypso Extractor");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setContentPane(new ApplicationGui());

    frame.pack();
    frame.setVisible(true);
  }
 }
 
  public class ApplicationGui extends JPanel {
	  private static final long serialVersionUID           = 7999442596780544357L;

	  private JTextField        envTextField               = new JTextField(20);
	  private JTextField        asOfDateTextField          = new JTextField();
	  private JComboBox         dataTypeComboBox           = new JComboBox();
	  private JTextField        filepathTextField          = new JTextField(20);
	  private JTextField        criteriaTextField          = new JTextField(20);
	  private JTextField        sheetTextField             = new JTextField(20);
	  private JCheckBox         appendToCheckBox           = new JCheckBox();
	  private JCheckBox         lastVersionCheckBox        = new JCheckBox();
	  private JCheckBox         exportDependenciesCheckBox = new JCheckBox();
	  private JTextArea         feedbackTextArea           = new JTextArea(20, 30);
	
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
	    builder.addColumn(feedbackTextArea);
	
	    builder.addButton(new JButton("Export"));
	    builder.build(this);
	  }
  } 
  
 * </pre>
 * 
 * @author 833797
 * 
 */
public class LayoutBuilder {
  private List<List<Component>> layout;
  private List<Component> currentRow;
  private List<JButton> buttons;
  private Border layoutBorder;

  private int hGap = 2;
  private int vGap = 2;

  public LayoutBuilder() {
    layout = new ArrayList<List<Component>>();
    buttons = new ArrayList<JButton>();
    currentRow = new ArrayList<Component>();
  }

  public LayoutBuilder setGaps(int h, int v) {
    hGap = h;
    vGap = v;
    return this;
  }

  public LayoutBuilder addBorder(Border border) {
    layoutBorder = border;
    return this;
  }

  public LayoutBuilder addRow() {
    // Don't add empty row
    if (currentRow.size() > 0) {
      layout.add(currentRow);
      currentRow = new ArrayList<Component>();
    }
    return this;
  }

  public LayoutBuilder addColumn(Component component) {
    currentRow.add(component);
    return this;
  }

  public LayoutBuilder addColumn(Component... components) {
    Container c = new Container();
    c.setLayout(new BoxLayout(c, BoxLayout.X_AXIS));
    for (Component component : components) {
      c.add(component);
    }
    currentRow.add(c);
    return this;
  }

  public LayoutBuilder addButton(JButton button) {
    buttons.add(button);
    return this;
  }

  public LayoutBuilder addButtons(JButton... buttons) {
    for (JButton button : buttons) {
      addButton(button);
    }
    return this;
  }

  public void build(JComponent pane) {
    // Add what is in the current row to the layout
    addRow();

    if (layoutBorder != null) {
      pane.setBorder(layoutBorder);
    }

    pane.setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.insets = new Insets(hGap, vGap, hGap, vGap);

    int maxColumns = computeMaxColCount();
    for (int r = 0; r < layout.size(); ++r) {
      // we are setting this row
      constraints.gridy = r;

      List<Component> row = layout.get(r);
      for (int c = 0; c < row.size() - 1; ++c) {
        Component component = row.get(c);
        constraints.weightx = isComponentFixedSize(component) ? 0 : 1;
        constraints.gridx = c;
        constraints.gridwidth = 1;
        pane.add(component, constraints);
      }
      // last column must fill all the space left over
      Component lastComponent = row.get(row.size() - 1);
      constraints.weightx = isComponentFixedSize(lastComponent) ? 0 : 1;
      constraints.gridx = row.size() - 1;
      constraints.gridwidth = maxColumns - row.size() + 1;
      pane.add(lastComponent, constraints);
    }

    // now deal with the buttons
    constraints.weightx = 1;
    constraints.gridx = 0;
    constraints.gridy++;
    constraints.gridwidth = maxColumns;
    pane.add(buildButtonRow(), constraints);
  }

  public JComponent build() {
    JComponent toReturn = new JPanel();
    build(toReturn);
    return toReturn;
  }

  private int computeMaxColCount() {
    int toReturn = 0;
    for (List<Component> row : layout) {
      toReturn = Math.max(toReturn, row.size());
    }
    return toReturn;
  }

  private Component buildButtonRow() {
    Container b = new Container();
    b.setLayout(new GridLayout(1, 0, hGap, vGap));
    for (JButton button : buttons) {
      b.add(button);
    }
    Container c = new Container();
    c.setLayout(new BorderLayout(hGap, vGap));
    c.add(b, BorderLayout.LINE_END);
    return c;
  }

  private boolean isComponentFixedSize(Component c) {
    return c instanceof JLabel || c instanceof JButton;
  }

}
