package com.example.jiaqiwang.monashfriendfinder.DataStore;



/**
 * Created by Jiaqi Wang on 5/4/2017.
 */

public class Friendship {
    private Integer friendshipid;
    private String startingdate;
    private String endingdate;
    private StudentProfile studentid2;
    private StudentProfile studentid1;

    public Friendship(Integer friendshipid, String startingdate, String endingdate, StudentProfile studentid2, StudentProfile studentid1) {
        this.friendshipid = friendshipid;
        this.startingdate = startingdate;
        this.endingdate = endingdate;
        this.studentid2 = studentid2;
        this.studentid1 = studentid1;
    }

    public Friendship(Integer friendshipid, String startingdate, StudentProfile studentid2, StudentProfile studentid1) {
        this.friendshipid = friendshipid;
        this.startingdate = startingdate;
        this.studentid2 = studentid2;
        this.studentid1 = studentid1;
    }

    public Integer getFriendshipid() {
        return friendshipid;
    }

    public void setFriendshipid(Integer friendshipid) {
        this.friendshipid = friendshipid;
    }

    public String getStartingdate() {
        return startingdate;
    }

    public void setStartingdate(String startingdate) {
        this.startingdate = startingdate;
    }

    public String getEndingdate() {
        return endingdate;
    }

    public void setEndingdate(String endingdate) {
        this.endingdate = endingdate;
    }

    public StudentProfile getStudentid2() {
        return studentid2;
    }

    public void setStudentid2(StudentProfile studentid2) {
        this.studentid2 = studentid2;
    }

    public StudentProfile getStudentid1() {
        return studentid1;
    }

    public void setStudentid1(StudentProfile studentid1) {
        this.studentid1 = studentid1;
    }
}
