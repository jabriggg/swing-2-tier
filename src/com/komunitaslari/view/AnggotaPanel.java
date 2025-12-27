package com.komunitaslari.view;

import com.komunitaslari.controller.AnggotaController;
import com.komunitaslari.model.Anggota;
import com.komunitaslari.view.dialog.AnggotaDialog;
import com.komunitaslari.view.tablemodel.AnggotaTableModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

/**
 * Panel untuk manajemen data Anggota
 */
public class AnggotaPanel extends JPanel {
    
    private AnggotaController controller;
    private AnggotaTableModel tableModel;
    
    // Components
    private JTextField txtSearch;
    private JButton btnTambah;
    private JButton btnEdit;
    private JButton btnHapus;
    private JButton btnRefresh;
    private JTable table;
    private JScrollPane scrollPane;
    private JLabel lblLoading;
    private JComboBox<String> cmbFilter;
    
    public AnggotaPanel() {
        initComponents();
        initController();
        controller.loadData();
    }
    
    private void initComponents() {
        setLayout(new MigLayout("fill, insets 15", "[grow]", "[]15[]15[grow]"));
        
        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, "growx, wrap");
        
        // Filter Panel
        JPanel filterPanel = createFilterPanel();
        add(filterPanel, "growx, wrap");
        
        // Table
        createTable();
        add(scrollPane, "grow, push");
        
        // Loading Label
        lblLoading = new JLabel("Loading...", SwingConstants.CENTER);
        lblLoading.setVisible(false);
        lblLoading.setFont(new Font("SansSerif", Font.BOLD, 14));
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new MigLayout("", "[]push[]", ""));
        
        JLabel lblTitle = new JLabel("Data Anggota Komunitas");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        panel.add(lblTitle);
        
        // Button Panel
        JPanel btnPanel = new JPanel(new MigLayout("insets 0", "[]5[]5[]5[]", ""));
        
        btnTambah = new JButton("Tambah");
        btnTambah.addActionListener(e -> showAddDialog());
        btnPanel.add(btnTambah);
        
        btnEdit = new JButton("Edit");
        btnEdit.setEnabled(false);
        btnEdit.addActionListener(e -> showEditDialog());
        btnPanel.add(btnEdit);
        
        btnHapus = new JButton("Hapus");
        btnHapus.setEnabled(false);
        btnHapus.addActionListener(e -> deleteSelected());
        btnPanel.add(btnHapus);
        
        btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> controller.loadData());
        btnPanel.add(btnRefresh);
        
        panel.add(btnPanel);
        
        return panel;
    }
    
    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new MigLayout("", "[]15[]push", ""));
        
        // Search
        JLabel lblSearch = new JLabel("Cari:");
        panel.add(lblSearch);
        
        txtSearch = new JTextField(20);
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                doSearch();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                doSearch();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                doSearch();
            }
            
            private void doSearch() {
                String keyword = txtSearch.getText().trim();
                controller.searchData(keyword);
            }
        });
        panel.add(txtSearch, "growx");
        
        // Filter Status
        JLabel lblFilter = new JLabel("Status:");
        panel.add(lblFilter);
        
        cmbFilter = new JComboBox<>(new String[]{"Semua", "Aktif", "Non-Aktif"});
        cmbFilter.addActionListener(e -> applyFilter());
        panel.add(cmbFilter);
        
        return panel;
    }
    
    private void createTable() {
        tableModel = new AnggotaTableModel();
        table = new JTable(tableModel);
        
        // Table settings
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);
        table.getTableHeader().setReorderingAllowed(false);
        
        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // Nama
        table.getColumnModel().getColumn(2).setPreferredWidth(200); // Email
        table.getColumnModel().getColumn(3).setPreferredWidth(120); // Telepon
        table.getColumnModel().getColumn(4).setPreferredWidth(100); // Tanggal
        table.getColumnModel().getColumn(5).setPreferredWidth(80);  // Status
        
        // Selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean hasSelection = table.getSelectedRow() != -1;
                btnEdit.setEnabled(hasSelection);
                btnHapus.setEnabled(hasSelection);
            }
        });
        
        // Double click untuk edit
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    showEditDialog();
                }
            }
        });
        
        scrollPane = new JScrollPane(table);
    }
    
    private void initController() {
        controller = new AnggotaController(this);
    }
    
    /**
     * Show loading indicator
     */
    public void showLoading(boolean show) {
        SwingUtilities.invokeLater(() -> {
            if (show) {
                // Disable buttons
                btnTambah.setEnabled(false);
                btnEdit.setEnabled(false);
                btnHapus.setEnabled(false);
                btnRefresh.setEnabled(false);
                txtSearch.setEnabled(false);
                
                // Show loading
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            } else {
                // Enable buttons
                btnTambah.setEnabled(true);
                btnRefresh.setEnabled(true);
                txtSearch.setEnabled(true);
                
                // Hide loading
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }
    
    /**
     * Update table dengan data baru
     */
    public void updateTable(List<Anggota> data) {
        SwingUtilities.invokeLater(() -> {
            tableModel.setData(data);
            
            // Clear selection
            table.clearSelection();
            btnEdit.setEnabled(false);
            btnHapus.setEnabled(false);
        });
    }
    
    /**
     * Apply filter berdasarkan status
     */
    private void applyFilter() {
        String selected = (String) cmbFilter.getSelectedItem();
        TableRowSorter<AnggotaTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        
        if ("Aktif".equals(selected)) {
            sorter.setRowFilter(RowFilter.regexFilter("Aktif", 5));
        } else if ("Non-Aktif".equals(selected)) {
            sorter.setRowFilter(RowFilter.regexFilter("Non-Aktif", 5));
        } else {
            sorter.setRowFilter(null);
        }
    }
    
    /**
     * Show dialog tambah anggota
     */
    private void showAddDialog() {
        AnggotaDialog dialog = new AnggotaDialog((Frame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            Anggota anggota = dialog.getAnggota();
            controller.saveAnggota(anggota);
        }
    }
    
    /**
     * Show dialog edit anggota
     */
    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        
        // Convert view row index to model row index
        int modelRow = table.convertRowIndexToModel(selectedRow);
        Anggota anggota = tableModel.getAnggotaAt(modelRow);
        
        if (anggota == null) {
            return;
        }
        
        AnggotaDialog dialog = new AnggotaDialog(
            (Frame) SwingUtilities.getWindowAncestor(this), 
            anggota
        );
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            Anggota updatedAnggota = dialog.getAnggota();
            controller.updateAnggota(updatedAnggota);
        }
    }
    
    /**
     * Delete selected anggota
     */
    private void deleteSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        
        // Convert view row index to model row index
        int modelRow = table.convertRowIndexToModel(selectedRow);
        Anggota anggota = tableModel.getAnggotaAt(modelRow);
        
        if (anggota == null) {
            return;
        }
        
        controller.deleteAnggota(anggota.getId(), anggota.getNama());
    }
}