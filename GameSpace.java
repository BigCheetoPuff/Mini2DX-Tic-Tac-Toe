package com.mystudio.gamename;

import org.mini2Dx.core.graphics.Graphics;

public class GameSpace {
    Space space;
    public GameSpace(Space space){
        this.space = space;
    }

    public void render(Graphics g, int posX,int posY,float gameWidth, float gameHeight){

        if(space == Space.X){

            g.drawLineSegment(gameWidth/3* posX+20,posY*gameHeight/3+20,gameWidth/3 *(posX+1)-20,(posY+1)*gameHeight/3-20);
            g.drawLineSegment((posX+1)*gameWidth/3-20,(posY)*gameHeight/3+20,posX *gameWidth/3+20, (posY+1) * gameHeight/3-20);
        }
        else if(space == Space.O){

            g.drawCircle((posX + .5f)* gameWidth/3, (posY + .5f)* gameHeight/3,gameWidth/9);

        }
    }
    enum Space {

        FREE,
        X,
        O

    }


}