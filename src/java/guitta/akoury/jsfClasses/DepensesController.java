package guitta.akoury.jsfClasses;

import guitta.akoury.entities.Depenses;
import guitta.akoury.entities.Utilisateurs;
import guitta.akoury.jsfClasses.util.JsfUtil;
import guitta.akoury.jsfClasses.util.JsfUtil.PersistAction;
import guitta.akoury.sessionBean.DepensesFacade;

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

@ManagedBean(name = "depensesController")
@SessionScoped
public class DepensesController implements Serializable {

    @EJB
    private guitta.akoury.sessionBean.DepensesFacade ejbFacade;
    private List<Depenses> items = null;
    private Depenses selected;
    private List<Depenses> logged = null;
    private String logUser;
    
    public DepensesController() {
    }

    public Depenses getSelected() {
        return selected;
    }

    public void setSelected(Depenses selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DepensesFacade getFacade() {
        return ejbFacade;
    }

    public Depenses prepareCreate() {
        selected = new Depenses();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DepensesCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            logged = null;
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DepensesUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DepensesDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Depenses> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<Depenses> getLogged() {
        if (logged == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext ectx = context.getExternalContext();
            HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
            String s = request.getRemoteUser();
            HttpSession session = request.getSession(true);

            logged = getFacade().findByName(s);
        }
        return logged;
    }

    public String getLogUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ectx = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
        String s = request.getRemoteUser();
        HttpSession session = request.getSession(true);

        Utilisateurs user = (Utilisateurs) getFacade().byname(s);
        
        logUser = user.getUsername();
        
        session.setAttribute("MySessionVariable", logUser);
               
        return logUser;
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

    public List<Depenses> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Depenses> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Depenses.class)
    public static class DepensesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DepensesController controller = (DepensesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "depensesController");
            return controller.getFacade().find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Depenses) {
                Depenses o = (Depenses) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Depenses.class.getName()});
                return null;
            }
        }

    }

}
