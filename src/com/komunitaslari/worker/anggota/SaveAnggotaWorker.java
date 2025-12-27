package com.komunitaslari.worker.anggota;

import com.komunitaslari.model.Anggota;
import com.komunitaslari.service.AnggotaService;
import com.komunitaslari.service.impl.AnggotaServiceImpl;
import javax.swing.*;

public class SaveAnggotaWorker extends SwingWorker<Boolean, Void> {
    private final Anggota anggota;
    private final WorkerCallback callback;
    private final AnggotaService service;

    public interface WorkerCallback {
        void onStart();
        void onSuccess();
        void onError(Exception e);
    }

    public SaveAnggotaWorker(Anggota anggota, WorkerCallback callback) {
        this.anggota = anggota;
        this.callback = callback;
        this.service = new AnggotaServiceImpl();
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        if (callback != null) SwingUtilities.invokeLater(callback::onStart);
        service.addAnggota(anggota);
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