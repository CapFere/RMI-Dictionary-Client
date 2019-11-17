/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Captain
 */
import java.io.Serializable;
import java.util.ArrayList;

public class Dictionary implements Serializable {
    private String keyword;
    private ArrayList<String> meanings;
    private String request;
    private boolean wasSucceful;

    public Dictionary(String keyword, ArrayList<String> meanings) {
        this.keyword = keyword;
        this.meanings = meanings;
        this.request = "";
        this.wasSucceful = false;
    }

    public String getKeyword() {
        return keyword;
    }

    public ArrayList<String> getMeanings() {
        return meanings;
    }

    public String getRequest() {
        return request;
    }

    public boolean getWasSucceful() {
        return wasSucceful;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setMeanings(ArrayList<String> meanings) {
        this.meanings = meanings;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setWasSucceful(Boolean wasSucceful) {
        this.wasSucceful = wasSucceful;
    }

    

}