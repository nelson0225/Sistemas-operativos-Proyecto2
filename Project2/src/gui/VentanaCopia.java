//package com.gui;
//
//import com.logic.Proceso;
//import com.logic.Validar;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
///**
// * Esta clase define la ventana donde se mostraran los panel de simulacion y de ingreso de procesos.
// * @author: Camilo Rodríguez
// * @author: Nelson Barreto
// * @version: 9/11/15
// */
//public class VentanaCopia extends JFrame implements Runnable{
//    private PanelVentana panelVentana;
//    private JMenu jMenu1;
//
//    private JMenuBar jMenuBar1;
//    private JMenuItem jMenuItem1;
//    private JMenuItem jMenuItem2;
//    private JMenuItem jMenuItem3;
//    private PanelSimulacion panelSimulacion;
//    private int sumarTiempo=0;
//    private Proceso proceso;
//    private Evento evento;
//    private Thread hilo;
//    private int tiempo=0;
//    private int opcion=0;
//    private int contador=0;
//
//    public VentanaCopia() {
//        hilo=new Thread(this);
//
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
//        this.setSize(800, 600);
//        this.setTitle("SIMULACION DE PROCESOS");
//        evento=new Evento();
//        inicio();
//        this.setVisible(true);
//    }
//
//    public void inicio() {
//        panelVentana = new PanelVentana();
//        panelSimulacion=new PanelSimulacion();
//        panelVentana.getBtnAgregar().addActionListener(evento);
//        panelVentana.getBtnSImular().addActionListener(evento);
//        panelSimulacion.getBtnEjecutar().addActionListener(evento);
//        panelSimulacion.getBtnBloquear().addActionListener(evento);
//        panelSimulacion.getBtnForzarBloqueo().addActionListener(evento);
//        jMenuBar1 = new JMenuBar();
//        jMenu1 = new JMenu();
//        jMenuItem1 = new JMenuItem();
//        jMenuItem2 = new JMenuItem();
//        jMenuItem3 = new JMenuItem();
//        getContentPane().add(panelVentana);
//        getContentPane().add(panelSimulacion);
//        panelSimulacion.setVisible(false);
//        jMenu1.setText("File");
//        jMenuItem1.setText("SALIR");
//        jMenuItem2.setText("SIMULACION");
//        jMenuItem3.setText("AGREGAR PROCESOS");
//        jMenu1.add(jMenuItem1);
//        jMenu1.add(jMenuItem2);
//        jMenu1.add(jMenuItem3);
//        jMenuBar1.add(jMenu1);
//
//        setJMenuBar(jMenuBar1);
//        jMenuItem1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.exit(0);
//            }
//        });
//        //simulacion
//        jMenuItem2.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                panelSimulacion.setVisible(true);
//                panelVentana.setVisible(false);
//            }
//        });
//        //agregar procesos
//        jMenuItem3.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                panelSimulacion.borrarLbn();
//                panelSimulacion.setVisible(false);
//                panelVentana.setVisible(true);
//                panelSimulacion.borrarFilas(panelSimulacion.getTablaProcesos());
//                panelSimulacion.borrarFilas(panelSimulacion.getTablaProcesosAtendidos());
//                panelVentana.getCola().getListaProcesos().deleteAll();
//                panelVentana.getColaDespachados().getListaProcesos().deleteAll();
//            }
//        });
//    }
//    @Override
//    public void run() {
//        while(true) {
//            System.out.println("infinito");
//            switch (opcion){
//                case 1:
//                    System.out.println("opcion 1");
//                    try {
//                        tiempo++;
//                        sumarTiempo++;
//                        proceso = (Proceso) panelVentana.getCola().getListaProcesos().get(0);
//                        if (!panelVentana.getCola().getListaProcesos().isEmpty()) {
//                            panelSimulacion.getLbnMostrarProceso().setText(proceso.getNombre());
//                            if (sumarTiempo >= proceso.getTiempo()) {
//                                sumarTiempo = 0;
//
//                                panelVentana.getColaDespachados().getListaProcesos().addFirst(proceso);
//                                panelVentana.getCola().getListaProcesos().deleteFirst();
//                                panelSimulacion.getItemTablaProceso().removeRow(0);
//                                Object[] filas = {proceso.getNombre(), proceso.getTiempo(),proceso.getTiempoBloqueado()
//                                        ,proceso.getTiempoEjecutado(),proceso.getInterrupcion()};
//                                panelSimulacion.getItemTablaProcesoAtendidos().addRow(filas);
//                                panelSimulacion.getTablaProcesosAtendidos().setModel(panelSimulacion.getItemTablaProcesoAtendidos());
//
//                                if (panelVentana.getCola().getListaProcesos().isEmpty()) {
//                                    panelSimulacion.getLbnMostrarProceso().setText("");
//                                    tiempo = 0;
//                                    opcion=0;
//                                    int total=0;
//                                    for (int i=0;i<panelVentana.getColaDespachados().getListaProcesos().size();i++){
//                                    Proceso proceso1=(Proceso)panelVentana.getColaDespachados().getListaProcesos().get(i);
//                                        total+=proceso1.getTiempoEjecutado();
//                                    }
//                                    panelSimulacion.borrarLbn();
//                                    panelSimulacion.getLbnTotalEjecucion().setText(panelSimulacion.getLbnTotalEjecucion().getText()+" "+Integer.toString(total));
//
//                                    hilo.suspend();
//                                }
//                            }
//                            panelSimulacion.getLbnTiempoMostrar().setText(Integer.toString(tiempo));
//                        }
//
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case 2:
//                    tiempo++;
//                    try {
//                        if(tiempo>=proceso.getTiempoBloqueado()){
//                            opcion=1;
//                            panelSimulacion.borrarFilas(panelSimulacion.getTablaProcesos());
//                            for (int i=0;i<panelVentana.getCola().getListaProcesos().size();i++) {
//                                Proceso proceso=panelVentana.getCola().getListaProcesos().get(i);
//                                Object[] filas = {proceso.getNombre(),proceso.getTiempo()};
//                                panelSimulacion.getItemTablaProceso().addRow(filas);
//                            }
//                            panelSimulacion.getTablaProcesos().setModel(panelSimulacion.getItemTablaProceso());
//                        }
//                        panelSimulacion.getLbnTiempoBLoqueado().setText(Integer.toString(tiempo));
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case 3:
//                    panelVentana.getCola().getListaProcesos().deleteFirst();
//                    panelVentana.getColaDespachados().getListaProcesos().addFirst(proceso);
//                    panelSimulacion.getItemTablaProceso().removeRow(0);
//                    proceso.setTiempoEjecutado(sumarTiempo);
//                    proceso.setInterrupcion((byte)1);
//                    sumarTiempo=0;
//                    Object[] filas = {proceso.getNombre(), proceso.getTiempo(),proceso.getTiempoBloqueado()
//                            ,proceso.getTiempoEjecutado(),proceso.getInterrupcion()};
//                    panelSimulacion.getItemTablaProcesoAtendidos().addRow(filas);
//                    panelSimulacion.getTablaProcesosAtendidos().setModel(panelSimulacion.getItemTablaProcesoAtendidos());
//                    opcion=1;
//                    break;
//
//            }
//        }
//    }
//
//
//
//
//    class Evento implements ActionListener {
//
//        @Override
//        public void actionPerformed(ActionEvent evt) {
//
//            if(evt.getSource()==panelVentana.getBtnAgregar()){
//                if(Validar.isNumeric(panelVentana.getTxtTiempo().getText())) {
//                    String nombre=panelVentana.getTxtNombreProceso().getText().replace(" ","");
//                    String tiempo=panelVentana.getTxtTiempo().getText().replace(" ","");
//                    panelVentana.getCola().addProcesos(new Proceso(nombre,Integer.parseInt(tiempo),Integer.parseInt(tiempo),0,(byte)0));
//                    panelVentana.getTxtNombreProceso().setText("");
//                    panelVentana.getTxtTiempo().setText("");
//                }
//            }
//
//            if(evt.getSource()==panelVentana.getBtnSImular()){
//                panelVentana.getCola().mostrarProcesos();
//                panelVentana.vaciarTxt();
//                panelVentana.setVisible(false);
//                panelSimulacion.setVisible(true);
//                for (int i=0;i<panelVentana.getCola().getListaProcesos().size();i++) {
//                    Proceso proceso=panelVentana.getCola().getListaProcesos().get(i);
//                    Object[] filas = {proceso.getNombre(),proceso.getTiempo()};
//                    panelSimulacion.getItemTablaProceso().addRow(filas);
//                }
//                panelSimulacion.getTablaProcesos().setModel(panelSimulacion.getItemTablaProceso());
//            }
//            if(evt.getSource()==panelSimulacion.getBtnEjecutar()){
//                if(!panelVentana.getCola().getListaProcesos().isEmpty()) {
//                    opcion=1;
//                    contador++;
//                    if(contador==1)
//                        hilo.start();
//                    else
//                        hilo.resume();
//                }
//            }
//            if(evt.getSource()==panelSimulacion.getBtnBloquear()){
//                opcion=2;
//                tiempo=0;
//                panelSimulacion.getItemTablaProceso().removeRow(0);
//                //tiempo completo del proceso
//                proceso.setTiempoEjecutado(proceso.getTiempo()+Integer.parseInt(panelSimulacion.getTxtBloquear().getText()));
//              //resta del tiempo - el tiempo hubo la interrupcion, para darnos la foto del proceso
//                proceso.setTiempo(proceso.getTiempo()-sumarTiempo);
//                //tiempo bloqueado
//                proceso.setTiempoBloqueado(Integer.parseInt(panelSimulacion.getTxtBloquear().getText()));
//                // panelVentana.getCola().getListaProcesos().addFirst(proceso);
//
//            }
//            if(evt.getSource()==panelSimulacion.getBtnForzarBloqueo()){
//                opcion=3;
//            }
//        }
//    }
//}
