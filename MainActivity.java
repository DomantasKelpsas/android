package com.example.lab2;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

//public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "MainActivity";
    ArrayList<HashMap<String, String>> UzrasaiDataList;

    int flag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_layout);
//        setContentView(R.layout.uzrasai_list_row);

//        gautiUzrasus();

        if (Tools.IsOnline(getApplicationContext())) {
            System.out.println("prisijungta");
        }

        Button ASCUzrasai = findViewById(R.id.sortASC);
        ASCUzrasai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, sortActivity.class);

                intent.putExtra("sortType", "ASC");
                startActivity(intent);

//                gautiUzrasusASC();

            }
        });


        Button DESCUzrasai = findViewById(R.id.sortDESC);
        DESCUzrasai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, sortActivity.class);

                intent.putExtra("sortType", "DESC");
                startActivity(intent);

//                gautiUzrasusDESC();
            }
        });

        Button InsertUzrasai = findViewById(R.id.btnInsert);
        InsertUzrasai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, InsertActivity.class);


                startActivity(intent);

//                gautiUzrasusDESC();
            }
        });

//        Button BtnDelete = findViewById(R.id.delete);
//
//        BtnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                Intent intent = new Intent(MainActivity.this, sortActivity.class);
//                String delID = null;
//                EditText delValue = findViewById(R.id.delID);
//                delID = delValue.getText().toString();
//
//                if (delID.compareTo("") != 0) {
//                    Intent intent = new Intent(MainActivity.this, TrintiActivity.class);
//
//                    intent.putExtra("delID", delID);
//                    startActivity(intent);
//                } else
//                {
//                    Toast.makeText(MainActivity.this, "Neivestas ID: ", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });

    }

    private void gautiUzrasus() {

        new gautiUzrasusTask().execute(Tools.RestURL, null, null);

    }

    private void gautiUzrasusASC() {

        new gautiUzrasusTask().execute(Tools.AscURL, null, null);

    }

    private void gautiUzrasusDESC() {

        new gautiUzrasusTask().execute(Tools.DescURL, null, null);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

//
//        String UzrasoID = UzrasaiDataList.get(pos).get("id");
//        String Pavadinimas = UzrasaiDataList.get(pos).get("pavadinimas");
//
//
//        Intent myIntent = new Intent(this, UzrasasActivity.class);
//        myIntent.putExtra("id", UzrasoID);
//        myIntent.putExtra("pavadinimas", Pavadinimas);
//
//        startActivity(myIntent);

    }


    public class gautiUzrasusTask extends AsyncTask<String, Void, List<Uzrasas>> {
        //private static final String TAG = "gautiUzrasusTask";
        ProgressDialog actionProgressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
//            actionProgressDialog.setMessage("Gaunami užrašai");
//            actionProgressDialog.show();
//            actionProgressDialog.setCancelable(false);
            super.onPreExecute();
        }

        protected List<Uzrasas> doInBackground(String... str_param) {
            String RestURL = str_param[0];
            List<Uzrasas> uzrasai = new ArrayList<>();
            try {
                uzrasai = DataAPI.gautiUzrasus(RestURL);
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }

            System.out.println("-----AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA-------");
            System.out.println(uzrasai);

            return uzrasai;
        }


        protected void onProgressUpdate(Void... progress) {
        }

        protected void onPostExecute(List<Uzrasas> result) {
            actionProgressDialog.cancel();
            rodytiUzrasus(result, flag);
        }

    }

    private void rodytiUzrasus(List<Uzrasas> uzrasai, int flag) {


        UzrasaiDataList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < uzrasai.size(); i++) {
            Uzrasas u = uzrasai.get(i);
            HashMap<String, String> UzrasasDataMap = new HashMap<>();
            UzrasasDataMap.put("id", String.valueOf(u.ID));
            UzrasasDataMap.put("pavadinimas", u.Pavadinimas);
            UzrasasDataMap.put("kategorija", u.Kategorija);
            UzrasasDataMap.put("data", u.DataIrLaikas);
            UzrasasDataMap.put("spalva", u.Spalva);


            UzrasaiDataList.add(UzrasasDataMap);
        }

        ListView mlv = (ListView) findViewById(R.id.uzrasaiListView);
//        LinearLayout box = findViewById(R.id.btnBox);
//        mlv.addFooterView(box);
        SimpleAdapter SimpleMiestaiAdapter = new SimpleAdapter(this, UzrasaiDataList, R.layout.uzrasai_list_row,
                new String[]{"pavadinimas", "kategorija"},
                new int[]{R.id.pavadinimasTextView, R.id.kategorijaTextView});

        mlv.setAdapter(SimpleMiestaiAdapter);
        mlv.setOnItemClickListener(this);
    }

}







