/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TCexam_Moodle.modelo.TCexam;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JuanPa
 */
public class PreguntaDesarrollo {
     private Respuesta respuesta;

    public PreguntaDesarrollo() {
        respuesta=new Respuesta();
    }

    public Respuesta getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }

   
}
