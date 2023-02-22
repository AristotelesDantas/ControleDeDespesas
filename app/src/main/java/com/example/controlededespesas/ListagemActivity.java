package com.example.controlededespesas;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListagemActivity extends AppCompatActivity {

    private ListView listViewDespesas;
    private ArrayList<Despesa> listDespesas;
    private ArrayAdapter<Despesa> listaAdapter;

    private int posicaoSelecionada = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        listViewDespesas = findViewById(R.id.listViewDespesas);

        listViewDespesas.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position,
                                            long id) {
                        posicaoSelecionada = position;
                        alterarDespesa();
                    }
                });

        listViewDespesas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,
                                           View view, int position, long id) {
                posicaoSelecionada = position;
                alterarDespesa();
                return true;
            }
        });

        popularLista();
    }

    private void popularLista() {

        listDespesas = new ArrayList<>();

        listaAdapter = new ArrayAdapter<>(this,
                                            android.R.layout.simple_list_item_1,
                                            listDespesas);

        listViewDespesas.setAdapter(listaAdapter);




        /*String[] categoria = getResources().getStringArray(R.array.categoria);
        String[] descricao = getResources().getStringArray(R.array.descricao);
        String[] conta = getResources().getStringArray(R.array.conta);
        int[] pagamento = getResources().getIntArray(R.array.pagamento);
        String[] valor = getResources().getStringArray(R.array.valor);

        despesas = new ArrayList<>();

        Despesa despesa;
        float val;

        Tipo[] tipos = Tipo.values();

        for (int cont = 0; cont < categoria.length; cont++) {

            despesa = new Despesa(categoria[cont]);
            despesa.setDescricao(descricao[cont]);
            despesa.setConta(conta[cont]);
            despesa.setPagamento(tipos[pagamento[cont]]);

            val = Float.parseFloat(valor[cont]);

            despesa.setValor(val);
            despesas.add(despesa);
        }

        DespesaAdapter despesaAdapter = new DespesaAdapter(this, despesas);

        listViewDespesas.setAdapter(despesaAdapter);
    }*/
    }

    private void alterarDespesa(){
        Despesa despesa = listDespesas.get(posicaoSelecionada);

        MainActivity.alterarDespesa(this, despesa);
    }

    public void adicionarDespesa(View view){
        MainActivity.novaDespesa(this);
    }

    public void abrirAutoria(View view){
        Autoria.sobre(this);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            Bundle bundle = data.getExtras();

            int categoria = bundle.getInt(MainActivity.CATEGORIA);
            String descricao = bundle.getString(MainActivity.DESCRICAO);
            boolean carteira = bundle.getBoolean(MainActivity.CARTEIRA);
            boolean conta_corrente = bundle.getBoolean(MainActivity.CONTACORRENTE);
            boolean poupanca = bundle.getBoolean(MainActivity.POUPANCA);
            String pagamento = bundle.getString(MainActivity.PAGAMENTO);
            String valor = bundle.getString(MainActivity.VALOR);

            if (requestCode == MainActivity.ALTERAR) {
                Despesa despesa = listDespesas.get(posicaoSelecionada);

                despesa.setCategoria(categoria);
                despesa.setDescricao(descricao);
                despesa.setCarteira(carteira);
                despesa.setConta_corrente(conta_corrente);
                despesa.setPoupanca(poupanca);
                despesa.setPagamento(pagamento);
                despesa.setValor(valor);

                posicaoSelecionada = -1;
            } else {
                Despesa despesa = new Despesa(categoria, descricao, carteira, conta_corrente, poupanca, pagamento, valor);

                listDespesas.add(despesa);
            }
            listaAdapter.notifyDataSetChanged();
        }
    }
}