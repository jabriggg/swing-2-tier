package com.komunitaslari.worker.anggota;

import com.komunitaslari.model.Anggota;
import com.komunitaslari.service.AnggotaService;
import com.komunitaslari.service.impl.AnggotaServiceImpl;
import javax.swing.*;

public class UpdateAnggotaWorker extends SwingWorker<Boolean, Void> {
    private final AnggotaService service;
    private final Anggota anggota;
    private final WorkerCallback callback;

    public interface WorkerCallback {
        void onStart();
        void onSuccess();
        void onError(Exception e);
    }

    public UpdateAnggotaWorker(Anggota anggota, WorkerCallback callback) {
        this.service = new AnggotaServiceImpl();
        this.anggota = anggota;
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        if (callback != null) SwingUtilities.invokeLater(callback::onStart);
        service.updateAnggota(anggota);
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