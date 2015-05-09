package com.example.myrecorder;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.net.Uri;

public class Recorder {
	MediaRecorder mRecorder;
	FileOutputStream mFileOutputStream;
	
	public void start() throws IllegalStateException, IOException{
		if (this.mRecorder == null) {
			// Create unique filename
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss") ;
	    	Calendar c1 = Calendar.getInstance(); // today
	    	String sFilename = MainActivity.STORAGE_PATH + "/rec_" + dateFormat.format(c1.getTime()) + ".3gp";
	    	File mFile = new File(sFilename);
	    	if(!mFile.exists()) {
	    		mFile.createNewFile();
	    	} 
	    	this.mFileOutputStream = new FileOutputStream(mFile);
	    	
	    	// Start media recorder
			this.mRecorder = new MediaRecorder();
			this.mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			this.mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			this.mRecorder.setOutputFile(mFileOutputStream.getFD());
			this.mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			this.mRecorder.prepare();
			this.mRecorder.start();
		} else {
			//Ya hay una grabación en curso
		}
	}
	
	public void stop() throws IOException {
		if (this.mRecorder != null) {
			this.mRecorder.stop();
			this.mRecorder.release();
			this.mRecorder = null;
			this.mFileOutputStream.close();
			this.mFileOutputStream = null;
		} else {
			// No hay grabación activa
		}
	}

}
