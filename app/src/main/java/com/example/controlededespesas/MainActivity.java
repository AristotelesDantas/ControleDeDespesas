package com.example.controlededespesas;

import static com.example.controlededespesas.R.id.checkBoxPoupanca;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText dp;
    Calendar calendar;

    private Spinner spinnerPagamento;
    private RadioGroup radioGroupDespesas;
    private CheckBox cbCarteira, cbContaCorrente, cbPoupanca;
    private EditText editValor;
    private NumberFormat numberFormat;

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

        radioGroupDespesas = findViewById(R.id.radioGroupDespesas);
        spinnerPagamento = findViewById(R.id.spinnerPagamento);
        cbCarteira = findViewById(R.id.checkBoxCarteira);
        cbContaCorrente = findViewById(R.id.checkBoxContaCorrente);
        cbPoupanca = findViewById(checkBoxPoupanca);
        editValor = findViewById(R.id.TextViewValor);

        popularSpinner();

          numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
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

        //--------------------Mostrar as despesas------------------

          String mensagem1 = "";
        switch (radioGroupDespesas.getCheckedRadioButtonId()){
            case R.id.radioButtonFixas:
                mensagem1 = getString(R.string.despesas_fixas) +
                           getString(R.string.foi_selecionada);
                break;
            case R.id.radioButtonAlimentacao:
                mensagem1 = getString(R.string.despesas_com_alimentacao) +
                           getString(R.string.foi_selecionada);
                break;
            case R.id.radioButtonTransportes:
                mensagem1 = getString(R.string.despesas_com_transporte) +
                           getString(R.string.foi_selecionada);
                break;
            case R.id.radioButtonLazer:
                mensagem1 = getString(R.string.despesas_com_lazer) +
                           getString(R.string.foi_selecionada);
                break;

            default:
                mensagem1 = getString(R.string.nenhuma_despesa_selecionada);
        }
        //Toast.makeText(this,                mensagem1,                Toast.LENGTH_LONG).show();


        //-------------------Mostrar as contas-----------------

        String mensagem2 = "";

        if (cbCarteira.isChecked()){
            mensagem2 = getString(R.string.carteira) + "\n";
        }
        if (cbContaCorrente.isChecked()){
            mensagem2 = getString(R.string.conta_corrente);
        }
        if (cbPoupanca.isChecked()){
            mensagem2 = getString(R.string.poupanca);
        }
        if (mensagem2.equals("")){
            mensagem2 = getString(R.string.nenhuma_opcao_foi_selecionada);
        }else{
            mensagem2 = getString(R.string.foram_selecionados) + "\n" + mensagem2;
        }
        //Toast.makeText(this, mensagem2, Toast.LENGTH_LONG).show();



        //------------------Mostrar forma de pagamento--------------

        String ling = (String) spinnerPagamento.getSelectedItem();

        String mensagem;

        if (ling != null){
            mensagem = ling + " " + getString(R.string.foi_selecionado);
        }else{
            mensagem = getString(R.string.nenhum_selecionado);
        }
       // Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();

        //------------------Mostrar Valor pago--------------

        int result = Integer.parseInt(editValor.getText().toString());
        String mensagem3;

        if (result != 0){
            mensagem3 = result + " " + "Reais";
        }else{
            mensagem3 = getString(R.string.valor_nulo);
        }

        Toast.makeText(this, mensagem1 + "\n" + mensagem2 + "\n" + mensagem + "\n" + mensagem3, Toast.LENGTH_LONG).show();

    }

    public void limparTudo (View view){
        //------------Limpar despesas---------
        radioGroupDespesas.clearCheck();

        //------------Limpar contas-----------
        cbCarteira.setChecked(false);
        cbContaCorrente.setChecked(false);
        cbPoupanca.setChecked(false);


        //------------Limpar Valor-----------
        editValor.setText("");

    }


}