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
public class UnitsANDFrequency {

    String unit;
    String Frequency;

    public UnitsANDFrequency(String unit, String Frequency) {
        this.unit = unit;
        this.Frequency = Frequency;
    }

    public UnitsANDFrequency() {
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFrequency() {
        return Frequency;
    }

    public void setFrequency(String Frequency) {
        this.Frequency = Frequency;
    }

}
