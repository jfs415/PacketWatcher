FROM node:alpine
LABEL authors="jfs415"
WORKDIR /packetwacter-ui
COPY package.json .
RUN npm i
COPY . .
CMD ["npm", "start"]