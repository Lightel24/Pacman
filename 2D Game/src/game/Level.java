package game;

import java.awt.Rectangle;

import engine.Entity;
import engine.Sprite;

public class Level {
	int[][] level;
	Rectangle[] collisions = new Rectangle[] {new Rectangle(100,100,100,100)};		
	
	int xOffset, yOffset;	
	Sprite mur;

	public Level(Sprite mur, int[][] level, Rectangle[] coll) {
		this.mur = mur;
		this.level = level;
		collisions = coll;
		xOffset =(int)( (Game.width/2) -	((level[0].length)/2)*mur.getWidth()); 
		yOffset =(int)( (Game.height/2) -	((level.length)/2)*mur.getHeight()); 
	}
	
	public void render() {
		for(int y=0; y<level.length;y++) {
			for(int x=0; x<level[y].length;x++){
				if(level[y][x]==1){
					mur.draw(xOffset + x*mur.getWidth(), yOffset + y*mur.getHeight());
				}
			}
		}
	}

	public boolean collideWith(Entity entity) {
		boolean toReturn =false;
		for(Rectangle collision: collisions) {
			if(entity.collideWith(collision)) {
				toReturn=true;
			}
		}
		return toReturn;
	}
}
