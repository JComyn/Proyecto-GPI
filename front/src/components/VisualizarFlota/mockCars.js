const mockCars = [
  {
    marca: "Toyota",
    modelo: "Corolla",
    categoria: "Sedán",
    puertas: 4,
    transmision: "Automática",
    techoSolar: true,
    tarifa: { diaria: 50, semanal: 300, mensual: 1000 },
    extras: ["GPS", "Asientos de cuero"],
  },
  {
    marca: "Ford",
    modelo: "Focus",
    categoria: "Compacto",
    puertas: 4,
    transmision: "Manual",
    techoSolar: false,
    tarifa: { diaria: 40, semanal: 250, mensual: 900 },
    extras: ["Bluetooth", "Cámara trasera"],
  },
  {
    marca: "Tesla",
    modelo: "Model 3",
    categoria: "Eléctrico",
    puertas: 4,
    transmision: "Automática",
    techoSolar: true,
    tarifa: { diaria: 100, semanal: 600, mensual: 2000 },
    extras: ["Autopilot", "Pantalla táctil"],
  },
];

export default mockCars;