import java.io.*;
import java.lang.*;

public class Cuenta {
	
	private int numero = 0;
	private byte[] nombre = new byte[20];
	private double saldo = 0;
	private byte[] eliminado = new byte[1];
	
	public static void main (String args[]) {
		
	}
	
	public Cuenta() {}
	
	public Cuenta(int num, String nomb, double sal) {
		if( nomb.length() > 20 ) {
			System.out.println( "ATENCION: Sucursal o nombre con más de 20 caracteres" );
		}
		
		for( int i = 0; i < 20 && i < nomb.getBytes().length; i++ ){
			nombre[i] = nomb.getBytes()[i];
		}
		numero = num;
		saldo = sal;
		String temp = "n";
		eliminado[0] = temp.getBytes()[0];
		
	}
	
	public long length () {
		return (Integer.SIZE / 8) +
			nombre.length +
			(Double.SIZE / 8) +
			eliminado.length;
	}
	
	public int getNumero()      { return this.numero; }
    
	public String getNombre()   { return new String( nombre ); }
    
	public double getSaldo()    { return saldo; }
	
	public String getEliminado() { return new String( eliminado ); }
	
	public void setEliminado(String elim) {
		eliminado[0] = elim.getBytes()[0];
	}

	/*-----------------------------------------------------------------
	/ métodos para escribir y leer un registro
	/-----------------------------------------------------------------*/

	public void read( RandomAccessFile raf ) throws IOException {
		long fijo = raf.getFilePointer();
		System.out.println("[CUENTA - read] Tratando de leer en posicion " +
							fijo);
		raf.read( eliminado );
		numero = raf.readInt();
		raf.read( nombre );
		saldo = raf.readDouble();
		System.out.println("[CUENTA - read] Se leyó en el archivo " +
							getEliminado() + " " + getNumero() + " " + getNombre() +
							" " + getSaldo());
	}
    
	public void write( RandomAccessFile raf ) throws IOException {
        
		raf.write( eliminado );
		raf.writeInt( numero );
		raf.write( nombre );
		raf.writeDouble( saldo );
		System.out.println("[CUENTA - write] Se escribió en el archivo " +
							eliminado + " " + numero + " " + nombre +
							" " + saldo);
		System.out.println("[CUENTA - write] El apuntador termina en " +  (raf.getFilePointer()));
	}
	
	public void erase( RandomAccessFile raf) throws IOException {
		String temp = "y";
		eliminado[0] = temp.getBytes()[0]; //Escribimos "y" para marcar el registro como eliminado
		raf.write( eliminado );
		//~ raf.writeInt( numero );
		//~ raf.write( nombre );
		//~ raf.writeDouble( saldo );
		System.out.println("[REGISTRO - erase] " + getEliminado());
	}
}

