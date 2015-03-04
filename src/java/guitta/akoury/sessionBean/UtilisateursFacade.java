/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guitta.akoury.sessionBean;

import guitta.akoury.entities.Depenses;
import guitta.akoury.entities.Utilisateurs;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author guitta
 */
@Stateless
public class UtilisateursFacade extends AbstractFacade<Utilisateurs> {

    @PersistenceContext(unitName = "GestionDepensesFamilialesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtilisateursFacade() {
        super(Utilisateurs.class);
    }

    public Object byname(String name) {
        return em.createQuery(
                "SELECT u FROM Utilisateurs u WHERE u.username = :username")
                .setParameter("username", name).getSingleResult();

    }
    
    public List<Utilisateurs> findByUser(String name) {
        return em.createQuery(
                "SELECT u FROM Utilisateurs u WHERE u.username = :username")
                .setParameter("username", name).getResultList();

}
}