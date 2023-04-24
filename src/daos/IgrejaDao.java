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
import modelo.Igreja;

/**
 *
 * @author otoniel.aalves
 */
public class IgrejaDao {

    // CRIANDO AS VARIAVERES DA CLASSES
    private EntityManager em;
    private EntityManagerFactory emf;

    // CRIA A FUNÇÃO PUBLICA PRA TODOS USAREM
    private EntityManager getEM() {
        emf = ConexaoJPA.getConexao(); //PEGANDO A CONEXÃO DA CLASSE CONEXAO
        return emf.createEntityManager();
    }

    // INSERIDO NOVO NO BANCO /////////////////////////////////////////////////
    public void gravar_atualizar(Igreja obj) {
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
            Igreja obj = em.find(Igreja.class, codigo);
            em.remove(obj);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    // RETORNA POR ID //////////////////////////////////////////
    public Igreja getPorId(int id) {
        em = getEM();
        Igreja obj = em.find(Igreja.class, id);
        em.close();
        return obj;

    }
    //   RETORNA USUARIO POR NOME /////////////////////////////////

    public Igreja getIgrejaPorNome(String nome) {
        em = getEM();
        TypedQuery<Igreja> query = em.createQuery("SELECT i FROM Igreja i WHERE i.nomeIgreja = ?1", Igreja.class);
        query.setParameter(1, nome);
        List<Igreja> lista = query.getResultList();
        em.close();
        return lista.size() > 0 ? lista.get(0) : null;
    }

    // RETORNA LISTAGEM DE TUDO  ////////////////////////////////
    public List<Igreja> getListagem() {
        em = getEM();
        List<Igreja> lista = em.createNamedQuery("Igreja.findAll").getResultList();
        em.close();
        return lista;
    }

}
