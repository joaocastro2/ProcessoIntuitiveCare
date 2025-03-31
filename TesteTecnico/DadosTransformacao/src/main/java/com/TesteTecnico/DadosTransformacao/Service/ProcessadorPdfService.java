package com.TesteTecnico.DadosTransformacao.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProcessadorPdfService {

    @Autowired
    private ExtracaoTabelaPdf extrairpdf;

    @Autowired
    private CsvZipperSERVICE csvZipper;

    public void processPdf(String pdfPath, String csvPath, String zipPath) throws IOException {
        extrairpdf.extractTableData(pdfPath, csvPath); // Extrai os dados para o CSV
        csvZipper.zipCsv(csvPath, zipPath); // Compacta o arquivo CSV em ZIP
    }

}
