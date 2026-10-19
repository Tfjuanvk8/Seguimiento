# Seguimiento
Seguimiento 19 de octubre:
Juan Andrés Alzate,
Juan José Rios,
Santiago Londoño
# SaborExpress — explicación del diseño y las clases

## 1. ¿Qué hace el proyecto?

SaborExpress es una aplicación base para gestionar pedidos de un restaurante. Está dividida en tres áreas de trabajo para que el equipo pueda desarrollar en paralelo:

- **`menu`**: productos, menú y descuentos.
- **`cliente`**: clientes y repartidores.
- **`pedido`**: pedidos, sus estados, notificaciones y estadísticas.

El objetivo de esta organización es separar responsabilidades: cada clase tiene una tarea clara y los cambios en una parte del sistema afectan lo menos posible a las demás.

## 2. ¿Por qué `Producto` es una clase abstracta?

`Producto` representa las características que todos los productos comparten: nombre y precio. Es `abstract` porque no queremos crear un producto genérico sin especificar qué clase de producto es. En cambio, se crean objetos concretos como `Plato`, `Bebida` o `Combo`.

Las clases `Plato`, `Bebida` y `Combo` **heredan** de `Producto` para reutilizar sus atributos, validaciones y método `toString()`, y luego agregan sus propios datos:

- `Plato`: añade la categoría del plato.
- `Bebida`: añade el tamaño en mililitros y valida que sea positivo.
- `Combo`: contiene una lista de productos y guarda una copia inmutable de esa lista.

Esto aplica **herencia** y evita repetir nombre y precio en cada clase. Aunque `Producto` es abstracta, sus subclases pueden usar su constructor mediante `super(nombre, precio)`.

## 3. Archivos de la rama `menu`

| Archivo | ¿Por qué existe? |
|---|---|
| `modelo/Producto.java` | Clase abstracta con los datos comunes de todo producto. Valida que el nombre exista y que el precio no sea negativo. |
| `modelo/Plato.java` | Especializa `Producto` con la categoría del plato. |
| `modelo/Bebida.java` | Especializa `Producto` con los mililitros. Valida que el tamaño sea mayor que cero. |
| `modelo/Combo.java` | Representa una combinación de productos. Usa `List<Producto>` para aceptar distintos tipos de producto. |
| `menu/Menu.java` | Centraliza las operaciones del menú: agregar, eliminar, listar y ordenar por precio. Usa `ArrayList` porque el menú es una colección que se recorre y puede cambiar de tamaño. |
| `promocion/EstrategiaDescuento.java` | Es una **interfaz** que define el contrato `aplicar(precio)`. No especifica cómo se calcula cada descuento; solo exige que cada estrategia implemente ese método. |
| `promocion/DescuentoPorcentaje.java` | Implementa la interfaz y calcula el precio después de aplicar un porcentaje de descuento. Valida que esté entre 0 y 100. |
| `promocion/DescuentoFijo.java` | Implementa la misma interfaz, pero resta una cantidad fija. Usa `Math.max(0, ...)` para evitar que el precio final sea negativo. |

### ¿Por qué una interfaz para los descuentos?

Porque las promociones pueden calcularse de maneras diferentes, pero todas ofrecen la misma operación. Gracias a `EstrategiaDescuento`, el código que utiliza un descuento puede trabajar con la interfaz sin depender de una clase concreta. Este es el patrón **Strategy (Estrategia)**. Para añadir una nueva regla de descuento se puede crear otra implementación, sin tener que cambiar las existentes.

## 4. Archivos de la rama `cliente`

| Archivo | ¿Por qué existe? |
|---|---|
| `cliente/Cliente.java` | Modelo de datos del cliente: id, nombre, teléfono y dirección. Los campos `final` hacen que esos datos no se puedan reasignar después de construir el objeto. |
| `cliente/Repartidor.java` | Modelo del repartidor. Conserva id y nombre, y permite actualizar si está disponible. |
| `cliente/GestorClientes.java` | Encapsula la lista de clientes y sus operaciones básicas. `buscarPorId` devuelve `Optional<Cliente>` para representar que quizá no haya coincidencia, en vez de devolver `null`. |
| `cliente/GestorRepartidores.java` | Administra repartidores y permite encontrar el primero que esté disponible. También usa `Optional` si no hay ninguno disponible. |

### ¿Por qué usar `List` y `Optional`?

- `List` permite guardar varios objetos del mismo tipo y recorrerlos.
- `ArrayList` es una implementación sencilla y flexible de `List`.
- `List.copyOf(...)` devuelve una copia no modificable de la lista. Así, quien llama a `listar()` no puede agregar o borrar elementos directamente dentro del gestor.
- `Optional` comunica que una búsqueda puede no encontrar un resultado y anima al código que llama a manejar ese caso.

**Nota de mejora:** los gestores actuales usan listas y buscan recorriendo sus elementos. Es sencillo para una primera versión, pero la búsqueda por id no es de tiempo constante. Si el requisito exige búsquedas promedio O(1), conviene almacenar los objetos en un `HashMap` indexado por id. También se debe añadir una validación para evitar documentos duplicados de clientes si el requisito del trabajo lo exige.

## 5. Archivos de la rama `pedido`

| Archivo | ¿Por qué existe? |
|---|---|
| `pedido/Pedido.java` | Representa una compra: identifica el pedido, asocia un cliente, conserva sus detalles, estado, repartidor y observadores. Calcula el total a partir de sus detalles. |
| `pedido/DetallePedido.java` | Representa una línea del pedido: producto y cantidad. Calcula el subtotal multiplicando precio por cantidad. Separar el detalle permite que un pedido tenga muchos productos y cantidades diferentes. |
| `pedido/EstadoPedido.java` | Es un `enum`, es decir, un conjunto cerrado de estados permitidos: creado, en preparación, listo, en camino, entregado o cancelado. Evita escribir estados arbitrarios como texto. |
| `pedido/GestorPedidos.java` | Encapsula la colección de pedidos y permite agregar, listar, buscar por id y ordenar por total. |
| `servicio/EstadisticasPedidos.java` | Contiene consultas agregadas sobre pedidos, como sumar ventas y contar los pedidos entregados. Usa Streams para expresar operaciones sobre colecciones. |
| `observador/ObservadorPedido.java` | Interfaz que define el método de notificación que deben implementar los observadores de un pedido. |
| `observador/ObservadorCliente.java` | Implementa el observador y simula un aviso al cliente cuando cambia el estado. |
| `observador/ObservadorCocina.java` | Implementa el observador y simula un aviso a cocina. |
| `observador/ObservadorRepartidor.java` | Implementa el observador y simula un aviso al repartidor. |
| `Main.java` | Punto de entrada del programa. Crea objetos de ejemplo, registra productos en un pedido, muestra el total y cambia el estado para demostrar las notificaciones. |

### ¿Por qué `EstadoPedido` es un `enum`?

Los estados son valores conocidos y limitados. Un `enum` evita errores de escritura y deja claro cuáles son los estados válidos del pedido.

### ¿Por qué `ObservadorPedido` es una interfaz?

El pedido no necesita conocer las clases concretas que reciben las notificaciones. Solo necesita llamar al método declarado en `ObservadorPedido`. Por eso se pueden agregar observadores nuevos —por ejemplo, una pantalla de seguimiento— sin cambiar la lista de tipos que `Pedido` conoce. Este diseño aplica el patrón **Observer (Observador)**.

### ¿Por qué separar `Pedido` de `DetallePedido`?

Un pedido puede tener varios productos, y cada uno puede tener una cantidad distinta. `DetallePedido` guarda esa relación y calcula su subtotal. `Pedido` reúne los detalles y suma sus subtotales para obtener el total. Así cada clase tiene una responsabilidad más específica.

### ¿Por qué usar Streams en estadísticas y ordenamiento?

Las operaciones `stream()`, `filter()`, `mapToDouble()`, `sum()` y `sorted()` permiten filtrar, transformar, sumar u ordenar elementos sin escribir manualmente todos los bucles. Esto es un ejemplo de **programación funcional** en Java.

## 6. Conceptos de programación orientada a objetos usados

- **Encapsulamiento:** los atributos se mantienen `private` y se accede a ellos mediante métodos públicos.
- **Herencia:** `Plato`, `Bebida` y `Combo` heredan de `Producto`.
- **Abstracción:** `Producto` representa lo común entre tipos concretos de productos.
- **Polimorfismo:** los diferentes productos pueden manejarse a través del tipo común `Producto`; las diferentes estrategias de descuento pueden manejarse mediante `EstrategiaDescuento`.
- **Interfaces:** `EstrategiaDescuento` y `ObservadorPedido` definen contratos para distintas implementaciones.
- **Colecciones:** `ArrayList` y `List` permiten administrar grupos de productos, clientes, repartidores, pedidos y detalles.
- **`Optional`:** representa resultados de búsqueda que pueden estar ausentes.
- **`enum`:** define los estados válidos del pedido.
- **Streams y `Comparator`:** permiten ordenar, filtrar y resumir información.
- **Separación de responsabilidades:** cada clase se ocupa de un aspecto concreto del sistema.

## 7. Cómo abrirlo en IntelliJ IDEA

1. Descomprime el archivo ZIP.
2. Abre la carpeta `SaborExpress` en IntelliJ IDEA.
3. Configura un JDK compatible (se recomienda JDK 17 o superior).
4. Marca las carpetas que contienen el código como **Sources Root** según la estructura de paquetes.
5. Revisa que IntelliJ pueda resolver los imports entre `cliente`, `modelo`, `menu`, `pedido`, `observador`, `promocion` y `servicio`.
6. Ejecuta `Main.java`.

Como los archivos están agrupados por las tres ramas, para compilar el programa integrado las tres partes deben estar presentes en el mismo proyecto y configuradas correctamente.

## 8. División sugerida para el trabajo en GitHub

- **Rama `menu`:** clases de productos, menú y estrategias de descuento.
- **Rama `cliente`:** cliente, repartidor y gestores de ambos.
- **Rama `pedido`:** pedido, detalles, estados, observadores, estadísticas y `Main.java`.

Antes de fusionar las ramas, acuerden las firmas de los constructores y métodos públicos, y comprueben juntos que el proyecto compile. Cada integrante debe realizar commits de su propio trabajo y luego abrir un Pull Request.

## 9. Limitaciones conocidas de esta versión

Esta versión es una base educativa. Aún se puede mejorar para cubrir todos los requisitos del trabajo:

1. `GestorClientes`, `GestorRepartidores` y `GestorPedidos` almacenan en listas; si se necesita búsqueda promedio O(1), deben migrarse a `HashMap`.
2. `GestorClientes` todavía no evita documentos duplicados; el modelo actual tampoco incluye el documento como atributo.
3. Los descuentos están implementados, pero todavía no están conectados al cálculo de `Pedido`. Para que el total use una promoción, hay que integrar una estrategia de descuento en el pedido o en un servicio de cálculo.
4. Las estadísticas actuales suman ventas y cuentan pedidos entregados; se pueden ampliar para obtener los tres platos más vendidos, ingresos por sucursal y el pedido más costoso por cliente.
5. Los métodos de construcción podrían validar explícitamente argumentos `null` y entradas inválidas en todos los modelos.

Estas mejoras deben añadirse de forma coordinada para que las tres ramas mantengan una interfaz compatible.
