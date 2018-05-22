package myGameEngine.CameraControl.ObjectControl;

import java.awt.Component;

import net.java.games.input.Event;
import net.java.games.input.Component.Identifier;
import ray.input.InputManager;
import ray.input.action.AbstractInputAction;
import ray.input.action.Action;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneNode;
import ray.rml.Degreef;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class Camera3Pcontroller {
	private Camera camera; // the camera being controlled
	private SceneNode cameraN; // the node the camera is attached to
	private SceneNode target; // the target the camera looks at
	private float cameraAzimuth; // rotation of camera around Y axis
	private float cameraElevation; // elevation of camera above target
	private float radias; // distance between camera and target
	private Vector3 targetPos; // target’s position in the world
	private Vector3 worldUpVec;
	private boolean bol;
	private String controlName;
	private int controlType;
	public Camera3Pcontroller(Camera cam, SceneNode camN, SceneNode targ, String controllerName, InputManager im,int controlType) {
		camera = cam;
		cameraN = camN;
		target = targ;
		cameraAzimuth = 225.0f; // start from BEHIND and ABOVE the target
		cameraElevation = 20.0f; // elevation is in degrees
		radias = 3.0f;
		worldUpVec = Vector3f.createFrom(0.0f, 1.0f, 0.0f);
		this.setupInput(im, controllerName);
		this.setupInputTwo(im,controllerName);
		this.updateCameraPosition();
		this.controlName = controllerName;
		System.out.println(controllerName);
		this.controlType = controlType;
		
	}
	
	public void updateCameraPosition() {
		double theta = Math.toRadians(cameraAzimuth); // rot around target
		double phi = Math.toRadians(cameraElevation); // altitude angle
		double x = 1 * radias * Math.cos(phi) * Math.sin(theta);
		double y = radias * Math.sin(phi);
		double z = 1 * radias * Math.cos(phi) * Math.cos(theta);
		cameraN.setLocalPosition(Vector3f.createFrom((float) x, (float) y, (float) z).add(target.getWorldPosition()));
		cameraN.lookAt(target, worldUpVec);
	}
	
	private void setupInput(InputManager im, String cn) {
	
		try{
			Action orbitAAction = new OrbitAroundAction();
			Action zoomAction = new ZoomInOutAction();
			Action toogle = new Toogle();
			im.associateAction(cn, net.java.games.input.Component.Identifier.Axis.Z, orbitAAction,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		// similar input set up for OrbitRadiasAction, OrbitElevationAction
			im.associateAction(cn, net.java.games.input.Component.Identifier.Axis.RZ, zoomAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
			//Toogle Button
			im.associateAction(cn, net.java.games.input.Component.Identifier.Button._5, toogle, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
		}catch(Exception e){
			System.out.println("Could Not Find Game Pad");
		}
		
		
		/*
		if(cn.equals("kbName")){
			Action orbitAAction = new OrbitAroundAction();
			Action zoomAction = new ZoomInOutAction();
			Action toogle = new Toogle();
			im.associateAction(cn, net.java.games.input.Component.Identifier.Key.LEFT, orbitAAction,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
			//im.associateAction(cn, net.java.games.input.Component.Identifier.Key.RIGHT, orbitAAction,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);		
			// similar input set up for OrbitRadiasAction, OrbitElevationAction
			im.associateAction(cn, net.java.games.input.Component.Identifier.Axis.RZ, zoomAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
			//Toogle Button
			im.associateAction(cn, net.java.games.input.Component.Identifier.Button._5, toogle, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
		}
		*/

	}
	
	private void setupInputTwo(InputManager im, String cn){

		try{
			Action orbitAAction = new OrbitAroundAction();
			Action zoomAction = new ZoomInOutAction();
			Action toogle = new Toogle();
			im.associateAction(cn, net.java.games.input.Component.Identifier.Key.LEFT, orbitAAction,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
			im.associateAction(cn, net.java.games.input.Component.Identifier.Key.RIGHT, orbitAAction,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		// similar input set up for OrbitRadiasAction, OrbitElevationAction
			im.associateAction(cn, net.java.games.input.Component.Identifier.Key.UP, zoomAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
			im.associateAction(cn, net.java.games.input.Component.Identifier.Key.DOWN, zoomAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
			//Toogle Button
			im.associateAction(cn, net.java.games.input.Component.Identifier.Key.SPACE, toogle, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
		}catch(Exception e){
			System.out.println("Could Not Find KeyBoard");
		}
		
	}
	
	private class OrbitAroundAction extends AbstractInputAction { // Moves the camera around the target (changes
																// camera azimuth).
		public OrbitAroundAction(){
			System.out.println("What");
		}
		@Override
		public void performAction(float time, net.java.games.input.Event evt) {
			System.out.println("Event Value: " + evt.getValue());
			System.out.println("Camera Scene Node" + cameraN.getLocalPosition());
			System.out.println("Camera " + camera.getPo());
			//System.out.println(evt.getComponent());
		
			
			float rotAmount = 0.0f;
			if (controlName.equals("gpName") || controlType == 1) {
				if (evt.getValue() < 0.2) {
					rotAmount = -0.7f;
				} else {
					if (evt.getValue() > -0.2) {
						rotAmount = 0.7f;
					} else {
						rotAmount = 0.0f;
					}
				}

				if(bol){
					float a = 0.0f;
					if(evt.getValue() > -0.2f){
						a = 0.9f;
					}else if(evt.getValue() < 0.2f){
						a = -0.9f;
					}
					target.yaw(Degreef.createFrom(a));
				}
			}
			
			if(controlType == 0){
				if (evt.getComponent().getIdentifier().getName() == "Right") {
					rotAmount = -0.7f;
				} else {
					if (evt.getComponent().getIdentifier().getName() == "Left") {
						rotAmount = 0.7f;
					} else {
						rotAmount = 0.0f;
					}
				}
				if(bol){
					float a = 0.0f;
					if(evt.getComponent().getIdentifier().getName() == "Right"){
						a = -0.9f;
					}else if(evt.getComponent().getIdentifier().getName() == "Left"){
						a = 0.9f;
					}
					target.yaw(Degreef.createFrom(a));
				}
			}
		

			cameraAzimuth += rotAmount;
			cameraAzimuth = cameraAzimuth % 360;
			updateCameraPosition();
		}
	}
	
	private class ZoomInOutAction extends AbstractInputAction{

		@Override
		public void performAction(float time, Event e) {
			float radius = 0.0f;
			if(controlType == 1){
			if (radias > 1) {
				if (e.getValue() < 0.2) {

					radius = -0.04f;
				} else {
					if (e.getValue() > -0.2) {
						radius = 0.04f;
					} else {
						radius = 0.0f;
					}
				}
			}else if (radias < 1) {
				if (e.getValue() > -0.2) {
					radius = 0.04f;
				} else {
					radius = 0.0f;
				}

			}
			}
			if(controlType == 0){
			if (radias > 1) {
				if (e.getComponent().getIdentifier().getName() == "Up") {

					radius = -0.04f;
				} else {
					if (e.getComponent().getIdentifier().getName() == "Down") {
						radius = 0.04f;
					} else {
						radius = 0.0f;
					}
				}
			}else if (radias < 1) {
				if (e.getComponent().getIdentifier().getName() == "Down") {
					radius = 0.04f;
				} else {
					radius = 0.0f;
				}

			}
			}
			
			System.out.print("Zoom Event " + e);

			radias += radius;
			updateCameraPosition();
		}
		
	}
	
	public class Toogle extends AbstractInputAction {
		private int count = 0;
		
		@Override
		public void performAction(float arg0, Event arg1) {
			System.out.println("Toogle " + bol);
			if(count == 0){
				bol = true;
				count++;
			}else{
				bol = false;
				count--;
			}
		}
	
	}
}
	
