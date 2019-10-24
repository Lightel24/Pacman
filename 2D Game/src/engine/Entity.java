package engine;

import java.awt.Rectangle;

public abstract class Entity {
	protected float x;
	protected float y;
	protected float dx;
	protected float dy;
	protected Sprite sprite;
	
	protected Entity(Sprite sprite, float x, float y){
		this.x= x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void move() {
		x+=dx;
		y+=dy;
	}

	public void draw(){
		sprite.draw((int) x,(int) y);
	}
	public Sprite getSprite() {
		return sprite;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setHorizontalSpeed(float dx) {
		this.dx = dx;
	}
	
	public void setVerticalSpeed(float dy) {
		this.dy = dy;
	}
	
	public float getHorizontalSpeed() {
		return this.dx;
	}
	
	public float getVerticalSpeed() {
		return this.dy;
	}
	
	public boolean collideWith(Entity entity) {
		Rectangle me = new Rectangle();
		Rectangle other = new Rectangle();
		me.setBounds((int)(x + dx), (int)(y + dy), sprite.getWidth(), sprite.getHeight());
		other.setBounds((int)entity.getX(), (int)entity.getY(), entity.getSprite().getWidth(), entity.getSprite().getHeight());
		return me.intersects(other);
	} 
	
	public boolean collideWith(Rectangle other) {
		Rectangle me = new Rectangle();
		me.setBounds((int)(x + dx), (int)(y + dy), sprite.getWidth(), sprite.getHeight());
		return me.intersects(other);
	}  
}
