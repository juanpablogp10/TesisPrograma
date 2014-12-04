/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TCexam_Moodle.modelo.TCexam;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 *
 * @author JuanPa
 */
public class Gaesession implements PhaseListener {
    private static final long serialVersionUID = 1L;

    @Override
    public void afterPhase(PhaseEvent arg0) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("CURRENT_TIME", System.currentTimeMillis());
    }

    @Override
    public void beforePhase(PhaseEvent arg0) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

}
