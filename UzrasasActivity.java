
package com.example.lab2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class UzrasasActivity extends Activity implements AdapterView.OnItemClickListener {

    ArrayList<HashMap<String, String>> UzrasaiDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uzrasai_list_row_info);

        //Intent MyIntent=getIntent();
        HashMap<String, String> UzrasasDataMap = new HashMap<>();
        //ArrayList<HashMap<String, String>> UzrasaiDataList = new ArrayList<>();
        UzrasaiDataList = new ArrayList<>();
        UzrasasDataMap.put("id", getIntent().getStringExtra("id"));
        UzrasasDataMap.put("pavadinimas", getIntent().getStringExtra("pavadinimas"));
        UzrasasDataMap.put("kategorija", getIntent().getStringExtra("kategorija"));
        UzrasasDataMap.put("tekstas", getIntent().getStringExtra("tekstas"));
        UzrasasDataMap.put("data", getIntent().getStringExtra("data"));
        UzrasaiDataList.add(UzrasasDataMap);

//        TextView kat = findViewById(R.id.kategorijaTextView);
//        String spalva = getIntent().getStringExtra("spalva");
//        kat.setTextColor(Color.parseColor("#"+spalva));

        ListView mlv = (ListView) findViewById(R.id.uzrasaiListView);
        SimpleAdapter SimpleMiestaiAdapter = new SimpleAdapter(this, UzrasaiDataList, R.layout.uzrasai_list_row_info,
                new String[]{"pavadinimas", "kategorija", "tekstas", "data"},
                new int[]{R.id.pavadinimasTextView, R.id.kategorijaTextView, R.id.tekstasTextView, R.id.dataTextView});

        mlv.setAdapter(SimpleMiestaiAdapter);
        mlv.setOnItemClickListener(this);
        System.out.println("Response Code **********************************1 ");

        TextView pav = findViewById(R.id.pavadinimasTextView);


////          D E L E T E
//
//        Button BtnDelete = findViewById(R.id.btnDelete);
//        BtnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                String delID = getIntent().getStringExtra("id");
//                Intent intent = new Intent(UzrasasActivity.this, TrintiActivity.class);
//
//                intent.putExtra("delID", delID);
//                startActivity(intent);
//
//
//            }
//        });


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

        String UzrasoID = UzrasaiDataList.get(pos).get("id");
        String Pavadinimas = UzrasaiDataList.get(pos).get("pavadinimas");
        String Data = UzrasaiDataList.get(pos).get("data");
        String Kategorija = UzrasaiDataList.get(pos).get("kategorija");
        String Tekstas = UzrasaiDataList.get(pos).get("tekstas");


        Intent myIntent = new Intent(UzrasasActivity.this, EditActivity.class);

        myIntent.putExtra("id", UzrasoID);
        myIntent.putExtra("pavadinimas", Pavadinimas);
        myIntent.putExtra("data", Data);
        myIntent.putExtra("kategorija", Kategorija);
        myIntent.putExtra("tekstas", Tekstas);

        startActivity(myIntent);
    }
}
