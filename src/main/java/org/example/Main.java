package org.example;

import org.example.infra.*;
import org.example.view.LoginDialog;

import javax.swing.*;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // --- dependencias lógicas ---
            SessionContext session = SessionContext.get(); // o new SessionContext()
            UsuarioRepository userRepo = new CsvUsuarioRepository(Path.of("data/usuarios.csv"), false);
            PeliculaRepository peliRepo = new CsvPeliculaRepository(Path.of("data/peliculas.csv"), false);
            AuthProvider auth = new CsvAuthProvider(userRepo, session);

            // --- ventana de login (GUI Builder) ---
            LoginDialog login = new LoginDialog();
            login.setAuth(auth);
            login.setSession(session);
            login.setPeliculaRepository(peliRepo);  // <-- importante para que luego MainFrame cargue datos
            login.setLocationRelativeTo(null);
            login.setVisible(true);
        });
    }
}
