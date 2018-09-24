/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.test;

import com.sample.ejb.ProductFacade;
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
public class ProductRESTfulJUnitTest {
    private static Context context;
    private static ProductFacade service;
    private static List<Product> products = new ArrayList<Product>();
    
    public ProductRESTfulJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws NamingException {
    }
    
    @AfterClass
    public static void tearDownClass() {
                try {
            if (context != null) {
                context.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(ProductRESTfulJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    
    @Test
    public void delete() throws Exception {

    }

    
}
