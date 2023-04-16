package com.serratec.domain.models;

import com.serratec.utils.Util;

public class Cliente extends Pessoa {
    private int idCliente;
    private String cpf;
    private String endereco;
    private String telefone;
    public void imprimirDadosCliente() {
        System.out.printf("%-13s %-35s %-20s %-30s %-70s %s\n",
                getIdCliente(), getNome(), Util.formatCPF(getCpf()),
                Util.formatDate(getDtNascimento()), getEndereco(), getTelefone());
    }
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
