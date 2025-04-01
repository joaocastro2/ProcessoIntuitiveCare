package com.TesteTecnico.DadosTransformacao.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class ExtratorDePdf {

    public void extrairTabelasParaCsv(String caminhoPdf, String caminhoCsv) throws IOException {
        validarCaminhos(caminhoPdf, caminhoCsv);

        try (PdfReader leitorPdf = new PdfReader(caminhoPdf);
             PdfDocument documentoPdf = new PdfDocument(leitorPdf);
             BufferedWriter escritorCsv = new BufferedWriter(new FileWriter(caminhoCsv))) {

            // Escreve o cabeçalho no CSV apenas uma vez
            escreverCabecalhoCsv(escritorCsv);

            // Percorre todas as páginas do PDF
            for (int pagina = 1; pagina <= documentoPdf.getNumberOfPages(); pagina++) {
                String conteudoPagina = PdfTextExtractor.getTextFromPage(
                        documentoPdf.getPage(pagina), new LocationTextExtractionStrategy());

                // Processa os dados extraídos da página
                String dadosTabela = processarDadosDaPagina(conteudoPagina);
                if (!dadosTabela.isEmpty()) {
                    escritorCsv.write(dadosTabela);
                }
            }
        }
    }

    private void validarCaminhos(String caminhoPdf, String caminhoCsv) {
        if (caminhoPdf == null || caminhoCsv == null) {
            throw new IllegalArgumentException("Os caminhos do PDF e CSV não podem ser nulos!");
        }
    }

    private void escreverCabecalhoCsv(BufferedWriter escritorCsv) throws IOException {
        // Cabeçalhos corrigidos para 13 colunas
        escritorCsv.write("PROCEDIMENTO;RN ALTERACAO;VIGENCIA;ODONTOLOGIA;AMBULATORIAL;HCO;HSO;REF;PAC;DUT;SUBGRUPO;GRUPO;CAPÍTULO");
        escritorCsv.newLine();
    }

    private String processarDadosDaPagina(String conteudoPagina) {
        StringBuilder tabelaFormatada = new StringBuilder();
        String[] linhas = conteudoPagina.split("\n"); // Divide o texto em linhas no PDF

        StringBuilder linhaAtual = new StringBuilder(); // Constrói a linha completa para o CSV

        for (String linha : linhas) {
            if (linha.isBlank()) {
                continue; // Ignora linhas vazias
            }

            String[] colunas = linha.split("\\s{2,}"); // Divide as colunas por múltiplos espaços

            if (colunas.length == 13) {
                // Caso a linha esteja completa, adiciona ao CSV
                for (String coluna : colunas) {
                    linhaAtual.append(coluna.trim()).append(";");
                }
                tabelaFormatada.append(linhaAtual).append("\n");
                linhaAtual.setLength(0); // Reseta a construção da próxima linha
            } else {
                // Consolida textos longos da mesma linha (linhas quebradas no PDF)
                for (String coluna : colunas) {
                    linhaAtual.append(coluna.trim()).append(" ");
                }
            }
        }

        if (linhaAtual.length() > 0) {
            // Adiciona a última linha consolidada
            tabelaFormatada.append(linhaAtual).append("\n");
        }

        return tabelaFormatada.toString();
    }
}