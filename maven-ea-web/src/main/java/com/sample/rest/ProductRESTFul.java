/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.rest;

import com.sample.ejb.ProductFacadeLocal;
import com.sample.model.Product;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
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
@Path("products")
@RequestScoped
public class ProductRESTFul {

    @Context
    private UriInfo context;
    
    @EJB
    private ProductFacadeLocal service;

    /**
     * Creates a new instance of ProductRESTFul
     */
    public ProductRESTFul() {
    }

    /**
     * Retrieves representation of an instance of com.sample.rest.ProductRESTFul
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> list() {
        return service.findAll();
    }

    
    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(@QueryParam("purchaseCost") BigDecimal purchaseCost,
            @QueryParam("quantityOnHand") Integer quantityOnHand,
            @QueryParam("markup") BigDecimal markup,
            @QueryParam("available") String available,
            @QueryParam("description") String description,
            @QueryParam("manufacturerId") Integer manufacturerId,
            @QueryParam("prodCode") String prodCode) {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    
    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") Integer id) {
        service.remove(service.find(id));
    }
    
}
