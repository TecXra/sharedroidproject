package Utils.webservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import gcm.play.android.samples.com.gcmquickstart.QuickstartPreferences;


public class RequestExecutor extends AsyncTask<Object, Object, Object> {
	public AsyncResponse delegate = null;
	public Context con;

	public RequestExecutor(Context con) {
		super();
		this.con = con;
	}

	@Override
	protected Object doInBackground(Object... params) {

		if (Utils.isNetworkAvailable(con)) {

			if (params[0].equals("1")) {
				return AllowSecondaryUser((String) params[1]);
			}else if (params[0].equals("2")) {
				return SendMessageToServer((String) params[1],(String)  params[2],(String)  params[3],(String)  params[4]);
			}else if (params[0].equals("4")) {
				return UserRegistration((String) params[1]);
			}else{return "Network error";}




		} else {
			return "Network error";
		}

	}


	@Override
	protected void onPostExecute(Object result) {
		delegate.onProcessCompelete(result);
	};


	public Object UserRegistration(String Number)
	{


		HttpClient httpclient = Utils.getClient();

		HttpGet httpget = new HttpGet(QuickstartPreferences.SERVER_HOST+QuickstartPreferences.URL_User_Register.replace("{number}", Number));    //"http://192.168.1.100/bus"
		String result="";
		String jsonString = "";
		try {

			HttpResponse response = httpclient.execute(httpget);
			jsonString = EntityUtils.toString(response.getEntity());
			JSONObject jsonObject = new JSONObject(jsonString);

			result = ("" +jsonObject.getInt("id"));



		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}




		return result;
	}





























// by secondary

	public Object SendMessageToServer(String pId,String Sid,String number,String message)
	{
		String jsonString = "";
        ArrayList<Conversation> chatMessages = new ArrayList<Conversation>();

		try {
			HttpClient httpclient = Utils.getClient();
			HttpPost httppost = new HttpPost(QuickstartPreferences.SERVER_HOST+ QuickstartPreferences.URL_SendMessagetoServer);

			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("primary_id", pId));
			formparams.add(new BasicNameValuePair("secondary_id", Sid));
			formparams.add(new BasicNameValuePair("to_number", number));
			formparams.add(new BasicNameValuePair("message", message));

			httppost.setEntity(new UrlEncodedFormEntity(formparams));


				HttpResponse response = httpclient.execute(httppost);
				jsonString = EntityUtils.toString(response.getEntity());

			JSONArray jsonArray= new JSONArray(jsonString);



			for (int i = 0; i < jsonArray.length(); i++)
			{
				chatMessages.add(new Conversation("" + jsonArray.getJSONObject(i).getString("message"), "" + jsonArray.getJSONObject(i).getString("check")));

			}





				//  Log.i("sendToken"," Token :  " + _token);

			httpclient.execute(httppost);

		} catch (ClientProtocolException e) {
			// writing exception to log
			e.printStackTrace();
		} catch (IOException e) {
			// writing exception to log
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}


		return chatMessages ;

	}

















	public String AllowSecondaryUser(String SId)
	{


		try {
			HttpClient httpclient = Utils.getClient();
			HttpPost httppost = new HttpPost(QuickstartPreferences.SERVER_HOST+ QuickstartPreferences.URL_Secondary_Id_Update);

			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("id", "1"));
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

		return "Approval Granted ....";

	}


	public String postData11(Object... params) {

		HttpClient httpclient = Utils.getClient();
		HttpPost httpPost = new HttpPost("http://192.168.10.134/sendmessage");
	//	httpclient.setRedirectHandler(new CustomRedirectHandler());
		String returnData = "nothing..";

		String	_token = "heavy token";

		try {





				List<NameValuePair> formparams = new ArrayList<NameValuePair>();
				formparams.add(new BasicNameValuePair("sender", "456"));
				formparams.add(new BasicNameValuePair("message", _token));
				//    formparams.add(new BasicNameValuePair("messaage", "value2"));



				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
				//	entity.setContentType("application/x-www-form-urlencoded");
				//	entity.setChunked(true);


			//	HttpPost httpPost = new HttpPost(QuickstartPreferences.SERVER_HOST + QuickstartPreferences.URL_PATH);
				//	httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
				httpPost.setEntity(entity);


			LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();
			CloseableHttpClient httpclient1 = HttpClients.custom()
					.setRedirectStrategy(redirectStrategy)
					.build();

			HttpClientContext context = HttpClientContext.create();

			//HttpContext httpContext = new BasicHttpContext();
			//httpContext.setAttribute(ClientContext.COOKIE_STORE, new BasicCookieStore());

			httpclient1.execute(httpPost,context);
			//	HttpResponse response = httpclient.execute(httpPost);
			//httpclient.execute(httpPost);

			//Log.d("resone","response is :"+response.toString()+"data is:"+response.getStatusLine());
		//	returnData = EntityUtils.toString(response.getEntity());


            /*
            System.out.println("Response Code : "
                    + response.getStatusLine().getStatusCode());

            System.out.println("Response Enity : \n"
                    + response.getEntity());

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            String inputLine;
            StringBuffer responseBuffer = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                responseBuffer.append(inputLine+"\n");
            }
            reader.close();

            // print result
            System.out.println(responseBuffer.toString());


*/




		} catch (ClientProtocolException e) {
			Log.d("Async Request", "Failed by client protocol");
		} catch (IOException e) {
			Log.d("Async Request", "Failed by IO");
		}
		return returnData;
	}

	public String getData(Object... params) {

		HttpClient httpclient = Utils.getClient();
		HttpGet httpget = new HttpGet(QuickstartPreferences.SERVER_HOST+QuickstartPreferences.URL_PATH);//"http://192.168.10.134/cloud"


		String jsonString = "Nothing returned";
		Log.d("jsonString", "Entered in get data finction");
		try {

			HttpResponse response = httpclient.execute(httpget);
			//Log.d("resone","response is :"+response.toString()+"data is:"+response.getStatusLine());
			 jsonString = EntityUtils.toString(response.getEntity());
			Log.d("jsonString", "Recived JSOn response: "+jsonString);
			return jsonString;
		//	return "[{\"Name\":\"Brofen\"},{\"Name\":\"Citrocine\"},{\"Name\":\"Asperine\"}]\n";
		} catch (ClientProtocolException e) {
			Log.d("Async Request", "Failed by client protocol");
		} catch (IOException e) {
			Log.d("Async Request", "Failed by IO");
		}


		return jsonString;
	}











}
