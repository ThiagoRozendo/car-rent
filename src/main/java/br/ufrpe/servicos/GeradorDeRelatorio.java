package br.ufrpe.servicos;

import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.Carro;
import br.ufrpe.negocio.beans.Categoria;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GeradorDeRelatorio {

    public static void main(String[] args) {
        Fachada fachada = Fachada.getInstance();

        if (fachada.listarCarros().length == 0) {
            fachada.cadastrarCarro("Fiat", "Mobi", 2023, "PDV1234", Categoria.ECONOMICO, true, 120.0, "", "");
            fachada.cadastrarCarro("Hyundai", "HB20", 2024, "PGE5678", Categoria.ECONOMICO, true, 185.50, "", "");
            fachada.cadastrarCarro("Toyota", "Corolla", 2023, "KGS1122", Categoria.INTERMEDIARIO, false, 250.00, "", "");
            fachada.cadastrarCarro("Jeep", "Compass", 2024, "QWE9876", Categoria.SUV, true, 350.00, "", "");
            fachada.cadastrarCarro("BMW", "320i", 2025, "BMW0320", Categoria.LUXO, true, 750.00, "", "");
            fachada.cadastrarCarro("VW", "Nivus", 2023, "JLP4321", Categoria.SUV, true, 280.00, "", "");
        }

        List<Carro> frota = Arrays.asList(fachada.listarCarros());

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

            listaDeCarros.forEach(carro -> relatorio.append(carro).append("\n"));

            relatorio.append("\n");
        });

        return relatorio.toString();
}
}