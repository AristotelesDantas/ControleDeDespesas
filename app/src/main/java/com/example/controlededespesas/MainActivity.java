package com.example.controlededespesas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText dp;
    Calendar calendar;

    private Spinner spinnerPagamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dp = findViewById(R.id.editTextDate);
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int i2) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                Locale locale = new Locale("PT", "BR");
                updateCalendar();

            }
            private void updateCalendar(){
                String Format = "LLLL";
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);

                dp.setText(sdf.format(calendar.getTime()));
            }
        };
        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE)).show();
            }
        });

        spinnerPagamento = findViewById(R.id.spinnerPagamento);

        popularSpinner();
    }

    public void popularSpinner(){
        ArrayList<String> lista = new ArrayList<>();

        lista.add(getString(R.string.dinheiro));
        lista.add(getString(R.string.cartao));
        lista.add(getString(R.string.pix));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);

        spinnerPagamento.setAdapter(adapter);
    }

    public void mostrarSelecionados (View view){
        String ling = (String) spinnerPagamento.getSelectedItem();

        String mensagem;

        if (ling != null){
            mensagem = ling + " " + getString(R.string.foi_selecionado);
        }else{
            mensagem = getString(R.string.nenhum_selecionado);
        }
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();

    }


}