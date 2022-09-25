# COCupon

1. [Introducción](#introducción)
2. [Prerequisitos](#prerequisitos)
3. [Getting started](#getting-started)
4. [Guia de desarrollo](#guia-de-desarrollo)

## Introducción

Este servicio permite encontrar la lista de items favoritos mas costosa que se puede comprar con un cupón sin superar su valor

## Prerequisitos

Por favor tener el siguiente software instalado y configurado en su maquina de desarrollo local antes de comenzar con cualquier desarrollo:

- Java 16
- Scala 2.13.x (solo para los performance tests con gatling)
- Maven

## Getting started

1. Ejecutar la aplicacion en local, basta con ejecutar la clase main que se encuentra en: `src/main/java/com/meli/Application.java`

2. Construir la applicaion

    > mvn clean package

3. Iniciar la aplicacion usando docker. Para iniciar el contenedor definido en la raiz del proyecto `.` se puede realizar con el siguiente comando: Esto va ha levantar una nueva instancia de la aplicacion y la va ha exponer en el puerto 9000.

    > docker-compose up

4. El microservicio se encuentra actualmente desplegado en GCP y es accesible a traves del link `https://cupon-xgnfdevtea-uc.a.run.app`, si se desea saber si esta corriendo se puede realizar a traves del metodo get up o siguiendo la url: `https://cupon-xgnfdevtea-uc.a.run.app/up`


## Guia de desarrollo

1. Por favor tener en cuenta el siguiente articulo al realizar cambios de diseno sobre el proyecto, de modo que se sigan los principios de la arquitectura hexagonal: https://beyondxscratch.com/2017/08/19/hexagonal-architecture-the-practical-guide-for-a-clean-architecture/ 

