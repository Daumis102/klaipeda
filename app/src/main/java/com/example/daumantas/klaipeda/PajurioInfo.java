package com.example.daumantas.klaipeda;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class PajurioInfo extends FragmentActivity implements OnMapReadyCallback {


    String pajuris;
    String login_url = "http://lekiam.we2host.lt/FetchPajurisInfo.php";
    String oras, banguotumas, vanduo, zydejimas;
    String vejas, lat, lng;
    String gelbetojai, pramogos;
    TextView orasTv, vejasTv, vanduoTv, zydejimasTv, banguotumasTv,pramogosTv, gelbetojaiTv, pajurisTv;
    GoogleMap mMap;
    LinearLayout infoLayout;

    void setInfo(){
        orasTv = (TextView)findViewById(R.id.oras);
        vejasTv = (TextView)findViewById(R.id.vejas);
        vanduoTv = (TextView)findViewById(R.id.vanduo);
        zydejimasTv = (TextView)findViewById(R.id.zydejimas);
        banguotumasTv = (TextView)findViewById(R.id.banguotumas);
        pramogosTv = (TextView)findViewById(R.id.pramogos);
        gelbetojaiTv = (TextView)findViewById(R.id.gelbetojai);
        pajurisTv = (TextView)findViewById(R.id.pajuris);

        orasTv.setText(String.valueOf(oras));
        banguotumasTv.setText(String.valueOf(banguotumas));
        vanduoTv.setText(String.valueOf(vanduo));
        gelbetojaiTv.setText(String.valueOf(gelbetojai));
        pramogosTv.setText(String.valueOf(pramogos));
        vejasTv.setText(String.valueOf(vejas));
        zydejimasTv.setText(String.valueOf(zydejimas));
        pajurisTv.setText(pajuris);
    }

    void update(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("mytag", response);

                    JSONObject jsonObject = new JSONObject(response);
                    oras = jsonObject.getString("oras");
                    banguotumas = jsonObject.getString("banguotumas");
                    vanduo = jsonObject.getString("vanduo");
                    gelbetojai = jsonObject.getString("gelbetojai");
                    pramogos = jsonObject.getString("pramogos");
                    vejas = jsonObject.getString("vejas");
                    zydejimas = jsonObject.getString("zydejimas");
                    lat = jsonObject.getString("lat");
                    lng = jsonObject.getString("lng");

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(PajurioInfo.this);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setInfo();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(LoginActivity.this, "Error",Toast.LENGTH_LONG).show();
                VolleyLog.e("Error: ", error.getMessage());
                error.printStackTrace();

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "Communication Error!", Toast.LENGTH_SHORT).show();
                    Log.d("mytag","communication error test");

                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "Authentication Error!", Toast.LENGTH_SHORT).show();
                    Log.d("mytag","authentification");
                } else if (error instanceof ServerError) {
                    Log.d("mytag","server error");
                    Toast.makeText(getApplicationContext(), "Server Side Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Log.d("mytag","networkerrpor");
                    Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Log.d("mytag","parse error");
                    Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
                }
            }
        })
        {
            protected Map<String, String> getParams()throws AuthFailureError{
                Map<String,String>params = new HashMap<String, String>();
                params.put("pajuris",pajuris);
                return params;
            }
        };
        MySingleton.getInstance(PajurioInfo.this).addToRequestQueue(stringRequest); // checks if there is a queue, if there is, puts request to it
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pajurio_info);
        infoLayout = (LinearLayout)findViewById(R.id.infoLayout);
        infoLayout.setVisibility(View.INVISIBLE);
        update();



        Intent intent = getIntent();
        pajuris = intent.getStringExtra("pajuris");


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        infoLayout.setVisibility(View.VISIBLE);
        PajurioInfoPermissionsDispatcher.setupMapWithCheck(this);
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void setupMap(){
        mMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.valueOf(lat), Double.valueOf(lng)))
                .title("Gelbetojų būstinė")
                .snippet("Šioje vietoje budi gelbėtojai"));

        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(lat),Double.valueOf(lng)), 15);
        mMap.animateCamera(location);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        PajurioInfoPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
