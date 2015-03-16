import java.io.*;
public class Archivo
{
    private RandomAccessFile raf = null;
    private static int mayorPal = 0;
  
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
		System.out.println( "Número de registros activos: " + (length-contel) );
		System.out.println( "Número de registros eliminados: " + (contel) );
		System.out.println( "Número de registros totales: " + length );
		System.out.println("--------------------------------------------------------------");
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
			Registro_Fijo temp = new Registro_Fijo();
			int n = (int) (raf.length() / temp.length());
			raf.seek(0);
			for(int i =0; i<n;i++) {
				Registro_Fijo temp1 = new Registro_Fijo();
				//~ raf.seek(i*temp.length());
				temp1.read(raf);
				if(p==temp1.getNumero() && !temp1.getEliminado().equals("y")) {
					raf.seek(i*temp1.length());
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
