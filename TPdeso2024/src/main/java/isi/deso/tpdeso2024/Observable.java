/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;

/**
 *
 * @author exero
 */
public interface Observable {
    //public void addObserver(ObserverPedido observer);
    //public boolean removeObserver(ObserverPedido observer);
    public void setChanged();
    public void clearChanged();
    public void notifyObservers();
}
