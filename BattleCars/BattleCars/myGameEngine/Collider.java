package myGameEngine;

import java.util.Vector;

import a1.GameObject.Bomb;
import a1.GameObject.GameObject;
import a1.GameObject.Missile;
import a1.GameObject.Player;
import ray.rage.scene.SceneManager;
import ray.rml.Vector2;
import ray.rml.Vector3;

public class Collider {
	private Vector listObject;
	private SceneManager sm;
	private boolean collide = false;
	private boolean outofbound = false;
	private boolean hitbol = false;
	public Collider(Vector listObject,SceneManager sm) {
		this.listObject = listObject;
		this.sm = sm;
	}
	
	public void detectCollision() {
		if (this.listObject != null && this.listObject.size() > 1) {
			for (int i = 0; i < this.listObject.size(); i++) {
				Object object = null;
				if (this.listObject.get(i) instanceof GameObject) {
					object = (GameObject) this.listObject.get(i);
				} else if (this.listObject.get(i) instanceof Missile) {
					object = (Missile) this.listObject.get(i);
				} else if (this.listObject.get(i) instanceof Player) {
					object = (Player) this.listObject.get(i);
				} else if(this.listObject.get(i) instanceof Bomb){
					object = (Bomb) this.listObject.get(i);
					Bomb b = (Bomb) object;
					bombOut(i,b);
				}
				
				this.checkbound(object);

				for (int k = 0; k < this.listObject.size(); k++) {
					Object objectTwo = null;
					if (this.listObject.get(k) instanceof GameObject) {
						objectTwo = (GameObject) this.listObject.get(k);
					} else if (this.listObject.get(k) instanceof Missile) {
						objectTwo = (Missile) this.listObject.get(k);
					} else if (this.listObject.get(k) instanceof Player) {
						objectTwo = (Player) this.listObject.get(k);
					}
					
					if(object != null && objectTwo != null) {
						if (!objectTwo.equals(object)) {
							//System.out.println("Inside the first");
							hit(object,objectTwo);
							barrier(object,objectTwo);
						}
					}
				}
			}
		}
		
	}
	
	private void bombOut(int i,Bomb b){
		this.listObject.set(i, null);
		if(b.getNode().getWorldPosition().y() < 1.75){
			 b.getNode().notifyDetached();
			 b.getNode().detachAllObjects();
			 sm.destroySceneNode(b.getNode());
		}
	}
	
	private void bombhit(Object one, Object two){
		if(one instanceof GameObject && two instanceof Bomb ) {
			GameObject obj = (GameObject) one;
			Bomb missile = (Bomb) two;
			if(obj.getNode().getName().toLowerCase().contains("player") && missile.getNode().getName().toLowerCase().contains("enemy") ) {
				float carSize = 1.0f;
				float boxSize = 1.0f;
				float xcar = obj.getNode().getLocalPosition().x() + (carSize/2.0f);
				float zcar = obj.getNode().getLocalPosition().z() + (carSize/2.0f);
				float ycar = obj.getNode().getLocalPosition().y() + (carSize/2.0f);
				float xbox = missile.getNode().getWorldPosition().x() + (boxSize/2.0f);
				float zbox = missile.getNode().getWorldPosition().z() + (boxSize/2.0f);
				float ybox = missile.getNode().getWorldPosition().y() + (boxSize/2.0f);
				float dx = xcar - xbox;
				float dz = zcar - zbox;
				float dy = ycar - ybox;
				
				float distCenter = (dx*dx + dz*dz + dy*dy) - 1.5f;
				
				float carRadius = carSize/2;
				float boxRadius = boxSize/2;
				float radiiSqr = (carRadius*carRadius + 2*carRadius*boxRadius+boxRadius*boxRadius);
				if(distCenter <= radiiSqr){
					 obj.loselife();
					 missile.getNode().notifyDetached();
					 missile.getNode().detachAllObjects();
					 sm.destroySceneNode(missile.getNode());
				}
			}else{
				hitbol = false;
			}
						
		}
	}
	
	private void hit(Object one, Object two) {
		if(one instanceof GameObject && two instanceof Missile ) {
			GameObject obj = (GameObject) one;
			Missile missile = (Missile) two;
			if(obj.getNode().getName().toLowerCase().contains("player") && missile.getNode().getName().toLowerCase().contains("enemy") ) {
				float carSize = 1.0f;
				float boxSize = 1.0f;
				float xcar = obj.getNode().getLocalPosition().x() + (carSize/2.0f);
				float zcar = obj.getNode().getLocalPosition().z() + (carSize/2.0f);
				float ycar = obj.getNode().getLocalPosition().y() + (carSize/2.0f);
				float xbox = missile.getNode().getWorldPosition().x() + (boxSize/2.0f);
				float zbox = missile.getNode().getWorldPosition().z() + (boxSize/2.0f);
				float ybox = missile.getNode().getWorldPosition().y() + (boxSize/2.0f);
				float dx = xcar - xbox;
				float dz = zcar - zbox;
				float dy = ycar - ybox;
				
				float distCenter = (dx*dx + dz*dz + dy*dy) - 1.5f;
				
				float carRadius = carSize/2;
				float boxRadius = boxSize/2;
				float radiiSqr = (carRadius*carRadius + 2*carRadius*boxRadius+boxRadius*boxRadius);
				if(distCenter <= radiiSqr){
					 obj.loselife();
					 missile.getNode().notifyDetached();
					 missile.getNode().detachAllObjects();
					 sm.destroySceneNode(missile.getNode());
				}
			}else{
				hitbol = false;
			}
						
		}
	}
	

	private void barrier(Object one, Object two) {
		if(one instanceof GameObject && two instanceof GameObject ) {
			GameObject obj = (GameObject) one;
			GameObject barrier = (GameObject) two;
			if(obj.getNode().getName().toLowerCase().contains("player") && barrier.getNode().getName().toLowerCase().contains("barrier") ) {
				float carSize = 1.0f;
				float boxSize = 1.0f;
				float xcar = obj.getNode().getLocalPosition().x() + (carSize/2.0f);
				float zcar = obj.getNode().getLocalPosition().z() + (carSize/2.0f);
				float ycar = obj.getNode().getLocalPosition().y() + (carSize/2.0f);
				float xbox = barrier.getNode().getLocalPosition().x() + (boxSize/2.0f);
				float zbox = barrier.getNode().getLocalPosition().z() + (boxSize/2.0f);
				float ybox = barrier.getNode().getLocalPosition().y() + (boxSize/2.0f);
				float dx = xcar - xbox;
				float dz = zcar - zbox;
				float dy = ycar - ybox;
				
				float distCenter = (dx*dx + dz*dz + dy*dy) - 1.5f;
				
				float carRadius = carSize/2;
				float boxRadius = boxSize/2;
				float radiiSqr = (carRadius*carRadius + 2*carRadius*boxRadius+boxRadius*boxRadius);
				
				//System.out.println(radiiSqr);
				//System.out.println(distCenter);
				if(distCenter <= radiiSqr){
					 collide = true;
				}
			}
						
		}else{
			collide = false;
		}
	
		
		
	}
	
	
	
	//Remove all the 
	public void outOfField() {
		if (this.listObject != null && this.listObject.size() > 1) {
			for (int i = 0; i < this.listObject.size(); i++) {
				Missile missile = null;
				if(this.listObject.get(i) instanceof Missile) {
					missile = (Missile) this.listObject.get(i);
					if(missile != null) {
						if(Math.abs(missile.getNode().getLocalPosition().x()) > 200.0f || Math.abs(missile.getNode().getLocalPosition().z()) > 200.0f) {
							this.listObject.set(i, null);
							if(missile.missileSound().getIsPlaying()){
								missile.missileSound().stop();
							}
							missile.getNode().notifyDetached();
							missile.getNode().detachAllObjects();
							sm.destroySceneNode(missile.getNode());
						}
					}
				}
				
			}
		}
	}
	
	public void checkbound(Object obj){
		if (obj instanceof GameObject) {
			GameObject object = (GameObject) obj;
			if (object.getNode().getName().contains("player")) {
				float x = Math.abs(object.getNode().getLocalPosition().x());
				float z = Math.abs(object.getNode().getLocalPosition().z());
				if (x > 45.0f || 45.0f < z) {
					outofbound = true;
				} else {
					outofbound = false;
				}
			}
		}
	}
	
	public boolean collide(){
		return collide;
	}
	
	public boolean hit(){
		return hitbol;
	}
	
	public boolean outofbound(){
		return outofbound;
	}
	
	public void resetCollider(){
		collide = false;
	}
	
	public void resetHit(){
		hitbol = false;
	}
}
