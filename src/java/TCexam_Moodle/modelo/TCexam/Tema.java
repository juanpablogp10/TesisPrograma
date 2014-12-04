/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TCexam_Moodle.modelo.TCexam;

import java.io.Serializable;
import java.util.StringTokenizer;

/**
 *
 * @author JuanPa
 */
public class Tema  implements Serializable{
    private String tema;
    private String descripcion;

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
      
}
