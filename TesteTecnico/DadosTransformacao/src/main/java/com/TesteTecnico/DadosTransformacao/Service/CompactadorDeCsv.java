package com.TesteTecnico.DadosTransformacao.Service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class CompactadorDeCsv {

    public void compactarCsv(String caminhoCsv, String caminhoZip) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(caminhoZip);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            File arquivoCsv = new File(caminhoCsv);
            try (FileInputStream fis = new FileInputStream(arquivoCsv)) {
                ZipEntry zipEntry = new ZipEntry(arquivoCsv.getName());
                zos.putNextEntry(zipEntry);

                byte[] buffer = new byte[1024];
                int tamanho;
                while ((tamanho = fis.read(buffer)) >= 0) {
                    zos.write(buffer, 0, tamanho);
                }
            }
        }
    }
}