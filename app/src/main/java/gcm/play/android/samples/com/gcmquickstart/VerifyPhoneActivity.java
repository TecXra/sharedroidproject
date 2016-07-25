package gcm.play.android.samples.com.gcmquickstart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import Adapters.CustomAdapter;
import Utils.webservices.AsyncResponse;
import Utils.webservices.RequestExecutor;

public class VerifyPhoneActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener ,AsyncResponse {
    String[] countryNames={"Pakistan","India","Afghanistan","Indonesia","Egypt"};
    String[] countryCodes={"+92","+91","+93","+62","+20"};
    int flags[] = {R.drawable.pakistan, R.drawable.india, R.drawable.afghanistan, R.drawable.indonesia, R.drawable.egypt};

    EditText phonenum;
      SharedPreferences sharedPreferences ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        sharedPreferences.edit().putString(QuickstartPreferences.UserId, "1").apply();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        Spinner spin = (Spinner) findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(this);

        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),flags,countryNames,countryCodes);
        spin.setAdapter(customAdapter);


        phonenum = (EditText) findViewById(R.id.edittext);


        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


               String number = phonenum.getText().toString();
   //             RequestExecutor re = new RequestExecutor(VerifyPhoneActivity.this);
   //             re.delegate = VerifyPhoneActivity.this;
   //             re.execute("4",number);



                // TODO Auto-generated method stub
                Intent launchactivity = new Intent(VerifyPhoneActivity.this, VerifyCodeActivity.class);
                startActivity(launchactivity);





            }
        });
    }



    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        Toast.makeText(getApplicationContext(), countryCodes[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProcessCompelete(Object result) {

        String value = (String) result;


        if(value.equals(""))
        {

            Toast.makeText(getApplicationContext(),
                    "User : Exist ", Toast.LENGTH_SHORT).show();

        }else
        {

 //           sharedPreferences.edit().putString(QuickstartPreferences.UserId,value ).apply();

 //           sharedPreferences.edit().putString(QuickstartPreferences.UserId, "1").apply();
            Toast.makeText(getApplicationContext(),
                    "New User Added ...", Toast.LENGTH_SHORT).show();
        }






    }
}