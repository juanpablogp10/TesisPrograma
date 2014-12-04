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
public class Respuesta implements Serializable{
    private String respuesta;
    private String identificador;
    private String estado;

    public Respuesta() {
    }

    public Respuesta(String respuesta, String identificador) {
        this.respuesta = respuesta;
        this.identificador = identificador;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
