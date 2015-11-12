package logic;

import gui.PanelSimulacion;
import gui.PanelVentana;
import gui.Ventana;

/**
 * Esta clase define el hilo de los procesesos bloqueados.
 * @author: Camilo Rodríguez
 * @author: Nelson Barreto
 * @version: 9/11/15
 */
public class HiloBloqueado implements  Runnable {
    private Global global;
    private int sumarTiempo=0;
    private int tiempo=0;
    private PanelSimulacion panelSimulacion;
    private boolean start=true;

    int opcion=0;
    /**
    * Constructor para inicializar la variable global de tipo Global.
    */
    public HiloBloqueado(Global global){
        this.global=global;

    }
    /**
     * Método run donde correrá el hilo de los procesos bloqueados.
     */
    @Override
    public void run() {
        panelSimulacion= global.getVentana().getPanelSimulacion();

        while(true) {
            if (start) {
                System.out.println("mirarlos otro");
                opcion = getOpcion();
                if (opcion == 1) {
                    if (!global.getColaBloqueos().getListaProcesos().isEmpty()) {
                        Proceso proceso = (Proceso) global.getColaBloqueos().getListaProcesos().get(global.getColaBloqueos().getListaProcesos().size() - 1);
                        System.out.println("nodo seleccionado " + proceso.getNombre());

                        while (tiempo < proceso.getTiempoBloqueado() && proceso.getInterrupcion() == 0) {
                            tiempo++;
                            try {
                                panelSimulacion.getLbnTiempoBLoqueado().setText(Integer.toString(tiempo));
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        panelSimulacion.borrarFilas(panelSimulacion.getTablaProcesos());
                        panelSimulacion.getItemTablaProcesoBloqueado().removeRow(0);
                        global.getCola().getListaProcesos().addLast(proceso);
                        for (int i = 0; i < global.getCola().getListaProcesos().size(); i++) {
                            Proceso proceso1 = global.getCola().getListaProcesos().get(i);
                            Object[] filas = {proceso1.getNombre(), proceso1.getTiempo()};
                            panelSimulacion.getItemTablaProceso().addRow(filas);
                            System.out.println("entro aqui las tablas");
                        }
                        panelSimulacion.getTablaProcesos().setModel(panelSimulacion.getItemTablaProceso());
                        global.getColaBloqueos().getListaProcesos().deleteLast();
                        tiempo = 0;
                        global.getHiloEjecucion().setBloqueo(true);
                    }

                }
                if (opcion == 2) {
                    if (!global.getColaBloqueos().getListaProcesos().isEmpty()) {
                        Proceso proceso = (Proceso) global.getColaBloqueos().getListaProcesos().get(global.getColaBloqueos().getListaProcesos().size() - 1);
                        panelSimulacion.borrarFilas(panelSimulacion.getTablaProcesos());
                        global.getCola().getListaProcesos().addLast(proceso);
                        for (int i = 0; i < global.getCola().getListaProcesos().size(); i++) {
                            Proceso proceso1 = global.getCola().getListaProcesos().get(i);
                            Object[] filas = {proceso1.getNombre(), proceso1.getTiempo()};
                            panelSimulacion.getItemTablaProceso().addRow(filas);
                            System.out.println("entro aqui las tablas");
                        }
                        panelSimulacion.getTablaProcesos().setModel(panelSimulacion.getItemTablaProceso());
                        global.getColaBloqueos().getListaProcesos().deleteLast();
                        tiempo = 0;
                    }
                }

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

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}
