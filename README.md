# Seg-A: Sistema de Control de Acceso Universitario

---

## Descripción:

Seg-A es una API REST desarrollada en Spring Boot para gestionar el control de acceso a distintos espacios dentro de una universidad. Los usuarios utilizan un código QR generado por una aplicación móvil, que es escaneado por una cámara conectada a un Raspberry Pi en cada puerta. Si el usuario tiene permiso de acceso en el horario correspondiente, el sistema desbloquea la puerta y registra el ingreso en la base de datos.

## Características Principales:

- **Autenticación y Autorización**: Autenticación basada en JWT para asegurar el acceso a la API.
- **Gestión de Usuarios y Roles**: Manejo de usuarios y sus roles para definir niveles de acceso.
- **Control de Salones y Clases**: Administración de salones, clases y materias dentro del sistema.
- **Reservas y Asistencias**: Sistema para gestionar reservas de salones y registrar asistencias de usuarios.
- **Integración con Raspberry Pi**: Verificación de acceso a través de lectura de códigos QR.

## Estructura del Proyecto:

- **config**: Configuración de seguridad, incluyendo filtros JWT y reglas de autorización.
- **controllers**: Controladores que gestionan las peticiones a la API, incluyendo autenticación, gestión de salones, clases, reservas y usuarios.
- **dto**: Objetos de transferencia de datos para las solicitudes y respuestas de los diferentes servicios.
- **models**: Entidades que representan las tablas de la base de datos, como User, Classroom, Lesson, y Reservation.
- **repositories**: Interfaces para interactuar con la base de datos.
- **services**: Lógica de negocio para autenticar, manejar reservas, gestionar asistencias, entre otros servicios.

## Seguridad:

La autenticación se realiza mediante JWT. Cada solicitud protegida debe incluir el token en el encabezado `Authorization` como `Bearer <token>`.

## Instalación y Configuración

### Prerrequisitos:

- **Java 17+**: Asegúrate de tener instalado Java 17 o una versión superior.
- **Maven**: Para la construcción y gestión del proyecto.
- **PostgreSQL**: Base de datos utilizada para el almacenamiento de datos.

### Instalación:

1. Clona el repositorio:
   ```bash
   git clone https://github.com/DanielRHades/Seg-A_SpringBoot_API.git
   ```
2. Configura las credenciales de la base de datos: Edita el archivo application.properties ubicado en src/main/resources y agrega tus credenciales de PostgreSQL:
   ```bash
   spring.datasource.url=jdbc:postgresql://localhost:5432/segaproj_db
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   ```
3. Construye el proyecto: Ejecuta el siguiente comando para limpiar y construir el proyecto con Maven:
   ```bash
   mvn clean install
   ```
4. Ejecuta la aplicación: Una vez completada la construcción, inicia la aplicación con:
   ```bash
   mvn spring-boot:run
   ```

## Verificación:

Una vez iniciada la aplicación, debería estar disponible en http://localhost:8080. Puedes verificar los endpoints de la API o usar herramientas como Postman para interactuar con el sistema.
