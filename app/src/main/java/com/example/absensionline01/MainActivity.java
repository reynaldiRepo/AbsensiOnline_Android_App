package com.example.absensionline01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private EditText id_pekerja;
    private Button login_btn;
    private String id;
    private int valid_login;
    private UserData userdata ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id_pekerja = (EditText) findViewById(R.id.id_pekerja_input);
        login_btn = (Button) findViewById(R.id.login_btn);
        userdata = new UserData();
    }

    public void loginAction(View view) {
        id = id_pekerja.getText().toString();
        //succses = 0, error = 1
        if (id.length() != 0) {
            String Url = "http://pretended-volts.000webhostapp.com/login.php?id="+id;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            try {
                                System.out.println(response);
                                JSONArray array = new JSONArray(response);
                                JSONObject data = array.getJSONObject(0);
                                Toast.makeText(MainActivity.this, "Login Status -> "+data.getString("status"), Toast.LENGTH_SHORT).show();
                                String status = data.getString("status");
                                if(status.equalsIgnoreCase("success")) {
                                    userdata.setId_karyawan(data.getString("id_karyawan"));
                                    userdata.setNama(data.getString("nama_karyawan"));
                                    userdata.setJabatan(data.getString("jabatan"));
                                    userdata.setSex(data.getString("sex"));
                                    userdata.setAbsen(data.getString("absen"));
                                    valid_login = 0;
//                                    Toast.makeText(MainActivity.this, userdata.getId_karyawan(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getBaseContext(), DetailAbsenActivity.class);
                                    intent.putExtra("id", userdata.getId_karyawan());
                                    intent.putExtra("nama", userdata.getNama());
                                    intent.putExtra("jabatan", userdata.getJabatan());
                                    intent.putExtra("status", userdata.getAbsen());
                                    startActivity(intent);
                                }else{
                                    valid_login = 1;
                                    Toast.makeText(MainActivity.this, "ID SALAH", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

            );
            Volley.newRequestQueue(this).add(stringRequest);


        } else {
            Toast.makeText(this, "ID Cannot Be Null", Toast.LENGTH_SHORT).show();
        }
    }

}
