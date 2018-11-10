package grafica.ventanas;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.ControladorFolio;
import logica.excepciones.PersistenciaException;
import logica.excepciones.ServidorException;
import logica.vo.VoFolio;

import java.util.LinkedList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JSpinner;

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
	private JTextField txtCodigo;
	private JTextField txtCaratula;
   
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
	 * @throws PersistenciaException 
	 * @throws ServidorException 
	 */
	private FAbFolio()  {
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 0, 730, 607);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		String[] columns = {"Codigo","Caratula","Paginas"};
		modelo = new DefaultTableModel(columns,0); //0 es la cantidad de rows
		this.listarFolios();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 234, 696, 362);
		panel.add(scrollPane);
		
		//Creo la JTable
		final JTable tablaFolios = new JTable(modelo);
		tablaFolios.setModel(modelo);
		scrollPane.setViewportView(tablaFolios);
		tablaFolios.setLayout(null);
		tablaFolios.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(183, 56, 296, 20);
		panel.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtCaratula = new JTextField();
		txtCaratula.setColumns(10);
		txtCaratula.setBounds(183, 87, 296, 20);
		panel.add(txtCaratula);
		
		JLabel lblCodigo = new JLabel("Codigo");
		lblCodigo.setBounds(53, 59, 87, 14);
		panel.add(lblCodigo);
		
		JLabel lblCaratula = new JLabel("Caratula");
		lblCaratula.setBounds(53, 93, 87, 14);
		panel.add(lblCaratula);
		
		final JSpinner txtPaginas = new JSpinner();
		txtPaginas.setBounds(183, 118, 296, 20);
		panel.add(txtPaginas);
		
		JLabel lblCantPaginas = new JLabel("Cant. Paginas");
		lblCantPaginas.setBounds(53, 124, 99, 14);
		panel.add(lblCantPaginas);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int rowSeleccionada = tablaFolios.getSelectedRow();
				if (rowSeleccionada < 0) {
					imprimirVentana("Seleccione folio a borrar");
				} else {
					//VoFolio voFolio = new VoFolio((String) tablaFolios.getValueAt(rowSeleccionada, 1), (String)tablaFolios.getValueAt(rowSeleccionada, 2), (Integer)tablaFolios.getValueAt(rowSeleccionada, 3));	
					Boolean borrar = controlador.borrarFolio((String) tablaFolios.getValueAt(rowSeleccionada, 0));
					modelo.removeRow(rowSeleccionada);
					if (!borrar) {
						imprimirVentana("Ocurrio un error al borrar el folio");
					}
				}
			}
		});
		btnBorrar.setBounds(340, 186, 139, 23);
		panel.add(btnBorrar);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String codigo = txtCodigo.getText();
				String caratula = txtCaratula.getText();
				Integer paginas = (Integer) txtPaginas.getValue();
			
				if (codigo.equals("") || caratula.equals("") || paginas <= 0 ) {
					imprimirVentana("Ingrese todos los datos necesarios para un folio.");
				} else {
					VoFolio folio = new VoFolio(codigo, caratula, paginas);
					Boolean ok = controlador.agregarFolio(folio);
					
					if (ok){
						Object[] fila = new Object[3]; 
					    fila[0] = folio.getCodigo(); 
					    fila[1] = folio.getCaratula(); 
					    fila[2] = folio.getPaginas();			             
					    modelo.addRow(fila);
					}
				}
				
			}
		});
		
		btnAgregar.setBounds(183, 186, 133, 23);
		panel.add(btnAgregar);
		setBounds(289, 0, 756, 636);
	}
	
	public void imprimirVentana(String msg) {
		
		JOptionPane.showMessageDialog (frame, msg);
	}
	
	public void listarFolios ()  {
		
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
