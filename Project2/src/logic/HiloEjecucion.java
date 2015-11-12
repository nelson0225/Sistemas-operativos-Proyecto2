package logic;

import gui.PanelSimulacion;
import gui.PanelVentana;
import gui.Ventana;

import javax.swing.*;

/**
 * Esta clase define el hilo de los procesesos ejecutados.
 * @author: Camilo Rodríguez
 * @author: Nelson Barreto
 * @version: 9/11/15
 */
public class HiloEjecucion implements Runnable {
    private Global global;
    private int opcion;
    private int sumarTiempo=0;
    private int tiempo=0;
    private  Proceso proceso;
    private PanelVentana   panelVentana;
    private PanelSimulacion panelSimulacion;
    private int control=0;
    private boolean contro=true;
    private int size=0;
    private boolean bloqueo=true;
    /**
    * Constructor para inicializar la variable global de tipo Global.
    */
    public HiloEjecucion(Global global){
        this.global=global;
    }
    /**
     * Método run donde correrá el hilo de los procesos que se ejecutarán.
     */
    @Override
    public void run() {
        panelSimulacion= global.getVentana().getPanelSimulacion();
        panelVentana= global.getVentana().getPanelVentana();
        size=global.getCola().getListaProcesos().size();
        while(true){
            bloqueo=isBloqueo();
            switch (opcion){
                case 1:
//                    System.out.println("opcion 1");
                    try {
                        if (!global.getCola().getListaProcesos().isEmpty()) {
                            proceso = (Proceso) global.getCola().getListaProcesos().get(0);
                            System.out.println("antes de reventarce " + proceso.getInterrupcion() + " " + proceso.getNombre() + " " + proceso.getTiempo() + " " + sumarTiempo +" "+bloqueo);
                            if(proceso.getInterrupcion()==(byte)0) {
                                tiempo++;
                                System.out.println("tiempo "+tiempo);
                                sumarTiempo++;
                                panelSimulacion.getLbnMostrarProceso().setText(proceso.getNombre());
                                if (sumarTiempo >= proceso.getTiempo()) {
                                    control++;
                                    sumarTiempo = 0;
                                    despachados();
                                }
                                panelSimulacion.getLbnTiempoMostrar().setText(Integer.toString(tiempo));

                                Thread.sleep(1000);
                            }else{
                                control++;
                                despachados();
                            }
                        }else{
                            bloqueados();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    break;
                case 2:

                    break;
            }
        }
    }
    private void despachados(){
        if(control<=size){
            System.out.println("entro ak ");
            global.getColaDespachados().getListaProcesos().addFirst(proceso);
            global.getCola().getListaProcesos().deleteFirst();
            if(panelSimulacion.getItemTablaProceso().getRowCount()!=0)
                panelSimulacion.getItemTablaProceso().removeRow(0);
            Object[] filas = {proceso.getNombre(), proceso.getTiempo(), proceso.getTiempoBloqueado()
                    , proceso.getTiempoEjecutado(), proceso.getInterrupcion()};
            panelSimulacion.getItemTablaProcesoAtendidos().addRow(filas);
            panelSimulacion.getTablaProcesosAtendidos().setModel(panelSimulacion.getItemTablaProcesoAtendidos());

            if (global.getCola().getListaProcesos().isEmpty() && control == size) {
                panelSimulacion.getLbnMostrarProceso().setText("");
                tiempo = 0;
                opcion = 0;
                int total = 0;
                for (int i = 0; i < global.getColaDespachados().getListaProcesos().size(); i++) {
                    Proceso proceso1 = (Proceso) global.getColaDespachados().getListaProcesos().get(i);
                    total += proceso1.getTiempoEjecutado();
                }
                panelSimulacion.borrarLbn();
                panelSimulacion.getLbnTotalEjecucion().setText("TIEMPO TOTAL DE EJECUCION= " + Integer.toString(total));
                getGlobal().getVentana().getHiloEjecucion().suspend();
                global.getHiloBloqueadoP().setStart(false);
                getGlobal().getVentana().getHiloBloqueado().suspend();
            }
        }
    }
    private void bloqueados(){
        if(bloqueo==true){
            global.getColaDespachados().getListaProcesos().addFirst(proceso);
            global.getCola().getListaProcesos().deleteFirst();
            if(panelSimulacion.getItemTablaProceso().getRowCount()!=0)
                panelSimulacion.getItemTablaProceso().removeRow(0);
            Object[] filas = {proceso.getNombre(), proceso.getTiempo(), proceso.getTiempoBloqueado()
                    , proceso.getTiempoEjecutado(), proceso.getInterrupcion()};
            panelSimulacion.getItemTablaProcesoAtendidos().addRow(filas);
            panelSimulacion.getTablaProcesosAtendidos().setModel(panelSimulacion.getItemTablaProcesoAtendidos());

            if (global.getCola().getListaProcesos().isEmpty() && control == size) {
                panelSimulacion.getLbnMostrarProceso().setText("");
                tiempo = 0;
                opcion = 0;
                int total = 0;
                for (int i = 0; i < global.getColaDespachados().getListaProcesos().size(); i++) {
                    Proceso proceso1 = (Proceso) global.getColaDespachados().getListaProcesos().get(i);
                    total += proceso1.getTiempoEjecutado();
                }
                panelSimulacion.borrarLbn();
                panelSimulacion.getLbnTotalEjecucion().setText("TIEMPO TOTAL DE EJECUCION= " + Integer.toString(total));
                getGlobal().getVentana().getHiloEjecucion().suspend();
                global.getHiloBloqueadoP().setStart(false);
                getGlobal().getVentana().getHiloBloqueado().suspend();
            }
        }
    }

    public Global getGlobal() {
        return global;
    }

    public void setGlobal(Global global) {
        this.global = global;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    public int getSumarTiempo() {
        return sumarTiempo;
    }

    public void setSumarTiempo(int sumarTiempo) {
        this.sumarTiempo = sumarTiempo;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public boolean isBloqueo() {
        return bloqueo;
    }

    public void setBloqueo(boolean bloqueo) {
        this.bloqueo = bloqueo;
    }

    public int getControl() {
        return control;
    }

    public void setControl(int control) {
        this.control = control;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
