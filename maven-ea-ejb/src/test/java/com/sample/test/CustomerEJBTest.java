/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.test;

import com.sample.ejb.CustomerFacade;
import com.sample.ejb.ProductFacade;
import com.sample.ejb.ProductFacadeLocal;
import com.sample.model.Customer;
import com.sample.model.Product;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author esaenz
 */
public class CustomerEJBTest {
    private static Context context;
    private static ProductFacadeLocal service;
    private static List<Customer> customers = new ArrayList<Customer>();
    
    
    public CustomerEJBTest() {
        try {
            context = EJBContainer.createEJBContainer().getContext();
            service = (ProductFacadeLocal) context.lookup("java:global/classes/ProductFacadeLocal");
        } catch (NamingException ex) {
            Logger.getLogger(CustomerEJBTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void create() throws Exception {

    }
    
    @Test
    public void update() throws Exception {

    }
    
    @Test
    public void find() throws Exception {

    }
    
    @Test
    public void delete() throws Exception {

    }
    
    @Test
    public void list() throws Exception {

    }
}
