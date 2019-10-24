package game;

import org.lwjgl.glfw.GLFW;

import engine.Sprite;

public class Menu extends GameState{	
	
	Sprite logo;
	Sprite enter;
	
	@Override
	public void initialisation(Game context) {
		super.initialisation(context);
		logo = new Sprite(context.textureLoader,"pacmanlogo.png");
		logo.setZoom(.425f);
		enter = new Sprite(context.textureLoader,"pressenter.png");
		enter.setZoom(1f);
		
	}

	@Override
	public void update() {
		if(context.isKeyDown(GLFW.GLFW_KEY_ENTER)) {
			context.setGameState(new Jeu());
		}
	}

	@Override
	public void render() {
		logo.draw(0,0);
		enter.draw(context.width/2 - enter.getWidth()/2,context.height/2 - enter.getHeight()/2);
	}

}
