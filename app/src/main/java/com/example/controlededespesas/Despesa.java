package com.example.controlededespesas;

public class Despesa {
    private String categoria;
    private String descricao;
    private String conta;
    private Tipo pagamento;
    private float valor;

    public Despesa (String categoria/*, String descricao, String conta, Tipo pagamento, float valor*/){
        setCategoria(categoria);
//       setDescricao(descricao);
//       setConta(conta);
//        setPagamento(pagamento);
//        setValor(valor);
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public Tipo getPagamento() {
        return pagamento;
    }

    public void setPagamento(Tipo pagamento) {
        this.pagamento = pagamento;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

}
