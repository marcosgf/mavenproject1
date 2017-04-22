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
 * @author bavi
 */
public class CFformat {

    private Map<String, String> cfDocument;

    public CFformat() {
        this.cfDocument = new HashMap<String, String>();
    }

    public void addTupla(String key, String value) {
        this.cfDocument.put(key, value);
    }

    public String getValue(String key) {
        return this.cfDocument.get(key);
    }

    public Map<String, String> getCfDocument() {
        return this.cfDocument;
    }

}
