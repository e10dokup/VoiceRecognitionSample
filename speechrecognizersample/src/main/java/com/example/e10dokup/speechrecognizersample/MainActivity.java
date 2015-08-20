package com.example.e10dokup.speechrecognizersample;

import android.content.DialogInterface;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final MainActivity self = this;
    private final String TAG = "MainActivity";

    private SpeechRecognizer mSpeechRecognizer;

    private TextView textView;
    private Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizer.setRecognitionListener(mRecognitionListener);

        // 認識結果を表示させる
        textView = (TextView)findViewById(R.id.text_view);

        buttonStart = (Button)findViewById(R.id.button_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                        getPackageName());
                mSpeechRecognizer.startListening(intent);
            }
        });


    }

    RecognitionListener mRecognitionListener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            Toast.makeText(self, "Ready for Speech", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onBeginningOfSpeech() {
            Toast.makeText(self, "Start Speech", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            Log.v(TAG, "recieve : " + rmsdB + "dB");
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            Log.v(TAG,"onBufferReceived");
        }

        @Override
        public void onEndOfSpeech() {
            Toast.makeText(self, "End of Speech", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(int error) {

        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> recData = results
                    .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if(recData.size() > 0) {
                // 認識結果候補で一番有力なものを表示
                textView.setText(recData.get(0));
            }
        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {
        }
    };


}
