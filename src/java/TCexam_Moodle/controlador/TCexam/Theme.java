/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TCexam_Moodle.controlador.TCexam;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author JuanPa
 */
//bean controlador 
@ManagedBean
//Bean de sesion
@SessionScoped
public class Theme implements Serializable{

    private String temaT;
    private String temaF="glass-x";
    private List<String> temas;

    /**
     * Creates a new instance of Theme
     */
    public Theme() {
        temas = new ArrayList<String>();
        temas.add("glass-x");
        temas.add("flick");
        temas.add("afternoon");
        temas.add("cupertino");
        temas.add("start");
        temas.add("ui-lightness");
        temas.add("redmond");
    }
    public void seleccionarTema(){
        temaF=temaT;
        System.out.println("tema "+temaF);
    }
    public String getTemaF() {
        return temaF;
    }

    public void setTemaF(String temaF) {
        this.temaF = temaF;
    }

    public String getTemaT() {
        return temaT;
    }

    public void setTemaT(String temaT) {
        this.temaT = temaT;
    }

    public List<String> getTemas() {
        return temas;
    }

    public void setTemas(List<String> temas) {
        this.temas = temas;
    }

  

}
