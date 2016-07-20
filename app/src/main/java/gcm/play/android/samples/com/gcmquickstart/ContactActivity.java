package gcm.play.android.samples.com.gcmquickstart;


import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;



public class ContactActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_contact);
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



        final Button btn = (Button) findViewById(R.id.goBtn);
        btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent launchactivity = new Intent(ContactActivity.this, VerifyCodeActivity.class);
                startActivity(launchactivity);


            }
        });

            /*public void onClick(View v) {
                final ListView l = (ListView) findViewById(R.id.contacts_list);
                int count = l.getChildCount();
                boolean allUnchecked = true;
                for (int i = 0; i < count; i++) {
                    Object child = (Object) l.getChildAt(i);
                    if (child instanceof RadioButton) {
                        RadioButton checkBoxChild = (RadioButton) child;
                        if (checkBoxChild.isChecked()) {

                            View Rg = (View) checkBoxChild.getParent();
                            View LLayout = (View) Rg.getParent();
                            TextView txtLvId = (TextView) LLayout.findViewById(R.id.no);

                Toast.makeText(getApplicationContext(), "maimoona", Toast.LENGTH_LONG).show();
            }
            //}
            // }
            // }
        });*/
    }


/*
        final Button btn = (Button) findViewById(R.id.next);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
        int count = listView.getChildCount();
        boolean allUnchecked = true;
        for (int i = 0; i < count; i++) {
            Object child = (Object) listView.getChildAt(i);
            if (child instanceof RadioButton) {
                RadioButton checkBoxChild = (RadioButton) child;
                if (checkBoxChild.isChecked()) {


                    Intent intent = new Intent(getBaseContext(), VerifyPhoneActivity.class);
                    intent.putExtra("Selected_Item", checkBoxChild);
                    startActivity(intent)
                    break;    //get out the for loop
                }
            }
        }*/



    // Load data on background

    @Override
    protected void onStop() {
        super.onStop();

    }
}