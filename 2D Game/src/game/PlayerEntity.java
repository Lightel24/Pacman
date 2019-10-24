package game;

import engine.Entity;
import engine.Sprite;

public class PlayerEntity extends Entity {
	
	float SPEED = 250f;
		
	protected PlayerEntity(Sprite sprite, float x, float y) {
		super(sprite, x, y);
	}
	
	public void move(Level level) {
		if(!level.collideWith(this)){
			super.move();
		}else {
			dx = 0;
			dy = 0;
		}
	}

	public void up(float delta) {
		this.setHorizontalSpeed(0);
		this.setVerticalSpeed(-SPEED*delta);
		sprite.setRotation(270);
	}

	public void down(float delta) {
		this.setHorizontalSpeed(0);
		this.setVerticalSpeed(SPEED*delta);
		sprite.setRotation(90);
	}

	public void left(float delta) {
		this.setHorizontalSpeed(-SPEED*delta);
		this.setVerticalSpeed(0);
		sprite.setRotation(180);
	}

	public void right(float delta) {
		this.setHorizontalSpeed(SPEED*delta);
		this.setVerticalSpeed(0);
		sprite.setRotation(0);
	}
}
