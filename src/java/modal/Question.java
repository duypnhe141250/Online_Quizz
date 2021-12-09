/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modal;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Dell Inc
 */
public class Question {
    private int qid;
    private String username;
    private String content;
    private Date date;
    private ArrayList<Answer> answers = new ArrayList<>();

    public Question() {
    }

    
    public Question( String username, String content, Date date) {
        this.username = username;
        this.content = content;
        this.date = date;
    }

    
    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
    public String getDateString(){
        return new SimpleDateFormat("dd-MMM-yyyy").format(this.date);
    }
}
