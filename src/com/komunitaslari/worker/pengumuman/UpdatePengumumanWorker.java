package com.komunitaslari.worker.pengumuman;

import com.komunitaslari.dao.DAOFactory;
import com.komunitaslari.dao.PengumumanDAO;
import com.komunitaslari.model.Pengumuman;
import javax.swing.SwingWorker;

public class UpdatePengumumanWorker extends SwingWorker<Boolean, Void> {
    private Pengumuman pengumuman;
    private Exception error;
    
    public UpdatePengumumanWorker(Pengumuman pengumuman) {
        this.pengumuman = pengumuman;
    }
    
    @Override
    protected Boolean doInBackground() throws Exception {
        PengumumanDAO dao = DAOFactory.getPengumumanDAO();
        return dao.update(pengumuman);
    }
    
    @Override
    protected void done() {
        try {
            get(); // Check for exceptions
        } catch (Exception e) {
            error = e;
        }
    }
    
    public boolean hasError() {
        return error != null;
    }
    
    public Exception getError() {
        return error;
    }
}