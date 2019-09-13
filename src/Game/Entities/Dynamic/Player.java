package Game.Entities.Dynamic;

import Main.GameSetUp;
import Main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JOptionPane;

import Display.DisplayScreen;
import Game.GameStates.GameState;
import Game.GameStates.State;
import Game.GameStates.PauseState;
import Game.Entities.Static.Apple;
/**
 * Created by AlexVR on 7/2/2018.
 */
/**
 * @author Veronica
 *
 */
public class Player {

    public int lenght;
    public boolean justAte;
	boolean isDead;
    public int currScore;
    public String scoreStr;
    private Handler handler;

    public int xCoord;
    public int yCoord;

    public int moveCounter;
    public int speed;
    
	public int snakeSteps;
    public int coinCredit;
    public String creditStr;
    
    public String direction;//is your first name one?
    
	public int xSteps = 0;
	public int ySteps = 0;
	public int playerX1 = 0;
	public int playerY1 = 0;

    public Player(Handler handler){
        this.handler = handler;
        xCoord = 0;
        yCoord = 0;
        moveCounter = 0;
        speed = 20;
        direction= "Right";
        justAte = false;
        currScore = 0;
        scoreStr = Integer.toString(currScore);
        lenght= 1;
        snakeSteps = 0;
        coinCredit = 0;
        creditStr = Integer.toString(coinCredit);
    }   
 
    public void tick(){
    	moveCounter++;
    	if(moveCounter >= speed) {
    		checkCollisionAndMove();
    		moveCounter = 0; 
    		if(justAte == true) {
    			speed = speed - 8; 
    		}
    	}
    	if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)){
    		addTail();}
    	if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)){
    		speed = speed - 8;}
    	if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)){
    		speed = speed + 8;}
    	if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
    		State.setState(handler.getGame().pauseState);
    	}

    	if (lenght > 1) {
    		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP) && direction!="Down"){
    			direction="Up";
    		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN) && direction!="Up"){
    			direction="Down";
    		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT) && direction!="Right"){
    			direction="Left";
    		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT) && direction!="Left"){
    			direction="Right"; }
    	}
    	else {
    		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)){
    			direction="Up";
    		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)){
    			direction="Down";
    		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)){
    			direction="Left";
    		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)){
    			direction="Right";
    		} 
    	}  
    }

    public void checkCollisionAndMove(){
    	handler.getWorld().playerLocation[xCoord][yCoord]=false;
    	int x = xCoord;
    	int y = yCoord;
    	isDead = false;
    	switch (direction){
    	case "Left":
    		if(xCoord!=0){
    			if(handler.getWorld().playerLocation[xCoord-1][yCoord]){
    				isDead = true;
    				gameOver();} }
    		if(xCoord==0){
    			xCoord = handler.getWorld().GridWidthHeightPixelCount-1; }
    		else{
    			xCoord--;
    		}
    		break;
    	case "Right":
    		if(xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
    			if(handler.getWorld().playerLocation[xCoord+1][yCoord]){
    				isDead = true;
    				gameOver();} }
    		if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
    			xCoord = 0;
    		}
    		else{
    			xCoord++; 
    		}
    		break;
    	case "Up":
    		if(yCoord!=0){
    			if(handler.getWorld().playerLocation[xCoord][yCoord-1]){
    				isDead = true;
    				gameOver();} }
    		if(yCoord==0){
    			yCoord = handler.getWorld().GridWidthHeightPixelCount-1;
    		}else 
    		{
    			yCoord--;
    		}
    		break;
    	case "Down":
    		if(yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
    			if(handler.getWorld().playerLocation[xCoord][yCoord+1]){
    				isDead = true;
    				gameOver();} }
    		if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
    			yCoord = 0;
    		}else
    		{
    			yCoord++;
    		}
    		break;
    	}
        if(isDead==false) {
        	handler.getWorld().playerLocation[xCoord][yCoord]=true;

        	if(handler.getWorld().coinLocation[xCoord][yCoord]){
        		gotCoin(); }

        	if(handler.getWorld().appleLocation[xCoord][yCoord]){
        		Eat(); }
        	else {
        		justAte = false; 

        	}

        if(!handler.getWorld().body.isEmpty()) {
            handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
            handler.getWorld().body.removeLast();
            handler.getWorld().body.addFirst(new Tail(x, y,handler));
        }}
        }
   
    public void render(Graphics g,Boolean[][] playeLocation){
        Random r = new Random();
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
                g.drawString("score:" + scoreStr, 680, 700);
                g.drawString("credit:" + creditStr, 680, 740);
                g.setColor(Color.green);
                if(playeLocation[i][j]){
                	g.setColor(Color.green);
                    g.fillRect((i*handler.getWorld().GridPixelsize),
                            (j*handler.getWorld().GridPixelsize),
                            handler.getWorld().GridPixelsize,
                            handler.getWorld().GridPixelsize); }
                if(handler.getWorld().appleLocation[i][j]){
                	if(handler.getWorld().getApple().isGood()) {
                		g.setColor(Color.red);
                		g.fillRect((i*handler.getWorld().GridPixelsize),
                            (j*handler.getWorld().GridPixelsize),
                            handler.getWorld().GridPixelsize,
                            handler.getWorld().GridPixelsize); }
                	else {
                		g.setColor(new Color(146, 130, 47));
                		g.fillRect((i*handler.getWorld().GridPixelsize),
                            (j*handler.getWorld().GridPixelsize),
                            handler.getWorld().GridPixelsize,
                            handler.getWorld().GridPixelsize); }
                	}
                if(handler.getWorld().coinLocation[i][j]){
                	g.setColor(Color.yellow);
            		g.fillOval((i*handler.getWorld().GridPixelsize),
                        (j*handler.getWorld().GridPixelsize),
                        handler.getWorld().GridPixelsize,
                        handler.getWorld().GridPixelsize); }
                }
            }}
       
        
    public int stepsCounter() {
    	int playerX2 = this.xCoord;
    	int playerY2 = this.yCoord; 
    	xSteps = Math.abs(playerX1 - playerX2);
    	ySteps = Math.abs(playerY1 - playerY2);
    	if (xSteps > 1) {
    		xSteps = 0;
    	}
    	if (ySteps > 1) {
    		ySteps = 0;
    	}
    	playerX1 = playerX2;
    	playerY1 = playerY2;
    	snakeSteps += (xSteps + ySteps); 
     	return (snakeSteps);  }

    public void Eat(){
        handler.getWorld().appleLocation[xCoord][yCoord]=false;
        handler.getWorld().appleOnBoard=false;
        if (handler.getWorld().getApple().isGood()) {
        	addTail();
        	justAte = true;
        	currScore = (int) (currScore + Math.sqrt((2*currScore) + 1));
        	scoreStr = Integer.toString(currScore); } 
        else {
        	substractTail();
        	currScore = (int) (currScore - Math.sqrt((2*currScore) + 1));
        	if(currScore < 0) {
        		currScore = 0; }
        	scoreStr = Integer.toString(currScore);
        }
        snakeSteps = 0;
        xSteps = 0;
        ySteps = 0;
    }
    public void gotCoin() {
    	handler.getWorld().coinLocation[xCoord][yCoord]=false;
        handler.getWorld().coinOnBoard=false;
        coinCredit ++;
        creditStr = Integer.toString(coinCredit);
        
        
    }
    public void addTail(){
    lenght++;
    Tail tail= null;
    switch (direction){
        case "Left":
            if( handler.getWorld().body.isEmpty()){
                if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                    tail = new Tail(this.xCoord+1,this.yCoord,handler);
                }else{
                    if(this.yCoord!=0){
                        tail = new Tail(this.xCoord,this.yCoord-1,handler);
                    }else{
                        tail =new Tail(this.xCoord,this.yCoord+1,handler);
                    }
                }
            }else{
                if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
                    tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
                }else{
                    if(handler.getWorld().body.getLast().y!=0){
                        tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
                    }else{
                        tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

                    }
                }

            }
            break;
        case "Right":
            if( handler.getWorld().body.isEmpty()){
                if(this.xCoord!=0){
                    tail=new Tail(this.xCoord-1,this.yCoord,handler);
                }else{
                    if(this.yCoord!=0){
                        tail=new Tail(this.xCoord,this.yCoord-1,handler);
                    }else{
                        tail=new Tail(this.xCoord,this.yCoord+1,handler);
                    }
                }
            }else{
                if(handler.getWorld().body.getLast().x!=0){
                    tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                }else{
                    if(handler.getWorld().body.getLast().y!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                    }else{
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                    }
                }

            }
            break;
        case "Up":
            if( handler.getWorld().body.isEmpty()){
                if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                    tail=(new Tail(this.xCoord,this.yCoord+1,handler));
                }else{
                    if(this.xCoord!=0){
                        tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                    }else{
                        tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                    }
                }
            }else{
                if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
                    tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                }else{
                    if(handler.getWorld().body.getLast().x!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                    }else{
                        tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                    }
                }

            }
            break;
        case "Down":
            if( handler.getWorld().body.isEmpty()){
                if(this.yCoord!=0){
                    tail=(new Tail(this.xCoord,this.yCoord-1,handler));
                }else{
                    if(this.xCoord!=0){
                        tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                    }else{
                        tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                    } System.out.println("Tu biscochito");
                }
            }else{
                if(handler.getWorld().body.getLast().y!=0){
                    tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                }else{
                    if(handler.getWorld().body.getLast().x!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                    }else{
                        tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                    }
                }

            }
            break;
    }
    handler.getWorld().body.addLast(tail);
    handler.getWorld().playerLocation[tail.x][tail.y] = true;
}
    
    public void substractTail(){
    lenght--;
    Tail tail= null;
    if(handler.getWorld().body.isEmpty()==false){
    	handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
    	handler.getWorld().body.removeLast();
    }    }

    public void kill(){
        lenght = 0;
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
                handler.getWorld().playerLocation[i][j]=false;
            }
        }
    }
    public void gameOver() {
    	JOptionPane.showMessageDialog(null, "You lost.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    	kill();
    	handler.getGame().reStart(); 
    	}
    public boolean isJustAte() {
        return justAte;
    }

    public void setJustAte(boolean justAte) {
        this.justAte = justAte;
    }
}
