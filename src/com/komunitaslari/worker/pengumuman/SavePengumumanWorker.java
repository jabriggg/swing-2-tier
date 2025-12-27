package com.komunitaslari.worker.pengumuman;

import com.komunitaslari.dao.DAOFactory;
import com.komunitaslari.dao.PengumumanDAO;
import com.komunitaslari.model.Pengumuman;
import javax.swing.SwingWorker;

public class SavePengumumanWorker extends SwingWorker<Integer, Void> {
    private Pengumuman pengumuman;
    private boolean isBroadcast;
    private Exception error;
    
    public SavePengumumanWorker(Pengumuman pengumuman, boolean isBroadcast) {
        this.pengumuman = pengumuman;
        this.isBroadcast = isBroadcast;
    }
    
    @Override
    protected Integer doInBackground() throws Exception {
        PengumumanDAO dao = DAOFactory.getPengumumanDAO();
        return dao.create(pengumuman);
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