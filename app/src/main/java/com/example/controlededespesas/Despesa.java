package com.example.controlededespesas;

import static android.provider.Settings.System.getString;

public class Despesa {


    public static final int FIXA        = 1;
    public static final int ALIMENTACAO = 2;
    public static final int TRANSPORTE  = 3;
    public static final int LAZER       = 4;

    private int categoria;
    private String descricao;
    private boolean carteira;
    private boolean conta_corrente;
    private boolean poupanca;
    private String pagamento;
    private String valor;

   public Despesa (int categoria){
        setCategoria(categoria);
    }

    public Despesa(int categoria, String descricao, boolean carteira, boolean conta_corrente, boolean poupanca, String pagamento, String valor) {
        setCategoria(categoria);
        setDescricao(descricao);
        setCarteira(carteira);
        setConta_corrente(conta_corrente);
        setPoupanca(poupanca);
        setPagamento(pagamento);
        setValor(valor);
    }
    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isCarteira() {
        return carteira;
    }

    public void setCarteira(boolean carteira) {
        this.carteira = carteira;
    }

    public boolean isConta_corrente() {
        return conta_corrente;
    }

    public void setConta_corrente(boolean conta_corrente) {
        this.conta_corrente = conta_corrente;
    }

    public boolean isPoupanca() {
        return poupanca;
    }

    public void setPoupanca(boolean poupanca) {
        this.poupanca = poupanca;
    }


    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString(){
        return  getCategoria()      +   "\n"    +
                getDescricao()      +   "\n"    +
                isCarteira()        +   "\n"    +
                isConta_corrente()  +   "\n"    +
                isPoupanca()        +   "\n"    +
                getPagamento()      +   "\n"    +
                getValor();
    }
}
