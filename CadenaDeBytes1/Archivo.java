/******************************************************************
/  clase Archivo
/
/  autor: Dr. José Luis Zechinelli Martini

/******************************************************************/

import java.io.*;
import java.util.*;

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
			System.out.println("[ARCHIVO - existeRegistro] Valor de i " + i);
			System.out.println("[ARCHIVO - existeRegistro] " + registro.getSucursal() + ". es igual a " 
								+ sucursal1 + ". ?");
			if(registro.getSucursal().trim().equals(sucursal1)){
				//System.out.println((i+1)+". " + "Registro marcado para eliminar");
				System.out.println("[ARCHIVO - existeRegistro] Sí es igual...");
				existe = true;
				i = t;
			}
		 }
		 return existe;
	}
	
	public void insertarCuenta(String sucursal, Cuenta cuenta) throws IOException{
		System.out.println("[ARCHIVO - insertarCuenta] Se ha entrado a método insertarCuenta");
		sucursal = sucursal.toUpperCase();
		List<Long> tams = new ArrayList<Long>();
		raf.seek(0);
		for(long i = 0; i<raf.length();) {
			System.out.println("[ARCHIVO - insertarCuenta] Primer for!");
			Registro_Cbytes temp = new Registro_Cbytes();
			tams.add(temp.read(raf)); //Lee cuentas hasta que encuentre el signo de fin de registro
									//También obtiene el tamaño del registro...
			i = i + raf.getFilePointer();
		}
		raf.seek(0);
		for(long i = 0; i<raf.length();) {
			System.out.println("[ARCHIVO - insertarCuenta] Segundo for!");
			Registro_Cbytes temp = new Registro_Cbytes();
			long x = temp.read(raf); //Lee cuentas hasta que encuentre el signo de fin de registro
									//También obtiene el tamaño del registro...
			i = raf.getFilePointer();
			System.out.println("[ARCHIVO - insertarCuenta] " + sucursal + 
								" es igual a " + temp.getSucursal() + " ?");
			if(temp.getSucursal().trim().equals(sucursal)) {
				System.out.println("[ARCHIVO - insertarCuenta] Las cuentas son iguales!");
				i = raf.length(); //Termina el for
				long z = x;
				long ap = raf.getFilePointer();
				long t = raf.length();
				int cont = tams.size()-1;
				for(long y = t; y>ap;) { 
					System.out.println("[ARCHIVO - insertarCuenta] Tercer for!");
					//Se mueven los registros para insertar... 
					//...las cuentas nuevas
					System.out.println("[ARCHIVO - insertarCuenta] Se ha entrado en for");
					System.out.println("[ARCHIVO - insertarCuenta] Tamaño de ap " + ap);
					System.out.println("[ARCHIVO - insertarCuenta] Tamaño de y " + y);
					System.out.println("[ARCHIVO - insertarCuenta] Tamaño de arreglo de tamaños " 
										+ tams.size());
					System.out.println("[ARCHIVO - insertarCuenta] Tamaño de registro " + tams.get(cont));
					Registro_Cbytes temp1 = new Registro_Cbytes();
					raf.seek(y-(tams.get((int)cont)));
					temp1.read(raf);
					raf.seek((y-(tams.get((int)cont)))+cuenta.length());
					temp1.write(raf);
					y = (y-((tams.get((int)cont))))-cuenta.length();
					cont--;
				}
				System.out.println("[ARCHIVO - insertarCuenta] Se ha salido de for");
				System.out.println("[ARCHIVO - insertarCuenta] En teoria el puntero debe apuntar a " +
									(ap-z));
				raf.seek(ap-z);
				temp.addCuenta(cuenta);
				temp.write(raf);
				i = raf.length();
			}
		}
	}
	
	//~ public void insertarCuenta(String sucursal, Cuenta cuenta) throws IOException{
		//~ long z = 0;
		//~ long x = 0;
		//~ long tamReg = 0;
		//~ raf.seek( 0 );
		//~ for( long i = 0; i < raf.length();){
			//~ Registro_Cbytes registro = new Registro_Cbytes();
			//~ z = registro.read( raf );
			//~ i =i+z;
			//~ if(registro.getSucursal().trim().equals(sucursal)){
				//~ //System.out.println((i+1)+". " + "Registro marcado para eliminar");
				//~ long l = raf.getFilePointer();
				//~ long ti = l;
				//~ for(long a = 0; a<raf.length();) {
					//~ Registro_Cbytes temp = new Registro_Cbytes();
					//~ tamReg = temp.read(raf); //Lee cuentas hasta que encuentre el signo de fin de registro
											//~ //También obtiene el tamaño del registro...
					//~ a = raf.getFilePointer();
					//~ System.out.println("[ARCHIVO - insertarEn] Entramos a for, tamReg: " + tamReg);
					//~ if(temp.getEliminado().equals("y")) {
						//~ x = a-tamReg; //Apuntador a inicio de registro marcado eliminado...
						//~ a = raf.length();
					//~ }
				//~ }
				//~ for(long y = raf.length(); y>l;) { 
					//~ //Se mueven los registros para insertar... 
					//~ //...las cuentas nuevas
					//~ System.out.println("[ARCHIVO - insertarCuenta] Se ha entrado en for");
					//~ Registro_Cbytes temp = new Registro_Cbytes();
					//~ raf.seek(l-tamReg);
					//~ temp.read(raf);
					//~ raf.seek(l+cuenta.length());
					//~ temp.write(raf);
					//~ 
				//~ }
				//~ System.out.println("[ARCHIVO - insertarCuenta] Se ha salido de for");
				//~ 
				//~ raf.seek(ti-z);
				//~ registro.addCuenta(cuenta);
				//~ registro.write(raf);
				//~ i = raf.length();
			//~ }
		 //~ }
	//~ }
    
    /*-----------------------------------------------------------------
    / presenta los registros del archivo
    / si hay un registro marcado lo ignora.
    /-----------------------------------------------------------------*/
	//~ public void imprimirTodo() throws IOException {
		//~ 
		//~ int contel = 0;
		//~ Registro_Cbytes registro = new Registro_Cbytes();
		//~ int length = (int) (raf.length() / registro.length());
		//~ raf.seek( 0 );
		//~ System.out.printf("%-12s%-7s%-12s%-9s%s\n","Sucursal","Cuenta",
							//~ "Nombre", "Saldo", "¿Marcado?");
		//~ for( int i = 0; i < length; i++ ) {
			//~ registro.read( raf );
			//~ if(registro.getEliminado().equals("y")){
				//~ //System.out.println((i+1)+". " + "Registro marcado para eliminar");
				//~ contel++;
			//~ }
			//~ int tempo = registro.getNumero();
			//~ String nume = Integer.toString(tempo);
			//~ System.out.printf("%-12s%-7d%-12s%-9.1f%s\n",registro.getSucursal().trim() + " ",
							//~ registro.getNumero(),registro.getNombre().trim(), 
							//~ registro.getSaldo(), registro.getEliminado());
		//~ }
		//~ System.out.println("");
		//~ System.out.println("y - SI está marcado :::: n - NO está marcado");
		//~ System.out.println( "Número de registros activos: " + (length-contel) );
		//~ System.out.println( "Número de registros marcados: " + (contel) );
		//~ System.out.println( "Número de registros totales: " + length );
		//~ System.out.println("--------------------------------------------------------------");
	//~ }
	
	public void imprimirRegistros() throws IOException {
		int contel = 0;
		raf.seek( 0 );
		long t = raf.length();
		for( long i = 0; i < t;){
			Registro_Cbytes registro = new Registro_Cbytes();
			System.out.println("[ARCHIVO - imprimirregistro] Apuntador antes de leer siguiente registro: " 
								+ raf.getFilePointer());
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
	//~ 
	//~ public String infoCuentas(Registro_Cbytes registro, RandomAccessFile raf) throws IOException{
		//~ String texto = "";
		//~ Cuenta temp = new Cuenta();
		//~ raf.seek(raf.getFilePointer()-(5*temp.length()));
		//~ System.out.println("[ARCHIVO - infocuentas] El apuntador antes de entrar a for " + 
							//~ raf.getFilePointer());
		//~ System.out.println("[ARCHIVO - infocuentas] Tamaño de cuentas en total " + 
							//~ (5*temp.length()));
			//~ for(int i = 0; i<5;i++) {
				//~ if(temp!=null) {
					//~ try {
						//~ System.out.println("[ARCHIVO - infocuentas] El apuntador está en " + 
						//~ raf.getFilePointer());
						//~ temp.read(raf);
						//~ texto = texto + temp.getNumero() + ", "
						//~ + temp.getNombre() + ", "
						//~ + temp.getSaldo() + ", "
						//~ + temp.getEliminado() + "\n";
					//~ } catch (Exception e) {
						//~ e.printStackTrace();
					//~ }
				//~ } else {
					//~ raf.seek(raf.getFilePointer()+temp.length());
				//~ }
			//~ }
		//~ return texto;
	//~ }
    
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
			System.out.println("[ARCHIVO - insertarEn] Entramos a for, tamReg: " + tamReg);
			//~ if(temp.getEliminado().equals("y")) {
				//~ int s = tams.size();
				//~ x = i-(tams.get(s-1)); //Apuntador a inicio de registro marcado eliminado...
				//~ i = raf.length();
				//~ //hayEliminados = true;
				//~ //System.out.println("Hay registros eliminados!");
			//~ }
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
					System.out.println("[ARCHIVO - insertar] ENTRADA A MEGA-FOR");
					Registro_Cbytes temp = new Registro_Cbytes();
					System.out.println("[ARCHIVO - insertarEn] Apuntador antes de primera lectura " 
										+ raf.getFilePointer());
					System.out.println("[ARCHIVO - insertarEn] Tamaño de registro leido anteriormente " 
										+ tamReg + " y tamaño total " + raf.length() + " apuntador "
										+ raf.getFilePointer() + " tamaño de i " + i);
					System.out.println("[ARCHIVO - insertar] Tamaño de Array (Long) " +
										tams.size());
					System.out.println("[ARCHIVO - insertar] Tamaño de registro " +
										tams.get((int)cont));
					if(i!=0) {
						raf.seek(i-(tams.get((int)cont))); 
					} else {
						raf.seek(0);
					}
					temp.read( raf );
					System.out.println("[ARCHIVO - insertarEn] Apuntador antes de escritura " + raf.getFilePointer());
					if(i!=0) {
						raf.seek((i-(tams.get((int)cont)))+registro.length());
					} else {
						raf.seek(registro.length());
					}
					temp.write( raf );
					i = (i-((tams.get((int)cont))))-registro.length();
					System.out.println("[ARCHIVO - insertarEn] i al finalizar for " + i);
					cont--;
				}
				System.out.println("[ARCHIVO - insertar] SALIDA DE MEGA-FOR");
			}
			raf.seek( p );   // inserta el nuevo registro
			registro.write( raf );
		}
	}
	
	public void eliminarSuc(String suc) throws IOException {    //Marcar y usar despues
		System.out.println("[ARCHIVO - eliminar] se inicia el método con el argumento: " + suc);
		raf.seek(0);
		for(long i =0; i<raf.length();) {
			Registro_Cbytes temp1 = new Registro_Cbytes();
			long x = temp1.read(raf);
			long point = raf.getFilePointer();
			System.out.println("[ARCHIVO - eliminar] la sucursal es: " 
								+ temp1.getSucursal() + " y buscamos " + suc);
			if(temp1.getSucursal().trim().equals(suc) && !temp1.getEliminado().equals("y")) {
				raf.seek(point-x);
				System.out.println("[ARCHIVO - eliminar] entramos en la condición con la sucursal "
									+ temp1.getSucursal());
				temp1.erase(raf);
				i = raf.length();
			}
		}
	}
	
	public void eliminarNum(int numCuenta) throws IOException {    //Marcar y usar despues
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
	
}
