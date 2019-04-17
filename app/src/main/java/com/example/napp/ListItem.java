package com.example.napp;

public class ListItem{
    private String imageDescription;
    private String imageTitle;
    private String imageURL;
    private String selectedDept;
    private String uId;

    public ListItem(){}
    public ListItem(String imageDescription, String imageTitle, String imageURL, String selectedDept, String uId) {
        this.imageDescription = imageDescription;
        this.imageTitle = imageTitle;
        this.imageURL = imageURL;
        this.selectedDept = selectedDept;
        this.uId = uId;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getSelectedDept() {
        return selectedDept;
    }

    public String getuId() {
        return uId;
    }
}








