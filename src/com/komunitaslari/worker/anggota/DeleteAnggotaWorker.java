package com.komunitaslari.worker.anggota;

import com.komunitaslari.service.AnggotaService;
import com.komunitaslari.service.impl.AnggotaServiceImpl;
import javax.swing.*;

public class DeleteAnggotaWorker extends SwingWorker<Boolean, Void> {
    private final AnggotaService service;
    private final int id;
    private final WorkerCallback callback;

    public interface WorkerCallback {
        void onStart();
        void onSuccess();
        void onError(Exception e);
    }

    public DeleteAnggotaWorker(int id, WorkerCallback callback) {
        this.service = new AnggotaServiceImpl();
        this.id = id;
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        if (callback != null) SwingUtilities.invokeLater(callback::onStart);
        service.deleteAnggota(id);
        return true;
    }

    @Override
    protected void done() {
        try {
            get();
            if (callback != null) callback.onSuccess();
        } catch (Exception e) {
            if (callback != null) callback.onError(e);
        }
    }
}