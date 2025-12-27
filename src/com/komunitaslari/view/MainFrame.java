package com.komunitaslari.view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Main Frame - Window utama aplikasi
 */
public class MainFrame extends JFrame {
    
    private JPanel contentPanel;
    private CardLayout cardLayout;
    
    // Menu buttons
    private JButton btnDashboard;
    private JButton btnAnggota;
    private JButton btnEvent;
    private JButton btnPengumuman;
    
    // Panels
    private DashboardPanel dashboardPanel;
    private AnggotaPanel anggotaPanel;
    private PengumumanPanel pengumumanPanel;
    // EventPanel akan dibuat nanti
    
    public MainFrame() {
        initComponents();
        showDashboard(); // Default tampilan
    }
    
    private void initComponents() {
        setTitle("Sistem Manajemen Komunitas Lari Cirebon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        
        // Main Layout
        setLayout(new BorderLayout());
        
        // Sidebar
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);
        
        // Content Panel dengan CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        
        // Initialize panels
        dashboardPanel = new DashboardPanel();
        anggotaPanel = new AnggotaPanel();
        pengumumanPanel = new PengumumanPanel();
        
        contentPanel.add(dashboardPanel, "dashboard");
        contentPanel.add(anggotaPanel, "anggota");
        contentPanel.add(createPlaceholderPanel("Event"), "event");
        contentPanel.add(pengumumanPanel, "pengumuman");
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Status bar (optional)
        JPanel statusBar = createStatusBar();
        add(statusBar, BorderLayout.SOUTH);
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new MigLayout("fill, insets 0", "[250!]", "[]15[]15[]15[]push[]"));
        sidebar.setBackground(new Color(45, 52, 54));
        
        // Header
        JPanel header = new JPanel(new MigLayout("fill, insets 20", "[]", "[]5[]"));
        header.setBackground(new Color(45, 52, 54));
        
        JLabel lblTitle = new JLabel("Komunitas Lari");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        header.add(lblTitle, "wrap");
        
        JLabel lblSubtitle = new JLabel("Cirebon Running Club");
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSubtitle.setForeground(new Color(200, 200, 200));
        header.add(lblSubtitle);
        
        sidebar.add(header, "growx, wrap 20");
        
        // Menu Buttons
        btnDashboard = createMenuButton("Dashboard", "📊");
        btnDashboard.addActionListener(e -> showDashboard());
        sidebar.add(btnDashboard, "growx, wrap");
        
        btnAnggota = createMenuButton("Anggota", "👥");
        btnAnggota.addActionListener(e -> showAnggota());
        sidebar.add(btnAnggota, "growx, wrap");
        
        btnEvent = createMenuButton("Event", "🏃");
        btnEvent.addActionListener(e -> showEvent());
        sidebar.add(btnEvent, "growx, wrap");
        
        btnPengumuman = createMenuButton("Pengumuman", "📢");
        btnPengumuman.addActionListener(e -> showPengumuman());
        sidebar.add(btnPengumuman, "growx, wrap");
        
        // Footer
        JPanel footer = new JPanel(new MigLayout("fill, insets 20", "[]", ""));
        footer.setBackground(new Color(45, 52, 54));
        
        JLabel lblVersion = new JLabel("v1.0.0");
        lblVersion.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblVersion.setForeground(new Color(150, 150, 150));
        footer.add(lblVersion);
        
        sidebar.add(footer, "growx, south");
        
        return sidebar;
    }
    
    private JButton createMenuButton(String text, String icon) {
        JButton btn = new JButton(icon + "  " + text);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(45, 52, 54));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(250, 45));
        
        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(70, 77, 79));
                btn.setContentAreaFilled(true);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!btn.isSelected()) {
                    btn.setContentAreaFilled(false);
                }
            }
        });
        
        return btn;
    }
    
    private void setActiveButton(JButton activeBtn) {
        // Reset all buttons
        btnDashboard.setContentAreaFilled(false);
        btnAnggota.setContentAreaFilled(false);
        btnEvent.setContentAreaFilled(false);
        btnPengumuman.setContentAreaFilled(false);
        
        // Set active button
        activeBtn.setBackground(new Color(52, 152, 219));
        activeBtn.setContentAreaFilled(true);
    }
    
    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new MigLayout("insets 5", "[]push[]", ""));
        statusBar.setBackground(new Color(240, 240, 240));
        
        JLabel lblStatus = new JLabel("Ready");
        lblStatus.setFont(new Font("SansSerif", Font.PLAIN, 11));
        statusBar.add(lblStatus);
        
        JLabel lblTime = new JLabel(java.time.LocalDateTime.now().toString());
        lblTime.setFont(new Font("SansSerif", Font.PLAIN, 11));
        statusBar.add(lblTime);
        
        return statusBar;
    }
    
    private JPanel createPlaceholderPanel(String title) {
        JPanel panel = new JPanel(new MigLayout("fill, insets 20", "[center]", "[center]"));
        
        JLabel label = new JLabel(title + " - Coming Soon");
        label.setFont(new Font("SansSerif", Font.BOLD, 24));
        label.setForeground(Color.GRAY);
        panel.add(label);
        
        return panel;
    }
    
    // Navigation methods
    public void showDashboard() {
        cardLayout.show(contentPanel, "dashboard");
        setActiveButton(btnDashboard);
        dashboardPanel.refreshData();
    }
    
    public void showAnggota() {
        cardLayout.show(contentPanel, "anggota");
        setActiveButton(btnAnggota);
    }
    
    public void showEvent() {
        cardLayout.show(contentPanel, "event");
        setActiveButton(btnEvent);
    }
    
    public void showPengumuman() {
        cardLayout.show(contentPanel, "pengumuman");
        setActiveButton(btnPengumuman);
    }
}