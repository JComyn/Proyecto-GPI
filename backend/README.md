# Auto Veloz - Backend

📌 **Proyecto para la asignatura: Gestión de Proyectos Informáticos (GPI)**

## 🏗️ Arquitectura
El backend sigue una **arquitectura hexagonal** y está estructurado separando la lógica en capas:

- **Capa de Aplicación** → Orquesta los casos de uso y la lógica de negocio.
- **Capa de Dominio** → Define las entidades y repositorios centrales.
- **Capa de Infraestructura** → Maneja la comunicación con la base de datos y los controladores REST.

## 📂 Estructura del Proyecto
El proyecto se organiza en los siguientes paquetes:

```
📦 src/main/java/com/autoveloz
 ┣ 📂 application      # Lógica de negocio y casos de uso
 ┣ 📂 domain           # Modelos de datos y repositorios
 ┗ 📂 infrastructure   # Controladores, configuración y persistencia
```


## ⚙️ Configuración y Ejecución
### 1️⃣ Clonar el repositorio
```sh
git clone git@github.com:JComyn/Proyecto-GPI.git
cd Proyecto-GPI
```
### 3️⃣ Compilar y ejecutar
```sh
cd backend
gradlew bootRun # En Windows
./gradlew bootRun # En Unix
```

## 📄 Documentación de la API
El servicio expone endpoints documentados con **Swagger**. Para acceder a la documentación:
```
http://localhost:8080/swagger
```

## 🚀 Despliegue con Docker
Aún no configurado

## 🏆 Equipo de Desarrollo

📌 **Última actualización:** _[11/03/2025]_

