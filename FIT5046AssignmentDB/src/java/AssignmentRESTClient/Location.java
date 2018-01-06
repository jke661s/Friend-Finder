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
@Table(name = "LOCATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Location.findAll", query = "SELECT l FROM Location l")
    , @NamedQuery(name = "Location.findByLocationid", query = "SELECT l FROM Location l WHERE l.locationid = :locationid")
    , @NamedQuery(name = "Location.findByLatitude", query = "SELECT l FROM Location l WHERE l.latitude = :latitude")
    , @NamedQuery(name = "Location.findByLongitude", query = "SELECT l FROM Location l WHERE l.longitude = :longitude")
    , @NamedQuery(name = "Location.findByDateandtime", query = "SELECT l FROM Location l WHERE l.dateandtime = :dateandtime")
    , @NamedQuery(name = "Location.findByLocationname", query = "SELECT l FROM Location l WHERE l.locationname = :locationname")})
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOCATIONID")
    private Integer locationid;
    @Size(max = 100)
    @Column(name = "LATITUDE")
    private String latitude;
    @Size(max = 100)
    @Column(name = "LONGITUDE")
    private String longitude;
    @Size(max = 100)
    @Column(name = "DATEANDTIME")
    private String dateandtime;
    @Size(max = 50)
    @Column(name = "LOCATIONNAME")
    private String locationname;
    @JoinColumn(name = "STUDID", referencedColumnName = "STUDID")
    @ManyToOne
    private Studentprofile studid;

    public Location() {
    }

    public Location(Integer locationid) {
        this.locationid = locationid;
    }

    public Integer getLocationid() {
        return locationid;
    }

    public void setLocationid(Integer locationid) {
        this.locationid = locationid;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }

    public String getLocationname() {
        return locationname;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }

    public Studentprofile getStudid() {
        return studid;
    }

    public void setStudid(Studentprofile studid) {
        this.studid = studid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locationid != null ? locationid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        if ((this.locationid == null && other.locationid != null) || (this.locationid != null && !this.locationid.equals(other.locationid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AssignmentRESTClient.Location[ locationid=" + locationid + " ]";
    }
    
}
