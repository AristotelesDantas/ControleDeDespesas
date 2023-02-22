/*package com.example.controlededespesas;

import static com.example.controlededespesas.Tipo.Cartao;
import static com.example.controlededespesas.Tipo.Dinheiro;
import static com.example.controlededespesas.Tipo.Pix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DespesaAdapter extends BaseAdapter {

    private Context context;
    private List<Despesa> despesas;
    private NumberFormat numberFormat;

    private static class DespesaHolder {
        public TextView textViewValorCategoria;
        public TextView textViewValorDescricao;
        public TextView textViewValorConta;
        public TextView textViewValorPagamento;
        public TextView textViewValorValor;
    }

    public DespesaAdapter(Context context, List<Despesa> despesas){
        this.context = context;
        this.despesas = despesas;

        numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    }

    @Override
    public int getCount(){
        return despesas.size();
    }

    @Override
    public Object getItem(int i){
        return despesas.get(i);
    }

    @Override
    public long getItemId (int i){
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        DespesaHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.linha_lista_despesa, viewGroup, false);

            holder = new DespesaHolder();

            holder.textViewValorCategoria = view.findViewById(R.id.textViewValorCategoria);
            holder.textViewValorDescricao = view.findViewById(R.id.textViewValorDescricao);
            holder.textViewValorConta = view.findViewById(R.id.textViewValorConta);
            holder.textViewValorPagamento = view.findViewById(R.id.textViewValorPagamento);
            holder.textViewValorValor = view.findViewById(R.id.textViewValorValor);

            view.setTag(holder);
        } else {
            holder = (DespesaHolder) view.getTag();
        }
        holder.textViewValorCategoria.setText(despesas.get(i).getCategoria());
        holder.textViewValorDescricao.setText(despesas.get(i).getDescricao());
        holder.textViewValorConta.setText(despesas.get(i).getConta());
        switch (despesas.get(i).getPagamento()){
            case Dinheiro:
                holder.textViewValorPagamento.setText(R.string.dinheiro);
                break;
            case Cartao:
                holder.textViewValorPagamento.setText(R.string.cartao);
                break;
            case Pix:
                holder.textViewValorPagamento.setText(R.string.pix);
                break;
        }

        String valorFormatado = numberFormat.format(despesas.get(i).getValor());

        holder.textViewValorValor.setText(valorFormatado);

        return view;
    }
}
*/