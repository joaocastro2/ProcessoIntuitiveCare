package com.TesteTecnico.DadosTransformacao.Controller;


import com.TesteTecnico.DadosTransformacao.Service.CsvDownloadSERVICE;
import com.TesteTecnico.DadosTransformacao.Service.ProcessadorPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
public class TransformacaoDadosCONTROLLER {

    @Autowired
    private ProcessadorPdfService pdfProcessor;

    @Autowired
    private CsvDownloadSERVICE downloadService;

    @GetMapping("/extract-and-download")
    public ResponseEntity<Object> extractAndDownload(@RequestParam String pdfPath) {
        try {
            String csvPath = "dados_extraidos.csv";
            String zipPath = "Teste_JoaoVitor.zip";

            pdfProcessor.processPdf(pdfPath, csvPath, zipPath); // Invoca o processamento
            return downloadService.downloadFile(zipPath, "Teste_JoaoVitor.zip");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao processar o arquivo: " + e.getMessage());
        }
    }

}
