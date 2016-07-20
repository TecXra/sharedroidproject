package gcm.play.android.samples.com.gcmquickstart;
import java.util.ArrayList;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import android.widget.Toast;


public class ReplyReceive extends BroadcastReceiver {
    ArrayList<String> list  = new  ArrayList<String>();
    int duration = Toast.LENGTH_SHORT;
    Toast toast;
    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {






        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);

                   Toast.makeText(context,
                            "senderNum: "+ senderNum + ", message: " + message, duration).show();
                   // toast.show();

                } // end for loop

            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }

    //    clearAbortBroadcast();
   //     abortBroadcast();
    }
    /*
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "sms received..", Toast.LENGTH_LONG).show();
        final Bundle bundle = intent.getExtras();

        try {
            final Object[] pdusObj = (Object[]) bundle.get("pdus");
            SmsMessage receivedtMessage = SmsMessage.createFromPdu((byte[]) pdusObj[0]);
            String senderPhoneNumber = receivedtMessage.getDisplayOriginatingAddress();





            Toast.makeText(context, "sms received from: " + senderPhoneNumber, Toast.LENGTH_LONG).show();
            //--
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }*/
}