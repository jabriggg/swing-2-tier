package com.komunitaslari.view.dialog;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;

/**
 * Custom Confirmation Dialog untuk Komunitas Lari
 * Didesain biar seragam sama AnggotaDialog (MigLayout)
 */
public class ConfirmDialog extends JDialog {

    private boolean confirmed = false;
    private final String message;
    private final MessageType type;

    // Enum buat nentuin level bahaya konfirmasi
    public enum MessageType {
        QUESTION, // Konfirmasi biasa (Simpan/Edit)
        WARNING,  // Peringatan (Keluar tanpa simpan)
        DANGER    // Bahaya (Hapus data permanen)
    }

    /**
     * Constructor Utama
     */
    public ConfirmDialog(Frame parent, String title, String message, MessageType type) {
        super(parent, title, true);
        this.message = message;
        this.type = type;
        initComponents();
    }

    private void initComponents() {
        // Layout: kolom 1 (icon), kolom 2 (pesan text)
        setLayout(new MigLayout("fill, insets 25", "[center]20[grow, fill]", "[]25[]"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // 1. Icon Section (Ambil dari System Look & Feel)
        JLabel lblIcon = new JLabel();
        lblIcon.setIcon(getSystemIcon());
        add(lblIcon, "top");

        // 2. Message Section (Support HTML biar text wrap otomatis)
        JLabel lblMessage = new JLabel("<html><body style='width: 250px;'>" + message + "</body></html>");
        lblMessage.setFont(new Font("SansSerif", Font.PLAIN, 13));
        add(lblMessage, "wrap");

        // 3. Button Section
        JPanel btnPanel = new JPanel(new MigLayout("insets 0", "push[]10[]", ""));
        
        JButton btnYes = new JButton("Ya");
        JButton btnNo = new JButton("Batal");

        // Styling khusus kalau tipenya DANGER (buat hapus)
        if (type == MessageType.DANGER) {
            btnYes.setText("Ya, Hapus!");
            btnYes.setForeground(Color.RED);
        } else if (type == MessageType.QUESTION) {
            btnYes.setText("Ya, Simpan");
        }

        // Action Listeners
        btnYes.addActionListener(e -> {
            confirmed = true;
            dispose();
        });

        btnNo.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        btnPanel.add(btnYes, "w 100!");
        btnPanel.add(btnNo, "w 100!");
        
        add(btnPanel, "span 2, growx");

        // Dialog Settings
        pack();
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    /**
     * Ambil icon bawaan OS biar gak perlu aset gambar luar
     */
    private Icon getSystemIcon() {
        switch (type) {
            case WARNING: return UIManager.getIcon("OptionPane.warningIcon");
            case DANGER:  return UIManager.getIcon("OptionPane.errorIcon");
            default:      return UIManager.getIcon("OptionPane.questionIcon");
        }
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    // =========================================================================
    // STATIC HELPERS (Biar tinggal panggil satu baris di controller)
    // =========================================================================

    /**
     * Helper Konfirmasi Hapus
     */
    public static boolean showDelete(Frame parent, String itemName) {
        ConfirmDialog cd = new ConfirmDialog(parent, "Konfirmasi Hapus", 
                "Apakah Anda yakin ingin menghapus <b>" + itemName + "</b>?<br>Data tidak bisa dikembalikan!", 
                MessageType.DANGER);
        cd.setVisible(true);
        return cd.isConfirmed();
    }

    /**
     * Helper Konfirmasi Simpan/Edit
     */
    public static boolean showSave(Frame parent) {
        ConfirmDialog cd = new ConfirmDialog(parent, "Konfirmasi Simpan", 
                "Apakah data yang Anda masukkan sudah benar?", 
                MessageType.QUESTION);
        cd.setVisible(true);
        return cd.isConfirmed();
    }
    
    /**
     * Helper Konfirmasi Custom
     */
    public static boolean showCustom(Frame parent, String title, String message, MessageType type) {
        ConfirmDialog cd = new ConfirmDialog(parent, title, message, type);
        cd.setVisible(true);
        return cd.isConfirmed();
    }
}