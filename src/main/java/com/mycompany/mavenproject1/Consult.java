/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory;

/**
 *
 * @author marcos
 */
public class Consult {
    private IndexSearcher idxSearcher ;
    private QueryParser queryParser;
    private Analysers an;
    private String[] tags;
    
    public Consult(Directory r) throws IOException{
        IndexReader iReader = DirectoryReader.open(r);
        this.idxSearcher = new IndexSearcher(iReader);
        this.idxSearcher.setSimilarity(new BM25Similarity());
        this.an = new Analysers();
        String[] _tags = {"RN","AU","TI","AB","MJ","MN","EX"};
        this.tags = _tags;
    }
    
    public TopDocs searchMultiField(String cns,int n) throws IOException, ParseException
    {
         queryParser = new MultiFieldQueryParser(this.tags, new StandardAnalyzer());
         Query query = queryParser.parse(an.Process(cns));
         return idxSearcher.search(query, n);
    }
    
    public TopDocs searchPhraseQuery(String cns, int n) throws IOException, ParseException
    {
         PhraseQuery query = new PhraseQuery(1,tags[0], an.Process(cns));
         return idxSearcher.search(query, n);
    }
    
    public Document getDoc(ScoreDoc sd) throws IOException{
        return this.idxSearcher.doc(sd.doc);
    }
}
