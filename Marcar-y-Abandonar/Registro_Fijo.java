import java.io.*;
import java.lang.*;


public class Registro_Fijo 
{
    private byte[] sucursal = new byte[20];
	private int numero = 0;
	private byte[] nombre = new byte[20];
	private double saldo = 0;
	private byte[] eliminado = new byte[1];
    
    /*-----------------------------------------------------------------
    / constructores
    /-----------------------------------------------------------------*/
    
	public Registro_Fijo() {}
    
	public Registro_Fijo( String nomSucursal, int numCuenta,
                     String nomCliente, double deposito ){
		byte[] chars;
        
		if( nomSucursal.length() > 20 || nomCliente.length() > 20 ) {
			System.out.println( "ATENCION: Sucursal o nombre con más de 20 caracteres" );
		}
		
		for( int i = 0; i < 20 && i < nomSucursal.getBytes().length; i++ ){
			sucursal[i] = nomSucursal.getBytes()[i];
		}
		numero = numCuenta;
		for( int i = 0; i < 20 && i < nomCliente.getBytes().length; i++ ){
			nombre[i] = nomCliente.getBytes()[i];
		}
		String temp = "n";
		eliminado[0] = temp.getBytes()[0];
		saldo = deposito;
	}
    
    /*-----------------------------------------------------------------
    / métodos getters
    /-----------------------------------------------------------------*/
    
	public String getSucursal() { return new String( sucursal ); }
    
	public int getNumero()      { return this.numero; }
    
	public String getNombre()   { return new String( nombre ); }
    
	public double getSaldo()    { return saldo; }
	
	public String getEliminado() { return new String( eliminado ); }
    
    /*-----------------------------------------------------------------
    / longitud en bytes de un registro
    /-----------------------------------------------------------------*/
    
	public int length() {
        
		return sucursal.length +
               (Integer.SIZE / 8) +
               nombre.length +
               (Double.SIZE / 8) +
               eliminado.length;
	}
    
    /*-----------------------------------------------------------------
    / métodos para escribir y leer un registro
    /-----------------------------------------------------------------*/
    
	public void read( RandomAccessFile raf ) throws IOException {
        
        raf.read( eliminado );
		raf.read( sucursal );
		numero = raf.readInt();
		raf.read( nombre );
		saldo = raf.readDouble();
	}
    
	public void write( RandomAccessFile raf ) throws IOException {
        
        raf.write( eliminado );
		raf.write( sucursal );
		raf.writeInt( numero );
		raf.write( nombre );
		raf.writeDouble( saldo );
	}
	
	public void erase( RandomAccessFile raf) throws IOException {
		String temp = "y";
		eliminado[0] = temp.getBytes()[0]; //Escribimos "y" para marcar el registro como eliminado
		raf.write( eliminado );
		raf.write( sucursal );
		raf.writeInt( numero );
		raf.write( nombre );
		raf.writeDouble( saldo );
		System.out.println("[REGISTRO - erase] " + getEliminado());
	}

}
