-Se procede a la creacion de un nuevo proyecto usando Android Studio Koala Feature Drop version 2024.1.2 -Luego New Project , seguido procederemos a utilizar la plantilla Empty Activity.
-En nuestra siguiente ventana, en donde dice Name se procede a colocar nuestro nombre de proyecto el cual es SharedPreferences1, seguido de nuestro Package name el cual llevara como nombre es.ua.eps.sharedpreferences1,
lenguaje Kotlin, SDK API 24 ("Nougat"; Android 7.0), seguido de Finish para la creación.
-El siguiente paso seria la creación de nuestro dispositivo virtual: Create Virtual Device, seleccionamos la opcion Phone seguido de Pixel 2, API 34 y la version de Android (14).
Se debe utilizar Gradle 8.7 que es la version que da Koala por defecto.
-Se procede a la creacion de nuestra App primero usando Kotlin, la cual tendra 2 activities llamadas MainActivity.kt y DetailActivity.kt, luego de la creacion de la App, se procedera a crear 2 nuevos archivos en java llamados MainActivityjava.java , DetailActivityjava.java

1) MainActivity Permite al usuario ingresar un texto y seleccionar un tamaño de fuente mediante un SeekBar (rango de 1 a 50).
-Incluye un botón para aplicar los cambios, que guarda los valores del texto y el tamaño en SharedPreferences utilizando codificación en Base64.
-También tiene un botón para cerrar la aplicación.
-Los valores actuales (texto y tamaño) se muestran debajo del botón en la interfaz.

2) DetailActivity:
 - Muestra el texto ingresado con el tamaño de fuente especificado.
 -Incluye un botón para volver a la actividad anterior.

En resumen:La aplicación está diseñada para funcionar con SharedPreferences, permitiendo la comunicación de los valores entre las dos actividades. Los valores se guardan en Base64 dentro del archivo de preferencias para garantizar que sean manejados de manera segura.

Nota: -Recordar que la version de Kotlin tiene un botón con el logo de Java que permite cambiar a la version Java de la aplicación.
      -La version de Java tiene un botón con el logo de Kotlin que permite cambiar a la version de Kotlin de la aplicación.
      -Las preferencias son compartidas entre todas las actividades y los datos a lo largo de la aplicación.
