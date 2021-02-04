import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/*
 * @author :hyacinth
 * GamePanel继承了JPanel(SWING提供的类)以后，才具备面板的功能，才能成为一个面板
 * 注：先创建一个窗体，然后在窗体上创建一个面板，面板上可以填充图片信息
 * */
public class GamePanel extends JPanel{
    //定义两个数组
    //蛇的长度：
    int length;
    //一个数组，专门存储蛇的x轴坐标；一个数组，专门存储蛇的y轴坐标
    int[] snakeX = new int[200];
    int[] snakeY = new int[200];
    //游戏的状态，开始或暂停,默认游戏暂停
    boolean isStart=false;
    //加入一个定时器
    Timer timer;
    //蛇头
    String direction;
    //定义食物的X、Y轴坐标;
    int foodX;
    int foodY;
    //定义一个积分
    int score;
    //蛇初始化信息
    public void init(){
        //初始化蛇的长度：
        length = 3;
        //初始化蛇头坐标；
        snakeX[0] = 175;
        snakeY[0] = 275;
        //初始化第一节身子坐标
        snakeX[1] = 150;
        snakeY[1] = 275;
        //初始化第二节身子坐标
        snakeX[2] = 125;
        snakeY[2] = 275;
        //初始化蛇头的方向
        direction ="R";
        //初始化食物的坐标；
        foodX = 300;
        foodY = 200;
        //初始化积分
        score = 0;
    }
    //构造器调用蛇初始化方法
    public GamePanel(){
        init();
        //将焦点定位在当前操作的面板上
        this.setFocusable(true);
        //加入监听： 匿名内部类
        this.addKeyListener(new KeyAdapter() {
            //覆盖重写：监听键盘按压动作
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int keyCode =e.getKeyCode();
                System.out.println(keyCode);
                if(keyCode == KeyEvent.VK_SPACE){ //ASII码 32对应空格
                    isStart = !isStart; //监听到空格以后，isStart变为true
                    repaint();//画笔重绘动作
                }
                //监听向上箭头
                if(keyCode == KeyEvent.VK_UP){
                   direction = "U";
                }
                //监听向下箭头
                if(keyCode == KeyEvent.VK_DOWN){
                    direction = "D";
                }
                //监听向左箭头
                if(keyCode == KeyEvent.VK_LEFT){
                    direction = "L";
                }
                //监听向右箭头
                if(keyCode == KeyEvent.VK_RIGHT){
                    direction = "R";
                }

            }
        });
        //对定时器进行初始化动作  匿名内部类
        timer = new Timer(100, new ActionListener() {
            //ActionListener是事件监听，相当于每100ms监听一下是否发生一个动作
            //具体的动作放入actionPerformed方法
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isStart){//游戏是开始状态，蛇才动
                    //后一节身子走到前一节身子的位置上
                    for(int i=length-1;i>0;i--){
                        snakeX[i] = snakeX[i-1];
                        snakeY[i] = snakeY[i-1];
                    }
                    //动蛇头
                    if("R".equals(direction)) {
                        snakeX[0] = snakeX[0]+25;
                    }
                    if("L".equals(direction)){
                        snakeX[0] = snakeX[0]-25;
                    }
                    if("U".equals(direction)){
                        snakeY[0] = snakeY[0]-25;
                    }
                    if ("D".equals(direction)) {
                        snakeY[0] = snakeY[0]+25;
                    }
                    //防止蛇超出边界；
                    if(snakeX[0]>750){
                        snakeX[0] =25;
                    }
                    if(snakeX[0]<25){
                        snakeX[0] = 750;
                    }
                    if(snakeY[0]<100){
                        snakeY[0] = 725;
                    }
                    if(snakeY[0]>725){
                        snakeY[0] = 100;
                    }
                    //检测碰撞的动作：
                    //食物的坐标和蛇头的坐标一样的时候，蛇长度加1
                    if(snakeX[0] == foodX && snakeY[0] == foodY){
                        //蛇长度加+1
                        length++;
                        //生成随机食物坐标
                        foodX = (int)(Math.random()*30+1)*25;//[25,750]
                        foodY = (new Random().nextInt(26)+4)*25; //[100,725]
                        //每吃一个食物，积分加10
                        score +=10;
                    }

                    repaint();//重绘
                }
            }
        });
        timer.start();
    }

    //paintComponcnt 画笔 这个方法比较特殊，这个方法就属于图形版的main 方法，可以直接调用
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            //填充背景颜色：RGB三原色 红绿蓝
            this.setBackground(Color.BLACK);
            //画头部图片
            //paintIcon四个参数：this指的是当前面板，g:指的是使用的画笔,x轴，y轴坐标
            Images.headerImg.paintIcon(this,g,10,10);
            //调节画笔颜色：为画矩形做准备
            g.setColor(new Color(219,226,219));
            //画一个矩形：
            g.fillRect(10,70,770,685);
            //画蛇头：
            if("R".equals(direction)){
                Images.rightImg.paintIcon(this,g,snakeX[0],snakeY[0]);
            }
            if("L".equals(direction)){
                Images.leftImg.paintIcon(this,g,snakeX[0],snakeY[0]);
            }
            if("U".equals(direction)){
                Images.upImg.paintIcon(this,g,snakeX[0],snakeY[0]);
            }
            if("D".equals(direction)){
                Images.downImg.paintIcon(this,g,snakeX[0],snakeY[0]);
            }
//            //画第一节身子：
//            Images.bodyImg.paintIcon(this,g,snakeX[1],snakeY[1]);
//            //画第二节身子：
//            Images.bodyImg.paintIcon(this,g,snakeX[2],snakeY[2]);
            for(int i=1;i<length;i++){
                Images.bodyImg.paintIcon(this,g,snakeX[i],snakeY[i]);
            }
            //如果游戏暂停的，界面中间应该有依据提示语
            if(isStart == false){
                //画一个文字
                g.setColor(new Color(114,98,255));
                //三个参数 字体 ，加粗， 字号
                g.setFont(new Font("华文楷体",Font.BOLD,40));
                //画文字：三个参数：文字内容，X轴坐标，Y轴坐标
                g.drawString("点击空格开始游戏",250,330);
            }
            //画食物：
            Images.foodImg.paintIcon(this,g,foodX,foodY);
            //画积分：
            g.setColor(new Color(255,240,240));
            g.setFont(new Font("华文楷体",Font.BOLD,20));
            g.drawString("积分："+score,620,40);
            //画作者：
            g.setColor(new Color(255,240,240));
            g.setFont(new Font("华文楷体",Font.BOLD,20));
            g.drawString("作者：hyacinth",270,40);
    }
}
