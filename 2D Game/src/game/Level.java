package game;

import java.awt.Point;
import java.awt.Rectangle;

import engine.Entity;
import engine.Sprite;

public class Level {
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
	
	public void render() {
		for(int y=0; y<level.length;y++) {
			for(int x=0; x<level[y].length;x++){
				if(level[y][x]==0) {
					point.draw(x*mur.getWidth(), y*mur.getHeight());
				}else if(level[y][x]==1 || level[y][x]==2 || level[y][x]==3 || level[y][x]==4 || level[y][x]==5 || level[y][x]==6){
					mur.draw(x*mur.getWidth(),  y*mur.getHeight());
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
