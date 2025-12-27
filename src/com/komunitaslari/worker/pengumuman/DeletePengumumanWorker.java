package com.komunitaslari.worker.pengumuman;

import com.komunitaslari.dao.DAOFactory;
import com.komunitaslari.dao.PengumumanDAO;
import javax.swing.SwingWorker;

public class DeletePengumumanWorker extends SwingWorker<Boolean, Void> {
    private int id;
    private Exception error;
    
    public DeletePengumumanWorker(int id) {
        this.id = id;
    }
    
    @Override
    protected Boolean doInBackground() throws Exception {
        PengumumanDAO dao = DAOFactory.getPengumumanDAO();
        return dao.delete(id);
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