import javax.swing.*;
import java.awt.*;

public class StartGame {
    //这是一个main方法，是程序的入口：
    public static void main(String[] args){
        //创建一个窗体： JFrame  swing提供的类
        JFrame jf = new JFrame();
        //给窗体设置一个标题：
        jf.setTitle("JAVA版贪吃蛇  author : hyacinth");
        //设置窗体弹出的坐标（位置）
        //获取屏幕宽高
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        //据X轴像素位数，据y轴像素位数，GUI宽高
        jf.setBounds((width-800)/2,(height-800)/2,800,800);
        //设置窗体大小不可调节
        jf.setResizable(false);
        //关闭窗口的同时 程序随之关闭
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //创建面板：
        GamePanel gp = new GamePanel();
        //将面板放入窗体
        jf.add(gp);
        //默认情况下窗体是隐藏的，必须让窗体显示出来。注：这个方法默认放到最后执行
        jf.setVisible(true);
    }
}
