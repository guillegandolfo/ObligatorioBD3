package grafica.ventanas;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import grafica.controladores.ControladorFolioMasRevisado;
import logica.excepciones.ConfiguracionException;
import logica.excepciones.ConsultaRevisionException;
import logica.excepciones.LecturaArchivoException;
import logica.vo.VOFolioMaxRev;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSpinner;


public class FolioMasRevisado extends JInternalFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ControladorFolioMasRevisado controlador;
	private static JInternalFrame frame;
	private static FolioMasRevisado Ventana = null;
	private JTable TableMasRevisado;
	DefaultTableModel modelo;	   
	private JTextField textCodigoFolio;
	private JTextField textResultado;
    //singleton
    public static FolioMasRevisado getInstancia() {
        if (Ventana == null) {
        	 Ventana = new FolioMasRevisado();
        }
        return Ventana;
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

    //Contructor
	public FolioMasRevisado() {
		setClosable(true);
		controlador = new ControladorFolioMasRevisado(this);
		setBounds(0, 0, 756, 636);
		getContentPane().setLayout(null);		
		JPanel panel = new JPanel();
		panel.setBounds(10, 30, 716, 200);
		getContentPane().add(panel);
		String[] columns = {"Codigo","Caratula","Paginas","CantRevisiones"};
		//this.GetMasRevisado();
		modelo = new DefaultTableModel(columns,0); //0 es la cantidad de rows
		//this.listarFolios();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 234, 696, 200);
		panel.add(scrollPane);		
		//Creo la JTable
		TableMasRevisado = new JTable(modelo);
		
		TableMasRevisado.setLayout(null);
		scrollPane.setViewportView(TableMasRevisado);
		TableMasRevisado.setModel(modelo);
		TableMasRevisado.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel lblFolioMasRevisado = new JLabel("Folio mas Revisado");
		lblFolioMasRevisado.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblFolioMasRevisado.setBounds(10, 5, 215, 21);
		getContentPane().add(lblFolioMasRevisado);
		
		JLabel lblObtenerDescripcion = new JLabel("Obtener Descripcion");
		lblObtenerDescripcion.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblObtenerDescripcion.setBounds(10, 262, 215, 21);
		getContentPane().add(lblObtenerDescripcion);
		
		textCodigoFolio = new JTextField();
		textCodigoFolio.setBounds(111, 294, 86, 20);
		getContentPane().add(textCodigoFolio);
		textCodigoFolio.setColumns(10);
		
		JLabel lblCodigoFolio = new JLabel("Codigo Folio :");
		lblCodigoFolio.setBounds(36, 297, 83, 14);
		getContentPane().add(lblCodigoFolio);
		
		final JSpinner textNumero = new JSpinner();
		textNumero.setBounds(142, 337, 55, 20);
		getContentPane().add(textNumero);
		
		JButton btnObtenerDescripcion = new JButton("Obtener Descripcion");
		btnObtenerDescripcion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String codigo = textCodigoFolio.getText() ;
				Integer Numero = (Integer) textNumero.getValue();
				
				if (codigo.isEmpty()) {
					imprimirVentana("Debe ingresar un codigo de Folio.");
				} else {
					if (Numero <= 0){
						imprimirVentana("Ingrese un numero valido");
					}else{
						String descripcion;
						try {
							descripcion = controlador.getDescripcion(codigo, Numero);
							textResultado.setText(descripcion);
						} catch (LecturaArchivoException e) {
							imprimirVentana(e.getMessage());
						} catch (ConfiguracionException e) {
							imprimirVentana(e.getMessage());
						} catch (ConsultaRevisionException e) {
							imprimirVentana(e.getMessage());
						}
						
					}
				}
			}
		});
		btnObtenerDescripcion.setBounds(233, 293, 129, 23);
		getContentPane().add(btnObtenerDescripcion);
		
		textResultado = new JTextField();
		textResultado.setEditable(false);
		textResultado.setBounds(240, 337, 86, 20);
		getContentPane().add(textResultado);
		textResultado.setColumns(10);
		
		JLabel lblNumeroDeRevision = new JLabel("Numero de Revision:");
		lblNumeroDeRevision.setBounds(36, 340, 108, 14);
		getContentPane().add(lblNumeroDeRevision);
		
	}
	
	public void imprimirVentana(String msg) {
		
		JOptionPane.showMessageDialog (frame, msg);
	}
	
	//obtiene el VO mas revisado desde el controlador
	public void GetMasRevisado () 
	{		
		VOFolioMaxRev fMasRev  = new VOFolioMaxRev();		
		fMasRev = controlador.getFolioMasRevisado();
		modelo.setRowCount(0);
		if (fMasRev != null) {
			Object[] fila = new Object[4]; 
			fila[0] = fMasRev.getCodigo(); 
		    fila[1] = fMasRev.getCaratula();
			fila[2] = fMasRev.getPaginas();
			fila[3] = fMasRev.getCantRevisiones();
	  		modelo.addRow(fila); 		
		}
	}	
}
