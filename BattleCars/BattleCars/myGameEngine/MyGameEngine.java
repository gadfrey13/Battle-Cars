package myGameEngine;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;
import java.util.Vector;

import a1.GameObject.AmmoPack;
import a1.GameObject.Barrier;
import a1.GameObject.Bomb;
import a1.GameObject.GameObject;
import a1.GameObject.GhostNPC;
import a1.GameObject.Player;
import a1.GameObject.Scripting;
import a1.GameWorld.MySkyBox;
import a1.GameWorld.Terrain;
import myGameEngine.CameraControl.CameraControl;
import myGameEngine.CameraControl.ObjectControl.Camera3Pcontroller;
import myGameEngine.CameraControl.ObjectControl.ObjectControl;
import myGameEngine.CameraControl.ObjectControl.Toggle;
import net.java.games.input.Event;
import ray.audio.AudioManagerFactory;
import ray.audio.IAudioManager;
import ray.input.GenericInputManager;
import ray.input.InputManager;
import ray.input.action.AbstractInputAction;
import ray.networking.IGameConnection.ProtocolType;
import ray.rage.Engine;
import ray.rage.asset.material.Material;
import ray.rage.asset.texture.Texture;
import ray.rage.game.VariableFrameRateGame;
import ray.rage.rendersystem.RenderSystem;
import ray.rage.rendersystem.RenderWindow;
import ray.rage.rendersystem.Renderable.Primitive;
import ray.rage.rendersystem.states.RenderState;
import ray.rage.rendersystem.states.TextureState;
import ray.rage.scene.Camera;
import ray.rage.scene.Entity;
import ray.rage.scene.Light;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.rage.scene.SceneObject;
import ray.rage.scene.SkeletalEntity;
import ray.rage.scene.Tessellation;
import ray.rml.Angle;
import ray.rml.Degreef;
import ray.rml.Matrix3;
import ray.rml.Matrix4;
import ray.rml.Matrix4f;
import ray.rml.Vector3;
import ray.rml.Vector3f;
import static ray.rage.scene.SkeletalEntity.EndType.*;
import com.jogamp.openal.ALFactory;
import ray.audio.*;
public class MyGameEngine extends VariableFrameRateGame {
	private MySkyBox skybox;
	private MyCamera mycamera;
	private Terrain hill;
	private InputManager im;
	private float elapseTime;
	// Networking
	private String serverAddress;
	private int serverPort;
	private ProtocolType serverProtocol;
	private ProtocolClient protClient;
	private boolean isClientConnected;
	private Vector<UUID> gameObjectsToRemove;
	// SceneManager
	private SceneManager sm;
	// Copy of avatar
	private Vector<Player> ghostAvatar;// For all the game
	//My Avatar
	private GameObject myAvatar;
	//Game Controller
	private ObjectControl control;
	//Camera Orbital
	private Camera3Pcontroller orbitController;
	//Delay
	private float delay;
	//Check
	private boolean once = true;
	//Previous Location
	private Vector3 preLocation;
	private GameObject car;
	//PhysicsEngine
	private MyPhysicsEngine physicsEngine;
	//Frame
	private int frame = 0;
	//time delay
	private float lastTickUpdateTime;
	//previous position
	private double[] preMat;
	private float preTime;	
	private Vector objectList;
	private Collider collider;
	private float decTime;
	//Sound
	private MySound sound;
	//Sound Manager
	private IAudioManager audioMgr;
	//car
	private float[] preVelocity;
	//HUB
	private GameHub hub;
	private int color;
	private boolean fullScreen;
	private int texColor;
	private RenderSystem rs;
	public MyGameEngine(String serverAddr, int serverPort) {
		this.serverAddress = serverAddr;
		this.serverPort = serverPort;
		this.serverProtocol = ProtocolType.UDP;
		this.ghostAvatar = new Vector<Player>();
		objectList = new Vector();
		
	}
	

	public void setParams(int c, boolean f)
	{
		color = c;
		fullScreen = f;
	}

	@Override
	protected void setupCameras(SceneManager sm, RenderWindow rw) {
		this.sm = sm;
		// Setup Camera
		mycamera = new MyCamera(sm, rw);
		mycamera.setUpCamera("maincamera");
	
	}

	@Override
	protected void setupScene(Engine eng, SceneManager sm) throws IOException {
		
		audioMgr = AudioManagerFactory.createAudioManager("ray.audio.joal.JOALAudioManager");
		
		
		if (!audioMgr.initialize()) {
			System.out.println("Audio Manager failed to initialize!");
			return;
		}
		
		// Create the skybox
		skybox = new MySkyBox(eng, sm);
		skybox.createSkyBox();
		// Create terrain
		hill = new Terrain(eng, sm);
		hill.defaultTerrain();
		//hill.addTerrain("tree.obj","barrier", 10, 45.0f, 1.80f, 0.0f, 1);
		
		GameObject shuttle = new GameObject(sm,"shuttlebarrier","shuttle.obj","barrier");
		shuttle.create();
		shuttle.addTexture("spstob_1.jpg");
		shuttle.getNode().setLocalPosition(45.0f,1.90f,0.0f);
		shuttle.getNode().scale(3.0f,3.0f,3.0f);
		
		
		
		// Input Manager
		im = new GenericInputManager();
		// Camera control
		//CameraControl camControl = new CameraControl(mycamera, im);
		//camControl.setUpKeyBoard();
		// SetupNetworking
		setUpNetworking();
		// Setup Your Own Avatar
		//myAvatar = new GameObject(sm,"player","carmodelcolor.obj","player");
		myAvatar = new GameObject(sm,"player","carmodelbasicanimationtwo.rkm","carmodelbasicanimationtwo.rks","player",this.objectList,this.audioMgr);
		myAvatar.createAnimate();
		myAvatar.addMaterialAnimated("carmodelcolor.mtl");
		String texturePath = "";
		if (color == 0)
			texturePath = "carmkrtexture.png";
		else if (color == 1)
			texturePath = "car_blue.png";
		else
			texturePath = "car_yellow.png";
		myAvatar.addTextureAnimated(texturePath);
		myAvatar.setupScripts();
		myAvatar.getNode().setLocalPosition(10.3f, 1.65f, 12.0f);
		myAvatar.loadAnimation("turnleft", "carmodelbasicanimationtwo.rka");
		//myAvatar.getNode().setLocalPosition(10.3f, 20.95f, 10.0f);
		myAvatar.getNode().scale(.05f, .05f, .05f);
		
		
		

		
		System.out.println("Local Transform" + myAvatar.getNode().getLocalTransform());
		System.out.println("World Transform" + myAvatar.getNode().getWorldTransform());
	
		
		 //car = new GameObject(sm,"car","carmodel.rkm","carmodel.rks","player");
		//car.createAnimate();
		// Light
		Light sunLight = sm.createLight("sunlight", Light.Type.POINT);
		sunLight.setAmbient(new Color(.4f, .4f, .4f));
		sunLight.setDiffuse(new Color(1.0f, 1.0f, 0.0f));
		sunLight.setSpecular(new Color(1.0f, 1.0f, 1.0f));
		sunLight.setLinearAttenuation(5);
		sunLight.setQuadraticAttenuation(5);
		sunLight.setRange(10f);
		sm.getAmbientLight().setIntensity(new Color(0.0f,0.0f,0.0f));

		SceneNode sunNode = sm.getRootSceneNode().createChildSceneNode("sunNode");
		sunNode.attachObject(sunLight);
		sunNode.setLocalPosition(Vector3f.createFrom(0.0f, 4.0f, 0.0f));
		
		Light newPLight = sm.createLight("pointlight", Light.Type.POINT);
		newPLight.setAmbient(new Color(0.0f,0.0f,0.0f));
		newPLight.setDiffuse(new Color(0.0f,0.0f,0.0f));
		newPLight.setSpecular(new Color(0.0f,0.5f,0.5f));
		newPLight.setQuadraticAttenuation(0);
		newPLight.setLinearAttenuation(0);
		newPLight.setRange(5);
		SceneNode newLight = sm.getRootSceneNode().createChildSceneNode("newLightNode");
		newLight.setLocalPosition(myAvatar.getNode().getLocalPosition());
		newLight.attachObject(newPLight);
		
		//Setting Ground Plane
		// Ground plane
		Entity groundEntity = sm.createEntity("Ground", "cube.obj");
		SceneNode groundNode = sm.getRootSceneNode().createChildSceneNode("GroundNode");
		groundNode.attachObject(groundEntity);
		groundNode.setLocalPosition(0, 1.6f, -2);
		//groundNode.scale(100f, .05f, 100f);
		
		//Texture tex = sm.getTextureManager().getAssetByPath("blue.jpeg");
		//TextureState texState = (TextureState) sm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
		//texState.setTexture(tex);
		//groundEntity.setRenderState(texState);
		
		//Setup OrbitCamera
		setupOrbitCamera(eng,sm);
		
		//Setup Physics Engine
		this.physicsEngine = new MyPhysicsEngine(sm,eng);
		this.physicsEngine.initPhysicsSystem();
		
		//Create Barries
		//Barrier barrier = new Barrier(sm,eng,this.physicsEngine);
		//barrier.init();
		
		//shuttle
		//MyPhysicsObject shuttlephy = new MyPhysicsObject(shuttle.getNode(),"car",this.physicsEngine);
		//shuttlephy.create();
		
		this.objectList.add(shuttle);
		
		//test
		GameObject test = new GameObject(sm,"barrier","planeone.obj","barrier");
		//test.create();
		//test.addTexture("coloredplane.png");
		//test.getNode().setLocalPosition(10.0f,1.85f, 15.0f);
		//this.objectList.add(test);
		
		//Create A PhysicsObject
		MyPhysicsObject carObject = new MyPhysicsObject(myAvatar.getNode(),"car",this.physicsEngine);
		MyPhysicsObject groundObject = new MyPhysicsObject(groundNode,"ground",this.physicsEngine);
		
		groundObject.create();
		carObject.create();

		//Send command to server to create an avatar
		//Add Controller for Game Object
		//control = new ObjectControl(mycamera,myAvatar,im, protClient,eng);
		control = new ObjectControl(mycamera,myAvatar,im, protClient,carObject);
		control.setUpKeyBoard();
		
		myAvatar.getPhysicsEngine(this.physicsEngine);
		
		objectList.add(myAvatar);
		
		collider = new Collider(this.objectList,sm);
		
		myAvatar.soundInit();
		
		//sound = new MySound(sm,this.audioMgr);
		//sound.initAudio();
		
		hub = new GameHub(eng,this.myAvatar);
		
		//Make this full screen
		//sm.getRenderSystem().createRenderWindow(true);
		
		Toggle t = new Toggle(newPLight);
		im.associateAction(im.getKeyboardName(), net.java.games.input.Component.Identifier.Key.P,t, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
		//im.associateAction("PC/AT Enhanced PS/2 Keyboard (101/102-Key)", net.java.games.input.Component.Identifier.Key.P,t, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
		
	}
	
	public int getTextColor(){
		return color;
	}
	
	@Override
	protected void setupWindow(RenderSystem rs, GraphicsEnvironment ge)
	{ 
		this.rs = rs;
		if (fullScreen)
			rs.createRenderWindow(true);
		else
			rs.createRenderWindow(new DisplayMode(1000,700,24,60),false);
	}

	@Override
	protected void update(Engine eng) {
		
		hub.setHub(15, 15);
		
		elapseTime += eng.getElapsedTimeMillis();
		im.update(elapseTime);
		processNetworking(elapseTime);
		orbitController.updateCameraPosition();
		SkeletalEntity manSE = (SkeletalEntity) eng.getSceneManager().getEntity("player");
		manSE.update();
		
		float time = eng.getElapsedTimeMillis();
		int count = 0;
		count += 1;
		if(time == 1000){
			frame = count;
		}
		
		long currentTime = System.nanoTime();
		float elapsedTickMilliSecs = (currentTime - preTime) / (1000000.0f);
		float elapsedTick = (currentTime - decTime) / (1000000.0f);
		if(elapsedTickMilliSecs >= 10.0f){
			preTime = currentTime;
			preMat = myAvatar.getNode().getPhysicsObject().getTransform();
			//System.out.println("Vector " +  this.objectList.toString());
			preVelocity = myAvatar.getNode().getPhysicsObject().getAngularVelocity();
			//myAvatar.movingSound().setLocation(myAvatar.getNode().getWorldPosition());
			collider.outOfField();
			if(elapsedTick >= 1500.0f) {
				decTime = currentTime;
			
				
				float[] velocity = myAvatar.getNode().getPhysicsObject().getLinearVelocity();
				boolean xbol = Math.abs(velocity[0]) < Math.abs(preVelocity[0]);
				boolean zbol = Math.abs(velocity[2]) < Math.abs(preVelocity[2]);
				
				if((xbol || zbol) && myAvatar.movingSound().getIsPlaying()){
					myAvatar.movingSound().stop();
					this.protClient.sendMoveMessage("sound", 1.0f);
				}
			
			}
		}
		
		if(this.myAvatar.getLives() == 0){
			String gameOver = "Game Over";
			rs.setHUD(gameOver, rs.getCanvas().getWidth()/2, rs.getCanvas().getHeight()/2);	
		}
		
		
	
		//collider.detectCollision(preMat,myAvatar);
		
		Matrix4 mat;
		this.physicsEngine.getPhysicsEngine().update(time);
		Iterator<SceneNode> iter = eng.getSceneManager().getSceneNodes().iterator();
		while(iter.hasNext()) {
			SceneNode s = iter.next();
			if (s.getPhysicsObject() != null) {
				mat = Matrix4f.createFrom(this.physicsEngine.toFloatArray(s.getPhysicsObject().getTransform()));
				s.setLocalPosition(mat.value(0, 3), mat.value(1, 3), mat.value(2, 3));
				Vector3 l = Vector3f.createFrom(mat.value(0, 3), mat.value(1, 3), mat.value(2, 3));
				//protClient.updatelocation(l);
			}
			if (s.getName().contains("ammo"))
			{
				if (myAvatar.getAmmo() < 10)
				{
					if (s.getWorldPosition().x() <= myAvatar.getNode().getWorldPosition().x()+1 && s.getWorldPosition().x() >= myAvatar.getNode().getWorldPosition().x()-1)
					{
						if (s.getWorldPosition().z() <= myAvatar.getNode().getWorldPosition().z()+1 && s.getWorldPosition().z() >= myAvatar.getNode().getWorldPosition().z()-1)
						{
							AudioResource r1;
							r1 = audioMgr.createAudioResource("assets\\sound\\reload.wav", AudioResourceType.AUDIO_STREAM);
							Sound reload = new Sound(r1,SoundType.SOUND_EFFECT, 100, false);
							reload.initialize(audioMgr);
							reload.setMaxDistance(5.0f);
							reload.setMinDistance(0.5f);
							reload.setRollOff(0.5f);
							reload.setLocation(myAvatar.getNode().getWorldPosition());
							reload.play();
							myAvatar.addMissile(10);
							for (SceneObject o : s.getAttachedObjects())
							{
								o.detachFromParent();
								sm.destroyEntity((Entity)o);
							}
							iter.remove();
						}
					}
				}
			}
		}
		
		long missileTime = System.nanoTime();
		if(myAvatar.getMissile() != null){
			myAvatar.applyForce(missileTime);
		}
		
		if(ghostAvatar != null) {
			for(int i = 0; i < ghostAvatar.size(); i++) {
				Player player = ghostAvatar.get(i);
				if(player.getMissile() != null) {
					player.applyForce(missileTime);
				}
			}
		}
		
		
		if(myAvatar.updateVerticalPosition().y() > 1.88f || myAvatar.updateVerticalPosition().y() < 1.22f) {
			myAvatar.getNode().getPhysicsObject().setTransform(preMat);
			System.out.println(myAvatar.getNode().getLocalForwardAxis());
		}
		
		collider.detectCollision();
		if(collider.collide()){
			myAvatar.getNode().getPhysicsObject().setTransform(preMat);
			collider.resetCollider();
		}
		
		if(collider.outofbound()){
			myAvatar.getNode().getPhysicsObject().setTransform(preMat);
		}
		
		if(Math.floor(elapseTime/1000)%33 ==0){
			Random rng = new Random();
			int foo = rng.nextInt(30);
			if(foo % 3 == 0){
				AmmoPack testAmmoPack = new AmmoPack(sm,eng,this.physicsEngine);
				testAmmoPack.init();
			}
		}
		
	}
	
	

	/**
	 * Setup the networking for the client
	 */
	private void setUpNetworking() {
		gameObjectsToRemove = new Vector<UUID>();
		isClientConnected = false;
		try {
			protClient = new ProtocolClient(InetAddress.getByName(serverAddress), serverPort, serverProtocol, this);
			
			if (protClient == null) {
				System.out.println("missing protocol host");
			} else {
				System.out.println("Send a join message to server: " + protClient.getID());
				protClient.sendJoinMessage();
				
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	
	}

	/**
	 * Destroy the game object
	 * 
	 * @param elapseTime
	 */
	protected void processNetworking(float elapseTime) {
		// process packets receive by the client from the server
		if (protClient != null) {
			protClient.processPackets();
			
			boolean move = sendMove(elapseTime);
			
			//Send a move message to the server if the client moves
			long currentTime = System.nanoTime();
			float elapsedTickMilliSecs = (currentTime - lastTickUpdateTime) / (1000000.0f);
			
			if(elapsedTickMilliSecs >= 20.0f && move){
				//System.out.println(elapsedTickMilliSecs);
				lastTickUpdateTime = currentTime;
				Matrix4 mat;
				mat = Matrix4f.createFrom(this.physicsEngine.toFloatArray(myAvatar.getNode().getPhysicsObject().getTransform()));
				float trans[] = this.physicsEngine.toFloatArray(myAvatar.getNode().getPhysicsObject().getTransform());
				this.protClient.updatelocation(trans);
				
			}
			
			
			 
			// Remove avatars for players who have left the game
			Iterator<UUID> it = gameObjectsToRemove.iterator();
			while (it.hasNext()) {
				sm.destroySceneNode(it.next().toString());
			}
			gameObjectsToRemove.clear();
		   
		}
	}

	/**
	 * Return the location of the player avatar
	 * 
	 * @return
	 */
	public Vector3 getPlayerPosition() {
		SceneNode avN = sm.getSceneNode("playerNode");
		return avN.getWorldPosition();
	}
	
	/**
	 * 
	 */
	public Matrix3 getPlayerRotation(){
		SceneNode avN = sm.getSceneNode("playerNode");
		return avN.getWorldRotation();
	}

	/**
	 * This creates a player in the client game
	 * 
	 * @param avatar
	 * @throws IOException
	 */
	public void addAvatar(Player avatar,Vector3 position) throws IOException {
		if (avatar != null) {
				
				
				System.out.println("Creating ghost avatar in local space: " + avatar.getID().toString());
				SkeletalEntity avatarE =sm.createSkeletalEntity("E" + avatar.getID().toString(), "carmodel.rkm","carmodel.rks");
				avatarE.setPrimitive(Primitive.TRIANGLES);
				SceneNode avatarN = sm.getRootSceneNode().createChildSceneNode(avatar.getID().toString());
				avatarN.attachObject(avatarE);
				avatarN.setLocalPosition(position);
				avatarN.scale(.05f, .05f, .05f);
				avatar.setNode(avatarN);
				avatar.setEntity(avatarE);
				//avatar.setPostion()
				ghostAvatar.add(avatar);
				avatar.addFields(sm, this.physicsEngine, objectList);
				Scripting script = new Scripting("script/texture.js");
				String s = " ";
				//script.applyText("texture", s);
				//Object texture = script.scriptValue();
				//avatar.addTextureAnimated(texture.toString());
				script.applyMat("material", s);
				Object material = script.scriptValue();
				avatar.addMaterialAnimated(material.toString());
				/*
				GameObject ghostAvatar = new GameObject(sm,avatarId.toString(),"carmodel.rkm","carmodel.rks","enemy");
				ghostAvatar.createAnimate();
				ghostAvatar.getNode().scale(.05f,.05f,.05f);
				ghostAvatar.getNode().setLocalPosition(position);
				MyPhysicsObject carObject = new MyPhysicsObject(ghostAvatar.getNode(),"car",this.physicsEngine);
				carObject.create();
				*/
				MyPhysicsObject carObject = new MyPhysicsObject(avatarN,"car",this.physicsEngine);
				carObject.create();
				avatar.initSound(this.audioMgr);
				objectList.add(avatar);
				
				
			
		}
	}
	
	public void spawnAmmoBox(String number, String location){
		
	}
	
	public void addGhostNPCtoGame(GhostNPC npc, Vector3 position) throws IOException{
		if(npc != null){
			System.out.println("Creating NPC in local space: " + npc.getID());
			Entity avatarE =sm.createEntity("E" + npc.getID(), "planeone.obj");
			avatarE.setPrimitive(Primitive.TRIANGLES);
			Texture tex = sm.getTextureManager().getAssetByPath("coloredplane.png");
			TextureState texState = (TextureState) sm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
			texState.setTexture(tex);
			avatarE.setRenderState(texState);
			SceneNode avatarN = sm.getRootSceneNode().createChildSceneNode(String.valueOf(npc.getID()));
			avatarN.attachObject(avatarE);
			avatarN.setLocalPosition(position);
			avatarN.rotate(Degreef.createFrom(180), Vector3f.createFrom(0.0f, 1.0f, 0.0f));
			//avatarN.scale(.05f, .05f, .05f);
			npc.setNode(avatarN);
			npc.setEntity(avatarE);
			//avatar.setPostion()
			MyPhysicsObject carObject = new MyPhysicsObject(avatarN,"car",this.physicsEngine);
		}
	}
	
	public void updateGhostNPC(int id, String commands) {
		SceneNode node = sm.getSceneNode(String.valueOf(id));
		if(commands.equals("moveforward")) {
			
			try {
				protClient.sendNPCLoc(node.getLocalPosition());
				node.moveBackward(0.2f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(commands.equals("yaw")){
			node.yaw(Degreef.createFrom(5.0f));
		}else if(commands.equals("drop")){
			try {
				if(node != null){
					Bomb bomb = new Bomb(sm,this.physicsEngine,1,node);
					bomb.setPhysicsEngine(physicsEngine);
					bomb.addPhysics();
					this.objectList.add(bomb);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void updateGhostAvatarPosition(UUID id, Vector3 position){
		SceneNode avatarNode = sm.getSceneNode(id.toString());
		
	}
	
	public void updateGhostAvatarPosition(UUID id, String transform){
		try{
		//System.out.println("Game Engine " + transform);
		String[] trValue = transform.split("x");
		System.out.println(trValue[0] + " " + trValue[1]);
		double[] tr = strDouble(trValue);
		SceneNode avatarNode = sm.getSceneNode(id.toString());
		avatarNode.getPhysicsObject().setTransform(tr);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public double[] strDouble(String[] trValue){
		if (trValue == null)
			return null;
		int n = trValue.length;
		double[] ret = new double[n];
		for (int i = 0; i < n; i++) {
			ret[i] = Double.parseDouble(trValue[i]);
		}
		return ret;
	}
	
	public void updateGhostAvatarPosition(UUID id, String type, float value){
		try{
		Player player = null;
		
		if(ghostAvatar != null) {
			for(int i = 0; i < ghostAvatar.size(); i++) {
				if(id.toString().equals(ghostAvatar.get(i).getID().toString())) {
					player = ghostAvatar.get(i);
				}
			}
		}
		
		SceneNode avatarNode = sm.getSceneNode(id.toString());
		System.out.println("Avatar " + avatarNode.getName());
		Angle angle = Degreef.createFrom(value);
		System.out.println("This is inside the game engine: " + id.toString()  + " " + value + " " + type);
		
		if(type.equals("backward")){
			avatarNode.getPhysicsObject().applyForce(-avatarNode.getLocalForwardAxis().x()*value,0.0f, -avatarNode.getLocalForwardAxis().z()*value,avatarNode.getLocalPosition().x(),0.0f, avatarNode.getLocalPosition().z());
		}else if(type.equals("forward")){
			//avatarNode.moveForward(value);
			avatarNode.getPhysicsObject().applyForce(avatarNode.getLocalForwardAxis().x()*value,0.0f, avatarNode.getLocalForwardAxis().z()*value,avatarNode.getLocalPosition().x(),0.0f, avatarNode.getLocalPosition().z());
		}else if(type.equals("yawleft")){
			avatarNode.yaw(angle);
		}else if(type.equals("yawright")){
			avatarNode.yaw(angle);
		}else if(type.equals("fire")) {
			if(player != null) {
				System.out.println("Enemy Fired");
				player.fireMissile();
				player.getMissile().missileSound().play();
			}
		}else if(type.equals("sound")){
			if(player != null){
				if(value == 0.0f){
				  player.getMoving().play();
				}else{
				  player.getMoving().stop();
				}
			}
		}else if(type.equals("texture")){
			if(player != null){
				String texturePath = "";
				if (value == 0)
					texturePath = "carmkrtexture.png";
				else if (value == 1)
					texturePath = "car_blue.png";
				else
					texturePath = "car_yellow.png";
				texColor = texColor + 1;
				player.addTextureAnimated(texturePath);
			}
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Remove the avatar from all clients connected to the game
	 * 
	 * @param avatar
	 */
	public void removeAvatarFromGameWorld(UUID id) {
		if (id != null) {
			gameObjectsToRemove.add(id);
		}
	}

	/**
	 * Check if the game is connected to the server
	 * 
	 * @param isConnected
	 */
	public void setIsConnected(boolean isConnected) {
		this.isClientConnected = isConnected;
	}

	private class SendCloseConnectionPacketAction extends AbstractInputAction {

		@Override
		public void performAction(float arg0, Event arg1) {
			// TODO Auto-generated method stub

		}

	}
	
	
	/**
	 * Setup Orbit Camera
	 * @param eng
	 * @param sm
	 * @param kbName
	 * @param gpName
	 */
	protected void setupOrbitCamera(Engine eng, SceneManager sm) {
		//Setup Camera For Player One
		SceneNode avatar = sm.getSceneNode(myAvatar.getNode().getName());
		SceneNode cameraNode = sm.getSceneNode(mycamera.getCameraNode().getName());
		Camera camera = sm.getCamera(mycamera.getCamera().getName());
		orbitController = new Camera3Pcontroller(camera,cameraNode,avatar,im.getKeyboardName(),im,0);
		//orbitController = new Camera3Pcontroller(camera,cameraNode,avatar,"PC/AT Enhanced PS/2 Keyboard (101/102-Key)",im,0);
	}
	
	/**
	 * This function checks if a move message should be sent to the server
	 * @param elapseTime
	 * @return
	 */
	public boolean sendMove(float elapseTime){
		 boolean move = false;
		
		 float rate = elapseTime / 1000;
		 
		 if(once){
			 delay = rate + 0.05f;
			 once = false;
			 preLocation = getPlayerPosition();
		 }
		 
		 if(rate > delay){
			 if(!preLocation.equals(getPlayerPosition())){
		     	 move = true;
			 }
		     once = true;
		 }
		
		
		return move;
	}
	
	

}
