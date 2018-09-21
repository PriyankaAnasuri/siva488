/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.controller.jpa;

import com.sample.controller.jpa.exceptions.NonexistentEntityException;
import com.sample.controller.jpa.exceptions.PreexistingEntityException;
import com.sample.controller.jpa.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sample.model.Customer;
import com.sample.model.Product;
import com.sample.model.PurchaseOrder;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author esaenz
 */
public class PurchaseOrderJpaController implements Serializable {

    public PurchaseOrderJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PurchaseOrder purchaseOrder) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Customer customerId = purchaseOrder.getCustomerId();
            if (customerId != null) {
                customerId = em.getReference(customerId.getClass(), customerId.getCustomerId());
                purchaseOrder.setCustomerId(customerId);
            }
            Product productId = purchaseOrder.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getProductId());
                purchaseOrder.setProductId(productId);
            }
            em.persist(purchaseOrder);
            if (customerId != null) {
                customerId.getPurchaseOrderCollection().add(purchaseOrder);
                customerId = em.merge(customerId);
            }
            if (productId != null) {
                productId.getPurchaseOrderCollection().add(purchaseOrder);
                productId = em.merge(productId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPurchaseOrder(purchaseOrder.getOrderNum()) != null) {
                throw new PreexistingEntityException("PurchaseOrder " + purchaseOrder + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PurchaseOrder purchaseOrder) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PurchaseOrder persistentPurchaseOrder = em.find(PurchaseOrder.class, purchaseOrder.getOrderNum());
            Customer customerIdOld = persistentPurchaseOrder.getCustomerId();
            Customer customerIdNew = purchaseOrder.getCustomerId();
            Product productIdOld = persistentPurchaseOrder.getProductId();
            Product productIdNew = purchaseOrder.getProductId();
            if (customerIdNew != null) {
                customerIdNew = em.getReference(customerIdNew.getClass(), customerIdNew.getCustomerId());
                purchaseOrder.setCustomerId(customerIdNew);
            }
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getProductId());
                purchaseOrder.setProductId(productIdNew);
            }
            purchaseOrder = em.merge(purchaseOrder);
            if (customerIdOld != null && !customerIdOld.equals(customerIdNew)) {
                customerIdOld.getPurchaseOrderCollection().remove(purchaseOrder);
                customerIdOld = em.merge(customerIdOld);
            }
            if (customerIdNew != null && !customerIdNew.equals(customerIdOld)) {
                customerIdNew.getPurchaseOrderCollection().add(purchaseOrder);
                customerIdNew = em.merge(customerIdNew);
            }
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getPurchaseOrderCollection().remove(purchaseOrder);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getPurchaseOrderCollection().add(purchaseOrder);
                productIdNew = em.merge(productIdNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = purchaseOrder.getOrderNum();
                if (findPurchaseOrder(id) == null) {
                    throw new NonexistentEntityException("The purchaseOrder with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PurchaseOrder purchaseOrder;
            try {
                purchaseOrder = em.getReference(PurchaseOrder.class, id);
                purchaseOrder.getOrderNum();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The purchaseOrder with id " + id + " no longer exists.", enfe);
            }
            Customer customerId = purchaseOrder.getCustomerId();
            if (customerId != null) {
                customerId.getPurchaseOrderCollection().remove(purchaseOrder);
                customerId = em.merge(customerId);
            }
            Product productId = purchaseOrder.getProductId();
            if (productId != null) {
                productId.getPurchaseOrderCollection().remove(purchaseOrder);
                productId = em.merge(productId);
            }
            em.remove(purchaseOrder);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PurchaseOrder> findPurchaseOrderEntities() {
        return findPurchaseOrderEntities(true, -1, -1);
    }

    public List<PurchaseOrder> findPurchaseOrderEntities(int maxResults, int firstResult) {
        return findPurchaseOrderEntities(false, maxResults, firstResult);
    }

    private List<PurchaseOrder> findPurchaseOrderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PurchaseOrder.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PurchaseOrder findPurchaseOrder(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PurchaseOrder.class, id);
        } finally {
            em.close();
        }
    }

    public int getPurchaseOrderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PurchaseOrder> rt = cq.from(PurchaseOrder.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
