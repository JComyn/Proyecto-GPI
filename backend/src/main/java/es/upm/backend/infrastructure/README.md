# Capa de Infraestructura

📌 **Información en constante actualización**  
_La información de esta capa se irá ajustando según las necesidades del proyecto._

## 📂 Estructura de Paquetes
La capa de **infraestructura** contiene los siguientes paquetes esenciales:

### 📌 `controllers/`
Encargado de gestionar la comunicación con el mundo exterior, proporcionando los endpoints de la API. Sus principales responsabilidades incluyen:
- Exponer las operaciones disponibles del sistema.
- Validar y procesar las solicitudes HTTP.
- Invocar los servicios de aplicación para ejecutar la lógica de negocio.
- Manejar respuestas y excepciones.

### ⚙️ `config/`
Contiene la configuración de la aplicación, incluyendo:
- Definición de beans y dependencias.
- CORS.
- Configuraciones específicas para cada entorno (desarrollo, producción, etc.).

### 🗄 `repositories/`
Implementa el acceso a los datos y la persistencia, interactuando con la base de datos a través de:
- Métodos personalizados para consultas avanzadas.
- Transacciones y optimización de acceso a datos.

📌 **Última actualización:** _[11/03/2025]_

