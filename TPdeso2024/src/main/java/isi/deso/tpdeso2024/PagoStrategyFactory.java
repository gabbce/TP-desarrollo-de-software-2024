/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tpdeso2024;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author augus
 */

// NO LO USAMOS POR AHORA
public class PagoStrategyFactory {
    private Map<PagoType,Pago> strategies = new HashMap<>();
    
    public PagoStrategyFactory(Set<Pago> sp){this.createStrategy(sp);}
    
    public void createStrategy(Set<Pago> sp){
    for(Pago p:sp){
        strategies.put(p.getStrategyType(),p);
        }
    }
    
    public Pago findPago(PagoType tipo){
        return strategies.get(tipo);
    }
 
}
