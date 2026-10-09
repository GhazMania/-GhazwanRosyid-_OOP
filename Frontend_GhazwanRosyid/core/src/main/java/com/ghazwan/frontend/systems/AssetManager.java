package com.ghazwan.frontend.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.*;

public class AssetManager {
    // 1. Single instance for the Singleton pattern
    private static AssetManager instance;

    // 2. Caches for the Flyweight pattern
    private Map<String, TextureRegion> textureRegionMap;
    private Map<String, Animation<TextureRegion>> animationMap;
    private Map<String, Texture> textureMap;

    // Find out: why is the AssetManager constructor *private*?
    private AssetManager() {
        // TODO: Initialize the three Maps above as empty HashMaps
        textureRegionMap = new HashMap<>();
        animationMap = new HashMap<>();
        textureMap = new HashMap<>();
    }

    // Global access point for the single instance
    public static AssetManager getInstance() {
        // TODO: If instance is still null, create a new instance (Lazy Initialization)
        // Return the instance reference
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    public Texture loadTexture(String filename) {
        // TODO:
        // 1. Check whether filename is already in textureMap.
        // 2. If it is not:
        //    - Check that Gdx.files != null and the file exists using Gdx.files.internal(filename).exists()
        //    - Create a new Texture: new Texture(Gdx.files.internal(filename))
        //    - Store it in textureMap with filename as the key
        //    - If the file does not exist / Gdx.files is not ready, return null
        // 3. Return the stored Texture
        if (!textureMap.containsKey(filename)){
            if(Gdx.files!=null && Gdx.files.internal(filename).exists()){
                Texture texture = new Texture(Gdx.files.internal(filename));
                textureMap.put(filename, texture);
            } else
                return null;
        }
        return textureMap.get(filename);
    }

    // ========================================================================
    // Register the Region Textures
    // ========================================================================

    // Register a single region
    public void registerRegion(String key, TextureRegion region) {
        textureRegionMap.put(key, region);
    }

    // Extract a specific cell from the sprite sheet at [row][col]
    public void registerRegionFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int col) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);
            // TODO: Store the region grid[row][col] in textureRegionMap under this key
            textureRegionMap.put(key, grid[row][col]);
        }
    }

    // Convenience overload: register an animation starting at column 0 with PlayMode.LOOP
    public void registerAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int numFrames, float frameDuration) {
        registerAnimationFromSheet(key, filename, tileWidth, tileHeight, row, 0, numFrames, frameDuration, LOOP);
    }

    // Register a sequence of horizontal frames as an Animation object
    public void registerAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int startCol, int numFrames, float frameDuration, Animation.PlayMode playMode) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);

            // TODO:
            // 1. Create a TextureRegion[] array with numFrames elements
            TextureRegion[] frames = new TextureRegion[numFrames];
            // 2. Fill the array with grid[row][startCol + i]
            for (int i = 0; i < numFrames; i++) {
                frames[i] = grid[row][startCol + i];
            }
            // 3. Create Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);
            Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);
            // 4. Set the animation's play mode: anim.setPlayMode(playMode);
            anim.setPlayMode(playMode);
            // 5. Store anim in animationMap under key
            animationMap.put(key, anim);
            // 6. Store the first frame (frames[0]) in textureRegionMap under the same key (as the default sprite)
            textureRegionMap.put(key,frames[0]);
        }
    }

    // Overload for flipped animations (e.g., facing left using a horizontal flip)
    public void registerFlippedAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int numFrames, float frameDuration, boolean flipX, boolean flipY) {
        registerFlippedAnimationFromSheet(key, filename, tileWidth, tileHeight, row, 0, numFrames, frameDuration, LOOP, flipX, flipY);
    }

    public void registerFlippedAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int startCol, int numFrames, float frameDuration, Animation.PlayMode playMode, boolean flipX, boolean flipY) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);

            // TODO: Follow the same steps as registerAnimationFromSheet, but copy each frame
            // using 'new TextureRegion(...)', then call 'frame.flip(flipX, flipY)'
            TextureRegion[] frames = new TextureRegion[numFrames];
            for (int i = 0; i < numFrames; i++) {
                frames[i] = grid[row][startCol + i];
                frames[i].flip(flipX, flipY);
            }
            Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);
            anim.setPlayMode(playMode);
            animationMap.put(key, anim);
            textureRegionMap.put(key,frames[0]);
        }
    }

// ========================================================================
// Getting the Region Textures
// ========================================================================

    // Retrieve a region by key
    public TextureRegion getTextureRegion(String key) {
        return textureRegionMap.get(key);
    }

    public TextureRegion getRegion(String key) {
        return getTextureRegion(key);
    }

    // Retrieve an animation by key
    public Animation<TextureRegion> getAnimation(String key) {
        return animationMap.get(key);
    }

// ========================================================================
// Initialization for EntityFactory
// ========================================================================
public void init() {
    // Tip 1: Explore values for row, startCol, numFrames, frameDuration, and Animation.PlayMode that look good to you.
    // Tip 2: Among the .png files registered below, which sprite sheets contain *animation* frames, and which contain *static images*? What does this mean for how you use them?

    // TODO: Register Reimu Hakurei's idle animation (player.png: 32x48 per cell)
    registerAnimationFromSheet("player_idle", "player.png", 32, 48, 0, 0, 8, 0.125f, LOOP);
    registerAnimationFromSheet("player_left", "player.png", 32, 48,1, 0, 4, 0.12f, LOOP);
    registerAnimationFromSheet("player_right", "player.png", 32, 48,2, 0, 4, 0.12f, LOOP);
    // TODO: Register the Boss's idle animation (rumia.png: 64x64 per cell)
    registerAnimationFromSheet("boss_idle", "rumia.png", 64, 64, 0, 0, 4, 0.2f, LOOP);
    registerAnimationFromSheet("boss_left", "rumia.png", 64, 64, 1, 0, 4, 0.15f, REVERSED);
    registerAnimationFromSheet("boss_idle", "rumia.png", 64, 64, 2, 0, 4, 0.15f, NORMAL);

    // TODO: Register the Fairy animations
    registerAnimationFromSheet("fairy_idle_red","fairy.png",32,32,1, 0, 8,0.125f, LOOP);
    registerAnimationFromSheet("fairy_idle_blue","fairy.png",32,32,0, 0,8,0.125f, LOOP);

    // TODO: Register the enemy bullet (bullets_small.png: 16x16 cell at row 2, column 3)
    registerRegionFromSheet("enemy_bullet", "bullets_small.png", 16, 16, 2, 3);


    // TODO: Register the 4 Item variants (items.png: 16x16 per cell)
    registerRegionFromSheet("item1", "items.png", 16, 16, 0, 0);
    registerRegionFromSheet("item2", "items.png", 16, 16, 0, 1);
    registerRegionFromSheet("item3", "items.png", 16, 16, 0, 2);
    registerRegionFromSheet("item4", "items.png", 16, 16, 0, 3);
}

    public void dispose() {
        // TODO: Release the VRAM resources of all loaded Textures, then clear all Maps
        for (Texture v : textureMap.values()) {
            v.dispose();
        }
        textureMap.clear();
        textureRegionMap.clear();
        animationMap.clear();
    }
}
