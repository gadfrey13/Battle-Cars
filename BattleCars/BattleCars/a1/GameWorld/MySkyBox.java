package a1.GameWorld;

import ray.rage.rendersystem.states.*;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SkyBox;
import ray.rage.Engine;
import ray.rage.asset.texture.*;
import ray.rage.util.*;
import java.awt.geom.*;
import java.io.IOException;

public class MySkyBox {
	private static final String SKYBOX_NAME = "SkyBox";
	private boolean skyBoxVisible = true;
	private Engine eng;
	private SceneManager sm;
	public MySkyBox(Engine eng, SceneManager sm){
		this.eng = eng;
		this.sm = sm;
	}
	public void createSkyBox() throws IOException {
		// set up sky box
		Configuration conf = eng.getConfiguration();
		TextureManager tm = eng.getTextureManager();
		tm.setBaseDirectoryPath(conf.valueOf("assets.skyboxes.path"));
		Texture front = tm.getAssetByPath("hills_ft.jpg");
		Texture back = tm.getAssetByPath("hills_bk.jpg");
		Texture left = tm.getAssetByPath("hills_lf.jpg");
		Texture right = tm.getAssetByPath("hills_rt.jpg");
		Texture top = tm.getAssetByPath("hills_up.jpg");
		Texture bottom = tm.getAssetByPath("hills_dn.jpg");
		tm.setBaseDirectoryPath(conf.valueOf("assets.textures.path"));
		// cubemap textures are flipped upside-down.
		// All textures must have the same dimensions, so any image�s
		// heights will work since they are all the same height
		AffineTransform xform = new AffineTransform();
		xform.translate(0, front.getImage().getHeight());
		xform.scale(1d, -1d);
		front.transform(xform);
		back.transform(xform);
		left.transform(xform);
		right.transform(xform);
		top.transform(xform);
		bottom.transform(xform);
		SkyBox sb = sm.createSkyBox(SKYBOX_NAME);
		sb.setTexture(front, SkyBox.Face.FRONT);
		sb.setTexture(back, SkyBox.Face.BACK);
		sb.setTexture(left, SkyBox.Face.LEFT);
		sb.setTexture(right, SkyBox.Face.RIGHT);
		sb.setTexture(top, SkyBox.Face.TOP);
		sb.setTexture(bottom, SkyBox.Face.BOTTOM);
		sm.setActiveSkyBox(sb);
	}
}
