FROM postgres:15.2

ENV POSTGRES_USER scrm
ENV POSTGRES_PASSWORD scrm
ENV POSTGRES_DB scrm

COPY ./initialize /docker-entrypoint-initdb.d

EXPOSE 5432