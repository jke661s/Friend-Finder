/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AssignmentRESTClient.service;

import AssignmentRESTClient.Friendship;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
@Path("assignmentrestclient.friendship")
public class FriendshipFacadeREST extends AbstractFacade<Friendship> {

    @PersistenceContext(unitName = "FIT5046AssignmentDBPU")
    private EntityManager em;

    public FriendshipFacadeREST() {
        super(Friendship.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Friendship entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Friendship entity) {
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
    public Friendship find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Path("findByFirstname1ANDStartingdate/{firstname}/{startingdate}")
    @Produces({"application/json"})
    public List<Friendship> findByFirstname1ANDStartingdate(@PathParam("firstname") String firstname, @PathParam("startingdate") String startingdate){
    Query query = em.createNamedQuery("Friendship.findByFirstname1ANDStartingdate");
    query.setParameter("firstname", firstname);
    query.setParameter("startingdate", startingdate);
    return query.getResultList();
    }


    @GET
    @Path("findByStartingdate/{startingdate}")
    @Produces({"application/json"})
    public List<Friendship> findByStartingdate(@PathParam("startingdate") String startingdate) {
        Query query = em.createNamedQuery("Friendship.findByStartingdate");
        query.setParameter("startingdate", startingdate);
        return query.getResultList();
    }

    @GET
    @Path("findByEndingdate/{endingdate}")
    @Produces({"application/json"})
    public List<Friendship> findByEndingdate(@PathParam("endingdate") String endingdate) {
        Query query = em.createNamedQuery("Friendship.findByEndingdate");
        query.setParameter("endingdate", endingdate);
        return query.getResultList();
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Friendship> findAll() {
        return super.findAll();
    }
   

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Friendship> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
