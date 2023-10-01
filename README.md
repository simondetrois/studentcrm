# About this app

Fullstack Webapp to admin students and courses

## Start this app

- either run docker-compose in `./infrastructure`
- or do following:
  - docker run -d -p 5432:5432 --name scrmdb -e POSTGRES_USER=scrmdb -e POSTGRES_PASSWORD=scrmdb postgres:15.4
  - start backend
  - start frontend
