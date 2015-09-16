package zhy2002.springexamples.domain;

import java.util.Date;

/**
 * Article on the website.
 */
public class Article {

    private String title;
    private String content;
    private Date createdDate;

    public Article(){}

    public Article(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
