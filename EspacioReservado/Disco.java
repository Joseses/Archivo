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
			}while(option != 6);
		} catch (Exception e) {
			System.out.println("Caracter inválido");
			e.printStackTrace();
		}
	}
    
	private static void showMenu(){
		System.out.print("Con el numero correspondiente, elija una opcion del menu: "+ "\n" +
							"1) Agregar cuenta"+ "\n"+
							"2) Eliminar una sucursal"+"\n"+
							"3) Imprimir Todos los Registros"+"\n"+
							"4) Borrar una cuenta"+"\n"+
							"5) Buscar un Registro"+"\n"+
							"6) Salir del programa"+"\n"+
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
			}else if(option == 2){
				System.out.println("--------------------------------------------------------------");
				System.out.print("Introduzca la sucursal que será eliminada: ");
				String suc = sc.nextLine();
				suc = suc.toUpperCase();
				if(!archivo.existeRegistro(suc)) {
					System.out.println("La sucursal no existe.");
				} else {
					archivo.eliminar(suc);
				}
			}else if(option == 3){
				System.out.println("--------------------------------------------------------------");
				archivo.imprimirRegistros();
			}else if(option == 4){
				System.out.println("--------------------------------------------------------------");
				System.out.print("Introduzca la sucursal donde está el registro a eliminar: ");
				String suc = sc.nextLine();
				suc = suc.toUpperCase();
				if(!archivo.existeRegistro(suc)) {
					System.out.println("La sucursal no existe.");
				} else {
					System.out.print("Introduzca el número de cuenta a eliminar: ");
					int num = sc.nextInt();
					sc.nextLine();
					if(archivo.eliminarCuenta(suc, num)) {
						System.out.println("La eliminación ha sido un éxito.");
						System.out.println("--------------------------------------------------------------");
						System.out.println("--------------------------------------------------------------");
					} else {
						System.out.println("La eliminación no ha podido realizarse.");
						System.out.println("--------------------------------------------------------------");
						System.out.println("--------------------------------------------------------------");
					}
				}
			}else if (option == 5) {
				//FALTA METODO AQUI
			}else if (option == 6) {
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
