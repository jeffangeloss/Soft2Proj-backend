# Flowbuilder Backend

Este es el núcleo del proyecto **Workflow Builder**, responsable de la lógica de negocio, la validación de estructuras y la ejecución técnica de los flujos de trabajo. El sistema actúa como un motor intermedio que procesa datos en formato JSON y se comunica bidireccionalmente con un frontend desarrollado en React.js.

## Capacidades del Sistema

El backend ha sido diseñado para ofrecer robustez y escalabilidad, soportando las siguientes funciones críticas:

* **Procesamiento de Datos:** Recepción y parseo de workflows complejos en formato JSON.
* **Motor de Ejecución:** Orquestación de tareas según la lógica definida en el flujo.
* **Validación de Flujos:** Verificación de integridad para asegurar que no existan errores lógicos antes de la ejecución.
* **Interfaz API:** Comunicación estandarizada mediante una API REST para integración con el frontend.

## Stack Tecnológico

* **Lenguaje:** Java 17
* **Framework:** Spring Boot
* **Gestor de dependencias:** Maven
* **Arquitectura:** API REST

---

## Requisitos Previos

Antes de comenzar, asegúrate de tener instaladas las siguientes herramientas:

1.  **Java JDK 17** o superior: [Descargar desde Adoptium](https://adoptium.net/)
2.  **IntelliJ IDEA**: [Descargar IDE](https://www.jetbrains.com/idea/)
3.  **GitHub Desktop**: [Descargar aquí](https://desktop.github.com/)

---

## Configuración e Instalación

### 1. Obtención del Proyecto
1.  Abre **GitHub Desktop**.
2.  Inicia sesión con tu cuenta de GitHub.
3.  Selecciona la opción para **Clonar el repositorio** del backend.
4.  Elige la carpeta local de tu preferencia para guardar los archivos.

### 2. Preparación en el IDE
1.  Abre **IntelliJ IDEA**.
2.  Selecciona la opción **Open** y navega hasta la carpeta donde clonaste el proyecto.
3.  Al abrirlo, espera a que **Maven** descargue automáticamente todas las dependencias necesarias (esto puede tardar unos minutos dependiendo de tu conexión).

### 3. Ejecución
1.  Localiza la clase principal del proyecto dentro de la estructura de paquetes.
2.  Ejecuta la aplicación (Run).
3.  Confirma que el servidor está activo verificando que el log indique el inicio en el puerto **8080**.
4. Si se va a probar el backend y frontend en distintas PCs, se debe configurar la ip en la clase controller
5. Si se va a probar el backend y frontend en la misma PC, solo va a escuchar en localhost:8080
6. En ambos escenarios la direccion url tendrá la siguiente forma "http://ipAddress:8080/api/workflows/run", cambiar ipAdress por "localhost" o por la ip configurada

---

## Estructura de Datos (Workflow)

El motor procesa la información bajo el siguiente esquema lógico:

* **Cabecera:** Datos de identificación del flow.
* **Nodos:** Representación de cada paso o tarea dentro del proceso.
* **Conexiones:** Definición de la secuencia y dependencias entre tareas.
* **Reglas:** Validaciones que determinan el éxito o fallo de la ejecución.

---
