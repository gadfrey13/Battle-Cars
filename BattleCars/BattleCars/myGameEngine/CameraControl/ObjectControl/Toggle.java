package myGameEngine.CameraControl.ObjectControl;

import java.awt.Color;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Light;

public class Toggle extends AbstractInputAction {
	private boolean  bol = false;
	private int count = 0;
	private Light light;
	public Toggle(Light light){
		this.light = light;
	}
	
	@Override
	public void performAction(float arg0, Event arg1) {
		System.out.println("Toggle Light");
		if(bol){
			light.setAmbient(new Color(0f,0f,0f));
		}else{
			light.setAmbient(new Color(1f,1f,1f));
		}
		
		bol = !bol;	
	}
	

	
}
