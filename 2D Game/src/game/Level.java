package game;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Point;
import java.awt.Rectangle;

import engine.Entity;
import engine.Sprite;

public class Level {
	private static final int offset = -8;
	int[][] level;
	Point spawn = new Point(Game.width/2,Game.height/2);
	Rectangle[] collisions;		
	
	Sprite mur;
	Sprite point;

	public Level(Sprite mur, Sprite point, int[][] level, Rectangle[] coll) {
		this.mur = mur;
		this.level = level;
		this.point = point;
		collisions = coll;
		
		for(int y=0; y<level.length;y++) {
			for(int x=0; x<level[y].length;x++){
				if(level[y][x]==9) {
					spawn.setLocation(16*x , 16*y);
				}
			}
		}
		
		for(Rectangle rec: collisions) {
			rec.setBounds(rec.x, rec.y, rec.width, rec.height);
		}
		
	}
	
	public void render(float delta) {
		/*Ancien renderer
		for(int y=0; y<level.length;y++) {
			for(int x=0; x<level[y].length;x++){
				if(level[y][x]==0) {
					point.draw(x*mur.getWidth(), y*mur.getHeight());
				}else if(level[y][x]==1 || level[y][x]==2 || level[y][x]==3 || level[y][x]==4 || level[y][x]==5 || level[y][x]==6){
					mur.draw(x*mur.getWidth(),  y*mur.getHeight());
				}
			}
		}*/
        glPushMatrix();
		for(int y=0; y<level.length; y++) {
			for(int x=0; x<level[y].length; x++) {
				if(level[y][x]==1) {
					// On vérifie si il y a des murs autour:
					// En haut
					if(y!=0 && level[y-1][x]!=1) {
						renderLine(x*16,y*16,(x+1)*16,y*16);
					}
					// A droite
					if(x != level[y].length-1 &&level[y][x+1]!=1) {
						renderLine((x+1)*16,y*16,(x+1)*16,(y+1)*16);
					}
					// En bas
					if(y != level.length-1 && level[y+1][x]!=1) {
						renderLine(x*16,(y+1)*16,(x+1)*16,(y+1)*16);
					}
					// A gauche
					if(x!=0 && level[y][x-1]!=1) {
						renderLine(x*16,y*16,(x)*16,(y+1)*16);
					}
				}else if(level[y][x]==0) {
					point.draw(x*mur.getWidth(), y*mur.getHeight(),delta);
				}
			}
		}
        glPopMatrix();
	}
	
	private void renderLine(int x, int y, int x1, int y1) {
		glDisable(GL_TEXTURE_2D);
		
        glTranslatef(0,0, 0);
        glRotatef(0, 0, 0, 1);

            glColor3f(0,0,1);
        	glBegin(GL_LINES);
            {
                glVertex2f(x+offset,y+offset);
                glVertex2f(x1+offset,y1+offset);
            }
            glEnd();
	}

	public boolean collideWithWall(Entity entity) {
		boolean toReturn =false;
		for(Rectangle collision: collisions) {
			if(entity.collideWith(collision)) {
				toReturn=true;
			}
		}
		return toReturn;
	}
	
	public int collideWithPoint(Entity joueur) {
		int toReturn = 0;
		
		for(int y=0; y<level.length;y++) {
			for(int x=0; x<level[y].length;x++){
				if(level[y][x]==0) {
					Rectangle Coll = new Rectangle(x*mur.getWidth(), y*mur.getHeight(),16,16);
						if(joueur.collideWith(Coll)) {
							toReturn ++;
							level[y][x]=-1;
						}
				}
			}
		}
		
		return toReturn;
	}
}
