package org.example.view;

import org.example.model.Pelicula;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class DetallePeliculaDialog extends JDialog {

    // componentes de la UI
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

    // Cargar datos de la película
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

    // Carga la imagen de forma segura
    private void loadImageSafe(String path) {
        // si no hay URL mostrar texto
        if (path == null || path.isBlank()) {
            lblImagen.setIcon(null);
            lblImagen.setText("(Sin imagen)");
            return;
        }

        try {
            // Asegurar que tenga esquema http/https
            String urlStr = (path.startsWith("http://") || path.startsWith("https://"))
                    ? path
                    : "https://" + path;

            Image img = ImageIO.read(new URL(urlStr)); // carga

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

    // helper para valores nulos o vacíos
    private static String nz(String s) {
        return (s == null || s.isBlank()) ? "-" : s;
    }
}
