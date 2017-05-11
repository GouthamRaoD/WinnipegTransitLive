package com.youngbro.mytransitlive;

/**
 * Created by Goutham on 2017-03-04.
 */

public class News {
    private String title;
    private String body;
    public News(String title, String body){
        this.title=title;
        this.body =body;
    }
    public String getTitle(){return title;}
    public String getBody(){return body;}
}
