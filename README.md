# DSSAndroid

Repositorio para el desarrollo de la práctica de Android de la asignatura Desarrollo de Sistemas Software de el Máster en Ingeniería Informática

# Parte del servidor

La parte del servidor es un proyecto en Java con Maven desarrollado en Eclispe Jee
Version: 2018-09 (4.9).

Se han usado las siguientes dependencias:
- jerser-server 1.9
- jersey-json 1.9
- jersey-client 1.9
- gson 2.8.0

Se ha usado como server Tomcat v9.0. Para la base de datos se ha usado MySQL con
PHPMyAdmin alojada en un servidor Apache todo ello controlado por XAMPP Control Panel
v3.2.2.

La base de datos se puede inicializar con el archivo que se encuentra en
DSSJava/DB/consorcio.sql

Para ejecutar el proyecto hay que introducirlo en Tomcat y acceder a las distintas páginas
web que se encuentran en WebContent como por ejemplo:
http://localhost:8080/DSSJava/productos.jsp

Es necesario que esté conectada la base de datos para que funcione


# PARTE ANDROID

El desarrollo de la aplicación en Android se ha realizado desde Android Studio. Para
ejecutar el proyecto, debemos extraer el archivo “.zip” que se entrega y abrirlo desde
Android Studio.

Una vez abierto simplemente tendremos que ejecutar el proyecto sobre uno de los
emuladores que podemos crear en el mismo Android Studio.
Para la conexión con el servidor REST se ha utilizado una librería de Android llamada
“Android Volley” por ello, para poder hacer uso de esta librería es necesario indicar esta
dependencia dentro del gradle, con la siguiente línea:

```implementation 'com.android.volley:volley:1.0.0'```

Además, se hace consulta la ubicación del usuario, por lo que la primera vez que iniciemos
la aplicación e intentemos abrir el mapa, se nos pedirá que autoricemos que se obtenga la
ubicación del usuario.

Para obtener dicha ubicación, ha sido necesario obtener una clave de la API de Google,
además de dar permisos a la aplicación para que tenga acceso a internet, así como a las
localizaciones, como podemos ver en las siguientes líneas del archivo AndroidManifest.xml:

    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /> 
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" /> 
    <uses-permission android:name="android.permission.INTERNET" /> 
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

Para comenzar a usar la aplicación, será necesario que dispongamos de la parte del servidor corriendo en nuestro ordenador, para ello debemos seguir los pasos de instalación que se proporcionan en dicha parte, además de tener la base de datos conectada.
Si estas primeras restricciones se cumplen, podremos comenzar a utilizar la aplicación iniciando sesión con uno de los usuarios ya existentes o bien creándonos uno nuevo desde la pantalla de registro.

