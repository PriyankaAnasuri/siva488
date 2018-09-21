/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.ejb;

import com.sample.Constant;
import com.sample.model.ProductCode;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author esaenz
 */
@Stateless
public class ProductCodeFacade extends AbstractFacade<ProductCode> implements ProductCodeFacadeLocal {

    @PersistenceContext(unitName = Constant.PERSIST_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductCodeFacade() {
        super(ProductCode.class);
    }
    
}
