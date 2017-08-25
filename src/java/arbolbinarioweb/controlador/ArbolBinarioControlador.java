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
    
    private DefaultDiagramModel model;
    
    private ArbolBinario arbol = new ArbolBinario();
    private int dato;
    private boolean verInOrden=false;

    public boolean isVerInOrden() {
        return verInOrden;
    }

    public void setVerInOrden(boolean verInOrden) {
        this.verInOrden = verInOrden;
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
    public ArbolBinarioControlador() {
        
    }
    
    public void adicionarNodo()
    {
        try {
            arbol.adicionarNodo(dato, arbol.getRaiz());
            JsfUtil.addSuccessMessage("El dato ha sido adicionado");
            dato=0;
            pintarArbol();
            
        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }
    
    public void habilitarInOrden()
    {
        try {
            arbol.isLleno();
            verInOrden=true;            
        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }

    public DefaultDiagramModel getModel() {
        return model;
    }

    public void setModel(DefaultDiagramModel model) {
        this.model = model;
    }
    
    
    public void pintarArbol()
    {
        
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);
        model.setConnectionsDetachable(false);
        StraightConnector connector = new StraightConnector();
        connector.setPaintStyle("{strokeStyle:'#404a4e', lineWidth:2}");
        connector.setHoverPaintStyle("{strokeStyle:'#20282b'}");
        model.setDefaultConnector(connector);
        pintarArbol(arbol.getRaiz(), model, null, 30, 0);
        
    }
    
    
    

    private void pintarArbol(Nodo reco,DefaultDiagramModel model, Element padre, int x, int y) {
               
        if (reco != null) {
            Element elementHijo = new Element(reco.getDato());
            
            elementHijo.setX(String.valueOf(x)+"em");
            elementHijo.setY(String.valueOf(y)+"em");
            
            if(padre!=null)
            {
                elementHijo.addEndPoint(new DotEndPoint(EndPointAnchor.TOP));
                DotEndPoint conectorPadre=new DotEndPoint(EndPointAnchor.BOTTOM);
                padre.addEndPoint(conectorPadre);                
                model.connect(new Connection(conectorPadre, elementHijo.getEndPoints().get(0)));        
                
            }    
            
            model.addElement(elementHijo);
            
            pintarArbol(reco.getIzquierda(),model, elementHijo,x-5,y+5);
            pintarArbol(reco.getDerecha(),model,elementHijo,x+5, y+5);
        }
    }
    
    
    
    
}
