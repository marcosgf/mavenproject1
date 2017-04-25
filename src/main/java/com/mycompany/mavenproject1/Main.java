/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

/**
 *
 * @author bavi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    private static Directory r = new RAMDirectory();

    public static void results(List<Integer> relevants, List<Integer> returns, BufferedWriter br, String Id) throws IOException {
        double precision = 0.0;
        double recall = 0.0;
        double count = 0;
        for (int r : returns) {
            System.out.println(r);
            if (relevants.contains(r)) {
                count++;
            }
        }
        precision = count / returns.size();
        recall = count / relevants.size();
        br.write(Id + ":" + precision + " " + recall + "\n");

    }

    public static void main(String[] args) throws IOException, ParseException {
        Indexer indexer = new Indexer("cfcDocuments/", r);
        QueryInit query = new QueryInit("cfc/");
        Consult cns = new Consult(r);
        //Envia Querys 
        List<Integer> result = new ArrayList<>();
        BufferedWriter br = new BufferedWriter(new FileWriter("results.txt"));
        for (CFQuery q : query.getQuerys()) {
            TopDocs tp = cns.searchMultiField(q.getDescription(), 25);
            for (ScoreDoc scDoc : tp.scoreDocs) {
                Document d = cns.getDoc(scDoc);
                System.out.println(d.getField("RN"));
                result.add(Integer.parseInt(d.getField("RN").stringValue()));
            }
            //gera resultados para cada consulta
            results(result, q.getRelevants(), br, q.getId());
            break;
        }
        br.close();

    }

}
