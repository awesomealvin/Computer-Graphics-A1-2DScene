package assignment;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class MovableObject {

	public Point velocity = new Point(0f, 0f);
	protected Point position = new Point(0f, 0f);
	
	protected boolean hasGravity = false;
	protected float gravityScale = -0.01f;
	
	protected boolean hasFriction = false;
	protected float frictionScale = 0.005f;
	
	protected void move(double deltaTime) {
		updateVelocity(deltaTime);
		this.position.add(velocity);
	}
	
	private void updateVelocity(double deltaTime) {
		float gravityScale = this.gravityScale;
		if (!hasGravity) {
			gravityScale = 0f;
		}
		
		float frictionScale = this.frictionScale;
		if (!hasFriction) {
			frictionScale = 0f;
		}

		Point force = new Point((float) (frictionScale * deltaTime), (float) (gravityScale * deltaTime));
		velocity.add(force);
	}
	
	public void resetVelocity() {
		this.velocity = new Point(0f, 0f);
	}
}
