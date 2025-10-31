package org.example.view;

import org.example.infra.PeliculaRepository;
import org.example.infra.SessionContext;
import org.example.model.Pelicula;

import javax.swing.*;
import java.io.IOException;

public class NuevoPeliculaDialog extends JDialog {

    // --- componentes (GUI Builder) ---
    private JPanel rootPanel;
    private JTextField txtTitulo;
    private JTextField txtAnio;
    private JTextField txtDirector;
    private JTextField txtGenero;
    private JTextArea txtDescripcion;
    private JTextField txtImagen;      // ruta o URL (opcional)
    private JButton btnGuardar;
    private JButton btnCancelar;

    // --- dependencias ---
    private SessionContext session;
    private PeliculaRepository peliRepo;

    public NuevoPeliculaDialog() {
        setContentPane(rootPanel);
        setTitle("Nueva película");
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        if (txtDescripcion != null) {
            txtDescripcion.setLineWrap(true);
            txtDescripcion.setWrapStyleWord(true);
        }

        if (btnCancelar != null) btnCancelar.addActionListener(e -> dispose());
        if (btnGuardar != null)  btnGuardar.addActionListener(e -> onGuardar());

        pack();
        setResizable(false);
    }

    // inyección
    public void setSession(SessionContext session) { this.session = session; }
    public void setPeliculaRepository(PeliculaRepository repo) { this.peliRepo = repo; }

    // guardar
    private void onGuardar() {
        String titulo = nz(txtTitulo.getText());
        String anioStr = nz(txtAnio.getText());
        String director = nz(txtDirector.getText());
        String genero = nz(txtGenero.getText());
        String descripcion = nz(txtDescripcion.getText());
        String imagen = nz(txtImagen.getText());

        if (titulo.isBlank()) { warn("Título es obligatorio"); txtTitulo.requestFocus(); return; }

        int anio;
        try {
            anio = anioStr.isBlank() ? 0 : Integer.parseInt(anioStr);
            if (anio < 1900 || anio > 2100) { warn("Año inválido (1900–2100)"); txtAnio.requestFocus(); return; }
        } catch (NumberFormatException nfe) {
            warn("Año debe ser numérico"); txtAnio.requestFocus(); return;
        }

        if (session == null || session.getCurrentUser() == null || peliRepo == null) {
            error("Dependencias no inicializadas"); return;
        }

        // Construir película
        Pelicula p = new Pelicula();
        try {
            p.setTitle(titulo);
            p.setYear(anio);
            p.setDirector(director);
            p.setGenre(genero);
            try { p.setDescription(descripcion); } catch (Throwable ignore) {}
            if (!imagen.isBlank()) { try { p.setImageUrl(imagen); } catch (Throwable ignore) {} }
            try { p.setUserId(session.getCurrentUser().getId()); } catch (Throwable ignore) {}

            // Guardar: si no hay excepción, consideramos OK
            String uid = session.getCurrentUser().getId();
                peliRepo.add(p);
            
            // Éxito
            dispose(); // cierra; el MainFrame hará reload() al volver del setVisible()

        } catch (IOException ex) {
            error("Error de acceso a datos");
        }
    }


    // helpers
    private static String nz(String s) { return s == null ? "" : s.trim(); }
    private void warn(String msg) { JOptionPane.showMessageDialog(this, msg, "Validación", JOptionPane.WARNING_MESSAGE); }
    private void error(String msg) { JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE); }
}
