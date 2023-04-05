package System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChooseFile extends MouseAdapter {
    private JTextField filePathFild;
    private JFrame frame;
    private FileDialog fileDialog;
    private String filePath;
    private String fileName;

    public ChooseFile(JTextField filePathFild,JFrame frame) {
        this.filePathFild = filePathFild;
        this.frame = frame;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        fileDialog = new FileDialog(frame);
        fileDialog.show();
        filePath = fileDialog.getDirectory();
        fileName = fileDialog.getFile();
        if(filePath == null  || fileName == null){
        }else{
            filePathFild.setText(filePath + fileName);
        }
    }
}