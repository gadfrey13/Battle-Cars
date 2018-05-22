package myGameEngine;
import java.io.IOException;

import myGameEngine.NPC.NPCcontroller;
import ray.networking.IGameConnection.ProtocolType;
public class NetworkingServer {
	private GameServerUDP thisUDPServer;
	private NPCcontroller npcCtrl;
	private float startTime;
	private float lastUpdateTime;
	public NetworkingServer(int serverPort) throws IOException{
		startTime = System.nanoTime();
		lastUpdateTime = startTime;
		npcCtrl = new NPCcontroller();
		
		try{
		 thisUDPServer = new GameServerUDP(serverPort,npcCtrl);//Create a server
		 npcCtrl.addServer(thisUDPServer);
		}catch(IOException e){
			e.printStackTrace();
		}
	
		//Start NPC control loop
		//npcCtrl.setupNPCs();
		//npcCtrl.npcLoop();
		//npcLoop();
		npcCtrl.start();
		
	}
	
	public void npcLoop() {
		while (true) {
			long frameStartTime = System.nanoTime();
			float elapMilSecs = (frameStartTime - lastUpdateTime) / (1000000.0f);
			if (elapMilSecs >= 50.0f) {
				lastUpdateTime = frameStartTime;
				npcCtrl.updateNPCs();
				if(thisUDPServer.npcUpdate()){
					thisUDPServer.sendNPCinfo();
				}
			}
			Thread.yield();
		}
	}
	
	public static void main(String[] args) throws IOException{
		if(args.length > 0){
			System.out.println(args[0]);
			NetworkingServer app = new NetworkingServer(Integer.parseInt(args[0]));
		}
		
	}
}
