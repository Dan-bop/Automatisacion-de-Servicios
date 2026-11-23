[README.md](https://github.com/user-attachments/files/35262960/README.md)
# Proyecto Automatizscion de APIS

Automatización de pruebas de API (GET, POST, PUT y DELETE, más un flujo de autenticación con token) sobre [dummyjson.com](https://dummyjson.com), usando **Serenity BDD**, **Cucumber** y **RestAssured** con el patrón **Screenplay**.

**Autor:** Danner Grosvi Loyola Blas

## Requisitos

| Herramienta | Versión |
|---|---|
| Java (JDK) | 17 (recomendado) |
| Maven | 3.9 o superior |
| Conexión a internet | Necesaria, la API es pública |

Verifica tu entorno:

```bash
java -version
mvn -version
```

> `mvn -version` debe mostrar el mismo JDK que piensas usar. Si Maven usa Java 21, el `pom.xml` ya incluye el `argLine` con `--add-opens` que Serenity 3.1.20 necesita; aun así, Java 17 es la opción más segura.

## Cómo ejecutar

Desde la raíz del proyecto:

```bash
mvn clean verify
```

Este comando compila, ejecuta todos los escenarios y genera el reporte de Serenity al final.

Para regenerar solo el reporte, sin volver a ejecutar las pruebas (y **sin** `clean`, porque borraría los resultados):

```bash
mvn serenity:aggregate
```

## Reporte

El reporte queda en:

```
target/site/serenity/index.html
```

Ábrelo en el navegador. Desde PowerShell:

```powershell
start target\site\serenity\index.html
```

El reporte muestra el resumen de escenarios aprobados y fallidos y, dentro de cada uno, el request y el response de la API.

## Endpoints cubiertos

| Servicio | Verbo | Endpoint | Resultado esperado |
|---|---|---|---|
| Productos | GET | `/products/{id}` | 200 y datos del producto |
| Productos | POST | `/products/add` | 201 y producto creado |
| Productos | PUT | `/products/{id}` | 200 y título actualizado |
| Productos | DELETE | `/products/{id}` | 200 e `isDeleted = true` |
| Autenticación | POST | `/auth/login` | 200 y `accessToken` |
| Autenticación | GET | `/auth/me` | 200 y `username` (con Bearer token) |

> dummyjson **simula** las escrituras (POST, PUT, DELETE): responde como si se hubieran hecho, pero no guarda cambios. Por eso las pruebas se pueden repetir sin ensuciar datos.

## Escenarios

| # | Feature | Escenario | Tipo |
|---|---|---|---|
| 1 | productos | Consultar un producto existente | Positivo |
| 2 | productos | Crear un producto nuevo | Positivo |
| 3 | productos | Actualizar un producto existente | Positivo |
| 4 | productos | Eliminar un producto existente | Positivo |
| 5 | productos | Consultar un producto que no existe (404) | Negativo |
| 6 | autenticacion | Login exitoso devuelve un token | Positivo |
| 7 | autenticacion | Consultar el perfil con el token obtenido | Positivo |
| 8 | autenticacion | Login con contraseña incorrecta es rechazado (400) | Negativo |

## Estructura del proyecto

```
CapacitacionApiAuto/
├── pom.xml
├── README.md
└── src/
    ├── main/java/org/example/
    │   ├── apis/            # Rutas de los endpoints
    │   ├── models/          # Cuerpos de las peticiones (request)
    │   ├── task/            # Acciones HTTP del actor (Screenplay)
    │   └── questions/       # Lectura de datos de la respuesta
    └── test/
        ├── java/
        │   ├── runners/         # RunnerTest (arranque de Cucumber)
        │   └── stepdefinitions/ # Hooks, CommonSteps, ProductSteps, AuthSteps
        └── resources/
            ├── features/        # Escenarios en Gherkin
            ├── data/            # Datos de prueba
            └── serenity.conf    # Configuración (URL base)
```

## Configuración

La URL base de la API vive en `src/test/resources/serenity.conf`, no en el código:

```hocon
environments {
  default {
    restapi.baseurl = "https://dummyjson.com"
  }
}
```

Para apuntar a otro ambiente basta con cambiar ese valor.

## Cómo funciona (resumen)

1. `RunnerTest` arranca Cucumber con Serenity y busca los `.feature`.
2. `Hooks` prepara al actor antes de cada escenario con la URL base de `serenity.conf`.
3. Cada línea del feature se conecta con un step (`ProductSteps`, `AuthSteps` o `CommonSteps`).
4. El step le pide al actor una **Task** (acción HTTP) y valida con una **Question** (dato de la respuesta).
5. Serenity reúne los resultados y genera el reporte.

**Decisión de diseño:** las acciones HTTP están separadas en Tasks, las rutas en clases de `apis` y la URL base en `serenity.conf`. Los steps solo orquestan. Así, si cambia un endpoint o el ambiente, se modifica un solo archivo y las Tasks se reutilizan entre escenarios.

## Escenario de fallo intencional (solo para evidencia)

Para demostrar cómo reporta Serenity un error, se puede agregar temporalmente un escenario que valida un dato incorrecto (por ejemplo, un título que no corresponde al producto 1). Ese escenario aparece en rojo en el reporte y la consola termina en `BUILD FAILURE`, pero el reporte se genera igual. **Debe eliminarse o comentarse antes de la entrega final** para que `mvn clean verify` termine en `BUILD SUCCESS`.

## Solución de problemas

| Síntoma | Causa probable y solución |
|---|---|
| El reporte sale vacío (`0 tests`) | La clase runner no termina en `Test`, o se ejecutó `mvn clean` seguido solo de `serenity:aggregate`. Usa `mvn clean verify` |
| `module java.base does not "opens java.lang"` | Maven corre con Java 21. Usa Java 17 o conserva el `argLine` del `pom.xml` |
| `UndefinedEnvironmentVariableException: restapi.baseurl` | Falta `serenity.conf` en `src/test/resources` o no contiene `restapi.baseurl` |
| `Undefined step` | La frase del feature no coincide con la anotación del step (comillas, tildes, espacios) |
| `404 Not Found` en productos | La ruta debe ser `/products` (en inglés), no `/productos` |
| `No enum constant ... TakeScreenshots.NEVER` | En Serenity 3.1.20 el valor válido es `DISABLED` |
