package assignment;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class Asteroid extends Particle implements Drawable {

	private float radiusVariation;
	private float radius;
	private int smoothness;
	
	private Color minColor = new Color(50, 50, 50);
	private Color maxColor = new Color(120, 120, 120);

	private Point[] points;
	
	public Bounds bounds;
	
	private float maxRadius;

	public Asteroid(Point position, float gravityScale, boolean hasGravity, float radius, 
			float radiusVariation, int smoothness, Color color) {
		super(position, gravityScale, hasGravity);
		this.radius = radius;
		this.radiusVariation = radiusVariation;
		this.smoothness = smoothness;

		this.position = position;
		
		this.color = randomizeColor();
		
		this.maxRadius = radius;

		points = setPoints();
		
		setBounds();
	}
	
	private void setBounds() {
		Point min = new Point(-maxRadius + position.x, -maxRadius+position.y);
		Point max = new Point(maxRadius + position.x, maxRadius+position.y);
		bounds = new Bounds(min, max);
	}
	
	private void updateBounds() {
		Point min = new Point(-maxRadius + position.x, -maxRadius+position.y);
		Point max = new Point(maxRadius + position.x, maxRadius+position.y);
		
		bounds.min = min;
		bounds.max = max;
	}

	@Override
	public void update(double deltaTime) {
		move(deltaTime);
		updateBounds();
	}

	private Point[] setPoints() {
		ArrayList<Point> points = new ArrayList<Point>();

		points.add(new Point(0f, 0f));

		float currentAngle = 0f;
		float angleDifference = 360f / (float) smoothness;
		for (int i = 0; i < smoothness; i++) {
			Point point = calculateNextPoint(position, currentAngle);
			points.add(point);
			currentAngle += angleDifference;
		}

		// Smoothly connects the last point to the start of circle (not center)
		points.add(points.get(1));

		Point[] pointArray = new Point[points.size()];
		for (int i = 0; i < pointArray.length; i++) {
			pointArray[i] = points.get(i);
		}

		return pointArray;
	}

	@Override
	public void draw(GL2 gl) {
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glColor3f(color.r, color.g, color.b);
		//System.out.println(position.toString());

		for (Point p : points) {
			gl.glVertex2f(p.getX() + position.getX(), p.getY() + position.getY());
		}

		gl.glEnd();
		
		bounds.draw(gl);
	}
	
	private Color randomizeColor() {
		float interpolation = RandomRange.randomRange(0f, 1f);
		
		return Color.lerp(minColor, maxColor, interpolation);
	}

	private Point calculateNextPoint(Point position, float angle) {
		Point newPoint;

		float radius = RandomRange.randomRange(this.radius, radiusVariation + this.radius);
		maxRadius = (radius > maxRadius)? radius : maxRadius;
		float radians = (float) Math.toRadians(angle);
		float x = (float) (radius * Math.cos(radians));
		float y = (float) (radius * Math.sin(radians));

		newPoint = new Point(x, y);

		return newPoint;
	}

}
