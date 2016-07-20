package Fragments;


import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import Adapters.ChatAdapter;
import gcm.play.android.samples.com.gcmquickstart.ChatActivity;
import gcm.play.android.samples.com.gcmquickstart.R;
import gcm.play.android.samples.com.gcmquickstart.RequestActivity;


/**
 * Created by Maimoona on 5/16/2016.
 */
public class SecondaryFragment extends Fragment {
    private static final String TAG = "ChatActivity";

    private ChatAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private boolean side = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.secondfragment, container, false);

        Button btn= (Button) view.findViewById(R.id.requestBtn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent launchactivity = new Intent(getActivity(), RequestActivity.class);
                startActivity(launchactivity);


            }
        });

        Button chatBtn= (Button) view.findViewById(R.id.chatBtn);
        chatBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent launchactivity = new Intent(getActivity(), ChatActivity.class);
                startActivity(launchactivity);


            }
        });

        /*buttonSend = (Button) view.findViewById(R.id.send);

        listView = (ListView) view.findViewById(R.id.msgview);

        chatArrayAdapter = new ChatAdapter(this.getContext(), R.layout.rightmsg);
        listView.setAdapter(chatArrayAdapter);

        chatText = (EditText) view.findViewById(R.id.msg);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });

    }

    private boolean sendChatMessage() {
        chatArrayAdapter.add(new ChatMessage(side, chatText.getText().toString()));
        chatText.setText("");
        side = !side;
        return true;
        */
        return view;
    }

}