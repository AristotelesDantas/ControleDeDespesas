package com.example.controlededespesas;

import static com.example.controlededespesas.R.id.checkBoxCarteira;
import static com.example.controlededespesas.R.id.checkBoxPoupanca;
import static com.example.controlededespesas.R.id.radioGroupCategoria;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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

    public void salvar(){
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

    public void cancelar(){
        onBackPressed();
    }

    public void onBackPressed(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.despesa_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuItemSalvar:
                salvar();
                return true;

            case android.R.id.home:
            case R.id.menuItemCancelar:
                cancelar();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}