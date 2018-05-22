package myGameEngine.NPC;

import java.io.IOException;

import a1.GameObject.Scripting;
import myGameEngine.GameServerUDP;
import ray.ai.behaviortrees.BTCompositeType;
import ray.ai.behaviortrees.BTSequence;
import ray.ai.behaviortrees.BehaviorTree;
import ray.rml.Vector3;

public class NPCcontroller {
	private NPC npc;
	private float thinkStartTime;
	private float thinkStateTime;
	private float lastThinkUpdateTime;
	private float lastTickUpdateTime;
	private BehaviorTree bt = new BehaviorTree(BTCompositeType.SELECTOR);
	private Scripting jscript = new Scripting("script/location.js");
	private GameServerUDP server;
	private Vector3 npcCurrentLoc;
	private float forX;
	private float forZ;
	public void addServer(GameServerUDP server) {
		this.server = server;
		
	}
	public void start() throws IOException{
		thinkStartTime = System.nanoTime();
		thinkStateTime = System.nanoTime();
		this.lastThinkUpdateTime = thinkStartTime;
		this.lastTickUpdateTime = thinkStateTime;
		setupNPCs();
		npcLoop();
	}
	
	public void updateNPCs(){
		npc.updateLocation();
	}
	
	public void setupNPCs(){
		//jscript.apply("location",this);
		npc = new NPC(0,10.0f,7.0f,10.0f);
	}
	
	
	public void npcLoop() throws IOException {
		while (true) {
			long currentTime = System.nanoTime();
			float elapsedThinkMilliSecs = (currentTime - lastThinkUpdateTime) / (1000000.0f);
			float elapsedTickMilliSecs = (currentTime - lastTickUpdateTime) / (1000000.0f);

			if (elapsedTickMilliSecs >= 50.0f) // “TICK”
			{
				lastTickUpdateTime = currentTime;
				npc.updateLocation();
				//server.sendNPCinfo();
				
				
				if(this.server.getNPCLoc() != null) {
					npc.updateLoc(this.server.getNPCLoc());
					this.npcCurrentLoc = this.server.getNPCLoc();
					//System.out.println(this.npcCurrentLoc);
					if(Math.abs(this.npcCurrentLoc.x()) < 80.0f || Math.abs(this.npcCurrentLoc.z()) < 80.0f){
						
						this.server.sendCommand("moveforward");
						if(elapsedThinkMilliSecs >= 200.0f){
							this.server.sendCommand("yaw");
						}
						
						if(elapsedThinkMilliSecs >= 500.0f){
							this.server.sendCommand("drop");
						}
						
					}else{
						this.server.sendCommand("moveforward");
					}
				}
			
			}
			if (elapsedThinkMilliSecs >= 2000.0f) // “THINK”
			{
				lastThinkUpdateTime = currentTime;
				//bt.update(elapsedMilliSecs);
				
				System.out.println("SErver");
			}
			Thread.yield();
		}
	}
	
	public void setupBehaviorTree(){
		bt.insertAtRoot(new BTSequence(10));
		bt.insertAtRoot(new BTSequence(20));
		//bt.insert(10, new OneSecPassed(this,npc,false));
	}
	
	public NPC getNPC(){
		return this.npc;
	}
}
