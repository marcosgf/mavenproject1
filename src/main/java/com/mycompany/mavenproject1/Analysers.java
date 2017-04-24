/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.AttributeFactory;

/**
 *
 * @author marcos
 */
public class Analysers {
    private StandardTokenizer standTokz;
    private Set stopwords;
    public Analysers() throws FileNotFoundException, IOException
    {
        stopwords = new HashSet<>();
        standTokz = new StandardTokenizer(AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY);
        BufferedReader br = new BufferedReader(new FileReader("stopword_list.txt"));
        while(br.ready())
            stopwords.add(br.readLine());      
    }
 
    public  String Process(String input) throws IOException 
    {
        standTokz.setReader(new StringReader(input));
        TokenStream tokenStream =  standTokz;        
        tokenStream = new StopFilter(tokenStream, CharArraySet.copy(stopwords));     
        tokenStream = new PorterStemFilter(tokenStream);        
        tokenStream.reset();
        StringBuilder sb = new StringBuilder();
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        CharTermAttribute charTermAttr = tokenStream.getAttribute(CharTermAttribute.class);
        try{
            while (tokenStream.incrementToken()) 
            {
                if (sb.length() > 0)
                        sb.append(" ");                
                sb.append(charTermAttr.toString());
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        tokenStream.close();
        return sb.toString();
    }
}
