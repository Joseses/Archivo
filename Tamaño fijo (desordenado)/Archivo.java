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
    
	public void imprimirRegistros() throws IOException {
        int contel = 0;
		Registro_Fijo registro = new Registro_Fijo();
		int length = (int) (raf.length() / registro.length());
        
		raf.seek( 0 );
        
		for( int i = 0; i < length; i++ ) {
            
			registro.read( raf );
            if(registro.getEliminado().equals("y")) {
				System.out.println((i+1)+". " + "Registro marcado para eliminar");
				contel++;
			} else {
				System.out.println( (i+1) +". " + "( " + registro.getSucursal() + ", "
										 + registro.getNumero() + ", "
										 + registro.getNombre() + ", "
										 + registro.getSaldo() + " )" );
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
			System.out.println("Efectovamente hay registros eliminados!");
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
	
	public void eliminar(int p) throws IOException {
		Registro_Fijo temp = new Registro_Fijo();
		int n = (int) (raf.length() / temp.length());
		raf.seek( (p-1) * temp.length() );   // Marcamos el registro
		temp.mark( raf );
	}
}
