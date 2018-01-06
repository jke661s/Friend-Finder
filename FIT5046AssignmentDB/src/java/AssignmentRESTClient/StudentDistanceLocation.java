/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AssignmentRESTClient;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jiaqi Wang
 */
@XmlRootElement
public class StudentDistanceLocation {
    
    String firstname;
    String surname;
    String latitude;
    String longitude;
    String distance;
    public StudentDistanceLocation(){
        
    }
    
    public StudentDistanceLocation(String newFirstname, String newSurname, String newLatitude, String newLongitude, String newdistance){
        firstname = newFirstname;
        surname = newSurname;
        latitude = newLatitude;
        longitude = newLongitude;
        distance = newdistance;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
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
    
}
