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
    /-----------------------------------------------------------------*/
    
	public void imprimirRegistros() throws IOException {
        
		Registro_Fijo registro = new Registro_Fijo();
		int length = (int) (raf.length() / registro.length());
        
		System.out.println( "Nœmero de registros: " + length );
		raf.seek( 0 );
        
		for( int i = 0; i < length; i++ ) {
            
			registro.read( raf );
            
			System.out.println( "( " + registro.getSucursal() + ", "
                                     + registro.getNumero() + ", "
                                     + registro.getNombre() + ", "
                                     + registro.getSaldo() + " )" );
		}
	}
    
    /*-----------------------------------------------------------------
    / desplaza registros para insertar un registro en la posici—n p
    /-----------------------------------------------------------------*/
    
	private void insertarEn( int p, Registro_Fijo registro ) throws IOException {
        
		int n = (int) (raf.length() / registro.length());
        
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
