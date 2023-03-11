# base image
FROM node:16.14.0-alpine

# set working directory
WORKDIR /app

# copy package.json and package-lock.json
COPY package*.json ./

# install dependencies
RUN npm install

# copy project files
COPY . .

# build app
RUN npm run build

# set environment variable
ENV REACT_APP_API_URL=http://localhost:8888

# start app
CMD ["npm", "start"]
