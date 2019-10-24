package game;

import java.awt.Rectangle;

import engine.Entity;
import engine.Sprite;

public class Level {
	int[][] level;
	Rectangle[] collisions;		
	
	int xOffset, yOffset;	
	Sprite[] mur;
	Sprite point;

	public Level(Sprite[] murs, Sprite point, int[][] level, Rectangle[] coll) {
		this.mur = murs;
		this.level = level;
		this.point = point;
		collisions = coll;
		xOffset =(int)( (Game.width/2) -	((level[0].length)/2)*mur[0].getWidth()); 
		yOffset =(int)( (Game.height/2) -	((level.length)/2)*mur[0].getHeight()); 
		
		for(Rectangle rec: collisions) {
			rec.setBounds(rec.x+xOffset, rec.y+yOffset, rec.width, rec.height);
		}
		
	}
	
	public void render() {
		for(int y=0; y<level.length;y++) {
			for(int x=0; x<level[y].length;x++){
				if(level[y][x]==0) {
					point.draw(xOffset + x*mur[0].getWidth(), yOffset + y*mur[0].getHeight());
				}else if(level[y][x]==1 || level[y][x]==2 || level[y][x]==3 || level[y][x]==4 || level[y][x]==5 || level[y][x]==6){
					mur[level[y][x]-1].draw(xOffset + x*mur[0].getWidth(), yOffset + y*mur[0].getHeight());
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
					Rectangle Coll = new Rectangle(xOffset + x*mur[0].getWidth(), yOffset + y*mur[0].getHeight(),16,16);
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
