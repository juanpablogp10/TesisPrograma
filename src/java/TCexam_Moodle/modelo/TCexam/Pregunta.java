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
public class Pregunta implements Serializable{
    private String pregunta;
    private String identificador;
    private String estado;
    private String tipo;
    private List<Respuesta> listaRespuesta;

    public Pregunta() {
        listaRespuesta=new ArrayList<Respuesta>();
    }

    public Pregunta(String pregunta, String identificador) {
        this.pregunta = pregunta;
        this.identificador = identificador;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
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

    public List<Respuesta> getListaRespuesta() {
        return listaRespuesta;
    }

    public void setListaRespuesta(List<Respuesta> listaRespuesta) {
        this.listaRespuesta = listaRespuesta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
