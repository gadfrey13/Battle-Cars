package a1.GameObject;

import java.io.IOException;
import java.util.UUID;
import java.util.Vector;

import myGameEngine.MyPhysicsEngine;
import ray.audio.AudioResource;
import ray.audio.AudioResourceType;
import ray.audio.IAudioManager;
import ray.audio.Sound;
import ray.audio.SoundType;
import ray.rage.asset.material.Material;
import ray.rage.asset.texture.Texture;
import ray.rage.rendersystem.states.RenderState;
import ray.rage.rendersystem.states.TextureState;
import ray.rage.scene.Entity;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.rage.scene.SkeletalEntity;
import ray.rml.Vector3;

public class Player {
	private UUID id;
	private SceneNode node;
	private SkeletalEntity entity;
	private Vector3 position;
	private SceneManager sm;
	private MyPhysicsEngine physicsEngine;
	private int num = 10;
	private Vector objectList;
	private Missile missile;
	private boolean hold = false;
	private Sound soundObject;
	private IAudioManager audioMgr;
	public Player(UUID id, Vector3 postion){
		this.id = id;
		this.position = position;
	}
	
	public UUID getID(){
		return id;
	}
	
	public void setNode(SceneNode node){
		this.node = node;
	}
	
	public void setEntity(SkeletalEntity entity){
		this.entity = entity;
	}
	
	public void addTextureAnimated(String texture) throws IOException{
		Texture tex = sm.getTextureManager().getAssetByPath(texture);
		TextureState texState = (TextureState) sm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
		texState.setTexture(tex);
		this.entity.setRenderState(texState);
	}
	
	public void addMaterialAnimated(String material) throws IOException{
		Material mat = sm.getMaterialManager().getAssetByPath(material);
		this.entity.setMaterial(mat);
	}
	
	public SceneNode getNode(){
		return this.node;
	}
	
	public Vector3 originalPosition(){
		return position;
	}
	
	public void fireMissile() throws IOException{
			num = num - 1;
			missile(this);	
	}
	
	public void addFields(SceneManager sm, MyPhysicsEngine eng, Vector objectList) {
		this.sm = sm;
		this.physicsEngine = eng;
		this.objectList = objectList;
		this.hold = true;
	}
	
	private void missile(Player object) throws IOException {
		missile = new Missile(object,sm,this.physicsEngine,num,"enemy",this.audioMgr);
		missile.setPhysicsEngine(physicsEngine);
		missile.addPhysics();
		missile.soundInit();
		this.objectList.add(missile);
	}
	
	public Missile getMissile() {
		return missile;
	}
	
	public void applyForce(long currentTime){
		missile.applyForce(currentTime,hold);
	}
	
	
	public void initSound(IAudioManager audioMgr){
		this.audioMgr = audioMgr;
		AudioResource resource1, resource2;
		resource1 = audioMgr.createAudioResource("assets\\sound\\carmoving.wav", AudioResourceType.AUDIO_SAMPLE);
		soundObject = new Sound(resource1, SoundType.SOUND_EFFECT, 100, true);
		//oceanSound = new Sound(resource2, SoundType.SOUND_EFFECT, 100, true);
		soundObject.initialize(audioMgr);
		//oceanSound.initialize(audioMgr);
		soundObject.setMaxDistance(1000.0f);
		soundObject.setMinDistance(0.5f);
		soundObject.setRollOff(4.0f);
		soundObject.setLocation(node.getWorldPosition());
	}
	
	public Sound getMoving(){
		return this.soundObject;
	}
}
