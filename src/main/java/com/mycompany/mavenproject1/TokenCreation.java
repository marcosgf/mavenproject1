/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author jorao
 */
public class TokenCreation {
/** Classe para criação de Tokens utilizados nos docs e nas query **/
    String steam;
    String analayze;

    public TokenCreation(String steam, String analayze) {
        this.steam = steam;
        this.analayze = analayze;
    }

    public void setSteam(String steam) {
        this.steam = steam;
    }

    public void setAnalayze(String analayze) {
        this.analayze = analayze;
    }

    public String getSteam() {
        return steam;
    }

    public String getAnalayze() {
        return analayze;
    }

    public void tokenizer() {

    }
}
