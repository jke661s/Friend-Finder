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
public class LocationANDFrequency {

    String locationName;
    String frequency;

    public LocationANDFrequency() {

    }
    
    public LocationANDFrequency(String locationname, String newfrequency) {
        locationName = locationname;
        frequency = newfrequency;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    
}
