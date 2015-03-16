/******************************************************************
/  clase Archivo
/
/  autor: Dr. José Luis Zechinelli Martini

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
	public boolean existeRegistro(String sucursal) throws IOException {
		String sucursal1 = sucursal.toUpperCase();
		boolean existe = false;
		Registro_Cbytes registro = new Registro_Cbytes();
		int length = (int) (raf.length() / registro.length());
		raf.seek( 0 );
		for( int i = 0; i < length; i++ ){
			registro.read( raf );
			System.out.println("[ARCHIVO - existeRegistro] " + registro.getSucursal() + ". es igual a " 
								+ sucursal1 + ". ?");
			System.out.println(" '"+registro.getSucursal()+"' is '" + sucursal1 + "'");
			if(registro.getSucursal().trim().equals(sucursal1)){
				//System.out.println((i+1)+". " + "Registro marcado para eliminar");
				System.out.println("[ARCHIVO - existeRegistro] Sí es igual...");
				existe = true;
				i = length;
			}
		 }
		 return existe;
	}
	
	public void insertarCuenta(String sucursal, Cuenta cuenta) throws IOException{
		Registro_Cbytes registro = new Registro_Cbytes();
		int length = (int) (raf.length() / registro.length());
		raf.seek( 0 );
		for( int i = 0; i < 5; i++ ){
			registro.read( raf );
			if(registro.getSucursal().trim().equals(sucursal)){
				//System.out.println((i+1)+". " + "Registro marcado para eliminar");
				raf.seek(raf.getFilePointer()-(5*cuenta.length()));
				i = length;
				registro.crearCuenta(cuenta, raf);
			}
		 }
	}
	
	public void insertar( Registro_Cbytes registro ) throws IOException {
		insertarEn( 0, registro );
	}
    
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
		Registro_Cbytes registro = new Registro_Cbytes();
		int length = (int) (raf.length() / registro.length());
		raf.seek( 0 );
		System.out.println("[ARCHIVO - imprimirregistro] Actualmente el número de registros es: " + length);
		System.out.println("[ARCHIVO - imprimirregistro] Tamaño de registro: " + registro.length());
		
		for( int i = 0; i < length; i++ ){
			System.out.println("[ARCHIVO - imprimirregistro] Apuntador antes de leer siguiente registro: " 
								+ raf.getFilePointer());
			registro.read( raf );
			if(registro.getEliminado().equals("y")){
				//System.out.println((i+1)+". " + "Registro marcado para eliminar");
				contel++;
			}else{
				System.out.println( "( " + registro.getSucursal() + ", "
									+ infoCuentas(registro, raf) + " )");
			}
		 }
		System.out.println( "Número de registros activos: " + (length-contel) );
		System.out.println("--------------------------------------------------------------");
	}
	
	public String infoCuentas(Registro_Cbytes registro, RandomAccessFile raf) throws IOException{
		String texto = "";
		Cuenta temp = new Cuenta();
		raf.seek(raf.getFilePointer()-(5*temp.length()));
		System.out.println("[ARCHIVO - infocuentas] El apuntador antes de entrar a for " + 
							raf.getFilePointer());
		System.out.println("[ARCHIVO - infocuentas] Tamaño de cuentas en total " + 
							(5*temp.length()));
			for(int i = 0; i<5;i++) {
				if(temp!=null) {
					try {
						System.out.println("[ARCHIVO - infocuentas] El apuntador está en " + 
						raf.getFilePointer());
						temp.read(raf);
						texto = texto + temp.getNumero() + ", "
						+ temp.getNombre() + ", "
						+ temp.getSaldo() + ", "
						+ temp.getEliminado() + "\n";
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					raf.seek(raf.getFilePointer()+temp.length());
				}
			}
		return texto;
	}
    
    /*-----------------------------------------------------------------
    / Revisa si hay un registro marcado, y si así es, ahí inserta el registro o
    / en su defecto, desplaza registros para insertar un registro en la posición p
    /-----------------------------------------------------------------*/
    
	private void insertarEn( int p, Registro_Cbytes registro ) throws IOException {
        
		int n = (int) (raf.length() / registro.length());
		int x = 0;
		boolean hayEliminados = false;
		System.out.println("[ARCHIVO - insertarEn] Número de registros actuales: " + n);
		Cuenta cuenta = new Cuenta();
		
		for(int i = 0; i<n; i++) {
			Registro_Cbytes temp = new Registro_Cbytes();
			raf.seek(i* temp.length());
			temp.read(raf);
			if(temp.getEliminado().equals("y")) {
				x = i;
				i = n;
				hayEliminados = true;
				System.out.println("Hay registros eliminados!");
			}
		}
		
		if(hayEliminados) {
			System.out.println("Efectivamente hay registros eliminados!");
			raf.seek( x * registro.length() );   // inserta el nuevo registro
			registro.write( raf );
		} else {
			for( int i = n-1; i >= p; i -- ) {    // desplazamiento de registros
				System.out.println("[ARCHIVO - insertarEn] Entrando a for con valor n: " + n);
				Registro_Cbytes temp = new Registro_Cbytes();
				System.out.println("[ARCHIVO - insertarEn] Apuntador antes de primera lectura " + raf.getFilePointer());
				raf.seek( i * temp.length() );
				temp.read( raf );
				//~ raf.seek(raf.getFilePointer()+cuenta.length());
				
				System.out.println("[ARCHIVO - insertarEn] Apuntador antes de movimiento " + raf.getFilePointer());
				raf.seek( (i+1) * temp.length() );
				System.out.println("[ARCHIVO - insertarEn] Apuntador antes de escritura " + raf.getFilePointer());
				temp.write( raf );
			}
			raf.seek( p * registro.length() );   // inserta el nuevo registro
			registro.write( raf );
		}
	}
	
	public void eliminar(String suc) throws IOException {    //Marcar y usar despues
		System.out.println("[ARCHIVO - eliminar] se inicia el método con el argumento: " + suc);
		Registro_Cbytes temp = new Registro_Cbytes();
		int n = (int) (raf.length() / temp.length());
		System.out.println("[ARCHIVO - eliminar] n es: " + n);
		raf.seek(0);
		for(int i =0; i<n;i++) {
			Registro_Cbytes temp1 = new Registro_Cbytes();
			raf.seek(i*temp.length());
			temp1.read(raf);
			System.out.println("[ARCHIVO - eliminar] la sucursal es: " 
								+ temp1.getSucursal() + " y buscamos " + suc);
			if(temp1.getSucursal().trim().equals(suc) && !temp1.getEliminado().equals("y")) {
				raf.seek(i*temp1.length());
				System.out.println("[ARCHIVO - eliminar] entramos en la condición con la sucursal "
									+ temp1.getSucursal());
				temp1.erase(raf);
				i = n;
			}
		}
	}
}
