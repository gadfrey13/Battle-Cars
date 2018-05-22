package myGameEngine;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;
import java.util.Vector;

import a1.MyGame;
import a1.GameObject.GhostNPC;
import a1.GameObject.Player;
import ray.networking.client.GameConnectionClient;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class ProtocolClient extends GameConnectionClient {

	private MyGameEngine game;
	private UUID id;
	private Vector<GhostNPC> ghostNPCs;

	public ProtocolClient(InetAddress remoteAddr, int remotePort,
			ProtocolType protocolType, MyGameEngine game) throws IOException {
		super(remoteAddr, remotePort,protocolType);
		this.game = game;
		this.id = UUID.randomUUID();
		ghostNPCs = new Vector<GhostNPC>();
		
	}
	

	/**
	 * Process the data send by the server
	 * @param message
	 * @return unused
	 */
	@Override
	protected void processPacket(Object message){
		String strMessage = (String) message;
		String[] messageTokens = strMessage.split(",");//Split the message string by ,
		if(messageTokens[0].compareTo("join") == 0){//receive: join
			//format: join, success or join,failure
			if(messageTokens[1].compareTo("success") == 0){
				System.out.println("Client Receive Succesful Join Message");
				game.setIsConnected(true);
				sendCreateMessage(game.getPlayerPosition());//Send the position of the player to the server
			}
			if(messageTokens[1].compareTo("failure") == 0){
				System.out.println("Client Receive Unsucessful in joining game server");
				game.setIsConnected(false);
			}
		}
		
		if(messageTokens[0].compareTo("bye") == 0){//receive: bye
			System.out.print("Client receive bye message from: " + messageTokens[1]);
			//format: bye, remoteId
			UUID avatarID = UUID.fromString(messageTokens[1]);//Get the UUID of the player leaving
			removeAvatar(avatarID);//Remove the player from the game
		}
		
		if(messageTokens[0].compareTo("dsfr") == 0){//receive "dsfr"
			//format: create, remoteId,x,y,z or dsfr, remoteid,x,y,z
			System.out.println("Client Receive A DSFR message" + messageTokens[1]);
			UUID avatarID = UUID.fromString(messageTokens[1]);
			Vector3 avatarPosition = Vector3f.createFrom(Float.parseFloat(messageTokens[2]),Float.parseFloat(messageTokens[3]),Float.parseFloat(messageTokens[4]));
			createAvatar(avatarID,avatarPosition);//Instantiate an avatar
		}
		
		if(messageTokens[0].compareTo("wsds") == 0){//wants details
			System.out.println("Client Recieve A WSDS: " + messageTokens[1]);
			Vector3 playerPosition = game.getPlayerPosition();//Get the players position
			UUID avatarID = UUID.fromString(messageTokens[1]);
			sendDetailsForMessage(avatarID, playerPosition);//Send details of the player position to the server
		}
		
		if(messageTokens[0].compareTo("move") == 0){//details of player moving
			System.out.println("Client Receive a Move message for "+ messageTokens[1] + " " + messageTokens[2]);
			UUID avatarID = UUID.fromString(messageTokens[1]);
			//Vector3 playerPosition =  Vector3f.createFrom(Float.parseFloat(messageTokens[2]),Float.parseFloat(messageTokens[3]),Float.parseFloat(messageTokens[4]));
			//updateAvatarPosition(avatarID,playerPosition);//Update position of the avatar in local game
			String type = messageTokens[2];
			float value = Float.parseFloat(messageTokens[3]);
			updateAvatarPosition(avatarID,type,value);
		}
		
		if(messageTokens[0].compareTo("create") == 0){
			System.out.println("Client receive a create from the server: " + messageTokens[1]);
			UUID avatarID = UUID.fromString(messageTokens[1]);
			Vector3 playerPosition = Vector3f.createFrom(Float.parseFloat(messageTokens[2]),Float.parseFloat(messageTokens[3]),Float.parseFloat(messageTokens[4]));
			createAvatar(avatarID, playerPosition);
		}
		
		if(messageTokens[0].compareTo("location") == 0){
			UUID avatarID = UUID.fromString(messageTokens[1]);
			//Vector3 playerPosition =  Vector3f.createFrom(Float.parseFloat(messageTokens[2]),Float.parseFloat(messageTokens[3]),Float.parseFloat(messageTokens[4]));
			String transform = messageTokens[2];
			updateAvatarPosition(avatarID,transform);//Update position of the avatar in local game
		}
		
		if(messageTokens[0].compareTo("mnpc") == 0){
			System.out.println("Client receive npc move message");
			int ghostID = Integer.parseInt(messageTokens[1]);
			 Vector3 ghostPosition = Vector3f.createFrom(
					 Float.parseFloat(messageTokens[2]),
					 Float.parseFloat(messageTokens[3]),
					 Float.parseFloat(messageTokens[4]));
			 updateGhostNPC(ghostID,ghostPosition);
		}
		
		if(messageTokens[0].compareTo("createNPC") == 0){
			System.out.println("Client receive create npc");
			int ghostID = Integer.parseInt(messageTokens[1]);
			 Vector3 ghostPosition = Vector3f.createFrom(
					 Float.parseFloat(messageTokens[2]),
					 Float.parseFloat(messageTokens[3]),
					 Float.parseFloat(messageTokens[4]));
			 createGhostNPC(ghostID,ghostPosition);
		}
		
		if(messageTokens[0].compareTo("cmd") == 0) {
			int npcId = Integer.parseInt(messageTokens[1]);
			String cm = messageTokens[2];
			updateGhostNPC(npcId,cm);
		}
		
		if(messageTokens[0].compareTo("ammo") == 0){
			String number = messageTokens[1];
			String location = messageTokens[2];
			
		}
		
		
		
	}
	
	public void ammo(String number, String location){
		this.game.spawnAmmoBox(number, location);
	}
	
	public void sendNPCLoc(Vector3 loc) throws IOException {
		String message = new String("npcloc,");
		message += loc.x();
		message += "," + loc.y();
		message += "," + loc.z();
		sendPacket(message);
	}
	
	public void updateGhostNPC(int npcId,String cm) {
		this.game.updateGhostNPC(npcId, cm);
	}
	
	public void createGhostNPC(int id, Vector3 position) {
		
		GhostNPC newNPC = new GhostNPC(id,position);
		ghostNPCs.add(newNPC);
		try{
		game.addGhostNPCtoGame(newNPC,position);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void updateGhostNPC(int i, Vector3 position){
		if(ghostNPCs.get(i) != null){
		  ghostNPCs.get(i).setPosition(position);
		}
	}
	
	public void askForNPCinfo(){
		try{
			sendPacket(new String("needNPC," + id.toString()));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Join the server
	 */
	public void sendJoinMessage(){
		try{
			System.out.println("Send join messge");
			sendPacket(new String("join," + id.toString()));//Send the 
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method sends a data packet to the server. Containing a create
	 * command and the position of the avatar.
	 */
	public void sendCreateTwo(Vector3 pos){
		//format: (create,localid,x,y,z)
		try{
			String message = new String("dsfr," + id.toString());//Progate the creation of new avatar 
			message += "," + pos.x() + "," + pos.y() + "," + pos.z();
			sendPacket(message);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method sends a data packet to the server. Containing a create
	 * command and the position of the avatar.
	 * @param pos
	 */
	public void sendCreateMessage(Vector3 pos){
		//format: (create,localid,x,y,z)
		try{
			String message = new String("create," + id.toString());//Progate the creation of new avatar 
			message += "," + pos.x() + "," + pos.y() + "," + pos.z();
			sendPacket(message);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * Send message to remove client from the server list. Server progate to all clients to remove 
	 * avatar
	 */
	public void sendByeMessage(){
		try{
			sendPacket(new String("bye,"+id.toString()));//Send a packet to the server
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param remid
	 * @param pos
	 */
	public void sendDetailsForMessage(UUID remid, Vector3 pos){
		try{
			this.sendMoveMessage("texture", this.game.getTextColor());
			String message = new String("dsfr," + remid.toString() + "," + id.toString());
			message += "," + pos.x();
			message += "," + pos.y();
			message += "," + pos.z();
			sendPacket(message);//Send the details of the user 
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * Send the movement of the avatar to the server.
	 * @param pos
	 */
	public void updatelocation(Vector3 pos){
		try{
			String message = new String("location," + id.toString());
			message += "," + pos.x();
			message += "," + pos.y();
			message += "," + pos.z();
			sendPacket(message);//Send the movement of the avatar
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void updatelocation(float[] transform){
		try{
			String tr = "";
			for(int i = 0; i < transform.length; i++){
				tr += transform[i] + "x";
			}
			//System.out.println(tr);
			String message = new String("location," + id.toString());
			message += "," + tr;
			sendPacket(message);//Send the movement of the avatar
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void sendMoveMessage(String type, float value){
		try{
			
			String message = new String("move," + id.toString());
			message += "," + type;
			message += "," + value;
			//System.out.println(message);
			sendPacket(message);//Send the movement of the avatar
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Instantiate the game object
	 * @param avatarId
	 * @param pos
	 */
	public void createAvatar(UUID avatarId, Vector3 pos){
		
			try {
				this.sendMoveMessage("texture", this.game.getTextColor());
				Player player = new Player(avatarId,pos);
				System.out.println("Created Ghost Avatar " + avatarId.toString());
				game.addAvatar(player,pos);
			} catch (IOException e) {
				e.printStackTrace();
			}		
	}
	
	/**
	 * Remove the avatar from the game world.
	 * @param avatarId
	 */
	public void removeAvatar(UUID avatarId){
		//String message = new String("bye," + avatarId.toString());//Remove the avatar from all game clients
		//sendPacket(message);
		game.removeAvatarFromGameWorld(avatarId);
	}
	
	/**
	 * Return the id
	 * @return
	 */
	public UUID getID(){
		return id;
	}
	
	public void updateAvatarPosition(UUID id, Vector3 position){
		game.updateGhostAvatarPosition(id, position);
	}
	
	public void updateAvatarPosition(UUID id, String position){
		System.out.println("Client Transform: " + position);
		game.updateGhostAvatarPosition(id, position);
	}
	
	public void updateAvatarPosition(UUID id, String type, float value){
		game.updateGhostAvatarPosition(id,type,value);
	}
}
