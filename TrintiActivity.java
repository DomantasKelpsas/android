package com.example.lab2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrintiActivity extends Activity /*implements AdapterView.OnItemClickListener*/ {
    private static final String TAG = "TrintiActivity";
    ArrayList<HashMap<String, String>> sortDataList;

    String sortType = null;
   String delID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sortType = getIntent().getStringExtra("sortType");
        delID = getIntent().getStringExtra("delID");
        //setContentView(R.layout.uzrasai_list_row);

        //Toast.makeText(DuomenysActivity.this, "ParasytaData turinys " + parasytaData.length(), Toast.LENGTH_LONG).show();


        onActivityResult();

    }

    //----------------------------------------------------------------------------------------------


    protected void onActivityResult() {


        new TrintiActivity.trintiUzrasusTask().execute(Tools.DelURL + delID, null, null);
        //new sortActivity.gautiUzrasusTask().execute(Tools.RestURL, null, null);


    }


    public class trintiUzrasusTask extends AsyncTask<String, String, List<Uzrasas>> {
        ProgressDialog actionProgressDialog = new ProgressDialog(TrintiActivity.this);

        @Override
        protected void onPreExecute() {

            actionProgressDialog.setMessage("Gaunami uzrasai");
            actionProgressDialog.show();
            actionProgressDialog.setCancelable(false);
            super.onPreExecute();

        }

        protected List<Uzrasas> doInBackground(String... str_param) {

            String sortURL = str_param[0];


            try {
                DataAPI.trintiUzrasus(sortURL);
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
            return null;

        }

        protected void onProgressUpdate(Void... progress) {

        }

        protected void onPostExecute(List<Uzrasas> result) {
            Toast.makeText(TrintiActivity.this, "OnPostEx  ", Toast.LENGTH_LONG).show();
            actionProgressDialog.cancel();


            Toast.makeText(TrintiActivity.this, "uzrasas istrintas", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(TrintiActivity.this, sortActivity.class);
            intent.putExtra("sortType", "ASC");
            startActivity(intent);

        }

    }


}