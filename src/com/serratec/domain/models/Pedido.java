package com.serratec.domain.models;

import java.time.LocalDate;
import java.util.List;

public class Pedido {
    private int idPedido;
    private LocalDate dtEmissao;
    private LocalDate dtEntrega;
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

    public LocalDate getDtEmissao() {
        return dtEmissao;
    }

    public void setDtEmissao(LocalDate dtEmissao) {
        this.dtEmissao = dtEmissao;
    }

    public LocalDate getDtEntrega() {
        return dtEntrega;
    }

    public void setDtEntrega(LocalDate dtEntrega) {
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
