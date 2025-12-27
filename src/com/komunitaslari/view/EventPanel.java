package com.komunitaslari.view;

import com.komunitaslari.controller.EventController;
import com.komunitaslari.model.Event;
import com.komunitaslari.util.MessageUtil;
import com.komunitaslari.view.tablemodel.EventTableModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;

public class EventPanel extends JPanel {

    private final EventController controller;
    private final EventTableModel tableModel;
    
    // UI Components
    private JTable table;
    private JTextField txtSearch;
    private JButton btnTambah, btnEdit, btnHapus, btnRefresh;
    private JComboBox<String> cmbFilterStatus;

    public EventPanel() {
        // Inisialisasi table model duluan
        tableModel = new EventTableModel();
        
        // Setup UI
        initComponents();
        
        // Inisialisasi controller
        controller = new EventController(this);
        
        // Load data pertama kali
        controller.loadData();
    }

    private void initComponents() {
        // Layout utama: 3 baris (Header, Filter, Table)
        setLayout(new MigLayout("fill, ins 20", "[grow]", "[][][grow]"));

        // --- BARIS 1: Header & Buttons ---
        JPanel headerPanel = new JPanel(new MigLayout("ins 0", "[]push[]", ""));
        JLabel lblTitle = new JLabel("Manajemen Event Lari");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        
        JPanel btnPanel = new JPanel(new MigLayout("ins 0", "[]5[]5[]5[]"));
        btnTambah = new JButton("Tambah Event");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");
        btnRefresh = new JButton("Refresh");
        
        btnPanel.add(btnTambah);
        btnPanel.add(btnEdit);
        btnPanel.add(btnHapus);
        btnPanel.add(btnRefresh);
        
        headerPanel.add(lblTitle);
        headerPanel.add(btnPanel);
        add(headerPanel, "growx, wrap 15");

        // --- BARIS 2: Search & Filter Bar ---
        JPanel filterPanel = new JPanel(new MigLayout("ins 0", "[]10[grow]20[]10[]"));
        txtSearch = new JTextField();
        cmbFilterStatus = new JComboBox<>(new String[]{"Semua Status", "OPEN", "CLOSED", "DONE"});
        
        filterPanel.add(new JLabel("Cari Event:"));
        filterPanel.add(txtSearch, "growx");
        filterPanel.add(new JLabel("Status:"));
        filterPanel.add(cmbFilterStatus);
        
        add(filterPanel, "growx, wrap 10");

        // --- BARIS 3: Table ---
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, "grow");

        setupListeners();
    }

    private void setupListeners() {
        // Fitur Pencarian Otomatis (Saat ngetik)
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { performSearch(); }
            @Override public void removeUpdate(DocumentEvent e) { performSearch(); }
            @Override public void changedUpdate(DocumentEvent e) { performSearch(); }
            
            private void performSearch() {
                String keyword = txtSearch.getText().trim();
                if (keyword.isEmpty()) {
                    controller.loadData();
                } else {
                    controller.searchData(keyword);
                }
            }
        });

        // Fitur Filter Status
        cmbFilterStatus.addActionListener(e -> {
            String selected = (String) cmbFilterStatus.getSelectedItem();
            if (selected.equals("Semua Status")) {
                controller.loadData();
            } else {
                controller.loadByStatus(selected);
            }
        });

        // Tombol Actions
        btnRefresh.addActionListener(e -> controller.loadData());
        
        btnHapus.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                // Ambil ID dari kolom 0 (sesuaikan dengan table model lo)
                int id = (int) tableModel.getValueAt(row, 0);
                String nama = (String) tableModel.getValueAt(row, 1);
                controller.deleteEvent(id, nama);
            } else {
                MessageUtil.showError(this, "Silakan pilih event yang ingin dihapus dari tabel.");
            }
        });

        // Button Tambah & Edit (Lo bisa arahin ke JDialog lo sendiri nanti)
        btnTambah.addActionListener(e -> {
            // Contoh: EventDialog dialog = new EventDialog(null, true);
            // dialog.setVisible(true);
        });
    }

    // --- METHOD YANG DIBUTUHKAN CONTROLLER ---

    /**
     * Mengatur kursor loading dan status tombol
     */
    public void showLoading(boolean isLoading) {
        SwingUtilities.invokeLater(() -> {
            if (isLoading) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                btnRefresh.setEnabled(false);
                btnHapus.setEnabled(false);
            } else {
                setCursor(Cursor.getDefaultCursor());
                btnRefresh.setEnabled(true);
                btnHapus.setEnabled(true);
            }
        });
    }

    /**
     * Memperbarui isi tabel dengan list data dari worker
     */
    public void updateTable(List<Event> data) {
        SwingUtilities.invokeLater(() -> {
            tableModel.setData(data); // Pastikan EventTableModel punya method setData
            tableModel.fireTableDataChanged();
        });
    }
}