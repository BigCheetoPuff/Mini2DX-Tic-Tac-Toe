package com.mystudio.gamename;

import com.badlogic.gdx.graphics.Color;
import org.mini2Dx.core.graphics.Graphics;

public class Board {

    GameSpace[][] grid;

    public Board() {
        grid = new GameSpace[3][3];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new GameSpace(GameSpace.Space.FREE);
            }
        }

    }

    public void draw(Graphics g, Game game) {
        g.setColor(Color.BLACK);
        float width = (float) (game.getWidth());
        float height = (float) (game.getHeight());
        g.drawLineSegment(width / 3, 0f, width / 3, height);
        g.drawLineSegment(width / 3 * 2, 0f, width / 3 * 2, height);
        g.drawLineSegment(0, height / 3, width, height / 3);
        g.drawLineSegment(0, height / 3 * 2, width, height / 3 * 2);

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j].render(g, i, j, width, height);
            }
        }
    }

    public boolean checkGameOver(int x, int y, GameSpace.Space player) {
        for (int i = 0; i < 3; i++) {
            if (grid[x][i].space != player)
                break;
            if (i == 2) {
                return true;
            }
        }

        //check row
        for (int i = 0; i < 3; i++) {
            if (grid[i][y].space != player)
                break;
            if (i == 2) {
                return true;
            }
        }

        //check diag
        if (x == y) {
            //we're on a diagonal
            for (int i = 0; i < 3; i++) {
                if (grid[i][i].space != player)
                    break;
                if (i == 2) {
                    return true;
                }
            }
        }

        //check anti diag (thanks rampion)
        if (x + y == 2) {
            for (int i = 0; i < 3; i++) {
                if (grid[i][(2) - i].space != player)
                    break;
                if (i == 2) {
                    return true;
                }
            }

        }

return false;
    }

    public boolean checkTie(){
        for(GameSpace[] arr : grid){
            for(GameSpace gs : arr){
                if(gs.space == GameSpace.Space.FREE){
                    return false;
                }
            }
        }
        return true;
    }

    public void reset(){
        for(GameSpace row[] : grid){
            for(GameSpace gs : row )
            gs.space = GameSpace.Space.FREE;
        }
    }

}
