package gcm.play.android.samples.com.gcmquickstart;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Utils.webservices.AsyncResponse;
import Utils.webservices.RequestExecutor;


public class AllowToShareActivity extends AppCompatActivity implements AsyncResponse {
    String name,phone,Sid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allowto_share);
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
        name = prefs.getString("name", null);
        phone = prefs.getString("phone", null);
        Sid=prefs.getString(QuickstartPreferences.Sid, null);

//        TextView text=(TextView) findViewById(R.id.shareText);
//        text.setText(Sid +" ( "+phone+" ) is allowed to Share you phone ");


        RequestExecutor re = new RequestExecutor(this);
        re.delegate = this;
        re.execute("1",Sid);




        Button btn= (Button) findViewById(R.id.Btn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent launchactivity = new Intent(AllowToShareActivity.this, TabsActivity.class);
                startActivity(launchactivity);


            }
        });
    }


    @Override
    public void onProcessCompelete(Object result) {



        TextView text=(TextView) findViewById(R.id.shareText);
        text.setText(" ( "+phone+" ) "+ (String) result);



    }
}
