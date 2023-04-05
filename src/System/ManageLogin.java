package System;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ManageLogin {

    static JFrame jf;
    JLabel jl_name, jl11, jl21,jl31, jl4,jl_password;
    JPasswordField jpassword;
    JTextField jt_name;
    JPanel jp1, jp2, jp4,jp5,jp3, jp_jb, jp_img,jp_scroll;
    static JPanel jp_center,jp_south;
    JButton jb,jb_revise_teacher;
    static JTable table;
    static JScrollPane scrollPane;
    static DefaultTableModel model;
    JMenuBar jmb;
    JMenuItem jmb1findStudentMessage,jmb2findTeacherMessage,
            jmb1reviseStudentMessage,jmb2revise,jmb0message,jmb1deleteStudent,
            jmb2deleteTeacher,jmb3Courseadd,jmb3work,jmb3duplicate,
            jmb4logout,jmb4exit;

    public ManageLogin(final Manage man) {

        jf = new JFrame("欢迎 " + man.getId() + " 管理员，你好！");
        jf.setSize(1000, 700);
        jf.setLocation(800, 100);
        jf.setLayout(new BorderLayout(0,0));
        Icon img = new ImageIcon("tu2.pn");
        jf.add(new JLabel(img),BorderLayout.NORTH);

        jmb = new JMenuBar();
        jf.setJMenuBar(jmb);
        JMenu jmb0 = new JMenu("我的信息");
        jmb.add(jmb0);
        jmb0message = new JMenuItem("查看和修改");
        jmb0.add(jmb0message);
        JMenu jmb1 = new JMenu("学生管理");
        jmb.add(jmb1);
        jmb1findStudentMessage = new JMenuItem("查看学生信息");
        jmb1.add(jmb1findStudentMessage);
        jmb1reviseStudentMessage = new JMenuItem("修改学生信息");
        jmb1.add(jmb1reviseStudentMessage);
        jmb1deleteStudent = new JMenuItem("删除学生信息");
        jmb1.add(jmb1deleteStudent);

        JMenu jmb2 = new JMenu("老师管理");
        jmb.add(jmb2);
        jmb2findTeacherMessage = new JMenuItem("查看老师信息");
        jmb2.add(jmb2findTeacherMessage);
        jmb2revise = new JMenuItem("修改老师信息");
        jmb2.add(jmb2revise);
        jmb2deleteTeacher = new JMenuItem("删除老师信息");
        jmb2.add(jmb2deleteTeacher);
        JMenu jmb4 = new JMenu("退出");
        jmb.add(jmb4);
        jmb4logout = new JMenuItem("退出登录");
        jmb4.add(jmb4logout);
        jmb4exit = new JMenuItem("退出系统");
        jmb4.add(jmb4exit);





        jp_center = new JPanel();//中心的面板
        jp_south = new JPanel();//下方的面板
        jf.setVisible(true);
        //查看和修改自己信息
        jmb0message.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jp_center.removeAll();
                jp_center.setLayout(new GridLayout(7,2));
                jf.add(jp_center,BorderLayout.CENTER);

                jl_name = new JLabel("姓名");
                jt_name = new JTextField(20);
                jt_name.setText(man.getName());
                jp1 = new JPanel();
                jp1.add(jl_name);
                jp1.add(jt_name);

                jl_password = new JLabel("密码");
                jpassword = new JPasswordField(20);
                jpassword.setText(man.getPassword());
                jp2 = new JPanel();
                jp2.add(jl_password);
                jp2.add(jpassword);

                jl4 = new JLabel("修改成功");
                jp4 = new JPanel();
                jb = new JButton("修改信息");
                jp_jb = new JPanel();
                jp_jb.add(jb);

                jp_center.add(jp1);
                jp_center.add(jp2);
                jp_center.add(jp_jb);
                jp_center.add(jp4);
                jf.setVisible(true);

                jb.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        man.setName(jt_name.getText());
                        man.setPassword(String.valueOf(jpassword.getPassword()));


                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itcast", "root","gaozhenlin233");

                            String SQL = "update users set password = ? , name = ? where id = "+man.getId();
                            //操作数据库
                            PreparedStatement pare = connection.prepareStatement(SQL);
                            pare.setString(1,man.getPassword());
                            pare.setString(2,man.getName());
                            //执行sql语句
                            int rows = pare.executeUpdate();

                            if(rows>0){
                                jf.setVisible(true);

                            }
                            connection.close();
                            pare.close();

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                        JOptionPane.showMessageDialog(null,"修改成功");
                        jf.setVisible(true);
                    }
                });
            }
        });
        //查看学生信息
        jmb1findStudentMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManageLogin.Excel();
                model = (DefaultTableModel) table.getModel();
                model.setColumnIdentifiers(new Object[] { "用户名", "姓名", "年龄","性别" });
                CourseManage.findAllStudentMessage();
                jp_center.add(scrollPane);
                jf.add(jp_center);
                jf.setVisible(true);

            }
        });
        //修改学生信息
        jmb1reviseStudentMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jf = new JFrame("修改学生信息");
                jf.setLocation(1000, 200);
                jf.setSize(400, 300);
                jf.setLayout(new FlowLayout());
                JPanel jp = new JPanel();
                JLabel jl = new JLabel("输入要修改的用户名：");
                JTextField jt_id = new JTextField(15);
                jp.add(jl);
                jp.add(jt_id);
                jf.add(jp);
                jl = new JLabel("用户密码：");
                JPasswordField jt_pass = new JPasswordField(15);
                jp1 = new JPanel();
                jp1.add(jl);
                jp1.add(jt_pass);
                jf.add(jp1);
                JPanel jp2 = new JPanel();
                jl = new JLabel("姓名：");
                JTextField jt_name = new JTextField(15);
                jp2.add(jl);
                jp2.add(jt_name);
                jf.add(jp2);
                jl = new JLabel("年龄：");
                JTextField jt_age = new JTextField(15);
                jp3 = new JPanel();
                jp3.add(jl);
                jp3.add(jt_age);
                jf.add(jp3);
                jl = new JLabel("性别：");
                JTextField jt_sex = new JTextField(15);
                jp4 = new JPanel();
                jp4.add(jl);
                jp4.add(jt_sex);
                jf.add(jp4);
                jb = new JButton("确定修改");
                jp5 = new JPanel();
                jp5.add(jb);
                jf.add(jp5);
                jf.setVisible(true);
                jb.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itcast", "root","gaozhenlin233");

                            String SQL = "update users set  password = ?, name = ?, age = ?, sex = ? where job = '学生' and id = ?";
                            //操作数据库
                            PreparedStatement pare = connection.prepareStatement(SQL);
                            pare.setString(1,String.valueOf(jt_pass.getPassword()));
                            pare.setString(2,jt_name.getText());
                            pare.setInt(3, Integer.parseInt(jt_age.getText()));
                            pare.setString(4,jt_sex.getText());
                            pare.setString(5,jt_id.getText());
                            //执行sql语句
                            int  rs = pare.executeUpdate();
                            if(rs>0)
                            {
                                JOptionPane.showMessageDialog(null,"修改成功");
                            }else {
                                JOptionPane.showMessageDialog(null,"修改失败");
                            }
                            connection.close();
                            pare.close();

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

            }

        });
        //删除学生
        jmb1deleteStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CourseManage.deleteStudent();
            }
        });


        //查看老师信息
        jmb2findTeacherMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                System.out.println("查看所有老师信息");
                ManageLogin.Excel();
                model = (DefaultTableModel) table.getModel();
                model.setColumnIdentifiers(new Object[] { "用户名", "姓名", "年龄", "性别",  });
                CourseManage.findAllTeacherMessage();
                jp_center.add(scrollPane);
                jf.setVisible(true);

            }
        });
        //修改老师信息
        jmb2revise.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jp_center.removeAll();
                JFrame jf1 = new JFrame("修改教师信息");
                jf1.setLocation(1000, 200);
                jf1.setSize(400, 300);
                jf1.setLayout(new FlowLayout());
                JPanel jp = new JPanel();
                JLabel jl = new JLabel("输入要修改的用户名：");
                JTextField jt_id = new JTextField(15);
                jp.add(jl);
                jp.add(jt_id);
                jf1.add(jp);
                jl = new JLabel("用户密码：");
                JPasswordField jt_pass = new JPasswordField(15);
                jp1 = new JPanel();
                jp1.add(jl);
                jp1.add(jt_pass);
                jf1.add(jp1);
                JPanel jp2 = new JPanel();
                jl = new JLabel("姓名：");
                JTextField jt_name = new JTextField(15);
                jp2.add(jl);
                jp2.add(jt_name);
                jf1.add(jp2);
                jl = new JLabel("年龄：");
                JTextField jt_age = new JTextField(15);
                jp3 = new JPanel();
                jp3.add(jl);
                jp3.add(jt_age);
                jf1.add(jp3);
                jl = new JLabel("性别：");
                JTextField jt_sex = new JTextField(15);
                jp4 = new JPanel();
                jp4.add(jl);
                jp4.add(jt_sex);
                jf1.add(jp4);
                jb = new JButton("确定修改");
                jp5 = new JPanel();
                jp5.add(jb);
                jf1.add(jp5);
                jf1.setVisible(true);
                jb.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itcast", "root","gaozhenlin233");

                            String SQL = "update users set  password = ?, name = ?, age = ?, sex = ? where job = '教师' and id = ?";
                            //操作数据库
                            PreparedStatement pare = connection.prepareStatement(SQL);
                            pare.setString(1,String.valueOf(jt_pass.getPassword()));
                            pare.setString(2,jt_name.getText());
                            pare.setInt(3, Integer.parseInt(jt_age.getText()));
                            pare.setString(4,jt_sex.getText());
                            pare.setString(5,jt_id.getText());
                            //执行sql语句
                            int  rs = pare.executeUpdate();
                            if(rs>0)
                            {
                                JOptionPane.showMessageDialog(null,"修改成功");
                            }else {
                                JOptionPane.showMessageDialog(null,"修改失败");
                            }
                            connection.close();
                            pare.close();

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

            }
        });
        //删除老师
        jmb2deleteTeacher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CourseManage.deleteTeacher();
            }
        });
        jmb4logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                jf.setVisible(false);
                jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        jmb4exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    //表格模型
    public static void Excel(){
        jp_center.removeAll();
        jp_south.removeAll();
        jp_center.setLayout(new GridLayout());
        scrollPane = new JScrollPane();
        jp_center.add(scrollPane);
        jf.add(jp_center, BorderLayout.CENTER);
        jf.setVisible(true);
        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        table.setRowHeight(30);
        JTableHeader header = table.getTableHeader(); // 获取 JTable 的头的对象
        header.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        header.setPreferredSize(new Dimension(header.getWidth(), 35)); // 设置此组件的首选大小
        scrollPane.setViewportView(table);
    }

}