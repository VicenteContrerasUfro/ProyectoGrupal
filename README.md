# Proyecto de Gestión de Gastos Estudiantiles

Este proyecto en Java permite gestionar gastos estudiantiles, registrando montos y categorías de gastos, así como calculando y visualizando estadísticas. El programa almacena los datos en archivos CSV y proporciona opciones en un menú para interactuar con los datos.

## Clases Principales

### 1. Clase `Gasto`
- **Descripción**: Representa un gasto individual en el sistema.
- **Atributos**:
    - `monto` (double): Monto del gasto.
    - `fecha` (String): Fecha en que se realizó el gasto (en formato `DD/MM/AAAA`).
    - `categoria` (String): Categoría del gasto, como "Alimentación", "Transporte", etc.
    - `comentario` (String): Un comentario opcional para detalles adicionales sobre el gasto.
- **Encapsulamiento**: Los atributos están encapsulados y se acceden a través de métodos `get` y `set`.

### 2. Clase `GestorGastos`
- **Descripción**: Administra los gastos registrados, realizando operaciones como el registro de nuevos gastos y cálculos sobre los datos almacenados.
- **Atributos**:
    - `csvGastos` (String): Ruta del archivo CSV donde se almacenan los gastos.
    - `limiteGasto` (double): Límite de gasto que no se debe superar.
- **Métodos principales**:
    - `registrarGasto(Gasto gasto)`: Registra un nuevo gasto y lo guarda en el archivo CSV si no excede el límite de gasto.
    - `calcularMontoTotal()`: Calcula el monto total de todos los gastos registrados.
    - `calcularPorcentajePorCategoria()`: Calcula el porcentaje de cada categoría en comparación con el monto total de gastos.
    - `cargarGastosDesdeCSV()`: Carga los gastos del archivo CSV en el sistema.
    - `validarFecha(String fecha)`: Valida que la fecha esté en el formato correcto `DD/MM/AAAA`.

### 3. Clase `ControlDeGastosEstudiantil`
- **Descripción**: Clase principal que maneja la interacción con el usuario a través de un menú.
- **Métodos principales**:
    - `mostrarMenu()`: Muestra las opciones del menú al usuario.
    - `registrarGastoEstudiantil()`: Solicita los datos de un gasto al usuario y los registra en el sistema.
    - `mostrarPorcentajePorCategoria()`: Muestra el porcentaje de cada tipo de gasto en comparación con el monto total.
    - Otras opciones: Visualización de historial de gastos, total gastado, y establecimiento de un límite de gasto.

## Opciones del Menú
1. **Registrar gasto**: Permite ingresar un nuevo gasto con monto, fecha, categoría y comentario.
2. **Visualizar historial de gastos**: Muestra todos los gastos registrados.
3. **Monto total gastado**: Muestra la suma de todos los gastos registrados.
4. **Búsqueda por categoría de gasto**: Filtra y muestra gastos de una categoría específica.
5. **Búsqueda por fecha**: Filtra y muestra los gastos de una fecha específica.
6. **Establecer meta de gasto**: Define un límite máximo de gasto.
7. **Visualizar porcentaje de tipos de gastos en el monto total**: Muestra el porcentaje que representa cada tipo de gasto en comparación con el monto total.
8. **Salir**: Termina el programa.