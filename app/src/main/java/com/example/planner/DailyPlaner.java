package com.example.planner;

public class DailyPlaner {

    // variables for our coursename,
    // description, tracks and duration, id.
    private String tambahdailyplaner;
    private String tambahstarttime;
    private String tambahendtime;
    private String tambahstatus;
    private String tambahsaveid;
    private String tambahtanggal;
    private String tambahuserid;
    private int id;


    // constructor
    public DailyPlaner(Integer id,  String tambahuserid, String inputDailyPlan, String inputStartTime, String inputEndTime, String inputSaveId, String inputTanggal, String inputStatus) {
        this.id = id;
        this.tambahdailyplaner = inputDailyPlan;
        this.tambahstarttime = inputStartTime;
        this.tambahendtime = inputEndTime;
        this.tambahsaveid = inputSaveId;
        this.tambahtanggal = inputTanggal;
        this.tambahstatus = inputStatus;

    }

    // creating getter and setter methods
    public String getTambahstatus() {
        return tambahstatus;
    }

    public void setTambahstatus(String tambahstatus ) {
        this.tambahstatus = tambahstatus;
    }

    public String getTambahdailyplaner() {
        return tambahdailyplaner;
    }

    public void setTambahdailyplaner(String tambahdailyplaner ) {
        this.tambahdailyplaner = tambahdailyplaner;
    }

    public String getTambahstarttime() {
        return tambahstarttime;
    }

    public void setTambahstarttime(String tambahstarttime) {
        this.tambahstarttime = tambahstarttime;
    }

    public String getTambahendtime() {
        return tambahendtime;
    }

    public void setTambahendtime(String tambahendtime) {
        this.tambahendtime = tambahendtime;
    }

    public String getTambahuserid() {
        return tambahuserid;
    }

    public void setTambahuserid(String tambahuserid) {
        this.tambahuserid = tambahuserid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}