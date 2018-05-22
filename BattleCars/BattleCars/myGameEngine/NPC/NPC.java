package myGameEngine.NPC;

import ray.rml.Vector3;
import ray.rml.Vector3f;

public class NPC {
	 float locX, locY, locZ; // other state info goes here (FSM)
	 private int id;
	 Vector3 pos;
	 public NPC(int id, float x, float y, float z){
		 this.id = id;
		 this.locX = x;
		 this.locY = y;
		 this.locZ = z;
		 pos = Vector3f.createFrom(locX, locY, locZ);
	 }
	 
	 public float getX() { return locX; }
	 public float getY() { return locY; }
	 public float getZ() { return locZ; }
	 
	 public void updateLocation(){
		 
	 }
	 
	 public Vector3 getLocation() {
		 return pos;
	 }
	 
	 public void updateLoc(Vector3 loc) {
		 float locX = loc.x();
		 float locY = loc.y();
		 float locZ = loc.z();
		
		 
	 }
	 
}
