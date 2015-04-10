/******************************************************************
/  clase Archivo
/
/  autor: Dr. José Luis Zechinelli Martini

/******************************************************************/

import java.io.*;
import java.util.*;


//Comentario de prueba...
public class Archivo {
	private RandomAccessFile raf = null;
  
	public Archivo( RandomAccessFile raf ){
		this.raf = raf;
  	}
  
  /*-----------------------------------------------------------------
  / inserta un registro al inicio del archivo
  /-----------------------------------------------------------------*/
	public boolean existeRegistro(String sucursal) throws IOException {
		String sucursal1 = sucursal.toUpperCase();
		boolean existe = false;
		Registro_Cbytes registro = new Registro_Cbytes();
		raf.seek( 0 );
		long t = raf.length();
		for( long i = 0; i < t;){
			i = i+registro.read( raf );
			if(registro.getSucursal().trim().equals(sucursal1)){
				//System.out.println((i+1)+". " + "Registro marcado para eliminar");
				existe = true;
				i = t;
			}
		 }
		 return existe;
	}
	
	public void insertarCuenta(String sucursal, Cuenta cuenta) throws IOException{
		sucursal = sucursal.toUpperCase();
		List<Long> tams = new ArrayList<Long>();
		raf.seek(0);
		for(long i = 0; i<raf.length();) {
			Registro_Cbytes temp = new Registro_Cbytes();
			tams.add(temp.read(raf)); //Lee cuentas hasta que encuentre el signo de fin de registro
									//También obtiene el tamaño del registro...
			i = i + raf.getFilePointer();
		}
		raf.seek(0);
		for(long i = 0; i<raf.length();) {
			Registro_Cbytes temp = new Registro_Cbytes();
			long x = temp.read(raf); //Lee cuentas hasta que encuentre el signo de fin de registro
									//También obtiene el tamaño del registro...
			i = raf.getFilePointer();
			if(temp.getSucursal().trim().equals(sucursal)) {
				i = raf.length(); //Termina el for
				long z = x;
				long ap = raf.getFilePointer();
				long t = raf.length();
				int cont = tams.size()-1;
				for(long y = t; y>ap;) { 
					//Se mueven los registros para insertar... 
					//...las cuentas nuevas
					Registro_Cbytes temp1 = new Registro_Cbytes();
					raf.seek(y-(tams.get((int)cont)));
					temp1.read(raf);
					raf.seek((y-(tams.get((int)cont)))+cuenta.length());
					temp1.write(raf);
					y = (y-((tams.get((int)cont))))-cuenta.length();
					cont--;
				}
				raf.seek(ap-z);
				temp.addCuenta(cuenta);
				temp.write(raf);
				i = raf.length();
			}
		}
	}
	
    /*-----------------------------------------------------------------
    / presenta los registros del archivo
    / si hay un registro marcado lo ignora.
    /-----------------------------------------------------------------*/
	public void imprimirRegistros() throws IOException {
		int contel = 0;
		raf.seek( 0 );
		long t = raf.length();
		for( long i = 0; i < t;){
			Registro_Cbytes registro = new Registro_Cbytes();
			i = i+registro.read( raf );
			if(registro.getEliminado().equals("y")){
				//System.out.println((i+1)+". " + "Registro marcado para eliminar");
				contel++;
			}else{
				System.out.println( "( " + registro.getSucursal() + " )");
				List<Cuenta> cuentas = registro.getCuentas();
				for(int l = 0; l<cuentas.size() ; l++) {
					Cuenta temp = cuentas.get(l);
					if(temp.getEliminado().equals("y")) {
						
					} else {
						System.out.println("( " + temp.getNumero() + " ," + temp.getNombre() 
										+ ", " + temp.getSaldo() + " )");
					}
				}
			}
		 }
		System.out.println("--------------------------------------------------------------");
	}
    
    /*-----------------------------------------------------------------
    / Revisa si hay un registro marcado, y si así es, ahí inserta el registro o
    / en su defecto, desplaza registros para insertar un registro en la posición p
    /-----------------------------------------------------------------*/
    
	public void insertar(Registro_Cbytes registro) throws IOException {
		int p = 0;
		long x = 0;
		long tamReg = 0;
		boolean hayEliminados = false;
		List<Long> tams = new ArrayList<Long>();
		raf.seek(0);
		for(long i = 0; i<raf.length();) {
			Registro_Cbytes temp = new Registro_Cbytes();
			tams.add(temp.read(raf)); //Lee cuentas hasta que encuentre el signo de fin de registro
									//También obtiene el tamaño del registro...
			i = raf.getFilePointer();
		}
		
		if(hayEliminados) {
			System.out.println("Efectivamente hay registros eliminados!");
			raf.seek( x );
			registro.write( raf ); //Inserta el nuevo registro en la posición x (veáse x arriba)
		} else {
			if(raf.length()!=0) { //Creación de primer registro...
				long t = raf.length();
				int cont = tams.size()-1;
				for( long i = t; i >= 0;) {    // desplazamiento de registros
					//(Apuntador de final de archivo)-(tamaño de último registro leído)
					Registro_Cbytes temp = new Registro_Cbytes();
					if(i!=0) {
						raf.seek(i-(tams.get((int)cont))); 
					} else {
						raf.seek(0);
					}
					temp.read( raf );
					if(i!=0) {
						raf.seek((i-(tams.get((int)cont)))+registro.length());
					} else {
						raf.seek(registro.length());
					}
					temp.write( raf );
					i = (i-((tams.get((int)cont))))-registro.length();
					cont--;
				}
			}
			raf.seek( p );   // inserta el nuevo registro
			registro.write( raf );
		}
	}
	
	public void eliminarSuc(String suc) throws IOException {    //Marcar y usar despues
		raf.seek(0);
		for(long i =0; i<raf.length();) {
			Registro_Cbytes temp1 = new Registro_Cbytes();
			long x = temp1.read(raf);
			long point = raf.getFilePointer();
			if(temp1.getSucursal().trim().equals(suc) && !temp1.getEliminado().equals("y")) {
				raf.seek(point-x);
				temp1.erase(raf);
				i = raf.length();
			}
		}
	}
	
	public void eliminarNum(int numCuenta) throws IOException {
		raf.seek(0);
		for(long i =0; i<raf.length();) {
			Registro_Cbytes registro = new Registro_Cbytes();
			long x = registro.read(raf); //Tamaño de registro
			long point = raf.getFilePointer(); //Posición después de leer registro
			List<Cuenta> cuentas = registro.getCuentas();
			if(!registro.getEliminado().equals("y")){
				for(int l = 0; l<cuentas.size() ; l++) {
					Cuenta cuenta = cuentas.get(l);
					if(cuenta.getNumero()==numCuenta && !cuenta.getEliminado().equals("y")) {
						cuenta.setEliminado("y");
						registro.setCuenta(cuenta, l);
						raf.seek(point-x);
						registro.write(raf);
						i = raf.length();
						l = cuentas.size();
					}
				}
			}
		}
	}
	
	public void eliminarNom(String nombre) throws IOException {
		raf.seek(0);
		for(long i =0; i<raf.length();) {
			Registro_Cbytes registro = new Registro_Cbytes();
			long x = registro.read(raf); //Tamaño de registro
			long point = raf.getFilePointer(); //Posición después de leer registro
			List<Cuenta> cuentas = registro.getCuentas();
			if(!registro.getEliminado().equals("y")){
				for(int l = 0; l<cuentas.size() ; l++) {
					Cuenta cuenta = cuentas.get(l);
					if(cuenta.getNombre().trim().equals(nombre) && !cuenta.getEliminado().equals("y")) {
						cuenta.setEliminado("y");
						registro.setCuenta(cuenta, l);
						raf.seek(point-x);
						registro.write(raf);
						i = raf.length();
						l = cuentas.size();
					}
				}
			}
		}
	}
	
}
