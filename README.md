# Archivo
ORGANIZACIÓN EN ARCHIVOS
Considere una aplicación que requiere almacenar registros de depósitos. Cada registro tiene los siguientes campos: SUCURSAL (cadena de 20 bytes), NUMERO (entero de 4 bytes), NOMBRE (cadena de 20 bytes), SALDO (real de 6 bytes). Para administrar los registros de esta aplicación, se pondrá a su disposición un programa Java que implementa el siguiente esquema conceptual:
 

Usted puede agregar a este modelo un byte adicional para marcar los registros borrados y las funciones necesarias para:
1.	Implementar un programa que permita crear, eliminar y recuperar registros de tamaño fijo. Utilice al menos dos técnicas diferentes para su implementación y considere la definición de un conjunto de pruebas significativas para su demostración.
2.	Implementar un programa que permita crear, eliminar y recuperar registros de tamaño variable. Para ello es necesario utilizar una de las siguientes técnicas:
a.	Cadenas de bytes.
b.	Espacio reservado.
3.	Implementar un programa que permita crear, eliminar y recuperar registros usando las técnicas anteriores usando archivos ordenados: Una técnica con registros de tamaño fijo y una técnica con registros de longitud variable. Para cada caso considere la definición de un conjunto de pruebas significativas para su demostración.
