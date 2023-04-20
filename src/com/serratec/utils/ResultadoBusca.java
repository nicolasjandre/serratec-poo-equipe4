package com.serratec.utils;

import com.serratec.domain.models.Pedido;
import com.serratec.domain.models.Produto;

import java.util.List;

public class ResultadoBusca {
    private List<Produto> produtos;
    private List<Double> qtdVendida;
    private Pedido pedido;
    public ResultadoBusca(List<Double> qtdVendida, Pedido pedido) {
        this.qtdVendida = qtdVendida;
        this.pedido = pedido;
    }

    public ResultadoBusca(List<Produto> produtos, List<Double> valores) {
        this.produtos = produtos;
        this.qtdVendida = valores;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Double> getQtdVendida() {
        return qtdVendida;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setQtdVendida(List<Double> qtdVendida) {
        this.qtdVendida = qtdVendida;
    }
}

