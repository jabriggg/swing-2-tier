package com.komunitaslari.view.dialog;

import com.komunitaslari.model.Event;
import com.komunitaslari.util.DateUtil;
import com.komunitaslari.util.MessageUtil;
import com.komunitaslari.util.ValidationUtil;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class EventDialog extends JDialog {
    private boolean confirmed = false;
    private Event event;

    private JTextField txtNama, txtLokasi, txtTanggal, txtKuota;
    private JTextArea txtDeskripsi;
    private JComboBox<String> cmbStatus;

    public EventDialog(Frame parent) {
        super(parent, "Tambah Event Baru", true);
        this.event = new Event();
        initComponents();
    }

    public EventDialog(Frame parent, Event event) {
        super(parent, "Edit Event", true);
        this.event = event;
        initComponents();
        fillForm();
    }

    private void initComponents() {
        setLayout(new MigLayout("fillx, insets 20", "[right]15[grow, fill]", "[]10[]10[]10[]10[]10[]20[]"));
        
        add(new JLabel("Nama Event *:"));
        txtNama = new JTextField(30);
        add(txtNama, "wrap");

        add(new JLabel("Lokasi *:"));
        txtLokasi = new JTextField(30);
        add(txtLokasi, "wrap");

        add(new JLabel("Tanggal (yyyy-MM-dd) *:"));
        txtTanggal = new JTextField(30);
        txtTanggal.setText(DateUtil.format(LocalDate.now()));
        add(txtTanggal, "wrap");

        add(new JLabel("Kuota *:"));
        txtKuota = new JTextField("100");
        add(txtKuota, "wrap");

        add(new JLabel("Status:"));
        cmbStatus = new JComboBox<>(new String[]{"OPEN", "CLOSED", "DONE"});
        add(cmbStatus, "wrap");

        add(new JLabel("Deskripsi:"), "aligny top");
        txtDeskripsi = new JTextArea(4, 30);
        txtDeskripsi.setLineWrap(true);
        add(new JScrollPane(txtDeskripsi), "wrap");

        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.addActionListener(e -> handleSimpan());
        JButton btnBatal = new JButton("Batal");
        btnBatal.addActionListener(e -> dispose());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnSimpan);
        btnPanel.add(btnBatal);
        add(btnPanel, "span, growx");

        pack();
        setLocationRelativeTo(getParent());
    }

    private void fillForm() {
        txtNama.setText(event.getNamaEvent());
        txtLokasi.setText(event.getLokasi());
        if (event.getTanggalEvent() != null) {
            txtTanggal.setText(DateUtil.format(event.getTanggalEvent().toLocalDate()));
        }
        txtKuota.setText(String.valueOf(event.getKuota()));
        txtDeskripsi.setText(event.getDeskripsi());
        cmbStatus.setSelectedItem(event.getStatus());
    }

    private void handleSimpan() {
        if (!validateInput()) return;

        event.setNamaEvent(txtNama.getText().trim());
        event.setLokasi(txtLokasi.getText().trim());
        // Asumsi DateUtil.parseDate mengembalikan LocalDate
        event.setTanggalEvent(DateUtil.parseDate(txtTanggal.getText().trim()).atStartOfDay());
        event.setKuota(Integer.parseInt(txtKuota.getText().trim()));
        event.setStatus(cmbStatus.getSelectedItem().toString());
        event.setDeskripsi(txtDeskripsi.getText().trim());

        confirmed = true;
        dispose();
    }

    private boolean validateInput() {
        if (ValidationUtil.isEmpty(txtNama.getText())) {
            MessageUtil.showError(this, "Nama event harus diisi!");
            return false;
        }
        try {
            Integer.parseInt(txtKuota.getText());
        } catch (NumberFormatException e) {
            MessageUtil.showError(this, "Kuota harus berupa angka!");
            return false;
        }
        return true; 
    }

    public boolean isConfirmed() { return confirmed; }
    public Event getEvent() { return event; }
}