/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidade;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author otoniel.aalves
 */
public class ConvertendoSenhaMD5 {

    public static String ConvertendoSenha(String senha) {
        MessageDigest md5;
        String senhaConvertida = "";
        try {
            md5 = MessageDigest.getInstance("MD5");
            BigInteger hash = new BigInteger(1, md5.digest(senha.getBytes()));
            senhaConvertida = hash.toString(16);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ConvertendoSenhaMD5.class.getName()).log(Level.SEVERE, null, ex);

        }
        return senhaConvertida;
    }

}
