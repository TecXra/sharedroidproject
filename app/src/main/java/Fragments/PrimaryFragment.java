package Fragments;


import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import Adapters.UserAdapter;
import Utils.webservices.Utils;
import gcm.play.android.samples.com.gcmquickstart.QuickstartPreferences;
import gcm.play.android.samples.com.gcmquickstart.R;
import gcm.play.android.samples.com.gcmquickstart.AllowToShareActivity;
import Utils.webservices.UserSelection;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maimoona on 5/16/2016.
 */
public class PrimaryFragment extends Fragment implements View.OnClickListener {
    ArrayList<UserSelection> selectUsers;
    List<UserSelection> temp;
    // Contact List
    ListView listView;
    // Cursor to load contacts list
    Cursor phones;

    Context _c;


    // Pop up
    ContentResolver resolver;
    SearchView search;
    UserAdapter adapter;
    String name,phone;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_contact, container, false);
        final Button btn = (Button) view.findViewById(R.id.goBtn);

        //SharedPreferences sharedPref = getActivity().getPreferences(getContext(),);
        // SharedPreferences pref = getContext().getSharedPreferences("MyPref", getContext().MODE_PRIVATE);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        name = prefs.getString("name", null);
        phone = prefs.getString("phone", null);
        if(name!=null||phone!=null) {


            Toast.makeText(getContext(), "You have gave Permission to " + name + " ( " + phone + " ) to Share Your Phone", Toast.LENGTH_LONG).show();
        }
        selectUsers = new ArrayList<UserSelection>();
        resolver = this.getContext().getContentResolver();
        listView = (ListView) view.findViewById(R.id.contacts_list);
        btn.setOnClickListener(this);
        phones = this.getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        LoadContact loadContact = new LoadContact();
        loadContact.execute();

        search = (SearchView) view.findViewById(R.id.searchView);


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                adapter.filter(newText);
                return false;
            }
        });

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
        }
        final Button btn = (Button) findViewById(R.id.next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int child=listView.getChildCount();
                for(int i=0;i<child;i++) {
                    if(listView.childAt(i) instanceof RadioButton){
                        RadioButton rg=(RadioButton)listView.childAt(i);
                        int a = rg.getChildCount();
                        for(int j=0;j<a;++j) {
                            if((RadioButton)rg.getChildAt(j).isChecked()==true) {
                                System.out.println("RadioButton At "+j+" Is Selected");}
                        }
                    }
                }
            }
        });
        }*/


        return view;
    }

    @Override
    public void onClick(View v) {






        /*int count = listView.getChildCount();

        for (int i = 0; i < count; i++) {
            Object child = (Object) listView.getChildAt(i);
            if (child instanceof RadioButton) {
                RadioButton checkBoxChild = (RadioButton) child;
                if (checkBoxChild.isChecked()) {

                    //View c= (View) checkBoxChild.getParent();
                  //  TextView txtLvId = (TextView) c.findViewById(R.id.name);
                   /* View Rg = (View) checkBoxChild.getParent();
                    View LLayout = (View) Rg.getParent();
                    TextView txtLvId = (TextView) Rg.findViewById(R.id.name);
*/
        // Toast.makeText(getActivity(), "name  " + d, Toast.LENGTH_LONG).show();
        Intent launchactivity = new Intent(getActivity(), AllowToShareActivity.class);
        startActivity(launchactivity);
    }



    // Load data on background
    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {



            HttpClient httpclient =Utils.getClient();

            HttpGet httpget = new HttpGet(QuickstartPreferences.SERVER_HOST + QuickstartPreferences.URI_All_Contacts);    //"http://192.168.1.100/bus"

            String jsonString = "";
            try {

                HttpResponse response = httpclient.execute(httpget);
                jsonString = EntityUtils.toString(response.getEntity());
                JSONArray jsonArray= new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    UserSelection selectUser = new UserSelection();
                    selectUser.setPhone(jsonArray.getJSONObject(i).getString("phone_number"));
                    selectUser.setId(""+jsonArray.getJSONObject(i).getInt("id"));
                    selectUser.setRadioButton(false);

                    selectUsers.add(selectUser);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
















/*




            // Get Contact list from Phone

            if (phones != null) {
                // Log.d("count", "" + phones.getCount());

                if (phones.getCount() == 0) {
                    Toast.makeText(getActivity(), "No contacts in your contact list.", Toast.LENGTH_LONG).show();
                }

                while (phones.moveToNext()) {
                    Bitmap bit_thumb = null;
                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String image_thumb = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
                    try {
                        if (image_thumb != null) {
                            bit_thumb = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(image_thumb));
                        } else {
                            Log.e("No Image Thumb", "--------------");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    UserSelection selectUser = new UserSelection();
                    selectUser.setThumb(bit_thumb);
                    selectUser.setName(name);
                    selectUser.setPhone(phoneNumber);
                    selectUser.setRadioButton(false);
                    selectUsers.add(selectUser);
                }
            } else {
                Log.e("Cursor close 1", "----------------");
            }
            //phones.close();
            return null;
  */
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new UserAdapter(selectUsers, getActivity());
            listView.setAdapter(adapter);

            // Select item on listclick
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Log.e("search", "here---------------- listener");

                    UserSelection data = selectUsers.get(i);

                }
            });

            listView.setFastScrollEnabled(true);

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        phones.close();
    }
}