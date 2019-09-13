package Game.Entities.Static;

import java.awt.Color;

import Main.Handler;


/**
 * Created by AlexVR on 7/2/2018.
 */
public class Apple {

    private Handler handler;

    public int xCoord;
    public int yCoord;
    
    public boolean goodApple = true;

    public Apple(Handler handler,int x, int y){
        this.handler=handler;
        this.xCoord=x;
        this.yCoord=y;
    }
    public boolean isGood() {
    	int x = handler.getWorld().GridWidthHeightPixelCount;
    	if(handler.getWorld().player.stepsCounter()>x) {
    		goodApple = false;
    	}
    	else {
    		goodApple = true;
    	}
    		return (goodApple); } 
    
}
    	
   
    	
    	


