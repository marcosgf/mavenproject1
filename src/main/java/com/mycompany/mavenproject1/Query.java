/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author marcos
 */
public class Query {

    private final String id;
    private final String description;
    private final String nrRelevant;
    private final Map<String, String> relevantScore;

    public Query(String id, String description, String nrRelevant) {
        this.id = id;
        this.description = description;
        this.nrRelevant = nrRelevant;
        this.relevantScore = new HashMap<>();
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

}
