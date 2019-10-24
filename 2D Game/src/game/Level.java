package game;

import java.awt.Rectangle;

import engine.Entity;
import engine.Sprite;

public class Level {
	int[][] level;
	Rectangle[] collisions;		
	
	int xOffset, yOffset;	
	Sprite mur;
	Sprite point;

	public Level(Sprite mur, Sprite point, int[][] level, Rectangle[] coll) {
		this.mur = mur;
		this.level = level;
		this.point = point;
		collisions = coll;
		xOffset =(int)( (Game.width/2) -	((level[0].length)/2)*mur.getWidth()); 
		yOffset =(int)( (Game.height/2) -	((level.length)/2)*mur.getHeight()); 
		
		for(Rectangle rec: collisions) {
			rec.setBounds(rec.x+xOffset, rec.y+yOffset, rec.width, rec.height);
		}
		
	}
	
	public void render() {
		for(int y=0; y<level.length;y++) {
			for(int x=0; x<level[y].length;x++){
				if(level[y][x]==1){
					mur.draw(xOffset + x*mur.getWidth(), yOffset + y*mur.getHeight());
				}else if(level[y][x]==0) {
					point.draw(xOffset + x*mur.getWidth(), yOffset + y*mur.getHeight());
				}
			}
		}
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
					Rectangle Coll = new Rectangle(xOffset + x*mur.getWidth(), yOffset + y*mur.getHeight(),16,16);
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
