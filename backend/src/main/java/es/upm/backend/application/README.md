# Capa de Aplicación

📌 **Información en constante actualización**  
_La información de esta capa se irá ajustando según las necesidades del proyecto._

## 📂 Estructura de Paquetes
La capa de **aplicación** contiene la lógica de negocio y los casos de uso. Sus principales paquetes son:

### 📌 `dto/`
Este paquete agrupa los **Data Transfer Objects (DTOs)**, utilizados para la comunicación entre capas y la transferencia de datos. Sus características principales incluyen:
- Definir objetos que encapsulan datos sin exponer directamente las entidades del dominio.
- Evitar acoplamiento innecesario entre la capa de infraestructura y el dominio.
- Permitir validaciones y transformaciones de datos antes de su procesamiento.
- Mejorar la seguridad y la eficiencia en la transmisión de información.

### ⚙️ `services/`
Este paquete contiene los **servicios de aplicación**, encargados de orquestar los casos de uso y la lógica de negocio. Sus responsabilidades incluyen:
- Implementar la lógica de negocio sin depender de la infraestructura.
- Coordinar la interacción entre los repositorios y las entidades del dominio.
- Garantizar la integridad de las operaciones sobre los datos.
- Exponer métodos que serán consumidos por los controladores.

📌 **Última actualización:** _[11/03/2025]_

