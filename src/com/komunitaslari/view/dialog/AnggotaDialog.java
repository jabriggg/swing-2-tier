package com.komunitaslari.view.dialog;

import com.komunitaslari.model.Anggota;
import com.komunitaslari.util.DateUtil;
import com.komunitaslari.util.MessageUtil;
import com.komunitaslari.util.ValidationUtil;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Dialog untuk input/edit data Anggota
 */
public class AnggotaDialog extends JDialog {
    
    private boolean confirmed = false;
    private Anggota anggota;
    private boolean isEditMode;
    
    // Components
    private JTextField txtNama;
    private JTextField txtEmail;
    private JTextField txtTelepon;
    private JTextField txtTanggalDaftar;
    private JComboBox<Anggota.StatusAnggota> cmbStatus;
    private JButton btnSimpan;
    private JButton btnBatal;
    
    /**
     * Constructor untuk mode tambah
     */
    public AnggotaDialog(Frame parent) {
        super(parent, "Tambah Anggota", true);
        this.isEditMode = false;
        this.anggota = new Anggota();
        initComponents();
    }
    
    /**
     * Constructor untuk mode edit
     */
    public AnggotaDialog(Frame parent, Anggota anggota) {
        super(parent, "Edit Anggota", true);
        this.isEditMode = true;
        this.anggota = anggota;
        initComponents();
        fillForm();
    }
    
    private void initComponents() {
        setLayout(new MigLayout("fill, insets 20", "[right]15[grow, fill]", ""));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Nama
        add(new JLabel("Nama *:"));
        txtNama = new JTextField(30);
        add(txtNama, "wrap");
        
        // Email
        add(new JLabel("Email *:"));
        txtEmail = new JTextField(30);
        add(txtEmail, "wrap");
        
        // Telepon
        add(new JLabel("Telepon:"));
        txtTelepon = new JTextField(30);
        add(txtTelepon, "wrap");
        
        // Tanggal Daftar
        add(new JLabel("Tanggal Daftar *:"));
        txtTanggalDaftar = new JTextField(30);
        txtTanggalDaftar.setText(DateUtil.format(LocalDate.now()));
        add(txtTanggalDaftar, "wrap");
        
        // Status
        add(new JLabel("Status *:"));
        cmbStatus = new JComboBox<>(Anggota.StatusAnggota.values());
        add(cmbStatus, "wrap");
        
        // Note
        JLabel lblNote = new JLabel("* Field wajib diisi");
        lblNote.setFont(new Font("SansSerif", Font.ITALIC, 10));
        lblNote.setForeground(Color.GRAY);
        add(lblNote, "span, wrap 20");
        
        // Buttons
        JPanel btnPanel = new JPanel(new MigLayout("insets 0", "push[]10[]", ""));
        
        btnSimpan = new JButton("Simpan");
        btnSimpan.addActionListener(e -> handleSimpan());
        btnPanel.add(btnSimpan, "w 100!");
        
        btnBatal = new JButton("Batal");
        btnBatal.addActionListener(e -> dispose());
        btnPanel.add(btnBatal, "w 100!");
        
        add(btnPanel, "span, growx");
        
        // Dialog settings
        pack();
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        // Set focus ke nama
        SwingUtilities.invokeLater(() -> txtNama.requestFocus());
    }
    
    /**
     * Fill form dengan data anggota (untuk edit mode)
     */
    private void fillForm() {
        if (anggota != null) {
            txtNama.setText(anggota.getNama());
            txtEmail.setText(anggota.getEmail());
            txtTelepon.setText(anggota.getTelepon());
            txtTanggalDaftar.setText(DateUtil.format(anggota.getTanggalDaftar()));
            cmbStatus.setSelectedItem(anggota.getStatus());
        }
    }
    
    /**
     * Handle button simpan
     */
    private void handleSimpan() {
        if (!validateInput()) {
            return;
        }
        
        // Set data ke object anggota
        if (isEditMode) {
            // Update existing
            anggota.setNama(txtNama.getText().trim());
            anggota.setEmail(txtEmail.getText().trim());
            anggota.setTelepon(txtTelepon.getText().trim());
            anggota.setTanggalDaftar(DateUtil.parseDate(txtTanggalDaftar.getText().trim()));
            anggota.setStatus((Anggota.StatusAnggota) cmbStatus.getSelectedItem());
        } else {
            // Create new
            anggota = new Anggota();
            anggota.setNama(txtNama.getText().trim());
            anggota.setEmail(txtEmail.getText().trim());
            anggota.setTelepon(txtTelepon.getText().trim());
            anggota.setTanggalDaftar(DateUtil.parseDate(txtTanggalDaftar.getText().trim()));
            anggota.setStatus((Anggota.StatusAnggota) cmbStatus.getSelectedItem());
        }
        
        confirmed = true;
        dispose();
    }
    
    /**
     * Validasi input form
     */
    private boolean validateInput() {
        // Validasi nama
        String nama = txtNama.getText().trim();
        if (ValidationUtil.isEmpty(nama)) {
            MessageUtil.showError(this, ValidationUtil.getRequiredMessage("Nama"));
            txtNama.requestFocus();
            return false;
        }
        if (!ValidationUtil.hasMinLength(nama, 3)) {
            MessageUtil.showError(this, ValidationUtil.getMinLengthMessage("Nama", 3));
            txtNama.requestFocus();
            return false;
        }
        
        // Validasi email
        String email = txtEmail.getText().trim();
        if (ValidationUtil.isEmpty(email)) {
            MessageUtil.showError(this, ValidationUtil.getRequiredMessage("Email"));
            txtEmail.requestFocus();
            return false;
        }
        if (!ValidationUtil.isValidEmail(email)) {
            MessageUtil.showError(this, ValidationUtil.getInvalidFormatMessage("Email"));
            txtEmail.requestFocus();
            return false;
        }
        
        // Validasi telepon (opsional)
        String telepon = txtTelepon.getText().trim();
        if (ValidationUtil.isNotEmpty(telepon)) {
            if (!ValidationUtil.isValidPhone(telepon)) {
                MessageUtil.showError(this, ValidationUtil.getInvalidFormatMessage("Nomor Telepon"));
                txtTelepon.requestFocus();
                return false;
            }
        }
        
        // Validasi tanggal
        String tanggalStr = txtTanggalDaftar.getText().trim();
        if (ValidationUtil.isEmpty(tanggalStr)) {
            MessageUtil.showError(this, ValidationUtil.getRequiredMessage("Tanggal Daftar"));
            txtTanggalDaftar.requestFocus();
            return false;
        }
        
        LocalDate tanggal = DateUtil.parseDate(tanggalStr);
        if (tanggal == null) {
            MessageUtil.showError(this, "Format tanggal tidak valid! Gunakan format: dd/MM/yyyy");
            txtTanggalDaftar.requestFocus();
            return false;
        }
        
        if (tanggal.isAfter(LocalDate.now())) {
            MessageUtil.showError(this, "Tanggal daftar tidak boleh di masa depan!");
            txtTanggalDaftar.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Cek apakah user mengklik simpan
     */
    public boolean isConfirmed() {
        return confirmed;
    }
    
    /**
     * Mendapatkan data anggota dari form
     */
    public Anggota getAnggota() {
        return anggota;
    }
}