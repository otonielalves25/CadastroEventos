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
import modelo.Usuario;

/**
 *
 * @author otoniel.aalves
 */
public class UsuarioDao {

    // CRIANDO AS VARIAVERES DA CLASSES
    private EntityManager em;
    private EntityManagerFactory emf;

    // CRIA A FUNÇÃO PUBLICA PRA TODOS USAREM
    private EntityManager getEM() {
        emf = ConexaoJPA.getConexao(); //PEGANDO A CONEXÃO DA CLASSE CONEXAO
        return emf.createEntityManager();
    }

    // INSERIDO NOVO NO BANCO /////////////////////////////////////////////////
    public void gravar_atualizar(Usuario obj) {
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
            Usuario obj = em.find(Usuario.class, codigo);
            em.remove(obj);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    // RETORNA POR ID //////////////////////////////////////////
    public Usuario getPorId(int id) {
        em = getEM();
        Usuario obj = em.find(Usuario.class, id);
        em.close();
        return obj;
    }

    // RETORNA LISTAGEM DE TUDO  ////////////////////////////////
    public List<Usuario> getListagem() {
        em = getEM();
        List<Usuario> lista = em.createNamedQuery("Usuario.findAll").getResultList();
        em.close();
        return lista;
    }

    //   POR QUALQUER PARTE DO NOME ///////////////////////////////
    public List<Usuario> getListagemLikeNome(String nome) {
        em = getEM();
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.nome LIKE ?1", Usuario.class);
        query.setParameter(1, "%" + nome + "%");
        List<Usuario> lista = query.getResultList();
        em.close();
        return lista;
    }

    //   RETORNA USUARIO POR NOME /////////////////////////////////
    public Usuario getUsuarioPorNome(String nome) {
        em = getEM();
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.nome = ?1", Usuario.class);
        query.setParameter(1, nome);
        List<Usuario> lista = query.getResultList();
        em.close();
        return lista.size() > 0 ? lista.get(0) : null;
    }
    
        //   RETORNA USUARIO POR NOME /////////////////////////////////
    public Usuario autenticar(String nome, String senha) {
        em = getEM();
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.login  = ?1 AND u.senha = ?2 AND u.ativo = 1", Usuario.class);
        query.setParameter(1, nome);
        query.setParameter(2, senha);
        List<Usuario> lista = query.getResultList();
        em.close();
        return lista.size() > 0 ? lista.get(0) : null;
    }

}
