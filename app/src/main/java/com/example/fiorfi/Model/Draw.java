package com.example.fiorfi.Model;

public class Draw {
    private String giftid;
    private String giftimage;
    private String description;
    private String publisher;


    public Draw(String giftid, String giftimage, String description, String publisher) {
        this.giftid = giftid;
        this.giftimage = giftimage;
        this.description = description;
        this.publisher = publisher;
    }

    public Draw() {
    }


    public String getPostid() {
        return giftid;
    }

    public void setPostid(String giftid) {
        this.giftid = giftid;
    }



    public String getPostimage() {
        return giftimage;
    }

    public void setPostimage(String giftimage) {
        this.giftimage = giftimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
