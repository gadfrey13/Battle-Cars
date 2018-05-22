package myGameEngine.CameraControl.ObjectControl;

import a1.GameObject.GameObject;
import myGameEngine.MyCamera;
import myGameEngine.MyPhysicsObject;
import myGameEngine.ProtocolClient;
import myGameEngine.CameraControl.MoveBackwardAction;
import myGameEngine.CameraControl.MoveForwardAction;
import myGameEngine.CameraControl.MoveLeftAction;
import myGameEngine.CameraControl.MoveRightAction;
import myGameEngine.CameraControl.PitchDownAction;
import myGameEngine.CameraControl.PitchUpAction;
import myGameEngine.CameraControl.YawLeftAction;
import myGameEngine.CameraControl.YawRightAction;
import ray.input.InputManager;
import ray.input.action.Action;
import ray.rage.Engine;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneNode;
import ray.rml.Vector3f;

public class ObjectControl {
	//private data fields
	private MyCamera camera;
	private GameObject object;
	private InputManager im;
	
	//GamePad data fields
	private Action forwardBackward;
	private Action leftRight;
	private Action pitch;
	private Action yaw;
	
	//KeyBoard data fields
	private Action backward;
	private Action forward;
	private Action left;
	private Action right;
	private Action pitchUp,pitchDown;
	private Action yawLeft,yawRight;
	private Vector3f camObjectDistance;
	//Fire Missile
	private Action fire;
	//private String gpName;
	//private String kbName;
	private Camera3Pcontroller control;
	//
	private String gpName,kbName;
	//Protocol
	private ProtocolClient protclient;
	//Engine
	private Engine eng;
	//Physics Object
	private MyPhysicsObject physicsObject;
	public ObjectControl(MyCamera camera, GameObject object, InputManager im,String kbName, String gpName){
		this.camera = camera;
		this.object = object;
		this.im = im;
		this.camObjectDistance = (Vector3f) Vector3f.createFrom(0.0f, -.8f, -2.5f);
		this.gpName = gpName;
		this.kbName = kbName;
		System.out.println(kbName);
		System.out.println(gpName);
		//this.kbName = kbName;
		//this.gpName = gpName;
	}
	
	public ObjectControl(MyCamera camera, GameObject object, InputManager im){
		this.camera = camera;
		this.object = object;
		this.im = im;
		this.gpName = im.getFirstGamepadName();
		this.kbName = im.getKeyboardName();
	}
	
	
	public ObjectControl(MyCamera camera, GameObject object, InputManager im,Engine eng){
		this.camera = camera;
		this.object = object;
		this.im = im;
		this.gpName = im.getFirstGamepadName();
		this.kbName = im.getKeyboardName();
		this.eng = eng;
	}
	
	public ObjectControl(MyCamera camera, GameObject object, InputManager im, ProtocolClient protclient ){
		this.camera = camera;
		this.object = object;
		this.im = im;
		this.gpName = im.getFirstGamepadName();
		this.kbName = im.getKeyboardName();
		this.protclient = protclient;
	}
	
	public ObjectControl(MyCamera camera, GameObject object, InputManager im, ProtocolClient protclient,Engine eng ){
		this.camera = camera;
		this.object = object;
		this.im = im;
		this.gpName = im.getFirstGamepadName();
		this.kbName = im.getKeyboardName();
		this.protclient = protclient;
		this.eng = eng;
	}
	
	
	public ObjectControl(MyCamera camera, GameObject object, InputManager im, ProtocolClient protclient,MyPhysicsObject physicsObject ){
		this.camera = camera;
		this.object = object;
		this.im = im;
		this.gpName = im.getFirstGamepadName();
		this.kbName = im.getKeyboardName();
		//this.kbName = "PC/AT Enhanced PS/2 Keyboard (101/102-Key)";
		this.protclient = protclient;
		this.physicsObject = physicsObject;
	}
	
	
	/**
	 * Setup the keyboard control for the camera.
	 */
	public void setUpKeyBoard(){

    	try{
    		if(kbName.equals("PC/AT Enhanced PS/2 Keyboard (101/102-Key)")){
    			System.out.println("This is the same");
    		}
    		backward = new MoveBackwardNodeAction(camera.getCamera(),object.getNode(),protclient,this.physicsObject);
    		forward = new MoveForwardNodeAction(camera.getCamera(),object,protclient,this.physicsObject);
    		left = new MoveLeftNodeAction(camera.getCamera(),object.getNode(),camObjectDistance);
    		right = new MoveRightNodeAction(camera.getCamera(),object.getNode(),camObjectDistance);
    		yawLeft = new MoveLeftYawNodeAction(camera.getCamera(),object.getNode(),protclient,this.physicsObject);
    		yawRight = new MoveRightYawNodeAction(camera.getCamera(),object.getNode(),protclient,this.physicsObject);
    		pitchUp = new MoveUpPitchNodeAction(camera.getCamera(),object.getNode(),camObjectDistance);
    		pitchDown = new MoveDownPitchNodeAction(camera.getCamera(),object.getNode(),camObjectDistance);
    		fire = new Fire(camera.getCamera(),object,protclient,this.physicsObject);
    		
    		//MoveForward
        	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.W,forward, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        		
        	//MoveBackward
        	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.S,backward, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	
        	//MoveLeft
        	//im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.A,left, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	
        	//MoveRight
        	//im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.D,right, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	
        	//MoveYawLeft
        	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.A,yawLeft, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	
        	//MoveYawRight
        	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.D,yawRight, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	
        	//Fire 
        	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.SPACE,fire, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
        	
        	//MovePitchDown
        	//im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.DOWN,pitchDown, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	//MovePitchUp
        	//im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.UP,pitchUp, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
    		
    	}catch(Exception e){
    		System.out.println(e.getClass() + " " + "KeyBoard Not Found");
    	}
	}
	
	public void setUpGamePad(){
		
		try{
			//forwardBackward =new MoveForwardBackwardNodeAction(camera.getCamera(), object.getNode());
			forward = new MoveForwardNodeAction(camera.getCamera(), object.getNode());
			backward = new MoveBackwardNodeAction(camera.getCamera(), object.getNode());
		    leftRight = new MoveRightLeftNodeAction(camera.getCamera(), object.getNode());
	    	pitch = new MovePitchNodeAction(camera.getCamera(),object.getNode());
	    	yaw = new MoveYawNodeAction(camera.getCamera(),object.getNode());
		   //MoveForwardAndBackward
	    	im.associateAction(gpName,net.java.games.input.Component.Identifier.Button._7,forward,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	//
	    	im.associateAction(gpName,net.java.games.input.Component.Identifier.Button._6,backward,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	//MoveRightLeft Control
	    	//im.associateAction(gpName,net.java.games.input.Component.Identifier.Axis.X,leftRight,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	      	//MovePitch
	    	//im.associateAction(gpName,net.java.games.input.Component.Identifier.Axis.RZ,pitch,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	//MoveYaw
	    	im.associateAction(gpName,net.java.games.input.Component.Identifier.Axis.X,yaw,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		}catch(Exception e){
			System.out.print(e.getClass() + " " + "GamePad Not Found");
		}
	}
	
	public void cameraToObjectDistance(float x, float y, float z){
		this.camObjectDistance = (Vector3f) Vector3f.createFrom(x,y,z);
	}
	
	public InputManager getInputManager(){
		return im;
	}
	
	public String getGPName(){
		return gpName;
	}
	
	public String getKBName(){
		return kbName;
	}
}
