package grafica.ventanas;

import javax.swing.DefaultDesktopManager;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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
	private JTextField textField;
	private JTable tablaAlumnos;
	private ControladorFolio controlador ;
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
		controlador = new ControladorFolio(this);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		getContentPane().setLayout(null);
		
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
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VoFolio folio = new VoFolio("codigo6", "caratula", 1);
				Boolean ok = controlador.agregarFolio(folio);
				
				if (ok){
					Object[] fila = new Object[3]; 
				    fila[0] = folio.getCodigo(); 
				    fila[1] = folio.getCaratula(); 
				    fila[2] = folio.getPaginas();			             
				    modelo.addRow(fila);
				}
			}
		});
		btnAgregar.setBounds(190, 197, 89, 23);
		panel.add(btnAgregar);
		//setBounds(0, 0, 740, 573);
		setBounds(289, 0, 756, 636);
		//desk.setBounds(0, 0, 740, 573);
		
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
}
