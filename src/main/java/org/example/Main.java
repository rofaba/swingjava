package org.example;

import org.example.infra.*;
import org.example.view.LoginDialog;
import javax.swing.*;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Dependencias lógicas
            SessionContext session = SessionContext.get();
            UsuarioRepository userRepo = new CsvUsuarioRepository(Path.of("data/usuarios.csv"), false);
            PeliculaRepository peliRepo = new CsvPeliculaRepository(Path.of("data/peliculas.csv"), false);
            AuthProvider auth = new CsvAuthProvider(userRepo, session);

            // Ventana de login
            LoginDialog login = new LoginDialog();
            login.setAuth(auth);
            login.setSession(session);
            login.setPeliculaRepository(peliRepo);
            login.setLocationRelativeTo(null);
            login.setVisible(true);
        });
    }
}