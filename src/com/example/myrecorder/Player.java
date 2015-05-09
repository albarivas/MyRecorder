package com.example.myrecorder;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;

public class Player {
	MediaPlayer mPlayer;
	Context context;
	FileInputStream mFileInputStream;
	
	public Player(Context context) {
		this.context = context;
	}
	
	public void loadFile(String mFilename) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException{
		if (this.mPlayer==null) {
			//Open file
			File mFile = new File(mFilename);
			this.mFileInputStream = new FileInputStream(mFile);
			//Initialize mPlayer
			this.mPlayer = new MediaPlayer();
			this.mPlayer.setDataSource(this.mFileInputStream.getFD());
			this.mPlayer.setOnCompletionListener(new OnCompletionListener() {
	            public void onCompletion(MediaPlayer mp) {
	            	System.out.println("Finished!");
	            }
	        });
			this.mPlayer.prepare();
		}
	}
	
	public void play() {
		if (this.mPlayer!=null && !this.mPlayer.isPlaying()) {
			//Continue playing	
			this.mPlayer.start();
		} else {
			// Player no inicializado o reproduciendo
		}
	}

	public void pause() {
		if (this.mPlayer!=null && this.mPlayer.isPlaying()) {
			//Pause
			this.mPlayer.pause();
		} else {
			// Player no inicializado o no reproduciendo
		}
	}
	
	public void stop() throws IllegalStateException, IOException{
		if (this.mPlayer != null) {
			if(this.mPlayer.isPlaying())
				this.mPlayer.stop();
			this.mPlayer.release();
			this.mPlayer = null;
			this.mFileInputStream.close();
			this.mFileInputStream = null;
		} else {
			//Player no inicializado
		}
	}

}
