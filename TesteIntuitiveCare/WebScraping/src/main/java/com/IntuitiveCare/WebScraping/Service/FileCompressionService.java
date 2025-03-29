package com.IntuitiveCare.WebScraping.Service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileCompressionService {


    //Compacta uma lista de arquivos em um único arquivo ZIP.
    public void comprimirArquivos(String[] arquivosForZip, String saidaZip) {
        try (FileOutputStream fos = new FileOutputStream(saidaZip);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            // Itera por todos os arquivos fornecidos e adiciona ao ZIP
            for (String arquivo : arquivosForZip) {
                File arquivoparazip = new File(arquivo);

                // Verifica se o arquivo existe antes de tentar compactar
                if (!arquivoparazip.exists()) {
                    System.out.println("Arquivo não encontrado: " + arquivo);
                    continue;
                }

                // Adiciona o arquivo ao ZIP
                try (FileInputStream fis = new FileInputStream(arquivoparazip)) {
                    ZipEntry entradaZip = new ZipEntry(arquivoparazip.getName());
                    zipOut.putNextEntry(entradaZip);

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) > 0) {
                        zipOut.write(buffer, 0, bytesRead);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao compactar arquivos: " + e.getMessage());
        }
    }
}
