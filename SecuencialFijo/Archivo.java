import java.io.*;

public class Archivo {
	private RandomAccessFile raf = null;
  
	public Archivo( RandomAccessFile raf ){
		this.raf = raf;
  	}
  
  /*-----------------------------------------------------------------
  / inserta un registro al inicio del archivo
  /-----------------------------------------------------------------*/
    
	public void insertar( Registro_Sec registro ) throws IOException {
		insertarEn( 0, registro );
	}
    
    /*-----------------------------------------------------------------
    / presenta los registros del archivo
    / si hay un registro marcado lo ignora.
    /-----------------------------------------------------------------*/
    public void imprimirTodo() throws IOException {
		
		Registro_Sec registro = new Registro_Sec();
		int length = (int) (raf.length() / registro.length());
		System.out.println( "Número de registros: " + length );
		raf.seek( 0 );
		for( int i = 0; i < length; i++ ) {
			
			registro.read( raf );
			System.out.println( "( " + registro.getSucursal() + ", "
                                     + registro.getNumero() + ", "
                                     + registro.getNombre() + ", "
                                     + registro.getSaldo() + ", "
                                     + registro.getEliminado() + ")");
//                                     + registro.getPuntero()+ " )");
		}
	}
	
	public void imprimirRegistros() throws IOException {
		int contel = 0;
		Registro_Sec registro = new Registro_Sec();
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
									+ registro.getEliminado() + /*","  
                                                                        + registro.getPuntero() + */")" );
                              //  if(registro.getPuntero() =)
			}
		 }
                
                
		System.out.println( "Número de registros activos: " + (length-contel) );
		System.out.println("--------------------------------------------------------------");
	}
    
    /*-----------------------------------------------------------------
    / Revisa si hay un registro marcado, y si así es, ahí inserta el registro o
    / en su defecto, desplaza registros para insertar un registro en la posición p
    /-----------------------------------------------------------------*/
    
	private void insertarEn( int p, Registro_Sec registro ) throws IOException {
        
		int n = (int) (raf.length() / registro.length());
		int x = 0;
		boolean hayEliminados = false;
		
		for(int i = 0; i<n; i++) {
			Registro_Sec temp = new Registro_Sec();
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
                                Registro_Sec temp = new Registro_Sec();
                                Registro_Sec temp2 = new Registro_Sec();
                                Registro_Sec temp3 = new Registro_Sec();
                                
                                raf.seek(i* temp.length());
                                temp.read(raf);
                                raf.seek((i+1)* temp.length());
                                temp2.read(raf);
                                
                                if(temp.getNumero()>temp2.getNumero()) 
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
   
			
		}//end else
	}
        
        
	
	public void eliminar(int p) throws IOException {    //Marcar y usar despues
		System.out.println("[ARCHIVO - eliminar] se inicia el método con el argumento: " + p);
		Registro_Sec temp = new Registro_Sec();
		int n = (int) (raf.length() / temp.length());
		System.out.println("[ARCHIVO - eliminar] n es: " + n);
		raf.seek(0);
		for(int i =0; i<n;i++) {
			Registro_Sec temp1 = new Registro_Sec();
			//~ raf.seek(i*temp.length());
			temp1.read(raf);
			System.out.println("[ARCHIVO - eliminar] el número de registro es: " 
								+ temp1.getNumero() + " y buscamos " + p);
			if(p==temp1.getNumero() && !temp1.getEliminado().equals("y")) {
				raf.seek(i*temp1.length());
				System.out.println("[ARCHIVO - eliminar] " + p + " " + temp1.getNumero());
				System.out.println("[ARCHIVO - eliminar] entramos en la condición con el número "
									+ temp1.getNumero());
				temp1.erase(raf);
				i = n;
			}
		}
	}
  
}
