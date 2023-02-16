package com.example.controlededespesas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListagemActivity extends AppCompatActivity {

    private ListView listViewDespesas;
    private ArrayList<Despesa> despesas;

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
                        Despesa despesa = (Despesa) listViewDespesas.getItemAtPosition(position);

                        Toast.makeText(getApplicationContext(), getString(R.string.foi_selecionado) +
                                despesa.getCategoria() + despesa.getDescricao() +
                                despesa.getConta() + despesa.getPagamento() +
                                despesa.getValor(), Toast.LENGTH_LONG).show();

                    }
                });

        popularList();
    }

    private void popularList() {

        String[] categoria = getResources().getStringArray(R.array.categoria);
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
    }
}