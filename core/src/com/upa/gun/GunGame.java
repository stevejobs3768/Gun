package com.upa.gun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GunGame extends Game {
	public SpriteBatch batch;
	public GunWorld world;
	private float elapsed;
	private Music music;

	@Override
	public void create () {
	    Assets.load();

	    music = Gdx.audio.newMusic(Gdx.files.internal("sfx/music.mp3"));
        music.setLooping(true);
        music.play();
        batch = new SpriteBatch();
        world = new GunWorld(this);
        elapsed = 0.0f;

        setScreen(new MenuScreen(this));
        
	}

	public void render() {
	    super.render();
    }

    public void doPhysicsStep(float delta) {
	    float frameTime = Math.min(delta, 0.25f);
	    elapsed += frameTime;
	    while (elapsed >= Settings.STEP_TIME) {
	        world.world.step(Settings.STEP_TIME, 6, 2);
	        elapsed -= Settings.STEP_TIME;
        }
    }

	@Override
	public void dispose () {
		batch.dispose();
	}
}
