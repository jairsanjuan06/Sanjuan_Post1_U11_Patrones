# jairs-post1-u11

Proyecto de laboratorio para la Unidad 11: Refactorizacion Avanzada y Clean Code Profundo.

## Objetivo

Identificar y eliminar code smells de tipo Bloater en un servicio Spring Boot aplicando tecnicas de refactorizacion avanzada vistas en la Unidad 11: `Extract Method`, `Extract Class` e introduccion de `Value Objects`.

## Smells detectados en el codigo inicial

El primer commit contiene el servicio con smells intencionales solicitados en la guia:

| Smell | Ubicacion | Evidencia |
| --- | --- | --- |
| Large Class | `PedidoService` | Mezcla validacion, calculo, descuento, notificacion y persistencia. |
| Long Method | `procesarPedido` | Metodo extenso con varias responsabilidades y condicionales. |
| Primitive Obsession | Firma de `procesarPedido` | Datos de cliente y direccion representados con `String` separados. |
| Long Parameter List | Firma de `procesarPedido` | Mas de diez parametros. |
| Field Injection | `PedidoService.repo` | Uso de `@Autowired` sobre atributo. |

## Tecnicas aplicadas

| Tecnica | Archivo | Resultado |
| --- | --- | --- |
| Introduce Value Object | `DatosCliente`, `Direccion`, `LineaPedido`, `CodigoDescuento` | Se reemplazaron parametros primitivos por objetos del dominio con validacion propia. |
| Extract Method | `PedidoService` | `procesarPedido` quedo como orquestador breve; calculo, validacion, descuento y persistencia quedaron en metodos privados. |
| Extract Class | `NotificacionService` | La responsabilidad de notificar salio de `PedidoService`. |
| Constructor Injection | `PedidoService` | Se elimino la inyeccion por campo y se declararon dependencias `final`. |
| Clean Error Handling | Value Objects y servicio | Se usan excepciones descriptivas en vez de valores `null` o codigos ambiguos para entradas invalidas. |

## Comparacion de metricas

> Nota: Docker no esta instalado en este equipo, por lo que las capturas reales de SonarQube deben generarse al ejecutar el proyecto en una maquina con Docker Desktop. La siguiente tabla resume la comparacion esperada y verificable al correr SonarQube.

| Metrica | Antes de refactorizar | Despues de refactorizar | Mejora esperada |
| --- | ---: | ---: | --- |
| Complejidad ciclomatrica de `procesarPedido` | Alta por `if`, `for` y `else if` mezclados | 1 | El metodo queda como flujo lineal de orquestacion. |
| Lineas de `procesarPedido` | Mas de 30 lineas | 8 lineas de firma/cuerpo, 5 instrucciones en el cuerpo | Cumple el checkpoint del laboratorio. |
| Code Smells principales | 5 | 0 en los smells objetivo | Se eliminan `Long Method`, `Long Parameter List`, `Primitive Obsession`, `Large Class` y `Field Injection`. |
| Mantenibilidad | Menor por mezcla de responsabilidades | Mayor por cohesion y bajo acoplamiento | Cada clase tiene una responsabilidad clara. |
| Pruebas automatizadas | 2 pruebas de caracterizacion | 3 pruebas actualizadas | `mvn test` pasa correctamente. |

## Evidencia de pruebas

Comando ejecutado:

```bash
mvn test
```

Resultado local:

```text
BUILD SUCCESS
Tests run: 3, Failures: 0, Errors: 0
```

## Ejecucion de SonarQube

Iniciar SonarQube:

```bash
docker run -d --name sonarqube -p 9000:9000 sonarqube:community
```

Abrir `http://localhost:9000`, crear un token en `Account > Security` y ejecutar:

```bash
mvn verify -Pcoverage sonar:sonar ^
  -Dsonar.host.url=http://localhost:9000 ^
  -Dsonar.token=TU_TOKEN ^
  -Dsonar.projectKey=refactoring-u11
```

En Linux/macOS:

```bash
mvn verify -Pcoverage sonar:sonar \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=TU_TOKEN \
  -Dsonar.projectKey=refactoring-u11
```

Las capturas deben guardarse en:

```text
capturas/sonarqube-antes.png
capturas/sonarqube-despues.png
```

## Estructura

```text
src/
  main/java/com/jairs/u11/
    pedidos/
      CodigoDescuento.java
      DatosCliente.java
      Direccion.java
      LineaPedido.java
      NotificacionService.java
      Pedido.java
      PedidoRepository.java
      PedidoService.java
  test/java/com/jairs/u11/pedidos/
    PedidoServiceTest.java
capturas/
README.md
pom.xml
```

## Commits requeridos

| Commit | Descripcion |
| --- | --- |
| `chore: agregar codigo original con smells` | Codigo inicial con smells deliberados. |
| `refactor: aplicar value objects y servicios extraidos` | Refactorizacion principal. |
| `docs: documentar analisis final de sonar` | README, instrucciones de SonarQube y evidencias. |
