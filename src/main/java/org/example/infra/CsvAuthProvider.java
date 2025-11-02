package org.example.infra;
import org.example.model.Usuario;
import java.io.IOException;
import java.util.Optional;

/** Autenticación simple contra el CSV. */
public class CsvAuthProvider implements AuthProvider {

    private final UsuarioRepository usuarios;
    private final SessionContext session;

    public CsvAuthProvider(UsuarioRepository usuarios, SessionContext session) {
        this.usuarios = usuarios;
        this.session = session;
    }

    @Override
    public Usuario login(String email, String password) throws IOException {
        String e = email == null ? "" : email.trim();
        String p = password == null ? "" : password.trim();
        if (e.isEmpty() || p.isEmpty()) return null;

        Optional<Usuario> u = usuarios.findByEmail(e);
        if (u.isEmpty()) return null;

        Usuario user = u.get();
        if (!p.equals(user.getPassword())) return null;
        session.setCurrentUser(user); // guarda sesión aquí
        return user;
    }


    @Override
    public void logout() {
    }

    @Override
    public Usuario getCurrentUser() {
        return null;
    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }
}
