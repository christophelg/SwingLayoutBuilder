package com.github.christophelg.swinglayoutbuilder;

import javax.swing.JFrame;

public class LayoutBuilderTest {
  public static void main(String args[]) {
    JFrame frame = new JFrame("Calypso Extractor");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setContentPane(new ApplicationGui());

    frame.pack();
    frame.setVisible(true);
  }
}
