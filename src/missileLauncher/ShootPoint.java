package missileLauncher;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import assignment.Asteroid;
import assignment.Drawable;
import assignment.Particle;
import assignment.Point;
import assignment.RandomRange;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class ShootPoint implements Drawable{
	
	private float shootingDelay = 0.6f;
	private float currentTime = 0f;
	private float force = 0.01f;
	
	private Point position;
	
	private ArrayList<Missile> missiles = new ArrayList<Missile>();
	private ArrayList<Particle> asteroids;
	
	public ShootPoint(Point position, ArrayList<Particle> asteroids) {
		this.position = position;
		this.asteroids = asteroids;
	}
	
	public void shoot(float angle) {
		// Calculate Vector Angle
		double radians = Math.toRadians(angle);
		double hyp = 0.1f;
		float x = (float) (Math.cos(radians) * hyp);
		float y = (float) (Math.sin(radians) * hyp);
		Point velocity = new Point(x, y);
		velocity = velocity.normalized();
		velocity.multiply(force);

		Missile missile = new Missile(new Point(position.x, position.y), velocity);
		missiles.add(missile);
	}

	@Override
	public void update(double deltaTime) {
		ArrayList<Missile> toExplode = new ArrayList<Missile>();
		for (Missile m : missiles) {
			ArrayList<Particle> toDestroy = new ArrayList<Particle>();
			m.update(deltaTime);
			for (Particle a : asteroids) {
				Asteroid asteroid = (Asteroid) a;
				if (m.bounds.collision(asteroid.bounds)) {
					toDestroy.add(a);
					toExplode.add(m);
				}
			}
			
			for (Particle p : toDestroy) {
				asteroids.remove(p);
			}
		}
		
		for (Missile m : toExplode) {
			missiles.remove(m);
		}
		
		currentTime += deltaTime;
		if (currentTime >= shootingDelay) {
			currentTime = 0f;
			
			float angle = RandomRange.randomRange(15f, 165f);
//			angle = 40f;
			shoot(angle);
		}
	}

	@Override
	public void draw(GL2 gl) {
		for (Missile m : missiles) {
			m.draw(gl);
		}
		
	}
}
