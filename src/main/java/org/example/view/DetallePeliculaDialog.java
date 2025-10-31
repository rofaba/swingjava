package org.example.view;

import org.example.model.Pelicula;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class DetallePeliculaDialog extends JDialog {

    // --- componentes del GUI Builder ---
    private JPanel rootPanel;
    private JLabel lblTituloV;
    private JLabel lblAnioV;
    private JLabel lblDirectorV;
    private JLabel lblGeneroV;
    private JTextArea txtDescripcion;
    private JLabel lblImagen;
    private JButton btnCerrar;

    public DetallePeliculaDialog() {
        setContentPane(rootPanel);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        if (txtDescripcion != null) {
            txtDescripcion.setEditable(false);
            txtDescripcion.setLineWrap(true);
            txtDescripcion.setWrapStyleWord(true);
        }

        if (lblImagen != null) {
            lblImagen.setPreferredSize(new Dimension(220, 300));
            lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
            lblImagen.setText("(Sin imagen)");
        }

        if (btnCerrar != null)
            btnCerrar.addActionListener(e -> dispose());

        pack();
    }

    // --- Cargar datos de la película ---
    public void setPelicula(Pelicula p) {
        if (p == null) return;

        lblTituloV.setText(nz(p.getTitle()));
        lblAnioV.setText(String.valueOf(p.getYear()));
        lblDirectorV.setText(nz(p.getDirector()));
        lblGeneroV.setText(nz(p.getGenre()));
        txtDescripcion.setText(nz(p.getDescription()));

        // intenta cargar la imagen
        loadImageSafe(p.getImageUrl());
    }

    // --- Cargar imagen local o remota ---
    private void loadImageSafe(String path) {
        if (path == null || path.isBlank()) {
            lblImagen.setIcon(null);
            lblImagen.setText("(Sin imagen)");
            return;
        }

        try {
            Image img = null;

            // Si es una URL (http o https)
            if (path.startsWith("http://") || path.startsWith("https://")) {
                URL url = new URL(path);
                img = ImageIO.read(url);
            }
            // Si es un archivo local
            else {
                File f = new File(path);
                if (f.exists()) img = ImageIO.read(f);
                else {
                    // Si está en resources (ej: /img/poster.jpg)
                    var res = getClass().getResource(path.startsWith("/") ? path : "/" + path);
                    if (res != null) img = ImageIO.read(res);
                }
            }

            if (img != null) {
                Image scaled = img.getScaledInstance(220, 300, Image.SCALE_SMOOTH);
                lblImagen.setText(null);
                lblImagen.setIcon(new ImageIcon(scaled));
            } else {
                lblImagen.setIcon(null);
                lblImagen.setText("(No se pudo cargar)");
            }

        } catch (Exception e) {
            lblImagen.setIcon(null);
            lblImagen.setText("(No se pudo cargar)");
        }
    }

    // --- helper simple ---
    private static String nz(String s) {
        return (s == null || s.isBlank()) ? "-" : s;
    }
}
