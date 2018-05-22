package a1.GameWorld;

import java.io.IOException;

import a1.GameObject.GameObject;
import ray.rage.Engine;
import ray.rage.asset.texture.Texture;
import ray.rage.asset.texture.TextureManager;
import ray.rage.rendersystem.states.TextureState.WrapMode;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.rage.scene.Tessellation;
import ray.rage.util.Configuration;
import ray.rml.Degreef;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class Terrain {
	private SceneManager sm;
	private Engine eng;
	String heightMap = "terrainheightmap.png";
	String text = "overviewterraintexture.png";
	public Terrain(Engine eng,SceneManager sm) {
		this.eng = eng;
		this.sm = sm;
	}

	public void createTerrain(String tsname, String tsnodename, String heightpath, String texturepath) {
		Tessellation tessE = sm.createTessellation("tessE", 6);
		tessE.setSubdivisions(4f);
		SceneNode tessN = sm.getRootSceneNode().createChildSceneNode("tessN");
		tessN.attachObject(tessE);
		// to move it, note that X and Z must BOTH be positive OR negative
		// tessN.translate(Vector3f.createFrom(-6.2f, -2.2f, 2.7f));
		// tessN.yaw(Degreef.createFrom(37.2f));
		tessN.scale(10, 30, 10);
		tessE.setHeightMap(eng, "scribble.jpg");
		tessE.setTexture(eng, "grass.jpg");
		tessE.getTextureState().setWrapMode(WrapMode.REPEAT_MIRRORED);
	}
	
	public void defaultTerrain() throws IOException {
		/*
		Configuration conf = eng.getConfiguration();
		TextureManager tm = eng.getTextureManager();
		tm.setBaseDirectoryPath(conf.valueOf("assets.textures.path"));
		Texture heightMap = tm.getAssetByPath("hills.png");
		Texture text = tm.getAssetByPath("blue.jpeg");
		*/
		Tessellation tessE = sm.createTessellation("tessE", 6);
		tessE.setSubdivisions(8f);
		SceneNode tessN = sm.getRootSceneNode().createChildSceneNode("tessN");
		tessN.attachObject(tessE);
		// to move it, note that X and Z must BOTH be positive OR negative
		//tessN.translate(Vector3f.createFrom(-6.2f, -2.2f, 2.7f));
		//tessN.yaw(Degreef.createFrom(37.2f));
		tessN.scale(100.0f, 300.0f, 100.0f);
		tessE.setHeightMap(eng, heightMap);
		tessE.setTexture(eng, text);
		
	}
	
	public void addTerrain(String type,String name,int num,float x, float y , float z,int sep) throws IOException{
		for(int i = 0; i < num; i++ ){
			GameObject forest = new GameObject(sm,"name"+i,type,"forest");
			forest.create();
			forest.addMaterial("tree.mtl");
			forest.addTexture("falltree.jpg");
			forest.getNode().setLocalPosition(x+i,y,z+i*10);
		}
	}
	
	
}
