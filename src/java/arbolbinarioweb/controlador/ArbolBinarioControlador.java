/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolbinarioweb.controlador;

import arbolbinario.modelo.ArbolBinario;
import arbolbinario.modelo.Nodo;
import arbolbinario.modelo.excepciones.ArbolBinarioException;
import arbolbinarioweb.controlador.util.JsfUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.Connector;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;

/**
 *
 * @author carloaiza
 */
@Named(value = "arbolBinarioControlador")
@SessionScoped
public class ArbolBinarioControlador implements Serializable {
    
    Arbol miArbol = new Arbol();
    
    public ArbolBinarioControlador(){
        
    }

    private DefaultDiagramModel model;
    private DefaultDiagramModel modelArbol2;

    private ArbolBinario arbol = new ArbolBinario();
    private int dato;
    private boolean verInOrden = false;
    private boolean verPreOrden = false;
    private boolean verPostOrden = false;

    private String datoscsv = "18,15,13,17,8,14,-8,10,59,28,80,78,90";
 // private int numero = Integer.parseInt(JOptionPane.showInputDialog("Digite un numero"));
    private int terminado;
    private ArbolBinario arbolTerminados = new ArbolBinario();

    public ArbolBinario getArbolTerminados() {
        return arbolTerminados;
    }

    public void setArbolTerminados(ArbolBinario arbolTerminados) {
        this.arbolTerminados = arbolTerminados;
    }

    public int getTerminado() {
        return terminado;
    }

    public void setTerminado(int terminado) {
        this.terminado = terminado;
    }

    public DefaultDiagramModel getModelArbol2() {
        return modelArbol2;
    }

    public void setModelArbol2(DefaultDiagramModel modelArbol2) {
        this.modelArbol2 = modelArbol2;
    }

    public String getDatoscsv() {
        return datoscsv;
    }

    public void setDatoscsv(String datoscsv) {
        this.datoscsv = datoscsv;
    }
    
    /*
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    */
    
// InOrden
    public boolean isVerInOrden() {
        return verInOrden;
    }
    
    public void setVerInOrden(boolean verInOrden) {
        this.verInOrden = verInOrden;
    }
    
 // PreOrden 
    public boolean isVerPreorden(){
            return verPreOrden;
    }
    
    public void setVerPreOrden(boolean verPreOrden){
        this.verPreOrden = verPreOrden;
    }
 
 // PostOrden
    public boolean isVerPostOrden() {
        return verPostOrden;
    }
    
    public void setVerPostOrden(boolean verPostorden){
        this.verPostOrden = verPostOrden;
    }
    

    public int getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }

    public ArbolBinario getArbol() {
        return arbol;
    }
    
    
    public void setArbol(ArbolBinario arbol) {
        this.arbol = arbol;
    }

    /**
     * Creates a new instance of ArbolBinarioControlador
     */
   

    public void adicionarNodo() {
        try {
            arbol.adicionarNodo(dato, arbol.getRaiz());
            JsfUtil.addSuccessMessage("El dato ha sido adicionado");
            dato = 0;
            pintarArbol();

        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }

    public void habilitarInOrden() {
        try {
            arbol.isLleno();
            verInOrden = true;
        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }
    
    public void habilitarPreOrden() {
        try {
            arbol.isLleno();
            verPreOrden = true;
        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }

    public void habilitarPostOrden(){
        try{
            arbol.isLleno();
            verPostOrden = true;
        } catch (ArbolBinarioException ex){
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }
    
    /*public String imprimirPorNiveles(){
        ArrayList it = this.miArbol.impNivel();
        return (recorrido(it, "Imprimir por niveles"));
    }
    */
    
    public String borrar(Integer dato){
        Integer x = this.arbol.borrarNodo(dato);
        if(x == null){
            return ("No existe el dato en el arbol");
        }
        return ("Borrado el dato: " + x.toString());
    }
    
    
    public String recorrido(ArrayList it, String msg){
        int i =0;
        String r = msg + "\n";
        while(i < it.size()){
            r += "\t" + it.get(i).toString() + "\n";
            i++;
        }
        return (r);
    }
    
  //Niveles sin orden
    public String imprimirPorNiveles(){
        ArrayList it = this.arbol.impNiveles();
        return (recorrido(it, "Imprimir por Niveles sin orden"));
    }
    
    //Niveles con orden
    public String porNivel(){
        this.arbol.alturaArbol();
        ArrayList it = this.arbol.imprimirNivel();
        return (recorrido(it, "Los Nodos en orden son: "));
    }
    
    // Hojas
    public String darHojas(){
        ArrayList it = this.arbol.getHojas();
        return (recorrido(it, "Las hojas del Arbol son: "));
    }
    
    public String esta(Integer dato){
        boolean siEsta = this.arbol.buscar(dato);
        String r = "El Nodo : " + dato.toString() + "\n";
        r += siEsta ? " Si se encuentra en el arbol" : " No se encuentra en el arbol";
        return (r);
    }
    
    
    public DefaultDiagramModel getModel() {
        return model;
    }

    public void setModel(DefaultDiagramModel model) {
        this.model = model;
    }

    public void pintarArbol() {

        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);
        model.setConnectionsDetachable(false);
        StraightConnector connector = new StraightConnector();
        connector.setPaintStyle("{strokeStyle:'red', lineWidth:2}");
        connector.setHoverPaintStyle("{strokeStyle:'green'}");
        model.setDefaultConnector(connector);
        pintarArbol(arbol.getRaiz(), model, null, 30, 0);

    }

    private void pintarArbol(Nodo reco, DefaultDiagramModel model, Element padre, int x, int y) {

        if (reco != null) {
            Element elementHijo = new Element(reco.getDato() + " (G:" +reco.obtenerAlturaNodo() + " H:"+ reco.obtenerAlturaNodo()+")");

            elementHijo.setX(String.valueOf(x) + "em");
            elementHijo.setY(String.valueOf(y) + "em");

            if (padre != null) {
                elementHijo.addEndPoint(new DotEndPoint(EndPointAnchor.TOP));
                DotEndPoint conectorPadre = new DotEndPoint(EndPointAnchor.BOTTOM);
                padre.addEndPoint(conectorPadre);
                model.connect(new Connection(conectorPadre, elementHijo.getEndPoints().get(0)));

            }

            model.addElement(elementHijo);

            pintarArbol(reco.getIzquierda(), model, elementHijo, x - 5, y + 5);
            pintarArbol(reco.getDerecha(), model, elementHijo, x + 5, y + 5);
        }
    }
    
    /*
    public void buscarNodo(){
        int dato_a_buscar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese Numero"));
        String salida = this.esta(dato_a_buscar);
        }
    */
    
    public void extraerDatos() {
        try {
            arbol.setRaiz(null);
            arbol.llenarArbol(datoscsv);
            pintarArbol();
            datoscsv = "";
        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage("Los datos ingresados no tienen el formato separado por comas");
        }
    }

    public void buscarTerminadosEn() {
        for (Element ele : model.getElements()) {
            ele.setStyleClass("ui-diagram-element");
            int numTerm = Integer.parseInt(ele.getData().toString());
            if (numTerm < 0) {
                numTerm *= -1;
            }
            if (numTerm % 10 == terminado) {
                ele.setStyleClass("ui-diagram-element-busc");
            }
        }
    }

    public void encontrarTerminadosEn() {
        try {
            arbolTerminados = new ArbolBinario();
            encontrarTerminadosEn(arbol.getRaiz());
            pintarArbolTerminados();
        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage("Ocurrio un error generando el árbol de terminados" + ex);
        }
    }

    private void encontrarTerminadosEn(Nodo reco) throws ArbolBinarioException {
        if (reco != null) {
            int numTerm= reco.getDato();
            if(numTerm<0)
            {
                numTerm *=-1;
            }
            if(numTerm%10==terminado)
            {
                arbolTerminados.adicionarNodo(reco.getDato(), arbolTerminados.getRaiz());
            }
            encontrarTerminadosEn(reco.getIzquierda());
            encontrarTerminadosEn(reco.getDerecha());
        }
    }

    public void pintarArbolTerminados() {

        modelArbol2 = new DefaultDiagramModel();
        modelArbol2.setMaxConnections(-1);
        modelArbol2.setConnectionsDetachable(false);
        StraightConnector connector = new StraightConnector();
        connector.setPaintStyle("{strokeStyle:'#404a4e', lineWidth:2}");
        connector.setHoverPaintStyle("{strokeStyle:'#20282b'}");
        modelArbol2.setDefaultConnector(connector);
        pintarArbolTerminados(arbolTerminados.getRaiz(), modelArbol2, null, 30, 0);

    }

    private void pintarArbolTerminados(Nodo reco, DefaultDiagramModel model, Element padre, int x, int y) {

        if (reco != null) {
            Element elementHijo = new Element(reco.getDato());

            elementHijo.setX(String.valueOf(x) + "em");
            elementHijo.setY(String.valueOf(y) + "em");

            if (padre != null) {
                elementHijo.addEndPoint(new DotEndPoint(EndPointAnchor.TOP));
                DotEndPoint conectorPadre = new DotEndPoint(EndPointAnchor.BOTTOM);
                padre.addEndPoint(conectorPadre);
                model.connect(new Connection(conectorPadre, elementHijo.getEndPoints().get(0)));

            }

            model.addElement(elementHijo);

            pintarArbolTerminados(reco.getIzquierda(), model, elementHijo, x - 5, y + 5);
            pintarArbolTerminados(reco.getDerecha(), model, elementHijo, x + 5, y + 5);
        }
    }
    
    
    public void podarArbol(){
        this.arbol.podar();
    }

}
