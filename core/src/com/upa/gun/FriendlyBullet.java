package com.upa.gun;

import com.badlogic.gdx.physics.box2d.World;

public class FriendlyBullet extends Bullet {
    FriendlyBullet(float x, float y, double angle, World world) {
        super(x, y, angle, world);
    }
}