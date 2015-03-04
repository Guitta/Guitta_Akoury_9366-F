package guitta.akoury.jsfClasses;

import guitta.akoury.entities.Utilisateurs;
import guitta.akoury.jsfClasses.util.JsfUtil;
import guitta.akoury.jsfClasses.util.JsfUtil.PersistAction;
import guitta.akoury.sessionBean.UtilisateursFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "utilisateursController")
@SessionScoped
public class UtilisateursController implements Serializable {

    @EJB
    private guitta.akoury.sessionBean.UtilisateursFacade ejbFacade;
    private List<Utilisateurs> items = null;
    private Utilisateurs selected;
    private Utilisateurs loggedUser;
    private List<Utilisateurs> itemsLoggedUser = null;

    public UtilisateursController() {
    }

    public Utilisateurs getSelected() {
        return selected;
    }

    public void setSelected(Utilisateurs selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UtilisateursFacade getFacade() {
        return ejbFacade;
    }

    public Utilisateurs prepareCreate() {
        selected = new Utilisateurs();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UtilisateursCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UtilisateursUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UtilisateursDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Utilisateurs> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public Utilisateurs getLoggedUser() {

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ectx = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
        String s = request.getRemoteUser();
        HttpSession session = request.getSession(true);

        Utilisateurs user = (Utilisateurs) getFacade().byname(s);
    
        String name = user.getUsername();
    
        session.setAttribute("MySessionVariable", name);
    

        loggedUser = getFacade().find(name);
    
        return loggedUser;

    }

    public List<Utilisateurs> getItemsLoggedUser() {
            if (itemsLoggedUser == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext ectx = context.getExternalContext();
            HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
            String s = request.getRemoteUser();
            HttpSession session = request.getSession(true);

            itemsLoggedUser = getFacade().findByUser(s);
        }
        
        return itemsLoggedUser;
    }
    
    

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<Utilisateurs> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Utilisateurs> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Utilisateurs.class)
    public static class UtilisateursControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UtilisateursController controller = (UtilisateursController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "utilisateursController");
            return controller.getFacade().find(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Utilisateurs) {
                Utilisateurs o = (Utilisateurs) object;
                return getStringKey(o.getUsername());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Utilisateurs.class.getName()});
                return null;
            }
        }

    }

}
