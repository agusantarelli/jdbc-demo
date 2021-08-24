# jdbc-demo

## Prop&oacute;sito

Este proyecto tiene como objetivo demostrar c&oacute;mo realizar consultas a una Base de datos utilizando la API JDBC.

- [Pre-requisitos](#pre-requisitos)
    * [Java 11](#java-11)
    * [Postgres](#postgres)
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

### Postgres

Por cuestiones pr&aacute;cticas, utilizaremos una imagen de Docker.

En `resources/sql/init.sql` se encuentra un script SQL con la definici&oacute;n del esquema a utilizar y algunos datos de prueba.

Abrir una terminal en la ra&iacute;z del proyecto y ejecutar:

```bash
sudo docker run --name movies-db \
	-e POSTGRES_USER=postgres \
	-e POSTGRES_PASSWORD=postgres \
	-e POSTGRES_DB=movies \
	-v $PWD/src/main/resources/sql:/docker-entrypoint-initdb.d/:ro \
	-p 5432:5432 \
	--rm -d postgres:12.7-alpine
```

Verificar en los logs del container que este se haya creado correctamente.

Ejecutar `sudo docker logs -f movies-db`. Se deber&aacute; encontrar una linea similar a:

```bash
...
PostgreSQL init process complete; ready for start up.

2021-07-02 17:22:23.573 UTC [1] LOG:  starting PostgreSQL 12.7 on x86_64-pc-linux-musl, compiled by gcc (Alpine 10.3.1_git20210424) 10.3.1 20210424, 64-bit
2021-07-02 17:22:23.573 UTC [1] LOG:  listening on IPv4 address "0.0.0.0", port 5432
2021-07-02 17:22:23.573 UTC [1] LOG:  listening on IPv6 address "::", port 5432
2021-07-02 17:22:23.589 UTC [1] LOG:  listening on Unix socket "/var/run/postgresql/.s.PGSQL.5432"
2021-07-02 17:22:23.631 UTC [48] LOG:  database system was shut down at 2021-07-02 17:22:23 UTC
2021-07-02 17:22:23.640 UTC [1] LOG:  database system is ready to accept connections
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
- **Martin Britez** (martin.britez@redb.ee)
- **Santiago Leiva** (santiago.leiva@redb.ee)
