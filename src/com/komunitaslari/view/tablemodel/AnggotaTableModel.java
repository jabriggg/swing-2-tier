package com.komunitaslari.view.tablemodel;

import com.komunitaslari.model.Anggota;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class AnggotaTableModel extends AbstractTableModel {
    private List<Anggota> list = new ArrayList<>();
    private final String[] columns = {"ID", "Nama", "Email", "Telepon", "Tanggal Daftar", "Status"};

    public void setData(List<Anggota> data) {
        this.list = (data != null) ? data : new ArrayList<>();
        fireTableDataChanged();
    }

    public Anggota getAnggotaAt(int row) {
        if (row >= 0 && row < list.size()) {
            return list.get(row);
        }
        return null;
    }

    @Override
    public int getRowCount() { return list.size(); }

    @Override
    public int getColumnCount() { return columns.length; }

    @Override
    public String getColumnName(int col) { return columns[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        Anggota a = list.get(row);
        switch (col) {
            case 0: return a.getId();
            case 1: return a.getNama();
            case 2: return a.getEmail();
            case 3: return a.getTelepon();
            case 4: return a.getTanggalDaftar();
            case 5: return a.getStatus();
            default: return null;
        }
    }
}