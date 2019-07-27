package com.example.absensionline01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailAbsenActivity extends AppCompatActivity {
    TextView id, nama, jabatan, status, tgl, jam;
    Button absen_btn;
    String telat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_absen);
        id = (TextView) findViewById(R.id.id_tv);
        nama = (TextView) findViewById(R.id.nama_tv);
        jabatan = (TextView) findViewById(R.id.jabatan_tv);
        status = (TextView) findViewById(R.id.status_tv);
        tgl = (TextView) findViewById(R.id.tanggal_masuk);
        jam = (TextView) findViewById(R.id.jam_masuk);
        absen_btn= (Button) findViewById(R.id.absen_btn);
        String ID = getIntent().getStringExtra("id");
        String nama_str = getIntent().getStringExtra("nama");
        String jabatan_str = getIntent().getStringExtra("jabatan");
        String status_str = getIntent().getStringExtra("status");
        id.setText(""+ID);
        nama.setText(""+nama_str);
        jabatan.setText(""+jabatan_str);
        if (status_str.equalsIgnoreCase("FALSE")){
            status.setText("BELUM ABSEN");
        }else{
            status.setText("SUDAH ABSEN");
            absen_btn.setText("SUDAH ABSEN");
            absen_btn.setEnabled(false);
        }
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat nd = new SimpleDateFormat("EEEE");
        String Today = df.format(c);
        String name_day = nd.format(c);
        tgl.setText("Today ("+Today+")");
//        Toast.makeText(this, name_day, Toast.LENGTH_SHORT).show();
        if (name_day.equalsIgnoreCase("Sunday")){
            absen_btn.setText("DAY OFF");
            absen_btn.setEnabled(false);
            jam.setText("Jam Masuk : OFF");
        }



        absen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date Jam = Calendar.getInstance().getTime();
                SimpleDateFormat jm = new SimpleDateFormat("HH.mm");
                String jam_now = jm.format(Jam);
                String URL = "http://pretended-volts.000webhostapp.com/submit_absen.php?id="+getIntent().getStringExtra("id")+"&time="+jam_now;
                Toast.makeText(DetailAbsenActivity.this,URL, Toast.LENGTH_SHORT).show();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    System.out.println(response);
                                    JSONArray array = new JSONArray(response);
                                    JSONObject data = array.getJSONObject(0);
                                    if(data.getString("absen_log").equalsIgnoreCase("success")){
                                        status.setText("SUDAH ABSEN");
                                        absen_btn.setText("SUDAH ABSEN");
                                        absen_btn.setEnabled(false);
                                        telat = data.getString("telat");
                                        if (telat.equalsIgnoreCase("FALSE")){
                                            telat = "TIDAK TERLAMBAT";
                                        }
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(DetailAbsenActivity.this);
                                        builder1.setTitle("ABSEN SUCCESS");
                                        builder1.setMessage("Keterlambatan : "+telat);
                                        builder1.setCancelable(false);
                                        builder1.setPositiveButton(
                                                "Close",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                        final AlertDialog alert11 = builder1.create();
                                        alert11.show();
                                    }else{
                                        Toast.makeText(DetailAbsenActivity.this, "ERROR, Please Login Again", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(DetailAbsenActivity.this, "error", Toast.LENGTH_SHORT).show();

                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println(error.networkResponse);
                                Toast.makeText(DetailAbsenActivity.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                Volley.newRequestQueue(DetailAbsenActivity.this).add(stringRequest);
            }
        });

    }
}
