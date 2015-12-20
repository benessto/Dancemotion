package mum.dancemotion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    Button Start, Statistik, Hitlist, Info, DBTest;
    int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Start = (Button) findViewById(R.id.StartButton);

        Statistik = (Button) findViewById(R.id.StatistikButton);

        Hitlist = (Button) findViewById(R.id.HitlistButton);

        Info = (Button) findViewById(R.id.InfoButton);

        DBTest = (Button) findViewById(R.id.DBTestButton);
        DBTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = 5;
                Bundle bundle = new Bundle();
                bundle.putInt("DBTest",status);
                Intent i = new Intent(StartActivity.this, DatenbankTestActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }
}
