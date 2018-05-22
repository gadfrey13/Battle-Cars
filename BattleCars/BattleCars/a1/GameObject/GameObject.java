package a1.GameObject;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import myGameEngine.MyPhysicsEngine;
import myGameEngine.MyPhysicsObject;
import ray.audio.AudioResource;
import ray.audio.AudioResourceType;
import ray.audio.IAudioManager;
import ray.audio.Sound;
import ray.audio.SoundType;
import ray.rage.asset.material.Material;
import ray.rage.asset.texture.Texture;
import ray.rage.rendersystem.Renderable.Primitive;
import ray.rage.rendersystem.states.RenderState;
import ray.rage.rendersystem.states.TextureState;
import ray.rage.scene.Entity;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.rage.scene.SkeletalEntity;
import ray.rage.scene.Tessellation;
import ray.rml.Degreef;
import ray.rml.Vector2;
import ray.rml.Vector2f;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class GameObject {
	private String name;
	private String path;
	private SceneManager sm;
	private SceneNode objectNode;
	private String type;
	private Entity gameEntity;
	private int points = 1;
	private String rkm;
	private String rks;
	SkeletalEntity manSE;
	private int numMissile;
	private MyPhysicsEngine physicsEngine;
	private float lastTickUpdate;
	private Missile missile;
	private boolean hold = false;
	private Vector objectList;
	private Dimension dimension;
	private String enemy;
	private IAudioManager audioMgr;
	private Sound soundObject;
	private int lives = 3;
	private int speed = 0;
	/***
	 * @param SceneManager
	 * @param name
	 * @param path
	 */
	public GameObject(SceneManager sm,String name, String path,String type){
		this.name = name;
		this.path = path;
		this.sm = sm;
		this.type = type;
		this.numMissile = 10;
	}
	
	public GameObject(SceneManager sm, String name, String rkm,String rks, String type){
		this.name = name;
		this.rkm = rkm;
		this.rks = rks;
		this.sm = sm;
		this.type = type;
		this.numMissile = 10;
	}
	
	public GameObject(SceneManager sm, String name, String rkm,String rks, String type,String enemy){
		this.name = name;
		this.rkm = rkm;
		this.rks = rks;
		this.sm = sm;
		this.type = type;
		this.numMissile = 10;
		this.enemy = enemy;
	}
	
	
	
	public GameObject(SceneManager sm, String name, String rkm,String rks, String type,Vector objectList,IAudioManager audioMgr){
		this.name = name;
		this.rkm = rkm;
		this.rks = rks;
		this.sm = sm;
		this.type = type;
		this.numMissile = 10;
		this.objectList = objectList;
		dimension = new Dimension(1,1);
		this.audioMgr = audioMgr;
	}
	
	public void soundInit(){
		if(type.equals("player")){
			
			AudioResource resource1, resource2;
			resource1 = audioMgr.createAudioResource("assets\\sound\\carmoving.wav", AudioResourceType.AUDIO_SAMPLE);
			soundObject = new Sound(resource1, SoundType.SOUND_EFFECT, 100, true);
			//oceanSound = new Sound(resource2, SoundType.SOUND_EFFECT, 100, true);
			soundObject.initialize(audioMgr);
			//oceanSound.initialize(audioMgr);
			soundObject.setMaxDistance(1000.0f);
			soundObject.setMinDistance(0.5f);
			soundObject.setRollOff(4.0f);
			SceneNode robotN = sm.getSceneNode("playerNode");
			soundObject.setLocation(robotN.getWorldPosition());
			setEarParameters();
		}
	}
	public void setupScripts()
	{
		String pth = ".\\scripts";
		if (System.getProperty("os.name").toLowerCase().equals("linux"))
			pth = "./scripts";
		File folder = new File(pth);
		File[] list = folder.listFiles();
		if (list == null || list.length == 0)
			return;
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine jsEngine = factory.getEngineByName("js");
		for (File f : list)
		{
			try {
				if (!f.getName().equals("initParamCar.js"))
					return;
				FileReader r = new FileReader(f);
				jsEngine.eval(r);
				if (jsEngine.get("texture") == null)
					return;
				String newTex = (String)(jsEngine.get("texture"));
				this.addTexture(newTex);
				r.close();
			}
		}
	}
	
	public Sound movingSound(){
		return this.soundObject;
	}
	
	public int getAmmo(){
		return this.numMissile;
	}
	
	
	public void setEarParameters() {
		SceneNode ear = sm.getSceneNode("maincameraNode");
		Vector3 avDir = ear.getWorldForwardAxis();
		// note - should get the camera's forward direction
		// - avatar direction plus azimuth
		audioMgr.getEar().setLocation(ear.getWorldPosition());
		audioMgr.getEar().setOrientation(avDir, Vector3f.createFrom(0, 1, 0));
		
	}
	
	
	
	/**
	 * @throws IOException 
	 * 
	 */

	public void createAnimate() throws IOException {
		// load skeletal entity – in this case it is an avatar
		manSE = sm.createSkeletalEntity(name, rkm, rks);
		objectNode = sm.getRootSceneNode().createChildSceneNode(name+"Node");
		objectNode.attachObject(manSE);
	}
	
	public void loadAnimation(String name, String rka){
		try {
			manSE.loadAnimation(name, rka);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addTextureAnimated(String texture) throws IOException{
		Texture tex = sm.getTextureManager().getAssetByPath(texture);
		TextureState texState = (TextureState) sm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
		texState.setTexture(tex);
		manSE.setRenderState(texState);
	}
	
	public void addMaterialAnimated(String material) throws IOException{
		Material mat = sm.getMaterialManager().getAssetByPath(material);
		manSE.setMaterial(mat);
	}
	
	public SkeletalEntity getSkeletal(){
		return this.manSE;
	}
	
	public void createWall(){
		objectNode = sm.getRootSceneNode().createChildSceneNode(name+"Node");
	}
	
	/**
	 * Create a game object
	 * @throws IOException
	 */
	public void create() throws IOException{
		gameEntity = sm.createEntity(name, path);
		gameEntity.setPrimitive(Primitive.TRIANGLES);
		objectNode = sm.getRootSceneNode().createChildSceneNode(name+"Node");
		objectNode.attachObject(gameEntity);
		
	}
	
	/**
	 * Create a game object
	 * @throws IOException
	 */
	public void create(String text1,String text2 ) throws IOException{
		gameEntity = sm.createEntity(name, path);
		gameEntity.setPrimitive(Primitive.TRIANGLES);
		this.addTexture(text1);
		this.addTexture(text2);
		objectNode = sm.getRootSceneNode().createChildSceneNode(name+"Node");
		objectNode.attachObject(gameEntity);

	}
	
	/**
	 * Add texture to the object
	 * @throws IOException 
	 */
	public void addTexture(String texture) throws IOException{
		Texture tex = sm.getTextureManager().getAssetByPath(texture);
		TextureState texState = (TextureState) sm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
		texState.setTexture(tex);
		gameEntity.setRenderState(texState);
	}
	
	public void addMaterial(String material) throws IOException{
		Material mat = sm.getMaterialManager().getAssetByPath(material);
		gameEntity.setMaterial(mat);
	}
	

	
	/**
	 * 
	 */
	
	public SceneNode getNode(){
		return objectNode;
	}
	
	/**
	 * Get the type 
	 */
	public String getType(){
		return type;
	}
	
	public void setPoints(int points){
		this.points = points;
	}
	
	public int getPoints(){
		return points;
	}
	
	public void hit(){
		points--;
	}
	
	public Vector3 updateVerticalPosition() {
		SceneNode dolphinN = this.sm.getSceneNode("playerNode");
		SceneNode tessN = this.sm.getSceneNode("tessN");
		Tessellation tessE = ((Tessellation) tessN.getAttachedObject("tessE"));
		// Figure out Avatar's position relative to plane
		Vector3 worldAvatarPosition = dolphinN.getWorldPosition();
		Vector3 localAvatarPosition = dolphinN.getLocalPosition();
		if(dolphinN.getLocalForwardAxis().x() < 0.0){
			localAvatarPosition = localAvatarPosition.add(Vector3f.createFrom(1.0f, 0.0f, 0.0f));
			System.out.println(localAvatarPosition);
		}else if(dolphinN.getLocalForwardAxis().z() < 0.0){
			localAvatarPosition = localAvatarPosition.add(Vector3f.createFrom(0.0f, 0.0f, 1.0f));
		}
		// use avatar World coordinates to get coordinates for height
		Vector3 newAvatarPosition = Vector3f.createFrom(
				// Keep the X coordinate
				localAvatarPosition.x(),
				// The Y coordinate is the varying height
				tessE.getWorldHeight(worldAvatarPosition.x(), worldAvatarPosition.z()),
				// Keep the Z coordinate
				localAvatarPosition.z());
		// use avatar Local coordinates to set position, including height
		
		return newAvatarPosition;
	}
	
	
	
	public void addMissile(int missile){
		int temp = this.numMissile + missile;
		if(temp > 10){
			this.numMissile = 10;
		}else{
			this.numMissile = temp;
		}
	}
	
	public void fireMissile(GameObject object) throws IOException{
		if(this.numMissile > 0 && this.physicsEngine != null){
			this.numMissile = this.numMissile - 1;
			missile(object,this.numMissile);	
		}
	}
	
	public void getPhysicsEngine(MyPhysicsEngine physicsEngine){
		this.physicsEngine = physicsEngine;
	}
	
	private void missile(GameObject object,int num) throws IOException {
			missile = new Missile(object,sm,this.physicsEngine,num,this.audioMgr);
			missile.setPhysicsEngine(physicsEngine);
			missile.addPhysics();
			missile.soundInit();
			hold = true;
		    this.objectList.add(missile);
		    if(!missile.missileSound().getIsPlaying()){
				missile.missileSound().play();
			}
	}
	
	public void applyForce(long currentTime){
		missile.applyForce(currentTime,hold);
		
	}
	
	public Missile getMissile(){
		return this.missile;
	}
	
	
	private void destroy(GameObject object,Entity missile,SceneNode node){
			missile.notifyDispose();
	}
	
	public Vector2 dimension(){
		
		float x = objectNode.getLocalPosition().x(); 
		float z = objectNode.getLocalPosition().z();
		Vector2 dim = Vector2f.createFrom(x, z);
		return dim;
				
	}
	
	public int getSpeed(){
		float[]  velocity = this.objectNode.getPhysicsObject().getLinearVelocity();
		for(int i = 0; i < velocity.length; i++){
			int temp = (int) velocity[i];
			if(Math.abs(temp) > speed){
				this.speed = Math.abs(temp);
			}
		}
		return speed;
	}
	
	public int getNumberOfMissile(){
		return this.numMissile;
	}
	
	public void loselife(){
		lives = lives - 1;
	}
	
	public int getLives(){
		if(lives < 0){
			lives = 0;
		}
		return lives;
	}
	
	
}
