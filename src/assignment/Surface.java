package assignment;


import java.util.ArrayList;
import java.util.Random;

import com.jogamp.opengl.GL2;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class Surface implements Drawable {
	
	public float minHeight;
	public float maxHeight;
	
	private int numberOfPeaks;
	
	float minLength;
	float lengthVariety;
	float maxLength;
	
	int maxHeightLength;
	boolean up;
	private Point[] points;
	Color color;
	
	int displayListIndex;
	
	
	public Surface(float height, float heightVariety, int numberOfPeaks, float lengthVariety, Color color) {
		
		
		this.minHeight = height - heightVariety;
		this.maxHeight = height + heightVariety;
		
		
		this.numberOfPeaks = numberOfPeaks;
		this.minLength = (float)2/this.numberOfPeaks;
		this.maxLength = minLength + lengthVariety;
		
		this.color = color;
		
		up = false;
		
		points = GenerateSurface();
	}
	
	private Point[] GenerateSurface() {
		float x = -1f;
		ArrayList<Point> pointList = new ArrayList<Point>();
		pointList.add(new Point(-1f, -1f));
		
		for (int i = 0; i <= numberOfPeaks; i++) {
			float y = RandomRange.randomRange(minHeight, maxHeight);
			pointList.add(new Point(x, y));
			y = RandomRange.randomRange(minHeight, maxHeight);
			x += RandomRange.randomRange(minLength, maxLength);
			pointList.add(new Point(x, -1f));
		}
		
		Point[] points = new Point[pointList.size()];
		
		for (int i = 0; i < points.length; i++) {
			points[i] = pointList.get(i);
			//System.out.println(points[i]);
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
		gl.glBegin(GL2.GL_QUAD_STRIP);
		gl.glColor3f(color.getR(), color.getG(), color.getR());
		for (Point p : points) {
			gl.glVertex2f(p.getX(), p.getY());
		}
		gl.glEnd();
		gl.glEndList();
		//System.out.println(displayListIndex);
	}

}
