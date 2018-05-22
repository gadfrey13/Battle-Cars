package myGameEngine.CameraControl;

import myGameEngine.MyCamera;
import ray.input.GenericInputManager;
import ray.input.InputManager;
import ray.input.action.Action;
import ray.rml.Vector3f;

public class CameraControl {
	//KeyBoard Data Fields
	private Action backward;
	private Action forward;
	private Action left;
	private Action right;
	private Action pitchUp,pitchDown;
	private Action yawLeft,yawRight;
	
	//GamePad Data Fields
	private Action forwardBackward;
	private Action leftRight;
	private Action pitch;
	private Action yaw;
	
	//MyCamera DataFields
	private MyCamera camera;
	
	//Input Manager
	private InputManager im;
	
	public CameraControl(MyCamera camera, InputManager im){
		this.camera = camera;
		this.im = im;

	}
	/**
	 * Setup the keyboard control for the camera.
	 */
	public void setUpKeyBoard(){
    	String kbName = im.getKeyboardName();
    	try{
    		backward = new MoveBackwardAction(camera.getCamera());
    		forward = new MoveForwardAction(camera.getCamera());
    		left = new MoveLeftAction(camera.getCamera());
    		right = new MoveRightAction(camera.getCamera());
    		yawLeft = new YawLeftAction(camera.getCamera());
    		yawRight = new YawRightAction(camera.getCamera());
    		pitchUp = new PitchUpAction(camera.getCamera());
    		pitchDown = new PitchDownAction(camera.getCamera());
    		
    		
    		//MoveForward
        	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.W,forward, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        		
        	//MoveBackward
        	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.S,backward, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	
        	//MoveLeft
        	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.A,left, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	
        	//MoveRight
        	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.D,right, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	
        	//MoveYawLeft
        	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.LEFT,yawLeft, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	
        	//MoveYawRight
        	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.RIGHT,yawRight, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	
        	//MovePitchDown
        	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.DOWN,pitchDown, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	//MovePitchUp
        	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.UP,pitchUp, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
    		
    	}catch(Exception e){
    		System.out.println(e.getClass() + " " + "KeyBoard Not Found");
    	}
    	
		
	}
	
	public void setUpGamePad(){
		String gpName = im.getFirstGamepadName();
		try{
			forwardBackward = new MoveForwardBackwardAction(camera.getCamera());
	    	leftRight = new MoveRightLeftAction(camera.getCamera());
	    	pitch = new PitchAction(camera.getCamera());
	    	yaw = new YawAction(camera.getCamera());
	    	
	    	//MoveForwardBackward Control
	    	im.associateAction(gpName,net.java.games.input.Component.Identifier.Axis.Y,forwardBackward,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	  
	    	//MoveRightLeft Control
	    	im.associateAction(gpName,net.java.games.input.Component.Identifier.Axis.X,leftRight,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	
	    	//MovePitch
	    	im.associateAction(gpName,net.java.games.input.Component.Identifier.Axis.RZ,pitch,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	
	    	//MoveYaw
	    	im.associateAction(gpName,net.java.games.input.Component.Identifier.Axis.Z,yaw,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	
		}catch(Exception e){
			System.out.println(e.getClass() + " " + "GamePad Not Found");
		}
	
	}
	
	public InputManager getInputManager(){
		return im;
	}
	
	/**
	 * Return MyCamera
	 * @return
	 */
	public MyCamera getMyCamera(){
		return camera;
	}
	
}
