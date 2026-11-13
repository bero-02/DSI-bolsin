# 📦 Sistema de Gestión de Bolsines

Implementación del **Caso de Uso 36: Consultar seguimiento de bolsines** (incluyendo el CU 31: Notificar ubicación de bolsín)

## 🚀 Tecnologías Implementadas y su Propósito

### Backend (Lógica de Negocio y Persistencia)
* **Java 17:** Lenguaje de programación principal utilizado para modelar las entidades del dominio, aplicando estrictamente los conceptos de Orientación a Objetos Pura (patrones GRASP, Controlador, Experto) exigidos por la cátedra.
* **Spring Boot (Spring Web):** Framework base del backend. Se encarga de levantar el servidor web embebido (Tomcat) y exponer los endpoints REST (`@RestController`) para que el frontend pueda consumir los datos (como consultar la ubicación o confirmar el envío del mail).
* **Spring Data JPA e Hibernate:** Herramientas de ORM (Object-Relational Mapping). Permiten que las clases Java anotadas con `@Entity` se mapeen automáticamente a tablas relacionales en la base de datos, gestionando las relaciones (`@ManyToOne`, `@OneToMany`) sin necesidad de escribir consultas SQL manuales.
* **PostgreSQL:** Motor de base de datos relacional donde se almacena de forma persistente toda la información del sistema (bolsines, historial de estados, comisiones médicas, empleados y usuarios).
* **Maven (`pom.xml` y `mvnw`):** Gestor de dependencias y construcción del proyecto. Se encarga de descargar las librerías necesarias (driver de Postgres, Spring Boot) y compilar el código Java.

### Frontend (Interfaz de Usuario)
* **HTML5:** Define la estructura semántica de la pantalla del caso de uso, organizando la cabecera, los filtros, el contenedor del mapa, el listado lateral y las ventanas modales.
* **CSS3:** Provee todo el diseño visual implementando un tema *Dark/Tech* con *Glassmorphism* (efectos de desenfoque y semitransparencia). Utiliza variables nativas (`:root`), Flexbox y CSS Grid para organizar el layout de forma responsiva.
* **JavaScript (Vanilla JS):** Maneja la interactividad del lado del cliente sin usar frameworks pesados, dividiendo responsabilidades:
  * `api.js`: Utiliza la Fetch API nativa del navegador para realizar peticiones HTTP asíncronas al backend.
  * `main.js`: Controla el DOM (manejo de clics, estados de modales), aplica filtros en memoria y gestiona la secuencia visual del caso de uso.
* **Leaflet.js:** Librería open-source de JavaScript especializada en mapas interactivos. Toma las coordenadas (latitud y longitud) provistas por el backend y dibuja el mapa geográfico, los marcadores y los tooltips emergentes.
* **OpenStreetMap / CARTO:** Proveedores de los "tiles" (mosaicos de imágenes) que Leaflet utiliza para renderizar el fondo del mapa geográfico oscuro.

## 🏗️ Arquitectura y Patrones de Diseño
El proyecto respeta los lineamientos de **Orientación a Objetos Pura** exigidos por la cátedra:
* **Patrón Experto (GRASP):** Delegación de responsabilidades en las entidades del dominio (ej. `Bolsin.esEnviado()`, `Empleado.buscarCM()`).
* **Arquitectura en Capas:** Separación clara entre Presentación (Pantalla frontera), Controladores (REST), Servicios (Gestores) y Acceso a Datos (Repositorios en memoria y JPA).

## ⚙️ Instrucciones de Instalación y Ejecución

### 1. Base de Datos
* Crear una base de datos vacía en PostgreSQL desde pgAdmin o la terminal:
CREATE DATABASE dsi_bolsines;

### 2. Levantar el Backend
El sistema utiliza un DataInitializer para crear las tablas automáticamente y poblar la base de datos con los registros simulados al iniciar. Para ejecutarlo, abrir una terminal en la carpeta BACKEND y correr el Maven Wrapper:

En Windows (PowerShell/CMD):
.\mvnw.cmd spring-boot:run


En Linux/Git Bash:
./mvnw spring-boot:run

### 3. Levantar el Frontend
Abrir la carpeta FRONT en el editor y lanzar el archivo index.html utilizando la extensión Live Server, o simplemente abrir el archivo directamente en cualquier navegador moderno.