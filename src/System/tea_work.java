package System;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

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


public class tea_work extends JFrame
{
    private JTextArea textshow;
    private JMenuBar menubar;					//菜单条
    private JMenu menu;							//菜单
    private JMenuItem itemOpen;		//菜单项

    public tea_work()
    {
        init();
    }

    /*
     * 初始化函数
     */
    public void init()
    {
        //初始化 菜单条
        this.menubar = new JMenuBar();

        //初始化菜单
        this.menu = new JMenu("作业查看");
        this.menu .setFont(new Font("楷体_gb2312" , Font.PLAIN , 20));

        //初始化菜单项
        this.itemOpen = new JMenuItem("打开文件");
        this.itemOpen .setFont(new Font("楷体_gb2312" , Font.PLAIN , 17));
        //添加快捷键
        this.itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D , InputEvent.CTRL_MASK));



        //初始化文本区
        this.textshow = new JTextArea();
        this.textshow.setFont(new Font("楷体_gb2312" , Font.PLAIN , 15));
        this.textshow.setForeground(Color.BLACK);


        //将 菜单项 添加到菜单
        this.menu.add(this.itemOpen);

        //将 菜单 添加到 菜单条 上
        this.menubar.add(this.menu);


        //将菜单条加到窗口上
        this.setJMenuBar(this.menubar);

        //将文本区 添加到窗体
        JScrollPane cen_pan = new JScrollPane(this.textshow);

        //注册监视器

        this.itemOpen.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                do_JMenuItem_actionPerfored(e);

            }

        });


        this.add(cen_pan);

        //窗口属性设置
        this.setTitle("点击作业查看按钮打开文件对话框选择您要查看的作业");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 600);
    }

    /*
     * 处理菜单项事件
     */
    private void do_JMenuItem_actionPerfored(ActionEvent e)
    {
        /*
         * 保存文件按钮
         */
//        if (e.getSource() == this.itemOpen)
//        {
//            /*
//             * 目的：将文本区的内容保存到所选的文件中
//             */
//            String str = this.textshow.getText();
//            if (str.isEmpty())
//            {
//                JOptionPane.showMessageDialog(null, "请输入要保存的内容！！！",null ,JOptionPane.WARNING_MESSAGE);
//                return;
//            }
//
//
//            JFileChooser fileChoose = new JFileChooser();		//创建文件对话框
//            fileChoose.setFileFilter(new FileNameExtensionFilter("java文件" , "java"));
//
//            int result = fileChoose.showSaveDialog(this);		//显示文件对话框
//            if (result == JFileChooser.APPROVE_OPTION)
//            {
//                try
//                {
//                    File file = new File(fileChoose.getSelectedFile().getAbsolutePath());//获取选择的文件
//                    FileOutputStream file_write = new FileOutputStream(file);			//创建文件字节输出流
//                    file_write.write(str.getBytes());
//                    file_write.close();
//
//                }
//                catch(Exception e1)
//                {
//                    e1.printStackTrace();
//                }
//            }
//
//
//        }
        if(e.getSource() == this.itemOpen)
        {

            /*
             * 目的：将所选的文件内容输出到文本区
             */
            JFileChooser fileChoose = new JFileChooser();		//创建文件对话框
            fileChoose.setFileFilter(new FileNameExtensionFilter("java文件" , "java"));

            int result = fileChoose.showOpenDialog(this);		//显示文件对话框

            if (result == JFileChooser.APPROVE_OPTION)
            {
                this.textshow.setText(null);					//设置文本区内容为空
                File file = new File(fileChoose.getSelectedFile().getAbsolutePath());
                try
                {

                    BufferedReader buff_read = new BufferedReader( new FileReader(file));//创建输入缓冲流
                    String str = null;
                    while((str = buff_read.readLine())!=null)
                        this.textshow.append(str + '\n');
                    buff_read.close();


                }
                catch (Exception e1)
                {
                    e1.printStackTrace();
                }

            }

        }
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);

    }

}

