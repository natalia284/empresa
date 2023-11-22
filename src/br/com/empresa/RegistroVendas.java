package br.com.empresa;

class RegistroVendas {
    private Funcionario vendedor;
    private String mesAno;
    private double valorVendido;

    public RegistroVendas(Funcionario vendedor, String mesAno, double valorVendido) {
        this.vendedor = vendedor;
        this.mesAno = mesAno;
        this.valorVendido = valorVendido;
    }

    public Funcionario getVendedor() {
        return vendedor;
    }

    public String getMesAno() {
        return mesAno;
    }

    public double getValorVendido() {
        return valorVendido;
    }
}

