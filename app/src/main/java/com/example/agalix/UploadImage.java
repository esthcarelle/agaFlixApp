package com.example.agalix;

public class UploadImage {
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UploadImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public UploadImage(){

    }

    private String imageUrl;


}
