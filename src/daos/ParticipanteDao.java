/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import conexao.ConexaoJPA;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import modelo.Participante;

/**
 *
 * @author otoniel.aalves
 */
public class ParticipanteDao {

    // CRIANDO AS VARIAVERES DA CLASSES
    private EntityManager em;
    private EntityManagerFactory emf;

    // CRIA A FUNÇÃO PUBLICA PRA TODOS USAREM
    private EntityManager getEM() {
        emf = ConexaoJPA.getConexao(); //PEGANDO A CONEXÃO DA CLASSE CONEXAO
        return emf.createEntityManager();
    }

    // INSERIDO NOVO NO BANCO /////////////////////////////////////////////////
    public void gravar_atualizar(Participante obj) {
        try {
            em = getEM();
            em.getTransaction().begin();

            if (obj.getId() == null) {
                em.persist(obj);
            } else {
                em.merge(obj);
            }

            em.getTransaction().commit();
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();

        }

    }

    // EXCLUIR ///////////////////////////////////////////////
    public Boolean excluir(int codigo) {

        try {
            em = getEM();
            em.getTransaction().begin();
            Participante obj = em.find(Participante.class, codigo);
            em.remove(obj);
            em.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    // RETORNA POR ID //////////////////////////////////////////
    public Participante retornaPorId(int id) {
        em = getEM();
        Participante obj = em.find(Participante.class, id);
        em.close();
        return obj;

    }

    // RETORNA LISTAGEM DE TUDO  ////////////////////////////////
    public List<Participante> getListagem() {
        em = getEM();
        List<Participante> lista = em.createNamedQuery("Participante.findAll").getResultList();
        em.close();
        return lista;
    }

    // verifica nome já cadastrado
    public Participante getPorNome(String nome) {
        em = getEM();
        TypedQuery<Participante> query = em.createQuery("SELECT p FROM Participante p WHERE p.nome = ?1", Participante.class);
        query.setParameter(1, nome);
        List<Participante> lista = query.getResultList();
        em.close();
        return lista.size() > 0 ? lista.get(0) : null;
    }

    // verifica nome já cadastrado
    public List<Participante> getPorNomeLike(String nome) {
        em = getEM();
        TypedQuery<Participante> query = em.createQuery("SELECT p FROM Participante p WHERE p.nome LIKE ?1", Participante.class);
        query.setParameter(1, "%" + nome + "%");
        List<Participante> lista = query.getResultList();
        em.close();
        return lista;
    }

    // por novo pai
    public List<Participante> getPorPaiLike(String nome) {
        em = getEM();
        TypedQuery<Participante> query = em.createQuery("SELECT p FROM Participante p WHERE p.pai LIKE ?1", Participante.class);
        query.setParameter(1, "%" + nome + "%");
        List<Participante> lista = query.getResultList();
        em.close();
        return lista;
    }

    // por novo mae
    public List<Participante> getPorMaeLike(String nome) {
        em = getEM();
        TypedQuery<Participante> query = em.createQuery("SELECT p FROM Participante p WHERE p.mae LIKE ?1", Participante.class);
        query.setParameter(1, "%" + nome + "%");
        List<Participante> lista = query.getResultList();
        em.close();
        return lista;
    }

    // por novo contato
    public List<Participante> getPorContatoLike(String nome) {
        em = getEM();
        TypedQuery<Participante> query = em.createQuery("SELECT p FROM Participante p WHERE p.nomeContato LIKE ?1", Participante.class);
        query.setParameter(1, "%" + nome + "%");
        List<Participante> lista = query.getResultList();
        em.close();
        return lista;
    }
 
    // por novo contato
    public List<Participante> getPorIgrejaLike(String texto) {
        em = getEM();
        TypedQuery<Participante> query = em.createQuery("SELECT p FROM Participante p INNER JOIN p.igreja i WHERE i.nomeIgreja LIKE ?1", Participante.class);
        query.setParameter(1, "%" + texto + "%");
        List<Participante> lista = query.getResultList();
        em.close();
        return lista;
    }

    // BUSCA POR CELULAR ///////////////////////////////////////////////////////    
    public List<Participante> getPorTelefoneLike(String texto) {
                em = getEM();
        TypedQuery<Participante> query = em.createQuery("SELECT p FROM Participante p WHERE p.celular LIKE ?1", Participante.class);
        query.setParameter(1, "%" + texto + "%");
        List<Participante> lista = query.getResultList();
        em.close();
        return lista;
    }

}
