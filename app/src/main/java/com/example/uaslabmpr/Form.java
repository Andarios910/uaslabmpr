package com.example.uaslabmpr;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uaslabmpr.databinding.ActivityFormBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Form extends AppCompatActivity {

    ActivityFormBinding binding;
    String  fullName, age, study;
    FirebaseDatabase db;
    DatabaseReference reference;


    Spinner spProvinsi;
    Spinner spKotaKabupaten;
    Spinner spKecamatan;
    Spinner spKelurahan;
    String gender;
    String linkProvinsiAll = "https://dev.farizdotid.com/api/daerahindonesia/provinsi/";
    String linkProvinsi = "https://dev.farizdotid.com/api/daerahindonesia/provinsi/36";
    String linkKotaKabupaten = "https://dev.farizdotid.com/api/daerahindonesia/kota?id_provinsi=";
    String linkKecamatan = "https://dev.farizdotid.com/api/daerahindonesia/kecamatan?id_kota=";
    String linkKelurahan = "https://dev.farizdotid.com/api/daerahindonesia/kelurahan?id_kecamatan=";

    private Spinner spinner;
    private String Items;
    private String prov;
    private String kota;
    private String kecamatan;
    private String kelurahan;

    ArrayList<String> listNamaProvinsi = new ArrayList<>();
    ArrayList<String> listNamaKabupaten_Kota = new ArrayList<>();
    ArrayList<String> listNamaKecamatan = new ArrayList<>();
    ArrayList<String> listNamaKelurahan = new ArrayList<>();

    ArrayList<String> listIDProvinsi = new ArrayList<>();
    ArrayList<String> listIDKabupaten_Kota = new ArrayList<>();
    ArrayList<String> listIDKecamatan = new ArrayList<>();
    ArrayList<String> listIDKelurahan = new ArrayList<>();

    Context context;
    ProgressDialog progressDialog;
    //private int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_form);


          setContentView(binding.getRoot());

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fullName = binding.fullNameForm.getText().toString();
                age = binding.ageForm.getText().toString();
                study = binding.studyForm.getText().toString();
                gender = binding.gender.getSelectedItem().toString();
                prov = binding.spProvinsi.getSelectedItem().toString();
                kota = binding.spKotaKabupaten.getSelectedItem().toString();
                kelurahan = binding.spKelurahan.getSelectedItem().toString();


                if (!fullName.isEmpty() && !age.isEmpty() && !age.isEmpty() && !study.isEmpty() && !prov.isEmpty()){

                    Users users = new Users(fullName,age,study,gender,prov,kota,kecamatan,kelurahan);

                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Users");
                    reference.child(fullName).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            binding.fullNameForm.setText("");
                            binding.ageForm.setText("");
                            binding.studyForm.setText("");
                            binding.gender.getSelectedItem();
                            binding.spProvinsi.getSelectedItem();
                            binding.spKotaKabupaten.getSelectedItem();
                            binding.spKecamatan.getSelectedItem();
                            binding.spKelurahan.getSelectedItem();

                            thankyou();
                            Toast.makeText(Form.this, "Success Updated", Toast.LENGTH_SHORT).show();


                        }

                    });

                }



            }
        });


        spinner = findViewById(R.id.gender);
        String[]Item = new String[]{"Laki - Laki", "Perempuan", "Eunuch"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,Item));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Items = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        context = this;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        spProvinsi = findViewById(R.id.spProvinsi);
        spKotaKabupaten = findViewById(R.id.spKotaKabupaten);
        spKecamatan = findViewById(R.id.spKecamatan);
        spKelurahan = findViewById(R.id.spKelurahan);

        progressDialog.setMessage("Loading....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, linkProvinsiAll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listIDProvinsi.clear();
                listNamaProvinsi.clear();
                try {
                    // Mengambil daerah yang spesifik

//                    String id = jsonObject.getString("id");
//                    String nama = jsonObject.getString("nama");
//                    listIDProvinsi.add(id);
//                    listNamaProvinsi.add(nama);

                    // Mengambil semua data provinsi
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("provinsi");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String nama = object.getString("nama");

                        listIDProvinsi.add(id);
                        listNamaProvinsi.add(nama);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, listNamaProvinsi);
                    spProvinsi.setAdapter(arrayAdapter);

                    progressDialog.dismiss();
                    spProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            prov = spProvinsi.getSelectedItem().toString();
                            showDataKota(listIDProvinsi.get(position));

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
//        showData();

    }

    public void thankyou(){
        Intent i = new Intent (
                this,thankyou.class
        );
        startActivity(i);
    }

    private void showData() {}
//        progressDialog.setMessage("Loading....");
//        progressDialog.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, linkProvinsiAll, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listIDProvinsi.clear();
//                listNamaProvinsi.clear();
//                try {
//                    // Mengambil daerah yang spesifik
//
////                    String id = jsonObject.getString("id");
////                    String nama = jsonObject.getString("nama");
////                    listIDProvinsi.add(id);
////                    listNamaProvinsi.add(nama);
//
//                    // Mengambil semua data provinsi
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = jsonObject.getJSONArray("provinsi");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject object = jsonArray.getJSONObject(i);
//                        String id = object.getString("id");
//                        String nama = object.getString("nama");
//
//                        listIDProvinsi.add(id);
//                        listNamaProvinsi.add(nama);
//                    }
//                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, listNamaProvinsi);
//                    spProvinsi.setAdapter(arrayAdapter);
//
//                    progressDialog.dismiss();
//                    spProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                            showDataKota(listIDProvinsi.get(position));
//
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                        }
//                    });
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);
//    }

    private void showDataKota(String s) {
        progressDialog.setMessage("Loading......");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, linkKotaKabupaten + s, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listIDKabupaten_Kota.clear();
                listNamaKabupaten_Kota.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("kota_kabupaten");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String nama = object.getString("nama");

                        listIDKabupaten_Kota.add(id);
                        listNamaKabupaten_Kota.add(nama);
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, listNamaKabupaten_Kota);
                    spKotaKabupaten.setAdapter(arrayAdapter);

                    progressDialog.dismiss();

                    spKotaKabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            kota = spKotaKabupaten.getSelectedItem().toString();
                            showDataKecamatan(listIDKabupaten_Kota.get(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    private void showDataKecamatan(String s) {
        progressDialog.setMessage("Loading......");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, linkKecamatan + s, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listIDKecamatan.clear();
                listNamaKecamatan.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("kecamatan");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String nama = object.getString("nama");

                        listIDKecamatan.add(id);
                        listNamaKecamatan.add(nama);
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, listNamaKecamatan);
                    spKecamatan.setAdapter(arrayAdapter);

                    spKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            kecamatan = spKecamatan.getSelectedItem().toString();
                            showDataKelurahan(listIDKecamatan.get(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void showDataKelurahan(String s) {
        progressDialog.setMessage("Loading......");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, linkKelurahan + s, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listIDKelurahan.clear();
                listNamaKelurahan.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("kelurahan");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String nama = object.getString("nama");

                        listIDKelurahan.add(id);
                        listNamaKelurahan.add(nama);
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, listNamaKelurahan);

                    spKelurahan.setAdapter(arrayAdapter);
                    spKelurahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            kelurahan = spKelurahan.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}