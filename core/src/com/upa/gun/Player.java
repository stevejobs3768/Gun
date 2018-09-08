package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {
    public float timeElapsed;
    boolean moving;
    boolean dying;
    boolean fading;

    Vector2 spawnPoint;
    Polygon hitbox;

    float opacity;

    public static int FRONT = 0;
    public static int BACK = 1;
    public static int LEFT = 2;
    public static int RIGHT = 3;

    int rotation;

    public Player(float x, float y) {
        super(x, y, Assets.playerAtlas.findRegion("playerFront-idle").getRegionWidth(),
                Assets.playerAtlas.findRegion("playerFront-idle").getRegionHeight());

        spawnPoint = new Vector2(x, y);

        timeElapsed = 0.0f;
        moving = false;
        dying = false;

        opacity = 1.0f;

        rotation = FRONT;

        hitbox = new Polygon(new float[]{bounds.x, bounds.y, bounds.x+bounds.width, bounds.y,
                bounds.x+bounds.width, bounds.y+bounds.height, bounds.x, bounds.y+bounds.height});
    }

    public void update(float delta) {
        moving = false;
        hitbox = new Polygon(new float[]{bounds.x, bounds.y, bounds.x+bounds.width, bounds.y,
                bounds.x+bounds.width, bounds.y+bounds.height, bounds.x, bounds.y+bounds.height});

        if (dying) {
            Assets.playerIdleSprites[rotation].rotate(Settings.DEATH_ROTATE_SPEED * delta);
            if (Assets.playerIdleSprites[rotation].getRotation() > 90) {
                dying = false;
                fading = true;
            }
        } if (fading) {
            opacity -= Settings.DEATH_FADE_SPEED * delta;
            if (opacity < 0.0f) {
                opacity = 1.0f;
                Assets.playerIdleSprites[rotation].setRotation(0);
                fading = false;
                this.position.x = spawnPoint.x;
                this.position.y = spawnPoint.y;
                this.bounds.x = position.x;
                this.bounds.y = position.y;
            }
        }
        if (!dying && !fading) {
            if (Gdx.input.isKeyPressed(Settings.KEY_LEFT)) {
                this.position.x -= Settings.PLAYER_SPEED * delta;
                this.bounds.x = this.position.x;
                moving = true;
                rotation = LEFT;
            }

            if (Gdx.input.isKeyPressed(Settings.KEY_RIGHT)) {
                this.position.x += Settings.PLAYER_SPEED * delta;
                this.bounds.x = this.position.x;
                moving = true;
                rotation = RIGHT;
            }

            if (Gdx.input.isKeyPressed(Settings.KEY_DOWN)) {
                this.position.y -= Settings.PLAYER_SPEED * delta;
                this.bounds.y = this.position.y;
                moving = true;
                rotation = FRONT;
            }

            if (Gdx.input.isKeyPressed(Settings.KEY_UP)) {
                this.position.y += Settings.PLAYER_SPEED * delta;
                this.bounds.y = this.position.y;
                moving = true;
                rotation = BACK;
            }
        }

        if (moving) {
            timeElapsed += delta;
        }
    }
}
