package assignment;

import com.jogamp.opengl.GL2;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class Square extends MovableObject implements Drawable{
	
	private Color color;
	public float size;
	
	private Point[] points;
	
	public Square(float size, Color color, Point position) {
		this.size = size;
		this.color = color;
		this.position = position;
		init();
	}

	private void init() {
		points = new Point[4];
		points[0] = new Point(0f, 0f);
		points[1] = new Point(0f, size);
		points[2] = new Point(size, size);
		points[3] = new Point(size, 0f);
	}

	@Override
	public void update(double deltaTime) {
		
	}

	@Override
	public void draw(GL2 gl) {
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(color.r, color.g, color.b, color.a);
		for (Point p : points) {
			gl.glVertex2f(p.x + position.x, p.y + position.y);
		}
		gl.glEnd();
		
	}

}
