# Simple Java User Web Application
Dynamic java web app - users can login and view authorized pages. Meets the requirements listed below (Requirements section)

Uses servlets, servlet filters, servlet sessions and jsps.

Users are assigned roles and resources are mapped to these roles - this gives users view access to the resource.

Welcome page at /login.html, login with username 1, 2 or 3 (same pwd as username).
Users are directed to a successful login page, they can then access their resource at /resource?p={id}
User has access to resource with same id as their username. This is done via userId-userRole and resourceId-userRole relationship (as would work in a real persistence layer)

Best practices:
Persistence of application data should always be done on it's own layer.

## Pending
Automated build is in the process (branch maven)

## Requirements
En JAVA. Es un plus hacerlo con las extensiones funcionales.
 
Desarrollar una aplicación web en java o algún lenguaje que corra sobre la máquina virtual de java. 
 
Esta aplicación tendrá 3 páginas privadas y un formulario de login.  Para acceder a cualquiera de estas páginas privadas el usuario deberá haber iniciado sesión a traves de la página de login y ademas deberá tener el rol apropiado para poder ver la página.
 
Consideraciones:
Los roles necesarios para acceder a cada página son los siguientes:
            Pagina 1: Para acceder a ella el usuario logeado debe tener el rol PAG_1
            Pagina 2: Para acceder a ella el usuario logeado debe tener el rol PAG_2
            Pagina 3: Para acceder a ella el usuario logeado debe tener el rol PAG_3
La sesión de usuario expirará a los 5 minutos desde la última acción que realizó el usuario
Cada página simplemente deberá mostrar el nombre de la página a la que se esta accediendo y el texto Hola {NOMBRE_DE_USUARIO}. Además tendrá un botón para cerrar la sesión de usuario. No es necesario poner foco en maquetar lás páginas para que sean "bonitas"
En el caso de intentar acceder a una de estas páginas sin haber iniciado sesión la aplicación redirigirá al usuario automáticamente a la página de login
En el caso de intentar acceder a una de estas páginas con sesión iniciada pero con un usuario que no tenga el rol adecuado para acceder a la página la aplicación devolverá un código de error apropiado indicando que el acceso esta denegado.
No se podrá utilizar ningún framework tipo Spring o similar ni utilizar el servidor de aplicaciones para gestionar aspectos de seguridad.
No es necesario tener los usuario en un BBDD, es suficiente con tenerlos en memoria o en un fichero de texto/xml.
Habrá un mínimo de 3 usuarios, cada uno de ellos con un rol distinto aunque si se cree oportuno puede haber usuarios extra que tengan varios roles.
Usar reactive programing es un plus.