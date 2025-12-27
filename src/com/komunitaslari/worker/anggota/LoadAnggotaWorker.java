package com.komunitaslari.worker.anggota;

import com.komunitaslari.model.Anggota;
import com.komunitaslari.service.AnggotaService;
import com.komunitaslari.service.impl.AnggotaServiceImpl;
import javax.swing.*;
import java.util.List;

public class LoadAnggotaWorker extends SwingWorker<List<Anggota>, Void> {
    private final AnggotaService service;
    private final WorkerCallback callback;

    public interface WorkerCallback {
        void onStart();
        void onSuccess(List<Anggota> anggotaList);
        void onError(Exception e);
    }

    public LoadAnggotaWorker(WorkerCallback callback) {
        this.service = new AnggotaServiceImpl();
        this.callback = callback;
    }

    @Override
    protected List<Anggota> doInBackground() throws Exception {
        if (callback != null) SwingUtilities.invokeLater(callback::onStart);
        return service.getAllAnggota();
    }

    @Override
    protected void done() {
        try {
            List<Anggota> result = get();
            if (callback != null) callback.onSuccess(result);
        } catch (Exception e) {
            if (callback != null) callback.onError(e);
        }
    }
}