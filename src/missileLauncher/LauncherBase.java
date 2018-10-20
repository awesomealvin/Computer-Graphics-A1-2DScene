package missileLauncher;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import assignment.Color;
import assignment.Drawable;
import assignment.Point;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class LauncherBase implements Drawable {

	private Point position = new Point(0f, 0f);
	private Point[] points;

	private float radius = 0.2f;

	private int smoothness = 50;
	
	private Color color = new Color(100, 100, 100);
	
	public LauncherBase(Point position) {
		this.position = position;
		init();
	}

	@Override
	public void update(double deltaTime) {
		

	}

	@Override
	public void draw(GL2 gl) {
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glColor3f(color.r, color.b, color.g);
		for (Point p : points) {
			gl.glVertex2f(p.x + position.x, p.y + position.y);
		}
		gl.glEnd();
	}

	private void init() {
		ArrayList<Point> pointList = new ArrayList<Point>();
		pointList.add(new Point(0f, 0f));

		float currentAngle = 360f;
		float angleDifference = 180f / smoothness;

		for (int i = 0; i <= smoothness; i++) {
			float radians = (float) Math.toRadians(currentAngle);
			float x = (float) (Math.cos(radians) * radius);
			float y = (float) (Math.sin(radians) * radius);
			Point p = new Point(x, y);
			pointList.add(p);
			
			currentAngle += angleDifference;
		}
		
		points = new Point[pointList.size()];
		for (int i = 0; i < points.length; i++) {
			points[i] = pointList.get(i);
		}

	}

}
