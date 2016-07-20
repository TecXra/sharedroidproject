package gcm.play.android.samples.com.gcmquickstart;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class RequestSentActivity extends AppCompatActivity {


    String sharingName;
    String sharingNo;
    String sendToName;
    String sendToNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_sent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharingName = prefs.getString("sharingName", null);
        sharingNo = prefs.getString("sharingPhone", null);
        sendToName = prefs.getString("sendtoName", null);
        sendToNumber = prefs.getString("sendtoPhone", null);

        TextView text=(TextView) findViewById(R.id.txt);
        text.setText("A request is Sent to " + sharingName + " " + sharingNo + " for sending messages to " + sendToName + " " + sendToNumber);
    }

}
