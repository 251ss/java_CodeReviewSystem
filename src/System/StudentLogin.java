package System;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentLogin {

    JFrame jf;
    JLabel jl_name, jl11, jl_age, jl21, jl_sex, jl31, jl_ReviseOK, jl_old_password, jl_new_password1, jl_new_password2, jl_inpassword_err,jl_Revisefile;
    JTextField jt_name, jt_age, jt_sex;
    JPasswordField jp_old_password, jpassword1, jpassword2;
    JPanel jp1, jp2, jp3, jp4, jp_jb, jp_img, jp_scroll, jp_center;
    JButton jb, jb_revise_password;
    static JTable table;
    JScrollPane scrollPane;
    static DefaultTableModel model;
    JMenuBar jmb;
    JMenuItem jmb1find, jmb2find, jmb1revisePassword, jmb3working,  jmb3look,jmb4communicate, jmb4message, jmb5logout, jmb5exit;


    public StudentLogin(final Student stu) {

        jf = new JFrame("欢迎 " + stu.getId() + " 同学，你好！");
        jf.setSize(1000, 700);
        jf.setLocation(800, 100);
        jf.setLayout(new BorderLayout(0, 0));
        Icon img = new ImageIcon("tu2.jpg");
        jf.add(new JLabel(img), BorderLayout.NORTH);

        jmb = new JMenuBar();
        jf.setJMenuBar(jmb);
        JMenu jmb1 = new JMenu("个人信息");
        jmb.add(jmb1);
        jmb1find = new JMenuItem("查看个人信息");
        jmb1.add(jmb1find);
        jmb1revisePassword = new JMenuItem("修改账户密码");
        jmb1.add(jmb1revisePassword);
        JMenu jmb2 = new JMenu("我的课程");
        jmb.add(jmb2);
        jmb2find = new JMenuItem("查看所有");
        jmb2.add(jmb2find);
        JMenu jmb3 = new JMenu("课程作业");
        jmb.add(jmb3);
        jmb3look = new JMenuItem("查看题目");
        jmb3.add(jmb3look);
        jmb3working = new JMenuItem("题目作答");
        jmb3.add(jmb3working);


        JMenu jmb4 = new JMenu("师生交流");
        jmb.add(jmb4);
        jmb4message = new JMenuItem("离线留言");
        jmb4.add(jmb4message);
        jmb4communicate = new JMenuItem("在线沟通");
        jmb4.add(jmb4communicate);


        JMenu jmb5 = new JMenu("退出");
        jmb.add(jmb5);
        jmb5logout = new JMenuItem("退出登录");
        jmb5.add(jmb5logout);
        jmb5exit = new JMenuItem("退出系统");
        jmb5.add(jmb5exit);

        jp_center = new JPanel();//中心的面板
        jf.setVisible(true);


        jmb1find.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jp_center.removeAll();
                jp_center.setLayout(new GridLayout(7,2));
                jf.add(jp_center,BorderLayout.CENTER);

                jl_name = new JLabel("姓名");
                jt_name = new JTextField(20);
                jt_name.setText(stu.getName());
                jp1 = new JPanel();
                jp1.add(jl_name);
                jp1.add(jt_name);
                jl_age = new JLabel("年龄");
                jt_age = new JTextField(20);
                jt_age.setText(String.valueOf(stu.getAge()));
                jp2 = new JPanel();
                jp2.add(jl_age);
                jp2.add(jt_age);
                jl_sex = new JLabel("性别");
                jt_sex = new JTextField(20);
                jt_sex.setText(stu.getSex());
                jp3 = new JPanel();
                jp3.add(jl_sex);
                jp3.add(jt_sex);
                jl_Revisefile = new JLabel("修改失败，请重新尝试");
                jl_ReviseOK = new JLabel("修改成功");
                jp4 = new JPanel();
                jb = new JButton("修改信息");
                jp_jb = new JPanel();
                jp_jb.add(jb);

                jp_center.add(jp1);
                jp_center.add(jp2);
                jp_center.add(jp3);
                jp_center.add(jp_jb);
                jp_center.add(jp4);
                jf.setVisible(true);

                jb.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                            try {
                                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itcast", "root","gaozhenlin233");

                                String SQL = "update users set name = ? , age = ? , sex = ? where id = "+stu.getId();
                                //操作数据库
                                PreparedStatement pare = connection.prepareStatement(SQL);
                                pare.setString(1, jt_name.getText());
                                pare.setInt(2,Integer.valueOf(jt_age.getText()));
                                pare.setString(3, jt_sex.getText());
                                //执行sql语句
                                int rows = pare.executeUpdate();

                                if(rows>0){
                                    jp4.add(jl_ReviseOK);
                                    JOptionPane.showMessageDialog(null,"修改成功");
                                    jt_name.setText(jt_name.getText());
                                    jt_age.setText(jt_age.getText());
                                    jt_sex.setText(jt_sex.getText());
                                }else{
                                    jp4.add(jl_Revisefile);
                                    JOptionPane.showMessageDialog(null,"修改失败");
                                }
                                connection.close();
                                pare.close();

                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }

                        jf.setVisible(true);
                    }
                });
            }
        });
        jmb1revisePassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jp_center.removeAll();
                jp_center.setLayout(new GridLayout(6,2));
                jf.add(jp_center, BorderLayout.CENTER);
                jl_old_password = new JLabel("原密码");
                jp_old_password = new JPasswordField(20);
                jl_new_password1 = new JLabel("新密码");
                jpassword1 = new JPasswordField(20);
                jl_new_password2 = new JLabel("再次输入新密码");
                jpassword2 = new JPasswordField(20);
                jb_revise_password = new JButton("保存");
                jl_inpassword_err = new JLabel("密码输入错误，请重新输入");
                jl_inpassword_err.setForeground(Color.red);
                jl_inpassword_err.setFont(new Font("宋体",Font.ITALIC,16));
                JPanel jp1 = new JPanel();
                JPanel jp2 = new JPanel();
                JPanel jp3 = new JPanel();
                final JPanel jp4 = new JPanel();
                final JPanel jp5 = new JPanel();
                JPanel jp_jb = new JPanel();
                jp1.add(jl_old_password);
                jp1.add(jp_old_password);
                jp_center.add(jp1);
                jp2.add(jl_new_password1);
                jp2.add(jpassword1);
                jp_center.add(jp2);
                jp3.add(jl_new_password2);
                jp3.add(jpassword2);
                jp_center.add(jp3);
                jp_center.add(jp4);
                jp_jb.add(jb_revise_password);
                jp_center.add(jp_jb);
                jp_center.add(jp5);
                jf.setVisible(true);


                jb_revise_password.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {


                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itcast", "root","gaozhenlin233");

                            String SQL = "update users set password = ? where id = "+stu.getId();
                            //操作数据库
                            PreparedStatement pare = connection.prepareStatement(SQL);
                            pare.setString(1, String.valueOf(jpassword1.getPassword()));
                            //执行sql语句
                            int rows = pare.executeUpdate();

                            if(rows>0){
                                jp5.removeAll();
                                jp5.add(new JLabel("修改密码成功"));
                                jf.setVisible(true);
                            } else {
                                jp4.removeAll();
                                jp4.add(jl_inpassword_err);
                                jf.setVisible(true);
                            }
                            connection.close();
                            pare.close();

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

//                        if (String.valueOf(jp_old_password.getPassword()).equals(stu.getPassword())
//                                && (!(String.valueOf(jpassword2.getPassword())).equals(""))
//                                && ((String.valueOf(jpassword1.getPassword())).equals(String.valueOf(jpassword2.getPassword())))) {
//                            stu.setPassword(String.valueOf(jpassword2.getPassword()));
//                            Student newstu = stu;
//                            DB.arrStu.clear();
//                            DB.arrStu = IOStreamStudent.readFromFile();
//                            DB.arrStu.set(Login.searchStudent(), newstu);
//                            IOStreamStudent.writeToFile();
//                            jp5.removeAll();
//                            jp5.add(new JLabel("修改密码成功"));
//                            jf.setVisible(true);
//                        } else {
//                            jp4.removeAll();
//                            jp4.add(jl_inpassword_err);
//                            jf.setVisible(true);
//                        }
                    }
                });
            }
        });

        jmb2find.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jp_center.removeAll();
                jp_center.setLayout(new FlowLayout());
                //2,添加table
                JTable table = null;
                String[] index = {"课程编号", "课程名", "成绩", "任课老师"};
                Object[][] data = new Object[2][index.length];
                //3,向data中添加数据
                data[0][0] = "0001";
                data[0][1] = "JAVA程序设计方法";
                data[0][2] = "100";
                data[0][3] = "马军霞";
                data[1][0] = "0002";
                data[1][1] = "JAVA程序设计方法实训";
                data[1][2] = "99";
                data[1][3] = "王文冰";

                //4,创建一个默认的表格模型
                DefaultTableModel defaultModel = new DefaultTableModel(data, index);
                table = new JTable(defaultModel);
                table.setPreferredScrollableViewportSize(new Dimension(980, 700));//JTable的高度和宽度按照设定
                table.setFillsViewportHeight(true);

                //5，给表格设置滚动条
                JScrollPane jScrollPane = new JScrollPane();
                jScrollPane.setViewportView(table);
                jp_center.add(jScrollPane, BorderLayout.CENTER);
                Font font = new Font("宋体", Font.BOLD, 16);
                table.setFont(font);
                table.setRowHeight(30);

                jf.add(jp_center);
//                //添加button
//                JButton button = new JButton("查询");
//                button.setBounds(50, 10, 200, 100);
//
//                //添加label
//                JLabel label = new JLabel("点击按钮，查询课程数据：");
//                label.setFont(font);
//                label.setBounds(1, 10, 300, 200);
//
//                JPanel panel = new JPanel();
//                panel.setBackground(Color.GRAY);
//                panel.setSize(200, 100);
//                panel.add(label);
//                panel.add(button);
//                jp_center.add(panel);
//                JPanel panel1 = new JPanel();
//                button.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        panel1.add(jScrollPane, BorderLayout.CENTER);
//                        panel1.setVisible(true);
//
//                    }
//                });
//                panel1.setLayout(new GridLayout());
//                scrollPane = new JScrollPane();
//                panel.add(scrollPane);
//                jp_center.add(panel1);
//
//                table = new JTable();
//                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//                table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
//                table.setRowHeight(30);
//                JTableHeader header = table.getTableHeader(); // 获取 JTable 的头的对象
//                header.setFont(new Font("微软雅黑", Font.PLAIN, 16));
//                header.setPreferredSize(new Dimension(header.getWidth(), 35)); // 设置此组件的首选大小
//                scrollPane.setViewportView(table);
//                model = (DefaultTableModel) table.getModel();
//                model.setColumnIdentifiers(new Object[]{"课程编号", "课程名", "成绩", "任课老师"});
//                model.setColumnIdentifiers(new Object[]{"0001", "JAVA程序设计方法", "100", "马军霞"});
//                CourseManage.findOneStudentAllCourseMark(stu);




//                //1，设定窗口
//                JFrame frame =new JFrame("您的课程~");
//                frame.setLocation(500,200);
//                frame.setSize(1000, 600);
//                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//
//
//                //2,添加table
//                JTable table =null;
//                String [] index={"课程编号", "课程名", "成绩","任课老师" };
//                Object [][] data=new Object[2][index.length];
//                //3,向data中添加数据
//                data[0][0]="0001";
//                data[0][1]="JAVA程序设计方法";
//                data[0][2]="100";
//                data[0][3]="马军霞";
//                data[1][0]="0002";
//                data[1][1]="JAVA程序设计方法实训";
//                data[1][2]="99";
//                data[1][3]="王文冰";
//
//                //4,创建一个默认的表格模型
//                DefaultTableModel defaultModel = new DefaultTableModel(data, index);
//                table=new JTable(defaultModel);
//                table.setBackground(Color.cyan);
//                table.setPreferredScrollableViewportSize(new Dimension(250, 150));//JTable的高度和宽度按照设定
//                table.setFillsViewportHeight(true);
//
//                //5，给表格设置滚动条
//                JScrollPane jScrollPane = new JScrollPane();
//                jScrollPane.setViewportView(table);
//
//                Font font = new Font("宋体", Font.BOLD, 16);
//
//                //添加button
//                JButton button =new JButton("查询");
//                button.setBounds(50,10,200,100);
//
//                //添加label
//                JLabel label =new JLabel("点击按钮，查询课程数据：");
//                label.setFont(font);
//                label.setBounds(1,10,300,200);
//
//                //通过panel组合button，label
//                JPanel panel =new JPanel();
//                panel.setBackground(Color.GRAY);
//                panel.setSize(200,100);
//                panel.add(label);
//                panel.add(button);
//
//                //6，添加表格、滚动条到容器中
//                frame.add(panel, BorderLayout.NORTH);
//                frame.setVisible(true);
//
//
//                button.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        frame.add(jScrollPane,BorderLayout.CENTER);
//                        frame.setVisible(true);
//
//                    }
//                });
//
//                System.out.println("查看所有课程");
//                jp_center.removeAll();
//                jp_center.setLayout(new GridLayout());
//                scrollPane = new JScrollPane();
//                jp_center.add(scrollPane);
//                jf.add(jp_center, BorderLayout.CENTER);
//
//                table = new JTable();
//                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//                table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
//                table.setRowHeight(30);
//                JTableHeader header = table.getTableHeader(); // 获取 JTable 的头的对象
//                header.setFont(new Font("微软雅黑", Font.PLAIN, 16));
//                header.setPreferredSize(new Dimension(header.getWidth(), 35)); // 设置此组件的首选大小
//                scrollPane.setViewportView(table);
//                model = (DefaultTableModel) table.getModel();
//                model.setColumnIdentifiers(new Object[] { "课程编号", "课程名", "成绩","任课老师" });
//                model.setColumnIdentifiers(new Object[] { "0001", "JAVA程序设计方法", "100","马军霞" });
//                CourseManage.findOneStudentAllCourseMark(stu);
//                jf.setVisible(true);
//
//            }}
            }
        });


        jmb3look.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jp_center.removeAll();
                jp_center.setLayout(new FlowLayout());
                JPanel jPanel = new JPanel();
                JScrollPane jScrollPane;
                JTextArea jtxArea;
                jScrollPane = new JScrollPane();//滚动条

                jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                jtxArea=new JTextArea("题目列表如下：",38,80);
                jtxArea.setFocusable(false);//没有光标显示
                jScrollPane.setViewportView(jtxArea);//将jtxArea放在 jScrollPane里
                jp_center.add(jScrollPane);
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itcast", "root","gaozhenlin233");

                    String SQL = "select * from question";
                    //操作数据库
                    PreparedStatement pare = connection.prepareStatement(SQL);
                    //执行sql语句
                    ResultSet rs = pare.executeQuery();

                    jtxArea.append("\n");
                    while(rs.next()){
                        jtxArea.append("题目"+rs.getString(1)+"       题目内容："+rs.getString(2)+"\n\n");
                    }

                    jf.setVisible(true);
                    connection.close();
                    pare.close();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


                jPanel.setLayout(new FlowLayout());
                jp_center.add(jPanel, BorderLayout.SOUTH);
                jf.add(jp_center);
                jf.setVisible(true);

            }
        });

        jmb3working.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jp_center.removeAll();
                jp_center.setLayout(new FlowLayout());
                JPanel jPanel1= new JPanel();
                jPanel1.setLayout(new GridLayout());
                JLabel jl_answer = new JLabel("请输入您想要作答的题目编号：");
                JTextField jt_answer = new JTextField();
                JButton jb_answer = new JButton("查询");
                jPanel1.add(jl_answer);
                jPanel1.add(jt_answer);
                jPanel1.add(jb_answer);
                jp_center.add(jPanel1);
                JPanel jPanel = new JPanel();
                JScrollPane jScrollPane;
                JTextArea jtxArea;
                jScrollPane = new JScrollPane();//滚动条

                jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                jtxArea=new JTextArea("您的题目：",10,60);
                jtxArea.setFocusable(false);//没有光标显示
                jScrollPane.setViewportView(jtxArea);//将jtxArea放在 jScrollPane里

                jb_answer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itcast", "root","gaozhenlin233");

                            String SQL = "select * from question where Qid = ?";
                            //操作数据库
                            PreparedStatement pare = connection.prepareStatement(SQL);
                            pare.setString(1,jt_answer.getText());
                            //执行sql语句
                            ResultSet rs = pare.executeQuery();

                            jtxArea.append("\n");
                            while(rs.next()){
                                jtxArea.append("您的题目："+rs.getString(2));
                            }

                            connection.close();
                            pare.close();

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });


                jPanel.setLayout(new FlowLayout());
                jp_center.add(jPanel, BorderLayout.SOUTH);
                JPanel jPanel2 = new JPanel(new GridLayout());
                JButton jb_choose=new JButton("题目作答");
                JTextField jTextField=new JTextField(30);
                jPanel2.add(jb_choose);

                jp_center.add(jScrollPane);
                jp_center.add(jPanel2);

                jf.add(jp_center);
                jb_choose.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String Qid = jt_answer.getText();
                        new stu_work(stu,Qid);

                    }


                });

            }
        });



        jmb5logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                jf.setVisible(false);
                jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        jmb4message.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jp_center.removeAll();
                jp_center.setVisible(false);
                new LoginFrame(stu);
            }
        });
        jmb5exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });



    }


}