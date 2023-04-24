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
import modelo.FormaPagamento;

/**
 *
 * @author otoniel.aalves
 */
public class FormaPagamentoDao {

    // CRIANDO AS VARIAVERES DA CLASSES
    private EntityManager em;
    private EntityManagerFactory emf;

    // CRIA A FUNÇÃO PUBLICA PRA TODOS USAREM
    private EntityManager getEM() {
        emf = ConexaoJPA.getConexao(); //PEGANDO A CONEXÃO DA CLASSE CONEXAO
        return emf.createEntityManager();
    }

    // INSERIDO NOVO NO BANCO /////////////////////////////////////////////////
    public void gravar_atualizar(FormaPagamento obj) {
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
            FormaPagamento obj = em.find(FormaPagamento.class, codigo);
            em.remove(obj);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    // RETORNA POR ID //////////////////////////////////////////
    public FormaPagamento retornaPorId(int id) {
        em = getEM();
        FormaPagamento obj = em.find(FormaPagamento.class, id);
        em.close();
        return obj;

    }

    // RETORNA LISTAGEM DE TUDO  ////////////////////////////////
    public List<FormaPagamento> getListagem() {
        em = getEM();
        List<FormaPagamento> lista = em.createNamedQuery("FormaPagamento.findAll").getResultList();
        em.close();
        return lista;
    }

    //   RETORNA USUARIO POR NOME /////////////////////////////////
    public FormaPagamento getPorForma(String nome) {
        em = getEM();
        TypedQuery<FormaPagamento> query = em.createQuery("SELECT f FROM FormaPagamento f WHERE f.formaNome = ?1", FormaPagamento.class);
        query.setParameter(1, nome);
        List<FormaPagamento> lista = query.getResultList();
        em.close();
        return lista.size() > 0 ? lista.get(0) : null;
    }

}
