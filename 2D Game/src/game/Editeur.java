package game;

import static org.lwjgl.opengl.GL11.*;

import engine.Sprite;

public class Editeur extends GameState{
	
	int[][] data;
	Sprite sprite;
	
	@Override
	public void initialisation(Game context) {
		super.initialisation(context);
		sprite = new Sprite(context.textureLoader,"pacmanlogo",1);
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void render() {
		int x = 0;
		int y = 0;
		sprite.draw(context.width/2, context.height/2);
		 // store the current model matrix
        glPushMatrix();
 
        glTranslatef(x, y, 0);
        glRotatef(0, 0, 0, 1);
        // draw a quad textured to match the sprite
        glColor3f(0, 1, 0);
        glBegin(GL_QUADS);
        {
            glVertex2f(0,0);
 
            glVertex2f(context.width/2, 0);
 
            glVertex2f(context.width,context.height);
 
            glVertex2f(0,context.height);
        }
        glEnd();
 
        // restore the model view matrix to prevent contamination
        glPopMatrix();
	}

}
