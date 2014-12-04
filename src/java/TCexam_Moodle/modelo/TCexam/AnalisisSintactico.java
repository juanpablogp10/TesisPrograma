/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TCexam_Moodle.modelo.TCexam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JuanPa
 */
public class AnalisisSintactico implements Serializable{

    private List<Token> listaToken;
    private String estado;
    private int Id;
    private String elemento2;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }
    public String getElemento2() {
        return elemento2;
    }

    public void setElemento2(String elemento2) {
        this.elemento2 = elemento2;
    }
    

    
    
    public AnalisisSintactico() {
        listaToken = new ArrayList<Token>();
    }
    
    public List<Token> getListaToken() {
        return listaToken;
    }
    
    public AnalisisSintactico(String estado) {
        this.estado = estado;
    }
    
    public void setListaToken(List<Token> listaToken) {
        this.listaToken = listaToken;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
