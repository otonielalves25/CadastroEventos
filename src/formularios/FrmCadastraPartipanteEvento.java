/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import daos.CadastroDao;
import daos.EventoDao;
import daos.FormaPagamentoDao;
import daos.PagamentoDao;
import daos.ParticipanteDao;
import java.awt.Color;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modelo.Cadastro;
import modelo.Evento;
import modelo.FormaPagamento;
import modelo.Pagamento;
import modelo.Participante;
import utilidade.ValidarCampos;

/**
 *
 * @author otoniel.aalves
 */
public class FrmCadastraPartipanteEvento extends javax.swing.JFrame {

    /**
     * Creates new form FrmCadastraPartipanteEvento
     */
    // VARIAVEIS GLOBAL DO PROJETO
    private final FormaPagamentoDao formaPagamentoDao = new FormaPagamentoDao();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final CadastroDao cadastroDao = new CadastroDao();
    private final boolean novo = true;

    public FrmCadastraPartipanteEvento() {
        initComponents();
        limpaMoedas();
        carregaCombox();
        dataHoje();
    }

    // CALCULA VALOR TOTAL PAGO POR PESSOA ////////////////////////////////////
    private void calculaValorTotalPago() {
        String valor1 = txtValor1.getText();
        String valor2 = txtValor2.getText();
        String valor3 = txtValor3.getText();

        Double va1 = 0.00;
        Double va2 = 0.00;
        Double va3 = 0.00;
        Double total;

        valor1 = valor1.replace("R$ ", "").replace(".", "").replace(",", ".");
        valor2 = valor2.replace("R$ ", "").replace(".", "").replace(",", ".");
        valor3 = valor3.replace("R$ ", "").replace(".", "").replace(",", ".");

        try {
            va1 = Double.parseDouble(valor1);
        } catch (NumberFormatException e) {
            txtValor1.setText("R$");

        }
        try {
            va2 = Double.parseDouble(valor2);
        } catch (NumberFormatException e) {
            txtValor2.setText("R$");

        }
        try {
            va3 = Double.parseDouble(valor3);
        } catch (NumberFormatException e) {
            txtValor3.setText("R$");

        }

        total = va1 + va2 + va3;

        txtValorPago.setText(total + "");
        converteValorDigitadoEmMoeda(txtValorPago);
        calculaValorRestaPagar();
        converteValorDigitadoEmMoeda(txtValorRestante);
    }

    // CALCULA VALOR TOTAL PAGO POR PESSOA ////////////////////////////////////
    private void calculaValorRestaPagar() {
        String valor1 = txtValorEvento.getText();
        String valor2 = txtValorPago.getText();

        Double va1 = 0.00;
        Double va2 = 0.00;
        Double total;

        valor1 = valor1.replace("R$ ", "").replace(".", "").replace(",", ".");
        valor2 = valor2.replace("R$ ", "").replace(".", "").replace(",", ".");

        try {
            va1 = Double.parseDouble(valor1);
        } catch (NumberFormatException e) {
            txtValorEvento.setText("R$");

        }
        try {
            va2 = Double.parseDouble(valor2);
        } catch (NumberFormatException e) {
            txtValorPago.setText("R$");

        }

        total = va1 - va2;
        txtValorRestante.setText(total + "");

    }

    // CONVERTE DINHEIRO DIGITADO EM MOEDA FORMATADA////////////////////////////
    private void converteValorDigitadoEmMoeda(JTextField caixaTexto) {

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

    // CONVERTE MOEDA PARA DOUBLE /////////////////////////////////////////
    private Double converteMoedaParaDouble(String valor) {

        Double valorNovo = 0.0;

        try {
            String replace = valor.replace("R$", "").replace(".", "").replace(",", ".");
            valorNovo = Double.parseDouble(replace);
        } catch (NumberFormatException e) {

        }
        return valorNovo;

    }

    // CALCULAR O VALOR TOTAL DE VAGAS COM QUANTIDADE /////////////////////////
    private void calcularValorTotal() {
//        if (!txtVagas.getText().equals("") && !txtValor.getText().equals("")) {
//            int quantidade = Integer.parseInt(txtVagas.getText());
//            String vlr = txtValor.getText().replace("R$ ", "").replace(".", "").replace(",", ".");
//            double valor = Double.parseDouble(vlr);
//            Double total = quantidade * valor;
//            txtValorTotal.setText(total + "");
//            valorTipoMoeda(txtValorTotal);
//
//        }

    }

    // COLOCA DATA ATUAL NO SISTEMA ///////////////////////////////////////////
    private void dataHoje() {
        String diaAtual = sdf.format(new Date());
        txtDataCadastro.setText(diaAtual);
    }

    // CARREGA COMBOBOX DE TIPOS DE PAGAMENTOS /////////////////////////////////
    private void carregaCombox() {
        List<FormaPagamento> formas = formaPagamentoDao.getListagem();
        cboPag1.removeAllItems();
        cboPag2.removeAllItems();
        cboPag3.removeAllItems();
        cboPag1.addItem("Selecione...");
        cboPag2.addItem("Selecione...");
        cboPag3.addItem("Selecione...");
        for (FormaPagamento forma : formas) {
            cboPag1.addItem(forma);
            cboPag2.addItem(forma);
            cboPag3.addItem(forma);
        }
    }

    // LIMPAR TUDO /////////////////////////////////////////////////////////////
    private void limparTudo() {
        txtCodigo.setText("");
        dataHoje();

        txtCodigoEvento.setText("");
        txtNomeEvento.setText("");
        txtDataEvento.setText("");
        txtLocalEvento.setText("");

        txtCodigoParticipante.setText("");
        txtNomeParticipamente.setText("");
        txtNomePai.setText("");
        txtNomeIgreja.setText("");
        txtSexo.setText("");
        txtContato.setText("");
        txtTelefoneContato.setText("");

        txtObservacao.setText("");

        radMais.setSelected(false);

        cboPag1.setSelectedIndex(0);
        cboPag2.setSelectedIndex(0);
        cboPag3.setSelectedIndex(0);

        txtDataPag1.setText("");
        txtDataPag2.setText("");
        txtDataPag3.setText("");

//        idPag1.setText("");
//        idPag2.setText("");
//        idPag3.setText("");
        limpaMoedas();

    }

    // ZERANDO VALORES NAS MOEDAS /////////////////////////////////////////////
    private void limpaMoedas() {
        txtValorEvento.setText("R$ ");
        txtValorRestante.setText("R$ ");
        txtValorPago.setText("R$ ");
        txtValor1.setText("R$ ");
        txtValor2.setText("R$ ");
        txtValor3.setText("R$ ");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        aba1 = new javax.swing.JTabbedPane();
        painel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtCodigoEvento = new javax.swing.JTextField();
        txtNomeEvento = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnBuscaEventoRapido = new javax.swing.JButton();
        txtLocalEvento = new javax.swing.JTextField();
        txtDataEvento = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtCodigoParticipante = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNomeParticipamente = new javax.swing.JTextField();
        btnBuscaParticipante = new javax.swing.JButton();
        txtNomePai = new javax.swing.JTextField();
        txtNomeIgreja = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtSexo = new javax.swing.JTextField();
        txtContato = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtTelefoneContato = new javax.swing.JFormattedTextField();
        jLabel27 = new javax.swing.JLabel();
        btnLimpar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservacao = new javax.swing.JTextArea();
        radMais = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        txtDataPag1 = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        cboPag1 = new javax.swing.JComboBox();
        txtValor1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtDataPag2 = new javax.swing.JFormattedTextField();
        cboPag2 = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        txtValor2 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtDataPag3 = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cboPag3 = new javax.swing.JComboBox();
        txtValor3 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtValorEvento = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtValorPago = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtValorRestante = new javax.swing.JTextField();
        idPag1 = new javax.swing.JTextField();
        idPag2 = new javax.swing.JTextField();
        idPag3 = new javax.swing.JTextField();
        painel2 = new javax.swing.JPanel();
        txtDataCadastro = new javax.swing.JFormattedTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();

        jFormattedTextField1.setText("jFormattedTextField1");

        jScrollPane3.setViewportView(jTextPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        lblTitulo.setBackground(new java.awt.Color(0, 51, 51));
        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Cadastro de Participante no Evento");
        lblTitulo.setToolTipText("");
        lblTitulo.setOpaque(true);

        painel1.setBackground(new java.awt.Color(102, 102, 102));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados Evento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Código:");

        txtCodigoEvento.setEditable(false);
        txtCodigoEvento.setBackground(new java.awt.Color(255, 255, 204));

        txtNomeEvento.setEditable(false);
        txtNomeEvento.setBackground(new java.awt.Color(255, 255, 204));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nome Evento:");

        btnBuscaEventoRapido.setText("...");
        btnBuscaEventoRapido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaEventoRapidoActionPerformed(evt);
            }
        });

        txtLocalEvento.setEditable(false);
        txtLocalEvento.setBackground(new java.awt.Color(255, 255, 204));
        txtLocalEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLocalEventoActionPerformed(evt);
            }
        });

        txtDataEvento.setEditable(false);
        txtDataEvento.setBackground(new java.awt.Color(255, 255, 204));
        try {
            txtDataEvento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Data:");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Local do Evento:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtDataEvento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(77, 77, 77)))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 427, Short.MAX_VALUE))
                            .addComponent(txtLocalEvento)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtCodigoEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtNomeEvento)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBuscaEventoRapido, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel2))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnBuscaEventoRapido)
                    .addComponent(txtNomeEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDataEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtLocalEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do Participante", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        txtCodigoParticipante.setBackground(new java.awt.Color(204, 255, 204));

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Código:");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nome Participante:");

        txtNomeParticipamente.setBackground(new java.awt.Color(204, 255, 204));

        btnBuscaParticipante.setText("...");
        btnBuscaParticipante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaParticipanteActionPerformed(evt);
            }
        });

        txtNomePai.setBackground(new java.awt.Color(204, 255, 204));
        txtNomePai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomePaiActionPerformed(evt);
            }
        });

        txtNomeIgreja.setBackground(new java.awt.Color(204, 255, 204));
        txtNomeIgreja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeIgrejaActionPerformed(evt);
            }
        });

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Nome Igreja:");

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Nome Pai:");

        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Sexo:");

        txtSexo.setBackground(new java.awt.Color(204, 255, 204));
        txtSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSexoActionPerformed(evt);
            }
        });

        txtContato.setBackground(new java.awt.Color(204, 255, 204));
        txtContato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContatoActionPerformed(evt);
            }
        });

        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Contato:");

        txtTelefoneContato.setEditable(false);
        txtTelefoneContato.setBackground(new java.awt.Color(204, 255, 204));
        try {
            txtTelefoneContato.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Telefone Contato:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNomePai, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtNomeIgreja)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(txtCodigoParticipante, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtNomeParticipamente, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBuscaParticipante, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel9)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(txtSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtContato, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtTelefoneContato))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnBuscaParticipante)
                    .addComponent(txtNomeParticipamente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoParticipante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomePai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNomeIgreja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefoneContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 18, Short.MAX_VALUE))
        );

        btnLimpar.setBackground(new java.awt.Color(204, 204, 204));
        btnLimpar.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/clear.png"))); // NOI18N
        btnLimpar.setText("Limpar");
        btnLimpar.setBorder(null);
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnSalvar.setBackground(new java.awt.Color(204, 204, 204));
        btnSalvar.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(0, 102, 102));
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/save-outlineResultado.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.setBorder(null);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Observação:");

        txtObservacao.setColumns(20);
        txtObservacao.setRows(5);
        jScrollPane1.setViewportView(txtObservacao);

        radMais.setForeground(new java.awt.Color(255, 255, 255));
        radMais.setText("Cadastrar mais participantes neste evento");
        radMais.setOpaque(false);

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pagamento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        try {
            txtDataPag1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Data:");

        cboPag1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtValor1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtValor1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValor1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValor1FocusLost(evt);
            }
        });
        txtValor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValor1ActionPerformed(evt);
            }
        });
        txtValor1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValor1KeyReleased(evt);
            }
        });

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Forma de Pagamento");

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Valor:");

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Data:");

        try {
            txtDataPag2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        cboPag2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Forma de Pagamento");

        txtValor2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtValor2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValor2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValor2FocusLost(evt);
            }
        });
        txtValor2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValor2ActionPerformed(evt);
            }
        });
        txtValor2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValor2KeyReleased(evt);
            }
        });

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Valor:");

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Data:");

        try {
            txtDataPag3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Valor:");

        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Forma de Pagamento");

        cboPag3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtValor3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtValor3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValor3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValor3FocusLost(evt);
            }
        });
        txtValor3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValor3ActionPerformed(evt);
            }
        });
        txtValor3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValor3KeyReleased(evt);
            }
        });

        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Valor do Evento:");

        txtValorEvento.setEditable(false);
        txtValorEvento.setBackground(new java.awt.Color(204, 255, 255));
        txtValorEvento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtValorEvento.setForeground(new java.awt.Color(0, 0, 153));
        txtValorEvento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValorEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorEventoActionPerformed(evt);
            }
        });

        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Valor do Pago:");

        txtValorPago.setEditable(false);
        txtValorPago.setBackground(new java.awt.Color(204, 255, 204));
        txtValorPago.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtValorPago.setForeground(new java.awt.Color(0, 102, 51));
        txtValorPago.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValorPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorPagoActionPerformed(evt);
            }
        });

        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Valor Devido:");

        txtValorRestante.setEditable(false);
        txtValorRestante.setBackground(new java.awt.Color(255, 204, 204));
        txtValorRestante.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtValorRestante.setForeground(new java.awt.Color(153, 0, 0));
        txtValorRestante.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValorRestante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorRestanteActionPerformed(evt);
            }
        });

        idPag1.setEditable(false);
        idPag1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        idPag1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        idPag1.setText("1ª");

        idPag2.setEditable(false);
        idPag2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        idPag2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        idPag2.setText("2ª");

        idPag3.setEditable(false);
        idPag3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        idPag3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        idPag3.setText("3ª");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(txtValorEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(txtValorRestante, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 22, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(122, 122, 122))
                            .addComponent(txtValorPago)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(idPag3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(idPag1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(idPag2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtDataPag1, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                .addComponent(txtDataPag2)
                                .addComponent(txtDataPag3))
                            .addComponent(jLabel16)
                            .addComponent(jLabel19))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cboPag2, 0, 212, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboPag3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboPag1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtValor3, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel18)
                            .addComponent(jLabel20)
                            .addComponent(txtValor2)
                            .addComponent(txtValor1))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboPag1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataPag1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idPag1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataPag2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboPag2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValor2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idPag2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel21)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataPag3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboPag3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValor3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idPag3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel24)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtValorRestante, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValorPago, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValorEvento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout painel1Layout = new javax.swing.GroupLayout(painel1);
        painel1.setLayout(painel1Layout);
        painel1Layout.setHorizontalGroup(
            painel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painel1Layout.createSequentialGroup()
                        .addGroup(painel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(painel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painel1Layout.createSequentialGroup()
                                .addGap(18, 41, Short.MAX_VALUE)
                                .addComponent(radMais)
                                .addGap(34, 34, 34)
                                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24))
                            .addGroup(painel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(painel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        painel1Layout.setVerticalGroup(
            painel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(painel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radMais)
                        .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(97, Short.MAX_VALUE))
        );

        aba1.addTab("Cadastro", painel1);

        painel2.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout painel2Layout = new javax.swing.GroupLayout(painel2);
        painel2.setLayout(painel2Layout);
        painel2Layout.setHorizontalGroup(
            painel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1235, Short.MAX_VALUE)
        );
        painel2Layout.setVerticalGroup(
            painel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 517, Short.MAX_VALUE)
        );

        aba1.addTab("Lista dos Participantes", painel2);

        txtDataCadastro.setEditable(false);
        try {
            txtDataCadastro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Data cadastro:");

        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Codigo Cadastro:");

        txtCodigo.setEditable(false);
        txtCodigo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(aba1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(aba1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtLocalEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLocalEventoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLocalEventoActionPerformed

    private void txtNomePaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomePaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomePaiActionPerformed

    private void txtNomeIgrejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeIgrejaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeIgrejaActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // TODO add your handling code here:
        limparTudo();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        // TODO add your handling code here:
        if (ValidarCampos.validarCampo(txtNomeEvento, "Evento")) {
            return;
        }
        if (ValidarCampos.validarCampo(txtNomeParticipamente, "participante")) {
            return;
        }

        // CRIA A CLASSE MARCA MODELO //////////////////////////////////////////
        Cadastro cadastro = new Cadastro();
        cadastro.setDataCadastro(txtDataCadastro.getText());
        cadastro.setObservacao(txtObservacao.getText());
        Evento evento1 = new EventoDao().getPorId(Integer.parseInt(txtCodigoEvento.getText()));
        Participante participante1 = new ParticipanteDao().retornaPorId(Integer.parseInt(txtCodigoParticipante.getText()));
        cadastro.setEvento(evento1);
        cadastro.setParticipante(participante1);

//        // CADATRAO NOVO NO BANCO //////////////////////////////////////////////
        if (novo) {
            Cadastro temCadastro = cadastroDao.retornaPorEventoParticipante(evento1, participante1);
            if (temCadastro != null) {
                JOptionPane.showMessageDialog(this, "Participante já cadastrado neste evento.", null, JOptionPane.ERROR_MESSAGE);
                return;
            }
            cadastroDao.gravar_atualizar(cadastro);
            JOptionPane.showMessageDialog(this, "Cadatrado com Sucesso !!!", null, JOptionPane.INFORMATION_MESSAGE);
            // ALTERAR CADASTRO NO BANCO ///////////////////////////////////////
        } else {

            cadastro.setId(Integer.parseInt(txtCodigo.getText()));

            cadastroDao.gravar_atualizar(cadastro);
            JOptionPane.showMessageDialog(this, "Alterada com Sucesso !!!", null, JOptionPane.INFORMATION_MESSAGE);
            // PEGANDO LOGS DO SISTEMA
        }

        cadastraPagamentos(evento1, participante1);
//        botaoInicial();
//        novo = false;
//        habilitado(false);
//        limparTudo();
//        carregaGrelha();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void cadastraPagamentos(Evento evento, Participante participante) {
        PagamentoDao pagamentoDao = new PagamentoDao();
        List<Pagamento> pagamentos = pagamentoDao.getPagamentoPorEvento(evento, participante);
        Pagamento pg1, pg2, pg3;

        if (pagamentos.size() == 1) {
            pg1 = pagamentos.get(0);
            pg1.setDataPagamento(txtDataPag3.getText());
            pg1.setNrParcela(Integer.parseInt(idPag3.getText().replace("ª", "")));
            pg1.setDataPagamento(txtDataPag3.getText());
            pg1.setFormaPagamento((FormaPagamento) cboPag3.getSelectedItem());
            pg1.setValor(converteMoedaParaDouble(txtValor3.getText()));
            pg1.setEvento(evento);
            pg1.setParticipante(participante);
            pagamentoDao.gravar_atualizar(pg1);
        }

        if (pagamentos.size() == 2) {
            pg2 = pagamentos.get(1);
            pg2.setDataPagamento(txtDataPag2.getText());
            pg2.setNrParcela(Integer.parseInt(idPag2.getText().replace("ª", "")));
            pg2.setDataPagamento(txtDataPag2.getText());
            pg2.setFormaPagamento((FormaPagamento) cboPag2.getSelectedItem());
            pg2.setValor(converteMoedaParaDouble(txtValor2.getText()));
            pg2.setEvento(evento);
            pg2.setParticipante(participante);
            pagamentoDao.gravar_atualizar(pg2);
        }

        if (pagamentos.size() == 3) {
            pg3 = pagamentos.get(2);
            pg3.setDataPagamento(txtDataPag3.getText());
            pg3.setNrParcela(Integer.parseInt(idPag3.getText().replace("ª", "")));
            pg3.setDataPagamento(txtDataPag3.getText());
            pg3.setFormaPagamento((FormaPagamento) cboPag3.getSelectedItem());
            pg3.setValor(converteMoedaParaDouble(txtValor3.getText()));
            pg3.setEvento(evento);
            pg3.setParticipante(participante);
            pagamentoDao.gravar_atualizar(pg3);
        }

    }


    private void btnBuscaEventoRapidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaEventoRapidoActionPerformed
        // TODO add your handling code here:
//                 int codigo = 0;
        FrmEventoConsultaRapida frmConsulta = new FrmEventoConsultaRapida(null, true);
        frmConsulta.setVisible(true);
        try {
            if (frmConsulta.getEvento() != null) {
                txtCodigoEvento.setText(frmConsulta.getEvento().getId() + "");
                txtNomeEvento.setText(frmConsulta.getEvento().getNome());
                txtNomeEvento.setBackground(Color.white);
                txtDataEvento.setText(frmConsulta.getEvento().getDataEvento());
                txtLocalEvento.setText(frmConsulta.getEvento().getLocalEvento());
                txtValorEvento.setText(frmConsulta.getEvento().getValorPessoa() + "");
                converteValorDigitadoEmMoeda(txtValorEvento);
            }

        } catch (Exception e) {
        }

    }//GEN-LAST:event_btnBuscaEventoRapidoActionPerformed

    private void txtValor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValor1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValor1ActionPerformed

    private void txtValor2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValor2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValor2ActionPerformed

    private void txtValor3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValor3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValor3ActionPerformed

    private void txtValorEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorEventoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorEventoActionPerformed

    private void txtValorPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorPagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorPagoActionPerformed

    private void txtValorRestanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorRestanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorRestanteActionPerformed

    private void txtSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSexoActionPerformed

    private void txtContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContatoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContatoActionPerformed

    private void btnBuscaParticipanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaParticipanteActionPerformed
        // TODO add your handling code here: 
        FrmParticipanteConsultaRapida frmConsulta = new FrmParticipanteConsultaRapida(null, true);
        frmConsulta.setVisible(true);
        try {
            if (frmConsulta.getParticipante() != null) {
                txtCodigoParticipante.setText(frmConsulta.getParticipante().getId() + "");
                txtNomeParticipamente.setText(frmConsulta.getParticipante().getNome());
                txtNomePai.setText(frmConsulta.getParticipante().getPai());
                txtNomeIgreja.setText(frmConsulta.getParticipante().getIgreja().getNomeIgreja());
                txtSexo.setText(frmConsulta.getParticipante().getSexo());
                txtContato.setText(frmConsulta.getParticipante().getNomeContato());
                txtTelefoneContato.setText(frmConsulta.getParticipante().getCelularContato());

            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnBuscaParticipanteActionPerformed

    private void txtValor1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValor1FocusGained
        // TODO add your handling code here:   
        if (txtNomeEvento.getText().equals("")) {
            txtNomeEvento.requestFocus();
            txtNomeEvento.setBackground(Color.yellow);
            JOptionPane.showMessageDialog(this, "Informe qual o Evento do Pagamento", null, JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtValor1.getText().trim().equals("R$")) {
            txtValor1.setText("");
        }

    }//GEN-LAST:event_txtValor1FocusGained

    private void txtValor2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValor2FocusGained
        // TODO add your handling code here:
        if (txtNomeEvento.getText().equals("")) {
            txtNomeEvento.requestFocus();
            txtNomeEvento.setBackground(Color.yellow);
            JOptionPane.showMessageDialog(this, "Informe qual o Evento do Pagamento", null, JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtValor2.getText().trim().equals("R$")) {
            txtValor2.setText("");
        }
    }//GEN-LAST:event_txtValor2FocusGained

    private void txtValor3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValor3FocusGained
        // TODO add your handling code here:
        if (txtNomeEvento.getText().equals("")) {
            txtNomeEvento.requestFocus();
            txtNomeEvento.setBackground(Color.yellow);
            JOptionPane.showMessageDialog(this, "Informe qual o Evento do Pagamento", null, JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtValor3.getText().trim().equals("R$")) {
            txtValor3.setText("");
        }
    }//GEN-LAST:event_txtValor3FocusGained

    private void txtValor1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValor1FocusLost
        // TODO add your handling code here:
        converteValorDigitadoEmMoeda(this.txtValor1);
        calculaValorTotalPago();
    }//GEN-LAST:event_txtValor1FocusLost

    private void txtValor2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValor2FocusLost
        // TODO add your handling code here:

        converteValorDigitadoEmMoeda(this.txtValor2);
        calculaValorTotalPago();
    }//GEN-LAST:event_txtValor2FocusLost

    private void txtValor3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValor3FocusLost
        // TODO add your handling code here:
        converteValorDigitadoEmMoeda(this.txtValor3);
        calculaValorTotalPago();
    }//GEN-LAST:event_txtValor3FocusLost

    private void txtValor1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValor1KeyReleased
        // TODO add your handling code here:
        String testeDigitado = txtValor1.getText().replace("R$ ", "").replace(" ", "").replace(",", ".");

        try {
            Double.parseDouble(testeDigitado);
        } catch (Exception e) {
            System.out.println("não é numero" + e);
            txtValor1.setText("R$ ");
        }
    }//GEN-LAST:event_txtValor1KeyReleased

    private void txtValor2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValor2KeyReleased
        // TODO add your handling code here:
        String testeDigitado = txtValor2.getText().replace("R$ ", "").replace(" ", "").replace(",", ".");

        try {
            Double.parseDouble(testeDigitado);
        } catch (Exception e) {
            System.out.println("não é numero" + e);
            txtValor2.setText("R$ ");
        }
    }//GEN-LAST:event_txtValor2KeyReleased

    private void txtValor3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValor3KeyReleased
        // TODO add your handling code here:
        String testeDigitado = txtValor3.getText().replace("R$ ", "").replace(" ", "").replace(",", ".");

        try {
            Double.parseDouble(testeDigitado);
        } catch (Exception e) {
            System.out.println("não é numero" + e);
            txtValor3.setText("R$ ");
        }
    }//GEN-LAST:event_txtValor3KeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmCadastraPartipanteEvento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCadastraPartipanteEvento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCadastraPartipanteEvento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCadastraPartipanteEvento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmCadastraPartipanteEvento().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane aba1;
    private javax.swing.JButton btnBuscaEventoRapido;
    private javax.swing.JButton btnBuscaParticipante;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox cboPag1;
    private javax.swing.JComboBox cboPag2;
    private javax.swing.JComboBox cboPag3;
    private javax.swing.JTextField idPag1;
    private javax.swing.JTextField idPag2;
    private javax.swing.JTextField idPag3;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel painel1;
    private javax.swing.JPanel painel2;
    private javax.swing.JCheckBox radMais;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoEvento;
    private javax.swing.JTextField txtCodigoParticipante;
    private javax.swing.JTextField txtContato;
    private javax.swing.JFormattedTextField txtDataCadastro;
    private javax.swing.JFormattedTextField txtDataEvento;
    private javax.swing.JFormattedTextField txtDataPag1;
    private javax.swing.JFormattedTextField txtDataPag2;
    private javax.swing.JFormattedTextField txtDataPag3;
    private javax.swing.JTextField txtLocalEvento;
    private javax.swing.JTextField txtNomeEvento;
    private javax.swing.JTextField txtNomeIgreja;
    private javax.swing.JTextField txtNomePai;
    private javax.swing.JTextField txtNomeParticipamente;
    private javax.swing.JTextArea txtObservacao;
    private javax.swing.JTextField txtSexo;
    private javax.swing.JFormattedTextField txtTelefoneContato;
    private javax.swing.JTextField txtValor1;
    private javax.swing.JTextField txtValor2;
    private javax.swing.JTextField txtValor3;
    private javax.swing.JTextField txtValorEvento;
    private javax.swing.JTextField txtValorPago;
    private javax.swing.JTextField txtValorRestante;
    // End of variables declaration//GEN-END:variables
}
