package gcm.play.android.samples.com.gcmquickstart;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Adapters.ChatAdapter;
import Utils.webservices.AsyncResponse;
import Utils.webservices.ChatMessage;
import Utils.webservices.Conversation;
import Utils.webservices.RequestExecutor;


public class ChatActivity extends AppCompatActivity implements AsyncResponse {
    private static final String TAG = "ChatActivity";

    Context c;

    private ChatAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private EditText To_num;
    private Button buttonSend;

    private boolean side = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {





        super.onCreate(savedInstanceState);

        setContentView(R.layout.coversation);

        buttonSend = (Button) findViewById(R.id.send);
        To_num = (EditText) findViewById(R.id.to);
        listView = (ListView) findViewById(R.id.msgview);

        chatArrayAdapter = new ChatAdapter(getApplicationContext(), R.layout.rightmsg);
        listView.setAdapter(chatArrayAdapter);

        chatText = (EditText) findViewById(R.id.msg);
       /* chatText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                   // return sendChatMessage();
                }
                return false;
            }
        });
        */



        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ChatActivity.this);

                sharedPreferences.edit().putString(QuickstartPreferences.To_number,To_num.getText().toString() ).commit();
                String to_number = sharedPreferences.getString(QuickstartPreferences.To_number,null);

                sharedPreferences.edit().putString(QuickstartPreferences.SendMessage,chatText.getText().toString() ).commit();

                String message = sharedPreferences.getString(QuickstartPreferences.SendMessage,null);
                String Pid = QuickstartPreferences.Pid;
                String Sid = sharedPreferences.getString(QuickstartPreferences.Sid,null);

                RequestExecutor re = new RequestExecutor(ChatActivity.this);
                re.delegate =ChatActivity.this;
                re.execute("2",Pid,Sid,to_number,message);


                //sendChatMessage();
            }
        });



        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                // listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }

    private boolean sendChatMessage(ArrayList<Conversation> chatMessages) {




        for(int i=0;i<chatMessages.size();i++)
        {
            String messagetext = chatMessages.get(i).message;
            String check = chatMessages.get(i).check;
            boolean position = false;
            if(check.equals("s"))
            {
                position = true;
            }

            chatArrayAdapter.add(new ChatMessage(position,messagetext));

        }
        chatText.setText("");

        return true;
    }

    @Override
    public void onProcessCompelete(Object result) {


        ArrayList<Conversation> chatMessages = (ArrayList<Conversation>)result;

        Toast.makeText(getApplicationContext(),
                "Message Send : OK ", Toast.LENGTH_SHORT).show();


        sendChatMessage(chatMessages);


    }
}