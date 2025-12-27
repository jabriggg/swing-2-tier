package com.komunitaslari.view.tablemodel;

import com.komunitaslari.model.Event;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class EventTableModel extends AbstractTableModel {
    private List<Event> list = new ArrayList<>();
    private final String[] columns = {"ID", "Nama Event", "Tanggal", "Lokasi", "Status"};

    // Method ini WAJIB ada buat dipanggil Controller/Panel
    public void setData(List<Event> data) {
        this.list = (data != null) ? data : new ArrayList<>();
    }

    @Override
    public int getRowCount() { return list.size(); }

    @Override
    public int getColumnCount() { return columns.length; }

    @Override
    public String getColumnName(int col) { return columns[col]; }

   @Override
public Object getValueAt(int row, int col) {
    Event e = list.get(row);
    switch (col) {
        case 0: return e.getId();
        case 1: return e.getNamaEvent(); // Cek di model lo, harusnya getNamaEvent
        case 2: return e.getTanggalEvent(); // Cek di model lo, harusnya getTanggalEvent
        case 3: return e.getLokasi();
        case 4: return e.getStatus();
        default: return null;
        }
    }
}