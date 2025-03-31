package com.TesteTecnico.DadosTransformacao.Service;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class CsvZipperSERVICE {

    public void zipCsv(String csvPath, String zipPath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipPath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            File csvFile = new File(csvPath);
            try (FileInputStream fis = new FileInputStream(csvFile)) {
                ZipEntry zipEntry = new ZipEntry(csvFile.getName());
                zos.putNextEntry(zipEntry);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) >= 0) {
                    zos.write(buffer, 0, length);
                }
            }
        }
    }

}
