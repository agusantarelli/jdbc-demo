# jdbc-demo

## Prop&oacute;sito

Este proyecto tiene como objetivo demostrar c&oacute;mo realizar consultas a una Base de datos utilizando la API JDBC.

- [Pre-requisitos](#pre-requisitos)
    * [Java 11](#java-11)
    * [MySQL](#mysql)
    * [Lombok Plugin](#lombok-plugin)
- [Ejecuci&oacute;n](#ejecución)
- [Contacto](#contacto)

## Pre-requisitos

### Java 11

En caso de no contar con dicha versi&oacute;n de Java instalada, se recomienda hacerlo mediante [SdkMan](https://sdkman.io/).

Se pueden listar las versiones disponibles ejecutando en la terminal el comando:

```bash
sdk list java
```

Una vez identificada la versi&oacute;n a utilizar (Ej: 11.0.3-zulu), ejecutar el siguiente comando en la terminal:

 ```bash
 sdk use java 11.0.3-zulu
 ```

### MySQL

Por cuestiones pr&aacute;cticas, utilizaremos una imagen de Docker.

En `resources/sql/init.sql` se encuentra un script SQL con la definici&oacute;n del esquema a utilizar y algunos datos de prueba.

Abrir una terminal en la ra&iacute;z del proyecto y ejecutar:

```bash
sudo docker run --name movies-db \
        -e MYSQL_ROOT_PASSWORD=mysql \
        -v $PWD/jdbc-demo/src/main/resources/sql:/docker-entrypoint-initdb.d/:ro \
        -p 3306:3306 \
        --rm -d mysql:8.0.25
```

Verificar en los logs del container que este se haya creado correctamente.

Ejecutar `sudo docker logs -f movies-db`. Se deber&aacute; encontrar una linea similar a:

```bash
...
2021-06-20T21:05:48.247618Z 0 [System] [MY-010931] [Server] /usr/sbin/mysqld: ready for connections. Version: '8.0.25'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server - GPL.
```

### Lombok Plugin

Dependiendo del IDE que se utilice, seguir las instrucciones para instalar el plugin correctamente.

- [IntelliJ Idea](https://projectlombok.org/setup/intellij)
- [Eclipse](https://projectlombok.org/setup/eclipse)

## Ejecuci&oacute;n

Ubicados en la ra&iacute;z del proyecto, ejecutar en la terminal:

```bash
./gradlew run
```

## Contacto

- **Ariel Gámez** (ariel.gamez@redb.ee)
- **Lautaro Aguilera** (lautaro.aguilera@redb.ee)
- **Mariano Ludueña** (mariano.luduena@redb.ee)
- **Santiago Leiva** (santiago.leiva@redb.ee)
