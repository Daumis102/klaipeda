package com.example.daumantas.klaipeda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PajurisEdit extends AppCompatActivity {

    private static final int REQUEST_GET_LOCATION = 1;
    String pajuris_update_url = "http://lekiam.we2host.lt/updateInfo.php";
    String telefonas;
    String email;
    String pajuris;
    String oras;
    String vejas;
    String vanduo;
    String zydejimas;
    String darbo_laikas;
    String pramogos;
    String debesuotumas;
    String banguotumas;
    String name;
    Double lat = null, lng = null;

    EditText tel_ed;
    EditText email_ed;
    EditText oras_ed;
    EditText vanduo_ed;
    EditText vejas_ed;
    EditText darbo_laikas_ed;
    EditText pramogos_ed;

    Button save, openMap;

    ArrayList<Button> sdeb;
    ArrayList<Button> szyd;
    ArrayList<Button> sbang;

    ArrayList<EditText> editTextList = new ArrayList<>();

    boolean checkInput(EditText inp){
        return inp.getText().toString().trim().length() > 0;
    }

    boolean checkAllInputs(){
        boolean allGood = true;
        for (EditText editText : editTextList){
            if(!checkInput(editText)){
                allGood = false;
                editText.setBackgroundResource(R.drawable.red_border);
            }else{
                editText.setBackgroundResource(R.drawable.text_box);
            }
        }

        if(lat == null || lng ==null){
            allGood = false;
            openMap.setBackgroundResource(R.drawable.red_border);
        } else {
            openMap.setBackgroundResource(0);
        }
        Log.d("mytag",String.valueOf(allGood));
        return allGood;
    }

    void changeColor(ArrayList<Button> list, int start, int finish, int color){

        if(finish<=list.size()&&finish>0&&start<finish&&start>=0)
        {
            for(int i = start;i<finish;i++)
            {
                list.get(i).setBackgroundColor(getResources().getColor(color));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pajuris_edit);

        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.pajuris_edit);
        tel_ed = (EditText)findViewById(R.id.telefonas);
        email_ed = (EditText)findViewById(R.id.email);
        oras_ed = (EditText)findViewById(R.id.oras);
        vanduo_ed = (EditText)findViewById(R.id.vanduo);
        vejas_ed = (EditText)findViewById(R.id.vejas);
        darbo_laikas_ed = (EditText)findViewById(R.id.darbo_laikas);
        pramogos_ed = (EditText)findViewById(R.id.pramogos);
        save = (Button)findViewById(R.id.save);
        openMap = (Button)findViewById(R.id.addLoc);

        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PajurisEdit.this, ChangeBussinessLoc.class);
                startActivityForResult(intent, REQUEST_GET_LOCATION);
            }
        });

        for(int i = 0; i < rootLayout.getChildCount(); i++) {
            if(rootLayout.getChildAt(i) instanceof EditText) {
                editTextList.add( (EditText) rootLayout.getChildAt(i));
            }
        }


        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        telefonas = intent.getStringExtra("telefonas");
        email = intent.getStringExtra("email");
        pajuris = intent.getStringExtra("pajuris");
        oras = intent.getStringExtra("oras");
        vanduo = intent.getStringExtra("vanduo");
        banguotumas = intent.getStringExtra("banguotumas");
        vejas = intent.getStringExtra("vejas");
        zydejimas = intent.getStringExtra("zydejimas");
        darbo_laikas = intent.getStringExtra("darbo_laikas");
        pramogos = intent.getStringExtra("pramogos");
        debesuotumas = intent.getStringExtra("debesuotumas");

        tel_ed.setText(telefonas);
        email_ed.setText(email);
        oras_ed.setText(oras);
        vanduo_ed.setText(vanduo);
        vejas_ed.setText(vejas);
        darbo_laikas_ed.setText(darbo_laikas);
        pramogos_ed.setText(pramogos);

        final Button sbang1 = (Button)findViewById(R.id.sbang1);
        final Button sbang2 = (Button)findViewById(R.id.sbang2);
        final Button sbang3 = (Button)findViewById(R.id.sbang3);
        final Button sbang4 = (Button)findViewById(R.id.sbang4);
        final Button sbang5 = (Button)findViewById(R.id.sbang5);

        sbang  = new ArrayList<Button>();
        sbang.add(sbang1);
        sbang.add(sbang2);
        sbang.add(sbang3);
        sbang.add(sbang4);
        sbang.add(sbang5);

        final Button szyd1 = (Button)findViewById(R.id.szyd1);
        final Button szyd2 = (Button)findViewById(R.id.szyd2);
        final Button szyd3 = (Button)findViewById(R.id.szyd3);
        final Button szyd4 = (Button)findViewById(R.id.szyd4);
        final Button szyd5 = (Button)findViewById(R.id.szyd5);

        szyd  = new ArrayList<Button>();
        szyd.add(szyd1);
        szyd.add(szyd2);
        szyd.add(szyd3);
        szyd.add(szyd4);
        szyd.add(szyd5);

        final Button sdeb1 = (Button)findViewById(R.id.sdeb1);
        final Button sdeb2 = (Button)findViewById(R.id.sdeb2);
        final Button sdeb3 = (Button)findViewById(R.id.sdeb3);
        final Button sdeb4 = (Button)findViewById(R.id.sdeb4);
        final Button sdeb5 = (Button)findViewById(R.id.sdeb5);

        sdeb  = new ArrayList<Button>();
        sdeb.add(sdeb1);
        sdeb.add(sdeb2);
        sdeb.add(sdeb3);
        sdeb.add(sdeb4);
        sdeb.add(sdeb5);




        //Change number of stars to number retrieved
        changeColor(sbang,0,Integer.parseInt(banguotumas),R.color.yellow);
        changeColor(sdeb,0,Integer.parseInt(debesuotumas),R.color.yellow);
        changeColor(szyd,0,Integer.parseInt(zydejimas),R.color.yellow);



        View.OnClickListener bangListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.sbang1:
                        banguotumas = "1";
                        sbang1.setBackgroundColor(getResources().getColor(R.color.yellow));
                        changeColor(sbang,1,5,R.color.grey);
                        break;
                    case R.id.sbang2:
                        banguotumas = "2";
                        changeColor(sbang,0,2,R.color.yellow);
                        changeColor(sbang,2,5,R.color.grey);
                        break;
                    case R.id.sbang3:
                        banguotumas = "3";
                        changeColor(sbang,0,3,R.color.yellow);
                        changeColor(sbang,3,5,R.color.grey);
                        break;
                    case R.id.sbang4:
                        banguotumas = "4";
                        changeColor(sbang,0,4,R.color.yellow);
                        sbang5.setBackgroundColor(getResources().getColor(R.color.grey));

                        break;
                    case R.id.sbang5:
                        banguotumas = "5";
                        changeColor(sbang,0,5,R.color.yellow);

                        break;
                    default:
                        throw new RuntimeException("Unknow button ID");
                }
            }
        };

        View.OnClickListener debListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.sdeb1:
                        debesuotumas = "1";
                        sdeb1.setBackgroundColor(getResources().getColor(R.color.yellow));
                        changeColor(sdeb,1,5,R.color.grey);

                        break;
                    case R.id.sdeb2:
                        debesuotumas = "2";
                        changeColor(sdeb,0,2,R.color.yellow);
                        changeColor(sdeb,2,5,R.color.grey);

                        break;
                    case R.id.sdeb3:
                        debesuotumas = "3";
                        changeColor(sdeb,0,3,R.color.yellow);
                        changeColor(sdeb,3,5,R.color.grey);

                        break;
                    case R.id.sdeb4:
                        debesuotumas = "4";
                        changeColor(sdeb,0,4,R.color.yellow);
                        sdeb5.setBackgroundColor(getResources().getColor(R.color.grey));

                        break;
                    case R.id.sdeb5:
                        debesuotumas = "5";
                        changeColor(sdeb,0,5,R.color.yellow);

                        break;
                    default:
                        throw new RuntimeException("Unknow button ID");
                }
            }
        };

        View.OnClickListener zydListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.szyd1:
                        zydejimas = "1";
                        szyd1.setBackgroundColor(getResources().getColor(R.color.yellow));
                        changeColor(szyd,1,5,R.color.grey);

                        break;
                    case R.id.szyd2:
                        zydejimas = "2";
                        changeColor(szyd,0,2,R.color.yellow);
                        changeColor(szyd,2,5,R.color.grey);

                        break;
                    case R.id.szyd3:
                        zydejimas = "3";
                        changeColor(szyd,0,3,R.color.yellow);
                        changeColor(szyd,3,5,R.color.grey);

                        break;
                    case R.id.szyd4:
                        zydejimas = "4";
                        changeColor(szyd,0,4,R.color.yellow);
                        szyd5.setBackgroundColor(getResources().getColor(R.color.grey));

                        break;
                    case R.id.szyd5:
                        zydejimas = "5";
                        changeColor(szyd,0,5,R.color.yellow);

                        break;
                    default:
                        throw new RuntimeException("Unknow button ID");
                }
            }
        };


        for(int i = 0;i<sdeb.size();i++)
        {
            sdeb.get(i).setOnClickListener(debListener);
        }

        for(int i = 0;i<sbang.size();i++)
        {
            sbang.get(i).setOnClickListener(bangListener);
        }

        for(int i = 0;i<szyd.size();i++)
        {
            szyd.get(i).setOnClickListener(zydListener);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mytag","save clicked");
                if(checkAllInputs()){
                    Log.d("mytag", "inputs ok");
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, pajuris_update_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d("mytag", response);
                                JSONArray jsonArray = new JSONArray(response);

                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                if(jsonObject.getString("code").equals("success")){
                                    Toast.makeText(PajurisEdit.this, getResources().getString(R.string.update_success), Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(PajurisEdit.this, getResources().getString(R.string.update_failure) + " ERROR: " + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

                            params.put("name", name);
                            params.put("telefonas", tel_ed.getText().toString());
                            params.put("email", email_ed.getText().toString());
                            params.put("pajuris", pajuris);
                            params.put("oras", oras_ed.getText().toString());
                            params.put("vanduo", vanduo_ed.getText().toString());
                            params.put("banguotumas", banguotumas);
                            params.put("vejas", vejas_ed.getText().toString());
                            params.put("zydejimas", zydejimas);
                            params.put("darbo_laikas", darbo_laikas_ed.getText().toString());
                            params.put("pramogos", pramogos_ed.getText().toString());
                            params.put("debesuotumas", debesuotumas);
                            params.put("lat", String.valueOf(lat));
                            params.put("lng", String.valueOf(lng));
                            return params;
                        }
                    };

                    MySingleton.getInstance(PajurisEdit.this).addToRequestQueue(stringRequest); // checks if there is a queue, if there is, puts request to it
                }else{
                    Toast.makeText(PajurisEdit.this, "Invalid input", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_GET_LOCATION) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a location.
                lat = data.getDoubleExtra("lat", 0);
                lng = data.getDoubleExtra("lng", 0);
                // Do something with the location here
            }
        }
    }


}
