package com.IntuitiveCare.WebScraping.Controller;

import com.IntuitiveCare.WebScraping.Service.FileCompressionService;
import com.IntuitiveCare.WebScraping.Service.WebScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

@RestController
public class WebScrappingController {

    @Autowired
    private WebScrapingService webScrapingService;

    @Autowired
    private FileCompressionService fileCompressionService;

    @GetMapping("/baixe-e-comprima")
    public ResponseEntity<byte[]> downloadAndCompress(@RequestParam String url) {
        try {
            // 1. Busca links dos anexos
            List<String> links = webScrapingService.BuscaLink(url);

            if (links.size() >= 2) {
                // Define caminhos para salvar os arquivos localmente
                String file1Path = "Anexo_I.pdf";
                String file2Path = "Anexo_II.pdf";

                // 2. Baixa os arquivos localmente
                downloadFile(links.get(0), file1Path);
                downloadFile(links.get(1), file2Path);

                // 3. Compacta os arquivos em um único ZIP
                String zipPath = "Anexos.zip";
                fileCompressionService.comprimirArquivos(new String[]{file1Path, file2Path}, zipPath);

                // 4. Retorna o arquivo ZIP como resposta
                File zipFile = new File(zipPath);
                byte[] zipContent = Files.readAllBytes(zipFile.toPath());
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Anexos.zip");
                return new ResponseEntity<>(zipContent, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Não foram encontrados os links para Anexo I e Anexo II.".getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar a requisição.".getBytes());
        }
    }

    private void downloadFile(String fileUrl, String destinationPath) throws Exception {
        try (InputStream in = new URL(fileUrl).openStream();
             FileOutputStream out = new FileOutputStream(destinationPath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }


}
