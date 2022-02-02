package com.example.SDA_ASSIGNMENT4_PAULFENNELL;
public class books  {

    // Model Class
    String author;
    String title;
    String image;

    //Constructors

    public books() {
    }

    public books(String author, String title, String image) {
        this.author = author;
        this.title = title;
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return image;
    }

    public void setImageUrl(String image) {
        this.image = image;
    }
}
