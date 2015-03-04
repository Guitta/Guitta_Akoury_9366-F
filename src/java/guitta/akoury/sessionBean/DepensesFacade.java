/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guitta.akoury.sessionBean;

import guitta.akoury.entities.Depenses;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author guitta
 */
@Stateless
public class DepensesFacade extends AbstractFacade<Depenses> {
    @PersistenceContext(unitName = "GestionDepensesFamilialesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepensesFacade() {
        super(Depenses.class);
    }
    
    public List<Depenses> findByName(String name) {
        return em.createQuery(
                "SELECT d FROM Depenses d WHERE d.username = :username")
                .setParameter("username", name).getResultList();

    }
    
    public Object byname(String name) {
        return em.createQuery(
                "SELECT u FROM Utilisateurs u WHERE u.username = :username")
                .setParameter("username", name).getSingleResult();

    }
    
}
