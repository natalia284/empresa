package br.com.empresa;

public class main {
    public static void main(String[] args) {
        // Criando a empresa
        Empresa empresa = new Empresa();

        // Adicionando funcionários à empresa
        empresa.adicionarFuncionario(new Funcionario("Jorge Carvalho", Cargo.SECRETARIO, "01/2018"));
        empresa.adicionarFuncionario(new Funcionario("Maria Souza", Cargo.SECRETARIO, "12/2015"));
        empresa.adicionarFuncionario(new Funcionario("Ana Silva", Cargo.VENDEDOR, "12/2021"));
        empresa.adicionarFuncionario(new Funcionario("João Mendes", Cargo.VENDEDOR, "12/2021"));
        empresa.adicionarFuncionario(new Funcionario("Juliana Alves", Cargo.GERENTE, "07/2017"));
        empresa.adicionarFuncionario(new Funcionario("Bento Albino", Cargo.GERENTE, "03/2014"));

        // Adicionando registros de vendas à empresa
        empresa.adicionarRegistroVendas(new RegistroVendas(
                empresa.funcionarios.get(2), "12/2021", 5200.00));
        empresa.adicionarRegistroVendas(new RegistroVendas(
                empresa.funcionarios.get(3), "12/2021", 3400.00));

        empresa.adicionarRegistroVendas(new RegistroVendas(
                empresa.funcionarios.get(2), "01/2022", 4000.00));
        empresa.adicionarRegistroVendas(new RegistroVendas(
                empresa.funcionarios.get(3), "01/2022", 7700.00));

        empresa.adicionarRegistroVendas(new RegistroVendas(
                empresa.funcionarios.get(2), "02/2022", 4200.00));
        empresa.adicionarRegistroVendas(new RegistroVendas(
                empresa.funcionarios.get(3), "02/2022", 5000.00));

        empresa.adicionarRegistroVendas(new RegistroVendas(
                empresa.funcionarios.get(2), "03/2022", 5850.00));
        empresa.adicionarRegistroVendas(new RegistroVendas(
                empresa.funcionarios.get(3), "03/2022", 5900.00));

        empresa.adicionarRegistroVendas(new RegistroVendas(
                empresa.funcionarios.get(2), "04/2022", 7000.00));
        empresa.adicionarRegistroVendas(new RegistroVendas(
                empresa.funcionarios.get(3), "04/2022", 6500.00));


        // USO DOS MÉTODOS

        String mesAnoDesejado = "01/2022";

        double valorTotalPago = empresa.calcularValorTotalPago(empresa.funcionarios, mesAnoDesejado);
        System.out.println("O valor total pago aos funcionários em " + mesAnoDesejado + " foi: R$" + valorTotalPago);

        double valorTotalSalarios = empresa.calcularTotalSalarios(empresa.funcionarios, mesAnoDesejado);
        System.out.println("O valor total pago somente de salarios aos funcionários em " + mesAnoDesejado +
                " foi: R$" + valorTotalSalarios);

        double valorTotalBeneficios = empresa.calcularTotalBeneficios(empresa.funcionarios, mesAnoDesejado);
        System.out.println("O valor total pago somente os beneficios pagos aos funcionários em " + mesAnoDesejado +
                " foi: R$" + valorTotalBeneficios);

        String funcionarioQueMaisRecebeu = empresa.funcionarioComMaiorValorRecebido(empresa.funcionarios,
                empresa.registrosVendas, mesAnoDesejado);
        System.out.println("O funcionário que mais recebeu no mês " + mesAnoDesejado +
                " foi: " + funcionarioQueMaisRecebeu);

        String funcionarioComMaiorValorEmBeneficios = empresa.funcionarioComMaiorBeneficio(empresa.funcionarios,
                mesAnoDesejado);
        System.out.println("O nome do funcionário que recebeu o valor mais alto em benefícios no mês " +
                mesAnoDesejado + " foi: " + funcionarioComMaiorValorEmBeneficios);

        String vendedorQueMaisVendeuNoMes = empresa.vendedorComMaisVendas(empresa.funcionarios, mesAnoDesejado);
        System.out.println("O vendedor que mais vendeu no mes " + mesAnoDesejado +
                " foi: " + vendedorQueMaisVendeuNoMes);
    }
}
