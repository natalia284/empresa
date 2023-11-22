package br.com.empresa;

class Funcionario {
    private String nome;
    private Cargo cargo;
    private String dataContratacao;

    public Funcionario(String nome, Cargo cargo, String dataContratacao) {
        this.nome = nome;
        this.cargo = cargo;
        this.dataContratacao = dataContratacao;
    }

    public String getNome() {
        return nome;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public String getDataContratacao() {
        return dataContratacao;
    }
}

// Enum para representar os diferentes cargos
enum Cargo {
    SECRETARIO,
    VENDEDOR,
    GERENTE
}
