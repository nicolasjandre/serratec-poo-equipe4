package com.serratec.domain.models;

public class Produto {
    private int idProduto;
    private String descricao;
    private int idCategoria;
    private Double estoque;
    private Double vlCusto;
    private Double vlVenda;
    private double qtdVendida;

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Double getVlCusto() {
        return vlCusto;
    }

    public void setVlCusto(Double vlCusto) {
        this.vlCusto = vlCusto;
    }

    public Double getVlVenda() {
        return vlVenda;
    }

    public void setVlVenda(Double vlVenda) {
        this.vlVenda = vlVenda;
    }

    public double getQtdVendida() {
        return qtdVendida;
    }

    public void setQtdVendida(double qtdVendida) {
        this.qtdVendida = qtdVendida;
    }

    public Double getEstoque() {
        return estoque;
    }

    public void setEstoque(Double estoque) {
        this.estoque = estoque;
    }
}
