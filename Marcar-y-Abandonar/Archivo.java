import java.io.*;
public class Archivo
{
    private RandomAccessFile raf = null;
    private static int eliminados = 0;
  
	public Archivo( RandomAccessFile raf ){
		this.raf = raf;
	}
  
  /*-----------------------------------------------------------------
  / inserta un registro al inicio del archivo
  /-----------------------------------------------------------------*/
    
	public void insertar( Registro_Fijo registro ) throws IOException{
		insertarEn( 0, registro );
	}
    
    /*-----------------------------------------------------------------
    / presenta los registros del archivo
    /-----------------------------------------------------------------*/
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
	
	public void imprimirTodo() throws IOException {
		
		Registro_Fijo registro = new Registro_Fijo();
		int length = (int) (raf.length() / registro.length());
		System.out.println( "Número de registros: " + length );
		raf.seek( 0 );
		for( int i = 0; i < length; i++ ) {
			
			registro.read( raf );
			System.out.println( "( " + registro.getSucursal() + ", "
                                     + registro.getNumero() + ", "
                                     + registro.getNombre() + ", "
                                     + registro.getSaldo() + ", "
                                     + registro.getEliminado() + " )" );
		}
	}
    
    /*-----------------------------------------------------------------
    / desplaza registros para insertar un registro en la posición p
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
        
		public void buscar(int p, Registro_Fijo registro) throws IOException {
			int n = (int) (raf.length() / registro.length());   //numero de registros en el archivo
			for(int i = 0; i<n; i++) {
				Registro_Fijo temp = new Registro_Fijo();
				raf.seek(i* temp.length());
				temp.read(raf);
			}
		}
}
