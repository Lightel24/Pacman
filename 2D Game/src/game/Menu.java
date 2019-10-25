package game;

import org.lwjgl.glfw.GLFW;

import engine.Sprite;

public class Menu extends GameState{	
	
	Sprite logo;
	Sprite enter;
	Sprite editeur;
	
	@Override
	public void initialisation(Game context) {
		super.initialisation(context);
		logo = new Sprite(context.textureLoader,"pacmanlogo",1);
		logo.setZoom(.425f);
		enter = new Sprite(context.textureLoader,"pressenter",1);
		enter.setZoom(1f);
		editeur = new Sprite(context.textureLoader,"editeur",1);
		
	}

	@Override
	public void update() {
		if(context.isKeyDown(GLFW.GLFW_KEY_ENTER)) {
			context.setGameState(new Jeu());
		}else if(context.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
			context.setGameState(new Editeur());
		}
	}

	@Override
	public void render() {
		logo.draw(context.width/2,logo.getHeight()/2);
		enter.draw(context.width/2,context.height/2 + enter.getHeight()/2);
		editeur.draw(context.width/2,context.height/2 + enter.getHeight()*2);
	}

}
