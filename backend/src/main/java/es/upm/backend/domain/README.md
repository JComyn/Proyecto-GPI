# Capa de Dominio

📌 **Información en constante actualización**  
_La información de esta capa se irá ajustando según las necesidades del proyecto._

## 📂 Estructura de Paquetes
La capa de **dominio** contiene los siguientes paquetes esenciales:

### 📌 `entities/`
Este paquete define las entidades del dominio y representa los modelos de datos utilizados en la aplicación. Sus características principales incluyen:
- Definir clases que representan los objetos del dominio.
- Contener anotaciones de persistencia (`@Entity`, `@Table`, `@Id`, etc.).
- Incluir validaciones y reglas propias del dominio.
- Garantizar la integridad y consistencia de los datos.

### 🗄 `repositories/`
Este paquete encapsula la lógica de acceso a los datos y proporciona una abstracción para la persistencia. Sus funciones principales son:
- Proporcionar métodos de consulta personalizados.
- Mantener independencia respecto a la implementación de la base de datos.

📌 **Última actualización:** _[11/03/2025]_

