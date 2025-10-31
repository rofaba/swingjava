package org.example.view;

import org.example.infra.AuthProvider;
import org.example.infra.SessionContext;
import org.example.infra.PeliculaRepository;

import javax.swing.*;
import java.io.IOException;


public class LoginDialog extends JDialog {


    private JPanel rootPanel;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnEntrar;
    private JLabel lblTitulo, lblSubtitulo, lblEmail, lblPassword, lblObligatorios;
    private PeliculaRepository peliRepo;
    // === Dependencias (lógica que ya tienes) ===
    private AuthProvider auth;
    private SessionContext session;

    public LoginDialog() {
        setContentPane(rootPanel);
        setTitle("Login");
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        btnEntrar.addActionListener(e -> onEntrar());
    }

    // Cableado
    public void setAuth(AuthProvider auth)         { this.auth = auth; }
    public void setSession(SessionContext session) { this.session = session; }
    public void setPeliculaRepository(PeliculaRepository peliRepo) {
        this.peliRepo = peliRepo;
    }
    // Handlers
    private void onEntrar() {
        try {
            var user = auth.login(getEmail(), getPassword());
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Credenciales inválidas", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // El CsvAuthProvider ya dejó la sesión lista, pero por si acaso
            if (session.getCurrentUser() == null) session.setCurrentUser(user);

            dispose();

            MainFrame main = new MainFrame();
            main.setSession(session);
            main.setPeliculaRepository(peliRepo);
            main.initAfterLogin();
            main.setLocationRelativeTo(this);

            main.wireActions(peliRepo);

            main.setVisible(true);




        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error de acceso a datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // getter

    public String getEmail()    { return txtEmail.getText().trim(); }
    public String getPassword() { return new String(txtPassword.getPassword()).trim(); }
    public JButton getBtnEntrar() { return btnEntrar; }
}
