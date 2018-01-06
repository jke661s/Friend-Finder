package com.example.jiaqiwang.monashfriendfinder.DataStore;

/**
 * Created by Jiaqi Wang on 5/1/2017.
 */

public class StudentProfile {
    private Integer studid;
    private String firstname;
    private String surname;
    private String dob;
    private String gender;
    private String course;
    private String studymode;
    private String address;
    private String suburb;
    private String nationality;
    private String nativelanguage;
    private String favouritesport;
    private String favouritemovie;
    private String favouriteunit;
    private String currentjob;
    private String monashemail;
    private String password;
    private String subscriptiondateandtime;

    public StudentProfile(Integer studid, String firstname, String surname, String dob, String gender, String course, String studymode, String address, String suburb, String nationality, String nativelanguage, String favouritesport, String favouritemovie, String favouriteunit, String currentjob, String monashemail, String password, String subscriptiondateandtime) {
        this.studid = studid;
        this.firstname = firstname;
        this.surname = surname;
        this.dob = dob;
        this.gender = gender;
        this.course = course;
        this.studymode = studymode;
        this.address = address;
        this.suburb = suburb;
        this.nationality = nationality;
        this.nativelanguage = nativelanguage;
        this.favouritesport = favouritesport;
        this.favouritemovie = favouritemovie;
        this.favouriteunit = favouriteunit;
        this.currentjob = currentjob;
        this.monashemail = monashemail;
        this.password = password;
        this.subscriptiondateandtime = subscriptiondateandtime;
    }

    public Integer getStudid() {
        return studid;
    }

    public void setStudid(Integer studid) {
        this.studid = studid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStudymode() {
        return studymode;
    }

    public void setStudymode(String studymode) {
        this.studymode = studymode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNativelanguage() {
        return nativelanguage;
    }

    public void setNativelanguage(String nativelanguage) {
        this.nativelanguage = nativelanguage;
    }

    public String getFavouritesport() {
        return favouritesport;
    }

    public void setFavouritesport(String favouritesport) {
        this.favouritesport = favouritesport;
    }

    public String getFavouritemovie() {
        return favouritemovie;
    }

    public void setFavouritemovie(String favouritemovie) {
        this.favouritemovie = favouritemovie;
    }

    public String getFavouriteunit() {
        return favouriteunit;
    }

    public void setFavouriteunit(String favouriteunit) {
        this.favouriteunit = favouriteunit;
    }

    public String getCurrentjob() {
        return currentjob;
    }

    public void setCurrentjob(String currentjob) {
        this.currentjob = currentjob;
    }

    public String getMonashemail() {
        return monashemail;
    }

    public void setMonashemail(String monashemail) {
        this.monashemail = monashemail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubscriptiondateandtime() {
        return subscriptiondateandtime;
    }

    public void setSubscriptiondateandtime(String subscriptiondateandtime) {
        this.subscriptiondateandtime = subscriptiondateandtime;
    }
}
