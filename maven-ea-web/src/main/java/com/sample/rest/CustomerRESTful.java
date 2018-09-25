/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.rest;

import com.sample.ejb.CustomerFacadeLocal;
import com.sample.ejb.ProductFacade;
import com.sample.ejb.ProductFacadeLocal;
import com.sample.model.Customer;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author esaenz
 */
@Path("customers")
@RequestScoped
public class CustomerRESTful {

    @Context
    private UriInfo context;

    @EJB
    private CustomerFacadeLocal service;

    /**
     * Creates a new instance of CusromerRESTful
     */
    public CustomerRESTful() {
    }

    /**
     * Retrieves representation of an instance of
 com.sample.rest.CustomerRESTful
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> list() {
        return service.findAll();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(@QueryParam("name") String name,
            @QueryParam("addressline1") String addressline1,
            @QueryParam("addressline2") String addressline2,
            @QueryParam("city") String city,
            @QueryParam("state") String state,
            @QueryParam("phone") String phone,
            @QueryParam("fax") String fax,
            @QueryParam("email") String email,
            @QueryParam("creditLimit")Integer creditLimit) {
        
    }

    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") Integer id) {
        service.remove(service.find(id));
    }

}
