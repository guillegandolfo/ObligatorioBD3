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
import grafica.controladores.ControladorRevision;
import logica.Fachada;
import logica.excepciones.ConfiguracionException;
import logica.vo.VOFolio;
import logica.vo.VORevision;
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
import javax.swing.JSpinner;

public class FAltaRevision extends JInternalFrame {
	private static JInternalFrame frame;
	private JTextField textField;
	private JTable tablaRevisiones;
	private ControladorRevision controlador = new ControladorRevision(this);
	DefaultTableModel modelo;
	/**
	 * Launch the application.
	 */
	
	private static FAltaRevision f = null;
	private JTextField txtCodigo;
	private JTextField txtCaratula;
   
    //singleton
    public static FAltaRevision getInstancia() {
        if (f == null) {
        	FAltaRevision.f = new FAltaRevision();
        }
        return FAltaRevision.f;
    }

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				frame = new JInternalFrame("frame", false, false, false, false);
				frame.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
				frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
				frame.setVisible(true);			
			
			}
		});
	}

	/**
	 * Create the frame.
	 */
	private FAltaRevision() {
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 756, 636);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		String[] columns = {"Codigo Folio","Descripcion","Numero"};
		modelo = new DefaultTableModel(columns,0); //0 es la cantidad de rows
		//this.listarRevisiones();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 234, 696, 362);
		panel.add(scrollPane);
		
		//Creo la JTable
		final JTable tablaFolios = new JTable(modelo);
		tablaFolios.setModel(modelo);
		scrollPane.setViewportView(tablaFolios);
		tablaFolios.setLayout(null);
		scrollPane.setViewportView(tablaFolios);
		tablaFolios.setModel(modelo);
		tablaFolios.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(204, 53, 295, 20);
		panel.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtCaratula = new JTextField();
		txtCaratula.setColumns(10);
		txtCaratula.setBounds(204, 84, 295, 20);
		panel.add(txtCaratula);
		
		JLabel lblCodigo = new JLabel("Codigo Folio");
		lblCodigo.setBounds(112, 56, 82, 14);
		panel.add(lblCodigo);
		
		JLabel lblCaratula = new JLabel("Descripcion");
		lblCaratula.setBounds(112, 90, 82, 14);
		panel.add(lblCaratula);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String codigoFolio = txtCodigo.getText();
				String descripcion = txtCaratula.getText();
			
				if (codigoFolio.equals("") && descripcion.equals("")) {
					imprimirVentana("Ingrese todos los datos necesarios para una revision.");
				} else {
					int numero = controlador.altaRevision(codigoFolio, descripcion);
					
					if (numero > 0) {
						Object[] fila = new Object[3]; 
					    fila[0] = codigoFolio;
					    fila[1] = descripcion;
					    fila[2] = numero;
					    modelo.addRow(fila);
					}
				}
				
			}
		});
		
		btnAgregar.setBounds(167, 157, 177, 23);
		panel.add(btnAgregar);
		
		JButton btnBuscarRevisionesFolio = new JButton("Buscar Revisiones Folio");
		btnBuscarRevisionesFolio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String codigoFolio = txtCodigo.getText();
			
				if (codigoFolio.equals("")) {
					imprimirVentana("Ingrese codigo folio para realizar la busqueda.");
				} else {
					LinkedList<VORevision> listado = new LinkedList<VORevision>();
					
					listado = controlador.listarRevisiones(codigoFolio);
					modelo.setRowCount(0);
					
					for (VORevision revision : listado) { 
					    Object[] fila = new Object[3]; 
					    fila[0] = revision.getCodigoFolio(); 
					    fila[1] = revision.getDescripcion(); 
					    fila[2] = revision.getNumero(); 

			  		    modelo.addRow(fila); 
					}
				}
			}
		});
		btnBuscarRevisionesFolio.setBounds(373, 157, 163, 23);
		panel.add(btnBuscarRevisionesFolio);
	
		setBounds(0, 0, 756, 636);
	
		inicialize();
		

	}
	private void inicialize() {
		
	}
	
	public void imprimirVentana(String msg) {
		
		JOptionPane.showMessageDialog (frame, msg);
	}
	
	public void listarRevisiones (String codFolio) {
		
		LinkedList<VORevision> listado = new LinkedList<VORevision>();
		
		listado = controlador.listarRevisiones(codFolio);
		modelo.setRowCount(0);
		
		for (VORevision revision : listado) { 
		    Object[] fila = new Object[3]; 
		    fila[0] = revision.getCodigoFolio(); 
		    fila[1] = revision.getDescripcion(); 
		    fila[2] = revision.getNumero(); 
		    
  		    modelo.addRow(fila); 
		}
		
	}
}
