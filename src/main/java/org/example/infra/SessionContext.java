package org.example.infra;

import org.example.model.Usuario;

public class SessionContext {
    /** Singleton
     * @return instancia única de SessionContext
     */

    private static final SessionContext INSTANCE = new SessionContext();
    public static SessionContext get() { return INSTANCE; }
    private Usuario currentUser;
    public Usuario getCurrentUser() { return currentUser; }
    public void setCurrentUser(Usuario u) { this.currentUser = u; }
    public void clear() { this.currentUser = null; }
}
