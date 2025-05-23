-- Tabla: clientes
CREATE TABLE IF NOT EXISTS clientes (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    direccion VARCHAR(255),
    nif VARCHAR(20),
    nacimiento DATE,
    es_negocio BOOLEAN NOT NULL DEFAULT FALSE
);

-- Tabla: oficinas
CREATE TABLE IF NOT EXISTS oficinas (
    id SERIAL PRIMARY KEY,
    direccion VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(50) NOT NULL
);

-- Tabla: coches
CREATE TABLE IF NOT EXISTS coches (
    id SERIAL PRIMARY KEY,
    modelo VARCHAR(255) NOT NULL,
    marca VARCHAR(255) NOT NULL,
    transmision VARCHAR(20) NOT NULL,
    categoria VARCHAR(20) NOT NULL,
    puertas SMALLINT,
    techo_solar BOOLEAN NOT NULL DEFAULT FALSE,
    tarifa_diaria DOUBLE PRECISION,
    tarifa_semanal DOUBLE PRECISION,
    tarifa_mensual DOUBLE PRECISION,
    oficina_id INTEGER,
    CONSTRAINT fk_coche_oficina FOREIGN KEY (oficina_id) REFERENCES oficinas(id)
);

-- Tabla: coche_extras
CREATE TABLE IF NOT EXISTS coche_extras (
    coche_id INTEGER NOT NULL,
    extras VARCHAR(255),
    FOREIGN KEY (coche_id) REFERENCES coches(id) ON DELETE CASCADE
);

-- Tabla: codigos_descuento
CREATE TABLE IF NOT EXISTS codigos_descuento (
    codigo VARCHAR(255) PRIMARY KEY,
    descuento FLOAT NOT NULL
);

-- Tabla: reservas
CREATE TABLE IF NOT EXISTS reservas (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER NOT NULL,
    coche_id INTEGER NOT NULL,
    oficina_recogida_id INTEGER NOT NULL,
    oficina_devolucion_id INTEGER NOT NULL,
    fecha_hora_recogida TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_hora_devolucion TIMESTAMP NOT NULL DEFAULT NOW(),
    tipo_tarifa VARCHAR(50),
    estado VARCHAR(50) NOT NULL DEFAULT 'PENDIENTE',
    precio DOUBLE PRECISION NOT NULL DEFAULT 0,
    CONSTRAINT fk_reserva_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    CONSTRAINT fk_reserva_coche FOREIGN KEY (coche_id) REFERENCES coches(id),
    CONSTRAINT fk_reserva_oficina_recogida FOREIGN KEY (oficina_recogida_id) REFERENCES oficinas(id),
    CONSTRAINT fk_reserva_oficina_devolucion FOREIGN KEY (oficina_devolucion_id) REFERENCES oficinas(id)
);

-- Inserciones

-- Clientes
INSERT INTO clientes (nombre, apellidos, email, password, direccion, nif, nacimiento, es_negocio) VALUES
('Juan', 'Pérez', 'juan@example.com', '1234', 'Calle Falsa 123', '12345678A', '1990-05-01', false),
('Empresa', 'SL', 'empresa@example.com', 'abcd', 'Avenida Empresa 456', 'B12345678', '1985-03-10', true);

-- Oficinas
INSERT INTO oficinas (direccion, telefono) VALUES
('Calle Mayor 1, Madrid', '911223344'),
('Avenida del Parque 23, Barcelona', '933112233'),
('Calle Sol 5, Valencia', '961234567'),
('Calle Luna 10, Sevilla', '954321098'),
('Calle Estrella 15, Bilbao', '944556677');

-- Coches
INSERT INTO coches (modelo, marca, transmision, categoria, puertas, techo_solar, tarifa_diaria, tarifa_semanal, tarifa_mensual, oficina_id)
VALUES
('Model 3', 'Tesla', 'AUTOMATICA', 'ALTA', 4, true, 70, 450, 1700,
 (SELECT id FROM oficinas WHERE direccion = 'Calle Mayor 1, Madrid')),
('Ibiza', 'Seat', 'MANUAL', 'BAJA', 5, false, 30, 180, 650,
 (SELECT id FROM oficinas WHERE direccion = 'Avenida del Parque 23, Barcelona')),
-- Nuevos coches para Madrid
('Clio', 'Renault', 'MANUAL', 'BAJA', 5, false, 28, 170, 600,
 (SELECT id FROM oficinas WHERE direccion = 'Calle Mayor 1, Madrid')),
('Golf', 'Volkswagen', 'AUTOMATICA', 'MEDIA', 5, true, 50, 300, 1100,
 (SELECT id FROM oficinas WHERE direccion = 'Calle Mayor 1, Madrid')),
('Model S', 'Tesla', 'AUTOMATICA', 'ALTA', 4, true, 90, 580, 2200,
 (SELECT id FROM oficinas WHERE direccion = 'Calle Mayor 1, Madrid')),
-- Nuevos coches para Barcelona
('Leon', 'Seat', 'MANUAL', 'MEDIA', 5, false, 45, 280, 1000,
 (SELECT id FROM oficinas WHERE direccion = 'Avenida del Parque 23, Barcelona')),
('A3', 'Audi', 'AUTOMATICA', 'ALTA', 5, true, 65, 400, 1500,
 (SELECT id FROM oficinas WHERE direccion = 'Avenida del Parque 23, Barcelona')),
-- Nuevos coches para Valencia
('Fiesta', 'Ford', 'MANUAL', 'BAJA', 3, false, 25, 150, 550,
 (SELECT id FROM oficinas WHERE direccion = 'Calle Sol 5, Valencia')),
('Focus', 'Ford', 'AUTOMATICA', 'MEDIA', 5, true, 48, 290, 1050,
 (SELECT id FROM oficinas WHERE direccion = 'Calle Sol 5, Valencia')),
('Corolla', 'Toyota', 'AUTOMATICA', 'MEDIA', 5, false, 52, 320, 1150,
 (SELECT id FROM oficinas WHERE direccion = 'Calle Sol 5, Valencia')),
-- Nuevos coches para Sevilla
('208', 'Peugeot', 'MANUAL', 'BAJA', 5, false, 27, 160, 580,
 (SELECT id FROM oficinas WHERE direccion = 'Calle Luna 10, Sevilla')),
('Corsa', 'Opel', 'MANUAL', 'BAJA', 3, false, 26, 155, 560,
 (SELECT id FROM oficinas WHERE direccion = 'Calle Luna 10, Sevilla')),
('Megane', 'Renault', 'AUTOMATICA', 'MEDIA', 5, true, 50, 310, 1120,
 (SELECT id FROM oficinas WHERE direccion = 'Calle Luna 10, Sevilla')),
-- Nuevos coches para Bilbao
('Polo', 'Volkswagen', 'MANUAL', 'BAJA', 5, false, 29, 175, 620,
 (SELECT id FROM oficinas WHERE direccion = 'Calle Estrella 15, Bilbao')),
('Astra', 'Opel', 'AUTOMATICA', 'MEDIA', 5, false, 47, 285, 1030,
 (SELECT id FROM oficinas WHERE direccion = 'Calle Estrella 15, Bilbao')),
('Yaris', 'Toyota', 'AUTOMATICA', 'BAJA', 5, false, 32, 190, 680,
 (SELECT id FROM oficinas WHERE direccion = 'Calle Estrella 15, Bilbao'));

-- Extras
INSERT INTO coche_extras (coche_id, extras)
VALUES
((SELECT id FROM coches WHERE modelo = 'Model 3' AND marca = 'Tesla'), 'GPS'),
((SELECT id FROM coches WHERE modelo = 'Model 3' AND marca = 'Tesla'), 'Asientos calefactables'),
((SELECT id FROM coches WHERE modelo = 'Ibiza' AND marca = 'Seat'), 'Bluetooth'),
-- Extras para Clio
((SELECT id FROM coches WHERE modelo = 'Clio' AND marca = 'Renault'), 'Bluetooth'),
-- Extras para Golf
((SELECT id FROM coches WHERE modelo = 'Golf' AND marca = 'Volkswagen'), 'Sensores de aparcamiento'),
-- Extras para Model S
((SELECT id FROM coches WHERE modelo = 'Model S' AND marca = 'Tesla'), 'Piloto automático'),
((SELECT id FROM coches WHERE modelo = 'Model S' AND marca = 'Tesla'), 'GPS'),
-- Extras para Leon
((SELECT id FROM coches WHERE modelo = 'Leon' AND marca = 'Seat'), 'Control de crucero'),
-- Extras para A3
((SELECT id FROM coches WHERE modelo = 'A3' AND marca = 'Audi'), 'Asientos de cuero'),
((SELECT id FROM coches WHERE modelo = 'A3' AND marca = 'Audi'), 'Bluetooth'),
-- Extras para Fiesta
((SELECT id FROM coches WHERE modelo = 'Fiesta' AND marca = 'Ford'), 'Aire Acondicionado'),
-- Extras para Focus
((SELECT id FROM coches WHERE modelo = 'Focus' AND marca = 'Ford'), 'Bluetooth'),
((SELECT id FROM coches WHERE modelo = 'Focus' AND marca = 'Ford'), 'GPS'),
-- Extras para Corolla
((SELECT id FROM coches WHERE modelo = 'Corolla' AND marca = 'Toyota'), 'Cámara trasera'),
-- Extras para 208
((SELECT id FROM coches WHERE modelo = '208' AND marca = 'Peugeot'), 'Bluetooth'),
-- Extras para Corsa
((SELECT id FROM coches WHERE modelo = 'Corsa' AND marca = 'Opel'), 'Aire Acondicionado'),
-- Extras para Megane
((SELECT id FROM coches WHERE modelo = 'Megane' AND marca = 'Renault'), 'Sensores de lluvia'),
-- Extras para Polo
((SELECT id FROM coches WHERE modelo = 'Polo' AND marca = 'Volkswagen'), 'Bluetooth'),
-- Extras para Astra
((SELECT id FROM coches WHERE modelo = 'Astra' AND marca = 'Opel'), 'Control de crucero'),
-- Extras para Yaris
((SELECT id FROM coches WHERE modelo = 'Yaris' AND marca = 'Toyota'), 'Sistema de mantenimiento de carril');

-- Códigos de descuento
INSERT INTO codigos_descuento (codigo, descuento) VALUES
('BIENVENIDA10', 10.0),
('EMPRESA15', 15.0);

-- Reservas
INSERT INTO reservas (
    cliente_id, coche_id, oficina_recogida_id, oficina_devolucion_id,
    fecha_hora_recogida, fecha_hora_devolucion, tipo_tarifa, estado, precio
) VALUES
(
    (SELECT id FROM clientes WHERE email = 'juan@example.com'),
    (SELECT id FROM coches WHERE modelo = 'Model 3'),
    (SELECT id FROM oficinas WHERE direccion = 'Calle Mayor 1, Madrid'),
    (SELECT id FROM oficinas WHERE direccion = 'Avenida del Parque 23, Barcelona'),
    '2025-05-25 10:00:00', '2025-05-30 10:00:00', 'DIARIA_KILOMETRAJE', 'EN_PROCESO', 500.0
),
(
    (SELECT id FROM clientes WHERE email = 'empresa@example.com'),
    (SELECT id FROM coches WHERE modelo = 'Ibiza'),
    (SELECT id FROM oficinas WHERE direccion = 'Avenida del Parque 23, Barcelona'),
    (SELECT id FROM oficinas WHERE direccion = 'Avenida del Parque 23, Barcelona'),
    '2025-06-01 09:00:00', '2025-06-05 09:00:00', 'FIN_DE_SEMANA', 'APROBADO', 300.0
);
