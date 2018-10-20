package assignment;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class Grass implements Drawable {

	private float width;
	private float height;

	private Color colorTop = new Color(14, 160, 46);
	private Color colorBottom = new Color(13, 96, 31);
	private Color colorPulse = new Color(16, 255, 0);
	private Color currentColorTop = new Color(colorBottom.r, colorBottom.g, colorBottom.b);

	private Point[] points;

	private static float minPulseDuration = 2f;
	private static float maxPulseDuration = 0.75f;
	private float pulseDuration;
	private float currentPulseDuration;
	private boolean pulseUp = true;

	public boolean canPulse = true;

	private Point position;

	public Grass(float width, float height, Point position) {
		this.width = width;
		this.height = height;
		this.position = position;

		pulseDuration = RandomRange.randomRange(minPulseDuration, maxPulseDuration);

		init();
	}

	private void init() {
		ArrayList<Point> pointList = new ArrayList<Point>();
		pointList.add(new Point(-width / 2, 0f));
		pointList.add(new Point(width / 2, 0f));
		pointList.add(new Point(0f, height));

		points = new Point[pointList.size()];
		for (int i = 0; i < points.length; i++) {
			points[i] = pointList.get(i);
			points[i].add(position);
		}
	}

	@Override
	public void update(double deltaTime) {
		if (canPulse && pulseUp ) {
			currentPulseDuration += deltaTime;
			pulseUp = (currentPulseDuration >= pulseDuration) ? false : true;
		} else if ((canPulse && !pulseUp) || !canPulse){
			currentPulseDuration -= deltaTime;
			pulseUp = (currentPulseDuration <= 0f) ? true : false;
			currentPulseDuration = (currentPulseDuration < 0f) ? 0f : currentPulseDuration;
		} 
		
		float percentage = currentPulseDuration / pulseDuration;
		currentColorTop = Color.lerp(colorTop, colorPulse, percentage);
	}

	@Override
	public void draw(GL2 gl) {
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glColor3f(colorBottom.r, colorBottom.g, colorBottom.b);
		gl.glVertex2f(points[0].x, points[0].y);
		gl.glColor3f(colorBottom.r, colorBottom.g, colorBottom.b);
		gl.glVertex2f(points[1].x, points[1].y);
		gl.glColor3f(currentColorTop.r, currentColorTop.g, currentColorTop.b);
		gl.glVertex2f(points[2].x, points[2].y);
		gl.glEnd();
	}

}
