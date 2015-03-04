/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guitta.akoury.sessionBean;

import guitta.akoury.entities.Catdep;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author guitta
 */
@Stateless
public class CatdepFacade extends AbstractFacade<Catdep> {
    @PersistenceContext(unitName = "GestionDepensesFamilialesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CatdepFacade() {
        super(Catdep.class);
    }
    
}
