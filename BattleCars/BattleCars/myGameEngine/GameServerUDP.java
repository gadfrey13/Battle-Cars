package myGameEngine;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

import myGameEngine.NPC.NPCcontroller;
import ray.networking.server.GameConnectionServer;
import ray.networking.server.IClientInfo;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class GameServerUDP extends GameConnectionServer<UUID> {
	private NPCcontroller npcCtrl;
	private boolean npcupdate = false;
	private Vector3 npcLoc;
	private int count = 0;
	public GameServerUDP(int localPort,NPCcontroller npcCtrl) throws IOException {
		super(localPort, ProtocolType.UDP);
		this.npcCtrl = npcCtrl;
	}
	
	@Override
	public void processPacket(Object o, InetAddress senderIP, int senderPort){
		String message=(String)o;//Process a string packet
		String[] messageTokens = message.split(",");
		System.out.println(messageTokens);
		if(messageTokens.length > 0){
			//Case where server receives a JOIN message
			//format: join/localid
			if(messageTokens[0].compareTo("join") == 0){ //If true. First value in the string is join
				try{
					System.out.println("Server Received Join Message" + messageTokens[1]);
					IClientInfo ci;//Client
					ci = getServerSocket().createClientInfo(senderIP,senderPort);//Client IP and Port
					UUID clientID = UUID.fromString(messageTokens[1]);//Client ID
					addClient(ci,clientID);//Add a client to server list
					sendJoinedMessage(clientID,true);//Send a message to the client 
					if(count == 0){
						npcLoc = npcCtrl.getNPC().getLocation();
						count++;
					}
					createNPC(clientID);
					npcupdate = true;
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		//Case where server receives a CREATE message
		//format: create,localid,x,y,z
		if(messageTokens[0].compareTo("create") == 0){
			System.out.println("Server Recieved Create" + messageTokens[1]);
			UUID clientID = UUID.fromString(messageTokens[1]);
			String[] pos = {messageTokens[2],messageTokens[3],messageTokens[4]};
			sendCreateMessage(clientID,pos);//Send the clientID and its position
			sendWantsDetailsMessage(clientID);//Send the details 
		}
		//case where server receives a BYE message
		//format: bye, localid
		if(messageTokens[0].compareTo("bye") == 0){
			System.out.println("Server Receive Bye Message " + messageTokens[1]);
			UUID clientID = UUID.fromString(messageTokens[1]);
			sendByeMessage(clientID);//Send a bye message to all the client in the server
			removeClient(clientID);//Remove the client from the server list
		}
		//Case where server recieves a DETAILS-FOR Message
		if(messageTokens[0].compareTo("dsfr") == 0){
			System.out.println("Server Receive DSFR Message" + messageTokens[1]);
			UUID remoteID = UUID.fromString(messageTokens[1]);
			UUID avatarID = UUID.fromString(messageTokens[2]);
			String[] position = {messageTokens[3],messageTokens[4],messageTokens[5]};
			sendDetailsMessage(remoteID, avatarID, position);//Tell the details for a particular client
		}
		
		//Case where server receives a move message
		if(messageTokens[0].compareTo("move") == 0){
			//System.out.println("Server Receive Mode Message" + messageTokens[1]);
			UUID clientID = UUID.fromString(messageTokens[1]);
			//String[] position = {messageTokens[2],messageTokens[3],messageTokens[4]};
			//sendMoveMessage(clientID, position);//Tells all the clients that a particular has move
			String type = messageTokens[2];
			float value = Float.parseFloat(messageTokens[3]);
			sendMoveMessage(clientID,type,value);
		}
		
		if(messageTokens[0].compareTo("location") == 0){
			UUID clientID = UUID.fromString(messageTokens[1]);
			//String[] position = {messageTokens[2],messageTokens[3],messageTokens[4]};
			String transform = messageTokens[2];
			sendUpdatePosition(clientID, transform);//Tells all the clients that a particular has move
		}
		
		if(messageTokens[0].compareTo("needNPC") == 0){
			System.out.println("System receive a need npc");
			UUID clientID = UUID.fromString(messageTokens[1]);
			createNPC(clientID);
		}
		
		if(messageTokens[0].compareTo("collide") == 0){
			
		}
		
		if(messageTokens[0].compareTo("npcloc") == 0) {
			float x = Float.parseFloat(messageTokens[1]);
			float y = Float.parseFloat(messageTokens[2]);
			float z = Float.parseFloat(messageTokens[3]);
			npcLoc = Vector3f.createFrom(x, y, z);
			System.out.println("NPC LOc");
		}
		
	}
	
	
		
	public Vector3 getNPCLoc() {
		return npcLoc;
	}
	
	public void sendCommand(String command) throws IOException {
		String message = new String("cmd," + Integer.toString(0));
		message += "," + command;
		sendPacketToAll(message);
	}
	
	public boolean npcUpdate(){
		return npcupdate;
	}
	
	public void createNPC(UUID clientID){
	
			try{
				System.out.println("Create NPC SErver");
				String message = new String("createNPC," + Integer.toString(0));//NPCs ID
				//message +="," + (npcCtrl.getNPC().getX());
				//message +="," + (npcCtrl.getNPC().getY());
				//message +="," + (npcCtrl.getNPC().getZ());
				message +="," + npcLoc.x();
				message +="," + npcLoc.y();
				message +="," + npcLoc.z();
				sendPacket(message, clientID);
			}catch(Exception e){
				e.printStackTrace();
			}
		
	}
	
	public void sendNPCinfo(){
	
			try{
				String message = new String("mnpc," + Integer.toString(0));//NPCs ID
				message +="," + (npcCtrl.getNPC().getX());
				message +="," + (npcCtrl.getNPC().getY());
				message +="," + (npcCtrl.getNPC().getZ());
				sendPacketToAll(message);
			}catch(Exception e){
				e.printStackTrace();
			}
		
	}
	
	/**
	 * Informs client wither he/she was successful or unsuccessful
	 * in joining the server 
	 * @param clientID
	 * @param success
	 */
	public void sendJoinedMessage(UUID clientID, boolean success){
		//format: join, success or join, failure
		try{
			String message = new String("join,");
			if(success) message += "success";
			else message += "failure";
			sendPacket(message,clientID);//Send the object to a specific client
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * Inform all clients new that a new client has join the server
	 * @param clientID
	 * @param position
	 */
	public void sendCreateMessage(UUID clientID, String[] position){
		//format: create, remoteld, x,y,z
		try{
			String message = "create," + clientID.toString();
			message += "," + position[0];
			message += "," + position[1];
			message += "," + position[2];
			forwardPacketToAll(message,clientID);//Send the information to all clients except the sender client
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * Sent details to all 
	 * @param clientId
	 * @param remoteId
	 * @param position
	 */
	public void sendDetailsMessage(UUID remoteId,UUID avatarID, String[] position){
		try{
			System.out.println("Remote ID: " + remoteId.toString());
			String message = "dsfr," + avatarID.toString();
			message += "," + position[0];
			message += "," + position[1];
			message += "," + position[2];
			sendPacket(message, remoteId);//Send to a particular client
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * Server sends data to all clients except for particular client
	 * @param clientId
	 */
	public void sendWantsDetailsMessage(UUID clientId){
		try{
			System.out.println("Server sends details to all clients except " + clientId.toString());
			String message = "wsds," + clientId.toString();
			forwardPacketToAll(message, clientId);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * Tell all the client that a particular client has move
	 * @param clientId
	 * @param position
	 */
	public void sendUpdatePosition(UUID clientId, String[] position){
		try{
			System.out.println("Tells all client a particular client has move " + clientId.toString());
			String message = "location," + clientId.toString();
			message += "," + position[0];
			message += "," + position[1];
			message += "," + position[2];
			forwardPacketToAll(message,clientId);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void sendUpdatePosition(UUID clientId, String transform){
		try{
			System.out.println("location transfrom: " + transform);
			String message = "location," + clientId.toString();
			message += "," + transform;
			forwardPacketToAll(message,clientId);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void sendMoveMessage(UUID clientId, String type, float value){
		try{
			String message = "move," + clientId.toString();
			message += "," + type;
			message += "," + value;
			forwardPacketToAll(message,clientId);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * Tell all client that a particular client is leaving
	 * @param clientID
	 * @throws IOException 
	 */
	public void sendByeMessage(UUID clientID){
		try{
		String message = "bye," + clientID.toString();
		forwardPacketToAll(message, clientID);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
