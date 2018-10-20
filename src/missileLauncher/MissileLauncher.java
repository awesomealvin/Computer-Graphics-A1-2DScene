package missileLauncher;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import assignment.Asteroid;
import assignment.Drawable;
import assignment.Particle;
import assignment.Point;
import assignment.Toggleable;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class MissileLauncher implements Drawable, Toggleable {

	private boolean on;

	LauncherBase base;
	Barrel barrel;

	private Point position;

	private ArrayList<Particle> asteroids;

	public MissileLauncher(ArrayList<Particle> particles) {
		this.asteroids = particles;

		init();
	}

	@Override
	public void update(double deltaTime) {
		if (on) {
			barrel.update(deltaTime);
		}
	}

	private void init() {
		position = new Point(-0f, -0.9f);
		base = new LauncherBase(position);
		barrel = new Barrel(position, asteroids);
	}

	@Override
	public void draw(GL2 gl) {
		if (on) {
			barrel.draw(gl);
			base.draw(gl);
		}
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
