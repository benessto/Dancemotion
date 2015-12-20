package mum.dancemotion;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DatenbankTestActivity extends AppCompatActivity {
    EditText SONG_NAME, SONG_DAUER;
    String song_name, song_dauer;
    Button SPButton;
    Context ctx = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datenbank_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SONG_NAME = (EditText) findViewById(R.id.SongNameText);
        SONG_DAUER = (EditText) findViewById(R.id.SongDauerText);
        SPButton = (Button) findViewById(R.id.SpeicherButton);
        SPButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                song_name = SONG_NAME.getText().toString();
                song_dauer = SONG_DAUER.getText().toString();

                DatenbankOperations DB = new DatenbankOperations(ctx);
                DB.putInformationIntoSong(DB, song_name, song_dauer);
                Toast.makeText(getBaseContext(), "Song-Daten wurden in die Datenbank eingefügt!", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}