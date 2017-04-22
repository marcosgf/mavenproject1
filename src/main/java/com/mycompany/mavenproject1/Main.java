/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author bavi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
//        Indexer teste = new Indexer("/home/jorao/Documentos/RI/cfcDoc/");
//        CFformat abc = teste.getDocument();
          QueryInit teste = new QueryInit("cfc/");
          teste.getQuerys();
          

    }

}
