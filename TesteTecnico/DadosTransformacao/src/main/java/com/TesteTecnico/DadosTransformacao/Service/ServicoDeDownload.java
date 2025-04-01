package com.TesteTecnico.DadosTransformacao.Service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class ServicoDeDownload {

    public ResponseEntity<Object> baixarArquivo(String caminhoArquivo, String nomeArquivo) throws IOException {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            throw new IOException("Arquivo não encontrado: " + caminhoArquivo);
        }

        InputStreamResource recurso = new InputStreamResource(new FileInputStream(arquivo));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nomeArquivo)
                .contentLength(arquivo.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(recurso);
    }
}