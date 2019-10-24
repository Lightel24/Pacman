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
		Sprite sprite = new Sprite(context.textureLoader, "pacman",4);
		pacman = new PlayerEntity(sprite, context.width/2,context.height/2);
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
			pacman.up(delta);
		}else if(context.isKeyDown(GLFW.GLFW_KEY_DOWN)){
			pacman.down(delta);			
		}else if(context.isKeyDown(GLFW.GLFW_KEY_LEFT)){
			pacman.left(delta);			
		}else if(context.isKeyDown(GLFW.GLFW_KEY_RIGHT)){
			pacman.right(delta);			
		}
	}
}
