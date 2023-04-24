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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author otoniel.aalves
 */
@Entity
@Table(name = "participante")
@NamedQueries({
    @NamedQuery(name = "Participante.findAll", query = "SELECT p FROM Participante p")})
public class Participante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "data_nascimento")
    private String dataNascimento;
    @Column(name = "estado_civil")
    private String estadoCivil;
    @Column(name = "sexo")
    private String sexo;
    @Column(name = "mae")
    private String mae;
    @Column(name = "pai")
    private String pai;
    @Column(name = "rg")
    private String rg;
    @Column(name = "celular_contato")
    private String celularContato;
    @Column(name = "data_cadastro")
    private String dataCadastro;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "gestante")
    private Double gestante;
    @Column(name = "cama_baixo")
    private Double camaBaixo;
    @Column(name = "lactante")
    private Double lactante;
    @Column(name = "nome_contato")
    private String nomeContato;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "telefone_contato")
    private String telefoneContato;
    @Column(name = "celula_id")
    private Integer celulaId;
    @Column(name = "igreja_id")
    private Integer igrejaId;
    @Column(name = "foto")
    private String foto;
    @Column(name = "observacao")
    private String observacao;

    public Participante() {
    }

    public Participante(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getMae() {
        return mae;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCelularContato() {
        return celularContato;
    }

    public void setCelularContato(String celularContato) {
        this.celularContato = celularContato;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Double getGestante() {
        return gestante;
    }

    public void setGestante(Double gestante) {
        this.gestante = gestante;
    }

    public Double getCamaBaixo() {
        return camaBaixo;
    }

    public void setCamaBaixo(Double camaBaixo) {
        this.camaBaixo = camaBaixo;
    }

    public Double getLactante() {
        return lactante;
    }

    public void setLactante(Double lactante) {
        this.lactante = lactante;
    }

    public String getNomeContato() {
        return nomeContato;
    }

    public void setNomeContato(String nomeContato) {
        this.nomeContato = nomeContato;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefoneContato() {
        return telefoneContato;
    }

    public void setTelefoneContato(String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }

    public Integer getCelulaId() {
        return celulaId;
    }

    public void setCelulaId(Integer celulaId) {
        this.celulaId = celulaId;
    }

    public Integer getIgrejaId() {
        return igrejaId;
    }

    public void setIgrejaId(Integer igrejaId) {
        this.igrejaId = igrejaId;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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
        if (!(object instanceof Participante)) {
            return false;
        }
        Participante other = (Participante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Participante[ id=" + id + " ]";
    }
    
}
