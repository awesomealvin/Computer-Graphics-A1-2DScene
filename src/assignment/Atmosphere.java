package assignment;

import com.jogamp.opengl.GL2;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class Atmosphere implements Drawable, Toggleable {

	
	private Color[] atmosphereColor;

	private boolean on = false;


	public Atmosphere(Color[] atmosphereColor) {
		this.atmosphereColor = atmosphereColor;
	}

	@Override
	public void update(double deltaTime) {
	
	}
	

	@Override
	public void draw(GL2 gl) {
		if (!on) {
			return;
		}
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(atmosphereColor[0].getR(), atmosphereColor[0].getG(), atmosphereColor[0].getB(), atmosphereColor[0].getA());
		gl.glVertex2f(1f, -1f);
		gl.glVertex2f(-1f, -1f);
		gl.glColor4f(atmosphereColor[1].getR(), atmosphereColor[1].getG(), atmosphereColor[1].getB(), atmosphereColor[1].getA());
		gl.glVertex2f(-1f, 1f);
		gl.glVertex2f(1f, 1f);
		gl.glEnd();
		gl.glDisable(GL2.GL_BLEND);
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
