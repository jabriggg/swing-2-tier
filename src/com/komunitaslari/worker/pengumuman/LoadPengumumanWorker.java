package com.komunitaslari.worker.pengumuman;

import com.komunitaslari.dao.DAOFactory;
import com.komunitaslari.dao.PengumumanDAO;
import com.komunitaslari.model.Pengumuman;
import javax.swing.SwingWorker;
import java.util.List;

public class LoadPengumumanWorker extends SwingWorker<List<Pengumuman>, Void> {
    private String keyword;
    private Exception error;
    
    public LoadPengumumanWorker() {
        this.keyword = null;
    }
    
    public LoadPengumumanWorker(String keyword) {
        this.keyword = keyword;
    }
    
    @Override
    protected List<Pengumuman> doInBackground() throws Exception {
        PengumumanDAO dao = DAOFactory.getPengumumanDAO();
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            return dao.search(keyword);
        } else {
            return dao.findAll();
        }
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