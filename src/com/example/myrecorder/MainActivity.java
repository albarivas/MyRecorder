package com.example.myrecorder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.myrecorder.R;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;


public class MainActivity extends Activity {

	Player player;
	Recorder recorder;
	HashMap<String,String> files;
	static final String STORAGE_PATH = Environment.getExternalStorageDirectory().toString()+"/myRecorder";
	
	
	// INITIALIZATION
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Fill file list and set click handler
		this.files = new HashMap<String,String>();
    	File f = new File(MainActivity.STORAGE_PATH);
    	if (!f.exists()) {
    		f.mkdir();
    	}
    	File file[] = f.listFiles();
    	for (int i=0; i < file.length; i++)
    	{
    	    files.put(file[i].getName(),file[i].getAbsolutePath());
    	}
    		
		ListView fileListView = (ListView) findViewById( R.id.file_list);  
		ListAdapter listAdapter = new ArrayAdapter<String>(this, R.layout.simple_row, new ArrayList<String>(this.files.keySet()));
	    fileListView.setAdapter(listAdapter);
	    fileListView.setOnItemClickListener(new OnItemClickListener(){
	    		@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					try {
						player.loadFile(files.get(parent.getItemAtPosition(position).toString()));
						for (int ctr=0;ctr<=parent.getChildCount();ctr++){
	                        if(position==ctr){
	                        	parent.getChildAt(ctr).setBackgroundColor(0xFF8CD2F5);
	                        }else{
	                        	parent.getChildAt(ctr).setBackgroundColor(Color.TRANSPARENT);
	                        }
	                    }
						Button buttonPlayStart = (Button)findViewById(R.id.button_play_start);
						buttonPlayStart.setEnabled(true);
						Button buttonPlayStop = (Button)findViewById(R.id.button_play_stop);
						buttonPlayStop.setEnabled(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	    });
	    
	    //Start Player thread
	    new Thread(new Runnable() {
			public void run() {
				player = new Player(MainActivity.this);
			}
		}).start();
	    
	    //Star Recorder thread
		new Thread(new Runnable() {
			public void run() {
				recorder = new Recorder();
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// CLICK HANDLERS
	
	/** Called when the user clicks the Start rec/Stop rec button 
	 * @throws IOException 
	 * @throws IllegalStateException */
    public void startRecording(View view) throws IllegalStateException, IOException {
    	Button button = (Button)findViewById(R.id.button_record);
    	String buttonTitle = (String) button.getText();
    	if (buttonTitle.equals(R.string.button_record_start)) {
    		recorder.start();
	    	button.setText(R.string.button_record_stop);
    	} else if (buttonTitle.equals(R.string.button_record_stop)) {
    		recorder.stop();
    		button.setText(R.string.button_record_start);
    	}
	 }
    
        
    /** Called when the user clicks the Play/Pause button 
     * @throws IOException 
     * @throws IllegalStateException 
     * @throws SecurityException 
     * @throws IllegalArgumentException */
    public void play(View view) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
    	Button button = (Button)findViewById(R.id.button_play_start);
    	String buttonTitle = (String) button.getText();
    	if (buttonTitle.equals(R.string.button_play_start)) {
    		player.play();
	    	button.setText(R.string.button_play_pause);
    	} else if (buttonTitle.equals(R.string.button_play_pause)) {
    		player.pause();
    		button.setText(R.string.button_play_start);
    	}
    }
    
    /** Called when the user clicks the Stop button 
     * @throws IOException 
     * @throws IllegalStateException */
    public void stop(View view) throws IllegalStateException, IOException {
       player.stop();
	}

}
