package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JDesktopPane;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.SystemColor;

import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.ControladorFolio;
import logica.vo.VoFolio;

import javax.swing.JList;
import javax.swing.JOptionPane;

import vistaGrafica.ventanas.Ventana;

import com.sun.glass.events.WindowEvent;

public class FAbFolio extends Ventana{

	private JFrame frame;
	private JTextField textField;
	private JTable tablaAlumnos;
	private ControladorFolio controlador = new ControladorFolio(this);
	DefaultListModel model;

	/**
	 * Launch the application.
	 */
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
		
		textField = new JTextField();
		frame.getContentPane().add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		/* cuando intenten cerrarme, solamente me cierro yo */
		frame.getContentPane().setLayout(null);
		WindowAdapter manFrame = (new WindowAdapter(){
			public void windowClosing (WindowEvent arg0){ 
				setVentanaAbierta(null);
				setVisible(false); // cierro el frame
				
				}
		});
		
		final JDesktopPane panel = new JDesktopPane();
		panel.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 14));
		panel.setBorder(null);
		panel.setBackground(new Color(230, 230, 250));
		panel.setBounds(289, 24, 740, 573);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		

		String[] columns = {"Codigo","Caratula","Paginas"};
		DefaultTableModel modelo = new DefaultTableModel(columns,0); //0 es la cantidad de rows
		this.listarFolios(modelo);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 35, 673, 497);
		panel.add(scrollPane);
		
		//Creo la JTable
		JTable tablaFolios = new JTable(modelo);
		tablaFolios.setModel(modelo);
		scrollPane.setViewportView(tablaFolios);
		tablaFolios.setLayout(null);
		scrollPane.setViewportView(tablaFolios);
		tablaFolios.setModel(modelo);
		tablaFolios.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		/**/
		/*jPanelConFondo jPanelConFondo = new jPanelConFondo();						
		jPanelConFondo.setImagen(Toolkit.getDefaultToolkit().getImage(FPrincipal.class.getResource("/Grafica/imagenes/cheffLogo.jpg")));
		jPanelConFondo.setBounds(21, 153, 288, 296);
		frame.getContentPane().add(jPanelConFondo);*/	
		
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
		btnSalir.setBounds(130, 563, 131, 23);
		panel_1.add(btnSalir);
		btnSalir.setForeground(Color.BLACK);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VoFolio folio = new VoFolio("codigo", "caratula", 1);
				controlador.agregarFolio(folio);
			}
		});
		btnAgregar.setForeground(Color.BLACK);
		btnAgregar.setBounds(71, 204, 131, 23);
		panel_1.add(btnAgregar);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnBorrar.setForeground(Color.BLACK);
		btnBorrar.setBounds(71, 263, 131, 23);
		panel_1.add(btnBorrar);
		
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

	public void setVisible(boolean isVisible) {
		// TODO Auto-generated method stub
		this.frame.setVisible(isVisible);
	}
	
	private void listarFolios (DefaultTableModel modelo) {
		
		LinkedList<VoFolio> listado = new LinkedList<VoFolio>();
		
		listado = controlador.listarFolios();
		modelo.setRowCount(0);
		
		for (VoFolio folio : listado) { 
		    Object[] fila = new Object[3]; 
		    fila[0] = folio.getCodigo(); 
		    fila[1] = folio.getCaratula(); 
		    fila[2] = folio.getPaginas();
		   
		             
		    modelo.addRow(fila); 
		}
		
	}
}
