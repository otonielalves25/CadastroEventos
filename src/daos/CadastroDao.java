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
import modelo.Cadastro;
import modelo.Evento;
import modelo.Participante;

/**
 *
 * @author otoniel.aalves
 */
public class CadastroDao {

    // CRIANDO AS VARIAVERES DA CLASSES
    private EntityManager em;
    private EntityManagerFactory emf;

    // CRIA A FUNÇÃO PUBLICA PRA TODOS USAREM
    private EntityManager getEM() {
        emf = ConexaoJPA.getConexao(); //PEGANDO A CONEXÃO DA CLASSE CONEXAO
        return emf.createEntityManager();
    }

    // INSERIDO NOVO OU ATUALIZAR NO BANCO /////////////////////////////////////////////////
    public void gravar_atualizar(Cadastro obj) {
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
            Cadastro obj = em.find(Cadastro.class, codigo);
            em.remove(obj);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    // RETORNA POR ID //////////////////////////////////////////
    public Cadastro retornaPorId(int id) {
        em = getEM();
        Cadastro obj = em.find(Cadastro.class, id);
        em.close();
        return obj;

    }

    // RETORNA LISTAGEM DE TUDO  ////////////////////////////////
    public List<Cadastro> getListagem() {
        em = getEM();
        List<Cadastro> lista = em.createNamedQuery("Cadastro.findAll").getResultList();
        em.close();
        return lista;
    }
//

        // VERIFICA CADASTRO POR EVENTO  /////////////////////////////////////////////////////////
    public List<Cadastro> retornaPorEventoParticipantes(Evento evento1) {
        em = getEM();
        TypedQuery<Cadastro> query = em.createQuery("SELECT c FROM Cadastro c WHERE c.evento = ?1", Cadastro.class);
        query.setParameter(1, evento1);        
        List<Cadastro> lista = query.getResultList();
        em.close();
        return lista;
    }
    
    // VERIFICA CADASTRO POR EVENTO  /////////////////////////////////////////////////////////
    public Cadastro retornaPorEventoParticipante(Evento evento1, Participante participante1) {
        em = getEM();
        TypedQuery<Cadastro> query = em.createQuery("SELECT c FROM Cadastro c WHERE c.evento = ?1 AND c.participante = ?2", Cadastro.class);
        query.setParameter(1, evento1);
        query.setParameter(2, participante1);
        List<Cadastro> lista = query.getResultList();
        em.close();
        return lista.size() > 0 ? lista.get(0) : null;
    }

}
