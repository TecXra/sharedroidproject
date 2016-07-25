/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gcm.play.android.samples.com.gcmquickstart;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import Utils.webservices.*;


public class QuickstartPreferences implements AsyncResponse {

    Context context;







    public static RequestExecutor executor ;
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String Token = "Token";

    public static String UserId="2";

    public static  String Pid = UserId;
    public static String Sid = "Sid";
    public static String To_number = "";
    public static String SendMessage = "";


    public static final String SERVER_HOST = "http://192.168.8.100";
    public static final String URL_Token_Update = "/tokenupdate";
    public static final String URI_All_Contacts = "/allcontacts";
    public static final String URL_Secondary_Id_Update = "/SecondaryIdUpdate";

    public static final String URL_SendMessagetoServer = "/SendMsgToPrimary";
    public static final String URL_SendRecievedMessagetoServer = "/RecievedMsgFromPrimary";
    public static final String URL_User_Register="/UserRegister/{number}";





    public static final String URL_PATH = "/cloud";
    public static HttpClient httpclient;

    @Override
    public void onProcessCompelete(Object result) {

    }



    public static void AllowSecondaryUser(String SId)
    {

        try {
            HttpClient httpclient = Utils.getClient();
            HttpPost httppost = new HttpPost(SERVER_HOST+ URL_Secondary_Id_Update);

            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("id", "2"));
            formparams.add(new BasicNameValuePair("secondary_id", SId));

            httppost.setEntity(new UrlEncodedFormEntity(formparams));

            //  Log.i("sendToken"," Token :  " + _token);

            httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // writing exception to log
            e.printStackTrace();
        } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();
        }

    }







    public static void sendTokenToServer(String _token)
    {

        try {
            HttpClient httpclient = Utils.getClient();
            HttpPost httppost = new HttpPost(SERVER_HOST+ URL_Token_Update);

            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("id", "2"));
            formparams.add(new BasicNameValuePair("token", _token));

            httppost.setEntity(new UrlEncodedFormEntity(formparams));

          //  Log.i("sendToken"," Token :  " + _token);

            httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // writing exception to log
            e.printStackTrace();
        } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();
        }


   }






// forward to adnan


    public static boolean CheckMode(String JPid,String JSid)
    {
        if(JSid.equals(Sid) && UserId.equals(JPid))
        {
            return true; //true for primary
        }
    return false;
    }



//check mode
    public static boolean CheckMode2()
    {
        if(UserId.equals(Pid))
        {
            return true; //true for primary
        }
        return false;
    }







// forward to server

    public static boolean checkReplyNumber(String phoneNumber,String Sharenumber)
    {
        if (phoneNumber.equals(Sharenumber))
        {
            return true; // true for primary
        }

        return false;
    }













}
