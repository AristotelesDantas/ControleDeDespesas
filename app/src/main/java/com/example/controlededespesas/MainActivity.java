package com.example.controlededespesas;

import static com.example.controlededespesas.R.id.checkBoxCarteira;
import static com.example.controlededespesas.R.id.checkBoxPoupanca;
import static com.example.controlededespesas.R.id.radioGroupCategoria;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.BreakIterator;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String MODO = "MODO";
    public static final String CATEGORIA = "CATEGORIA";
    public static final String DESCRICAO = "DESCRICAO";
    public static final String CARTEIRA = "CARTEIRA";
    public static final String CONTACORRENTE = "CONTACORRENTE";
    public static final String POUPANCA = "POUPANCA";
    public static final String PAGAMENTO = "PAGAMENTO";
    public static final String VALOR = "VALOR";

    public static final int NOVO = 1;
    public static final int ALTERAR = 2;

    private Spinner spinnerPagamento;
    private RadioGroup radioGroupCategoria;
    private CheckBox cbCarteira, cbContaCorrente, cbPoupanca;
    private EditText editValor, editDescricao;



    private int modo;

    public static void novaDespesa(AppCompatActivity activity) {
        Intent intent = new Intent(activity, MainActivity.class);

        intent.putExtra(MODO, NOVO);

        activity.startActivityForResult(intent, NOVO);
    }

    public static void alterarDespesa(AppCompatActivity activity, Despesa despesa) {
        Intent intent = new Intent(activity, MainActivity.class);

        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(CATEGORIA, despesa.getCategoria());
        intent.putExtra(DESCRICAO, despesa.getDescricao());
        intent.putExtra(CARTEIRA, despesa.isCarteira());
        intent.putExtra(CONTACORRENTE, despesa.isConta_corrente());
        intent.putExtra(POUPANCA, despesa.isPoupanca());
        intent.putExtra(PAGAMENTO, despesa.getPagamento());
        intent.putExtra(VALOR, despesa.getValor());

        activity.startActivityForResult(intent, ALTERAR);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroupCategoria = findViewById(R.id.radioGroupCategoria);
        editDescricao       = findViewById(R.id.textViewDescri);
        spinnerPagamento    = findViewById(R.id.spinnerPagamento);
        cbCarteira          = findViewById(checkBoxCarteira);
        cbContaCorrente     = findViewById(R.id.checkBoxContaCorrente);
        cbPoupanca          = findViewById(checkBoxPoupanca);
        editValor           = findViewById(R.id.TextViewValor);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            modo = bundle.getInt(MODO, NOVO);

            if (modo == NOVO) {
                setTitle(getString(R.string.app_name));
            } else {
                int categoria = bundle.getInt(CATEGORIA);

                RadioButton button;
                switch (categoria) {
                    case Despesa.FIXA:
                        button = findViewById(R.id.radioButtonFixas);
                        button.setChecked(true);

                        break;

                    case Despesa.ALIMENTACAO:
                        button = findViewById(R.id.radioButtonAlimentacao);
                        button.setChecked(true);
                        break;

                    case Despesa.TRANSPORTE:
                        button = findViewById(R.id.radioButtonTransportes);
                        button.setChecked(true);
                        break;

                    case Despesa.LAZER:
                        button = findViewById(R.id.radioButtonLazer);
                        button.setChecked(true);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + categoria);
                }

                String descricao = bundle.getString(DESCRICAO);
                editDescricao.setText(descricao);

                boolean carteira = bundle.getBoolean(CARTEIRA);
                cbCarteira.setChecked(carteira);

                boolean conta_corrente = bundle.getBoolean(CONTACORRENTE);
                cbContaCorrente.setChecked(conta_corrente);

                boolean poupanca = bundle.getBoolean(POUPANCA);
                cbPoupanca.setChecked(poupanca);

                String pagamento = bundle.getString(PAGAMENTO);

                for (int pos = 0; 0 < spinnerPagamento.getAdapter().getCount(); pos++) {
                    String valor = (String) spinnerPagamento.getItemAtPosition(pos);

                    if (valor.equals(pagamento)) {
                        spinnerPagamento.setSelection(pos);
                        break;
                    }
                }

                String valor = bundle.getString(VALOR);

               editValor.setText(valor);

                setTitle(getString(R.string.alterar_despesa));
            }
        }
    }

    public void salvar(View view){
        int categoria;

        switch (radioGroupCategoria.getCheckedRadioButtonId()){
            case R.id.radioButtonFixas:
                categoria = Despesa.FIXA;
                break;

            case R.id.radioButtonAlimentacao:
                categoria = Despesa.ALIMENTACAO;
                break;

            case R.id.radioButtonTransportes:
                categoria = Despesa.TRANSPORTE;
                break;

            case R.id.radioButtonLazer:
                categoria = Despesa.LAZER;
                break;

            default:
                Toast.makeText(this,
                               getString(R.string.escolha_uma_opcao),
                               Toast.LENGTH_LONG). show();
                return;
        }

        String descricao = editDescricao.getText().toString();

        if (descricao == null || descricao.trim().isEmpty()){
            Toast.makeText(this,
                           getString(R.string.campo_nao_pode_ser_vazio),
                           Toast.LENGTH_LONG).show();

            return;
        }

        boolean carteira = cbCarteira.isChecked();
        boolean conta_corrente = cbContaCorrente.isChecked();
        boolean poupanca = cbPoupanca.isChecked();

        String pagamento = (String) spinnerPagamento.getSelectedItem();

        if (pagamento == null){
            Toast.makeText(this,
                           getString(R.string.forma_de_pagamento),
                           Toast.LENGTH_LONG).show();
            return;
        }

        String valor = editValor.getText().toString();

        if (valor == null || valor.trim().isEmpty()){
                       Toast.makeText(this,
                            getString(R.string.digite_um_valor),
                            Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent();

        intent.putExtra(CATEGORIA, categoria);
        intent.putExtra(DESCRICAO, descricao);
        intent.putExtra(CARTEIRA, carteira);
        intent.putExtra(CONTACORRENTE, conta_corrente);
        intent.putExtra(POUPANCA, poupanca);
        intent.putExtra(PAGAMENTO, pagamento);
        intent.putExtra(VALOR, valor);

        setResult(Activity.RESULT_OK, intent);

        finish();
    }

    public void cancelar(View view){
        onBackPressed();
    }

    public void onBackPressed(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }







     /*   dp = findViewById(R.id.editTextDate);
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

*/

}