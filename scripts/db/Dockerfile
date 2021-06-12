FROM mysql:5.7.15

MAINTAINER me

ENV MYSQL_DATABASE=magneto \
    MYSQL_ROOT_PASSWORD=root

ADD create.sql /docker-entrypoint-initdb.d

EXPOSE 3306