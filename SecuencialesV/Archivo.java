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
			if(registro.getSucursal().trim().equals(sucursal1) && !registro.getEliminado().equals("y")){
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
			if(registro.getSucursal().trim().equals(sucursal) && !registro.getEliminado().equals("y")){
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
		
		for( int i = 0; i < length; i++ ){
			registro.read( raf );
			if(registro.getEliminado().equals("y")){
				//System.out.println((i+1)+". " + "Registro marcado para eliminar");
				contel++;
			}else{
				System.out.println( "( " + registro.getSucursal() +" )");
				System.out.print(infoCuentas(registro, raf) + "\n");
			}
		 }
		System.out.println( "Número de registros activos: " + (length-contel) );
		System.out.println("--------------------------------------------------------------");
	}
	
	public String infoCuentas(Registro_Cbytes registro, RandomAccessFile raf) throws IOException{
		String texto = "";
		Cuenta temp = new Cuenta();
		raf.seek(raf.getFilePointer()-(5*temp.length()));
			for(int i = 0; i<5;i++) {
				if(temp!=null) {
					try {
						temp.read(raf);
						if(temp.getNumero()!=0 && temp.getSaldo()!=0 
						&& !temp.getEliminado().equals("y")) {
							texto = texto + temp.getNumero() + ", "
							+ temp.getNombre() + ", "
							+ temp.getSaldo() + ", "
							+ temp.getEliminado() + "\n";
						}
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
		Cuenta cuenta = new Cuenta();
		
		for(int i = 0; i<n; i++) {
			Registro_Cbytes temp = new Registro_Cbytes();
			raf.seek(i* temp.length());
			temp.read(raf);
			if(temp.getEliminado().equals("y")) {
				x = i;
				i = n;
				hayEliminados = true;
			}
		}
		
		if(hayEliminados) {
			raf.seek( x * registro.length() );   // inserta el nuevo registro
			registro.write( raf );
		} else {
			/*for( int i = n-1; i >= p; i -- ) {    // desplazamiento de registros
				Registro_Cbytes temp = new Registro_Cbytes();
				raf.seek( i * temp.length() );
				temp.read( raf );
				raf.seek( (i+1) * temp.length() );
				temp.write( raf );
			}
			raf.seek( p * registro.length() );   // inserta el nuevo registro
			registro.write( raf );
		*/
                raf.seek( (n) * registro.length() );   // inserta el nuevo registro
			registro.write( raf);
                        if(n>=1)
                        {
                            boolean c=true;
                            while(c)
                            {
                            c=false;
                            for(int i = 0; i<n ; i++)
                            {
                                Registro_Cbytes temp = new Registro_Cbytes();
                                Registro_Cbytes temp2 = new Registro_Cbytes();
                                Registro_Cbytes temp3 = new Registro_Cbytes();
                                
                                raf.seek(i* temp.length());
                                temp.read(raf);
                                raf.seek((i+1)* temp.length());
                                temp2.read(raf);
                                
                                if(temp2.getSucursal().compareTo(temp.getSucursal()) < 0) 
                                {
                                   temp3=temp;
                                   temp=temp2;
                                   temp2=temp3;
                                   
                                   raf.seek(i * temp.length());
                                   temp.write(raf);
                                   
                                   raf.seek((i+1)* temp.length());
                                   temp2.write(raf);
                                   
                                   c=true;
                                   
                                }//end if
                               
                            }// end for
                          } //end while
                        }
                
                
                
                }
	}
	
	public void eliminar(String suc) throws IOException {    //Marcar y usar despues
		Registro_Cbytes temp = new Registro_Cbytes();
		int n = (int) (raf.length() / temp.length());
		raf.seek(0);
		for(int i =0; i<n;i++) {
			Registro_Cbytes temp1 = new Registro_Cbytes();
			raf.seek(i*temp.length());
			temp1.read(raf);
			if(temp1.getSucursal().trim().equals(suc) && !temp1.getEliminado().equals("y")) {
				raf.seek(i*temp1.length());
				temp1.erase(raf);
				i = n;
			}
		}
	}
	
	public boolean eliminarCuenta(String suc, int cuenta) throws IOException {    //Marcar y usar despues
		Registro_Cbytes temp = new Registro_Cbytes();
		int n = (int) (raf.length() / temp.length());
		boolean estaEliminado = false;
		raf.seek(0);
		for(int i =0; i<n;i++) {
			Registro_Cbytes temp1 = new Registro_Cbytes();
			raf.seek(i*temp.length());
			temp1.read(raf);
			if(temp1.getSucursal().trim().equals(suc) && !temp1.getEliminado().equals("y")) {
				raf.seek(i*temp1.length());
				Cuenta[] cuentas = temp1.getCuentas();
				for(int l = 0; l<5; l++) {
					if(cuentas[l].getNumero()==cuenta) {
						cuentas[l].setEliminado();
						temp1.write(raf);
						l=5;
						i = n;
						estaEliminado = true;
					}
				}
			}
		}
		return estaEliminado;
	}
}
