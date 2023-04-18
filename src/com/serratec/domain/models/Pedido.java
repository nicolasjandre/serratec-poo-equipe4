package com.serratec.domain.models;

import java.sql.Date;
import java.util.List;

public class Pedido {
    private int idPedido;

    private Date dtEmissao;
    private Date dtEntrega;
    private Double valorTotal;
    private String obervacao;
    private Cliente cliente;
    private List<PedItem> produto;
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Date getDtEmissao() {
        return dtEmissao;
    }

    public void setDtEmissao(Date dtEmissao) {
        this.dtEmissao = dtEmissao;
    }

    public Date getDtEntrega() {
        return dtEntrega;
    }

    public void setDtEntrega(Date dtEntrega) {
        this.dtEntrega = dtEntrega;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getObervacao() {
        return obervacao;
    }

    public void setObervacao(String obervacao) {
        this.obervacao = obervacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<PedItem> getProduto() {
        return produto;
    }

    public void setProduto(List<PedItem> produto) {
        this.produto = produto;
    }
}
