package a1.GameObject;

import java.io.IOException;
import java.util.UUID;

import myGameEngine.MyPhysicsEngine;
import myGameEngine.MyPhysicsObject;
import ray.audio.AudioManagerFactory;
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
import ray.rage.scene.SceneObject;
import ray.rage.scene.SkeletalEntity;
import ray.rage.scene.Tessellation;
import ray.rml.Degreef;
import ray.rml.Matrix3;
import ray.rml.Matrix4;
import ray.rml.Vector3;
import ray.rml.Vector3f;
import ray.audio.*;
public class Missile {
	private GameObject parent;
	private Player enemy;
	private SceneManager sm;
	private MyPhysicsEngine physicsEngine;
	private MyPhysicsObject physicsObject;
	private float lastTickUpdate,keep;
	private SceneNode node;
	private Entity missile;
	int count = 0;
	private Sound soundObject;
	private IAudioManager audioMgr;
	public Missile(GameObject object,SceneManager sm,MyPhysicsEngine physicsEngine,int num,IAudioManager audioMgr) throws IOException{
		UUID ID = UUID.randomUUID();
		missile = sm.createEntity("missile" + object.getNode().getName()+num+ID, "missileunwrapfix.obj");
		missile.setPrimitive(Primitive.TRIANGLES);
		node = sm.getRootSceneNode().createChildSceneNode("missile" + object.getNode().getName()+num+ID);
		node.attachObject(missile);
		Material mat = sm.getMaterialManager().getAssetByPath("missileunwrapfix.mtl");
		missile.setMaterial(mat);
		Texture tex = sm.getTextureManager().getAssetByPath("missileunwrapfixcolor.png");
		TextureState texState = (TextureState) sm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
		texState.setTexture(tex);
		missile.setRenderState(texState);
		node.scale(0.05f, 0.05f, 0.05f);
		object.getNode().attachChild(node);
		node.translate(0.0f,-5.0f,10.0f);
		node.rotate(Degreef.createFrom(90.0f), Vector3f.createFrom(1.0f, 0.0f, 0.0f));
		//Create the physics object
		//physicsObject = new MyPhysicsObject(node,"missile",this.physicsEngine);
		//physicsObject.create();
		this.parent = object;
		this.audioMgr = audioMgr;
	}
	
	public Missile(Player object,SceneManager sm,MyPhysicsEngine physicsEngine,int num,String enemy,IAudioManager audioMgr) throws IOException{
		UUID ID = UUID.randomUUID();
		missile = sm.createEntity("missile" + object.getNode().getName()+num+enemy, "missileunwrapfix.obj");
		missile.setPrimitive(Primitive.TRIANGLES);
		node = sm.getRootSceneNode().createChildSceneNode("missile" + object.getNode().getName()+num+enemy+ID);
		node.attachObject(missile);
		Material mat = sm.getMaterialManager().getAssetByPath("missileunwrapfix.mtl");
		missile.setMaterial(mat);
		Texture tex = sm.getTextureManager().getAssetByPath("missileunwrapfixcolor.png");
		TextureState texState = (TextureState) sm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
		texState.setTexture(tex);
		missile.setRenderState(texState);
		node.scale(0.05f, 0.05f, 0.05f);
		object.getNode().attachChild(node);
		node.translate(0.0f,-5.0f,10.0f);
		node.rotate(Degreef.createFrom(90.0f), Vector3f.createFrom(1.0f, 0.0f, 0.0f));
		//Create the physics object
		//physicsObject = new MyPhysicsObject(node,"missile",this.physicsEngine);
		//physicsObject.create();
		this.enemy = object;
		this.audioMgr = audioMgr;
	}
	
	public void light(){
		
	}
	
	public SceneNode getNode() {
		return this.node;
	}
	
	public void setPhysicsEngine(MyPhysicsEngine physicsEngine){
		this.physicsEngine = physicsEngine;
	}
	
	public void addPhysics(){
		physicsObject = new MyPhysicsObject(node,"missile",this.physicsEngine);
		physicsObject.create();
	}
	
	public void applyForce(long currentTime, boolean child){
		float elapsedTickMilliSecs = (currentTime - lastTickUpdate) / (1000000.0f);
		float c = (currentTime - keep) /(1000000000.0f);
		//System.out.println(c);
		if(elapsedTickMilliSecs >= 10.0f){
			lastTickUpdate = elapsedTickMilliSecs;
			//physicsObject.getPhysicsObject().applyForce(node.getLocalForwardAxis().x()*ac,0.0f, node.getLocalForwardAxis().z()*ac,node.getLocalPosition().x(),0.0f, node.getLocalPosition().z());
			float[] vel = {0.0f,0.0f,400.0f};
			physicsObject.getPhysicsObject().setLinearVelocity(vel);
			if(c >= 20000.0f){
				keep = c;
				count = count + 1;
				//System.out.println("Count " + count);
				//this.parent.getNode().detachChild(node);
				/*
				Vector3 loc = node.getWorldPosition();
				Vector3 scale = node.getWorldScale();
				Matrix3 rot = node.getWorldRotation();
				node.notifyDetached();
				node.setLocalPosition(loc);
				node.setLocalScale(scale);
				node.setLocalRotation(rot);
				*/
			}
		}
		
		float z = node.getLocalPosition().z();
		//System.out.println(z);
		
		/*
		if(z > 500){
			if(node != null){
				Vector3 position = node.getLocalPosition();
				this.parent.getNode().detachChild(node);
				node.setLocalPosition(position);
			}
		}*/
		
		if(Math.abs(node.getLocalPosition().x()) > 500.0f || Math.abs(node.getLocalPosition().z()) > 500.0f){
			if(node != null){
				//sm.destroyEntity(missile);
				//sm.destroySceneNode(node);
				//this.parent.getNode().detachChild(node);
			}
		}
	}
	
	public void soundInit(){
			AudioResource resource1, resource2;
			resource1 = audioMgr.createAudioResource("assets\\sound\\missilesound.wav", AudioResourceType.AUDIO_SAMPLE);
			soundObject = new Sound(resource1, SoundType.SOUND_EFFECT, 100, true);
			//oceanSound = new Sound(resource2, SoundType.SOUND_EFFECT, 100, true);
			soundObject.initialize(audioMgr);
			//oceanSound.initialize(audioMgr);
			soundObject.setMaxDistance(1000.0f);
			soundObject.setMinDistance(0.5f);
			soundObject.setRollOff(4.0f);
			soundObject.setLocation(node.getLocalPosition());
		
	}
	
	
	public Sound missileSound(){
		return this.soundObject;
	}
	
	 
}
