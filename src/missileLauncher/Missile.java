package missileLauncher;

import com.jogamp.opengl.GL2;

import assignment.Bounds;
import assignment.Color;
import assignment.Drawable;
import assignment.MovableObject;
import assignment.Point;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class Missile extends MovableObject implements Drawable{
	
	private Point[] points;
	
	public Bounds bounds;
	
	private float width = 0.06f;
	private float height = 0.06f;
	
	private Color color = new Color(140, 140, 140);
	
	
	public Missile(Point position, Point velocity) {
		this.hasFriction = false;
		this.hasGravity = false;
		this.velocity = velocity;
		this.position = position;
		color = new Color(1f, 0f, 0f);
		init();
	}
	
	public void setRotation() {
		double radians = Math.atan2(velocity.y, velocity.x);
		double degrees = Math.toDegrees(radians);
		degrees -= 90f;
		radians = Math.toRadians(degrees);
		System.out.println(degrees);
		for (Point p : points) {
//			float hyp = p.length();
//			float newX = (float) (Math.cos(radians) * hyp); 
//			float newY = (float) (Math.sin(radians) * hyp);
//			Point point = new Point(newY, newX);
//			
//			p.add(point);
			
			double x = ( p.x * Math.cos(radians)) - (p.y * Math.sin(radians));
			double y = (p.x * Math.sin(radians)) + (p.y * Math.cos(radians));
			
			p.x = (float)x;
			p.y = (float)y;
		}
	}
	
	private void init() {
		points = new Point[3];
		
		points[0] = new Point(-width/2, 0f);
		points[1] = new Point(0f, height);
		points[2] = new Point(width/2, 0f);
		
		//initialize bounds
		Point min = new Point(-width/2 + position.x, 0f + position.y);
		Point max = new Point(width/2 + position.x, height+position.y);
		bounds = new Bounds(min, max);
		
		setRotation();
	}
	
	private void updateBounds() {
		Point min = new Point(-width/2 + position.x, 0f + position.y);
		Point max = new Point(width/2 + position.x, height+position.y);
		bounds.min = min;
		bounds.max = max;
	}

	@Override
	public void update(double deltaTime) {
		move(deltaTime);
		updateBounds();
	}

	@Override
	public void draw(GL2 gl) {
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glColor3f(color.r, color.g, color.b);
		//for (Point p : points) {
			gl.glVertex2f(points[0].x + position.x, points[0].y + position.y);
			gl.glColor3f(0f, 1f, 0f);
			gl.glVertex2f(points[1].x + position.x, points[1].y + position.y);
			gl.glColor3f(color.r, color.g, color.b);
			gl.glVertex2f(points[2].x + position.x, points[2].y + position.y);
		//}
		gl.glEnd();
		
		bounds.draw(gl);
	}

}
