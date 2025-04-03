package com.IntuitiveCare.WebScraping.Service;

import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

@Service
public class ArquivoDownloadService {

    public void downloadArquivo(String fileUrl, String destinationPath) throws Exception {
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
