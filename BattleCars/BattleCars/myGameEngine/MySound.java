package myGameEngine;

import ray.audio.*;
import ray.audio.SoundType;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.rml.Vector3;
import ray.rml.Vector3f;
import com.jogamp.openal.ALFactory;
import ray.audio.AudioManagerFactory;
public class MySound {
	private SceneManager sm;
	private IAudioManager audioMgr;
	private Sound carMoving;
	

	public MySound(SceneManager sm,IAudioManager audioMgr) {
		this.sm = sm;
		this.audioMgr = audioMgr;
	}

	public void initAudio() {
		
		AudioResource resource1, resource2;
		resource1 = audioMgr.createAudioResource("assets\\sound\\carengine.wav", AudioResourceType.AUDIO_SAMPLE);
		carMoving = new Sound(resource1, SoundType.SOUND_EFFECT, 100, true);
		//oceanSound = new Sound(resource2, SoundType.SOUND_EFFECT, 100, true);
		carMoving.initialize(audioMgr);
		//oceanSound.initialize(audioMgr);
		carMoving.setMaxDistance(1000.0f);
		carMoving.setMinDistance(0.5f);
		carMoving.setRollOff(4.0f);
		//oceanSound.setMaxDistance(10.0f);
		//oceanSound.setMinDistance(0.5f);
		//oceanSound.setRollOff(5.0f);
		SceneNode robotN = sm.getSceneNode("barrierNode");
		//SceneNode earthN = sm.getSceneNode("earthNode");
		carMoving.setLocation(robotN.getWorldPosition());
		//oceanSound.setLocation(earthN.getWorldPosition());
		//carMoving.setVolume(5000);
		//audioMgr.setMasterVolume(100);
		carMoving.play();
		//System.out.println("Barrier " + robotN.getLocalPosition());
		//System.out.println("Sound " + carMoving.getLocation());
		//oceanSound.play();
		setEarParameters(sm);
	}

	public void setEarParameters(SceneManager sm) {
		SceneNode ear = sm.getSceneNode("maincameraNode");
		Vector3 avDir = ear.getWorldForwardAxis();
		// note - should get the camera's forward direction
		// - avatar direction plus azimuth
		audioMgr.getEar().setLocation(ear.getWorldPosition());
		audioMgr.getEar().setOrientation(avDir, Vector3f.createFrom(0, 1, 0));
		
	}
}
