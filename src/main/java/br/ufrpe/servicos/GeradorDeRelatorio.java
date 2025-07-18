package br.ufrpe.servicos;


import br.ufrpe.negocio.beans.Carro;
import br.ufrpe.negocio.beans.Categoria;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GeradorDeRelatorio {

    public static void main(String[] args) {

        List<Carro> frota = Arrays.asList(
                new Carro("Fiat ", "Mobi", 2023, "PDV-1234", Categoria.ECONOMICO, true, 68000.00),
                new Carro("Hyundai ", "HB20", 2024, "PGE-5678", Categoria.ECONOMICO, true, 85000.50),
                new Carro("Toyota", "Corolla", 2023, "KGS-1122", Categoria.INTERMEDIARIO, false, 150000.00),
                new Carro("Jeep", "Compass", 2024, "QWE-9876", Categoria.SUV, true, 230000.00),
                new Carro("BMW", "320i", 2025, "BMW-0320", Categoria.LUXO, true, 320000.00),
                new Carro("VW", "Nivus", 2023, "JLP-4321", Categoria.SUV, true, 135000.00)
        );


        Map<Categoria, List<Carro>> carrosPorCategoria = frota.stream()
                .collect(Collectors.groupingBy(Carro::getCategoria));


        String relatorioFinal = gerarRelatorio(carrosPorCategoria);
        System.out.println(relatorioFinal);
    }


    public static String gerarRelatorio(Map<Categoria, List<Carro>> carrosAgrupados) {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=========================================\n");
        relatorio.append("  Relatório de Carros por Categoria\n");
        relatorio.append("=========================================\n\n");


        carrosAgrupados.forEach((categoria, listaDeCarros) -> {
            relatorio.append("Categoria: ").append(categoria.getDescricao()).append("\n");
            relatorio.append("-----------------------------------------\n");

            // O método toString() do Carro cuida da formatação de cada linha
            listaDeCarros.forEach(carro -> relatorio.append(carro).append("\n"));

            relatorio.append("\n");
        });

        return relatorio.toString();
    }
}