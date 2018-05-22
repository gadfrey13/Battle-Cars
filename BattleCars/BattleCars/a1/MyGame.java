package a1;

import myGameEngine.MyGameEngine;
import ray.rage.game.Game;

public class MyGame extends MyGameEngine {
	public MyGame(String serverAddress, int serverPort) {
		super(serverAddress, serverPort);
		/*System.out.println("press T to render triangles");
		System.out.println("press L to render lines");
		System.out.println("press P to render points");
		System.out.println("press C to increment counter");*/
	}

	public static void main(String[] args) {
		PromptBox pb = new PromptBox();
		boolean fullScreen = pb.getFullScreen();
		int color = pb.getColor();
		pb.setVisible(true);
		int foobar = 0;
		while (pb.getContentPane().isVisible())
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fullScreen = pb.getFullScreen();
			color = pb.getColor();
		}
		pb.dispose();
		System.out.println("Full screen "+fullScreen+", color "+color);
		Game game = new MyGame(args[0],Integer.parseInt(args[1]));
		((MyGameEngine)game).setParams(color, fullScreen);
		try {
			game.startup();
			game.run();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally {
			game.setState(Game.State.STOPPING);
			game.exit();
		}
	}
}
