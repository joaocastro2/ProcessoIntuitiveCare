package com.TesteTecnico.DadosTransformacao.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ProcessadorDePdf {

    @Autowired
    private com.TesteTecnico.DadosTransformacao.Service.ExtratorDePdf extratorDePdf;

    @Autowired
    private com.TesteTecnico.DadosTransformacao.Service.CompactadorDeCsv compactadorDeCsv;

    public void processarPdf(String caminhoPdf, String caminhoCsv, String caminhoZip) throws IOException {
        extratorDePdf.extrairTabelasParaCsv(caminhoPdf, caminhoCsv);
        compactadorDeCsv.compactarCsv(caminhoCsv, caminhoZip);
    }
}


