package gui;

import logic.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Esta clase define la ventana donde se mostraran los panel de simulacion y de ingreso de procesos.
 * @author: Camilo Rodríguez
 * @author: Nelson Barreto
 * @version: 9/11/15
 */
public class Ventana extends JFrame{
    private PanelVentana panelVentana;
    private javax.swing.JMenu jMenu1;

    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private PanelSimulacion panelSimulacion;

    private Proceso proceso;
    private Evento evento;
    private Global global;
    private Thread hiloEjecucion, hiloBloqueado;


    private int contador=0;
    /**
     * Constructor para iniciar metodo inicio y establecer algunas opciones de la ventana.
     */
    public Ventana(Global global) {
        this.hiloEjecucion =new Thread(global.getHiloEjecucion());
        this.hiloBloqueado =new Thread(global.getHiloBloqueadoP());

        this.global=global;
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));
        this.setSize(800, 600);
        this.setTitle("SIMULACION DE PROCESOS");
        evento=new Evento();
        inicio();
        this.setVisible(true);
    }
    /**
     * Método inicia todos los elementos de la ventana.
     */
    public void inicio() {
        panelVentana = new PanelVentana();
        panelSimulacion=new PanelSimulacion();
        panelVentana.getBtnAgregar().addActionListener(evento);
        panelVentana.getBtnSImular().addActionListener(evento);
        panelSimulacion.getBtnEjecutar().addActionListener(evento);
        panelSimulacion.getBtnBloquear().addActionListener(evento);
        panelSimulacion.getBtnForzarBloqueo().addActionListener(evento);
        panelSimulacion.getBtnParaSimulacion().addActionListener(evento);
        panelSimulacion.getBtnReanudarHilo().addActionListener(evento);
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        getContentPane().add(panelVentana);
        getContentPane().add(panelSimulacion);
        panelSimulacion.setVisible(false);
        jMenu1.setText("File");
        jMenuItem1.setText("SALIR");
        jMenuItem2.setText("SIMULACION");
        jMenuItem3.setText("AGREGAR PROCESOS");
        jMenu1.add(jMenuItem1);
        jMenu1.add(jMenuItem2);
        jMenu1.add(jMenuItem3);
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);
        jMenuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //simulacion
        jMenuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelSimulacion.setVisible(true);
                panelVentana.setVisible(false);
            }
        });
        //agregar procesos
        jMenuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelSimulacion.borrarLbn();
                panelSimulacion.getLbnTiempoMostrar().setText("TIEMPO PROCESO");
                panelSimulacion.getLbnTiempoBLoqueado().setText("TIEMPO BLOQUEADO");
                panelSimulacion.setVisible(false);
                panelVentana.setVisible(true);
                panelSimulacion.borrarFilas(panelSimulacion.getTablaProcesos());
                panelSimulacion.borrarFilas(panelSimulacion.getTablaProcesosAtendidos());
                global.getCola().getListaProcesos().deleteAll();
                global.getColaDespachados().getListaProcesos().deleteAll();
                global.getColaBloqueos().getListaProcesos().deleteAll();


            }
        });
    }

    public Ventana Obj(){
        return this;
    }


    /**
     * Método donde se le implementaran los eventos a los botonos.
     */
    class Evento implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {

            if(evt.getSource()==panelVentana.getBtnAgregar()){
                if(Validar.isNumeric(panelVentana.getTxtTiempo().getText())) {
                    String nombre=panelVentana.getTxtNombreProceso().getText().replace(" ","");
                    String tiempo=panelVentana.getTxtTiempo().getText().replace(" ","");
                    global.getCola().addProcesos(new Proceso(nombre,Integer.parseInt(tiempo),Integer.parseInt(tiempo),0,(byte)0));
                    panelVentana.getTxtNombreProceso().setText("");
                    panelVentana.getTxtTiempo().setText("");
                }
            }

            if(evt.getSource()==panelVentana.getBtnSImular()){
                global.getHiloBloqueadoP().setStart(true);
                System.out.println("0");
                global.getHiloEjecucion().setSumarTiempo(0);
                System.out.println("1");
                global.getHiloEjecucion().setTiempo(0);
                System.out.println("2");
                global.getCola().mostrarProcesos();
                System.out.println("3");
                panelVentana.vaciarTxt();
                System.out.println("4");
                panelVentana.setVisible(false);
                System.out.println("5");
                panelSimulacion.setVisible(true);
                System.out.println("6");
                for (int i=0;i<global.getCola().getListaProcesos().size();i++) {
                    Proceso proceso=global.getCola().getListaProcesos().get(i);
                    Object[] filas = {proceso.getNombre(),proceso.getTiempo()};
                    panelSimulacion.getItemTablaProceso().addRow(filas);
                }
                System.out.println("7");
                panelSimulacion.getTablaProcesos().setModel(panelSimulacion.getItemTablaProceso());
                System.out.println("8");
            }
            if(evt.getSource()==panelSimulacion.getBtnEjecutar()){
                if(!global.getCola().getListaProcesos().isEmpty()) {
                    global.getHiloEjecucion().setControl(0);
                    global.getHiloEjecucion().setSize(global.getCola().getListaProcesos().size());
                    global.getHiloEjecucion().setOpcion(1);
                    contador++;
                    if(contador==1) {
                        hiloEjecucion.start();
                        hiloBloqueado.start();
                    }else {
                        hiloEjecucion.resume();
                        hiloBloqueado.resume();
                    }
                }
            }
            if(evt.getSource()==panelSimulacion.getBtnBloquear()) {
                pasarABloqueado((byte) 0);
                global.getHiloEjecucion().setBloqueo(false);
            }


            if(evt.getSource()==panelSimulacion.getBtnForzarBloqueo()) {
                forzarBloqueo((byte) 1);
            }

            if(evt.getSource()==panelSimulacion.getBtnParaSimulacion()){
                hiloEjecucion.suspend();
                global.getHiloBloqueadoP().setStart(false);
                hiloBloqueado.suspend();
            }
            if(evt.getSource()==panelSimulacion.getBtnReanudarHilo()){
                hiloEjecucion.resume();
                global.getHiloBloqueadoP().setStart(true);
                hiloBloqueado.resume();
            }

        }
    }
    /**
     * Método donde se pasa el proceso a bloqueado
     */
    public void pasarABloqueado(byte interrupcion){
        if(panelSimulacion.getItemTablaProceso().getRowCount()!=0) {
            Proceso proceso = global.getHiloEjecucion().getProceso();
            //tiempo completo del proceso
            proceso.setTiempoEjecutado(proceso.getTiempo() + Integer.parseInt(panelSimulacion.getTxtBloquear().getText()));
            //resta del tiempo - el tiempo hubo la interrupcion, para darnos la foto del proceso
            proceso.setTiempo(proceso.getTiempo() - global.getHiloEjecucion().getSumarTiempo());
            //tiempo bloqueado
            proceso.setTiempoBloqueado(Integer.parseInt(panelSimulacion.getTxtBloquear().getText()));
            proceso.setInterrupcion(interrupcion);
            global.getColaBloqueos().addProcesos(proceso);
            panelSimulacion.getItemTablaProceso().removeRow(0);
            global.getCola().getListaProcesos().deleteFirst();
            Object[] obj = {proceso.getNombre(), proceso.getTiempoBloqueado()};
            panelSimulacion.getItemTablaProcesoBloqueado().addRow(obj);
            panelSimulacion.getTablaBloqueados().setModel(panelSimulacion.getItemTablaProcesoBloqueado());
            global.getHiloBloqueadoP().setOpcion(1);
            global.getHiloBloqueadoP().setTiempo(0);
            global.getHiloEjecucion().setOpcion(1);
            global.getHiloEjecucion().setSumarTiempo(0);
        }
    }
    /**
     * Método donde se fuerza a estado bloqueado o interrumpe un proceso.
     */
    private void forzarBloqueo(byte interrupcion){

        if(panelSimulacion.getItemTablaProceso().getRowCount()!=0) {
            panelSimulacion.getItemTablaProceso().removeRow(0);
            Proceso proceso = global.getHiloEjecucion().getProceso();
            proceso.setTiempoEjecutado(proceso.getTiempo());
            proceso.setTiempo(proceso.getTiempo() - global.getHiloEjecucion().getSumarTiempo());
            proceso.setTiempoBloqueado(0);
            proceso.setInterrupcion(interrupcion);
            global.getCola().getListaProcesos().deleteFirst();
            global.getCola().getListaProcesos().addLast(proceso);
            Object[] filas = {proceso.getNombre(), proceso.getTiempo()};
            panelSimulacion.getItemTablaProceso().addRow(filas);
            panelSimulacion.getTablaProcesos().setModel(panelSimulacion.getItemTablaProceso());

            global.getHiloEjecucion().setOpcion(1);
            global.getHiloEjecucion().setSumarTiempo(0);
        }
    }

    public PanelVentana getPanelVentana() {
        return panelVentana;
    }

    public void setPanelVentana(PanelVentana panelVentana) {
        this.panelVentana = panelVentana;
    }

    public PanelSimulacion getPanelSimulacion() {
        return panelSimulacion;
    }

    public void setPanelSimulacion(PanelSimulacion panelSimulacion) {
        this.panelSimulacion = panelSimulacion;
    }

    public Thread getHiloEjecucion() {
        return hiloEjecucion;
    }

    public void setHiloEjecucion(Thread hiloEjecucion) {
        this.hiloEjecucion = hiloEjecucion;
    }

    public Thread getHiloBloqueado() {
        return hiloBloqueado;
    }

    public void setHiloBloqueado(Thread hiloBloqueado) {
        this.hiloBloqueado = hiloBloqueado;
    }

}
