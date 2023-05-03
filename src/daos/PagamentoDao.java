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
import modelo.Evento;
import modelo.Pagamento;
import modelo.Participante;

/**
 *
 * @author otoniel.aalves
 */
public class PagamentoDao {

    // CRIANDO AS VARIAVERES DA CLASSES
    private EntityManager em;
    private EntityManagerFactory emf;

    // CRIA A FUNÇÃO PUBLICA PRA TODOS USAREM
    private EntityManager getEM() {
        emf = ConexaoJPA.getConexao(); //PEGANDO A CONEXÃO DA CLASSE CONEXAO
        return emf.createEntityManager();
    }

    // INSERIDO NOVO NO BANCO /////////////////////////////////////////////////
    public void gravar_atualizar(Pagamento obj) {
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
    public void excluir(int codigo) {

        try {
            em = getEM();
            em.getTransaction().begin();
            Pagamento obj = em.find(Pagamento.class, codigo);
            em.remove(obj);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    // RETORNA POR ID //////////////////////////////////////////
    public Pagamento retornaPorId(int id) {
        em = getEM();
        Pagamento obj = em.find(Pagamento.class, id);
        em.close();
        return obj;

    }

    // RETORNA LISTAGEM DE TUDO  ////////////////////////////////
    public List<Pagamento> getListagem() {
        em = getEM();
        List<Pagamento> lista = em.createNamedQuery("Pagamento.findAll").getResultList();
        em.close();
        return lista;
    }

    //   RETORNA USUARIO POR EENTO /////////////////////////////////
    public List<Pagamento> getPagamentoPorEvento(Evento evento) {
        em = getEM();
        TypedQuery<Pagamento> query = em.createQuery("SELECT p FROM Pagamento p WHERE p.evento = ?1", Pagamento.class);
        query.setParameter(1, evento);
        List<Pagamento> lista = query.getResultList();
        em.close();
        return lista;
    }

    //   RETORNA USUARIO POR NOME /////////////////////////////////
    public List<Pagamento> getPagamentoPorEvento(Evento evento, Participante participante) {
        em = getEM();
        TypedQuery<Pagamento> query = em.createQuery("SELECT p FROM Pagamento p WHERE p.evento = ?1 AND p.participante = ?2", Pagamento.class);
        query.setParameter(1, evento);
        query.setParameter(2, participante);
        List<Pagamento> lista = query.getResultList();
        em.close();
        return lista;
    }

}
