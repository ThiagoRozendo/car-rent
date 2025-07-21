package br.ufrpe.servicos;

import br.ufrpe.negocio.Fachada;
import br.ufrpe.negocio.beans.*;
import br.ufrpe.negocio.excecoes.ClienteNaoEncontradoException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class RelatorioService {

    private final Fachada fachada;
    private final Locale brLocale = new Locale("pt", "BR");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RelatorioService(Fachada fachada) {
        this.fachada = fachada;
    }

    private Document criarDocumentoPdf(File arquivo) throws IOException {
        PdfWriter writer = new PdfWriter(arquivo.getAbsolutePath());
        PdfDocument pdf = new PdfDocument(writer);
        return new Document(pdf);
    }

    private Paragraph criarTitulo(String texto) {
        return new Paragraph(texto)
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
    }

    private Paragraph criarSubtitulo(String texto) {
        return new Paragraph(texto)
                .setBold()
                .setFontSize(14)
                .setMarginTop(15)
                .setMarginBottom(10);
    }

    public void gerarRelatorioCarrosDisponiveis(File arquivo) throws IOException {
        Document document = criarDocumentoPdf(arquivo);
        document.add(criarTitulo("Relatório de Carros Disponíveis"));

        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 3, 2, 2}));
        table.setWidth(UnitValue.createPercentValue(100));
        table.addHeaderCell("Marca").addHeaderCell("Modelo").addHeaderCell("Placa").addHeaderCell("Status");

        List<Carro> carrosDisponiveis = Arrays.stream(fachada.listarCarros())
                .filter(Carro::isStatus)
                .collect(Collectors.toList());

        for (Carro carro : carrosDisponiveis) {
            table.addCell(carro.getMarca());
            table.addCell(carro.getModelo());
            table.addCell(carro.getPlaca());
            table.addCell("Disponível");
        }
        document.add(table);
        document.close();
    }

    public void gerarRelatorioAlugueisAtivos(File arquivo) throws IOException, ClienteNaoEncontradoException {
        Document document = criarDocumentoPdf(arquivo);
        document.add(criarTitulo("Relatório de Aluguéis Ativos"));

        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 2, 3, 2, 2, 2}));
        table.setWidth(UnitValue.createPercentValue(100));
        table.addHeaderCell("Cliente").addHeaderCell("CPF").addHeaderCell("Carros Alugados").addHeaderCell("Devolução").addHeaderCell("Diária (R$)").addHeaderCell("Valor Atual (R$)");

        List<Aluguel> alugueisAtivos = fachada.listarAlugueis().stream()
                .filter(Aluguel::isAtivo)
                .collect(Collectors.toList());

        for (Aluguel aluguel : alugueisAtivos) {
            Cliente cliente = fachada.buscarClientePorCpf(aluguel.getCpfCliente());
            table.addCell(cliente.getNome());
            table.addCell(cliente.getCpf());

            String carrosStr = Arrays.stream(aluguel.getCarrinho())
                    .map(c -> c.getModelo() + " (" + c.getPlaca() + ")")
                    .collect(Collectors.joining(", "));
            table.addCell(carrosStr);
            table.addCell(aluguel.getDataFim().format(dateFormatter));

            double diaria = Arrays.stream(aluguel.getCarrinho()).mapToDouble(Carro::getPreco).sum();
            table.addCell(String.format(brLocale, "%.2f", diaria));
            table.addCell(String.format(brLocale, "%.2f", aluguel.getValorParcial()));
        }
        document.add(table);
        document.close();
    }

    public void gerarRelatorioHistoricoCliente(File arquivo, Cliente cliente) throws IOException {
        Document document = criarDocumentoPdf(arquivo);
        document.add(criarTitulo("Histórico de Aluguéis: " + cliente.getNome()));
        document.add(new Paragraph("CPF: " + cliente.getCpf()).setMarginBottom(20));

        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 2, 2, 2, 2, 2}));
        table.setWidth(UnitValue.createPercentValue(100));
        table.addHeaderCell("ID").addHeaderCell("Carros").addHeaderCell("Início").addHeaderCell("Fim").addHeaderCell("Status").addHeaderCell("Diária (R$)").addHeaderCell("Valor Final (R$)");

        List<Aluguel> alugueisDoCliente = fachada.listarAlugueis().stream()
                .filter(a -> a.getCpfCliente().equals(cliente.getCpf()))
                .collect(Collectors.toList());

        for (Aluguel aluguel : alugueisDoCliente) {
            table.addCell(String.valueOf(aluguel.getIdAluguel()));
            String carrosStr = Arrays.stream(aluguel.getCarrinho()).map(Carro::getModelo).collect(Collectors.joining(", "));
            table.addCell(carrosStr);
            table.addCell(aluguel.getDataInicio().format(dateFormatter));
            table.addCell(aluguel.getDataFim().format(dateFormatter));
            table.addCell(aluguel.isAtivo() ? "Ativo" : "Finalizado");
            double diaria = Arrays.stream(aluguel.getCarrinho()).mapToDouble(Carro::getPreco).sum();
            table.addCell(String.format(brLocale, "%.2f", diaria));
            table.addCell(String.format(brLocale, "%.2f", aluguel.getValorParcial()));
        }
        document.add(table);
        document.close();
    }

    public void gerarRelatorioClientesAtrasados(File arquivo) throws IOException, ClienteNaoEncontradoException {
        Document document = criarDocumentoPdf(arquivo);
        document.add(criarTitulo("Relatório de Clientes com Aluguéis Atrasados"));

        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 2, 2, 1, 2, 2}));
        table.setWidth(UnitValue.createPercentValue(100));
        table.addHeaderCell("Cliente").addHeaderCell("CPF").addHeaderCell("Devolução Prevista").addHeaderCell("Dias Atraso").addHeaderCell("Diária (R$)").addHeaderCell("Valor Atual (R$)");

        List<Aluguel> alugueisAtrasados = fachada.listarAlugueis().stream()
                .filter(a -> a.isAtivo() && LocalDate.now().isAfter(a.getDataFim()))
                .collect(Collectors.toList());

        for (Aluguel aluguel : alugueisAtrasados) {
            Cliente cliente = fachada.buscarClientePorCpf(aluguel.getCpfCliente());
            table.addCell(cliente.getNome());
            table.addCell(cliente.getCpf());
            table.addCell(aluguel.getDataFim().format(dateFormatter));
            long diasAtraso = ChronoUnit.DAYS.between(aluguel.getDataFim(), LocalDate.now());
            table.addCell(String.valueOf(diasAtraso));
            double diaria = Arrays.stream(aluguel.getCarrinho()).mapToDouble(Carro::getPreco).sum();
            table.addCell(String.format(brLocale, "%.2f", diaria));
            table.addCell(String.format(brLocale, "%.2f", aluguel.getValorParcial()));
        }
        document.add(table);
        document.close();
    }

    public void gerarRelatorioCarrosPorCategoria(File arquivo) throws IOException {
        Document document = criarDocumentoPdf(arquivo);
        document.add(criarTitulo("Relatório de Carros por Categoria"));

        Map<Categoria, List<Carro>> carrosAgrupados = Arrays.stream(fachada.listarCarros())
                .collect(Collectors.groupingBy(Carro::getCategoria));

        for (Map.Entry<Categoria, List<Carro>> entry : carrosAgrupados.entrySet()) {
            document.add(criarSubtitulo("Categoria: " + entry.getKey().toString()));
            Table table = new Table(UnitValue.createPercentArray(new float[]{3, 3, 2}));
            table.setWidth(UnitValue.createPercentValue(100));
            table.addHeaderCell("Marca").addHeaderCell("Modelo").addHeaderCell("Status");

            for (Carro carro : entry.getValue()) {
                table.addCell(carro.getMarca());
                table.addCell(carro.getModelo());
                table.addCell(carro.isStatus() ? "Disponível" : "Alugado");
            }
            document.add(table);
        }
        document.close();
    }
}