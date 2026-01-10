## frontend/Dockerfile
#FROM node:20-alpine AS build
#WORKDIR /app
#COPY package*.json ./
#RUN npm install
#COPY . .
#RUN npm run build
#
#
#FROM nginx:alpine
#COPY --from=build /app/dist/bancAppFront /usr/share/nginx/html
#EXPOSE 80
# -------- Stage 1: Build Angular --------
FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build -- --configuration production

# -------- Stage 2: Runtime --------
FROM node:20-alpine
WORKDIR /app

# Installer un serveur statique léger
RUN npm install -g serve

# ⚠️ Remplace bancAppFront si nécessaire
COPY --from=build /app/dist/bancAppFront ./dist

EXPOSE 3000
CMD ["serve", "-s", "dist", "-l", "3000"]
