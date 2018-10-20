package assignment;
import com.jogamp.opengl.GL2;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class ButtonPanel implements Drawable{
	
	private float height;
	private Color color;
	
	private Button[] buttons;
	
	private float widthPerButton;
	private float heightPerButton;
	private float padding = 0.03f;
	
	public ButtonPanel(float height, Color color, Button[] buttons) {
		this.height = height;
		this.color = color;
		this.buttons = buttons;
		
		initializeButtons();
	}
	
	private void initializeButtons() {
		widthPerButton = (2f/buttons.length) - padding;
		heightPerButton = height - (padding*2);
		
		float currentPosX = -1f + padding;
		
		for (Button b : buttons) {
			b.width = widthPerButton - (b.offsetValue*2f);
			b.height = heightPerButton - b.offsetValue;
			
			float x = currentPosX;
			float y = 1f - height + padding + b.offsetValue;
			b.position = new Point(x, y);
			
			b.init();
			
			currentPosX += widthPerButton + padding;
		}
	}

	@Override
	public void update(double deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(GL2 gl) {
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(color.getR(), color.getG(), color.getB());
		gl.glVertex2f(-1f, 1f);
		gl.glVertex2f(1f, 1f);
		gl.glVertex2f(1f, 1f-height);
		gl.glVertex2f(-1f, 1f-height);
		gl.glEnd();
		
		for (Button b : buttons) {
			b.draw(gl);
		}
	}
	
	public void handleClick(Point clickPosition) {
		for (Button b : buttons) {
			if (b.bounds.contains(clickPosition)) {
				b.toggle();
			}
		}
	}
	
	
}

