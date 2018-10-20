package missileLauncher;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import assignment.Color;
import assignment.Drawable;
import assignment.Particle;
import assignment.Point;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class Barrel implements Drawable {

	private Point position;
	private ShootPoint shootPoint;
	private Point shootPointPosition;

	private Point offset;

	private float width = 0.1f;
	private float height = 0.25f;

	private ArrayList<Particle> asteroids;
	
	private Point[] points;
	private Color color = new Color(120, 120, 120);

	public Barrel(Point position, ArrayList<Particle> asteroids) {
		this.position = position;
		this.asteroids = asteroids;
		
		init();
	}

	private void init() {
		points = new Point[4];
		points[0] = new Point(-width / 2, 0f);
		points[1] = new Point(-width / 2, height);
		points[2] = new Point(width / 2, height);
		points[3] = new Point(width / 2, 0f);
		
		shootPoint = new ShootPoint(updateShootPointPosition(), asteroids);
	}
	
	private Point updateShootPointPosition() {
		Point point = new Point(position.x, position.y + (height - 0.01f));
		
		return point;
	}

	@Override
	public void update(double deltaTime) {
		shootPoint.update(deltaTime);
	}

	@Override
	public void draw(GL2 gl) {
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(color.r, color.g, color.b);
		
		for (Point p : points) {
			gl.glVertex2f(p.x + position.x, p.y + position.y);
		}
		gl.glEnd();
		
		shootPoint.draw(gl);
	}

}
