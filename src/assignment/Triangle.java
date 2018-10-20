package assignment;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class Triangle implements Drawable {

	private Point position = new Point(0f, 0f);

	private Point[] points;
	private Color color;

	public Triangle(Point[] points, Color color) {
		this.points = points;
		this.color = color;
	}

	public Triangle(Color color, Point... points) {
		this.points = points;
		this.color = color;
	}

	@Override
	public void update(double deltaTime) {

	}

	@Override
	public void draw(GL2 gl) {
		gl.glBegin(GL.GL_TRIANGLES);
		gl.glColor3f(color.r, color.g, color.b);
		for (Point p : points) {
			gl.glVertex2f(p.x + position.x, p.y + position.y);
		}
		gl.glEnd();
	}

}
