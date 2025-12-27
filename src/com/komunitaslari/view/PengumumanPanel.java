package com.komunitaslari.view;

import com.komunitaslari.controller.PengumumanController;
import com.komunitaslari.model.Pengumuman;
import com.komunitaslari.view.dialog.PengumumanDialog;
import com.komunitaslari.view.tablemodel.PengumumanTableModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;

/**
 * Panel untuk manajemen Pengumuman dan Broadcast Realtime
 */
public class PengumumanPanel extends JPanel {
    
    private PengumumanController controller;
    private PengumumanTableModel tableModel;
    
    // Components
    private JTextField txtSearch;
    private JButton btnBroadcast;
    private JButton btnLihatDetail;
    private JButton btnHapus;
    private JButton btnRefresh;
    private JTable table;
    private JScrollPane scrollPane;
    
    public PengumumanPanel() {
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
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new MigLayout("", "[]push[]", ""));
        
        JLabel lblTitle = new JLabel("📢 Pengumuman Komunitas");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        panel.add(lblTitle);
        
        // Button Panel
        JPanel btnPanel = new JPanel(new MigLayout("insets 0", "[]5[]5[]5[]", ""));
        
        btnBroadcast = new JButton("📣 Broadcast");
        btnBroadcast.setBackground(new Color(52, 152, 219));
        btnBroadcast.setForeground(Color.WHITE);
        btnBroadcast.setFocusPainted(false);
        btnBroadcast.addActionListener(e -> showBroadcastDialog());
        btnPanel.add(btnBroadcast);
        
        btnLihatDetail = new JButton("👁 Lihat Detail");
        btnLihatDetail.setEnabled(false);
        btnLihatDetail.addActionListener(e -> showDetail());
        btnPanel.add(btnLihatDetail);
        
        btnHapus = new JButton("🗑 Hapus");
        btnHapus.setEnabled(false);
        btnHapus.addActionListener(e -> deleteSelected());
        btnPanel.add(btnHapus);
        
        btnRefresh = new JButton("🔄 Refresh");
        btnRefresh.addActionListener(e -> controller.loadData());
        btnPanel.add(btnRefresh);
        
        panel.add(btnPanel);
        
        return panel;
    }
    
    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new MigLayout("", "[]15[]", ""));
        
        // Search
        JLabel lblSearch = new JLabel("🔍 Cari:");
        panel.add(lblSearch);
        
        txtSearch = new JTextField(30);
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
        
        // Info label
        JLabel lblInfo = new JLabel("💡 Klik 2x pada baris untuk lihat detail lengkap");
        lblInfo.setFont(new Font("SansSerif", Font.ITALIC, 11));
        lblInfo.setForeground(Color.GRAY);
        panel.add(lblInfo, "gapleft 20");
        
        return panel;
    }
    
    private void createTable() {
        tableModel = new PengumumanTableModel();
        table = new JTable(tableModel);
        
        // Table settings
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.getTableHeader().setReorderingAllowed(false);
        
        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(250);  // Judul
        table.getColumnModel().getColumn(2).setPreferredWidth(300);  // Isi (preview)

        // Selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean hasSelection = table.getSelectedRow() != -1;
                btnLihatDetail.setEnabled(hasSelection);
                btnHapus.setEnabled(hasSelection);
            }
        });
        
        // Double click untuk lihat detail
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    showDetail();
                }
            }
        });
        
        scrollPane = new JScrollPane(table);
    }
    
    private void initController() {
        controller = new PengumumanController(this);
    }
    
    /**
     * Show loading indicator
     */
    public void showLoading(boolean show) {
        SwingUtilities.invokeLater(() -> {
            if (show) {
                // Disable buttons
                btnBroadcast.setEnabled(false);
                btnLihatDetail.setEnabled(false);
                btnHapus.setEnabled(false);
                btnRefresh.setEnabled(false);
                txtSearch.setEnabled(false);
                
                // Show loading cursor
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            } else {
                // Enable buttons
                btnBroadcast.setEnabled(true);
                btnRefresh.setEnabled(true);
                txtSearch.setEnabled(true);
                
                // Hide loading cursor
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }
    
    /**
     * Update table dengan data baru
     */
    public void updateTable(List<Pengumuman> data) {
        SwingUtilities.invokeLater(() -> {
            tableModel.setData(data);
            
            // Clear selection
            table.clearSelection();
            btnLihatDetail.setEnabled(false);
            btnHapus.setEnabled(false);
        });
    }
    
    /**
     * Show dialog broadcast pengumuman
     */
    private void showBroadcastDialog() {
        PengumumanDialog dialog = new PengumumanDialog(
            (Frame) SwingUtilities.getWindowAncestor(this)
        );
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            Pengumuman pengumuman = dialog.getPengumuman();
            controller.broadcastPengumuman(pengumuman);
        }
    }
    
    /**
     * Show detail pengumuman lengkap
     */
    private void showDetail() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        
        Pengumuman pengumuman = tableModel.getPengumumanAt(selectedRow);
        if (pengumuman != null) {
            controller.showDetail(pengumuman);
        }
    }
    
    /**
     * Delete selected pengumuman
     */
    private void deleteSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        
        Pengumuman pengumuman = tableModel.getPengumumanAt(selectedRow);
        if (pengumuman != null) {
            controller.deletePengumuman(pengumuman.getId(), pengumuman.getJudul());
        }
    }
}