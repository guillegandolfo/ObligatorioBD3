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
		return "insert into Juridico.Revisiones Values(?,?,?)";
	}	
	
	public String insertarFolio(){
		return "INSERT INTO Juridico.Folios VALUES (?,?,?)";
	}
	
	/*public String eliminarFolio(){
		return "delete from Juridico.Folios where codigo = (?)";
	}*/
	public String eliminarFolio() {
		//Marcar en BD para borrar revisiones en cascada
		return "delete from Juridico.Folios where codigo = (?)";
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
		return "SELECT f.codigo, f.caratula, f.paginas, count(*) cantidad" + 
				"	FROM juridico.folios f" + 
				"	inner join juridico.revisiones r" + 
				"	on f.codigo = r.codigoFolio" + 
				"	group by f.codigo ,f.caratula,f.paginas" + 
				"	order by 4 desc" + 
				"	limit 1";
	}
	
	public String LargoRevisiones() {
		String query = "select max(numero) from Juridico.Revisiones where codigoFolio = (?)";
		
		return query;
	}
	
	public String existeRevision(){
		return "select * from Juridico.Revisiones where codigoFolio = (?) and numero = (?)";
	}
}
