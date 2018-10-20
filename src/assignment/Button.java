package assignment;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class Button implements Drawable {

	private String name;
	private int font = GLUT.BITMAP_8_BY_13;
	private Point textOffset;
	
	public float width;
	public float height;
	public Point position;

	private Point offset;
	public float offsetValue = 0.02f;

	private Color colorOff;
	private Color colorOn;
	private Color colorShadow;
	private Color colorText;

	public boolean isActive;

	private Point[] points;
	
	Bounds bounds;
	
	private Toggleable feature;

	public Button(String name, float width, float height, Point position, Toggleable feature) {
		this(name, feature);
		this.width = width;
		this.height = height;
		this.position = position;
		
		init();
	}
	
	public Button(String name, Toggleable feature) {
		this.name = name;
		this.width = 0f;
		this.height = 0f;
		this.position = new Point(0f, 0f);
		this.feature = feature;
	}

	public void init() {
		points = new Point[4];
		points[0] = new Point(0f, 0f);
		points[1] = new Point(width, 0f);
		points[2] = new Point(width, height);
		points[3] = new Point(0f, height);

		colorOff = new Color(3, 158, 95);
		colorOn = new Color(204, 0, 204);
		colorShadow = new Color(50, 50, 50);
		colorText = new Color(255, 204, 0);

		offset = new Point(offsetValue, -offsetValue);
		textOffset = new Point(0.04f, height -0.05f);
		
		// initialize bounds
		float minX = points[0].x + position.x;
		float minY = points[0].y + offset.y + position.y;
		float maxX = points[2].x + offset.x + position.x;
		float maxY = points[2].y + position.y;
		Point min = new Point(minX, minY);
		Point max = new Point(maxX, maxY);
		bounds = new Bounds(min, max);
	}

	@Override
	public void update(double deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(GL2 gl) {
		drawShadow(gl);
		drawOn(gl);
		drawOff(gl);
		drawText(gl);
		//bounds.draw(gl);
	}
	
	private void drawText(GL2 gl) {
		GLUT glut = new GLUT();
		gl.glColor3f(colorText.r, colorText.g, colorText.b);
		if (!isActive) {
			gl.glRasterPos2f(position.x + textOffset.x, position.y+textOffset.y);
		} else {
			gl.glRasterPos2f(position.x + textOffset.x + offset.x, position.y+textOffset.y + offset.y);
		}
		glut.glutBitmapString(font, name);
	}

	private void drawOn(GL2 gl) {
		if (isActive) {
			gl.glBegin(GL2.GL_QUADS);
			gl.glColor3f(colorOn.r, colorOn.g, colorOn.b);
			for (Point p : points) {
				gl.glVertex2f(p.x + position.x + offset.x, p.y + position.y + offset.y);
				// System.out.println(p.toString());
			}
			gl.glEnd();
		}
	}

	private void drawShadow(GL2 gl) {
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(colorShadow.r, colorShadow.g, colorShadow.b);
		for (Point p : points) {
			gl.glVertex2f(p.x + position.x + offset.x, p.y + position.y + offset.y);
			// System.out.println(p.toString());
		}
		gl.glEnd();

	}

	private void drawOff(GL2 gl) {
		if (!isActive) {
			gl.glBegin(GL2.GL_QUADS);
			gl.glColor3f(colorOff.r, colorOff.g, colorOff.b);
			for (Point p : points) {
				gl.glVertex2f(p.x + position.x, p.y + position.y);
				// System.out.println(p.toString());
			}
			gl.glEnd();
		}
	}
	
	public void toggle() {
		if (isActive) {
			isActive = false;
			feature.turnOff();
		} else {
			isActive = true;
			feature.turnOn();
		}

		//isActive = (isActive)?false:true;
	}

}
