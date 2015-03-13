/******************************************************************
/  clase Archivo
/
/  autor: Dr. JosŽ Luis Zechinelli Martini
/	Buscando cosas....
/******************************************************************/

import java.io.*;

public class Archivo {
	private RandomAccessFile raf = null;
  
	public Archivo( RandomAccessFile raf ){
		this.raf = raf;
  	}
  
  /*-----------------------------------------------------------------
  / inserta un registro al inicio del archivo
  /-----------------------------------------------------------------*/
    
	public void insertar( Registro_Fijo registro ) throws IOException {
		insertarEn( 0, registro );
	}
    
    /*-----------------------------------------------------------------
    / presenta los registros del archivo
    / si hay un registro marcado lo ignora.
    /-----------------------------------------------------------------*/
    public void imprimirTodo() throws IOException {
		
		int contel = 0;
		Registro_Fijo registro = new Registro_Fijo();
		int length = (int) (raf.length() / registro.length());
		raf.seek( 0 );
		System.out.printf("%-12s%-7s%-12s%-9s%s\n","Sucursal","Cuenta",
							"Nombre", "Saldo", "¿Marcado?");
		for( int i = 0; i < length; i++ ) {
			registro.read( raf );
			if(registro.getEliminado().equals("y")){
				//System.out.println((i+1)+". " + "Registro marcado para eliminar");
				contel++;
			}
			int tempo = registro.getNumero();
			String nume = Integer.toString(tempo);
			System.out.printf("%-12s%-7d%-12s%-9.1f%s\n",registro.getSucursal().trim() + " ",
							registro.getNumero(),registro.getNombre().trim(), 
							registro.getSaldo(), registro.getEliminado());
		}
		System.out.println("");
		System.out.println("y - SI está marcado :::: n - NO está marcado");
		System.out.println( "Número de registros activos: " + (length-contel) );
		System.out.println( "Número de registros marcados: " + (contel) );
		System.out.println( "Número de registros totales: " + length );
		System.out.println("--------------------------------------------------------------");
	}
	
	public void imprimirRegistros() throws IOException {
		int contel = 0;
		Registro_Fijo registro = new Registro_Fijo();
		int length = (int) (raf.length() / registro.length());
		raf.seek( 0 );
		
		for( int i = 0; i < length; i++ ){
			registro.read( raf );
			if(registro.getEliminado().equals("y")){
				//System.out.println((i+1)+". " + "Registro marcado para eliminar");
				contel++;
			}else{
				System.out.println( "( " + registro.getSucursal() + ", "
									+ registro.getNumero() + ", "
									+ registro.getNombre() + ", "
									+ registro.getSaldo() + ", "
									+ registro.getEliminado() + " )" );
			}
		 }
		System.out.println( "Número de registros activos: " + (length-contel) );
		System.out.println("--------------------------------------------------------------");
	}
    
    /*-----------------------------------------------------------------
    / Revisa si hay un registro marcado, y si así es, ahí inserta el registro o
    / en su defecto, desplaza registros para insertar un registro en la posición p
    /-----------------------------------------------------------------*/
    
	private void insertarEn( int p, Registro_Fijo registro ) throws IOException {
        
		int n = (int) (raf.length() / registro.length());
		int x = 0;
		boolean hayEliminados = false;
		
		for(int i = 0; i<n; i++) {
			Registro_Fijo temp = new Registro_Fijo();
			raf.seek(i* temp.length());
			temp.read(raf);
			if(temp.getEliminado().equals("y")) {
				x = i;
				i = n;
				hayEliminados = true;
				System.out.println("Hay registros eliminados!");
			}
		}
		
		if(hayEliminados) {
			System.out.println("Efectivamente hay registros eliminados!");
			raf.seek( x * registro.length() );   // inserta el nuevo registro
			registro.write( raf );
		} else {
			for( int i = n-1; i >= p; i -- ) {    // desplazamiento de registros
            
			Registro_Fijo temp = new Registro_Fijo();
            
			raf.seek( i * temp.length() );
			temp.read( raf );
            
			raf.seek( (i+1) * temp.length() );
			temp.write( raf );
			}
			raf.seek( p * registro.length() );   // inserta el nuevo registro
			registro.write( raf );
		}
	}
	
	public void eliminar(int p) throws IOException {    //Marcar y usar despues
		System.out.println("[ARCHIVO - eliminar] se inicia el método con el argumento: " + p);
		Registro_Fijo temp = new Registro_Fijo();
		int n = (int) (raf.length() / temp.length());
		System.out.println("[ARCHIVO - eliminar] n es: " + n);
		raf.seek(0);
		for(int i =0; i<n;i++) {
			Registro_Fijo temp1 = new Registro_Fijo();
			//~ raf.seek(i*temp.length());
			temp1.read(raf);
			System.out.println("[ARCHIVO - eliminar] el número de registro es: " 
								+ temp1.getNumero() + " y buscamos " + p);
			if(p==temp1.getNumero() && !temp1.getEliminado().equals("y")) {
				raf.seek(i*temp1.length());
				System.out.println("[ARCHIVO - eliminar] " + p + " " + temp1.getNumero());
				System.out.println("[ARCHIVO - eliminar] entramos en la condición con el número "
									+ temp1.getNumero());
				temp1.erase(raf);
				i = n;
			}
		}
	}
}
