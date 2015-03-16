import java.io.*;
import java.lang.*;


public class Registro_Cbytes {
	private byte[] eliminado = new byte[1];
	private byte[] sucursal = new byte[20];
	private Cuenta[] cuentas = new Cuenta[5];
    
    /*-----------------------------------------------------------------
    / constructores
    /-----------------------------------------------------------------*/
    
	public Registro_Cbytes() {
		for(int i = 0; i< 5; i++) {
			Cuenta temp = new Cuenta();
			cuentas[i] = temp;
		}
	}
    
	public Registro_Cbytes(String nomSucursal){
		byte[] chars;
		nomSucursal = nomSucursal.toUpperCase();
        
		if( nomSucursal.length() > 20 ) {
			System.out.println( "ATENCION: Sucursal o nombre con más de 20 caracteres" );
		}
		
		for( int i = 0; i < 20 && i < nomSucursal.getBytes().length; i++ ){
			sucursal[i] = nomSucursal.getBytes()[i];
		}
		for(int i = 0; i< 5; i++) {
			Cuenta temp = new Cuenta();
			cuentas[i] = temp;
		}
		
	}
	
	public void crearCuenta(Cuenta cuenta, RandomAccessFile raf) throws IOException {
		int p = 0;
		int x = 0;
		long fijo = raf.getFilePointer();
		boolean hayEliminados = false;
		for(int i = 0; i<(5); i++) {
			Cuenta temp = new Cuenta();
				//~ raf.seek(fijo+(temp.length()));
			temp.read(raf); //Se leen 33 bytes en teoria
			if(temp.getEliminado().equals("y")) {
				x = i;
				i = 5;
				hayEliminados = true;
			}
		}
		if(hayEliminados) {
		raf.seek(fijo+(x * cuenta.length()));   // inserta el nuevo registro
		cuenta.write( raf );
		} else {
			for( int i = 3; i >= p; i -- ) {    // desplazamiento de registros
				Cuenta temp = new Cuenta();
				raf.seek( fijo+(i * temp.length()) );
				temp.read( raf );
				raf.seek(fijo + ((i+1) * temp.length()));
				temp.write( raf );
			}
			raf.seek(fijo+(p * cuenta.length()) );   // inserta el nuevo registro
			cuenta.write( raf );
		}
	}
    
    /*-----------------------------------------------------------------
    / métodos getters
    /-----------------------------------------------------------------*/
    
	public String getSucursal() { return new String( sucursal ); }
    
    public String getEliminado() { return new String( eliminado ); }
    
    public Cuenta[] getCuentas() {return cuentas;}
    /*-----------------------------------------------------------------
    / longitud en bytes de un registro
    /-----------------------------------------------------------------*/
    
	public int length() {
		Cuenta temp = new Cuenta();
		return sucursal.length + (temp.length()*5) +
									eliminado.length; //string sucursal + 5 cuentas + eliminado
	}
    
    /*-----------------------------------------------------------------
    / métodos para escribir y leer un registro
    /-----------------------------------------------------------------*/
    public void read( RandomAccessFile raf ) throws IOException {
		Cuenta temp = new Cuenta();
		raf.read( eliminado );
		raf.read( sucursal );
		//~ raf.seek(raf.getFilePointer()+(temp.length()*5));
		for(int i = 0; i<5;i++) {
			try{
				cuentas[i].read(raf);
			} catch(Exception e) {
				raf.seek(raf.getFilePointer()+temp.length());
			}
		}
	}
	
	public void write( RandomAccessFile raf ) throws IOException {
		Cuenta temp = new Cuenta();
		raf.write( eliminado );
		raf.write( sucursal );
		//~ raf.seek(raf.getFilePointer()+(temp.length()*5));
		for(int i = 0; i<5;i++) {
			cuentas[i].write(raf);
		}
	}
	
	public void erase( RandomAccessFile raf) throws IOException {
		String temp = "y";
		eliminado[0] = temp.getBytes()[0]; //Escribimos "y" para marcar el registro como eliminado
		raf.write( eliminado );
		raf.write( sucursal );
		
	}
}
