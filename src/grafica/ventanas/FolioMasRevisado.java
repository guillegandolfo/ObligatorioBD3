package grafica.ventanas;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.LinkedList;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import grafica.controladores.ControladorFolioMasRevisado;
import logica.excepciones.Exc_Persistencia;
import logica.vo.VOFolioMaxRev;
import logica.vo.VoFolio;



public class FolioMasRevisado extends JInternalFrame {

	
	private ControladorFolioMasRevisado controlador;
	private static JInternalFrame frame;
	private static FolioMasRevisado Ventana = null;
	private JTable TableMasRevisado;
	DefaultTableModel modelo;	   
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
				try {
					//FolioMasRevisado frame = new FolioMasRevisado();
					frame = new JInternalFrame("frame", false, false, false, false);
					frame.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
					frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    //Contructor
	public FolioMasRevisado() {
		try {
			controlador = new ControladorFolioMasRevisado(this);
		} catch (Exc_Persistencia e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setBounds(100, 100, 752, 633);
		getContentPane().setLayout(null);		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 716, 581);
		getContentPane().add(panel);
		String[] columns = {"Codigo","Caratula","Paginas","CantRevisiones"};
		//this.GetMasRevisado();
		modelo = new DefaultTableModel(columns,0); //0 es la cantidad de rows
		//this.listarFolios();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 234, 696, 362);
		panel.add(scrollPane);		
		//Creo la JTable
		TableMasRevisado = new JTable(modelo);
		TableMasRevisado.setModel(modelo);
		scrollPane.setViewportView(TableMasRevisado);
		TableMasRevisado.setLayout(null);
		scrollPane.setViewportView(TableMasRevisado);
		TableMasRevisado.setModel(modelo);
		TableMasRevisado.setBorder(new LineBorder(new Color(0, 0, 0)));
		
	}
	
	public void imprimirVentana(String msg) {
		
		JOptionPane.showMessageDialog (frame, msg);
	}
	
	//obtine el VO mas revisado desde el controlador
	public void GetMasRevisado () 
	{		
		VOFolioMaxRev FMasRev  = new VOFolioMaxRev();		
		FMasRev = controlador.getFolioMasRevisado();
		modelo.setRowCount(0);
		Object[] fila = new Object[4]; 
		fila[0] = FMasRev.getCodigo(); 
	    fila[1] = FMasRev.getCaratula();
		fila[2] = FMasRev.getPaginas();
		fila[3] = FMasRev.getCantRevisiones();
  		modelo.addRow(fila); 			
	}	
}
