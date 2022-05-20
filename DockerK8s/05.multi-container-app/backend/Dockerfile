FROM node:14

WORKDIR /app

COPY ./package.json .

RUN npm install

COPY . .

ENV PORT 80

ENV MONGODB_USER_NAME=andy
ENV MONGODB_USER_PASSWORD=notRight

EXPOSE ${PORT}

VOLUME [ "/app/node_modules" ]

CMD [ "node", "app.js" ]