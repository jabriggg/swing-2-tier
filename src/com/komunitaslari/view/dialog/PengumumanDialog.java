package com.komunitaslari.view.dialog;

import com.komunitaslari.model.Pengumuman;
import com.komunitaslari.util.MessageUtil;
import com.komunitaslari.util.ValidationUtil;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

/**
 * Dialog untuk broadcast pengumuman
 */
public class PengumumanDialog extends JDialog {
    
    private boolean confirmed = false;
    private Pengumuman pengumuman;
    
    // Components
    private JTextField txtJudul;
    private JTextArea txtIsi;
    private JTextField txtPengirim;
    private JButton btnBroadcast;
    private JButton btnBatal;
    
    public PengumumanDialog(Frame parent) {
        super(parent, "📣 Broadcast Pengumuman", true);
        this.pengumuman = new Pengumuman();
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new MigLayout("fill, insets 20", "[right]15[grow, fill]", ""));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Info banner
        JPanel infoBanner = createInfoBanner();
        add(infoBanner, "span, growx, wrap 15");
        
        // Judul
        add(new JLabel("Judul *:"));
        txtJudul = new JTextField(40);
        add(txtJudul, "wrap");
        
        // Isi Pengumuman
        add(new JLabel("Isi Pengumuman *:"), "top");
        txtIsi = new JTextArea(8, 40);
        txtIsi.setLineWrap(true);
        txtIsi.setWrapStyleWord(true);
        txtIsi.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        JScrollPane scrollPane = new JScrollPane(txtIsi);
        add(scrollPane, "wrap");
        
        // Pengirim
        add(new JLabel("Pengirim:"));
        txtPengirim = new JTextField(40);
        txtPengirim.setText("Admin"); // Default
        add(txtPengirim, "wrap");
        
        // Character counter untuk isi
        JLabel lblCounter = new JLabel("0 karakter");
        lblCounter.setFont(new Font("SansSerif", Font.ITALIC, 10));
        lblCounter.setForeground(Color.GRAY);
        txtIsi.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }
            
            private void update() {
                int count = txtIsi.getText().length();
                lblCounter.setText(count + " karakter");
            }
        });
        add(lblCounter, "skip, wrap");
        
        // Note
        JLabel lblNote = new JLabel("* Field wajib diisi");
        lblNote.setFont(new Font("SansSerif", Font.ITALIC, 10));
        lblNote.setForeground(Color.GRAY);
        add(lblNote, "span, wrap 20");
        
        // Buttons
        JPanel btnPanel = new JPanel(new MigLayout("insets 0", "push[]10[]", ""));
        
        btnBroadcast = new JButton("📣 Broadcast");
        btnBroadcast.setBackground(new Color(52, 152, 219));
        btnBroadcast.setForeground(Color.WHITE);
        btnBroadcast.setFocusPainted(false);
        btnBroadcast.addActionListener(e -> handleBroadcast());
        btnPanel.add(btnBroadcast, "w 150!");
        
        btnBatal = new JButton("Batal");
        btnBatal.addActionListener(e -> dispose());
        btnPanel.add(btnBatal, "w 100!");
        
        add(btnPanel, "span, growx");
        
        // Dialog settings
        pack();
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        // Set focus ke judul
        SwingUtilities.invokeLater(() -> txtJudul.requestFocus());
    }
    
    private JPanel createInfoBanner() {
        JPanel banner = new JPanel(new MigLayout("fill, insets 10", "[]10[]", ""));
        banner.setBackground(new Color(230, 247, 255));
        banner.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 2));
        
        JLabel iconLabel = new JLabel("ℹ️");
        iconLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        banner.add(iconLabel);
        
        JTextArea infoText = new JTextArea(
            "Pengumuman akan dikirim ke SEMUA anggota komunitas secara realtime.\n" +
            "Pastikan isi pengumuman sudah benar sebelum melakukan broadcast."
        );
        infoText.setEditable(false);
        infoText.setBackground(new Color(230, 247, 255));
        infoText.setFont(new Font("SansSerif", Font.PLAIN, 11));
        infoText.setLineWrap(true);
        infoText.setWrapStyleWord(true);
        banner.add(infoText, "grow");
        
        return banner;
    }
    
    /**
     * Handle button broadcast
     */
    private void handleBroadcast() {
        if (!validateInput()) {
            return;
        }
        
        // Set data ke object pengumuman
        pengumuman = new Pengumuman();
        pengumuman.setJudul(txtJudul.getText().trim());
        pengumuman.setIsi(txtIsi.getText().trim());
        pengumuman.setPengirim(txtPengirim.getText().trim());
        pengumuman.setTanggalKirim(LocalDateTime.now());
        
        confirmed = true;
        dispose();
    }
    
    /**
     * Validasi input form
     */
    private boolean validateInput() {
        // Validasi judul
        String judul = txtJudul.getText().trim();
        if (ValidationUtil.isEmpty(judul)) {
            MessageUtil.showError(this, ValidationUtil.getRequiredMessage("Judul"));
            txtJudul.requestFocus();
            return false;
        }
        if (!ValidationUtil.hasMinLength(judul, 5)) {
            MessageUtil.showError(this, ValidationUtil.getMinLengthMessage("Judul", 5));
            txtJudul.requestFocus();
            return false;
        }
        if (!ValidationUtil.hasMaxLength(judul, 200)) {
            MessageUtil.showError(this, ValidationUtil.getMaxLengthMessage("Judul", 200));
            txtJudul.requestFocus();
            return false;
        }
        
        // Validasi isi
        String isi = txtIsi.getText().trim();
        if (ValidationUtil.isEmpty(isi)) {
            MessageUtil.showError(this, ValidationUtil.getRequiredMessage("Isi Pengumuman"));
            txtIsi.requestFocus();
            return false;
        }
        if (!ValidationUtil.hasMinLength(isi, 10)) {
            MessageUtil.showError(this, ValidationUtil.getMinLengthMessage("Isi Pengumuman", 10));
            txtIsi.requestFocus();
            return false;
        }
        
        // Validasi pengirim (opsional, ada default)
        String pengirim = txtPengirim.getText().trim();
        if (ValidationUtil.isEmpty(pengirim)) {
            txtPengirim.setText("Admin");
        }
        
        return true;
    }
    
    /**
     * Cek apakah user mengklik broadcast
     */
    public boolean isConfirmed() {
        return confirmed;
    }
    
    /**
     * Mendapatkan data pengumuman dari form
     */
    public Pengumuman getPengumuman() {
        return pengumuman;
    }
}