package grafica.ventanas;

import grafica.controladores.ControladorAltaFolio;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class FAltaFolio extends Ventana {
	private JFrame frame;
	private JTextField textCodigoFolio;
	private JTextField textDescripcion;
	private JTextField textPaginas;
	
	/* Constructor de la ventana */
	public FAltaFolio() {
		//llamo el constructor de la clase Ventana para que me inicialize la controladora
		super();
		initialize();
	}
	
	/* Inicializo los componentes de la ventana */
	private void initialize(){
		/* marco de la ventana secundaria */
		frame = new JFrame();
		frame.setResizable(false);
		frame.setSize(new Dimension(300, 322));
		//frame.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagenes\\icon.png"));
		frame.setTitle("Ingreso Folio");
		

		
		
		/* cuando intenten cerrarme, solamente me cierro yo */
		frame.getContentPane().setLayout(null);
		WindowAdapter manFrame = (new WindowAdapter(){
			public void windowClosing (WindowEvent arg0){ 
				setVentanaAbierta(null);
				setVisible(false); // cierro el frame
				
				}
		});
		frame.addWindowListener(manFrame);
		
		JLabel lblFolioCodigo;
		lblFolioCodigo = new JLabel("Codigo: ");
		lblFolioCodigo.setBounds(10, 52, 110, 25);
		frame.getContentPane().add(lblFolioCodigo);
		
		JLabel lblFolioTitulo = new JLabel("Folio");
		lblFolioTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFolioTitulo.setBounds(10, 24, 69, 25);
		frame.getContentPane().add(lblFolioTitulo);
		
		JButton btnGuardar = new JButton("Guardar");
		
		btnGuardar.setBounds(25, 183, 89, 23);
		frame.getContentPane().add(btnGuardar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				setVentanaAbierta(null);
			}
		});
		btnVolver.setBounds(123, 183, 89, 23);
		frame.getContentPane().add(btnVolver);
		
		
		textCodigoFolio = new JTextField();
		textCodigoFolio.setBounds(144, 54, 86, 20);
		frame.getContentPane().add(textCodigoFolio);
		textCodigoFolio.setColumns(10);
		
		textDescripcion = new JTextField();
		textDescripcion.setBounds(144, 90, 86, 20);
		frame.getContentPane().add(textDescripcion);
		textDescripcion.setColumns(10);
		
		textPaginas = new JTextField();
		textPaginas.setBounds(144, 124, 86, 20);
		textPaginas.addKeyListener(new KeyAdapter() {
			@Override
		//Controlo que en el campo Paginas solo me ingresen numeros enteros
			public void keyTyped(KeyEvent arg0) {
				char ch = arg0.getKeyChar();
				if (!esEntero(ch)){
					arg0.consume();
				}
			}
		});
		
		frame.getContentPane().add(textPaginas);
		textPaginas.setColumns(10);
		
		JLabel lblPaginas = new JLabel("P\u00E1ginas: ");
		lblPaginas.setBounds(10, 127, 104, 14);
		frame.getContentPane().add(lblPaginas);
		
		JLabel lblCaraptula = new JLabel("Caraptula: ");
		lblCaraptula.setBounds(10, 93, 69, 14);
		frame.getContentPane().add(lblCaraptula);

		
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//codigo de lo que hace el boton INGRESAR
				try{
					//Obtengo los datos de la pantalla.
					String codigoFolio = textCodigoFolio.getText().trim();
					String Descripcion = textDescripcion.getText().trim();
					
					if(!codigoFolio.isEmpty()){	
						 if (!Descripcion.isEmpty()){
							 if (!Descripcion.isEmpty()){
								 //Se los mando a la controladora para que me revise que los valores sean correctos.
								 int Paginas = Integer.parseInt(textPaginas.getText());
								 ControladorAltaFolio c;
								 
								 c = new ControladorAltaFolio((FAltaFolio)getVentanaAbierta());
								 c.altaFolio(codigoFolio, Descripcion, Paginas);
								 
							 }else{
								 mostrarError("La cantidad de páginas esta vacia",0);
							 }
						 }else{
							 mostrarError("La caraptula del Folio esta vacia",0);
						 }
					}else{
						mostrarError("El Codigo del Folio esta vacio",0);
					}
				} catch (Exception e) {
					mostrarError("Error en la ventana", 0);
				}
			}
		});
		
		
		
		
	}

	/* Indico si deseo que la ventana sea visible o no */
	public void setVisible (boolean visible) {
		frame.setVisible(visible);
	}
	@Override
	public void toFront(){
		frame.toFront();
	}
}


	
