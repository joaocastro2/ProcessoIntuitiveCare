package com.TesteTecnico.DadosTransformacao.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class ExtracaoTabelaPdf {

    public void extractTableData(String pdfPath, String csvPath) throws IOException {
        try (PdfReader reader = new PdfReader(pdfPath);
             PdfDocument pdfDocument = new PdfDocument(reader);
             BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath))) {

            writer.write("PROCEDIMENTO,RN ALTERACAO,VIGENCIA,ODONTOLOGIA,AMBULATORIAL,HCO,HSO,REF,PAC,DUT,SUBGRUPO,GRUPO,CAPÍTULO");
            writer.newLine();

            for (int i = 1; i <= pdfDocument.getNumberOfPages(); i++) {
                String pageContent = PdfTextExtractor.getTextFromPage(pdfDocument.getPage(i));
                if (pageContent.contains("Tabela")) { // Identificar páginas com tabelas
                    writer.write("Exemplo de linha extraída"); // Substitua pela lógica real de extração
                    writer.newLine();
                }
            }
        }
    }

}
