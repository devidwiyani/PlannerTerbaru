package com.example.planner;

public class Repeat {


    // variables for our coursename,
    // description, tracks and duration, id.
    private String title;
    private String deskripsi;
    private int userId;
    private int id;



    public Repeat(int id, int userId, String title, String deskripsi) {
        this.userId = userId;;
        this.title = title;
        this.deskripsi = deskripsi;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
