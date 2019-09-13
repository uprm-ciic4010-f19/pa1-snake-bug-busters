package Worlds;

import Game.Entities.Static.Apple;
import Main.Handler;
import Game.Entities.Static.Coins;

import java.awt.*;
import java.util.Random;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class WorldOne extends WorldBase{
	
	public int appleCounter=0;

    public WorldOne (Handler handler) {
        super(handler);

        //has to be a number bigger than 20 and even
        GridWidthHeightPixelCount = 60;
        GridPixelsize = (800/GridWidthHeightPixelCount);
        playerLocation = new Boolean[GridWidthHeightPixelCount][GridWidthHeightPixelCount];
        appleLocation = new Boolean[GridWidthHeightPixelCount][GridWidthHeightPixelCount];
        coinLocation = new Boolean[GridWidthHeightPixelCount][GridWidthHeightPixelCount];
    }

    @Override
    public void tick() {
        super.tick();
        player.tick();
        if(!appleOnBoard){
            appleOnBoard=true;
            appleCounter++;
            int appleX = new Random().nextInt(handler.getWorld().GridWidthHeightPixelCount-1);
            int appley = new Random().nextInt(handler.getWorld().GridWidthHeightPixelCount-1);
            
            //change coordinates till one is selected in which the player isnt standing
            boolean goodCoordinates=false;
            do{
                if(!handler.getWorld().playerLocation[appleX][appley]){
                    goodCoordinates=true;
                }
            }while(!goodCoordinates);

            apple = new Apple(handler,appleX,appley);
            appleLocation[appleX][appley]=true;
        }
        if(!coinOnBoard && appleCounter>=5){
            coinOnBoard=true;
            appleCounter=0;
            int coinX = new Random().nextInt(handler.getWorld().GridWidthHeightPixelCount-1);
            int coiny = new Random().nextInt(handler.getWorld().GridWidthHeightPixelCount-1);

            boolean goodCoordinates=false;
            do{
                if(!handler.getWorld().playerLocation[coinX][coiny]){
                    goodCoordinates=true;
                }
            }while(!goodCoordinates);

            coins = new Coins(handler,coinX,coiny);
            coinLocation[coinX][coiny]=true;

        }
    }

    @Override
    public void render(Graphics g){
        super.render(g);
        player.render(g,playerLocation);
    }



	}


