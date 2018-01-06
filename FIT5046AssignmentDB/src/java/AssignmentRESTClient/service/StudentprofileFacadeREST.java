/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AssignmentRESTClient.service;

import AssignmentRESTClient.Studentprofile;
import AssignmentRESTClient.UnitsANDFrequency;
import java.lang.reflect.Method;
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
@Path("assignmentrestclient.studentprofile")
public class StudentprofileFacadeREST extends AbstractFacade<Studentprofile> {

    @PersistenceContext(unitName = "FIT5046AssignmentDBPU")
    private EntityManager em;

    public StudentprofileFacadeREST() {
        super(Studentprofile.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Studentprofile entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Studentprofile entity) {
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
    public Studentprofile find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("findByFirstname/{firstname}")
    @Produces({"application/json"})
    public List<Studentprofile> findByFirstname(@PathParam("firstname") String firstname) {
        Query query = em.createNamedQuery("Studentprofile.findByFirstname");
        query.setParameter("firstname", firstname);
        return query.getResultList();
    }

    @GET
    @Path("findBySurname/{surname}")
    @Produces({"application/json"})
    public List<Studentprofile> findBySurname(@PathParam("surname") String surname) {
        Query query = em.createNamedQuery("Studentprofile.findBySurname");
        query.setParameter("surname", surname);
        return query.getResultList();
    }

    @GET
    @Path("findByDob/{dob}")
    @Produces({"application/json"})
    public List<Studentprofile> findByDob(@PathParam("dob") String dob) {
        Query query = em.createNamedQuery("Studentprofile.findByDob");
        query.setParameter("dob", dob);
        return query.getResultList();
    }

    @GET
    @Path("findByGender/{gender}")
    @Produces({"application/json"})
    public List<Studentprofile> findByGender(@PathParam("gender") String gender) {
        Query query = em.createNamedQuery("Studentprofile.findByGender");
        query.setParameter("gender", gender);
        return query.getResultList();
    }

    @GET
    @Path("findByCourse/{course}")
    @Produces({"application/json"})
    public List<Studentprofile> findByCourse(@PathParam("course") String course) {
        Query query = em.createNamedQuery("Studentprofile.findByCourse");
        query.setParameter("course", course);
        return query.getResultList();
    }

    @GET
    @Path("findByStudymode/{studymode}")
    @Produces({"application/json"})
    public List<Studentprofile> findByStudymode(@PathParam("studymode") String studymode) {
        Query query = em.createNamedQuery("Studentprofile.findByStudymode");
        query.setParameter("studymode", studymode);
        return query.getResultList();
    }

    @GET
    @Path("findByStudidAND3Keywords/{studid}/{keyword1}/{keyword2}/{keyword3}")
    @Produces({"application/json"})
    public List<Studentprofile> findByStudidAND3Keywords(@PathParam("studid") int studid, @PathParam("keyword1") String keyword1, @PathParam("keyword2") String keyword2, @PathParam("keyword3") String keyword3) {
        TypedQuery<Studentprofile> q = em.createQuery("SELECT s FROM Studentprofile s WHERE s.studid = :studid", Studentprofile.class);
        q.setParameter("studid", studid);
        Studentprofile sp = q.getResultList().get(0);
        String[] keywordList = {keyword1, keyword2, keyword3};
        String firstname;
        String surname;
        String dob;
        String gender;
        String course;
        String studymode;
        String address;
        String suburb;
        String nationality;
        String nativeLanguage;
        String favouriteSport;
        String favouriteMovie;
        String favouriteUnit;
        String currentJob;
        String monashEmail;
        String password;
        String subscriptiondateandtime;
        List<String> keywordResult = new ArrayList<>();
        for (String str : keywordList) {
            switch (str) {
                case "firstname":
                    firstname = sp.getFirstname();
                    keywordResult.add(firstname);
                    break;
                case "surname":
                    surname = sp.getSurname();
                    keywordResult.add(surname);
                    break;
                case "dob":
                    dob = sp.getDob();
                    keywordResult.add(dob);
                    break;
                case "gender":
                    gender = sp.getGender();
                    keywordResult.add(gender);
                    break;
                case "course":
                    course = sp.getCourse();
                    keywordResult.add(course);
                    break;
                case "studymode":
                    studymode = sp.getStudymode();
                    keywordResult.add(studymode);
                    break;
                case "address":
                    address = sp.getAddress();
                    keywordResult.add(address);
                    break;
                case "suburb":
                    suburb = sp.getSuburb();
                    keywordResult.add(suburb);
                    break;
                case "nationality":
                    nationality = sp.getNationality();
                    keywordResult.add(nationality);
                    break;
                case "nativeLanguage":
                    nativeLanguage = sp.getNativelanguage();
                    keywordResult.add(nativeLanguage);
                    break;
                case "favouriteSport":
                    favouriteSport = sp.getFavouritesport();
                    keywordResult.add(favouriteSport);
                    break;
                case "favouriteMovie":
                    favouriteMovie = sp.getFavouritemovie();
                    keywordResult.add(favouriteMovie);
                    break;
                case "favouriteUnit":
                    favouriteUnit = sp.getFavouriteunit();
                    keywordResult.add(favouriteUnit);
                    break;
                case "currentJob":
                    currentJob = sp.getCurrentjob();
                    keywordResult.add(currentJob);
                    break;
                case "monashEmail":
                    monashEmail = sp.getMonashemail();
                    keywordResult.add(monashEmail);
                    break;
                case "password":
                    password = sp.getPassword();
                    keywordResult.add(password);
                    break;
                case "subscriptiondateandtime":
                    subscriptiondateandtime = sp.getSubscriptiondateandtime();
                    keywordResult.add(subscriptiondateandtime);
                    break;
            }
        }

        TypedQuery<Studentprofile> q1 = em.createQuery("SELECT s FROM Studentprofile s WHERE " + "s." + keyword1 + " = ?2 AND " + "s." + keyword2 + " = ?4 AND " + "s." + keyword3 + " = ?6", Studentprofile.class);

        q1.setParameter(2, keywordResult.get(0));
        q1.setParameter(4, keywordResult.get(1));
        q1.setParameter(6, keywordResult.get(2));
        List<Studentprofile> result = q1.getResultList();
        return result;
    }
    
    @GET
    @Path("findByAnyKeywords/{keywordList}/{keywordResult}")
    @Produces({"application/json"})
    public List<Studentprofile> findByAnyKeywords (@PathParam("keywordList") String keywordList, @PathParam("keywordResult") String keywordResult){
        String[] keywordListArray = keywordList.split(",");
        String select = "SELECT s FROM Studentprofile s WHERE " + "s." + keywordListArray[0] + " = ?1";

        int mm = 1;
        while (mm < keywordListArray.length) {
            select = select + " AND " + "s." + keywordListArray[mm] + " = ?" + (mm + 1);
            mm = mm + 1;
        }
        TypedQuery tq = em.createQuery(select, Studentprofile.class);
        String[] keywordResultArray = keywordResult.split(",");
        int nn = 0;
        while (nn < keywordListArray.length) {
            tq.setParameter(nn + 1, keywordResultArray[nn]);
            nn = nn + 1;
        }

        List<Studentprofile> result = tq.getResultList();
        return result;
    }
    
    
    

    @GET
    @Path("findByStudidANDAnyKeywords/{studid}/{keywords}")
    @Produces({"application/json"})
    public List<Studentprofile> findByStudidANDAnyKeywords(@PathParam("studid") int studid, @PathParam("keywords") String keywords) {
        TypedQuery<Studentprofile> q = em.createQuery("SELECT s FROM Studentprofile s WHERE s.studid = :studid", Studentprofile.class);
        q.setParameter("studid", studid);
        Studentprofile sp = q.getResultList().get(0);
        String[] keywordList = keywords.split(",");
        String firstname;
        String surname;
        String dob;
        String gender;
        String course;
        String studymode;
        String address;
        String suburb;
        String nationality;
        String nativeLanguage;
        String favouriteSport;
        String favouriteMovie;
        String favouriteUnit;
        String currentJob;
        String monashEmail;
        String password;
        String subscriptiondateandtime;
        List<String> keywordResult = new ArrayList<>();
        for (String str : keywordList) {
            switch (str) {
                case "firstname":
                    firstname = sp.getFirstname();
                    keywordResult.add(firstname);
                    break;
                case "surname":
                    surname = sp.getSurname();
                    keywordResult.add(surname);
                    break;
                case "dob":
                    dob = sp.getDob();
                    keywordResult.add(dob);
                    break;
                case "gender":
                    gender = sp.getGender();
                    keywordResult.add(gender);
                    break;
                case "course":
                    course = sp.getCourse();
                    keywordResult.add(course);
                    break;
                case "studymode":
                    studymode = sp.getStudymode();
                    keywordResult.add(studymode);
                    break;
                case "address":
                    address = sp.getAddress();
                    keywordResult.add(address);
                    break;
                case "suburb":
                    suburb = sp.getSuburb();
                    keywordResult.add(suburb);
                    break;
                case "nationality":
                    nationality = sp.getNationality();
                    keywordResult.add(nationality);
                    break;
                case "nativelanguage":
                    nativeLanguage = sp.getNativelanguage();
                    keywordResult.add(nativeLanguage);
                    break;
                case "favouritesport":
                    favouriteSport = sp.getFavouritesport();
                    keywordResult.add(favouriteSport);
                    break;
                case "favouritemovie":
                    favouriteMovie = sp.getFavouritemovie();
                    keywordResult.add(favouriteMovie);
                    break;
                case "favouriteunit":
                    favouriteUnit = sp.getFavouriteunit();
                    keywordResult.add(favouriteUnit);
                    break;
                case "currentjob":
                    currentJob = sp.getCurrentjob();
                    keywordResult.add(currentJob);
                    break;
                case "monashemail":
                    monashEmail = sp.getMonashemail();
                    keywordResult.add(monashEmail);
                    break;
                case "password":
                    password = sp.getPassword();
                    keywordResult.add(password);
                    break;
                case "subscriptiondateandtime":
                    subscriptiondateandtime = sp.getSubscriptiondateandtime();
                    keywordResult.add(subscriptiondateandtime);
                    break;
            }
        }
        String select = "SELECT s FROM Studentprofile s WHERE " + "s." + keywordList[0] + " = ?1";

        int mm = 1;
        while (mm < keywordList.length) {
            select = select + " AND " + "s." + keywordList[mm] + " = ?" + (mm + 1);
            mm = mm + 1;
        }
        TypedQuery tq = em.createQuery(select, Studentprofile.class);

        int nn = 0;
        while (nn < keywordList.length) {
            tq.setParameter(nn + 1, keywordResult.get(nn));
            nn = nn + 1;
        }

        List<Studentprofile> result = tq.getResultList();
        return result;
    }

    @GET
    @Path("GetFavouriteUnitANDFrequency")
    @Produces({"application/json"})
    public List<UnitsANDFrequency> GetFavouriteUnitANDFrequency(){
        TypedQuery q = em.createQuery("SELECT s.favouriteunit, COUNT(s.favouriteunit) FROM Studentprofile s GROUP BY s.favouriteunit", Object[].class);
        List<Object[]> uf = q.getResultList();
        List<UnitsANDFrequency> uflist = new ArrayList<>();
        for(int i = 0; i < uf.size (); i ++){
            String newUnit = uf.get(i)[0].toString();
            String frequency = uf.get(i)[1].toString();
            UnitsANDFrequency uf1 = new UnitsANDFrequency(newUnit, frequency);
            uflist.add(uf1);
        }
        return uflist;
    }
    
    @GET
    @Path("findByAddress/{address}")
    @Produces({"application/json"})
    public List<Studentprofile> findByAddress(@PathParam("address") String address) {
        Query query = em.createNamedQuery("Studentprofile.findByAddress");
        query.setParameter("address", address);
        return query.getResultList();
    }

    @GET
    @Path("findBySubscriptiondateandtime/{subscriptiondateandtime}")
    @Produces({"application/json"})
    public List<Studentprofile> findBySubscriptiondateandtime(@PathParam("subscriptiondateandtime") String subscriptiondateandtime) {
        Query query = em.createNamedQuery("Studentprofile.findBySubscriptiondateandtime");
        query.setParameter("subscriptiondateandtime", subscriptiondateandtime);
        return query.getResultList();
    }

    @GET
    @Path("findBySuburb/{suburb}")
    @Produces({"application/json"})
    public List<Studentprofile> findBySuburb(@PathParam("suburb") String suburb) {
        Query query = em.createNamedQuery("Studentprofile.findBySuburb");
        query.setParameter("suburb", suburb);
        return query.getResultList();
    }

    @GET
    @Path("findByNationality/{nationality}")
    @Produces({"application/json"})
    public List<Studentprofile> findByNationality(@PathParam("nationality") String nationality) {
        Query query = em.createNamedQuery("Studentprofile.findByNationality");
        query.setParameter("nationality", nationality);
        return query.getResultList();
    }

    @GET
    @Path("findByNativelanguage/{nativelanguage}")
    @Produces({"application/json"})
    public List<Studentprofile> findByNativelanguage(@PathParam("nativelanguage") String nativelanguage) {
        Query query = em.createNamedQuery("Studentprofile.findByNativelanguage");
        query.setParameter("nativelanguage", nativelanguage);
        return query.getResultList();
    }

    @GET
    @Path("findByFavouritesport/{favouritesport}")
    @Produces({"application/json"})
    public List<Studentprofile> findByFavouritesport(@PathParam("favouritesport") String favouritesport) {
        Query query = em.createNamedQuery("Studentprofile.findByFavouritesport");
        query.setParameter("favouritesport", favouritesport);
        return query.getResultList();
    }

    @GET
    @Path("findByFavouritemovie/{favouritemovie}")
    @Produces({"application/json"})
    public List<Studentprofile> findByFavouritemovie(@PathParam("favouritemovie") String favouritemovie) {
        Query query = em.createNamedQuery("Studentprofile.findByFavouritemovie");
        query.setParameter("favouritemovie", favouritemovie);
        return query.getResultList();
    }

    @GET
    @Path("findByFavouriteunit/{favouriteunit}")
    @Produces({"application/json"})
    public List<Studentprofile> findByFavouriteunit(@PathParam("favouriteunit") String favouriteunit) {
        Query query = em.createNamedQuery("Studentprofile.findByFavouriteunit");
        query.setParameter("favouriteunit", favouriteunit);
        return query.getResultList();
    }

    @GET
    @Path("findByCurrentjob/{currentjob}")
    @Produces({"application/json"})
    public List<Studentprofile> findByCurrentjob(@PathParam("currentjob") String currentjob) {
        Query query = em.createNamedQuery("Studentprofile.findByCurrentjob");
        query.setParameter("currentjob", currentjob);
        return query.getResultList();
    }

    @GET
    @Path("findByMonashemail/{monashemail}")
    @Produces({"application/json"})
    public List<Studentprofile> findByMonashemail(@PathParam("monashemail") String monashemail) {
        Query query = em.createNamedQuery("Studentprofile.findByMonashemail");
        query.setParameter("monashemail", monashemail);
        return query.getResultList();
    }

    @GET
    @Path("findByPassword/{password}")
    @Produces({"application/json"})
    public List<Studentprofile> findByPassword(@PathParam("password") String password) {
        Query query = em.createNamedQuery("Studentprofile.findByPassword");
        query.setParameter("password", password);
        return query.getResultList();
    }

    @GET
    @Path("findByFirstnameANDSurname/{firstname}/{surname}")
    @Produces({"application/json"})
    public List<Studentprofile> findByFirstnameANDSurname(@PathParam("firstname") String firstname, @PathParam("surname") String surname) {
        TypedQuery<Studentprofile> q = em.createQuery("SELECT s FROM Studentprofile s WHERE UPPER(s.firstname) = UPPER(:firstname) AND s.surname=:surname", Studentprofile.class);
        q.setParameter("firstname", firstname);
        q.setParameter("surname", surname);
        return q.getResultList();
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Studentprofile> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Studentprofile> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
