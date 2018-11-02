package grafica.ventanas;

<<<<<<< HEAD
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

=======
import javax.swing.DefaultDesktopManager;
import javax.swing.DefaultListModel;
>>>>>>> origin/master
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
<<<<<<< HEAD

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.SystemColor;

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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
<<<<<<< HEAD

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
>>>>>>> origin/master

public class FAbFolio extends JInternalFrame {
	private static JInternalFrame frame;
	private JTextField textField;
	private JTable tablaAlumnos;
	private ControladorFolio controlador = new ControladorFolio(this);
	DefaultTableModel modelo;
	/**
	 * Launch the application.
	 */
	
	private static FAbFolio f = null;
   
    //singleton
    public static FAbFolio getInstancia() {
        if (f == null) {
        	FAbFolio.f = new FAbFolio();
        }
        return FAbFolio.f;
    }

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//JinternalFrame frame = new JinternalFrame();
					frame = new JInternalFrame("frame", false, false, false, false);
					frame.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
					frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
					frame.setVisible(true);
					//frame.getContentPane().setLayout(null);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	private FAbFolio() {
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		getContentPane().setLayout(null);
		
<<<<<<< HEAD
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
=======
		JPanel panel = new JPanel();
		panel.setBounds(10, 0, 730, 607);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(304, 197, 89, 23);
		panel.add(btnBorrar);
>>>>>>> origin/master
		
		JButton button = new JButton("New button");
		button.setBounds(426, 197, 89, 23);
		panel.add(button);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
				VoFolio folio = new VoFolio("codigo", "caratula", 1);
				controlador.agregarFolio(folio);
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
		btnAgregar.setBounds(190, 197, 89, 23);
		panel.add(btnAgregar);
		//setBounds(0, 0, 740, 573);
		setBounds(289, 0, 756, 636);
		//desk.setBounds(0, 0, 740, 573);
		
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
		
	}
	public void imprimirVentana(String msg) {
		
		JOptionPane.showMessageDialog (frame, msg);
	}
	public void listarFolios () {
		
		LinkedList<VoFolio> listado = new LinkedList<VoFolio>();
		
		listado = controlador.listarFolios();
		modelo.setRowCount(0);
		
		for (VoFolio folio : listado) { 
		    Object[] fila = new Object[3]; 
		    fila[0] = folio.getCodigo(); 
		    fila[1] = folio.getCaratula(); 

  		    modelo.addRow(fila); 
		}
		
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
