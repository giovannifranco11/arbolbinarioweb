/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avl.modelo;

/**
 *
 * @author carloaiza
 */
public class NodoAVL {

    private int dato;
    private NodoAVL izquierda;
    private NodoAVL derecha;
    private int altura;

    public NodoAVL(int dato) {
        this.dato = dato;
        altura=1;
    }

    public int getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }

    public NodoAVL getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodoAVL izquierda) {
        this.izquierda = izquierda;
    }

    public NodoAVL getDerecha() {
        return derecha;
    }

    public void setDerecha(NodoAVL derecha) {
        this.derecha = derecha;
    }

    public boolean isHoja() {
        return izquierda == null && derecha == null;
    }

    public boolean isLleno() {
        return izquierda != null && derecha != null;
    }

    public boolean esVacio() {
        return izquierda == null && derecha == null;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void actualizarAltura() {
        if (esVacio()) {
            altura =1;
        }
        int alturaIzq = 0;
        int alturaDer = 0;
        if (izquierda != null) {
            alturaIzq = izquierda.getAltura();
        }
        if (derecha != null) {
            alturaDer = derecha.getAltura();
        }
        altura=Math.max(alturaIzq, alturaDer) + 1;
    }

    public int obtenerFactorEquilibrio() {
        int alturaIzq = 0;
        int alturaDer = 0;
        if (izquierda != null) {
            alturaIzq = izquierda.getAltura();
        }
        if (derecha != null) {
            alturaDer = derecha.getAltura();
        }

        return alturaDer - alturaIzq;
    }

    @Override
    public String toString() {
        return dato + "\nAltura:" + getAltura() + ", \nFE:" + obtenerFactorEquilibrio();
    }

}
