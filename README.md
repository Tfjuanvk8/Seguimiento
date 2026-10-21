# Seguimiento
Seguimiento 19 de octubre:
Juan Andrés Alzate,
Juan José Rios,
Santiago Londoño
# SaborExpress — Explicación del proyecto

## 1. ¿Qué hace el proyecto?
SaborExpress es una aplicación desarrollada en Java para gestionar los pedidos de un restaurante. El proyecto está dividido en tres partes para que cada integrante del equipo pueda trabajar en una rama diferente:

* **menu:** productos, menú y descuentos.
* **cliente:** clientes y repartidores.
* **pedido:** pedidos, estados, notificaciones y estadísticas.

Esta división permite organizar mejor el código y que cada clase tenga una responsabilidad específica.

## 2. ¿Por qué Producto es una clase abstracta?

Producto es una clase abstracta porque contiene los atributos que comparten todos los productos, como el nombre y el precio, pero no necesitamos crear objetos de tipo Producto directamente, es la base de donde parte cada producto.

En su lugar, creamos objetos de las clases que heredan de ella y la implementan:

* **Plato:** tiene una categoría.
* **Bebida:** tiene un tamaño en mililitros.
* **Combo:** contiene una lista de productos.

Estas clases utilizan super(nombre, precio) para llamar al constructor de Producto y reutilizar sus atributos y validaciones.

Esto nos permite aplicar herencia y evitar repetir código.

## 3. Archivos de la rama menu

* **Producto.java:** clase abstracta que contiene el nombre y el precio de los productos. Valida que el nombre no esté vacío y que el precio no sea negativo.

* **Plato.java:** hereda de Producto y agrega la categoría del plato.

* **Bebida.java:** hereda de Producto y agrega los mililitros de la bebida. También valida que el tamaño sea mayor que cero.

* **Combo.java:** representa un conjunto de productos. Utiliza List<Producto> para poder almacenar diferentes tipos de productos y guarda una copia que no se puede modificar directamente.

* **Menu.java:** administra los productos del restaurante. Permite agregarlos, eliminarlos, listarlos y ordenarlos por precio. Utiliza ArrayList porque necesitamos almacenar una cantidad variable de productos.

* **EstrategiaDescuento.java:** interfaz que define el método aplicar(precio), que deben implementar los diferentes descuentos.

* **DescuentoPorcentaje.java:** implementa EstrategiaDescuento y calcula el precio después de aplicar un porcentaje. Valida que esté entre 0 y 100.

* **DescuentoFijo.java:** también implementa EstrategiaDescuento, pero resta una cantidad fija. Utiliza Math.max(0, ...) para evitar que el precio final sea negativo.

### ¿Por qué usamos una interfaz para los descuentos?

Porque existen diferentes formas de calcular un descuento, pero todas realizan la misma operación.

Con la interfaz EstrategiaDescuento podemos crear distintos tipos de descuentos sin tener que modificar las clases existentes.

Esto corresponde al patrón de diseño **Strategy**, donde podemos cambiar la forma de realizar una operación utilizando diferentes implementaciones.

## 4. Archivos de la rama cliente

* **Cliente.java:** contiene los datos del cliente, como ID, nombre, teléfono y dirección. Los atributos son final para evitar que se reasignen después de crear el objeto.

* **Repartidor.java:** contiene el ID, nombre y disponibilidad del repartidor. La disponibilidad puede actualizarse dependiendo de si está ocupado o libre.

* **GestorClientes.java:** administra la lista de clientes y permite buscarlos por ID. Utiliza Optional<Cliente> porque puede ocurrir que el cliente buscado no exista.

* **GestorRepartidores.java:** administra los repartidores y permite buscar el primero que esté disponible. También utiliza Optional cuando no encuentra ninguno.

### ¿Por qué utilizamos List y Optional?

* List permite almacenar varios objetos y recorrerlos.
* ArrayList permite agregar o eliminar elementos fácilmente.
* List.copyOf() devuelve una copia de la lista que no se puede modificar directamente.
* Optional permite manejar búsquedas en las que puede no encontrarse un resultado, evitando depender de null.

Actualmente los gestores buscan los elementos recorriendo las listas. Esto funciona para esta primera versión, pero si necesitamos búsquedas promedio O(1), podemos utilizar un HashMap con el ID como clave.

## 5. Archivos de la rama pedido

* **Pedido.java:** representa un pedido realizado por un cliente. Guarda su identificador, cliente, detalles, estado, repartidor y observadores. También calcula el total sumando los subtotales de los productos.

* **DetallePedido.java:** representa cada producto que hace parte de un pedido junto con su cantidad. Calcula el subtotal multiplicando el precio por la cantidad.

* **EstadoPedido.java:** es un enum que contiene los estados permitidos de un pedido: CREADO, EN_PREPARACION, LISTO, EN_CAMINO, ENTREGADO y CANCELADO.

* **GestorPedidos.java:** administra los pedidos. Permite agregarlos, listarlos, buscarlos por ID y ordenarlos según su total.

* **EstadisticasPedidos.java:** realiza cálculos sobre los pedidos, como sumar las ventas y contar cuántos pedidos han sido entregados. Utiliza Streams de Java.

* **ObservadorPedido.java:** interfaz que define el método que deben implementar las clases que reciben notificaciones.

* **ObservadorCliente.java:** implementa ObservadorPedido y muestra una notificación dirigida al cliente cuando cambia el estado de un pedido.

* **ObservadorCocina.java:** implementa ObservadorPedido y muestra una notificación para cocina.

* **ObservadorRepartidor.java:** implementa ObservadorPedido y muestra una notificación para el repartidor.

* **Main.java:** es el punto de entrada del programa. Se utiliza para crear objetos de ejemplo, agregar productos a un pedido, calcular su total y cambiar su estado para comprobar las notificaciones.

### ¿Por qué EstadoPedido es un enum?

Porque un pedido solamente puede tener ciertos estados definidos.

Utilizar un enum evita tener que escribir los estados como cadenas de texto y reduce los errores al asignarlos.

### ¿Por qué utilizamos ObservadorPedido?

Porque necesitamos que diferentes partes del sistema reciban notificaciones cuando cambia el estado de un pedido.

En lugar de hacer que Pedido dependa directamente de las clases Cliente, Cocina o Repartidor para notificar, utilizamos una interfaz común.

Esto corresponde al patrón de diseño **Observer**, que permite registrar observadores y notificarlos cuando ocurre un cambio.

También facilita agregar otros observadores sin modificar toda la lógica del pedido.

### ¿Por qué separamos Pedido y DetallePedido?

Porque un pedido puede contener varios productos y cada producto puede tener una cantidad diferente.

DetallePedido se encarga de guardar el producto, su cantidad y calcular su subtotal.

Pedido se encarga de reunir esos detalles y calcular el total.

Así evitamos que una sola clase tenga demasiadas responsabilidades.

### ¿Por qué utilizamos Streams?

Porque nos permiten trabajar con las colecciones de una forma más sencilla.

Utilizamos operaciones como:

* stream() para trabajar con los elementos de una colección.
* filter() para filtrar elementos.
* mapToDouble() para convertir valores a números decimales.
* sum() para sumar valores.
* sorted() para ordenar elementos.

Estas operaciones permiten realizar cálculos y ordenamientos sin escribir todos los ciclos manualmente.

## 6. Conceptos que vimos en POO utilizados

* **Encapsulamiento:** los atributos de las clases son privados y se accede a ellos mediante métodos públicos.

* **Herencia:** Plato, Bebida y Combo heredan de Producto para reutilizar sus atributos y métodos.

* **Abstracción:** Producto es una clase abstracta que representa las características comunes de los productos.

* **Polimorfismo:** podemos trabajar con objetos de Plato, Bebida y Combo utilizando el tipo Producto. También ocurre con las diferentes implementaciones de las interfaces.

* **Interfaces:** EstrategiaDescuento y ObservadorPedido definen métodos que otras clases deben implementar.

* **Colecciones:** utilizamos List y ArrayList para almacenar productos, clientes, repartidores, pedidos y detalles.

* **Optional:** permite representar resultados que pueden no existir.

* **Enum:** define los posibles estados de un pedido.

* **Streams y Comparator:** permiten realizar cálculos, filtros y ordenamientos sobre las colecciones.

* **Separación de responsabilidades:** cada clase se encarga de una parte específica del funcionamiento del programa.

## 7. División del trabajo en GitHub

El proyecto se dividió en tres ramas:

* **Rama menu:** contiene las clases de productos, menú y estrategias de descuento.

* **Rama cliente:** contiene las clases Cliente, Repartidor y los gestores correspondientes.

* **Rama pedido:** contiene las clases relacionadas con los pedidos, detalles, estados, observadores, estadísticas y Main.

Cada integrante trabaja en su rama y realiza sus propios commits.

Cuando terminemos las partes individuales, se integran mediante Pull Requests y verificamos que las clases sean compatibles y que el proyecto compile correctamente.

## 8. Limitaciones y mejoras pendientes

Aunque el proyecto tiene una estructura funcional, todavía hay algunas cosas que podemos mejorar:

* **Búsquedas por ID:** actualmente los gestores utilizan listas. Si necesitamos búsquedas promedio O(1), podemos reemplazarlas por HashMap.

* **Clientes duplicados:** todavía falta agregar el documento de identificación y validar que no se registren clientes con el mismo documento, solo que no notamos excepciones.

* **Descuentos:** las estrategias ya están implementadas, pero todavía falta integrarlas al cálculo del total de los pedidos.

* **Estadísticas:** actualmente se calculan las ventas totales y la cantidad de pedidos entregados. Se pueden agregar estadísticas como los tres platos más vendidos, ingresos por sucursal y el pedido más costoso de cada cliente.

* **Validaciones:** se pueden mejorar las comprobaciones de valores null y otros datos inválidos en las clases.

Este es todo el trabajo, gracias por el tiempo extra y feliz día profe.
