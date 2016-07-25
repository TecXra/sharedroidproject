package gcm.play.android.samples.com.gcmquickstart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import Utils.webservices.Utils;


public class ReplyReceive extends BroadcastReceiver {

    ArrayList<String> list  = new  ArrayList<String>();



    int duration = Toast.LENGTH_SHORT;

    Toast toast;
    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {


        String phoneNumber="";
        String message="";




        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    phoneNumber = currentMessage.getDisplayOriginatingAddress();


                    message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ phoneNumber + "; message: " + message);

                   Toast.makeText(context,
                            "senderNum: "+ phoneNumber  + ", message: " + message, duration).show();
                   // toast.show();

                }

                SendReciveMessage(message, phoneNumber);
// end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }





        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String Sharenumber = prefs.getString(QuickstartPreferences.To_number,null);


        if(QuickstartPreferences.CheckMode2())
        {
            if (QuickstartPreferences.checkReplyNumber(phoneNumber, Sharenumber))
            {
                //                    SendReciveMessage(message, phoneNumber);
            }
        }
        else
        {
            //Secondary Task

        }




        //    clearAbortBroadcast();
   //     abortBroadcast();
    }




    public void SendReciveMessage(String message,String phoneNumber) throws IOException {

        HttpClient httpclient =Utils.getClient();
        HttpPost httppost = new HttpPost(QuickstartPreferences.SERVER_HOST +QuickstartPreferences.URL_SendRecievedMessagetoServer );

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("primary_id", QuickstartPreferences.Pid));
        formparams.add(new BasicNameValuePair("secondary_id", QuickstartPreferences.Sid));
        formparams.add(new BasicNameValuePair("to_number", phoneNumber));
        formparams.add(new BasicNameValuePair("message", message));


        httppost.setEntity(new UrlEncodedFormEntity(formparams));

        Log.i("SmsReceiver", "senderNum: "+ phoneNumber + " message: " + message);

        httpclient.execute(httppost);


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