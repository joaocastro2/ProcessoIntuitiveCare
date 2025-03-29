package com.IntuitiveCare.WebScraping.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebScrapingService {

    public List<String> BuscaLink(String url) {
        List<String> LinksAnexo = new ArrayList<>();
        try {
            // Conecta ao site e baixa o HTML
            Document documento = Jsoup.connect("https://www.gov.br/ans/pt-br/acesso-a-informacao" +
                                                   "/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos").get();

            // Seleciona links que possuem "Anexo" no texto ou nome
            Elements links = documento.select("a[href]");
            for (Element link : links) {
                String href = link.absUrl("href");
                if (href.endsWith(".pdf") && (link.text().contains("Anexo I") || link.text().contains("Anexo II"))) {
                    LinksAnexo.add(href);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao fazer web scraping: " + e.getMessage());
        }
        return LinksAnexo;
    }
}