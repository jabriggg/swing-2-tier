package com.komunitaslari.view;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.List;

public class DashboardPanel extends JPanel {
    
    private JLabel lblTotalAnggota, lblAnggotaAktif, lblTotalEvent, lblEventAktif;
    private JProgressBar progressBar;

    public DashboardPanel() {
        initComponents();
        refreshData();
    }
    
    public void initComponents() {
        setLayout(new MigLayout("fill, insets 20", "[grow][grow][grow][grow]", "[]10[]20[grow]"));
        
        // Header & Progress Bar
        JLabel lblTitle = new JLabel("Dashboard Overview");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(lblTitle, "span 3");
        
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);
        add(progressBar, "right, wrap");
        
        // Statistic Cards
        add(createStatCard("Total Anggota", "0", "👥", new Color(52, 152, 219)), "grow");
        add(createStatCard("Anggota Aktif", "0", "✓", new Color(46, 204, 113)), "grow");
        add(createStatCard("Total Event", "0", "🏃", new Color(155, 89, 182)), "grow");
        add(createStatCard("Event Aktif", "0", "📅", new Color(230, 126, 34)), "grow, wrap");

        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setBorder(BorderFactory.createTitledBorder("Informasi Sistem"));
        contentArea.add(new JLabel("Data statistik diperbarui secara real-time.", SwingConstants.CENTER));
        add(contentArea, "span, grow");
    }

    // METHOD-METHOD YANG DIPANGGIL CONTROLLER
    public void showLoading(boolean show) {
        progressBar.setVisible(show);
        setCursor(show ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
    }

    public void updateDashboard(Map<String, Object> data) {
    if (data == null) return;

    // Set teks ke label UI berdasarkan key dari service
    lblTotalAnggota.setText(String.valueOf(data.getOrDefault("totalAnggota", 0)));
    lblAnggotaAktif.setText(String.valueOf(data.getOrDefault("anggotaAktif", 0)));
    lblTotalEvent.setText(String.valueOf(data.getOrDefault("totalEvent", 0)));
    lblEventAktif.setText(String.valueOf(data.getOrDefault("eventAktif", 0)));
    
    // Debugging di console untuk memastikan data sampai
    System.out.println("Dashboard UI Updated: " + data);
}
    public void refreshData() {
    // Inisialisasi Worker untuk mengambil data terbaru
    com.komunitaslari.worker.dashboard.LoadDashboardWorker worker = 
        new com.komunitaslari.worker.dashboard.LoadDashboardWorker(new com.komunitaslari.worker.dashboard.LoadDashboardWorker.WorkerCallback() {
        @Override
        public void onStart() { /* Bisa tambahkan loading state di sini */ }
        
        @Override
        public void onSuccess(java.util.Map<String, Object> data) {
            // Update label UI kamu dengan data dari database
            // Contoh: lblTotalAnggota.setText(data.get("totalAnggota").toString());
        }

        @Override
        public void onError(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Gagal refresh: " + e.getMessage());
        }
    });
    worker.execute();
}

    public void updateAnggotaStats(Map<String, Object> data) { /* Implementasi jika perlu */ }
    public void updateEventStats(Map<String, Object> data) { /* Implementasi jika perlu */ }
    public void updatePengumumanList(Map<String, Object> data) { /* Implementasi jika perlu */ }
    public void updateEventList(Map<String, Object> data) { /* Implementasi jika perlu */ }

    private JPanel createStatCard(String title, String value, String icon, Color color) {
        JPanel card = new JPanel(new MigLayout("fill, insets 15", "[]push[]", "[]5[]"));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        
        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("SansSerif", Font.PLAIN, 30));
        card.add(lblIcon);
        
        JLabel lblVal = new JLabel(value);
        lblVal.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblVal.setForeground(color);
        
        if (title.contains("Total Anggota")) lblTotalAnggota = lblVal;
        else if (title.contains("Aktif") && title.contains("Anggota")) lblAnggotaAktif = lblVal;
        else if (title.contains("Total Event")) lblTotalEvent = lblVal;
        else if (title.contains("Event Aktif")) lblEventAktif = lblVal;
        
        card.add(lblVal, "wrap");
        JLabel lblT = new JLabel(title);
        lblT.setForeground(Color.GRAY);
        card.add(lblT, "span");
        return card;
    }
}