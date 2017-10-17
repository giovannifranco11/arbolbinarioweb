/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avl.modelo;

import arbolbinario.modelo.excepciones.ArbolBinarioException;
import java.util.ArrayList;

/**
 *
 * @author carloaiza
 */
public class ArbolBinarioAVL {

    private NodoAVL raiz;

    //public void adicionarNodo()
    public NodoAVL getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoAVL raiz) {
        this.raiz = raiz;
    }

    public void isLleno() throws ArbolBinarioException {
        if (raiz == null) {
            throw new ArbolBinarioException("El árbol está vacío");
        }
    }

    public void adicionarNodo(int dato, NodoAVL ubicacion) throws ArbolBinarioException {
        if (raiz == null) {
            raiz = new NodoAVL(dato);

        } else {

            if (dato < ubicacion.getDato()) {
                if (ubicacion.getIzquierda() == null) {
                    ubicacion.setIzquierda(new NodoAVL(dato));

                } else {
                    adicionarNodo(dato, ubicacion.getIzquierda());
                }
            } else if (dato > ubicacion.getDato()) {
                if (ubicacion.getDerecha() == null) {
                    ubicacion.setDerecha(new NodoAVL(dato));
                } else {
                    adicionarNodo(dato, ubicacion.getDerecha());
                }
            } else {
                throw new ArbolBinarioException("No se puede repetir");
            }
            ubicacion.actualizarAltura();
            balancear(ubicacion);
            
        }
    }

    public ArrayList inOrden() throws ArbolBinarioException {
        isLleno();
        ArrayList l = new ArrayList();
        inOrden(raiz, l);
        return l;
    }

    private void inOrden(NodoAVL reco, ArrayList l) {
        if (reco != null) {
            inOrden(reco.getIzquierda(), l);
            l.add(reco.getDato() + " ");
            inOrden(reco.getDerecha(), l);
        }
    }

    public void llenarArbol(String datos) throws ArbolBinarioException {
        String[] arrayDatos = datos.split(",");
        for (String cadena : arrayDatos) {
            adicionarNodo(Integer.parseInt(cadena), raiz);
        }

    }

    public void rotarSimple(NodoAVL principal, boolean sentido) {
        NodoAVL temp = new NodoAVL(principal.getDato());
        if (!sentido) {
            principal.setDato(principal.getIzquierda().getDato());
            principal.setDerecha(new NodoAVL(temp.getDato()));
            principal.setIzquierda(principal.getIzquierda().getIzquierda());
            principal.getIzquierda().actualizarAltura();
            principal.actualizarAltura();
        } else {
            principal.setDato(principal.getDerecha().getDato());
            principal.setIzquierda(new NodoAVL(temp.getDato()));
            principal.setDerecha(principal.getDerecha().getDerecha());
            principal.getDerecha().actualizarAltura();
            principal.actualizarAltura();

        }
    }

    public void rotarSimpleNuevo(NodoAVL princ, boolean sentido) {

        if (!sentido) {
             if (princ.getDerecha()!= null) {
                NodoAVL nodo = princ.getDerecha();
                princ.setDerecha(new NodoAVL(princ.getDato()));
                princ.getDerecha().setDerecha(nodo);
            } else {
                princ.setDerecha(new NodoAVL(princ.getDato()));
            }
            princ.setDato(princ.getIzquierda().getDato());
            if (princ.getIzquierda().getDerecha()!= null) {
                NodoAVL nodo = princ.getIzquierda().getDerecha();
                 princ.setIzquierda(princ.getIzquierda().getIzquierda());
                 princ.getIzquierda().setDerecha(nodo);
            } else {
                princ.setIzquierda(princ.getIzquierda().getIzquierda());
            }            
            princ.getDerecha().actualizarAltura();
        } else {
            if (princ.getIzquierda() != null) {
                NodoAVL nodo = princ.getIzquierda();
                princ.setIzquierda(new NodoAVL(princ.getDato()));
                princ.getIzquierda().setIzquierda(nodo);
            } else {
                princ.setIzquierda(new NodoAVL(princ.getDato()));
            }
            princ.setDato(princ.getDerecha().getDato());
            if (princ.getDerecha().getIzquierda() != null) {
                NodoAVL nodo = princ.getDerecha().getIzquierda();
                princ.setDerecha(princ.getDerecha().getDerecha());
                princ.getDerecha().setIzquierda(nodo);
            } else {
                princ.setDerecha(princ.getDerecha().getDerecha());
            }            
            princ.getIzquierda().actualizarAltura();
        }
        princ.actualizarAltura();
    }

    public void balancear(NodoAVL principal) {
        if (!principal.esVacio()) {
            int fe = principal.obtenerFactorEquilibrio();
            boolean signo = true;
            if (fe < 0) {
                signo = false;
                fe = fe * -1;
            }
            if (fe >= 2) {
                //Esta desequilibrado
                //hacia donde
                if (signo) {
                    //Desequilibrio a la derecha
                    //Valido desequilibrio simple a la izq
                    if (principal.getDerecha().obtenerFactorEquilibrio() > 0) {
                        //Desequilibrio simple - Rotacion simple
                        rotarSimpleNuevo(principal, signo);

                    } else {
                        //Desequilibrio doble - Rotación doble
                        rotarSimpleNuevo(principal.getDerecha(), false);
                        rotarSimpleNuevo(principal, true);
                    }
                } else {
                    //Desequilibrio a la izquierda
                    //Valido desequilibrio simple a la izq
                    if (principal.getIzquierda().obtenerFactorEquilibrio() < 0) {
                        rotarSimpleNuevo(principal, signo);
                    } else {
                        //Tengo un zig zag
                        //rotar doble
                        rotarSimpleNuevo(principal.getIzquierda(), true);
                        rotarSimpleNuevo(principal, false);
                    }
                }
            }
        }

    }

}
