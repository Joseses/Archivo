import java.util.Scanner;
import java.io.*;

public class Disco {
	
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
							"1) Crear un Registro"+ "\n"+
							"2) Eliminar un Registro"+"\n"+
							"3) Imprimir registros activos"+"\n"+
							"4) Imprimir todos los registros (incluye los marcados)" + 
							"\n" + "5) Buscar un Registro"+"\n"+
							"6) Salir del programa"+"\n"+
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
				sc.nextLine();
				registro = new Registro_Fijo(suc, num, nom, sal);
				archivo.insertar(registro);
			}else if(option == 2){
				System.out.println("--------------------------------------------------------------");
				archivo.imprimirRegistros();
				System.out.print("Introduzca el número de cuenta a eliminar: ");
				int num = sc.nextInt();
				archivo.eliminar(num);
			}else if(option == 3){
				System.out.println("--------------------------------------------------------------");
				archivo.imprimirRegistros();
			}else if(option == 4){
				System.out.println("--------------------------------------------------------------");
				archivo.imprimirTodo();
			}else if(option == 6){
				System.out.println("--------------------------------------------------------------");
				System.out.println("Salir, Adios");
				raf.close();
			}else if (option == 5) {
				
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
