/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AssignmentRESTClient;

/**
 *
 * @author Jiaqi Wang
 */
public class StudentLocation {
    String firstname;
    String surname;
    String latitude;
    String longitude;
    
    public StudentLocation(){
        
    }
    
    public StudentLocation(String newFirstname, String newSurname, String newLatitude, String newLongitude){
        firstname = newFirstname;
        surname = newSurname;
        latitude = newLatitude;
        longitude = newLongitude;
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
