package com.github.christophelg.swinglayoutbuilder;

import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.JTextArea;

/**
 * This JTextArea will add itself to the root logger so that any LogRecord be captured.<br/>
 * It will use the ConsoleHandler for its output definition Usage:<br/>
 * 
 * <pre>
 * JTextArea feedbackTextArea = new JTextAreaLogger(20, 20);
 * JScrollPane scrollPane = new JScrollPane(feedbackTextArea);
 * builder.add(scrollPane);
 * </pre>
 * 
 * @author 833797
 * 
 */
public class JTextAreaLogger extends JTextArea {

  private static final long serialVersionUID = 1L;

  private ConsoleHandler internalHandler = new ConsoleHandler() {

    public void publish(LogRecord record) {
      String msg = getFormatter().format(record);
      append(msg);
    }
  };

  public JTextAreaLogger(int rows, int cols) {
    super(rows, cols);
    Logger rootLogger = Logger.getLogger("");
    rootLogger.addHandler(internalHandler);
  }
}
