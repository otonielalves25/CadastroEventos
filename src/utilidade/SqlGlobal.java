/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidade;

/**
 *
 * @author otoniel.aalves
 */
public class SqlGlobal {
    
    private static String sqlGlogalCelulares;
    private static String sqlGlogalChipes;
    private static String sqlGlogalEmprestimos;

    public static String getSqlGlogalCelelares() {
        return sqlGlogalCelulares;
    }

    public static void setSqlGlogalCelulares(String aSqlGlogal) {
        sqlGlogalCelulares = aSqlGlogal;
    }

    public static String getSqlGlogalChipes() {
        return sqlGlogalChipes;
    }

    public static void setSqlGlogalChipes(String aSqlGlogalChipes) {
        sqlGlogalChipes = aSqlGlogalChipes;
    }

    public static String getSqlGlogalEmprestimos() {
        return sqlGlogalEmprestimos;
    }

    public static void setSqlGlogalEmprestimos(String aSqlGlogalEmprestimos) {
        sqlGlogalEmprestimos = aSqlGlogalEmprestimos;
    }


    
}
