package com.example.lab2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends Activity {

    private static final String TAG = "EditActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

        Intent MyIntent = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        toolbar.setTitle("Užrašo \"" + getIntent().getStringExtra("pavadinimas") + "\" redagavimas");

        final EditText Edit1 = (EditText) findViewById(R.id.Edit1);
        Edit1.setText(getIntent().getStringExtra("pavadinimas"));

        final EditText Edit2 = (EditText) findViewById(R.id.Edit2);
        Edit2.setText(getIntent().getStringExtra("tekstas"));

        final Spinner spinner = (Spinner) findViewById(R.id.Drop1);

        List<String> list = new ArrayList<String>();
        list.add("Darbas");
        list.add("Kita");
        list.add("Svarbu");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        TextView textView = (TextView) findViewById(R.id.Text1);
        textView.setText(getIntent().getStringExtra("data"));

        Button issaugoti = findViewById(R.id.Issaugoti);
        issaugoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kategorija;
                System.out.println(Edit1.getText().toString());
                System.out.println(Edit2.getText().toString());
                System.out.println(spinner.getSelectedItem().toString());
                if (spinner.getSelectedItem().toString().equals("Darbas"))
                    kategorija = "2";
                else if (spinner.getSelectedItem().toString().equals("Svarbu"))
                    kategorija = "1";
                else
                    kategorija = "3";
                String reiksmes = getIntent().getStringExtra("id") + ";" + Edit1.getText().toString() + ";" + Edit2.getText().toString() + ";" + kategorija;

                new updateTask().execute(Tools.EditURL, reiksmes);
                navigateUpTo(new Intent(getBaseContext(), MainActivity.class));
            }
        });

    }

    public class updateTask extends AsyncTask<String, String, String> {
        ProgressDialog actionProgressDialog = new ProgressDialog(EditActivity.this);

        @Override
        protected void onPreExecute() {
            actionProgressDialog.setMessage("Atnaujinimas užrašas");
            actionProgressDialog.show();
            actionProgressDialog.setCancelable(false);
            super.onPreExecute();
        }

        protected String doInBackground(String... str_param) {
            String URL = str_param[0];
            String val = str_param[1];
            String rez = "";
            try {
                rez = DataAPI.Redaguoti(URL, val);
                //uzrasai = DataAPI.gautiUzrasus(RestURL);
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
            return rez;
        }

        protected void onProgressUpdate(Void... progress) {

        }

        protected void onPostExecute(String rez) {
            actionProgressDialog.cancel();
            System.out.println(rez);
            if (rez != "\"nera\"" || rez != "\"no\"" || rez != "\"truksta\"") {
                Toast.makeText(EditActivity.this, "Užrašas sėkmingai atnaujintas", Toast.LENGTH_SHORT).show();
                //new MainActivity().salinimas();
            } else {
                Toast.makeText(EditActivity.this, "Užrašas nebuvo atnaujintass", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
