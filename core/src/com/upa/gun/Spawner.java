package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Spawner {
    GunWorld gunWorld;
    World world;
    public int slimesKilled;
    int slimesKilledSinceLastBoss;

    float timeAccumulated;

    float maxSpawnTime;
    float maxSpawnTimeMax;

    boolean bossAlive;

    int bossThreshold;

    int bossHealth;

    Spawner(GunWorld gunWorld, World world) {
        this.gunWorld = gunWorld;
        this.world = world;

        slimesKilled = 0;
        slimesKilledSinceLastBoss = 0;

        timeAccumulated = 0f;

        maxSpawnTime = generateRandomSpawnTime();
        maxSpawnTimeMax = 5.0f;

        bossAlive = false;

        bossThreshold = 30;

        bossHealth = 30;
    }

    float generateRandomSpawnTime() {
        return (float) Math.random() * maxSpawnTimeMax / Settings.PERCENT_SPAWN_CHANCE;
    }

    void createSpawn(SpawnIndicator spawn) {
        spawn.createSpawn(world, gunWorld);
    }

    void spawnSlime() {
        int spawnX = (int) (Math.random() * 1051) + 113;
        int spawnY = (int) (Math.random() * 600) + 100;
        int slimeType = (int) (Math.random() * 4);
        if (slimeType == 0) {
            gunWorld.indicators.add(new SpawnIndicator(spawnX, spawnY, 0f, 1f, StrongSlime.class));
        } else {
            gunWorld.indicators.add(new SpawnIndicator(spawnX, spawnY, 0f, 1f, Slime.class));
        }
    }

    void spawnBossSlime() {
        TextureRegion bossSlimeHurt = Assets.bossSlimeAnimations.get(ActionState.HURT).get(Direction.LEFT).getKeyFrame(0);
        int spawnX = (int) (Settings.RESOLUTION.x - bossSlimeHurt.getRegionWidth()) / 2;
        int spawnY = (int) (Settings.RESOLUTION.y - bossSlimeHurt.getRegionHeight() / 2);

        BossSlimeFactory factory = new BossSlimeFactory();
        gunWorld.enemies.add(factory.makeBossSlime(bossHealth, spawnX, spawnY, world, gunWorld));
    }

    void update(float delta) {
        timeAccumulated += delta;
        if (slimesKilledSinceLastBoss == bossThreshold && !bossAlive) {
            spawnBossSlime();
            bossAlive = true;
            slimesKilledSinceLastBoss = 0;
            bossHealth += 10;
            maxSpawnTimeMax *= 0.75f;
        }

        if (timeAccumulated >= maxSpawnTime && !bossAlive) {
            spawnSlime();
            maxSpawnTime = generateRandomSpawnTime();
            timeAccumulated = 0;
        }
    }

}
