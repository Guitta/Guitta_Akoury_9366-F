/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guitta.akoury.sessionBean;

import guitta.akoury.entities.Groupe;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author guitta
 */
@Stateless
public class GroupeFacade extends AbstractFacade<Groupe> {
    @PersistenceContext(unitName = "GestionDepensesFamilialesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GroupeFacade() {
        super(Groupe.class);
    }
    
}
