package persistencia.consultas;



public class Consultas {
	
	public Consultas() {
	}
	
	public String existeFolios(){
		return "select * from Juridico.Folios where codigo = (?)";
	}
	
	public String MaxFolioId(){
		return "select max(numero) from Juridico.Revisiones where codigoFolio = (?)";
	}
	
	public String InsertarRevision(){
		return "insert into Juridico.Revisiones Value('?','?','?')";
	}	
	
	public String insertarFolio(){
		return "INSERT INTO Juridico.Folios VALUES (?,?,?)";
	}
	
	/*public String eliminarFolio(){
		return "delete from Juridico.Folios where codigo = (?)";
	}*/
	public String eliminarFolio() {
		//Marcar en BD para borrar revisiones en cascada
		return "delete from folios where codigo = (?)";
	}	
	
	public String eliminarRevisiones(){
		return "delete from Juridico.Revisiones where codigoFolio = (?)";
	}
	
	public String getDescripcion(){
		return "select descripcion from Juridico.Revisiones where codigoFolio = (?) and numero = (?)";
	}

	public String listarFolios(){
		return "select * from Juridico.Folios order by codigo desc";
	}
	
	public String listarRevisiones(){
		return "select * from Juridico.Revisiones where codigoFolio = (?) order by numero desc";
	}
	public String folioMasRevisado() {
		/*group by codigo*/
		return "SELECT f.codigo,count(*) cantidad" + 
				"	FROM folios f" + 
				"	inner join revisiones r" + 
				"	on f.codigo = r.codigoFolio" + 
				"	group by f.codigo" + 
				"	order by 2 desc" + 
				"	limit 1";
	}
	
	public String LargoRevisiones() {
		String query = "select max(numero) from Juridico.Revisiones where codigoFolio = (?)";
		
		return query;
	}
	
}
