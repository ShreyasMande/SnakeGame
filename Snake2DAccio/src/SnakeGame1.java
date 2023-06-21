import javax.swing.*;
import java.awt.*;

public class SnakeGame1 {
JFrame frame;

        Board1 board;

         SnakeGame1(){
             board = new Board1();
             frame = new JFrame();
             frame.add(board);
             frame.pack();
             frame.setResizable(false);
             frame.setVisible(true);
             frame.getContentPane().setBackground(Color.BLACK);

        }
    public static void main(String[] args) {
        SnakeGame1 snakeGame1 = new SnakeGame1();


    }
}