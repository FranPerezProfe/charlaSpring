FROM node:18-alpine as ui-build

COPY . /app

WORKDIR /app

RUN npm install
RUN npm run build

FROM nginx:latest
COPY --from=ui-build /app/dist/angular-15-crud /usr/share/nginx/html
COPY nginx.conf  /etc/nginx/conf.d/default.conf
