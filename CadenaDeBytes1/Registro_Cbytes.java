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
	
	//~ public void crearCuenta(Cuenta cuenta, RandomAccessFile raf) throws IOException {
		//~ int p = 0;
		//~ int x = 0;
		//~ long fijo = raf.getFilePointer();
		//~ System.out.println("[REGISTRO - crearcuenta] Apuntador de archivo fijo " + fijo);
		//~ boolean hayEliminados = false;
		//~ for(int i = 0; i<(5); i++) {
			//~ Cuenta temp = new Cuenta();
			//~ temp.read(raf); //Se leen 33 bytes en teoria
			//~ if(temp.getEliminado().equals("y")) {
				//~ x = i;
				//~ i = 5;
				//~ hayEliminados = true;
				//~ System.out.println("Hay registros eliminados!");
			//~ }
		//~ }
		//~ if(hayEliminados) {
		//~ System.out.println("Efectivamente hay registros eliminados!");
		//~ raf.seek(fijo+(x * cuenta.length()));   // inserta el nuevo registro
		//~ cuenta.write( raf );
		//~ } else {
			//~ for( int i = 3; i >= p; i -- ) {    // desplazamiento de registros
				//~ Cuenta temp = new Cuenta();
				//~ System.out.println("[REGISTRO - crearcuenta] Apuntador de archivo en for " + 
									//~ raf.getFilePointer());
				//~ raf.seek( fijo+(i * temp.length()) );
				//~ temp.read( raf );
				//~ System.out.println("[REGISTRO - crearcuenta] Apuntador de archivo en for despues de primer lectura " + 
									//~ raf.getFilePointer() + " y fijo es" + fijo);
				//~ 
				//~ raf.seek(fijo + ((i+1) * temp.length()));
				//~ temp.write( raf );
			//~ }
			//~ System.out.println("[REGISTRO - crearcuenta] Por insertar la nueva cuenta " + 
								//~ cuenta.getNombre() + ", " + cuenta.getNumero());
			//~ raf.seek(fijo+(p * cuenta.length()) );   // inserta el nuevo registro
			//~ cuenta.write( raf );
		//~ }
	//~ }
    
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
		System.out.println("[REGISTRO - read]Posición de puntero antes de entrar a while: "
							+ raf.getFilePointer());
		System.out.println("[REGISTRO - read] Fin de registro? "
							+ getFinRegistro());
		if(raf.getFilePointer()!=0) {
			while(!getFinRegistro().equals("!")){
				Cuenta temp = new Cuenta();
				raf.seek(raf.getFilePointer()-1);
				System.out.println("[REGISTRO - read] Apuntador dentro de condición "
									+ raf.getFilePointer());
				temp.read(raf);
				cuentas.add(temp);
				raf.read( finRegistro );
				System.out.println("[REGISTRO - read] Fin de registro (while)? "
							+ getFinRegistro());
			}
			System.out.println("[REGISTRO - read] Se han leído " + cuentas.size() 
							+ " cuentas");
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
		System.out.println("[REGISTRO - write] Se han escrito " + cuentas.size() 
							+ " cuentas, en la sucursal " + getSucursal());
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
