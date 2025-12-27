package com.komunitaslari.worker;

import javax.swing.SwingWorker;

public abstract class BaseWorker<T, V> extends SwingWorker<T, V> {
    protected Exception error;

    public boolean hasError() { return error != null; }
    public Exception getError() { return error; }
}