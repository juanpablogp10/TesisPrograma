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
public class PreguntaRespuestaMultiple extends Pregunta{
  
   List<Respuesta> listaRespuesta;
   

    public PreguntaRespuestaMultiple() {
        listaRespuesta=new ArrayList<Respuesta>();
    }

    public List<Respuesta> getListaRespuesta() {
        return listaRespuesta;
    }

    public void setListaRespuesta(List<Respuesta> listaRespuesta) {
        this.listaRespuesta = listaRespuesta;
    }
    
}
