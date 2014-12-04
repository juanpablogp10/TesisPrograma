/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TCexam_Moodle.modelo.TCexam;

import java.io.Serializable;

/**
 *
 * @author JuanPa
 */
public class Token implements Serializable{
    private String token;
    private String tipo;
    private String estado;

    public Token() {
    }

    public Token(String token, String tipo, String estado) {
        this.token = token;
        this.tipo = tipo;
        this.estado = estado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
