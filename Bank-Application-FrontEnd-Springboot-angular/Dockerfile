# frontend/Dockerfile
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build


FROM nginx:alpine
COPY --from=build /app/dist/Bank-Application-FrontEnd-Springboot-angular /usr/share/nginx/html
EXPOSE 80
