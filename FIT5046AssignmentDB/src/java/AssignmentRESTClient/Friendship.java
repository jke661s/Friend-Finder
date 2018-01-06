/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AssignmentRESTClient;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jiaqi Wang
 */
@Entity
@Table(name = "FRIENDSHIP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Friendship.findAll", query = "SELECT f FROM Friendship f")
    , @NamedQuery(name = "Friendship.findByFriendshipid", query = "SELECT f FROM Friendship f WHERE f.friendshipid = :friendshipid")
    , @NamedQuery(name = "Friendship.findByStartingdate", query = "SELECT f FROM Friendship f WHERE f.startingdate = :startingdate")
    , @NamedQuery(name = "Friendship.findByFirstname1ANDStartingdate", query = "SELECT f FROM Friendship f JOIN Studentprofile s WHERE s.firstname = :firstname AND f.startingdate = :startingdate")
    , @NamedQuery(name = "Friendship.findByEndingdate", query = "SELECT f FROM Friendship f WHERE f.endingdate = :endingdate")})
public class Friendship implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FRIENDSHIPID")
    private Integer friendshipid;
    @Size(max = 20)
    @Column(name = "STARTINGDATE")
    private String startingdate;
    @Size(max = 20)
    @Column(name = "ENDINGDATE")
    private String endingdate;
    @JoinColumn(name = "STUDENTID2", referencedColumnName = "STUDID")
    @ManyToOne
    private Studentprofile studentid2;
    @JoinColumn(name = "STUDENTID1", referencedColumnName = "STUDID")
    @ManyToOne
    private Studentprofile studentid1;

    public Friendship() {
    }

    public Friendship(Integer friendshipid) {
        this.friendshipid = friendshipid;
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

    public Studentprofile getStudentid2() {
        return studentid2;
    }

    public void setStudentid2(Studentprofile studentid2) {
        this.studentid2 = studentid2;
    }

    public Studentprofile getStudentid1() {
        return studentid1;
    }

    public void setStudentid1(Studentprofile studentid1) {
        this.studentid1 = studentid1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (friendshipid != null ? friendshipid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Friendship)) {
            return false;
        }
        Friendship other = (Friendship) object;
        if ((this.friendshipid == null && other.friendshipid != null) || (this.friendshipid != null && !this.friendshipid.equals(other.friendshipid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AssignmentRESTClient.Friendship[ friendshipid=" + friendshipid + " ]";
    }
    
}
