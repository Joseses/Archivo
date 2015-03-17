import java.io.*;
import java.lang.*;
import java.util.*;


public class Registro_Cbytes {
	private byte[] eliminado = new byte[1];
	private byte[] sucursal = new byte[20];
	private List<Cuenta> cuentas = new ArrayList<Cuenta>();
	private byte[] finRegistro = new byte[1];
    
    /*-----------------------------------------------------------------
    / constructores
    /-----------------------------------------------------------------*/
    
	public Registro_Cbytes() {
		//~ Cuenta temp = new Cuenta();
		//~ cuentas.add(temp);
		String otro = "!";
		finRegistro[0] = otro.getBytes()[0];
	}
    
	public Registro_Cbytes(String nomSucursal, Cuenta cuenta){
		byte[] chars;
		nomSucursal = nomSucursal.toUpperCase();
        
		if( nomSucursal.length() > 20 ) {
			System.out.println( "ATENCION: Sucursal o nombre con más de 20 caracteres" );
		}
		
		for( int i = 0; i < 20 && i < nomSucursal.getBytes().length; i++ ){
			sucursal[i] = nomSucursal.getBytes()[i];
		}
		cuentas.add(cuenta);
		String text = "n";
		eliminado[0] = text.getBytes()[0];
		String otro = "!";
		finRegistro[0] = otro.getBytes()[0];
	}
	
    
    /*-----------------------------------------------------------------
    / métodos getters
    /-----------------------------------------------------------------*/
    
	public String getSucursal() { return new String( sucursal ); }

	public String getEliminado() { return new String( eliminado ); }

	public String getFinRegistro() { return new String( finRegistro ); }
	
	public List<Cuenta> getCuentas() {
		return cuentas;
	}
	
	public void addCuenta(Cuenta cuenta) {
		this.cuentas.add(cuenta);
	}
	
	public void setCuenta(Cuenta cuenta, int pos) {
		this.cuentas.set(pos,cuenta);
	}
	
	public int getNumCuentas() { return cuentas.size(); }

    /*-----------------------------------------------------------------
    / longitud en bytes de un registro
    /-----------------------------------------------------------------*/
    
	public long length() {
		Cuenta temp = new Cuenta();
		return sucursal.length + temp.length() + eliminado.length
				+ finRegistro.length; //string sucursal + 5 cuentas + eliminado
	}
	
	public long lengthSC() {
		return sucursal.length + eliminado.length + finRegistro.length;
	}
    
    /*-----------------------------------------------------------------
    / métodos para escribir y leer un registro
    /-----------------------------------------------------------------*/
    public long read( RandomAccessFile raf ) throws IOException {
		long fijo = raf.getFilePointer();
		raf.read( eliminado );
		raf.read( sucursal );
		raf.read( finRegistro );
		if(raf.getFilePointer()!=0) {
			while(!getFinRegistro().equals("!")){
				Cuenta temp = new Cuenta();
				raf.seek(raf.getFilePointer()-1);
				temp.read(raf);
				cuentas.add(temp);
				raf.read( finRegistro );
			}
		}
		return raf.getFilePointer()-fijo;
	}
	
	public void write( RandomAccessFile raf ) throws IOException {
		raf.write( eliminado );
		raf.write( sucursal );
		for(int i = 0; i<cuentas.size(); i++) {
			cuentas.get(i).write(raf);
		}
		raf.write( finRegistro );
	}
	
	public void erase( RandomAccessFile raf) throws IOException {
		String temp = "y";
		eliminado[0] = temp.getBytes()[0]; //Escribimos "y" para marcar el registro como eliminado
		raf.write( eliminado );
		raf.write( sucursal );
		for(int i = 0; i<cuentas.size(); i++) {
			cuentas.get(i).write(raf);
		}
		raf.write( finRegistro );
		
	}
}
