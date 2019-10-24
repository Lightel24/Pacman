package game;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import static org.lwjgl.opengl.GL11.*;
import engine.Display;
import engine.Entity;
import engine.Sprite;
import engine.TextureLoader;

public class Game{
	GameState MODE;
	private Display display;
	public TextureLoader textureLoader = new TextureLoader();
	public static int width = 800;
	public static int height = 600;

	public Game() {
		initialisation();
		
		mainLoop();
	}

	private void initialisation() {
		display = new Display(width,height,"PacMan");
		
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        
        //On mets le canevas a la taille de la fenetre
        glOrtho(0, width, height, 0, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glViewport(0, 0,(int) (width*1),(int) (height*1)); // Tester pour un zoom pas dégueu
        
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        setGameState(new Menu());
	}

	private void mainLoop() {
		while(!display.isClosing()) {
			display.clear();
			MODE.update();
			MODE.render();
			display.update();
		}
	}
	
	
	

	public static void main(String[] args) {
		new Game();
	}

	public void setGameState(GameState state) {
		this.MODE = state;
		state.initialisation(this);
	}

	public boolean isKeyDown(int keycode) {
		return display.isKeyDown(keycode);
	}
}
