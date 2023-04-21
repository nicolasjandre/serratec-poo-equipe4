package com.serratec.domain.models;

public class Produto {
    private int idProduto;
    private String descricao;
    private int idCategoria;
    private Double estoque;
    private Double vlCusto;
    private Double vlVenda;

    public void imprimirDadosProduto() {
        System.out.printf("%-13s %-35s %-20s %-30s %-70s %s\n",
                getIdProduto(), getDescricao(), getIdCategoria(), getEstoque(), getVlCusto(), getVlVenda());
    }

    public void imprimirDadosProdutoComQtdVendida(Double qtdVendida) {
        System.out.printf("%-13s %-35s %-20s %-30s %-30s %-30s %s\n",
                getIdProduto(), getDescricao(), getIdCategoria(), getEstoque(),
                getVlCusto(), getVlVenda(), qtdVendida);
    }

    public void imprimirDadosProdutoComQtdVendidaEDesconto(Double qtdVendida, Double desconto, Double vlVenda) {
        System.out.printf("%-13s %-35s %-20s %-30s %-30s %-30s %-40s %-30s\n",
                getIdProduto(), getDescricao(), getIdCategoria(), getEstoque(),
                getVlCusto(), vlVenda, qtdVendida, "Desconto: " + desconto + "%");
    }

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

    public Double getEstoque() {
        return estoque;
    }

    public void setEstoque(Double estoque) {
        this.estoque = estoque;
    }
}
