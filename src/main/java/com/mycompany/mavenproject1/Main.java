/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

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
    public static void main(String[] args) throws IOException, ParseException {
//        Indexer teste = new Indexer("/home/jorao/Documentos/RI/cfcDoc/");
//        CFformat abc = teste.getDocument();
        Directory r = new RAMDirectory();
        QueryInit teste = new QueryInit("cfc/");
        Consult cns = new Consult(r);
        //Envia Querys 
        for (CFQuery q : teste.getQuerys()) {
            List<String> result = new ArrayList<>();
            TopDocs tp = cns.searchMultiField(q.getDescription(), 25);
            for (ScoreDoc scDoc : tp.scoreDocs) {
                Document d = cns.getDoc(scDoc);
                System.out.println(d.getField("RN"));
                result.add(d.getField("RN").stringValue());
            }
            //gera resultados para cada consulta
            //results(docRelevants,docRetornados);
        }

    }
    
    public void results(List<String> relevants, List<String> returns){
        
    }

}
