/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guitta.akoury.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author guitta
 */
@Entity
@Table(name = "catdep")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Catdep.findAll", query = "SELECT c FROM Catdep c"),
    @NamedQuery(name = "Catdep.findByCatdep", query = "SELECT c FROM Catdep c WHERE c.catdep = :catdep")})
public class Catdep implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "catdep")
    private String catdep;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catdep")
    private List<Depenses> depensesList;

    public Catdep() {
    }

    public Catdep(String catdep) {
        this.catdep = catdep;
    }

    public String getCatdep() {
        return catdep;
    }

    public void setCatdep(String catdep) {
        this.catdep = catdep;
    }

    @XmlTransient
    public List<Depenses> getDepensesList() {
        return depensesList;
    }

    public void setDepensesList(List<Depenses> depensesList) {
        this.depensesList = depensesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catdep != null ? catdep.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Catdep)) {
            return false;
        }
        Catdep other = (Catdep) object;
        if ((this.catdep == null && other.catdep != null) || (this.catdep != null && !this.catdep.equals(other.catdep))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return catdep;
    }
    
}
