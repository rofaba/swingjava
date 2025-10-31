package org.example.view;

import org.example.infra.PeliculaRepository;
import org.example.infra.SessionContext;
import org.example.model.Pelicula;
import org.example.model.PeliculaTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import org.example.view.DetallePeliculaDialog;


public class MainFrame extends JFrame {

    // --- Componentes creados en el GUI Builder (mismos nombres del .form)
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

    // --- Lógica
    private SessionContext session;
    private PeliculaRepository peliRepo;
    private PeliculaTableModel peliModel;

    public MainFrame() {
        setContentPane(rootPanel);
        setTitle("Catálogo de Películas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();

        btnDetalles.addActionListener(e -> {
            int row = tblPeliculas.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this,"Selecciona una película"); return; }
            var p = peliModel.getAt(row);

            DetallePeliculaDialog dlg = new DetallePeliculaDialog();
            dlg.setPelicula(p);
            dlg.setLocationRelativeTo(this);
            dlg.setVisible(true);
        });
    }

    // Inyección
    public void setSession(SessionContext session) { this.session = session; }
    public void setPeliculaRepository(PeliculaRepository repo) { this.peliRepo = repo; }

    // Inicialización tras login
    public void initAfterLogin() throws IOException {
        peliModel = new PeliculaTableModel();
        tblPeliculas.setModel(peliModel);

        tblPeliculas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblPeliculas.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        // Alinear columnas
        var left = new DefaultTableCellRenderer();
        left.setHorizontalAlignment(SwingConstants.LEFT);
        var center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        tblPeliculas.setDefaultRenderer(String.class, left);
        tblPeliculas.getColumnModel().getColumn(2).setCellRenderer(center); // Año

        // Cargar datos
        reload();

        // Ocultar ID
        var idCol = tblPeliculas.getColumnModel().getColumn(0);
        idCol.setMinWidth(0); idCol.setMaxWidth(0); idCol.setPreferredWidth(0);

        // Anchos aproximados
        tblPeliculas.getColumnModel().getColumn(1).setPreferredWidth(240); // Título
        tblPeliculas.getColumnModel().getColumn(2).setPreferredWidth(60);  // Año
        tblPeliculas.getColumnModel().getColumn(3).setPreferredWidth(160); // Director
        tblPeliculas.getColumnModel().getColumn(4).setPreferredWidth(120); // Género
    }

    // botones
    public void wireActions(PeliculaRepository repo) {
        btnEliminar.addActionListener(e -> {
            int row = tblPeliculas.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this,"Selecciona una película"); return; }
            var p = peliModel.getAt(row);
            String uid = session.getCurrentUser().getId();
            try {
                if (repo.deleteById(p.getId(), uid)) {
                    reload();
                } else {
                    JOptionPane.showMessageDialog(this,"No se pudo eliminar","Error",JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"Error de acceso a datos","Error",JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAnadir.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,"Alta de película (pendiente diálogo).");
            // Luego: abrir JDialog de alta, repo.add(...), reload();
        });

        btnLogout.addActionListener(e -> {
            session.clear();
            dispose();
            // Si quieres volver al login, lo hacemos luego desde Main.
        });
    }

    // Recarga de datos
    private void reload() throws IOException {
        if (session == null || session.getCurrentUser() == null || peliRepo == null) return;
        int uid = Integer.parseInt(session.getCurrentUser().getId());
        List<Pelicula> list = peliRepo.findAllByUser(String.valueOf(uid));
        peliModel.setData(list);
        if (lblEstado != null) lblEstado.setText(list.size() + " películas");
    }

    //ver detalles pelicula


    // Getters de componentes
    public JTable getTblPeliculas() { return tblPeliculas; }
    public JButton getBtnAnadir() { return btnAnadir; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnDetalles() { return btnDetalles; }
    public JButton getBtnLogout() { return btnLogout; }
    public JLabel getLblEstado() { return lblEstado; }
}
