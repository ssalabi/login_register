package com.example.myapplication;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;

import static com.android.volley.Request.Method.POST;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        new NukeSSLCerts().nuke();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);


        //if user presses on login
        //calling the method login
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    userLogin();
                } catch (UnrecoverableKeyException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                } catch (CertificateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //if user presses on not registered
        findViewById(R.id.textViewRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

//    @org.jetbrains.annotations.NotNull
//    public  static String Getdata (String uri ){
//
//        BufferedReader reader = null;
//
//        try {
//
//            URL url = new URL(uri);
//            HttpURLConnection con = null;
//
//            URL testUrlHttps = new URL(uri);
//            if (testUrlHttps.getProtocol().toLowerCase().equals("https"))
//            {
//                trustAllHosts();
//                HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
//                https.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//                con = https;
//            } else
//            {
//                con = (HttpURLConnection) url.openConnection();
//            }
//
//
//            con.setReadTimeout(15000);
//            con.setConnectTimeout(15000);
//            StringBuilder sb = new StringBuilder();
//            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//            }
//
//            return sb.toString();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return "";
//                }
//            }
//        }
//    }

    private void userLogin() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, CertificateException, IOException {
        //first getting the values
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter your username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }


//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
//                "https://cookyfunction.azurewebsites.net/api/HttpTrigger1", null,
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("Response", response.toString());
//                        Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("Error.Response", "Error: " + error.getMessage());
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("username",username);
//                params.put("password",password);
//                return params;
//            }
//
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(jsonObjectRequest);

        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////


//        String cloud_url = "https://cookyfunction.azurewebsites.net/api/HttpTrigger1";
//        HttpsTrustManager.allowAllSSL();
//        if (cloud_url.toLowerCase().contains("https://")) {
//            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            trustStore.load(null, null);
//            SSLSocketFactory sf = new CustomSSLSocketFactory(trustStore);
//            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        //if everything is fine
//            String endpoint = "https://cookyfunction.azurewebsites.net/api/HttpTrigger1";
//            // endpoint = "https://postman-echo.com/post";
//            StringRequest stringRequest = new JSONObject(Request.Method.POST, endpoint,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            progressBar.setVisibility(View.GONE);
//
//                            try {
//                                //converting response to json object
//                                JSONObject obj = new JSONObject(response);
//
//                                //if no error in response
//                                if (!obj.getBoolean("error")) {
//                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//
//                                    //getting the user from the response
//                                    JSONObject userJson = obj.getJSONObject("user");
//
//                                    //creating a new user object
//                                    User user = new User(
//                                            userJson.getInt("id"),
//                                            userJson.getString("username"),
//                                            userJson.getString("email"),
//                                            userJson.getString("gender")
//                                    );
//
//                                    //storing the user in shared preferences
//                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
//
//                                    //starting the profile activity
//                                    finish();
//                                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
//                                } else {
//                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }) {

//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("username", username);
//                    params.put("password", password);
////                params.put("foo1", "bar1");
////                params.put("foo2", "bar2");
//                    return params;
//                }
//            };
//
//            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
//        }
//    }


        ///////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////

        {
            try {

                AsyncTask<Void,Void,Void> a = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        try
                        {
                            URL url = new URL("https://cookyfunction.azurewebsites.net/api/HttpTrigger1");
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            con.setRequestMethod("POST");
                            con.setRequestProperty("User-Agent", "Mozilla/5.0"); ///// need that?
                            /*Map<String, String> parameters = new HashMap<>();
                            parameters.put("username", username);
                            parameters.put("password", password);
                            String POST_PARAMS = "username=ssalabi";*/

                            con.setDoOutput(true);
                            /*OutputStream os = con.getOutputStream();
                            os.write(POST_PARAMS.getBytes());
                            os.flush();
                            os.close();*/
                            //StringBuilder result = new StringBuilder();

                            /*for (Map.Entry<String, String> entry : parameters.entrySet()) {
                                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                                result.append("=");
                                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                                result.append("&");
                            }*/
                            /*result.append(URLEncoder.encode("username", "UTF-8"));
                            result.append("=");
                            result.append(URLEncoder.encode("ssakabi", "UTF-8"));
                            result.append("&");
                            result.append(URLEncoder.encode("password", "UTF-8"));
                            result.append("=");
                            result.append(URLEncoder.encode("123", "UTF-8"));*/

                            DataOutputStream out = new DataOutputStream(con.getOutputStream());
                            //String resultString = result.toString();
                            String resultString = "{ username: '" + username + "', password: '"+ password + "' }";
                            //out.writeBytes(resultString);
                            JSONObject obj1 = new JSONObject(resultString);
                            out.writeBytes(obj1.toString());

                            out.flush();
                            out.close();
                            int responseCode = con.getResponseCode();
                            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                                BufferedReader in = new BufferedReader(new InputStreamReader(
                                        con.getInputStream()));
                                String inputLine;
                                StringBuffer response = new StringBuffer();

                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();

                                // print result
                                System.out.println(response.toString());
                                try {
                                    //converting response to json object
                                    JSONObject obj = new JSONObject(response.toString());

                                    //creating a new user object
                                    User user = new User(
                                            obj.getInt("id"),
                                            obj.getString("username"),
                                            obj.getString("email"),
                                            obj.getString("gender")
                                    );

                                    //storing the user in shared preferences
                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                    //starting the profile activity
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        catch (Exception e)
                        {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
                a.execute();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

//        String url = "https://cookyfunction.azurewebsites.net/api/HttpTrigger1";
//        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        Log.d("Response", response);
//                        progressBar.setVisibility(View.GONE);
//
//                        try {
//                            //converting response to json object
//                            JSONObject obj = new JSONObject(response);
//
//                            //if no error in response
//                            if (!obj.getBoolean("error")) {
//                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//
//                                //getting the user from the response
//                                JSONObject userJson = obj.getJSONObject("user");
//
//                                //creating a new user object
//                                User user = new User(
//                                        userJson.getInt("id"),
//                                        userJson.getString("username"),
//                                        userJson.getString("email"),
//                                        userJson.getString("gender")
//                                );
//
//                                //storing the user in shared preferences
//                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
//
//                                //starting the profile activity
//                                finish();
//                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
//                            } else {
//                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        String errorMessage = error.getMessage();
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("username", username);
//                params.put("password", password);
//                return params;
//            }
//        };
//        ////postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        try {
//            //////VolleySingleton.getInstance(this).addToRequestQueue(postRequest);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}


///////////////


///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(GitHubService.API_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

// Create an instance of our GitHub API interface.
//        GitHubService.GitHub github = retrofit.create(GitHubService.GitHub.class);
//        RequestUser ruser = new RequestUser(username, passwrod );
//        github.login()

//    }
//}
//        HashMap<String, String> body = new HashMap <String, String>() ;
//        body.put( "name", "xiaohui" );
//        body.put( "gender", "male" );

//        VolleyDataRequester.withHttp( this )
//                .setUrl( HTTP_HOST +  POST)
//                .setBody( body )
//                .setMethod( VolleyDataRequester.Method.POST )
//                .setStringResponseListener( new VolleyDataRequester.StringResponseListener() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText( MainActivity.this, "HTTP/POST,StringRequest successfully.", Toast.LENGTH_SHORT ).show();
//                        tvResult.setText( response );
//                    }
//                } )
//                .requestString();
//    }
