package me.eberli;

import javafx.scene.control.TextArea;

/**
 *
 * @author Andreas
 */
public class ConsoleLog {
    public static TextArea outputLog;
    
    public static void printEvent(String newEntry){
        outputLog.setText(outputLog.getText() + newEntry + System.lineSeparator());
    }
}
