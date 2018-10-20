package assignment;


/**
 * 
 * @author Alvin Tang 15909204
 *
 */
import com.jogamp.opengl.GL2;

public interface Drawable {
	public void update(double deltaTime);
	public void draw(GL2 gl);
}
