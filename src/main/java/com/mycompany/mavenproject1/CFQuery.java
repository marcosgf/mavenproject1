/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author marcos
 */
public class CFQuery {

    private final String id;
    private final String description;
    private final String nrRelevant;
    private final Map<String, String> relevantScore;
    private  List<Integer> relevants;

    public CFQuery(String id, String description, String nrRelevant) {
        this.id = id;
        this.description = description;
        this.nrRelevant = nrRelevant;
        this.relevantScore = new HashMap<>();
        this.relevants = new ArrayList<>();
    }

    public void PrintQuery() {
        System.out.println("ID:" + this.id + " Description:" + this.description + " nro Relevants:" + this.nrRelevant);
        System.out.println("Relevant docs AND relevant scores:");
        for (String key : this.relevantScore.keySet()) {
            String value = this.relevantScore.get(key);
            System.out.println(key + " = " + value);
        }
    }

    public void addRelevantScore(String nrDoc, String scores) {
        this.relevantScore.put(nrDoc, scores);
        this.relevants.add(Integer.parseInt(nrDoc));
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the nrRelevant
     */
    public String getNrRelevant() {
        return nrRelevant;
    }

    /**
     * @return the relevantScore
     */
    public Map<String, String> getRelevantScore() {
        return relevantScore;
    }
    
    public List<Integer> getRelevants(){
        return relevants;
    }

}
