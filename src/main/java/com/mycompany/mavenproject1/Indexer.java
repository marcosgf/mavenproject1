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
import org.apache.lucene.analysis.en.PorterStemFilter;

/**
 *
 * @author bavi
 */
public class Indexer {

    private String base;
    private ArrayList<String> tags;

    public Indexer(String base) {
        this.base = base;
        /**
         * Arraylist com as tags que serão extraídas *
         */
        this.tags = new ArrayList<String>();
        this.tags.add("RN");
        this.tags.add("AU");
        this.tags.add("TI");
        this.tags.add("AB");
        this.tags.add("MJ");
        this.tags.add("MN");
        this.tags.add("  ");
    }

    public CFformat getDocument() throws FileNotFoundException, IOException {
        /**
         * Retorna um map contendo todos os dados de todso os documentos. *
         */
        
//        Map docs = new HashMap<String, HashMap<String, String>>();
        File f = new File(this.base);
        String[] files = f.list();
        String[] line;
        String tag = "";
        String value = "";
        String tagAux = "";
        CFformat cfFormat = new CFformat();

        for (String file : files) {
            String text = new String(Files.readAllBytes(Paths.get(this.base + file)), StandardCharsets.UTF_8);
            text = text.replace("   ", "  ");
            line = text.split("\n");
//            System.out.println(text);
            int j = 0;
            for (String line1 : line) {
                if (!line1.isEmpty()) {
                    tagAux = (String) line1.subSequence(0, 2);
                    if (this.tags.contains(tagAux)) {
                        if (tagAux.equals("  ")) {
                            value += line1.replace(tagAux, " ");
                        } else {
                            value += line1.replace(tagAux, "");
                            if (!tagAux.equals(tag)) {
                                tag = tagAux;
//                                System.out.println(tagAux + "-" + value);
                                cfFormat.addTupla(tagAux, value.trim());
                                value = "";
                            }
                        }

                    }
                } else {
//                    System.out.println("Doc" + cfFormat.getValue("RN") + " \n");
//                    docs.put(cfFormat.getValue("RN") + j, cfFormat);
//                    cfFormat = new CFformat();
//                    tag = tagAux = value = "";

                }
            }

        }
        return cfFormat;

    }
}
