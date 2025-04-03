package com.TesteTecnico.DadosTransformacao.Controller;

import com.TesteTecnico.DadosTransformacao.Service.ProcessadorDePdf;
import com.TesteTecnico.DadosTransformacao.Service.ServicoDeDownload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TransformacaoDeDadosController {

    @Autowired
    private ProcessadorDePdf processadorDePdf;

    @Autowired
    private ServicoDeDownload servicoDeDownload;

    @GetMapping("/extrair-e-baixar")
    public ResponseEntity<Object> extrairEDefinirDownload(@RequestParam String caminhoPdf) {
        try {
            String caminhoCsv = "dados_extraidos.csv";
            String caminhoZip = "Teste_JoaoVitor.zip";

            processadorDePdf.processarPdf(caminhoPdf, caminhoCsv, caminhoZip);
            return servicoDeDownload.baixarArquivo(caminhoZip, "Teste_JoaoVitor.zip");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao processar o arquivo: " + e.getMessage());
        }
    }
}
