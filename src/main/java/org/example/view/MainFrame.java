package org.example.view;

import org.example.infra.PeliculaRepository;
import org.example.infra.SessionContext;
import org.example.model.Pelicula;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.List;

public class MainFrame extends JFrame {


    private JPanel rootPanel;
    private JPanel panelTop;
    private JLabel lblTitulo;
    private JPanel panelBarra;
    private JPanel panelBotones;
    private JButton btnAnadir;
    private JButton btnEliminar;
    private JButton btnDetalles;
    private JPanel panelSesion;
    private JLabel lblEstado;
    private JButton btnLogout;
    private JScrollPane scrollPeliculas;
    private JTable tblPeliculas;

    // === dependencias ===
    private SessionContext session;
    private PeliculaRepository peliRepo;

    public MainFrame() {
        setContentPane(rootPanel);
        setTitle("Catálogo de Películas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    // --- inyección de dependencias ---
    public void setSession(SessionContext session) { this.session = session; }
    public void setPeliculaRepository(PeliculaRepository repo) { this.peliRepo = repo; }

    // --- llamada tras abrir para cargar datos ---
    public void initAfterLogin() throws IOException {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID","Título","Año","Director","Género"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
            @Override public Class<?> getColumnClass(int c) {
                return switch (c) {
                    case 0, 2 -> Integer.class;
                    default -> String.class;
                };
            }
        };
        tblPeliculas.setModel(model);

        if (session != null && session.getCurrentUser() != null && peliRepo != null) {
            int uid = Integer.parseInt(session.getCurrentUser().getId());
            List<Pelicula> list = peliRepo.findAllByUser(String.valueOf(uid));
            for (Pelicula p : list) {
                model.addRow(new Object[]{p.getId(), p.getTitle(), p.getYear(), p.getDirector(), p.getGenre()});
            }
            if (lblEstado != null) lblEstado.setText(list.size() + " películas");
        }
    }

    // --- getters para el controlador ---
    public JTable getTblPeliculas() { return tblPeliculas; }
    public JButton getBtnAnadir() { return btnAnadir; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnDetalles() { return btnDetalles; }
    public JButton getBtnLogout() { return btnLogout; }
    public JLabel getLblEstado() { return lblEstado; }
}
