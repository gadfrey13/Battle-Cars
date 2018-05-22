package a1.GameObject;


import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import myGameEngine.MyPhysicsEngine;
import myGameEngine.MyPhysicsObject;
import ray.rage.Engine;
import ray.rage.asset.material.Material;
import ray.rage.asset.texture.Texture;
import ray.rage.rendersystem.states.RenderState;
import ray.rage.rendersystem.states.TextureState;
import ray.rage.scene.Entity;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;

public class AmmoPack {

	private SceneManager sm;
	private Engine eng;
	private MyPhysicsEngine physicsEngine;
	private Entity ent;
	private SceneNode node;
	private UUID ID;
	
	public AmmoPack(SceneManager sm, Engine eng,MyPhysicsEngine physicsEngine){
		this.sm = sm;
		this.eng = eng;
		this.physicsEngine = physicsEngine;
		ID = UUID.randomUUID();
	}
	
	public void init()
	{
		try {
			ent = sm.createEntity("ammo"+ID, "ammo.obj");
			Material mat = sm.getMaterialManager().getAssetByPath("ammo.mtl");
			ent.setMaterial(mat);
			Texture tex = sm.getTextureManager().getAssetByPath("ammo.png");
			TextureState texState = (TextureState) sm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
			texState.setTexture(tex);
			ent.setRenderState(texState);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		node = sm.getRootSceneNode().createChildSceneNode("ammoNode"+ID);
		node.attachObject(ent);
		node.scale(0.3f,0.3f,0.3f);
		Random rng = new Random();
		float x = rng.nextFloat();
		float z = rng.nextFloat();
		int xRand = rng.nextInt(50);
		if (xRand >= 15)
			xRand = -(30-xRand);
		int zRand = rng.nextInt(50);
		if (zRand >= 15)
			zRand = -(30-zRand);
		x+=xRand;
		z+=zRand;
		node.setLocalPosition(x, 1.90f, z);
		
	}
}
