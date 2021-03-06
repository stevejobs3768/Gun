package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameOver extends ScreenAdapter {
    GunGame game;
    OrthographicCamera camera;
    GlyphLayout layout;
    
    float textAlpha;
    boolean fading;

    GameOver(GunGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.RESOLUTION.x, Settings.RESOLUTION.y);

        layout = new GlyphLayout();

        textAlpha = 1.0f;
        fading = true;


    }

    void draw() {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.enableBlending();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        Assets.menuFont.setColor(1,1,1,1);
        Assets.menuFont.getData().setScale(8);
        layout.setText(Assets.menuFont, "GAME OVER.");
        Assets.menuFont.draw(game.batch, layout, (Settings.RESOLUTION.x - layout.width) / 2,
                (Settings.RESOLUTION.y*4/5 + layout.height*0.5f));
        game.batch.end();

        game.batch.begin();
        Assets.menuFont.setColor(1,1,1,1);
        Assets.menuFont.getData().setScale(2);
        layout.setText(Assets.menuFont, "By Rowan, Scott, Tushar, and Ishan.");
        Assets.menuFont.draw(game.batch, layout, (Settings.RESOLUTION.x - layout.width) / 2,
                Settings.RESOLUTION.y*3/5 + layout.height*0.5f);
        game.batch.end();

        game.batch.begin();
        Assets.menuFont.setColor(1,1,1,textAlpha);
        Assets.menuFont.getData().setScale(4);
        layout.setText(Assets.menuFont, "Your Score: " + game.world.spawner.slimesKilled);
        Assets.menuFont.draw(game.batch, layout, (Settings.RESOLUTION.x - layout.width) / 2,
                (Settings.RESOLUTION.x/6));

        game.batch.end();
    }

    private void update(float delta) {
        if (fading) {
            textAlpha -= Settings.DEATH_FADE_SPEED * delta;
            if (textAlpha <= 0.0f) {
                fading = false;
            }
        } else {
            textAlpha += Settings.DEATH_FADE_SPEED * delta;
            if (textAlpha >= 1.0f) {
                fading = true;
            }
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }
}
