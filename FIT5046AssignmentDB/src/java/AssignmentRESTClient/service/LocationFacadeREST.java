/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AssignmentRESTClient.service;

import AssignmentRESTClient.Location;
import AssignmentRESTClient.LocationANDFrequency;
import AssignmentRESTClient.StudentDistanceLocation;
import AssignmentRESTClient.StudentLocation;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Jiaqi Wang
 */
@Stateless
@Path("assignmentrestclient.location")
public class LocationFacadeREST extends AbstractFacade<Location> {

    @PersistenceContext(unitName = "FIT5046AssignmentDBPU")
    private EntityManager em;

    public LocationFacadeREST() {
        super(Location.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Location entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Location entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Location find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("findByLocationnameANDFirstnameANDSurname/{locationname}/{firstname}/{surname}")
    @Produces({"application/json"})
    public List<Location> findByLocationnameANDFirstnameANDSurname(@PathParam("locationname") String locationname, @PathParam("firstname") String firstname, @PathParam("surname") String surname) {
        TypedQuery<Location> q = em.createQuery("SELECT l FROM Location l WHERE l.locationname = :locationname AND l.studid.firstname = :firstname AND l.studid.surname = :surname", Location.class);
        q.setParameter("locationname", locationname);
        q.setParameter("firstname", firstname);
        q.setParameter("surname", surname);
        return q.getResultList();
    }

    @GET
    @Path("findByStudidANDStaringdateANDEndingdate/{studid}/{startingdate}/{endingdate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<LocationANDFrequency> findByStudidANDStaringdateANDEndingdate(@PathParam("studid") Integer studid, @PathParam("startingdate") String startingdate, @PathParam("endingdate") String endingdate) {
        TypedQuery q = em.createQuery("SELECT l.locationname, COUNT(l.locationname) FROM Location l WHERE l.studid.studid = :studid AND l.dateandtime > :startingdate AND l.dateandtime < :endingdate GROUP BY l.locationname", Object[].class);
        q.setParameter("studid", studid);
        q.setParameter("startingdate", startingdate);
        q.setParameter("endingdate", endingdate);
        List<Object[]> locationNamesANDFrequencies = q.getResultList();
        List<LocationANDFrequency> lflist = new ArrayList<LocationANDFrequency>();
        Integer number = -1;
        Integer size = locationNamesANDFrequencies.size();
        while (size != number + 1) {
            number = number + 1;
            String locname = locationNamesANDFrequencies.get(number)[0].toString();
            String freq = locationNamesANDFrequencies.get(number)[1].toString();
            LocationANDFrequency lf = new LocationANDFrequency(locname, freq);
            lflist.add(lf);
        }
        return lflist;
    }
 
    @GET
    @Path("findByStudidANDLatitudeANDLongitude/{studid}/{latitude}/{longitude}")
    @Produces({"application/json"})
    public List<StudentLocation> findByStudidANDLatitudeANDLongitude(@PathParam("studid") Integer studid, @PathParam("latitude") String latitude, @PathParam("longitude") String longitude) {
        TypedQuery q = em.createQuery("SELECT MAX(l.locationid), l.studid FROM Location l GROUP BY l.studid", Object[].class);
        List<Object[]> locationsANDStudentIds = q.getResultList();//list of (locationid & studid Array)
        List<StudentDistanceLocation> sdllist = new ArrayList<StudentDistanceLocation>();
        List<Double> disOrder = new ArrayList<>();
        List<StudentLocation> sllist = new ArrayList<StudentLocation>();
        for (Integer i = 0; i < locationsANDStudentIds.size(); i = i + 1) {
            Integer lcId = Integer.parseInt(locationsANDStudentIds.get(i)[0].toString());// convert locationid into int
            TypedQuery q2 = em.createQuery("SELECT l.latitude, l.longitude FROM Location l WHERE l.locationid = ?1 ", Object[].class);
            q2.setParameter(1, lcId);
            List<Object[]> q2ob = q2.getResultList();//list of (latitude & longitude Array)
            String lat = q2ob.get(0)[0].toString();//get latitude
            String longt = q2ob.get(0)[1].toString();//get longitude
            Double x2 = Double.parseDouble(lat);
            Double y2 = Double.parseDouble(longt);
            Double x1 = Double.parseDouble(latitude);
            Double y1 = Double.parseDouble(longitude);
            Double dist = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
            String distStr = dist.toString();
            disOrder.add(dist);

            String test123 = locationsANDStudentIds.get(i)[1].toString();
            String spidStr = "";
            for (int flag = 0; flag < test123.length(); flag++) {
                if (test123.charAt(flag) >= 48 && test123.charAt(flag) <= 57) {
                    spidStr = spidStr + test123.charAt(flag);
                }
            }
            Integer spid = Integer.parseInt(spidStr);// convert studentprofileid into int
            TypedQuery q3 = em.createQuery("SELECT s.firstname, s.surname FROM Studentprofile s WHERE s.studid = ?1", Object[].class);
            q3.setParameter(1, spid);
            List<Object[]> q3ob = q3.getResultList();//get list of (firstname & lastname Array)
            String fname = q3ob.get(0)[0].toString();//get firstname
            String sname = q3ob.get(0)[1].toString();//get surname
            StudentDistanceLocation newsdl = new StudentDistanceLocation(fname, sname, lat, longt, distStr);
            sdllist.add(newsdl);
        }

        Double temp; // 
        for (int i = 0; i < disOrder.size() - 1; i++) {
            for (int j = i + 1; j < disOrder.size(); j++) {
                if (disOrder.get(i) > disOrder.get(j)) {
                    temp = disOrder.get(i);
                    disOrder.set(i, disOrder.get(j));
                    disOrder.set(j, temp);
                }
            }
        }

        for (int i = 0; i < disOrder.size(); i++) {
            String newFirstname = "";
            String newSurname = "";
            String newLatitude = "";
            String newLongitude = "";
            for (int g = 0; g < sdllist.size(); g++) {
                if (sdllist.get(g).getDistance().equals(disOrder.get(i).toString())) {
                    newFirstname = sdllist.get(g).getFirstname();
                    newSurname = sdllist.get(g).getSurname();
                    newLatitude = sdllist.get(g).getLatitude();
                    newLongitude = sdllist.get(g).getLongitude();
                    StudentLocation newsl = new StudentLocation(newFirstname, newSurname, newLatitude, newLongitude);
                    sllist.add(newsl);
                }

            }

        }

        return sllist;
    }

    @GET
    @Path("findByLatitude/{latitude}")
    @Produces({"application/json"})
    public List<Location> findByLatitude(@PathParam("latitude") String latitude) {
        Query query = em.createNamedQuery("Location.findByLatitude");
        query.setParameter("latitude", latitude);
        return query.getResultList();
    }

    @GET
    @Path("findByLongitude/{longitude}")
    @Produces({"application/json"})
    public List<Location> findByLongitude(@PathParam("longitude") String longitude) {
        Query query = em.createNamedQuery("Location.findByLongitude");
        query.setParameter("longitude", longitude);
        return query.getResultList();
    }

    @GET
    @Path("findByDateandtime/{dateandtime}")
    @Produces({"application/json"})
    public List<Location> findByDateandtime(@PathParam("dateandtime") String dateandtime) {
        Query query = em.createNamedQuery("Location.findByDateandtime");
        query.setParameter("dateandtime", dateandtime);
        return query.getResultList();
    }

    @GET
    @Path("findByLocationname/{locationname}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Location> findByLocationname(@PathParam("locationname") String locationname) {
        Query query = em.createNamedQuery("Location.findByLocationname");
        query.setParameter("locationname", locationname);
        return query.getResultList();
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Location> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Location> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
