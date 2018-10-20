package assignment;

import com.jogamp.opengl.GL2;

import assignment.*;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public abstract class Particle extends MovableObject{
	
	protected Color color;
	
	protected boolean isDead;
	
	private float age;
		
	protected float size;
	protected float minSize;
	protected float maxSize;
	
	public Particle(Point position, float gravityScale,  boolean hasGravity) {
		velocity = new Point(0f, 0f);
		this.position = position;
		this.gravityScale = gravityScale;
		this.color = color;
		this.isDead = false;
		this.hasGravity = hasGravity;
		this.size = RandomRange.randomRange(minSize, maxSize);
	}
	
	public void setAge(float age) {
		this.age = age;
	}
	
	public void update(double deltaTime) {
		move(deltaTime);
	}
	
	public float getY() {
		return position.getY();
	}
	public float getX() {
		return position.getX();
	}
	
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	
	public void setPosition(Point position) {
		this.position = position;
	}
	
	public abstract void draw(GL2 gl);
	
}
