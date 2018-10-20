package assignment;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class Point {
	public float x;
	public float y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Point() {
		this.x = 0f;
		this.y = 0f;
	}
	
	public float length() {
		return (float)(Math.sqrt((double)(Math.pow(this.x, 2) + Math.pow(this.y, 2))));
	}
	
	public Point normalized() {
		float x = this.x/length();
		float y = this.y/length();
		
		return new Point(x, y);
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
	
	public String toString() {
		return "("+x+", "+y+")";
	}
	
	public void move(Point velocity, double deltaTime) {
		this.x += velocity.getX() * deltaTime;
		this.y += velocity.getY() * deltaTime;
	}
	
	public void add(Point other) {
		this.x += other.getX();
		this.y += other.getY();
	}
	
	public void multiply(float value) {
		this.x *= value;
		this.y *= value;
	}
}
