package assignment;

import com.jogamp.opengl.GL2;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class Snow extends Particle{
	
	
	float size = 0.02f;
	
	public Snow(Point position, float gravityScale, boolean hasGravity) {
		super(position, gravityScale, hasGravity);
		this.color = new Color(1f, 1f, 1f);
	}
	
	@Override
	public void draw(GL2 gl) {
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(color.getR(), color.getG(), color.getB());
		gl.glVertex2f(position.getX(), position.getY());
		gl.glVertex2f(position.getX() + size, position.getY());
		gl.glVertex2f(position.getX()+ size, position.getY() + size);
		gl.glVertex2f(position.getX(), position.getY()+size);
		gl.glEnd();
	}
	
	@Override
	public void update(double deltaTime) {
		super.update(deltaTime);
	}


}
