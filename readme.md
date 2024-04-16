# GameStore v1.0

En este repositorio se almacenará nuestro proyecto de la Tienda de juegos.

## Configuración del entorno

A continuación se presentan las instrucciones para instalar y configurar el entorno de desarrollo para programar en Java utilizando VSCode en Windows.

### Requisitos

- Instalar [Visual Studio Code](https://code.visualstudio.com/).
- Instalar **OpenJDK 21** desde [este link](https://adoptium.net/es/) o de [este otro](https://learn.microsoft.com/es-mx/java/openjdk/download#openjdk-21).
- Instalar [Git](https://git-scm.com/download/win).
- Crear una cuenta en [GitHub](https://github.com/signup?ref_cta=Sign+up&ref_loc=header+logged+out&ref_page=%2F&source=header-home).

### Configuración

1. Buscar e instalar la extensión **Extension Pack for Java** en Visual Studio.
2. Clonar este repositorio localmente.
3. Configurar correo electrónico y nombre de usuario en Git usando una ventana de consola (CMD) para poder realizar cambios en el código.
    - Correo: ```git config --global user.email "you@example.com"```
    - Usuario: ```git config --global user.name "Your name"```

## Manejo de código

Se podrán realizar cambios en el código fuente del proyecto una vez que se tenga el repositorio inicializado en el equipo. Cuando se realice una modificación dentro de un archivo, este se coloreara de amarillo en la vista del explorador de archivos de Visual Studio.

Para aplicar los cambios realizados se deberán seguir los siguientes pasos:

1. Dar clic en el icono de Control de código fuente.
2. Pasar el cursor encima de la sección de cambios y dar clic en el icono de suma que aparecerá del lado derecho. Al hacer esto, la sección de cambios indicará *“staged”*.
3. Escribir la descripción de los cambios realizados. Procurar que sea breve y conscisa.
4. Dar clic en el botón **Confirmación** (o *Commit*).
5. Dar clic en el botón **Sincronizar cambios**.

![Img 1](/images/Captura%20de%20pantalla_20240317_011445.png "Pasos 1 - 4")

![Img 2](/images/Captura%20de%20pantalla_20240317_012013.png "Paso 5")

# Estructura

El programa se encuentra estructurado por pantallas (Screen). Cada Screen 
representa un apartado, pudiendo ser la pantalla principal, el catálogo de 
juegos, ajustes de cuenta, etc.

Todos los apartados se encuentran dentro de la carpeta ```screen```.

Lista de apartados:

- ```MainScreen``` - Menú o pantalla principal que se muestra al iniciar el 
programa.
- ```LoginScreen``` - Apartado de inicio de sesión.
- ```SignUpScreen``` - Apartado de registro de nuevos clientes.
- ```AccountSettingsScreen``` - Apartado de ajustes de cuenta.
- ```GameLibrary``` - Apartado biblioteca de juegos donde el usuario podrá explorar su lista de juegos comprados, wishlist y el carrito de compras.
- ```GameCatalogScreen``` - Catálogo de juegos donde el usuario podrá explorar y ver los detalles de cada uno de ellos.
- ```PurchaseScreen``` - Apartado de compras donde el usuario podrá efectuar la adquisición de los juegos que están en su carrito de compras.
- ```AddFundsScreen``` - Pantalla para agregar fondos a la cuenta del usuario.

Cada apartado se encuentra definido dentro de una clase separada. Todas ellas contienen una función llamada ```show```, en la cual se lleva a cabo toda la lógica del apartado. 

Asimismo, la función ```show``` recibe un argumento llamado ``appContext``, el cual es un objeto que alberga todos los componentes que se comparten en todo el programa, tales como el objeto ```Scanner```, el objeto de la base de datos, etc.

## Objeto ``appContext``

Funciones:
- ```initialize()``` - Inicializa el objeto. Solo puede ser llamada una vez.
- ```exit()``` - Finaliza el programa.
- ```goToScreen([Screen])``` - Navega hacia la pantalla especificada.
- ```goToPreviousScreen()```- Navega hacia la pantalla anterior.
- ```getScanner()``` - Obtiene la instancia de ```Scanner``` para todo el programa.
- ```getDbContext()```- Obtiene la instancia de la conexión de la base de datos.
- ```getCurrentCustomer()``` - Obtiene el objeto ```Customer```asociado al cliente que ha iniciado sesión. Devuelve ```null``` si no se ha iniciado sesión.
- ```isLoggedIn()``` - Devuelve un indicador booleano que específica si se ha iniciado sesión.
- ```login()``` - Inicia sesión
- ```signup()``` - Registra a un nuevo cliente.
- ```deleteCustomerAccount()``` - Borra la cuenta del cliente actual.
- ```createMenu()``` - Crea un objeto para mostrar un menú.

## Objeto ``dbContext``

Funciones:
- ```getCustomers()``` - Obtiene un arreglo con la lista de todos los clientes registrados.
- ```getGameCategories()``` - Obtiene un arreglo que contiene todas las categorías de juegos disponibles.
- ```getGames()``` - Obtiene un arreglo que contiene todos los juegos registrados en la base de datos.
- ```getGames([Category])``` - Obtiene un arreglo que contiene todos los juegos que corresponen a una categoría.
- ```createCustomer()``` - Registra a un cliente nuevo.
- ```updateCustomer()``` - Actualiza la información de un cliente.
- ```deleteCustomer()``` - Elimina a un cliente registrado.
