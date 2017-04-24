/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

/**
 *
 * @author bavi
 */
public class Indexer {

    private String base;
    private ArrayList<String> tagsRequired;
    private ArrayList<String> allTags;
    private Analysers an;

    public Indexer(String base) throws IOException {
        this.base = base;
        /**
         * Arraylist com as tags que serão extraídas *
         */
        this.tagsRequired = new ArrayList<String>();
        this.tagsRequired.add("RN");
        this.tagsRequired.add("AU");
        this.tagsRequired.add("TI");
        this.tagsRequired.add("AB");
        this.tagsRequired.add("MJ");
        this.tagsRequired.add("MN");
        this.tagsRequired.add("  ");
        this.tagsRequired.add("EX");
        this.allTags = new ArrayList<String>(this.tagsRequired);
        this.allTags.add("PN");
        this.allTags.add("AN");
        this.allTags.add("SO");
        this.allTags.add("RF");
        this.allTags.add("CT");
        this.an = new Analysers();
    }

    public List<Document> getDocument() throws FileNotFoundException, IOException {
        /**
         * Retorna um map contendo todos os dados de todso os documentos. *
         */

        List<Document> list = new ArrayList<>();
        File f = new File(this.base);
        String[] files = f.list();
        String[] line;
        String tag = "";
        String value = "";
        String tagAux = "";
        CFformat cfFormat = new CFformat();
        Document doc = new Document();

        for (String file : files) {
            String text = new String(Files.readAllBytes(Paths.get(this.base + file)), StandardCharsets.UTF_8);
            text = text.replace("   ", "  ");
            line = text.split("\n");
//            System.out.println(text);
            int j = 0;
            for (String line1 : line) {
                if (!line1.isEmpty()) {
                    if (line1.length() > 2) {
                        tagAux = (String) line1.subSequence(0, 2);
                    } else {
                        tagAux = line1;
                    }
                    if (this.tagsRequired.contains(tagAux)) {
                        if (tagAux.equals("  ")) {
                            value += line1.replace(tagAux, " ");
                        } else {
                            value += line1.replace(tagAux, "");
                            if (!tagAux.equals(tag)) {
                                tag = tagAux;
                                cfFormat.addTupla(tagAux, value.trim());
                                value = "";
                            }
                        }

                    } else {
                        if (!this.allTags.contains(tagAux)) {
                            value = value + " " + line1;
                        }
                    }
                } else {
                    for (String key : cfFormat.getCfDocument().keySet()) {
                        TextField tF = new TextField(key, an.Process(cfFormat.getValue(key)), Field.Store.YES);
                        doc.add(tF);
                    }
                }
                list.add(doc);
                doc = new Document();
            }

        }
        return list;

    }
}