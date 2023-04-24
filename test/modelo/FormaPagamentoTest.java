/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import daos.FormaPagamentoDao;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author otoniel.aalves
 */
public class FormaPagamentoTest {

    public FormaPagamentoTest() {
    }

    @Test
    public void setUp() {

        //cadastrar("Cartão de Débito");
        listar();

    }

    public static void cadastrar(String formaPagar) {

        FormaPagamentoDao dao = new FormaPagamentoDao();
        FormaPagamento forma = new FormaPagamento();
        forma.setFormaNome(formaPagar);
        dao.gravar_atualizar(forma);
    }

    public static void atualizar() {

        FormaPagamentoDao dao = new FormaPagamentoDao();
        FormaPagamento forma = new FormaPagamento();
        forma.setId(2);
        forma.setFormaNome("boleto");
        dao.gravar_atualizar(forma);
    }

    public static void excluir() {

        FormaPagamentoDao dao = new FormaPagamentoDao();
        FormaPagamento forma = new FormaPagamento();
        dao.excluir(4);
    }

    public static void listar() {

        FormaPagamentoDao dao = new FormaPagamentoDao();
        List<FormaPagamento> listagem = dao.getListagem();

        for (FormaPagamento formaPagamento : listagem) {
            System.out.println(formaPagamento.getId() + " - " + formaPagamento.getFormaNome());
        }

    }

}
