import java.util.Scanner;
import java.io.*;

//Hola
public class Disco{
   	public static void main(String[] Joseses){
		Scanner sc = new Scanner(System.in);
		int option;
		try { //Previene cuando el usuario escribe cualquier cosa menos un número.
			do{
				showMenu();
				option = sc.nextInt();
				setMenu(option);
			}while(option != 7);
		} catch (Exception e) {
			System.out.println("Caracter inválido");
			e.printStackTrace();
		}
	}
    
	private static void showMenu(){
		System.out.print("Con el numero correspondiente, elija una opcion del menu: "+ "\n" +
							"1) Crear un Registro"+ "\n"+
							"2) Eliminar un Registro"+"\n"+
							"3) Imprimir Todos los Registros"+"\n"+
							"4) Agregar una cuenta"+"\n"+
							"5) Borrar una cuenta"+"\n"+
							"6) Buscar un Registro"+"\n"+
							"7) Salir del programa"+"\n"+
							"====== Opción: ");
	}
    
	public static void setMenu(int option){
		try {
            
			File file = new File( "Depositos.Info" );
			RandomAccessFile raf = new RandomAccessFile( file, "rw" );
			Archivo archivo = new Archivo( raf );
            
			Registro_Cbytes registro;
			
			Scanner sc = new Scanner(System.in);
			if(option == 1){
				System.out.println("--------------------------------------------------------------");
				System.out.print("Introduzca el nombre de la sucursal: ");
				String suc = sc.nextLine();
				//~ System.out.print("Introduzca el número de cuenta: ");
				//~ int num = sc.nextInt();
				//~ sc.nextLine();
				//~ System.out.print("Introduzca el nombre del titular: ");
				//~ String nom = sc.nextLine();
				//~ System.out.print("Introduzca la cantidad de la cuenta: ");
				//~ double sal = sc.nextDouble();
				registro = new Registro_Cbytes(suc);
				archivo.insertar(registro);
			}else if(option == 2){
				//~ System.out.println("--------------------------------------------------------------");
				//~ archivo.imprimirRegistros();
				//~ System.out.print("Introduzca el numero de registro a eliminar: ");
				//~ int num = sc.nextInt();
				//~ archivo.eliminar(num);
			}else if(option == 3){
				System.out.println("--------------------------------------------------------------");
				archivo.imprimirRegistros();
			}else if(option == 4){
				System.out.println("--------------------------------------------------------------");
				System.out.print("Introduzca la sucursal a la que se asociará (si no existe se creará): ");
				String suc = sc.nextLine();
				suc = suc.toUpperCase();
				if(!archivo.existeRegistro(suc)) {
					System.out.println("La sucursal no existe, por lo que será creada.");
					registro = new Registro_Cbytes(suc);
					archivo.insertar(registro);
				}
				System.out.print("Introduzca el número de cuenta: ");
				int num = sc.nextInt();
				sc.nextLine();
				System.out.print("Introduzca el nombre del titular: ");
				String nom = sc.nextLine();
				System.out.print("Introduzca la cantidad de la cuenta: ");
				double sal = sc.nextDouble();
				Cuenta cuenta = new Cuenta(num, nom, sal);
				archivo.insertarCuenta(suc, cuenta);
			}else if (option == 5) {
				System.out.println("--------------------------------------------------------------");
				System.out.print("Introduzca la sucursal que será eliminada: ");
				String suc = sc.nextLine();
				suc = suc.toUpperCase();
				if(!archivo.existeRegistro(suc)) {
					System.out.println("La sucursal no existe.");
				} else {
					archivo.eliminar(suc);
				}
			}else if (option == 6) {
				System.out.println("--------------------------------------------------------------");
			}else if(option == 7){
				System.out.println("--------------------------------------------------------------");
				System.out.println("Salir, Adios");
				raf.close();
			}else{
				System.out.println("--------------------------------------------------------------");
				System.out.println("Error, Opción invalida");
			}
		} catch( IOException e ) {
            
			System.out.println( "IOException:" );
			e.printStackTrace();
		}
	}
    
}
