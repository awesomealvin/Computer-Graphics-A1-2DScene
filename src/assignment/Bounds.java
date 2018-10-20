package assignment;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class Bounds implements Drawable{
	
	public Point min; // Bottom Left
	public Point max; // Top Right
	
	public Bounds(Point min, Point max) {
		this.min = min;
		this.max = max;
	}
	
	public boolean contains(Point position) {
		// check X
		if (position.x >= min.x && position.x <= max.x) {
			if (position.y >= min.y && position.y <= max.y) {
				return true;
			}
		} 
		
		return false;
	}
	
	public boolean collision(Bounds other) {
		boolean contains = false;
		
		Point[] corners = new Point[4];
		corners[0] = new Point(min.x, min.y);
		corners[1] = new Point(min.x, max.y);
		corners[2] = new Point(max.x, max.y);
		corners[3] = new Point(max.x, min.y);
		
		for (Point p : corners) {
			if (other.contains(p)) {
				contains = true;
			}
		}
		
//		if (contains) {
//			System.out.println("HIT!");
//		}
		
		return contains;
	}

	@Override
	public void update(double deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(GL2 gl) {
		if (true)
		{
			return;
		}
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glColor3f(1f,0f, 0f);
		gl.glVertex2f(this.min.x, this.min.y);
		gl.glVertex2f(this.min.x, this.max.y);
		gl.glVertex2f(this.max.x, this.max.y);
		gl.glVertex2f(this.max.x, this.min.y);
		gl.glVertex2f(this.min.x, this.min.y);
		gl.glEnd();		
	}
	
	
	
	
}
