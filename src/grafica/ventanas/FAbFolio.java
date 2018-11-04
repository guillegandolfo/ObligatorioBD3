package grafica.ventanas;

<<<<<<< HEAD
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.DefaultListModel;

import java.awt.Font;

=======
import javax.swing.DefaultDesktopManager;
import javax.swing.DefaultListModel;
>>>>>>> origin/master
import javax.swing.JButton;
import javax.swing.JDesktopPane;
<<<<<<< HEAD

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

=======
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
>>>>>>> origin/master
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
<<<<<<< HEAD

import java.awt.Dimension;

import javax.swing.border.MatteBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class FAbFolio extends Ventana{

	private JFrame frame;
=======
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.ControladorFolio;
import logica.Fachada;
import logica.excepciones.ConfiguracionException;
import logica.vo.VoFolio;
import persistencia.daos.DAOFolios;
import persistencia.daos.IDAOFolios;
import persistencia.poolConexiones.IPoolConexiones;
import persistencia.poolConexiones.PoolConexiones;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.beans.PropertyChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FAbFolio extends JInternalFrame {
	private static JInternalFrame frame;
>>>>>>> origin/master
	private JTextField textField;
	DefaultListModel<?> model;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FAbFolio window = new FAbFolio();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FAbFolio() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(70, 130, 180));
		frame.setBounds(100, 100, 1045, 636);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);	
		
<<<<<<< HEAD
		textField = new JTextField();
		frame.getContentPane().add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		final JDesktopPane panel = new JDesktopPane();
		panel.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 14));
		panel.setBorder(null);
		panel.setBackground(new Color(230, 230, 250));
		panel.setBounds(289, 24, 740, 573);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JList<Object> list = new JList<Object>();
		list.setBounds(178, 175, 423, 343);
		panel.add(list);
		
		/**/
		/*jPanelConFondo jPanelConFondo = new jPanelConFondo();						
		jPanelConFondo.setImagen(Toolkit.getDefaultToolkit().getImage(FPrincipal.class.getResource("/Grafica/imagenes/cheffLogo.jpg")));
		jPanelConFondo.setBounds(21, 153, 288, 296);
		frame.getContentPane().add(jPanelConFondo);*/	
		
		/* cuando intenten cerrarme, solamente me cierro yo */
		frame.getContentPane().setLayout(null);
		WindowAdapter manFrame = (new WindowAdapter(){
			public void windowClosing (WindowEvent arg0){ 
				setVentanaAbierta(null);
				setVisible(false); // cierro el frame
				
				}
		});
		frame.addWindowListener(manFrame);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 291, 597);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Estudio");
		lblNewLabel.setFont(new Font("MingLiU_HKSCS-ExtB", Font.PLAIN, 30));
		lblNewLabel.setBounds(104, 46, 115, 35);
		panel_1.add(lblNewLabel);
		
		JLabel lblJurdico = new JLabel("Jur\u00EDdico");
		lblJurdico.setFont(new Font("MingLiU_HKSCS-ExtB", Font.BOLD, 40));
		lblJurdico.setBounds(46, 92, 191, 46);
		panel_1.add(lblJurdico);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				setVentanaAbierta(null);
			}
		});
		btnSalir.setBounds(130, 563, 131, 23);
		panel_1.add(btnSalir);
		btnSalir.setForeground(Color.BLACK);
=======
		JPanel panel = new JPanel();
		panel.setBounds(10, 0, 730, 607);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(304, 197, 89, 23);
		panel.add(btnBorrar);
		
		JButton button = new JButton("New button");
		button.setBounds(426, 197, 89, 23);
		panel.add(button);
>>>>>>> origin/master
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
				
=======
				VoFolio folio = new VoFolio("codigo6", "caratula", 1);
				Boolean ok = controlador.agregarFolio(folio);
				
				if (ok){
					Object[] fila = new Object[3]; 
				    fila[0] = folio.getCodigo(); 
				    fila[1] = folio.getCaratula(); 
				    fila[2] = folio.getPaginas();			             
				    modelo.addRow(fila);
				}
>>>>>>> origin/master
			}
		});
		btnAgregar.setForeground(Color.BLACK);
		btnAgregar.setBounds(71, 204, 131, 23);
		panel_1.add(btnAgregar);
		
<<<<<<< HEAD
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnBorrar.setForeground(Color.BLACK);
		btnBorrar.setBounds(71, 263, 131, 23);
		panel_1.add(btnBorrar);
=======
		String[] columns = {"Codigo","Caratula","Paginas"};
		modelo = new DefaultTableModel(columns,0); //0 es la cantidad de rows
		this.listarFolios();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 234, 696, 362);
		panel.add(scrollPane);
		
		//Creo la JTable
		JTable tablaFolios = new JTable(modelo);
		tablaFolios.setModel(modelo);
		scrollPane.setViewportView(tablaFolios);
		tablaFolios.setLayout(null);
		scrollPane.setViewportView(tablaFolios);
		tablaFolios.setModel(modelo);
		tablaFolios.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		inicialize();
		

	}
	private void inicialize() {
>>>>>>> origin/master
		
		JSeparator separator = new JSeparator();
		separator.setBounds(286, 0, 5, 597);
		panel_1.add(separator);
		separator.setBorder(new MatteBorder(5, 5, 5, 5, (Color) new Color(119, 136, 153)));
		separator.setPreferredSize(new Dimension(0, 1));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(new Color(70, 130, 180));
		separator.setBackground(new Color(70, 130, 180));
		separator.setLayout(null);
		//jPanelConFondo.setVisible(true);
		/**/
	}
	
	public void imprimirVentana(String msg) {
		
		JOptionPane.showMessageDialog (frame, msg);
	}

<<<<<<< HEAD
	public void setVisible(boolean isVisible) {
		// TODO Auto-generated method stub
		this.frame.setVisible(isVisible);
=======
  		    modelo.addRow(fila); 
		}
		
>>>>>>> origin/master
	}
}
