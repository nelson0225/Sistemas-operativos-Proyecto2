package logic;

import gui.Ventana;

/**
 * Esta clase define los hilos y colas que se van a utilizar en la simulación.
 * @author: Camilo Rodríguez
 * @author: Nelson Barreto
 * @version: 9/11/15
 */
public class Global {

    private Cola cola;
    private Cola colaDespachados;
    private Cola colaBloqueos;
    private HiloBloqueado hiloBloqueadoP;
    private HiloEjecucion hiloEjecucion;
    private Ventana ventana;
    /**
    * Constructor para inicializar los hilos y las colas.
    */
    public Global(){
        cola=new Cola();
        colaDespachados=new Cola();
        colaBloqueos=new Cola();
        hiloBloqueadoP=new HiloBloqueado(this);
        hiloEjecucion=new HiloEjecucion(this);
        ventana= new Ventana(this);
    }


    public Cola getCola() {
        return cola;
    }

    public void setCola(Cola cola) {
        this.cola = cola;
    }

    public Cola getColaDespachados() {
        return colaDespachados;
    }

    public void setColaDespachados(Cola colaDespachados) {
        this.colaDespachados = colaDespachados;
    }

    public Cola getColaBloqueos() {
        return colaBloqueos;
    }

    public void setColaBloqueos(Cola colaBloqueos) {
        this.colaBloqueos = colaBloqueos;
    }

    public HiloBloqueado getHiloBloqueadoP() {
        return hiloBloqueadoP;
    }

    public void setHiloBloqueadoP(HiloBloqueado hiloBloqueadoP) {
        this.hiloBloqueadoP = hiloBloqueadoP;
    }

    public HiloEjecucion getHiloEjecucion() {
        return hiloEjecucion;
    }

    public void setHiloEjecucion(HiloEjecucion hiloEjecucion) {
        this.hiloEjecucion = hiloEjecucion;
    }

    public Ventana getVentana() {
        return ventana;
    }

    public void setVentana(Ventana ventana) {
        this.ventana = ventana;
    }
}
