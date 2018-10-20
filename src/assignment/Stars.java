package assignment;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import assignment.Color;
import assignment.Drawable;
import assignment.Point;
import assignment.RandomRange;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class Stars implements Drawable {

	private int displayListIndex;
	
	private final Color minColor = new Color(73, 255, 12);
	private final Color maxColor = new Color(203, 255, 186);


	private final int amount;
	private final float maxSize = 4.0f;
	private final float minSize = 1.5f;
	
	private float minHeight, maxHeight;

	private Point[] points;

	public Stars(float minHeight, float maxHeight) {
		amount = RandomRange.randomRange(101, 150);
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		points = initializePoints();
	}
	
	private Point[] initializePoints() {
		Point[] points = new Point[amount];

		for (int i = 0; i < points.length; i++) {
			float x = RandomRange.randomRange(-1f, 1f);
			float y = RandomRange.randomRange(minHeight, maxHeight);
			points[i] = new Point(x, y);
		}
		
		
		return points;
	}

	@Override
	public void update(double deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(GL2 gl) {
		gl.glCallList(displayListIndex);
	}

	public void buildDisplayList(GL2 gl) {
		displayListIndex = gl.glGenLists(1);
		gl.glNewList(displayListIndex, GL2.GL_COMPILE);
		
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		for (Point p : points) {
			float size = RandomRange.randomRange(minSize, maxSize);
			float percentage = (size-minSize)/(maxSize - minSize);
			gl.glPointSize(size);
			gl.glBegin(GL.GL_POINTS);
			
			Color color = Color.lerp(minColor, maxColor, percentage);
			gl.glColor4f(color.r, color.g, color.b, color.a);
			
			gl.glVertex2f(p.getX(), p.getY());
			gl.glEnd();
		}
		
		gl.glEndList();
	}

}
