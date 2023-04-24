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
import modelo.Caixa;

/**
 *
 * @author otoniel.aalves
 */
public class CaixaDao {

    // CRIANDO AS VARIAVERES DA CLASSES
    private EntityManager em;
    private EntityManagerFactory emf;

    // CRIA A FUNÇÃO PUBLICA PRA TODOS USAREM
    private EntityManager getEM() {
        emf = ConexaoJPA.getConexao(); //PEGANDO A CONEXÃO DA CLASSE CONEXAO
        return emf.createEntityManager();
    }

    // INSERIDO NOVO NO BANCO /////////////////////////////////////////////////
    public void gravar_atualizar(Caixa obj) {
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
            Caixa obj = em.find(Caixa.class, codigo);
            em.remove(obj);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    // RETORNA POR ID //////////////////////////////////////////
    public Caixa retornaPorId(int id) {
        em = getEM();
        Caixa obj = em.find(Caixa.class, id);
        em.close();
        return obj;

    }

    // RETORNA LISTAGEM DE TUDO  ////////////////////////////////
    public List<Caixa> getListagem() {
        em = getEM();
        List<Caixa> lista = em.createNamedQuery("Caixa.findAll").getResultList();
        em.close();
        return lista;
    }
//
//    // RETORNA LISTAGEM DE TUDO  ////////////////////////////////
//    public List<Acesso> getListagemLikeNome(String nome) {
//        em = getEM();
//        TypedQuery<Acesso> query = em.createQuery("SELECT e FROM Acesso e WHERE e.funcionarioId.nome LIKE ?1", Acesso.class);
//        query.setParameter(1, "%" + nome + "%");
//        List<Acesso> lista = query.getResultList();
//        em.close();
//        return lista;
//    }
//    

}
