package com.example.controlededespesas;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListagemActivity extends AppCompatActivity {

    private ListView listViewDespesas;
    private ArrayList<Despesa> listDespesas;
    private ArrayAdapter listaAdapter;

    private androidx.appcompat.view.ActionMode actionMode;
    private int posicaoSelecionada = -1;
    private View viewSelecionada;

       private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.editar_excluir, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()){
                case R.id.menuItemEditar:
                    alterarDespesa();
                    mode.finish();
                    return true;

                case R.id.menuItemExcluir:
                    excluirDespesa();
                    mode.finish();
                    return true;

                default: return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            if(viewSelecionada != null){
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode = null;
            viewSelecionada = null;

            listViewDespesas.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        listViewDespesas = findViewById(R.id.listViewDespesas);

        listViewDespesas.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {

                        posicaoSelecionada = position;
                        alterarDespesa();
                    }
        });

        listViewDespesas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listViewDespesas.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view, int position,
                                                   long id) {
                        if (actionMode != null) {
                            return false;
                        }

                        posicaoSelecionada = position;

                        view.setBackgroundColor(Color.LTGRAY);

                        viewSelecionada = view;

                        listViewDespesas.setEnabled(false);

                        actionMode = startSupportActionMode(mActionModeCallback);

                        return true;
                    }
                });
        popularLista();
        }

    private void popularLista() {

        listDespesas = new ArrayList<>();

        listaAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, listDespesas);

        listViewDespesas.setAdapter(listaAdapter);

        }

        private void excluirDespesa() {
        listDespesas.remove(posicaoSelecionada);
        listaAdapter.notifyDataSetChanged();
        }

        private void alterarDespesa(){

        Despesa despesa = listDespesas.get(posicaoSelecionada);
        MainActivity.alterarDespesa(this, despesa);
        }

    public void adicionarDespesa(){
        MainActivity.novaDespesa(this);
    }

    public void abrirAutoria(){
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuItemAdicionar:
                adicionarDespesa();
               return true;

            case R.id.menuItemSobre:
                abrirAutoria();
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }
}