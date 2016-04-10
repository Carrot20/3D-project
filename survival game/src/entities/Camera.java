package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.*;

public class Camera {

	private float distanceFromPlayer = -0.999999f;
	private float angleAroundPlayer = 0;

	private Vector3f position = new Vector3f(100, 15, 50);
	private float pitch;
	private float yaw;
	private float roll;

	private Player player;

	public Camera(Player player) {
		this.player = player;
	}

	public void move() {
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance()+3;
		calculateCameraPosition(horizontalDistance, verticalDistance);
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		if(Keyboard.isKeyDown(Keyboard.KEY_F5)){
			distanceFromPlayer = 50;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F4)){
			distanceFromPlayer = -0.99999999999f;
		}
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verticDistance + 9;
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}

	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}

	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}

	/*private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;
	}*/

	private void calculatePitch() {
		float dy = Mouse.getDY();
		float pitchChange = 0;
		if (dy>0 && pitch>-90) {
			pitchChange+=dy / 7;
			pitch -= pitchChange;
		}
		if(dy<0&&pitch<60){
			pitchChange+= dy / 7;
			pitch -= pitchChange;
		}
		dy = 0;
	}

	private void calculateAngleAroundPlayer() {
		if (Mouse.isButtonDown(0)) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}

	public void invertPitch() {
		this.pitch = -pitch;

	}

}
