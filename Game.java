package com.mystudio.gamename;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ClasspathFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import org.mini2Dx.core.assets.FallbackFileHandleResolver;
import org.mini2Dx.core.game.BasicGame;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.ui.UiContainer;
import org.mini2Dx.ui.UiThemeLoader;
import org.mini2Dx.ui.style.UiTheme;


import java.awt.event.InputEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends BasicGame{
	public static final String GAME_IDENTIFIER = "com.mystudio.gamename";


	private Texture texture;
	private boolean resetting;
	private Board board;
    private GameSpace.Space player;
    private GameSpace.Space winner;
    private AssetManager assetManager;
    private UiContainer uiContainer;

	@Override
    public void initialise() {

    	texture = new Texture("mini2Dx.png");
    	board = new Board();
    	player = GameSpace.Space.O;
        final Timer resetTimer = new Timer("Reset Timer",true);
        //Create fallback file resolver so we can use the default mini2Dx-ui theme
        FileHandleResolver fileHandleResolver = new FallbackFileHandleResolver(new ClasspathFileHandleResolver(), new InternalFileHandleResolver());

        //Create asset manager for loading resources
        assetManager = new AssetManager(fileHandleResolver);

        //Add mini2Dx-ui theme loader
        assetManager.setLoader(UiTheme.class, new UiThemeLoader(fileHandleResolver));

        //Load default theme
        assetManager.load(UiTheme.DEFAULT_THEME_FILENAME, UiTheme.class);

        uiContainer = new UiContainer(this, assetManager);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown (int x, int y, int pointer, int button) {
                return true;
            }

            @Override
            public boolean touchUp (int x, int y, int pointer, int button) {
                if (!resetting) {
                    int posX = x / (getWidth() / 3);
                    int posY = y / (getHeight() / 3);
                    System.out.println(posX + " " + posY);
                    if (board.grid[posX][posY].space == GameSpace.Space.FREE) {
                        board.grid[posX][posY].space = player;
                        if (board.checkGameOver(posX, posY, player)) {
                            winner = player;
                            resetting = true;
                            resetTimer.schedule(new TimerTask() {
                                public void run() {

                                    reset();
                                }
                            }, 1000);


                        } else if (board.checkTie()) {
                            winner = GameSpace.Space.FREE;
                            resetting = true;
                            resetTimer.schedule(new TimerTask() {
                                public void run() {

                                    reset();
                                }
                            }, 1000);
                        }

                    }

                    player = player == GameSpace.Space.X ? GameSpace.Space.O : GameSpace.Space.X;
                    return true; // return true to indicate the event was handled
                }

                return false;
            }

        });


    }






    @Override
    public void update(float delta) {

        if(!assetManager.update()) {
            //Wait for asset manager to finish loading assets
            return;
        }
        if(!uiContainer.isThemeApplied()) {
            uiContainer.setTheme(assetManager.get(UiTheme.DEFAULT_THEME_FILENAME, UiTheme.class));
        }
        uiContainer.update(delta);





    
    }
    
    @Override
    public void interpolate(float alpha) {
    uiContainer.interpolate(alpha);
    }
    
    @Override
    public void render(Graphics g){

	    g.setBackgroundColor(Color.WHITE);
        uiContainer.render(g);

        board.draw(g, this);




            if (winner == GameSpace.Space.O) {
                g.drawString("O Wins the Game!",  getWidth() / 2-50,getHeight() / 3);
            } else if (winner == GameSpace.Space.X) {
                g.drawString("X Wins the Game!",  getWidth() / 2-50,getHeight() / 3);
            }
            else if(winner ==GameSpace.Space.FREE) {
                g.drawString("Game Tied!",  getWidth() / 2-50,getHeight() / 3);
            }
        }



    public void reset(){
	    board.reset();
	    player = GameSpace.Space.O;
	    winner = null;
	    resetting = false;
    }


}
