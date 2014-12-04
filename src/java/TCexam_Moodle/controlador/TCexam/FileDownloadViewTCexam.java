/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCexam_Moodle.controlador.TCexam;

import java.io.InputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
public class FileDownloadViewTCexam {

    private StreamedContent file;

    public FileDownloadViewTCexam() {
        InputStream stream = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/archivosXml/examen.xml");
        file = new DefaultStreamedContent(stream, "application/xml", "examen.xml");
    }

    public StreamedContent getFile() {
        return file;
    }
}
