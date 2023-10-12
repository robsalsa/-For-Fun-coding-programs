package Snake_Program;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH=900;      //This is the size of the width of the box in the lil game 
    static final int SCREEN_HEIGHT=900;     //For normal people this thing is at 600 
    static final int UNIT_SIZE= 25;       //AND for this one the normal is like idk... 25
    static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY=50;              //For normal people the speed should be 50 (slow-ish)... also this is the speed btw
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts=1;                       //This is the size of the snake at the start meaning since this is a game it should start at 1... but you can crank this bitch to a million
    int pointGot;
    int pointX;
    int pointY;
    char direction= 'R';
    boolean running =false;
    Timer timer;
    Random random;




    GamePanel(){
        random=new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();


    }
    public void startGame(){
        newPoint();
        running=true;
        timer=new Timer(DELAY,this);
        timer.start();


    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){

        if(running){

        // This thing is the grid part and i can pick to keep it or not but idk im following directions man i just wanna exist

        //    for(int innie=0; innie<SCREEN_HEIGHT/UNIT_SIZE;innie++){
        //        g.drawLine(innie*UNIT_SIZE, 0, innie*UNIT_SIZE, SCREEN_HEIGHT);
        //        g.drawLine(0, innie*UNIT_SIZE, SCREEN_WIDTH, innie*UNIT_SIZE);
        //    }
            
            g.setColor(Color.black);
            g.fillOval(pointX, pointY, UNIT_SIZE, UNIT_SIZE);

            for(int innie=0; innie<bodyParts;innie++){
                if(innie==0){
                    g.setColor(Color.BLACK);
                    g.fillRect(x[innie], y[innie], UNIT_SIZE, UNIT_SIZE);
                }
                else{
                //    g.setColor(new Color(45,180,0));
                    g.setColor(new Color(128, 0, 128)); //This is the color Purple // You ever heard of RGB? Yeah... this is the color scaling for that... bro coding colors is weird

                //    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));       //Ew this shit randomizes the color but it looks like PUKE
                    g.fillRect(x[innie], y[innie], UNIT_SIZE, UNIT_SIZE);
                }
            }

            /* Apperently this is just to check if it works... and since no one but me will see it its fine :)

            g.setColor(Color.BLACK);
            g.setFont(new Font("Times New Roman", Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Thingys Got: "+pointGot, (SCREEN_WIDTH-metrics.stringWidth("Thingys Got: "+pointGot))/2,g.getFont().getSize());
            */
        }
        else{
            gameOver(g);
        }

    }
    public void newPoint(){
        pointX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        pointY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }
    public void move(){
        for(int innie= bodyParts; innie>0; innie--){
            x[innie]=x[innie-1];
            y[innie]=y[innie-1];
        }
        switch(direction){
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D':
            y[0]=y[0]+UNIT_SIZE;
                break;
            case 'L':
            x[0]=x[0]-UNIT_SIZE;
                break;
            case 'R':
            x[0]=x[0]+UNIT_SIZE;
                break;

        }


    }
    public void checkPoint(){
        if((x[0]==pointX)&&(y[0]==pointY)){
            bodyParts++;
            pointGot++;
            newPoint();


        }

    }
    public void checkCrash(){

        for(int innie=bodyParts;innie>0; innie--){
            if((x[0]==x[innie])&&(y[0] ==y[innie])){
                running= false;

            }
        }
        if(x[0]<0){
            running=false;
        }
        if(x[0]>SCREEN_WIDTH){
            running=false;
        }
        if(y[0]<0){
            running=false;
        }
        if(y[0]>SCREEN_HEIGHT){
            running=false;
        }

        if(!running){
            timer.stop();
        }


    }
    public void gameOver(Graphics g){

        g.setColor(Color.BLACK);                //This is the color of the ball...or whatever the snakes eat idk a fricking squirrle
        g.setFont(new Font("Times New Roman", Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Thingys Gotten: "+pointGot, (SCREEN_WIDTH-metrics1.stringWidth("Thingys Gotten: "+pointGot))/2,g.getFont().getSize());


        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman", Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("You Are Die", (SCREEN_WIDTH-metrics.stringWidth("You Are Die"))/2,SCREEN_HEIGHT/2);

    }


   
    @Override
    public void actionPerformed(ActionEvent e){

        if(running){
            move();
            checkPoint();
            checkCrash();

        }
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction !='R'){
                        direction='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction !='L'){
                        direction='R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction !='D'){
                        direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction !='U'){
                        direction='D';
                    }
                    break;
            }
     
        }
    }    
}