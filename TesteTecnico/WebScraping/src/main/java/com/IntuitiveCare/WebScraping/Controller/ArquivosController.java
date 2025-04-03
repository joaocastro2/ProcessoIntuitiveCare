package com.IntuitiveCare.WebScraping.Controller;

import com.IntuitiveCare.WebScraping.Service.CompressorService;
import com.IntuitiveCare.WebScraping.Service.ArquivoDownloadService;
import com.IntuitiveCare.WebScraping.Service.WebScrapingService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import org.slf4j.Logger;

@RestController
public class ArquivosController {

    @Autowired
    private WebScrapingService webScrapingService;

    @Autowired
    private CompressorService compressorService;

    @Autowired
    private ArquivoDownloadService arquivoDownloadService; // Injeta o serviço de download

    @GetMapping("/baixar-e-comprimir")
    public ResponseEntity<byte[]> baixarEComprimir(@RequestParam String url) {
        try {
            List<String> links = webScrapingService.BuscaLink(url);

            if (links.size() < 2 || links.get(0) == null || links.get(1) == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Não foram encontrados links válidos para Anexo I e Anexo II.".getBytes());
            }

            //Valida a existência dos arquivos
            String caminhoArquivo1 = "Anexo_I.pdf";
            String caminhoArquivo2 = "Anexo_II.pdf";

            //Baixa os arquivos
            arquivoDownloadService.downloadArquivo(links.get(0), caminhoArquivo1);
            arquivoDownloadService.downloadArquivo(links.get(1), caminhoArquivo2);

            //Comprime e salva os arquivos
            String caminhoZip = "Anexos.zip";
            compressorService.comprimirArquivos(new String[]{caminhoArquivo1, caminhoArquivo2}, caminhoZip);

            File arquivoZip = new File(caminhoZip);
            byte[] conteudoZip = Files.readAllBytes(arquivoZip.toPath());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Anexos.zip");

            // Remove arquivos temporários
            new File(caminhoArquivo1).delete();
            new File(caminhoArquivo2).delete();

            return new ResponseEntity<>(conteudoZip, headers, HttpStatus.OK);
        } catch (Exception e) {
            Logger registro = LoggerFactory.getLogger(ArquivosController.class);
            registro.error("Erro ao processar requisição", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar a requisição.".getBytes());
        }
    }
}
