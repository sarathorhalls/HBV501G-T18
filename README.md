# Kritikin

## First time setup

Make a copy of, and remove the .example part from docker-compose.yml.example and src/main/resources/application.properties.example.

Add a matching password for postgres to both files.

Install [Docker and Docker Compose](https://docs.docker.com/get-docker/).

If there is a target folder in the root directory remove it (Maven sometimes likes to create a folder called target which clashes with docker)

## Running

```bash
docker-compose up --build
```

Open <http://localhost/> in a web browser.
