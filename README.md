# Kritikin

## First time setup

make a copy of, and remove the .example part from docker-compose.yml.example and src/main/resources/application.properties.example.
add a mathcing password for postgres to both files.

Install [Docker and Docker Compose](https://docs.docker.com/get-docker/).

## Running

```bash
docker-compose up --build
```

Open <http://localhost:8080/> in a web browser.
