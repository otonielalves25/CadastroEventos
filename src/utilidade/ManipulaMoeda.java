/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidade;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JTextField;

/**
 *
 * @author otoniel.aalves
 */
public class ManipulaMoeda {
    
    // converte valor digitado em moeda ao sair
    public static void converteValorDigitadoEmMoeda(JTextField caixaTexto) {

        if (!caixaTexto.getText().equalsIgnoreCase("") && !caixaTexto.getText().trim().equals("R$")) {

            String valorCelula = caixaTexto.getText();
            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            valorCelula = valorCelula.replace("R$", "").replace(" ", "");

            if (valorCelula.length() >= 1 && valorCelula.length() <= 6) {
                valorCelula = valorCelula.replace(",", ".");

            } else {
                valorCelula = valorCelula.replace(".", "").replace(",", ".");
            }

            BigDecimal valor = new BigDecimal(valorCelula);

            String valorFormatado = nf.format(valor);
            caixaTexto.setText(valorFormatado);
        }

    }

}
