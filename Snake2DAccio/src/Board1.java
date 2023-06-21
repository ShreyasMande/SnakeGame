import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.security.PublicKey;
import java.awt.event.KeyAdapter;
public class Board1 extends JPanel implements ActionListener {
    int B_height = 400;
    int B_width = 400;
    int MaxDots = 1600;
    int DotSize = 10;
    int Dots;
    int x[]= new int[MaxDots];
    int y[]= new int[MaxDots];
    int apple_x;
    int apple_y;
    Image body, head , apple;
    Timer timer;
    int DELAY=300;
    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;

    boolean inGame = true;
    Board1(){
        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(B_width, B_height));
        //setBackground(Color.BLACK);
        initGame();
        loadImages();
    }
    public void initGame(){
        Dots=3;

        for(int i=0; i<Dots; i++){
            x[i] = x[0] + DotSize*i;
        }
        locateApple();
        timer = new Timer(DELAY, this);
         timer.start();
    }
    //Load images from resources folder to Image object
    public void loadImages(){
        ImageIcon bodyIcon = new ImageIcon("src/resorces/dot.png");
        body = bodyIcon.getImage();
        ImageIcon headIcon = new ImageIcon("src/resorces/head.png");
        body = headIcon.getImage();
        ImageIcon appleIcon = new ImageIcon("src/resorces/apple.png");
        body = appleIcon.getImage();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponents(g);
        doDrawing(g);
    }
    public void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < Dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[0], y[0], this);
                } else
                    g.drawImage(body, x[i], y[i], this);
            }
        } else {
            gameOver(g);
            timer.stop();
        }
    }
    public void locateApple(){
        apple_x = ((int)(Math.random()*39))*DotSize;
        apple_y = ((int)(Math.random()*39))*DotSize;
    }
    public void checkCollision(){
       int i=0;
        while( i<Dots){
            if(i>4 && x[0]==x[i] && y[0]==y[i]){
                  inGame = false;
            }
            i++;
        }
        if(x[0]<0){
            inGame = false;
        }
        if(x[0]>=B_width){
            inGame = false;
        }
        if(y[0]<0){
            inGame = false;
        }
        if(y[0]>=B_height){
            inGame = false;
        }
    }
    public void gameOver(Graphics g){
        String msg = "Game Over";
        int score = (Dots - 3)*100;
        String scoremsg = "Score:"+ Integer.toString(score);
        Font small = new Font("Helvetica",Font.BOLD, 14);
        FontMetrics fontMetrics = getFontMetrics(small);

        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg,(B_width-fontMetrics.stringWidth(msg))/2, B_height/4);
        g.drawString(scoremsg,(B_width-fontMetrics.stringWidth(scoremsg))/2, 3*(B_height/4));
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent){
        checkApple();
        checkCollision();
        move();
         repaint();
    }
    //Make Snake move
    public void move(){
        for(int i=Dots-1; i>0; i--){
             x[i] = x[i-1];
             y[i] = y[i-1];
        }
        if(leftDirection){
            x[0]=DotSize;
        }
        if(rightDirection){
            x[0]+=DotSize;
        }
        if(upDirection){
            x[0]-=DotSize;
        }
        if(downDirection){
            x[0]+= DotSize;
        }
    }
    public void checkApple(){
        if(apple_x==x[0] && apple_y==y[0]){
            Dots++;
            locateApple();
        }
    }
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if(key==KeyEvent.VK_LEFT&&!rightDirection){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key==KeyEvent.VK_RIGHT&&!leftDirection){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key==KeyEvent.VK_UP&&!downDirection){
                leftDirection = false;
                upDirection = true;
                rightDirection = false;
            }
            if(key==KeyEvent.VK_DOWN&&!upDirection){
                leftDirection = false;
                rightDirection = false;
                downDirection = true;
            }
        }
    }
}
