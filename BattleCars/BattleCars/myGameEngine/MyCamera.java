package myGameEngine;

import ray.rage.rendersystem.RenderWindow;
import ray.rage.scene.Camera;
import ray.rage.scene.Camera.Frustum.Projection;
import ray.rml.Vector3f;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;

public class MyCamera {
	private Camera camera;
	private SceneManager sm;
	private RenderWindow rw;
	private SceneNode cameraNode,rootNode;
	
	public MyCamera(SceneManager sm, RenderWindow rw){
		this.sm = sm;
		this.rw = rw;
	}
	
	public MyCamera(Camera camera, SceneManager sm, RenderWindow rw){
		this.camera = camera;
		this.sm = sm;
		this.rw = rw;
	}
	
	public MyCamera(Camera camera,SceneNode cameraNode){
		this.camera = camera;
		this.cameraNode = cameraNode;
	}
	
	public MyCamera(){
		SceneNode rootNode = sm.getRootSceneNode();
        camera = sm.createCamera("MainCamera", Projection.PERSPECTIVE);
        rw.getViewport(0).setCamera(camera);
		camera.setRt((Vector3f)Vector3f.createFrom(1.0f, 0.0f, 0.0f));
		camera.setUp((Vector3f)Vector3f.createFrom(0.0f, 1.0f, 0.0f));
		camera.setFd((Vector3f)Vector3f.createFrom(0.0f, 0.0f, -1.0f));
		camera.setPo((Vector3f)Vector3f.createFrom(1.0f, 1.0f, 20.0f));
        SceneNode cameraNode = rootNode.createChildSceneNode(camera.getName() + "Node");
        cameraNode.attachObject(camera);
	}
	
	/**
	 * Setup the camera with the default viewport of index 0
	 * @param cameraName
	 */
	public void setUpCamera(String cameraName){
		rootNode = sm.getRootSceneNode();
		camera = sm.createCamera(cameraName,Projection.PERSPECTIVE);
		rw.getViewport(0).setCamera(camera);
	    cameraNode =
		rootNode.createChildSceneNode(cameraName+"Node");
		cameraNode.attachObject(camera);
		camera.setRt((Vector3f)Vector3f.createFrom(1.0f, 0.0f, 0.0f));
		camera.setUp((Vector3f)Vector3f.createFrom(0.0f, 1.0f, 0.0f));
		camera.setFd((Vector3f)Vector3f.createFrom(0.0f, 0.0f, -1.0f));
		camera.setPo((Vector3f)Vector3f.createFrom(1.0f, 1.0f, 20.0f));
		camera.setMode('n');
		camera.getFrustum().setFarClipDistance(1000.0f);
	}
	/**
	 * Setup the camera 
	 * @param cameraName
	 * @param viewPort
	 */
	public void setUpCamera(String cameraName, int viewPort,char mode){
		rootNode = sm.getRootSceneNode();
		camera = sm.createCamera(cameraName, Projection.PERSPECTIVE);
		rw.getViewport(viewPort).setCamera(camera);
		camera.setRt((Vector3f)Vector3f.createFrom(1.0f, 0.0f, 0.0f));
		camera.setUp((Vector3f)Vector3f.createFrom(0.0f, 1.0f, 0.0f));
		camera.setFd((Vector3f)Vector3f.createFrom(0.0f, 0.0f, -1.0f));
		camera.setPo((Vector3f) Vector3f.createFrom(0.5f, 0.5f, 1.0f));
		cameraNode = rootNode.createChildSceneNode(camera.getName()+"Node");
		cameraNode.attachObject(camera);
		camera.setMode(mode);
	}
	
	/**
	 * Return the node that camera is attach to.
	 * @return
	 */
	public SceneNode getCameraNode(){
		return cameraNode;
	}
	
	/**
	 * Return camera 
	 * @return
	 */
	public Camera getCamera(){
		return camera;
	}
	
}
