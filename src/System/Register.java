package System;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.*;
import java.util.Iterator;

import javax.swing.*;

public class Register {
    JFrame jf;
    JLabel jl_id, jl_password, jl_name, jl_age, jl_sex, jl5;
    JTextField jt_id, jt_password, jt_name, jt_age, jt_sex;
    JPanel jp1, jp2, jp3, jp4, jp5, jp6, jp7, jp_group, jp_img;
    JButton jb1, jb2;
    ButtonGroup group;
    JRadioButton radioButton_student, radioButton_teacher, radioButton_manage;

    public Register() {
        jf = new JFrame("注册界面");
        jf.setSize(500, 700);
        jf.setLocation(500, 100);
        jf.setVisible(true);
        jf.setLayout(new FlowLayout());

        Icon img = new ImageIcon("tu1.jpeg");
        jp_img = new JPanel();
        jp_img.add(new JLabel(img));

        jl_id = new JLabel("用户名");
        jt_id = new JTextField(15);
        jp1 = new JPanel();
        jp1.add(jl_id);
        jp1.add(jt_id);
        jl_password = new JLabel("密码");
        jt_password = new JPasswordField(15);
        jp2 = new JPanel();
        jp2.add(jl_password);
        jp2.add(jt_password);
        jl_name = new JLabel("姓名");
        jt_name = new JTextField(15);
        jp3 = new JPanel();
        jp3.add(jl_name);
        jp3.add(jt_name);
        jl_age = new JLabel("年龄");
        jt_age = new JTextField(15);
        jp4 = new JPanel();
        jp4.add(jl_age);
        jp4.add(jt_age);
        jl_sex = new JLabel("性别");
        jt_sex = new JTextField(15);
        jp5 = new JPanel();
        jp5.add(jl_sex);
        jp5.add(jt_sex);


        jp_group = new JPanel();
        radioButton_student = new JRadioButton("学生");
        jp_group.add(radioButton_student);
        radioButton_teacher = new JRadioButton("老师");
        jp_group.add(radioButton_teacher);
        radioButton_manage = new JRadioButton("管理员");
        jp_group.add(radioButton_manage);
        group = new ButtonGroup();
        group.add(radioButton_student);
        group.add(radioButton_teacher);
        group.add(radioButton_manage);

        jb1 = new JButton("完成");
        jp6 = new JPanel();
        jp6.add(jb1);
        jl5 = new JLabel("注册成功");
        jp7 = new JPanel();
        jb2 = new JButton("返回登录界面");
        jp6.add(jb2);
        jf.add(jp_img);
        jf.add(jp1);
        jf.add(jp2);
        jf.add(jp3);
        jf.add(jp4);
        jf.add(jp5);
        jf.add(jp_group);
        jf.add(jp6);
        jf.add(jp7);
        jb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //学生
                if (radioButton_student.isSelected()) {
                    boolean flow = true;
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itcast", "root", "gaozhenlin233");

                        String SQL = "select * from users where id = " + jt_id.getText();
                        //操作数据库
                        PreparedStatement pare = connection.prepareStatement(SQL);
                        //执行sql语句
                        ResultSet rs = pare.executeQuery();

                        if (rs.next()) {
                            JOptionPane.showMessageDialog(null,
                                    "此用户名已存在，请重新输入");
                            jf.setVisible(true);
                        } else {
                            flow = false;
                        }
                        connection.close();
                        pare.close();

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }


                    if (flow == false) {
                        if (!(jt_id.getText().isEmpty() || jt_password
                                .getText().isEmpty())) {

                            Student stu = new Student(jt_id.getText(), jt_password
                                    .getText(), jt_name.getText(), Integer.parseInt(jt_age.getText()), jt_sex.getText());

                            try {
                                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itcast", "root", "gaozhenlin233");

                                String SQL = "insert into users value (?,?,?,?,?,'学生')";
                                //操作数据
                                PreparedStatement pare = connection.prepareStatement(SQL);
                                pare.setString(1, jt_id.getText());
                                pare.setString(2, jt_password.getText());
                                pare.setString(3, jt_name.getText());
                                pare.setInt(4, Integer.parseInt(jt_age.getText()));
                                pare.setString(5, jt_sex.getText());
                                //执行sql语句
                                int rows = pare.executeUpdate();

                                if (rows > 0) {
                                    JOptionPane.showMessageDialog(null,
                                            "添加成功");
                                    jf.setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "添加失败");
                                    jf.setVisible(true);
                                }
                                connection.close();
                                pare.close();

                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                            jt_id.setText("");
                            jt_password.setText("");
                            jt_sex.setText("");
                            jt_name.setText("");
                            jt_age.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "密码，用户名不能为空");
                        }
                    }
                }

                jb2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new Login();
                        jf.dispose();
                    }
                });
            }
        });

    }
    //                    if (IOStreamStudent.file.length() == 0) {
//                        DB.arrStu.add(stu);
//                        IOStreamStudent.writeToFile();
//                        jp6.add(jl5);
//                        jf.setVisible(true);
//                        jt_id.setText("");
//                        jt_password.setText("");
//                    } else {
//                        DB.arrStu.clear();
//                        DB.arrStu = IOStreamStudent.readFromFile();
//                        Iterator<Student> it = DB.arrStu.iterator();
//                        boolean flag = true;
//                        while (it.hasNext()) {
//                            Student newstu = it.next();
//                            if (newstu.getId().equals(jt_id.getText())) {
//                                flag = false;
//                                JOptionPane.showMessageDialog(null,
//                                        "此用户名已存在，请重新输入");
//                            }
//                        }
//                        if (flag) {
//                            if (!(jt_id.getText().isEmpty() || jt_password
//                                    .getText().isEmpty())) {
//                                DB.arrStu.add(stu);
//                                IOStreamStudent.writeToFile();
//                                jp6.add(jl5);
//                                jf.setVisible(true);
//                                jt_id.setText("");
//                                jt_password.setText("");
//                            } else {
//                                JOptionPane.showMessageDialog(null,
//                                        "密码，用户名不能为空");
//                            }
//                        }
//                    }
//                }
    //老师
//                if (radioButton_teacher.isSelected()) {
//                    Teacher tea = new Teacher(jt_id.getText(), jt_password
//                            .getText());
//                    if (IOStreamTeacher.file.length() == 0) {
//                        DB.arrTea.add(tea);
//                        IOStreamTeacher.writeToFile();
//                        jp6.add(jl5);
//                        jf.setVisible(true);
//                        jt_id.setText("");
//                        jt_password.setText("");
//                    } else {
//                        DB.arrTea.clear();
//                        DB.arrTea = IOStreamTeacher.readFromFile();
//                        Iterator<Teacher> it = DB.arrTea.iterator();
//                        boolean flag = true;
//                        while (it.hasNext()) {
//                            Teacher newtea = it.next();
//                            if (newtea.getId().equals(jt_id.getText())) {
//                                flag = false;
//                                JOptionPane.showMessageDialog(null,
//                                        "此用户名已存在，请重新输入");
//                            }
//                        }
//                        if (flag) {
//                            if (!(jt_id.getText().isEmpty() || jt_password
//                                    .getText().isEmpty())) {
//                                DB.arrTea.add(tea);
//                                IOStreamTeacher.writeToFile();
//                                jp6.add(jl5);
//                                jf.setVisible(true);
//                                jt_id.setText("");
//                                jt_password.setText("");
//                            } else {
//                                JOptionPane.showMessageDialog(null,
//                                        "密码，用户名不能为空");
//                            }
//
//                        }
//                    }
//
//                }
    //管理员
//                if (radioButton_manage.isSelected()) {
//                    Manage man = new Manage(jt_id.getText(), jt_password
//                            .getText());
//                    if (IOStreamManage.file.length() == 0) {
//                        DB.arrMan.add(man);
//                        IOStreamManage.writeToFile();
//                        jp6.add(jl5);
//                        jf.setVisible(true);
//                        jt_id.setText("");
//                        jt_password.setText("");
//                    } else {
//                        DB.arrMan.clear();
//                        DB.arrMan = IOStreamManage.readFromFile();
//                        Iterator<Manage> it = DB.arrMan.iterator();
//                        boolean flag = true;
//                        while (it.hasNext()) {
//                            Manage newman = it.next();
//                            if (newman.getId().equals(jt_id.getText())) {
//                                flag = false;
//                                JOptionPane.showMessageDialog(null,
//                                        "此用户名已存在，请重新输入");
//                            }
//                        }
//                        if (flag) {
//                            if (!(jt_id.getText().isEmpty() || jt_password
//                                    .getText().isEmpty())) {
//                                DB.arrMan.add(man);
//                                IOStreamManage.writeToFile();
//                                jp6.add(jl5);
//                                jf.setVisible(true);
//                                jt_id.setText("");
//                                jt_password.setText("");
//                            } else {
//                                JOptionPane.showMessageDialog(null,
//                                        "密码，用户名不能为空");
//                            }
//
//                        }
//                    }
//
//                }
//            }

//        });


//    }

}

