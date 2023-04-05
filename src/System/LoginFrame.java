package System;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;

public class LoginFrame {
    JFrame jFrame;
    JScrollPane jScrollPane;
    JTextArea jtxArea;
    JTextField jTextField;
    JButton jButton;
    JOptionPane jOptionPane;
    String content;
    Student stu;
    public LoginFrame(final Student stu) {
        jFrame = new JFrame("离线留言");
        jFrame.setLayout(new BorderLayout());
        JPanel jPanel = new JPanel();
        jScrollPane = new JScrollPane();//滚动条

        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jtxArea=new JTextArea(2000,1500);
        jtxArea.setFocusable(false);//没有光标显示
        jScrollPane.setViewportView(jtxArea);//将jtxArea放在 jScrollPane里
        jFrame.add(jScrollPane, BorderLayout.CENTER);

        jPanel.setLayout(new FlowLayout());
        jFrame.add(jPanel, BorderLayout.SOUTH);
        jButton=new JButton("发送");
        jTextField=new JTextField(30);
        jTextField.requestFocus();


        jPanel.add(jTextField);
        jPanel.add(jButton);
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itcast", "root","gaozhenlin233");

            String SQL = "select * from message";
            //操作数据库
            PreparedStatement pare = connection.prepareStatement(SQL);
            //执行sql语句
            ResultSet rs = pare.executeQuery();

            while(rs.next()){
                jtxArea.append(rs.getString(4)+" "+rs.getString(2)+"："+rs.getString(3)+"\n");
            }
            connection.close();
            pare.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jTextField.getText().equals("")) {
                    jOptionPane=new JOptionPane();
                    jOptionPane.showMessageDialog(jFrame, "不能为空字符串");
                    jFrame.add(jOptionPane);

                }else {
                    content=jTextField.getText();
                    Calendar calendar= Calendar.getInstance();
                    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
                    jtxArea.append(dateFormat.format(calendar.getTime())+" "+stu.getName()+"："+content+"\n");
                    jTextField.setText("");
                    jTextField.requestFocus();
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itcast", "root","gaozhenlin233");

                        String SQL = "insert into message value(?,?,?,?)";
                        //操作数据库
                        PreparedStatement pare = connection.prepareStatement(SQL);
                        pare.setInt(1,Integer.parseInt(stu.getId()));
                        pare.setString(2,stu.getName());
                        pare.setString(3,content);
                        pare.setString(4,dateFormat.format(calendar.getTime()));
                        //执行sql语句
                        int rs = pare.executeUpdate();

                        if (rs>0){
                            jOptionPane.showMessageDialog(jFrame, "留言成功~");
                        }
                        connection.close();
                        pare.close();

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        jTextField.addKeyListener(new KeyAdapter() {//匿名内部类
            @Override
            public void keyPressed(KeyEvent e) {
                content=jTextField.getText();//获得文本框里的内容
                if(e.getKeyChar()==KeyEvent.VK_ENTER) {//如果点击了回车键
                    if(content!=null && !content.trim().equals("")) {//文本框里的内容不为空
                        Calendar calendar= Calendar.getInstance();
                        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
                        jtxArea.append(dateFormat.format(calendar.getTime())+" "+stu.getName()+"："+content+"\n");//文本域接收文本内容
                        jTextField.setText("");//文本框内容清空
                        jTextField.requestFocus();//文本框显示光标
                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itcast", "root","gaozhenlin233");

                            String SQL = "insert into message value(?,?,?,?)";
                            //操作数据库
                            PreparedStatement pare = connection.prepareStatement(SQL);
                            pare.setInt(1,Integer.parseInt(stu.getId()));
                            pare.setString(2,stu.getName());
                            pare.setString(3,content);
                            pare.setString(4,dateFormat.format(calendar.getTime()));
                            //执行sql语句
                            int rs = pare.executeUpdate();

                            if (rs>0){
                                jOptionPane.showMessageDialog(jFrame, "留言成功~");
                            }
                            connection.close();
                            pare.close();

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }else {
                        jOptionPane.showMessageDialog(jFrame, "不能为空字符串");//会显示一个提示的消息框如下


                        jFrame.add(jOptionPane);
                    }
                }
            }

        });
        jFrame.setSize(500, 400);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(jFrame.DISPOSE_ON_CLOSE);
    }

    }

