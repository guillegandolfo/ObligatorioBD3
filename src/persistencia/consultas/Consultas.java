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
	
	public String eliminarFolio(){
		return "delete from Juridico.Folios where codigo = (?)";
	}
	
	public String eliminarRevisiones(){
		return "delete from Juridico.Revisiones where codigoFolio = (?)";
	}
	
	public String getDescripcion(){
		return "select descripcion from Juridico.Revisiones where codigoFolio = (?) and numero = (?)";
	}

	public String listarFolios(){
		return "select * from Juridico.Folios order by codigo";
	}
	
	public String listarRevisiones(){
		return "select * from Juridico.Revisiones where codigoFolio = (?) order by numero";
	}
	
	public String LargoRevisiones() {
	//	String query = "select max(numero) from Juridico.Revisiones where codigoFolio = (?)";
		return query;
	}
	
}
