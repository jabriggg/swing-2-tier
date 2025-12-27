package com.komunitaslari.view.tablemodel;

import com.komunitaslari.model.Pengumuman;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class PengumumanTableModel extends AbstractTableModel {
    private List<Pengumuman> list = new ArrayList<>();

    // Method buat masukin data dari database ke tabel
    public void setData(List<Pengumuman> list) {
        this.list = list;
        fireTableDataChanged();
    }

    // Method buat ambil object pengumuman saat baris tabel diklik
    public Pengumuman getPengumumanAt(int row) {
        return list.get(row);
    }

    // WAJIB ADA: Implementasi standar TableModel
    @Override public int getRowCount() { return list.size(); }
    @Override public int getColumnCount() { return 3; } // Sesuaikan jumlah kolom lo
    @Override public Object getValueAt(int r, int c) {
        Pengumuman p = list.get(r);
        switch(c) {
            case 0: return p.getJudul();
            case 1: return p.getPengirim();
            case 2: return p.getIsi();
            default: return null;
        }
    }
//     @Override
// public String getColumnName(int column) {
//     switch(column) {
//         case 0: return "ID";
//         case 1: return "Judul";
//         case 2: return "Isi Pengumuman";
//         default: return "";
//     }
}
