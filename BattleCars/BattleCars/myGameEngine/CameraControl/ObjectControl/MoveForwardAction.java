package myGameEngine.CameraControl.ObjectControl;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.SceneNode;

public class MoveForwardAction extends AbstractInputAction{
	private SceneNode node;
	public MoveForwardAction(SceneNode node) {
		this.node = node;
	}
	@Override
	public void performAction(float arg0, Event arg1) {
		System.out.println("Orbital Move Forward Action");
		node.moveForward(0.01f);
	}

}