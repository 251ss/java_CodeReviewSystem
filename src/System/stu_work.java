package System;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;


public class stu_work 
{
    JFrame jf = new JFrame();
    JTextArea textshow= new JTextArea();
    JMenuBar menubar = new JMenuBar();					//菜单条
    JMenu menu = new JMenu("保存文件");							//菜单
    JMenuItem itemSave = new JMenuItem("保存文件");		//菜单项


    public stu_work(Student stu,String Qid)
    {
        //初始化菜单

        menu .setFont(new Font("楷体_gb2312" ,  Font.PLAIN , 20));
        menu.setBackground(Color.blue);

        //初始化菜单项
        itemSave .setFont(new Font("楷体_gb2312" , Font.PLAIN , 17));
        //添加快捷键
        itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S , InputEvent.CTRL_MASK));



        //初始化文本区
        textshow.setFont(new Font("楷体_gb2312" , Font.PLAIN , 15));
        textshow.setForeground(Color.BLACK);


        //将 菜单项 添加到菜单
        menu.add(itemSave);
        //将 菜单 添加到 菜单条 上
        menubar.add(menu);


        //将菜单条加到窗口上
        jf.setJMenuBar(menubar);

        //将文本区 添加到窗体
        JScrollPane cen_pan = new JScrollPane(textshow);

        //注册监视器
        itemSave.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e){
            String str = textshow.getText();

            if (str.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "请输入要保存的内容！！！",null ,JOptionPane.WARNING_MESSAGE);
                return;
            }
                JFileChooser fileChoose = new JFileChooser();		//创建文件对话框
                fileChoose.setFileFilter(new FileNameExtensionFilter("java文件" , "java"));

                int result = fileChoose.showSaveDialog(jf);		//显示文件对话框
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = new File(fileChoose.getSelectedFile().getAbsolutePath());//获取选择的文件
                        FileOutputStream file_write = new FileOutputStream(file);            //创建文件字节输出流
                        file_write.write(str.getBytes());
                        file_write.close();

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itcast", "root", "gaozhenlin233");

                    String SQL = "insert into task value(?,?,?,?,?)";
                    //操作数据库
                    Calendar calendar= Calendar.getInstance();
                    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
                    PreparedStatement pare = connection.prepareStatement(SQL);
                    pare.setString(1,stu.getId());
                    pare.setString(2,stu.getName());
                    pare.setString(3,Qid);
                    pare.setString(4,dateFormat.format(calendar.getTime()));
                    pare.setString(5,str);
                    //执行sql语句
                    int rs = pare.executeUpdate();
                    if(rs>0){
                        JOptionPane.showMessageDialog(null,"上传成功");
                        jf.dispose();
                    }else {
                        JOptionPane.showMessageDialog(null,"上传失败");
                        jf.dispose();
                    }

                    connection.close();
                    pare.close();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }});
        jf.add(cen_pan);
        //窗口属性设置
        jf.setTitle("题目作答");
        jf.setVisible(true);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setSize(500, 600);
            }
        }