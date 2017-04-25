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
import java.util.HashMap;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;

/**
 *
 * @author bavi
 */
public class Indexer {

    private String base;
    private ArrayList<String> tagsRequired;
    private ArrayList<String> allTags;
    private Analysers an;
    private IndexWriter iwr;

    public Indexer(String base, Directory r) throws IOException {
        this.base = base;
        this.tagsRequired = new ArrayList<>();
        this.tagsRequired.add("RN");
        this.tagsRequired.add("AU");
        this.tagsRequired.add("TI");
        this.tagsRequired.add("AB");
        this.tagsRequired.add("MJ");
        this.tagsRequired.add("MN");
        this.tagsRequired.add("EX");
        this.allTags = new ArrayList<>(this.tagsRequired);
        this.allTags.add("PN");
        this.allTags.add("AN");
        this.allTags.add("SO");
        this.allTags.add("RF");
        this.allTags.add("CT");
        this.allTags.add("");

        this.an = new Analysers();
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwr = new IndexWriter(r, iwc);
        List<Document> list = this.getDocument();

        for (Document doc : list) {
            iwr.addDocument(doc);
            //System.out.println(iwr.numDocs());
        }
        System.out.println(iwr.numDocs());
        iwr.close();

    }

    public List<Document> getDocument() throws FileNotFoundException, IOException {

        List<Document> list = new ArrayList<>();
        File f = new File(this.base);
        String[] files = f.list();
        String[] line;
        Boolean ava = false;
        String tag = "";
        String value = "";
        String tagAux = "";
//        CFformat cfFormat = new CFformat();
        HashMap<String, String> format = new HashMap<>();
        Document doc = new Document();
        int j = 0;
        for (String file : files) {
            String text = new String(Files.readAllBytes(Paths.get(this.base + file)), StandardCharsets.UTF_8);
            text = text.replace("   ", "  ");
            line = text.split("\n");
            for (int i = 0; i < line.length; i++) {
                if (line[i].equals("")) {
                    list.add(doc);
                    doc = new Document();
                    i++;
                } else {
                    if (line[i].length() > 2) {
                        tagAux = (String) line[i].subSequence(0, 2);
                    } else {
                        tagAux = line[i];
                    }
                    if (this.tagsRequired.contains(tagAux)) {
                        tag = tagAux;
                        value += " " + line[i].replace(tagAux, "");
                        if (((String) line[i + 1].subSequence(0, 2)).equals("  ")) {
                            i++;
                            tagAux = (String) line[i].subSequence(0, 2);
                        }
                        while (tagAux.equals("  ") || !this.allTags.contains(tagAux)) {
                            value += " " + line[i].replace(tagAux, "");

                            i++;
                            if (line[i].length() > 2) {
                                tagAux = (String) line[i].subSequence(0, 2);
                            } else {
                                tagAux = line[i];
                            }
                            ava = true;

                        }
//                        System.out.println(tag + " " + value.trim());
                        TextField tF;
                        switch (tag) {
                            case "AB":
                            case "EX":
                                tF = new TextField(tag, an.Process(value.trim()), Field.Store.YES);
                                //tF.setBoost(.6f);
                                break;
                            case "RN":
                                tF = new TextField(tag, value.trim(), Field.Store.YES);
                                break;
                            case "TI":
                                tF = new TextField(tag, an.Process(value.trim()), Field.Store.YES);
                                //tF.setBoost(.4f);
                                break;
                            case "AU":
                            case "MJ":
                            case "MN":
                                tF = new TextField(tag, value.trim(), Field.Store.YES);
                                //tF.setBoost(.1f);
                                break;
                            default:
                                tF = null;
                                break;
                        }
                        doc.add(tF);
                        j++;
                        value = "";
                        if (ava) {
                            ava = false;
                            i--;
                        }
                    }

                }
            }

        }

        System.out.println(j);
        return list;
    }
}
