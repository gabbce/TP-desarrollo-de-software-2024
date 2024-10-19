/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author exero
 */
interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}
