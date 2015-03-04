/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guitta.akoury.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author guitta
 */
@Entity
@Table(name = "depenses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Depenses.findAll", query = "SELECT d FROM Depenses d"),
    @NamedQuery(name = "Depenses.findById", query = "SELECT d FROM Depenses d WHERE d.id = :id"),
    @NamedQuery(name = "Depenses.findByUsername", query = "SELECT d FROM Depenses d WHERE d.username = :username"),
    @NamedQuery(name = "Depenses.findByMontant", query = "SELECT d FROM Depenses d WHERE d.montant = :montant"),
    @NamedQuery(name = "Depenses.findByDate", query = "SELECT d FROM Depenses d WHERE d.date = :date")})
public class Depenses implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Column(name = "montant")
    private int montant;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "catdep", referencedColumnName = "catdep")
    @ManyToOne(optional = false)
    private Catdep catdep;

    public Depenses() {
    }

    public Depenses(Integer id) {
        this.id = id;
    }

    public Depenses(Integer id, int montant, Date date) {
        this.id = id;
        this.montant = montant;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Catdep getCatdep() {
        return catdep;
    }

    public void setCatdep(Catdep catdep) {
        this.catdep = catdep;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Depenses)) {
            return false;
        }
        Depenses other = (Depenses) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "guitta.akoury.entities.Depenses[ id=" + id + " ]";
    }
    
}
