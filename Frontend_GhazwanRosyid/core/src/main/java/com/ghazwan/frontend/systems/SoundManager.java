package com.ghazwan.frontend.systems;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static SoundManager instance;

    private Map<String, Music> musicMap;
    private Map<String, Sound> soundMap;

    private SoundManager(){
        musicMap = new HashMap<>();
        soundMap = new HashMap<>();
    }

    public static SoundManager getInstance() {
        // TODO: If instance is still null, create a new instance (Lazy Initialization)
        // Return the instance reference
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public Music loadMusic(String key, String filename) {
        // TODO:
        // 1. Check whether filename is already in textureMap.
        // 2. If it is not:
        //    - Check that Gdx.files != null and the file exists using Gdx.files.internal(filename).exists()
        //    - Create a new Texture: new Texture(Gdx.files.internal(filename))
        //    - Store it in textureMap with filename as the key
        //    - If the file does not exist / Gdx.files is not ready, return null
        // 3. Return the stored Texture
        if (!musicMap.containsKey(key)){
            if(Gdx.files!=null && Gdx.files.internal(filename).exists()){
                Music music = Gdx.audio.newMusic(Gdx.files.internal(filename));
                musicMap.put(key, music);
            } else
                return null;
        }
        return musicMap.get(key);
    }

    public Sound loadSound(String key, String filename) {
        // TODO:
        // 1. Check whether filename is already in textureMap.
        // 2. If it is not:
        //    - Check that Gdx.files != null and the file exists using Gdx.files.internal(filename).exists()
        //    - Create a new Texture: new Texture(Gdx.files.internal(filename))
        //    - Store it in textureMap with filename as the key
        //    - If the file does not exist / Gdx.files is not ready, return null
        // 3. Return the stored Texture
        if (!soundMap.containsKey(key)){
            if(Gdx.files!=null && Gdx.files.internal(filename).exists()){
                Sound sound = Gdx.audio.newSound(Gdx.files.internal(filename));
                soundMap.put(key, sound);
            } else
                return null;
        }
        return soundMap.get(key);
    }

    public void playSound(String key) {
        Sound sound = soundMap.get(key);
        if (sound != null) {
            sound.play(1f);
        }
    }

    public void playMusic(String key) {
        Music music = musicMap.get(key);
        if (music != null) {
            music.setLooping(true);
            music.play();
        }
    }

    public void dispose() {
        for (Sound sound : soundMap.values()) {
            sound.dispose();
        }
        for (Music music : musicMap.values()) {
            music.dispose();
        }
        soundMap.clear();
        musicMap.clear();
    }

    public void init(){
        loadSound("shoot1","shoot1.mp3");
        loadSound("itemclaim1","itemclaim1.mp3");
        loadSound("enemy_death","enemy_death.mp3");
        loadMusic("badapple","badapple.mp3");
    }
}
