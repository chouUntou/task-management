# Use an official Node.js runtime as a parent image
FROM node:18-alpine AS build

# Set the working directory
RUN mkdir /front
WORKDIR /front

# Copy the package.json and install dependencies
COPY package*.json ./
# 这个命令全局安装了指定版本的 Angular CLI（13版本）。这对于构建和开发 Angular 应用是必要的，但全局安装的过程可能会耗费一些时间
#RUN npm install -g @angular/cli@13
RUN npm install
RUN npm ci

# Copy the rest of the application
COPY . .

# Build the Angular app
RUN npm run build --prod

# Stage 2: Serve the application using Nginx
FROM nginx:alpine

# Copy the built Angular app to the Nginx directory
COPY --from=build /front/dist/angular /usr/share/nginx/html

# Expose the port on which the app runs
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]
