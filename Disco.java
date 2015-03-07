import java.util.Scanner;
import java.io.*;
//Testing
public class Disco{
	
	public static void main(String[] Betoesputo){
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
		}
	}
    
	private static void showMenu(){
		System.out.print("Con el numero correspondiente, elija una opcion del menu: "+ "\n" +
							"1) Crear un Registro"+ "\n"+
							"2) Eliminar un Registro"+"\n"+
							"3) Imprimir un Registro"+"\n"+
							"4) Salir del programa"+"\n"+
							"====== Opción:");
	}
    
	public static void setMenu(int option){
		try {
            
			File file = new File( "Depositos.Info" );
			RandomAccessFile raf = new RandomAccessFile( file, "rw" );
			Archivo archivo = new Archivo( raf );
            
			Registro_Fijo registro;
			
			Scanner sc = new Scanner(System.in);
			if(option == 1){
				System.out.println("--------------------------------------------------------------");
				System.out.print("Introduzca el nombre de la sucursal: ");
				String suc = sc.nextLine();
				System.out.print("Introduzca el número de cuenta: ");
				int num = sc.nextInt();
				sc.nextLine();
				System.out.print("Introduzca el nombre del titular: ");
				String nom = sc.nextLine();
				System.out.print("Introduzca la cantidad de la cuenta: ");
				double sal = sc.nextDouble();
				registro = new Registro_Fijo(suc, num, nom, sal);
				archivo.insertar(registro);
			}else if(option == 2){
				System.out.println("--------------------------------------------------------------");
				archivo.imprimirRegistros();
				System.out.print("Introduzca el numero de registro a eliminar: ");
				int num = sc.nextInt();
				archivo.eliminar(num);
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
