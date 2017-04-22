/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author marcos
 */
public class Testes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map<String, String> tst = new HashMap<>();
        tst.put("11", "fdasf");
        tst.put("12", "fdasf");
        tst.put("13", "fdasf");
        tst.put("14", "fdasf");
        tst.put("15", "fdasf");
        tst.put("16", "fdasf");
        tst.put("17", "fdasf");

        for (String key : tst.keySet()) {
            String value = tst.get(key);
            System.out.println(key + " = " + value);
        }
    }

}
