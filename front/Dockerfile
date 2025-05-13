FROM node:20

# Crear directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar package.json y package-lock.json primero para aprovechar caché de Docker
COPY package*.json ./

# Instalar dependencias
RUN npm install

# Copiar el resto del código
COPY . .

# Exponer el puerto que usa Vite o React (CRA)
EXPOSE 3000

# Comando para desarrollo
CMD ["npm", "start"]
