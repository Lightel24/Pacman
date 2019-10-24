package game;

import org.lwjgl.glfw.GLFW;

import engine.Entity;
import engine.Sprite;
import engine.Timer;

public class Jeu extends GameState{
	
	public static float SPEED = 150;
	float delta;
	int xOffset = 0;
	int yOffset = 0 ;
	
	PlayerEntity pacman;
	Timer timer = new Timer();
	// Variables de test
	Level level;
	
	@Override
	public void initialisation(Game context) {
		super.initialisation(context);
		pacman = new PlayerEntity(new Sprite(context.textureLoader, "pacman.png"), 0f,0f);
		//Pour centrer le niveau à l'écran:
		timer.init();
		level = AssetLoader.loadLevel("stage1");

	}
	
	@Override
	public void update() {
		delta = timer.getDelta();
		timer.updateFPS();
		timer.update();
		checkInput();
		pacman.move(level);
	}

	@Override
	public void render() {
		level.render();
		pacman.draw();
	}
	
	

	private void checkInput() {
		if(context.isKeyDown(GLFW.GLFW_KEY_UP)) {
			if(pacman.getVerticalSpeed() == 0) {
				pacman.setVerticalSpeed(-SPEED*delta);
				pacman.setHorizontalSpeed(0);
			}
		}else if(context.isKeyDown(GLFW.GLFW_KEY_DOWN)){
			if(pacman.getVerticalSpeed() == 0) {
				pacman.setVerticalSpeed(SPEED*delta);
				pacman.setHorizontalSpeed(0);
			}
		}else if(context.isKeyDown(GLFW.GLFW_KEY_LEFT)){
			if(pacman.getHorizontalSpeed() == 0) {
				pacman.setVerticalSpeed(0);
				pacman.setHorizontalSpeed(-SPEED*delta);
			}
		}else if(context.isKeyDown(GLFW.GLFW_KEY_RIGHT)){
			if(pacman.getHorizontalSpeed() == 0) {
				pacman.setVerticalSpeed(0);
				pacman.setHorizontalSpeed(SPEED*delta);
			}
		}
	}
}
