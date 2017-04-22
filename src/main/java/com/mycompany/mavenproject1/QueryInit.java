/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author marcos
 */
public class QueryInit {

    private String base;
    private ArrayList<Query> querys;

    public QueryInit(String base) {
        this.base = base;
        this.querys = new ArrayList<Query>();
    }

    public ArrayList<Query> getQuerys() throws IOException {
        File f = new File(this.base);
        String[] files = f.list();
        String line[], auxRelevants[];
        String tagAux = "   ";
        String aux = "", id = null, description, nrRelevant = null, relevants = "";
        int qr = 0;
        for (String file : files) {
            if (file.equals("cfquery")) {
                String text = new String(Files.readAllBytes(Paths.get(this.base + file)), StandardCharsets.UTF_8);
                line = text.split("\n");
                for (int i = 0; i < line.length; i++) {
                    tagAux = (String) line[i].subSequence(0, 3);
                    while (!tagAux.equals("RD ")) {
                        switch (tagAux) {
                            case "QN ":
                                id = line[i].replace(tagAux, "");
                                break;
                            case "QU ":
                                aux = line[i].replace(tagAux, "");
                                break;
                            case "NR ":
                                nrRelevant = line[i].replace(tagAux, "");
                                break;
                            case "   ":
                                aux += line[i].replace(tagAux, " ");
                            default:
                                break;
                        }
                        i++;
                        tagAux = (String) line[i].subSequence(0, 3);
                                
                    }
                    description = aux;
                    this.querys.add(new Query(id, description, nrRelevant));
                    relevants += " "+line[i].replace(tagAux, " ") + " ";
                    i++;
                    while (!line[i].equals("") && !line[i].equals(" ") && !line[i].equals("  ") && !line[i].equals(" ")&& !line[i].equals("   ")&& !line[i].equals("    ")&& !line[i].equals("     "))  {
                        relevants += " "+line[i]+" ";
                        i++;
                    }
                    relevants = relevants.replace("  ", " ");
                    relevants = relevants.replace("  ", " ");
                    relevants = relevants.replace("  ", " ");
                    relevants = relevants.replace("  ", " ");
                    auxRelevants = relevants.split(" ");
                    
                    for (int j = 1; j < auxRelevants.length; j += 2) {
                        this.querys.get(qr).addRelevantScore(auxRelevants[j], auxRelevants[j + 1]);
                    }
                    this.querys.get(qr).PrintQuery();
                    qr++;
                    aux = "";
                    relevants = "";
                }
            }
        }
        return this.querys;

    }

}
