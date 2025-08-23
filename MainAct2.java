/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DWFan95
 */
class Nodo {
    Object dato;
    Nodo siguiente;
    Nodo(Object dato){
        this.dato = dato;
    }
}

class ListaEnlazada{
    private Nodo cabeza, cola;
    private int tamaño;
    
    public void insertFinal(Object dato){
        Nodo nuevo = new Nodo(dato);
        if (cabeza == null){
            cabeza = cola = nuevo;
        } else {
            cola.siguiente = nuevo;
            cola = nuevo;
        }
        tamaño++;
    }
    public void insertInicio(Object dato){
        Nodo nuevo = new Nodo(dato);
        if(cabeza == null){
            cabeza = cola = nuevo;
        } else {
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
        }
        tamaño++;
    }
    public Object delInicio(){
        if (cabeza == null) throw new RuntimeException("VACIO");
        Object valor = cabeza.dato;
        cabeza = cabeza.siguiente;
        if (cabeza == null) cola = null;
        tamaño--;
        return valor;
    }
    public Object getInicio(){
        if (cabeza == null) throw new RuntimeException("VACIO");
        return cabeza.dato;
    }
    public boolean vacio() { return tamaño == 0; }
    public int tamaño() { return tamaño; }
    public void mostrar(){
        Nodo actual = cabeza;
        while (actual != null){
            System.out.print(actual.dato + " -> ");
            actual = actual.siguiente;
        }
        System.out.println("VACIO");
    }
}

class Pila {
    private ListaEnlazada lista = new ListaEnlazada();
    public void push(Object dato) {lista.insertInicio(dato);}
    public Object pop() {return lista.delInicio();}
    public Object peek() {return lista.getInicio();}
    public boolean mt() {return lista.vacio();}
    public int size() {return lista.tamaño();}
    public void show() {lista.mostrar();}
}

class Cola {
    private ListaEnlazada lista = new ListaEnlazada();
    public void nq(Object dato) {lista.insertFinal(dato);}
    public Object dq() {return lista.delInicio();}
    public Object peek() {return lista.getInicio();}
    public boolean mt() {return lista.vacio();}
    public int size() {return lista.tamaño();}
    public void show() {lista.mostrar();}
}


class Proceso {
    private final int pid;
    private final String nombre;
    
    public Proceso(int pid, String nombre){
        this.pid = pid;
        this.nombre = nombre;
    }
    
    @Override
    public String toString(){
        return String.format("PID:%d[%s]", pid, nombre);
    }
}

class Interrupcion{
    private final String tipo;
    
    public Interrupcion(String tipo){
        this.tipo = tipo;
    }
    
    @Override
    public String toString(){
        return "Int(" + tipo + ")";
    }
}

class SuperDOS {
    private final Cola readyQueue = new Cola();
    private final Pila interruptStack = new Pila();
    private int nextPid = 1;
    
    public void crearProceso(String nombre){
        Proceso p = new Proceso(nextPid++, nombre);
        readyQueue.nq(p);
        System.out.println("AGREGADO: " + p);
    }
    public void dispararInterrupcion(String tipo){
        Interrupcion i = new Interrupcion(tipo);
        interruptStack.push(i);
        System.out.println("INTERRUPIDO: " + i);
    }
    public void ejecutarCiclo(){
        if(!interruptStack.mt()){
            Interrupcion i = (Interrupcion) interruptStack.pop();
            System.out.println("ATENDIENDO: " + i);
        } else if (!readyQueue.mt()){
            Proceso p = (Proceso) readyQueue.dq();
            System.out.println("EJECUTANDO: " + p);
        } else {
            System.out.println("INACTIVO");
        }
    }
    public void estadoActual(){
        System.out.print("READY QUEUE: ");
        readyQueue.show();
        System.out.print("INTERRUPT STACK: ");
        interruptStack.show();
    }
}

public class MainAct2 {
    public static void main(String[] args){
        SuperDOS dos = new SuperDOS();
        
        dos.crearProceso("EDITOR");
        dos.crearProceso("COMPILADOR");
        dos.crearProceso("NAVEGADOR");
        
        dos.estadoActual();
        
        dos.dispararInterrupcion("TECLADO");
        dos.dispararInterrupcion("TEMPORIZADOR");
        
        for (int i = 0; i < 6; i++){
            dos.ejecutarCiclo();
            dos.estadoActual();
        }
    }
}
