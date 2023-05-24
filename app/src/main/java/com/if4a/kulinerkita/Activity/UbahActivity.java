package com.if4a.kulinerkita.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.if4a.kulinerkita.API.APIRequestData;
import com.if4a.kulinerkita.API.RetroServer;
import com.if4a.kulinerkita.Model.ModelResponse;
import com.if4a.kulinerkita.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahActivity extends AppCompatActivity {
    private String yId, yNama,yAsal, yDeskripsiSingkat;
    private EditText etNama,etAsal, etDeskripsiSingkat;
    private Button btnUbah;
    private String nama, asal, deskripsiSingkat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        Intent ambil = getIntent();
        yId = ambil.getStringExtra("xId");
        yNama = ambil.getStringExtra("xNama");
        yAsal = ambil.getStringExtra("xAsal");
        yDeskripsiSingkat = ambil.getStringExtra("xDeskripsiSingkat");

        etNama = findViewById(R.id.et_nama);
        etAsal = findViewById(R.id.et_asal);
        etDeskripsiSingkat = findViewById(R.id.et_deskripsi_singkat);
        btnUbah = findViewById(R.id.btn_ubah);

        etNama.setText(yNama);
        etAsal.setText(yAsal);
        etDeskripsiSingkat.setText(yDeskripsiSingkat);
        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nama = etNama.getText().toString();
                asal = etAsal.getText().toString();
                deskripsiSingkat = etDeskripsiSingkat.getText().toString();

                if(nama.trim().isEmpty()){
                    etNama.setError("Nama Tidak Boleh Kosong !!!");
                }
                else if (asal.trim().isEmpty())
                {
                    etAsal.setError("Asal Tidak Boleh Kosong !!!");
                }
                else if (deskripsiSingkat.trim().isEmpty())
                {
                    etDeskripsiSingkat.setError("Deskripsi Singkat tidak boleh Kosong !!!");
                }
                else
                {
                    ubahKuliner();
                }
            }
        });
    }
    private void ubahKuliner(){

        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardUpdate(yId,nama,asal,deskripsiSingkat);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this, "Kode : " + kode +"Pesan: " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(UbahActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}