package com.serratec.domain.models;

public class PedItem {
    private int idPedItem;
    private Pedido pedido;
    private Produto produto;
    private Double vlUnitario;
    private Double vlDesconto;
    private Double quantidade;

    public int getIdPedItem() {
        return idPedItem;
    }

    public void setIdPedItem(int idPedItem) {
        this.idPedItem = idPedItem;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getVlUnitario() {
        return vlUnitario;
    }

    public void setVlUnitario(Double vlUnitario) {
        this.vlUnitario = vlUnitario;
    }

    public Double getVlDesconto() {
        return vlDesconto;
    }

    public void setVlDesconto(Double vlDesconto) {
        this.vlDesconto = vlDesconto;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
}
