# Используем официальный Node.js образ
FROM node:14

# Рабочая директория внутри контейнера
WORKDIR /app

# Копируем package.json и package-lock.json
COPY package*.json ./

# Устанавливаем зависимости
RUN npm install

# Копируем остальные файлы проекта (включая .env)
COPY . .

# Открываем порт для dev-сервера
EXPOSE 3000

# Важно! Запускаем dev-сервер на 0.0.0.0, иначе он будет слушать только localhost!
ENV HOST 0.0.0.0

# Запускаем React-приложение (dev-режим)
CMD ["npm", "start"]
