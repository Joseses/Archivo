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
			}while(option != 4);
		} catch (Exception e) {
			System.out.println("Caracter inválido");
			e.printStackTrace();
		}
	}
    
	private static void showMenu(){
		System.out.print("Con el numero correspondiente, elija una opcion del menu: "+ "\n" +
							"1) Agregar una cuenta"+ "\n"+
							"2) Eliminar un Registro"+"\n"+
							"3) Imprimir Todos los Registros"+"\n"+
							"4) Salir del programa"+"\n"+
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
				System.out.print("Introduzca el número de cuenta: ");
				int num = sc.nextInt();
				sc.nextLine();
				System.out.print("Introduzca el nombre del titular: ");
				String nom = sc.nextLine();
				System.out.print("Introduzca la cantidad de la cuenta: ");
				double sal = sc.nextDouble();
				Cuenta cuenta = new Cuenta(num, nom, sal);
				if(!archivo.existeRegistro(suc)) {
					System.out.println("La sucursal no existe, por lo que será creada.");
					registro = new Registro_Cbytes(suc, cuenta);
					archivo.insertar(registro);
				} else {
					System.out.println("ELSE DE DISCO");
					archivo.insertarCuenta(suc, cuenta);
				}
			}else if(option == 2){
				System.out.print("Con el numero correspondiente, elija una opcion del menu: "+ "\n" +
							"1) Eliminar sucursal"+ "\n"+
							"2) Eliminar por número de cuenta"+"\n"+
							"3) Eliminar por titular"+"\n"+
							"4) Cancelar" + "\n" +
							"====== Opción: ");
				int opcion = sc.nextInt();
				sc.nextLine();
				if(opcion==1) {
					System.out.print("Introduzca la sucursal a eliminar: ");
					String suc = sc.nextLine();
					suc = suc.toUpperCase();
					if(!archivo.existeRegistro(suc)) {
						System.out.println("La sucursal no existe.");
					} else {
						System.out.println("ELSE DE DISCO");
						archivo.eliminarSuc(suc);
					}
				} else if(opcion==2) {
					System.out.print("Introduzca el número de cuenta a eliminar: ");
					int num = sc.nextInt();
					sc.nextLine();
					archivo.eliminarNum(num);
				} else if(opcion==3) {
					System.out.print("Introduzca el titular de la cuenta a eliminar: ");
					String suc = sc.nextLine();
					archivo.eliminarNom(suc);
				} else if(opcion==4) {
					
				} else {
					System.out.println("Opción inválida");
				}
			}else if(option == 3){
				System.out.println("--------------------------------------------------------------");
				archivo.imprimirRegistros();
			}else if(option == 4){
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
