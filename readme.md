# 🎬 Proyecto Gestor de Películas (Java Swing)

Aplicación de escritorio desarrollada en **Java Swing** para gestionar una colección de películas almacenadas en archivos **CSV**.  
Incluye un sistema de **autenticación de usuarios** y soporte para mostrar **imágenes desde URL**.

## 🚀 Funcionalidades principales
- Inicio y cierre de sesión con validación de usuario.
- Visualización y gestión básica de películas (listar, agregar, eliminar).
- Carga segura de imágenes desde URL.
- Almacenamiento persistente mediante archivos CSV.

## 🧩 Estructura del proyecto
- `model/` → Clases de dominio (`Usuario`, `Pelicula`).
- `infra/` → Repositorios CSV y proveedor de autenticación (`CsvAuthProvider`, `CsvPeliculaRepository`).
- `view/` → Interfaces gráficas (`LoginDialog`, `MainFrame`, `NuevoPeliculaDialog`, etc.).
- `controller/` → Lógica de control y coordinación entre vista y modelo.

## ⚙️ Tecnologías
- **Java 17+**
- **Swing**
- **Maven**

## ▶️ Ejecución
1. Clonar el repositorio.
2. Abrir el proyecto en IntelliJ IDEA o cualquier IDE con soporte para Maven.
3. Ejecutar la clase `Main` desde el paquete `org.example`.
