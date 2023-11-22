package br.com.empresa;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Empresa {
    public List<Funcionario> funcionarios;
    public List<RegistroVendas> registrosVendas;

    public Empresa() {
        funcionarios = new ArrayList<>();
        registrosVendas = new ArrayList<>();
    }

    public void adicionarFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    // Método para adicionar registro de vendas à empresa
    public void adicionarRegistroVendas(RegistroVendas registro) {
        registrosVendas.add(registro);
    }



    /*
    ============================================================================================================
    (1) Método para calcular o valor total pago aos funcionários no mês especificado
    ============================================================================================================
    */

    public double calcularValorTotalPago(List<Funcionario> listaFuncionarios, String mesAno) {
        double valorTotal = 0.0;
        for (Funcionario funcionario : listaFuncionarios) {
            Cargo cargo = funcionario.getCargo();
            int anosContratado = calcularAnosContratado(funcionario.getDataContratacao(), mesAno);

            switch (cargo) {
                case SECRETARIO:
                    double salario = 7000 + (1000 * anosContratado);
                    valorTotal += 7000 + (1000 * anosContratado) + (0.2 * (7000 + salario));
                    break;
                case VENDEDOR:
                    for (RegistroVendas registro : registrosVendas) {
                        if (registro.getVendedor().equals(funcionario) && registro.getMesAno().equals(mesAno)) {
                            valorTotal += 12000 + (1800 * anosContratado) + (0.3 * registro.getValorVendido());
                            break;
                        }
                    }
                    break;
                case GERENTE:
                    valorTotal += 20000 + (3000 * anosContratado);
                    break;
            }
        }

        return valorTotal;
    }


    /*
    ============================================================================================================
     // (2) Método para calcular o total pago somente em salários no mês especificado
    ============================================================================================================
    */

    public double calcularTotalSalarios(List<Funcionario> listaFuncionarios, String mesAno) {
        double totalSalarios = 0.0;

        for (Funcionario funcionario : listaFuncionarios) {
            Cargo cargo = funcionario.getCargo();
            String dataContratacaoFuncionario = funcionario.getDataContratacao();

            // Verificação se a data de contratação é anterior ou igual ao mês/ano fornecido
            if (isDataAnteriorOuIgual(dataContratacaoFuncionario, mesAno)) {
                int anosContratado = calcularAnosContratado(dataContratacaoFuncionario, mesAno);
                switch (cargo) {
                    case SECRETARIO:
                        totalSalarios += 7000 + (1000 * anosContratado);
                        break;
                    case VENDEDOR:
                        totalSalarios += 12000 + (1800 * anosContratado);
                        break;
                    case GERENTE:
                        totalSalarios += 20000 + (3000 * anosContratado);
                        break;
                }
            }
        }

        return totalSalarios;
    }

     /*
    ============================================================================================================
     // (3) Método para calcular o total pago somente em benefícios no mês especificado
    ============================================================================================================
    */

    public double calcularTotalBeneficios(List<Funcionario> listaFuncionarios, String mesAno) {
        double totalBeneficios = 0.0;

        for (Funcionario funcionario : listaFuncionarios) {
            Cargo cargo = funcionario.getCargo();

            // Verifica se o funcionário tem direito a benefícios (Secretário ou Vendedor)
            if (cargo == Cargo.SECRETARIO || cargo == Cargo.VENDEDOR) {
                int anosContratado = calcularAnosContratado(funcionario.getDataContratacao(), mesAno);

                switch (cargo) {
                    case SECRETARIO:
                        double salario = 7000 + (1000 * anosContratado);
                        totalBeneficios += (0.2 * salario);
                        break;
                    case VENDEDOR:
                        // Para o vendedor, é necessário encontrar o registro de vendas do mês especificado
                        for (RegistroVendas registro : registrosVendas) {
                            if (registro.getVendedor().equals(funcionario) && registro.getMesAno().equals(mesAno)) {
                                totalBeneficios += (0.3 * registro.getValorVendido());
                                break;
                            }
                        }
                        break;
                }
            }
        }

        return totalBeneficios;
    }

    /*
    ============================================================================================================
     // (4) Método para encontrar o funcionário com o valor mais alto de vendas no mês especificado
    ============================================================================================================
    */
    public String funcionarioComMaiorValorRecebido(List<Funcionario> funcionarios, List<RegistroVendas> registrosVendas,
                                                   String mesAno) {
        double maiorValorRecebido = Double.MIN_VALUE;
        String funcionarioComMaiorValor = "";

        for (Funcionario funcionario : funcionarios) {
            double valorRecebido = calcularValorTotalRecebido(funcionario, registrosVendas, mesAno);

            if (valorRecebido > maiorValorRecebido) {
                maiorValorRecebido = valorRecebido;
                funcionarioComMaiorValor = funcionario.getNome();
            }
        }

        return funcionarioComMaiorValor;
    }

    private double calcularValorTotalRecebido(Funcionario funcionario, List<RegistroVendas> registrosVendas,
                                              String mesAno) {
        Cargo cargo = funcionario.getCargo();
        double valorTotalRecebido = 0.0;

        int anosContratado = calcularAnosContratado(funcionario.getDataContratacao(), mesAno);

        switch (cargo) {
            case SECRETARIO:
                double salarioSecretario = 7000 + (1000 * anosContratado) + (0.2 * 7000);
                valorTotalRecebido = salarioSecretario;
                break;
            case VENDEDOR:
                double salarioFixo = 12000;
                double beneficioAnos = 1800 * anosContratado;
                double vendasNoMes = calcularTotalVendasNoMes(funcionario, registrosVendas, mesAno);
                valorTotalRecebido = salarioFixo + beneficioAnos + (0.3 * vendasNoMes);
                break;
            case GERENTE:
                double salarioGerente = 20000 + (3000 * anosContratado);
                valorTotalRecebido = salarioGerente;
                break;
            // Adicione outros casos para diferentes tipos de funcionários, se aplicável
        }

        return valorTotalRecebido;
    }

    private double calcularTotalVendasNoMes(Funcionario funcionario, List<RegistroVendas> registrosVendas,
                                            String mesAno) {
        double totalVendas = 0.0;

        for (RegistroVendas registro : registrosVendas) {
            if (registro.getVendedor().equals(funcionario) && registro.getMesAno().equals(mesAno)) {
                totalVendas += registro.getValorVendido();
            }
        }

        return totalVendas;
    }

    /*
    ============================================================================================================
     // (5) Método para encontrar o funcionário com o valor mais alto em benefícios no mês especificado
    ============================================================================================================
    */

    public String funcionarioComMaiorBeneficio(List<Funcionario> listaFuncionarios, String mesAno) {
        String funcionarioComMaiorBeneficio = "";
        double maiorBeneficio = Double.MIN_VALUE;

        for (Funcionario funcionario : listaFuncionarios) {
            Cargo cargo = funcionario.getCargo();
            double beneficio = 0.0;

            if (cargo == Cargo.SECRETARIO) {
                int anosContratado = calcularAnosContratado(funcionario.getDataContratacao(), mesAno);
                double salario = 7000 + (1000 * anosContratado);
                beneficio = (0.2 * salario);
            } else if (cargo == Cargo.VENDEDOR) {
                for (RegistroVendas registro : registrosVendas) {
                    if (registro.getVendedor().equals(funcionario) && registro.getMesAno().equals(mesAno)) {
                        int anosContratado = calcularAnosContratado(funcionario.getDataContratacao(), mesAno);
                        beneficio = (0.3 * registro.getValorVendido());
                        break;
                    }
                }
            }

            if (beneficio > maiorBeneficio) {
                maiorBeneficio = beneficio;
                funcionarioComMaiorBeneficio = funcionario.getNome();
            }
        }

        return funcionarioComMaiorBeneficio;
    }

     /*
    ============================================================================================================
     // (6) Método para encontrar o vendedor que mais vendeu no mês especificado
    ============================================================================================================
    */
        public String vendedorComMaisVendas (List < Funcionario > listaVendedores, String mesAno){
            String vendedorMaisVendas = "";
            double maiorValorVendido = Double.MIN_VALUE;

            for (Funcionario vendedor : listaVendedores) {
                double totalVendidoPorVendedor = 0;

                for (RegistroVendas registro : registrosVendas) {
                    if (registro.getVendedor().equals(vendedor) && registro.getMesAno().equals(mesAno)) {
                        totalVendidoPorVendedor += registro.getValorVendido();
                    }
                }

                if (totalVendidoPorVendedor > maiorValorVendido) {
                    maiorValorVendido = totalVendidoPorVendedor;
                    vendedorMaisVendas = vendedor.getNome();
                }
            }

            return vendedorMaisVendas;
            }

    private boolean isDataAnteriorOuIgual(String data1, String data2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        YearMonth dataFormatada1 = YearMonth.parse(data1, formatter);
        YearMonth dataFormatada2 = YearMonth.parse(data2, formatter);

        return !dataFormatada1.isAfter(dataFormatada2);
    }

    // Método auxiliar para calcular o número de anos desde a contratação até o mês especificado
    private int calcularAnosContratado(String dataContratacao, String mesAno) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        YearMonth dataContratacaoFormatada = YearMonth.parse(dataContratacao, formatter);
        YearMonth dataFornecida = YearMonth.parse(mesAno, formatter);

        return (int) ChronoUnit.YEARS.between(dataContratacaoFormatada.atDay(1),
                dataFornecida.atDay(1));
    }
}



