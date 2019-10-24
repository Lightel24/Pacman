package game;

import engine.Entity;
import engine.Sprite;

public class PlayerEntity extends Entity {

	protected PlayerEntity(Sprite sprite, float x, float y) {
		super(sprite, x, y);
	}
	
	public void move(Level level) {
		if(!level.collideWith(this)){
			super.move();
		}else {
			dx =0;
			dy = 0;
		}
	}
}
