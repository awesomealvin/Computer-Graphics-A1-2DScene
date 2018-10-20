package assignment;

import java.util.ArrayList;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class Circle extends MovableObject implements Drawable {

	private float radius;
	public Color color;

	private Point[] points;

	private int smoothing = 100;

	public Circle(float radius, Color color, Point position) {
		this.radius = radius;
		this.color = color;
		this.position = position;
		init();
	}

	private void init() {
		ArrayList<Point> pointList = new ArrayList<Point>();

		pointList.add(new Point(0f, 0f));

		float currentAngle = 0f;
		float angleDifference = 360f / smoothing;

		for (int i = 0; i <= smoothing; i++) {
			float radians = (float) Math.toRadians(currentAngle);

			float x = (float) (Math.cos(radians) * radius);
			float y = (float) (Math.sin(radians) * radius);

			pointList.add(new Point(x, y));
			
			currentAngle += angleDifference;
		}
		
		points = new Point[pointList.size()];
		for(int i = 0; i < points.length; i++) {
			points[i] = pointList.get(i);
		}
	}

	@Override
	public void update(double deltaTime) {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw(GL2 gl) {
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBegin(GL.GL_TRIANGLE_FAN);
	
		gl.glColor4f(color.r, color.g, color.b, color.a);
		for (Point p : points) {
			gl.glVertex2f(p.x + position.x, p.y + position.y);
		}
		gl.glEnd();
		gl.glDisable(GL2.GL_BLEND);
	}

}
