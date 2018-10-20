package assignment;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class Spaceship extends MovableObject implements Drawable, Toggleable{
	
	Circle cockpit;
	Square[] lights;
		
	Point[] bodyPoints;
	
	private Point cockpitPosition = new Point(0f, 0.08f);
	
	private boolean on = false;

	private float minSpawnX;
	private float minSpawnY;
	private float maxSpawnX;
	private float maxSpawnY;
	
	
	public Spaceship(float minSpawnX, float maxSpawnX, float minSpawnY,  float maxSpawnY) {
		this.minSpawnX = minSpawnX;
		this.minSpawnY = minSpawnY;
		this.maxSpawnX = maxSpawnX;
		this.maxSpawnY = maxSpawnY;
		
		this.hasGravity = false;
		this.hasFriction = false;
		this.velocity = new Point(0.01f, 0f);

		init();
	}

	private void init() {
		initializeCockpit();
		initializeBodyPoints();
		initializeLights();
		
		this.position.x = 2.5f;
	}
	
	private Point randomizeSpawnPoint() {
		float x = RandomRange.randomRange(minSpawnX, maxSpawnX);
		float y = RandomRange.randomRange(minSpawnY, maxSpawnY);
		Point p = new Point(x, y);
		
		// Main Position
		this.position.x = p.x;
		this.position.y = p.y;
		
		// Cockpit Position
		cockpit.position.x = cockpitPosition.x;
		cockpit.position.y = cockpitPosition.y;
		cockpit.position.add(p);
		
		initializeLights();
		
		return p;
	}
	
	
	private void initializeBodyPoints() {
		bodyPoints = new Point[5];
		
		// Center
		bodyPoints[0] = new Point(0f, 0f);
		
		// Top
		bodyPoints[1] = new Point(0f, 0.1f);
		
		// Right
		bodyPoints[2]= new Point(0.3f, 0f);
		
		// Bottom
		bodyPoints[3] = new Point(0f, -0.075f);
		
		// Left
		bodyPoints[4] = new Point(-0.3f, 0f);
	}
	
	private void initializeLights() {
		if (lights == null) {
			lights = new Square[8];
		}
		Color color = new Color(255, 199, 0);
		float size = 0.015f;
		
		float padding = 0.04f;
		
		float currentPosition = -(padding*2);
		
		for (int i = 0; i < lights.length/2; i++) {
			Point pos = new Point(currentPosition, -(size/2));
			pos.add(this.position);
			lights[i] = new Square(size, color, pos);
			
			currentPosition -= size + padding;
		}
		
		currentPosition = (padding*2) - size;
		for (int i = 4; i < lights.length; i++) {
			Point pos = new Point(currentPosition, -(size/2));
			pos.add(this.position);
			lights[i] = new Square(size, color, pos);
			
			currentPosition += size + padding;
		}
	}
	
	private void initializeCockpit() {
		Point position = new Point(cockpitPosition.x, cockpitPosition.y);
		position.add(this.position);
		Color color = new Color(5, 205, 198, 0.85f);
		cockpit = new Circle(0.075f, color, position);
	}

	@Override
	public void update(double deltaTime) {
		if (on || this.position.x < 2f) {
			move(deltaTime);
			cockpit.velocity = new Point(this.velocity.x, this.velocity.y);
			cockpit.move(deltaTime);
			
			for (Square l : lights) {
				l.velocity = new Point(this.velocity.x, this.velocity.y);
				l.move(deltaTime);
			}
			
			if (on && this.position.x >= 2f) {
				randomizeSpawnPoint();
			}
		}
	}

	@Override
	public void draw(GL2 gl) {
		if (on || this.position.x < 2f) {
			drawBody(gl);
			//upperBody.draw(gl);
			//lowerBody.draw(gl);
			cockpit.draw(gl);
			
			for (int i = 0; i < lights.length; i++) {
				lights[i].draw(gl);
			}
		}
		
	}
	
	private void drawBody(GL2 gl) {
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		
		// Colors and Gradient
		Color dark = new Color(55, 55, 55);
		Color light = new Color(90, 90, 90);
		
		// Triangular Spaceship with a point for each corner.
		// Adds the position to draw the spaceship in the correct position
		Point centerPoint = new Point(bodyPoints[0].x, bodyPoints[0].y);
		centerPoint.add(position);
		
		Point topPoint = new Point(bodyPoints[1].x, bodyPoints[1].y);
		topPoint.add(position);
		
		Point rightPoint = new Point(bodyPoints[2].x, bodyPoints[2].y);
		rightPoint.add(position);
		
		Point bottomPoint = new Point(bodyPoints[3].x, bodyPoints[3].y);
		bottomPoint.add(position);
		
		Point leftPoint = new Point(bodyPoints[4].x, bodyPoints[4].y);
		leftPoint.add(position);
		
		gl.glColor3f(light.r, light.g, light.b);
		gl.glVertex2f(centerPoint.x, centerPoint.y);
		
		gl.glColor3f(dark.r, dark.g, dark.b);
		gl.glVertex2f(topPoint.x, topPoint.y);
		gl.glVertex2f(rightPoint.x, rightPoint.y);
		
		gl.glColor3f(light.r, light.g, light.b);
		gl.glVertex2f(centerPoint.x, centerPoint.y);
		
		gl.glColor3f(dark.r, dark.g, dark.b);
		gl.glVertex2f(rightPoint.x, rightPoint.y);
		gl.glVertex2f(bottomPoint.x,  bottomPoint.y);
		
		gl.glColor3f(light.r, light.g, light.b);
		gl.glVertex2f(centerPoint.x, centerPoint.y);
		
		gl.glColor3f(dark.r, dark.g, dark.b);
		gl.glVertex2f(bottomPoint.x, bottomPoint.y);
		gl.glVertex2f(leftPoint.x, leftPoint.y);
		
		gl.glColor3f(light.r, light.g, light.b);
		gl.glVertex2f(centerPoint.x, centerPoint.y);
		
		gl.glColor3f(dark.r, dark.g, dark.b);
		gl.glVertex2f(leftPoint.x, leftPoint.y);
		gl.glVertex2f(topPoint.x, topPoint.y);
		
		
		gl.glEnd();
	}

	@Override
	public void turnOn() {
		on = true;
	}

	@Override
	public void turnOff() {
		on = false;
		
	}
}
