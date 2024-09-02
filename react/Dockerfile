# 基于官方的Node.js镜像构建
FROM node:16-alpine

# 创建应用目录
WORKDIR /app

# 复制package.json和package-lock.json
COPY package*.json ./

# 安装依赖
RUN npm install

# 复制项目文件
COPY . .

# 编译项目
RUN npm run build

# 使用Nginx提供服务
FROM nginx:alpine
COPY --from=0 /app/build /usr/share/nginx/html
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]

