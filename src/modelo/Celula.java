/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
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

/**
 *
 * @author otoniel.aalves
 */
@Entity
@Table(name = "celula")
@NamedQueries({
    @NamedQuery(name = "Celula.findAll", query = "SELECT c FROM Celula c")})
public class Celula implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome_celula")
    private String nomeCelula;
    @Column(name = "responsavel_celula")
    private String responsavelCelula;
    @JoinColumn(name = "igreja_id", referencedColumnName = "id")
    @ManyToOne
    private Igreja igrejaId;

    public Celula() {
    }

    public Celula(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeCelula() {
        return nomeCelula;
    }

    public void setNomeCelula(String nomeCelula) {
        this.nomeCelula = nomeCelula;
    }

    public String getResponsavelCelula() {
        return responsavelCelula;
    }

    public void setResponsavelCelula(String responsavelCelula) {
        this.responsavelCelula = responsavelCelula;
    }

    public Igreja getIgrejaId() {
        return igrejaId;
    }

    public void setIgrejaId(Igreja igrejaId) {
        this.igrejaId = igrejaId;
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
        if (!(object instanceof Celula)) {
            return false;
        }
        Celula other = (Celula) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Celula[ id=" + id + " ]";
    }
    
}
