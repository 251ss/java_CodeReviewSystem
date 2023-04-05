package System;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class TeacherLogin {

    static JFrame jf;
    JLabel jl_name,jl11,jl_age,jl21,jl_sex,jl31,jl4,jl5,jl_course,jl_old_password,jl_new_password1,jl_new_password2,jl_inpassword_err;
    JTextField jt_name,jt_age,jt_sex;
    JPasswordField jp_old_password,jpassword1,jpassword2;
    JPanel jp1,jp2,jp3,jp4,jp_jb;
    static JPanel jp_center,jp_south;
    JPanel jp_course;
    JButton jb,jb_revise,jb_revise_password;
    static JScrollPane scrollPane;
    static JTable table;
    static DefaultTableModel model;
    JMenuBar jmb;
    JMenuItem jmb1find,jmb2find,jmb2revise,jmb1revisePassword,jmb3look,jmb3work,jmb3duplicate,jmb4message,jmb4communicate,jmb5logout,jmb5exit;
    JMenu jmb1,jmb2;

    public TeacherLogin(){}

    public TeacherLogin(final Teacher tea){

        jf = new JFrame(tea.getId()+" 老师，您好！");
        jf.setSize(1000,700);
        jf.setLocation(800,100);
        jf.setLayout(new BorderLayout(0,0));
        Icon img = new ImageIcon("tu2.jpg");
        JLabel jl = new JLabel(img);
        jf.add(jl,BorderLayout.NORTH);

        jmb = new JMenuBar();
        jf.setJMenuBar(jmb);
        JMenu jmb1 = new JMenu("个人信息");
        jmb.add(jmb1);
        jmb1find = new JMenuItem("查看个人信息");
        jmb1.add(jmb1find);
        jmb1revisePassword = new JMenuItem("修改账户密码");
        jmb1.add(jmb1revisePassword);
        JMenu jmb2 = new JMenu("学生成绩");
        jmb.add(jmb2);
        jmb2find = new JMenuItem("查看所有");
        jmb2.add(jmb2find);
        JMenu jmb3 = new JMenu("课程管理");
        jmb.add(jmb3);
        jmb3look = new JMenuItem("查看作业提交");
        jmb3.add(jmb3look);
        jmb3work = new JMenuItem("查找详细作业");
        jmb3.add(jmb3work);
        jmb3duplicate = new JMenuItem("作业查重");
        jmb3.add(jmb3duplicate);

        JMenu jmb4 = new JMenu("师生交流");
        jmb.add(jmb4);
        jmb4message = new JMenuItem("查看留言");
        jmb4.add(jmb4message);
        jmb4communicate = new JMenuItem("在线沟通");
        jmb4.add(jmb4communicate);


        JMenu jmb5 = new JMenu("退出");
        jmb.add(jmb5);
        jmb5logout = new JMenuItem("退出登录");
        jmb5.add(jmb5logout);
        jmb5exit = new JMenuItem("退出系统");
        jmb5.add(jmb5exit);




        jp_center = new JPanel();
        jp_south = new JPanel();
        jf.setVisible(true);

        //查看并修改个人信息
        jmb1find.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jp_center.removeAll();
                jp_center.setLayout(new GridLayout(7,2));
                jf.add(jp_center,BorderLayout.CENTER);
                jl_name = new JLabel("姓名");
                jt_name = new JTextField(20);
                jt_name.setText(tea.getName());
                jp1  =new JPanel();
                jp1.add(jl_name);
                jp1.add(jt_name);
                jl_age = new JLabel("年龄");
                jt_age = new JTextField(20);
                jt_age.setText(String.valueOf(tea.getAge()));
                jp2 =new JPanel();
                jp2.add(jl_age);
                jp2.add(jt_age);
                jl_sex = new JLabel("性别");
                jt_sex = new JTextField(20);
                jt_sex.setText(tea.getSex());
                jp3 =new JPanel();
                jp3.add(jl_sex);
                jp3.add(jt_sex);


                jl4 = new JLabel("修改成功");
                jl5 = new JLabel("修改失败");
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

                            String SQL = "update users set name = ? , age = ? , sex = ? where id = "+tea.getId();
                            //操作数据库
                            PreparedStatement pare = connection.prepareStatement(SQL);
                            pare.setString(1, jt_name.getText());
                            pare.setInt(2,Integer.valueOf(jt_age.getText()));
                            pare.setString(3, jt_sex.getText());
                            //执行sql语句
                            int rows = pare.executeUpdate();

                            if(rows>0){
                                jp4.add(jl4);
                                JOptionPane.showMessageDialog(null,"修改成功");
                            }else{
                                jp4.add(jl5);
                                JOptionPane.showMessageDialog(null,"修改失败","修改失败",JOptionPane.ERROR_MESSAGE);
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
        //修改密码
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

                            String SQL = "update users set password = ? where id = "+tea.getId();
                            //操作数据库
                            PreparedStatement pare = connection.prepareStatement(SQL);
                            pare.setString(1, String.valueOf(jpassword1.getPassword()));
                            //执行sql语句
                            int rows = pare.executeUpdate();

                            if(rows>0){
                                jp4.removeAll();
                                jp4.add(new JLabel("修改密码成功"));
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
                    }
                });
            }
        });
        //查看所有学生这门课成绩
        jmb2find.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("查看所有学生");
                TeacherLogin.Excel();
                model = (DefaultTableModel) table.getModel();
                model.setColumnIdentifiers(new Object[] { "学生学号","学生姓名","学生年龄","学生性别","课程"});
                CourseManage.allStudentOneCourse_mark(tea);
                jf.setVisible(true);
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
        jmb5exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        jmb3look.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jp_center.removeAll();
                jp_center.setLayout(new FlowLayout());
                //2,添加table
                JTable table = null;
                String[] index = {"学号", "姓名", "问题编号", "回答时间"};
                int i = 0;
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itcast", "root", "gaozhenlin233");

                    String SQL = "select * from task";
                    //操作数据库
                    PreparedStatement pare = connection.prepareStatement(SQL);
                    //执行sql语句
                    ResultSet rs = pare.executeQuery();
                    i = 0;
                    while (rs.next()) {
                        i++;
                    }
                    connection.close();
                    pare.close();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                Object[][] data = new Object[i][index.length];
                i=0;
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itcast", "root", "gaozhenlin233");

                    String SQL = "select * from task";
                    //操作数据库
                    PreparedStatement pare = connection.prepareStatement(SQL);
                    //执行sql语句
                    ResultSet rs = pare.executeQuery();

                    while (rs.next()) {
                        data[i][0]=rs.getString(1);
                        data[i][1]=rs.getString(2);
                        data[i][2]=rs.getString(3);
                        data[i][3]=rs.getString(4);
                        i++;
                    }
                    connection.close();
                    pare.close();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

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
            }
        });
        jmb3work.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jp_center.removeAll();
                new tea_work();
            }
        });
        jmb3duplicate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //文件选择器获取文件或者文件夹
                //========================================
                JFileChooser jfc=new JFileChooser();
                String str = null;
                //设置当前路径为桌面路径,否则将我的文档作为默认路径
                FileSystemView fsv = FileSystemView .getFileSystemView();
                jfc.setCurrentDirectory(fsv.getHomeDirectory());
                //JFileChooser.FILES_AND_DIRECTORIES 选择路径和文件
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
                //用户选择的路径或文件
                if (jfc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                    File file=jfc.getSelectedFile();
                    str = file.getAbsolutePath();
                }
                JFrame jFrame = new JFrame("代码查重");
                JScrollPane jScrollPane = new JScrollPane();
                jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                JTextArea textshow= new JTextArea(2000,1500);
                textshow.setFocusable(false);//没有光标显示
                jScrollPane.setViewportView(textshow);//将jtxArea放在 jScrollPane里
                jFrame.add(jScrollPane);

                //初始化文本区
                textshow.setFont(new Font("楷体_gb2312" , Font.PLAIN , 15));
                textshow.setForeground(Color.BLACK);


                textshow.setText(null);					//设置文本区内容为空
                try {
                    RepeatFileManager rfm = new RepeatFileManager();

                    final long startTime = System.currentTimeMillis();

                    JTextArea finalTextshow = textshow;
                    rfm.setOnRepeatStatsListener(new RepeatFileManager.OnRepeatStatsListener() {

                        @Override
                        public void onRepeatStatsFinished(
                                Map<String, java.util.List<String>> repeatFiles) {
                            long useTime = System.currentTimeMillis() - startTime;
                            finalTextshow.append("查重比对开始~~~~~~~"+"\n");
                            finalTextshow.append("共用时：" + useTime /1000f + "秒"+"\n");

                            finalTextshow.append("所查询疑似抄袭代码如下所示："+"\n");
                            int i=1;
                            for(Map.Entry<String, List<String>> entry: repeatFiles.entrySet()){
                                int j =i;
                                finalTextshow.append("第"+(i++)+"组可疑作业："+"\n"+"获取到文件唯一散列值为："+entry.getKey()+"\n");
                                finalTextshow.append("第"+j+"组相似作业如下："+"\n");
                                for(String path: entry.getValue()){
                                    finalTextshow.append(path+"\n");
                                }
                                System.out.println();
                                if(i==1)
                                {
                                    finalTextshow.append("同学们表现良好，并未查询到疑似抄袭代码"+"\n");
                                }
                            }
                        }
                    });

                    rfm.start(new File(str));
                }
                catch (Exception e1)
                {
                    e1.printStackTrace();
                }
                jFrame.setTitle("查重结果");
                jFrame.setVisible(true);
                jFrame.setLocationRelativeTo(null);
                jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrame.setSize(1000, 500);

                //=======================================================
                //文件选择器获取文件,这里只能获取文件，不能获取文件夹

                //====================================================
            }


        });
        jmb4message.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = new JFrame("离线留言");
                jFrame.setLayout(new BorderLayout());
                JPanel jPanel = new JPanel();
               JScrollPane jScrollPane = new JScrollPane();//滚动条

                jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
               JTextArea jtxArea=new JTextArea(2000,1500);
                jtxArea.setFocusable(false);//没有光标显示
                jScrollPane.setViewportView(jtxArea);//将jtxArea放在 jScrollPane里
                jFrame.add(jScrollPane, BorderLayout.CENTER);

                jPanel.setLayout(new FlowLayout());
                jFrame.add(jPanel, BorderLayout.SOUTH);
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
                jFrame.setSize(500, 400);
                jFrame.setLocationRelativeTo(null);
                jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jFrame.setVisible(true);
                jFrame.setDefaultCloseOperation(jFrame.DISPOSE_ON_CLOSE);
            }

        });
    }

    //表格
    public static void Excel(){
        jp_center.removeAll();
        jp_south.removeAll();
        jp_center.setLayout(new GridLayout());
        scrollPane = new JScrollPane();
        jp_center.add(scrollPane);
        jf.add(jp_center,BorderLayout.CENTER);

        table = new JTable();
        // 将表的选择模式设置为只允许单个选择、单个连续间隔选择或多间隔选择。
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        table.setRowHeight(30);
        JTableHeader header = table.getTableHeader(); // 获取 JTable 的头的对象
        header.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        header.setPreferredSize(new Dimension(header.getWidth(), 35)); // 设置此组件的首选大小
        scrollPane.setViewportView(table);
    }

}