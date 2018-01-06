/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AssignmentRESTClient;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jiaqi Wang
 */
@Entity
@Table(name = "STUDENTPROFILE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Studentprofile.findAll", query = "SELECT s FROM Studentprofile s")
    , @NamedQuery(name = "Studentprofile.findByStudid", query = "SELECT s FROM Studentprofile s WHERE s.studid = :studid")
    , @NamedQuery(name = "Studentprofile.findByFirstname", query = "SELECT s FROM Studentprofile s WHERE s.firstname = :firstname")
    , @NamedQuery(name = "Studentprofile.findBySurname", query = "SELECT s FROM Studentprofile s WHERE s.surname = :surname")
    , @NamedQuery(name = "Studentprofile.findByDob", query = "SELECT s FROM Studentprofile s WHERE s.dob = :dob")
    , @NamedQuery(name = "Studentprofile.findByGender", query = "SELECT s FROM Studentprofile s WHERE s.gender = :gender")
    , @NamedQuery(name = "Studentprofile.findByCourse", query = "SELECT s FROM Studentprofile s WHERE s.course = :course")
    , @NamedQuery(name = "Studentprofile.findByStudymode", query = "SELECT s FROM Studentprofile s WHERE s.studymode = :studymode")
    , @NamedQuery(name = "Studentprofile.findByAddress", query = "SELECT s FROM Studentprofile s WHERE s.address = :address")
    , @NamedQuery(name = "Studentprofile.findBySuburb", query = "SELECT s FROM Studentprofile s WHERE s.suburb = :suburb")
    , @NamedQuery(name = "Studentprofile.findByNationality", query = "SELECT s FROM Studentprofile s WHERE s.nationality = :nationality")
    , @NamedQuery(name = "Studentprofile.findByNativelanguage", query = "SELECT s FROM Studentprofile s WHERE s.nativelanguage = :nativelanguage")
    , @NamedQuery(name = "Studentprofile.findByFavouritesport", query = "SELECT s FROM Studentprofile s WHERE s.favouritesport = :favouritesport")
    , @NamedQuery(name = "Studentprofile.findByFavouritemovie", query = "SELECT s FROM Studentprofile s WHERE s.favouritemovie = :favouritemovie")
    , @NamedQuery(name = "Studentprofile.findByFavouriteunit", query = "SELECT s FROM Studentprofile s WHERE s.favouriteunit = :favouriteunit")
    , @NamedQuery(name = "Studentprofile.findByCurrentjob", query = "SELECT s FROM Studentprofile s WHERE s.currentjob = :currentjob")
    , @NamedQuery(name = "Studentprofile.findByMonashemail", query = "SELECT s FROM Studentprofile s WHERE UPPER(s.monashemail) = UPPER(:monashemail)")
    , @NamedQuery(name = "Studentprofile.findByPassword", query = "SELECT s FROM Studentprofile s WHERE s.password = :password")
    , @NamedQuery(name = "Studentprofile.findBySubscriptiondateandtime", query = "SELECT s FROM Studentprofile s WHERE s.subscriptiondateandtime = :subscriptiondateandtime")})
public class Studentprofile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "STUDID")
    private Integer studid;
    @Size(max = 50)
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Size(max = 50)
    @Column(name = "SURNAME")
    private String surname;
    @Size(max = 50)
    @Column(name = "DOB")
    private String dob;
    @Size(max = 8)
    @Column(name = "GENDER")
    private String gender;
    @Size(max = 60)
    @Column(name = "COURSE")
    private String course;
    @Size(max = 12)
    @Column(name = "STUDYMODE")
    private String studymode;
    @Size(max = 50)
    @Column(name = "ADDRESS")
    private String address;
    @Size(max = 25)
    @Column(name = "SUBURB")
    private String suburb;
    @Size(max = 25)
    @Column(name = "NATIONALITY")
    private String nationality;
    @Size(max = 30)
    @Column(name = "NATIVELANGUAGE")
    private String nativelanguage;
    @Size(max = 30)
    @Column(name = "FAVOURITESPORT")
    private String favouritesport;
    @Size(max = 50)
    @Column(name = "FAVOURITEMOVIE")
    private String favouritemovie;
    @Size(max = 8)
    @Column(name = "FAVOURITEUNIT")
    private String favouriteunit;
    @Size(max = 20)
    @Column(name = "CURRENTJOB")
    private String currentjob;
    @Size(max = 30)
    @Column(name = "MONASHEMAIL")
    private String monashemail;
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 50)
    @Column(name = "SUBSCRIPTIONDATEANDTIME")
    private String subscriptiondateandtime;
    @OneToMany(mappedBy = "studid")
    private Collection<Location> locationCollection;
    @OneToMany(mappedBy = "studentid2")
    private Collection<Friendship> friendshipCollection;
    @OneToMany(mappedBy = "studentid1")
    private Collection<Friendship> friendshipCollection1;

    public Studentprofile() {
    }

    public Studentprofile(Integer studid) {
        this.studid = studid;
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

    @XmlTransient
    public Collection<Location> getLocationCollection() {
        return locationCollection;
    }

    public void setLocationCollection(Collection<Location> locationCollection) {
        this.locationCollection = locationCollection;
    }

    @XmlTransient
    public Collection<Friendship> getFriendshipCollection() {
        return friendshipCollection;
    }

    public void setFriendshipCollection(Collection<Friendship> friendshipCollection) {
        this.friendshipCollection = friendshipCollection;
    }

    @XmlTransient
    public Collection<Friendship> getFriendshipCollection1() {
        return friendshipCollection1;
    }

    public void setFriendshipCollection1(Collection<Friendship> friendshipCollection1) {
        this.friendshipCollection1 = friendshipCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studid != null ? studid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Studentprofile)) {
            return false;
        }
        Studentprofile other = (Studentprofile) object;
        if ((this.studid == null && other.studid != null) || (this.studid != null && !this.studid.equals(other.studid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AssignmentRESTClient.Studentprofile[ studid=" + studid + " ]";
    }

}
