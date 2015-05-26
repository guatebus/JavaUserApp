# Dynamic Java Web Application

Live demo at http://javauserapp-lunchies52.rhcloud.com/login.html

Dynamic java web app - users can login and view their authorized pages only. Meets the requirements listed below (in the Requirements section).

Uses servlets, servlet filters, servlet sessions and jsps. All servlets configured via annotations (init params included); the AuthorizationFilter is programmatically added by the ServletListener class to allow dependency injection of the ResourceVoter.

Users are assigned roles and resources are mapped to roles - this gives users access to the resource.

## To use:

#### Install:
If installing, rebuild from source and deploy to your favorite server (maven supported). Set application's context root to **"/"** (root).

Start server and reach login at **/login.html**. 

#### Demo:
Log in with username 1, 2 or 3 (same pwd as username) - app instance at http://javauserapp-lunchies52.rhcloud.com/login.html

Users are directed to a welcome page, containing links to all resources (**href="/resource?p={id}"**):

 - User '1' has access to /resources?p=1 only (with role PAGINA_1)
 - User '2' has access to /resources?p=2 only (with role PAGINA_2)
 - User '3' has access to /resources?p=3 only (with role PAGINA_3)

##Best practices (to be included):

 - TDD should be used for development (using JUnit) - not implemented because of time constraints
 - Persistence of application data should always be done on it's own layer.
 - Password hashing should be done prior to persising (and comparing) user input.

## Pending
Tests scaffolding with JUnit - to allow TDD

## Requirements
En JAVA. Es un plus hacerlo con las extensiones funcionales.
 
Desarrollar una aplicación web en java o algún lenguaje que corra sobre la máquina virtual de java. 
 
Esta aplicación tendrá 3 páginas privadas y un formulario de login.  Para acceder a cualquiera de estas páginas privadas el usuario deberá haber iniciado sesión a traves de la página de login y ademas deberá tener el rol apropiado para poder ver la página.
 
Consideraciones:

Los roles necesarios para acceder a cada página son los siguientes:

-Pagina 1: Para acceder a ella el usuario logeado debe tener el rol PAG_1
-Pagina 2: Para acceder a ella el usuario logeado debe tener el rol PAG_2
-Pagina 3: Para acceder a ella el usuario logeado debe tener el rol PAG_3

La sesión de usuario expirará a los 5 minutos desde la última acción que realizó el usuario

Cada página simplemente deberá mostrar el nombre de la página a la que se esta accediendo y el texto Hola {NOMBRE_DE_USUARIO}. Además tendrá un botón para cerrar la sesión de usuario. No es necesario poner foco en maquetar lás páginas para que sean "bonitas"

En el caso de intentar acceder a una de estas páginas sin haber iniciado sesión la aplicación redirigirá al usuario automáticamente a la página de login

En el caso de intentar acceder a una de estas páginas con sesión iniciada pero con un usuario que no tenga el rol adecuado para acceder a la página la aplicación devolverá un código de error apropiado indicando que el acceso esta denegado.

No se podrá utilizar ningún framework tipo Spring o similar ni utilizar el servidor de aplicaciones para gestionar aspectos de seguridad.

No es necesario tener los usuario en un BBDD, es suficiente con tenerlos en memoria o en un fichero de texto/xml.
Habrá un mínimo de 3 usuarios, cada uno de ellos con un rol distinto aunque si se cree oportuno puede haber usuarios extra que tengan varios roles.

Usar reactive programing es un plus.
