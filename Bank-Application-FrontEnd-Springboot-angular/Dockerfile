# frontend/Dockerfile
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --prod

FROM nginx:alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/* /usr/share/nginx/html/
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]




# -------- Stage 1: Build Angular --------
#FROM node:20-alpine AS build
#WORKDIR /app
#
#COPY package*.json ./
#RUN npm install
#
#COPY . .
#RUN npm run build -- --configuration production
#
## -------- Stage 2: Runtime --------
#FROM node:20-alpine
#WORKDIR /app
#
#RUN npm install -g serve
#
## Copier tout dist (peu importe le nom du projet)
#COPY --from=build /app/dist ./dist
#
#EXPOSE 3000
#CMD ["serve", "-s", "dist", "-l", "3000"]
#
