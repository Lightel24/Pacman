package game;

import engine.Entity;
import engine.Sprite;

public class PlayerEntity extends Entity {
	
	enum Direction{
		HAUT,DROITE,GAUCHE,BAS,STOP
	}
	
	Direction currentDirection = Direction.STOP;
	Direction nextDirection = Direction.STOP;
	float SPEED = 250f;
	private int points;
		
	protected PlayerEntity(Sprite sprite, float x, float y) {
		super(sprite, x, y);
	}
	
	public void move(Level level, float delta) {
		processDirection(nextDirection,delta);
		if(!level.collideWithWall(this)){
			currentDirection = nextDirection;
		}
		processDirection(currentDirection,delta);
		if(!level.collideWithWall(this)) {
			super.move();
			points += level.collideWithPoint(this);
		}
	}
	
	private void processDirection(Direction direction, float delta) {
		switch(direction) {
		case BAS:
			this.setHorizontalSpeed(0);
			this.setVerticalSpeed(SPEED*delta);
			sprite.setRotation(90);
			break;
		case DROITE:
			this.setHorizontalSpeed(SPEED*delta);
			this.setVerticalSpeed(0);
			sprite.setRotation(0);
			break;
		case GAUCHE:
			this.setHorizontalSpeed(-SPEED*delta);
			this.setVerticalSpeed(0);
			sprite.setRotation(180);
			break;
		case HAUT:
			this.setHorizontalSpeed(0);
			this.setVerticalSpeed(-SPEED*delta);
			sprite.setRotation(270);
			break;
		case STOP:
			this.setHorizontalSpeed(0);
			this.setVerticalSpeed(0);
			break;
		}	
	}

	public void up(float delta) {
		nextDirection = Direction.HAUT;
	}

	public void down(float delta) {
		nextDirection = Direction.BAS;
	}

	public void left(float delta) {
		nextDirection = Direction.GAUCHE;
	}

	public void right(float delta) {
		nextDirection = Direction.DROITE;
	}
}
