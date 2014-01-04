package org.hello;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;

public class MusicalActivity extends Activity {

  private String filename;
  private MediaRecorder recorder;

  @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.hello_layout);

      recorder = new MediaRecorder();

    }

  public String getNewFilename() {
    return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + System.currentTimeMillis() + ".3gp";
  }

  @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

      switch (event.getActionMasked()) {
        case MotionEvent.ACTION_DOWN:
          // start the recording process
          recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
          recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
          recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
          filename = getNewFilename();
          recorder.setOutputFile(filename);
          try {
            recorder.prepare();
          } catch (IOException e) {
            e.printStackTrace();
          }
          recorder.start();   // Recording is now started
          break;

        case MotionEvent.ACTION_UP:
          // stop recording, start looping
          recorder.stop();
          recorder.reset();   // You can reuse the object by going back to setAudioSource() step

          MediaPlayer player = new MediaPlayer();
          try {
            player.setDataSource(filename);
            player.prepare();
            player.setLooping(true);
          } catch (IOException e) {
            e.printStackTrace();
          }
          player.start();

          break;

      }


      return true;
    }

}
