package com.example.daumantas.klaipeda;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ImoniuLogin extends AppCompatActivity {

    Button login_button;
    EditText UserName, Password;
    String username, password;
    int MY_SOCKET_TIMEOUT_MS = 80000;
    String login_url = "http://lekiam.we2host.lt/login.php";
    AlertDialog.Builder builder;


    void FetchPajuris(JSONObject response){
        Intent intent = new Intent(ImoniuLogin.this, PajurisEdit.class);

        try {
            intent.putExtra("name", response.getString("name"));
            intent.putExtra("telefonas", response.getString("telefonas"));
            intent.putExtra("email", response.getString("email"));
            intent.putExtra("pajuris", response.getString("adresas"));
            intent.putExtra("oras", response.getString("oras"));
            intent.putExtra("vanduo", response.getString("vanduo"));
            intent.putExtra("vejas", response.getString("vejas"));
            intent.putExtra("banguotumas", response.getString("banguotumas"));
            intent.putExtra("zydejimas", response.getString("zydejimas"));
            intent.putExtra("darbo_laikas", response.getString("darbo_laikas"));
            intent.putExtra("pramogos", response.getString("pramogos"));
            intent.putExtra("debesuotumas", response.getString("debesuotumas"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imoniu_login);

        builder = new AlertDialog.Builder(ImoniuLogin.this);
        login_button = (Button)findViewById(R.id.login);
        UserName = (EditText)findViewById(R.id.username);
        Password = (EditText)findViewById(R.id.password);

        if(savedInstanceState != null){
            UserName.setText(savedInstanceState.getString("username"));
            Password.setText(savedInstanceState.getString("password"));
        }

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            username = UserName.getText().toString();
            password = Password.getText().toString();

            if (username.equals("") || password.equals("")) {
                builder.setTitle("Something went wrong");
                displayAlert("Enter a valid username and password");
            } else {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("mytag", response);
                            JSONArray jsonArray = new JSONArray(response);

                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String tipas = jsonObject.getString("tipas");
                            if(tipas.equals("pajuris")){
                                FetchPajuris(jsonObject);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(LoginActivity.this, "Error",Toast.LENGTH_LONG).show();
                        VolleyLog.e("Error: ", error.getMessage());
                        Log.d("mytag", "error");
                        error.printStackTrace();

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "Communication Error!", Toast.LENGTH_SHORT).show();
                            Log.d("mytag", "communication error test");

                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(), "Authentication Error!", Toast.LENGTH_SHORT).show();
                            Log.d("mytag", "authentification");
                        } else if (error instanceof ServerError) {
                            Log.d("mytag", "server error");
                            Toast.makeText(getApplicationContext(), "Server Side Error!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            Log.d("mytag", "networkerrpor");
                            Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            Log.d("mytag", "parse error");
                            Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_name", username);
                        params.put("password", password);
                        return params;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        5,
                        5));

                MySingleton.getInstance(ImoniuLogin.this).addToRequestQueue(stringRequest); // checks if there is a queue, if there is, puts request to it
            }
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putString("username", UserName.getText().toString());
        outState.putString("password", Password.getText().toString());
    }


    void displayAlert(String message)
    {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserName.setText("");
                Password.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
