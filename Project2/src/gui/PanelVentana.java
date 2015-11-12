package gui;

import logic.Cola;
import logic.Proceso;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Esta clase define el panel donse se ingresaran los procesos.
 * @author: Camilo Rodríguez
 * @author: Nelson Barreto
 * @version: 9/11/15
 */
public class PanelVentana extends JPanel {
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnSImular;
    private javax.swing.JLabel lbnNombreProceso;
    private javax.swing.JLabel lbnTiempo;
    private javax.swing.JTextField txtNombreProceso;
    private javax.swing.JTextField txtTiempo;

    /**
     * Constructor para iniciar metodo inicio y establecer el layout.
     */
    public PanelVentana(){

        this.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "PROCESOS"));
        this.setLayout(new java.awt.GridLayout(3, 2));
        inicio();
    }
    /**
     * Método inicia todos los elementos del panel.
     */
    public void inicio(){


        lbnNombreProceso = new javax.swing.JLabel();
        txtNombreProceso = new javax.swing.JTextField();
        lbnTiempo = new javax.swing.JLabel();
        txtTiempo = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnSImular = new javax.swing.JButton();

        lbnNombreProceso.setText("NOMBRE DEL PROCESO");
        this.add(lbnNombreProceso);
        this.add(txtNombreProceso);
        lbnTiempo.setText("TIEMPO");
        this.add(lbnTiempo);
        this.add(txtTiempo);
        btnAgregar.setText("AGREGAR");
        this.add(btnAgregar);
        btnSImular.setText("SIMULAR");
        this.add(btnSImular);

    }
    public void vaciarTxt(){
        txtNombreProceso.setText(" ");
        txtTiempo.setText(" ");
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public void setBtnAgregar(JButton btnAgregar) {
        this.btnAgregar = btnAgregar;
    }

    public JButton getBtnSImular() {
        return btnSImular;
    }

    public void setBtnSImular(JButton btnSImular) {
        this.btnSImular = btnSImular;
    }

    public JLabel getLbnNombreProceso() {
        return lbnNombreProceso;
    }

    public void setLbnNombreProceso(JLabel lbnNombreProceso) {
        this.lbnNombreProceso = lbnNombreProceso;
    }

    public JLabel getLbnTiempo() {
        return lbnTiempo;
    }

    public void setLbnTiempo(JLabel lbnTiempo) {
        this.lbnTiempo = lbnTiempo;
    }

    public JTextField getTxtNombreProceso() {
        return txtNombreProceso;
    }

    public void setTxtNombreProceso(JTextField txtNombreProceso) {
        this.txtNombreProceso = txtNombreProceso;
    }

    public JTextField getTxtTiempo() {
        return txtTiempo;
    }

    public void setTxtTiempo(JTextField txtTiempo) {
        this.txtTiempo = txtTiempo;
    }

}
