package grafica.ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
<<<<<<< HEAD
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class FAltaRevision extends Ventana {
	private JFrame frame;
	private JTextField textCodigoFolio;
	private JTextField textDescripcion;
	
	/* Constructor de la ventana */
	public FAltaRevision() {
		//llamo el constructor de la clase Ventana para que me inicialize la controladora
		super();
		initialize();
	}
	
	/* Inicializo los componentes de la ventana */
	private void initialize(){
		/* marco de la ventana secundaria */
		frame = new JFrame();
		frame.setResizable(false);
		frame.setSize(new Dimension(419, 322));
		//frame.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagenes\\icon.png"));
		frame.setTitle("Ingreso Revision");
		

		
		
		/* cuando intenten cerrarme, solamente me cierro yo */
		frame.getContentPane().setLayout(null);
		WindowAdapter manFrame = (new WindowAdapter(){
			public void windowClosing (WindowEvent arg0){ 
				setVentanaAbierta(null);
				setVisible(false); // cierro el frame
				
				}
		});
		frame.addWindowListener(manFrame);
		
		JLabel lblFolio = new JLabel("Folio: ");
		lblFolio.setBounds(10, 39, 38, 25);
		frame.getContentPane().add(lblFolio);
		
		JLabel lblRevision = new JLabel("Revision");
		lblRevision.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRevision.setBounds(10, 125, 69, 25);
		frame.getContentPane().add(lblRevision);
		
		JButton btnGuardar = new JButton("Guardar");
		
		btnGuardar.setBounds(99, 220, 89, 23);
		frame.getContentPane().add(btnGuardar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				setVentanaAbierta(null);
			}
		});
		btnVolver.setBounds(197, 220, 89, 23);
		frame.getContentPane().add(btnVolver);
		

		final JLabel lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setBounds(10, 151, 69, 25);
		frame.getContentPane().add(lblDescripcion);
		
		
		textCodigoFolio = new JTextField();
		textCodigoFolio.setBounds(58, 41, 86, 20);
		frame.getContentPane().add(textCodigoFolio);
		textCodigoFolio.setColumns(10);
		
		textDescripcion = new JTextField();
		textDescripcion.setBounds(75, 153, 86, 20);
		frame.getContentPane().add(textDescripcion);
		textDescripcion.setColumns(10);
		lblDescripcion.setVisible(false);

		
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//codigo de lo que hace el boton INGRESAR
				try{
					//Obtengo los datos de la pantalla.
					String codigoFolio = textCodigoFolio.getText().trim();
					String Descripcion = textDescripcion.getText().trim();
					if(!codigoFolio.isEmpty()){	
						 if (!Descripcion.isEmpty()){
							//Se los mando a la controladora para que me revise que los valores sean correctos.
							 System.out.println("1");
							 ControladorAltaRevision c;
							 System.out.println("2");
						
							 c = new ControladorAltaRevision((FAltaRevision)getVentanaAbierta());
							 System.out.println("3");
							 c.altaRevision(codigoFolio, Descripcion);
							 System.out.println("4");
						 }else{
							 mostrarError("La descripcion de la Revision esta vacia",0);
						 }
					}else{
						mostrarError("El Codigo del Folio esta vacio",0);
					}
=======
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FAltaRevision extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FAltaRevision frame = new FAltaRevision();
					frame.setVisible(true);
>>>>>>> origin/master
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FAltaRevision() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
