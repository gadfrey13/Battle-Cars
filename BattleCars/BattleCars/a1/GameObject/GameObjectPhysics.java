package a1.GameObject;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.MotionState;

import ray.rage.scene.SceneManager;

public class GameObjectPhysics extends RigidBody {

	public GameObjectPhysics(float mass, MotionState motionState, CollisionShape collisionShape, Vector3f localInertia) {
		super(mass, motionState, collisionShape, localInertia);
	}

	
}
