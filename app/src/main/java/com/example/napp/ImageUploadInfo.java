package com.example.napp;


public class ImageUploadInfo {

    public String imageTitle;

    public String imageDescription;

    public String imageURL;

    public String selectedDept;

    public String uId ;
    public ImageUploadInfo() {

    }

    public ImageUploadInfo(String TempUid,String ImageTitle,String selectedDept,String ImageDesc, String url ) {

        this.uId = TempUid;
        this.imageTitle = ImageTitle;
        this.selectedDept = selectedDept;
        this.imageDescription = ImageDesc;
        this.imageURL= url;
    }

    public String getuId() {
        return uId;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public String getSelectedDept() { return selectedDept; }

    public String getImageDescription() {
        return imageDescription;
    }

    public String getImageURL() {
        return imageURL;
    }


}

