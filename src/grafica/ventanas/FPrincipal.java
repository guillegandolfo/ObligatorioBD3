package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDesktopPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import java.awt.Dimension;

import javax.swing.border.MatteBorder;



public class FPrincipal extends Ventana{

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FPrincipal window = new FPrincipal();
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
	public FPrincipal() {
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
		
		final JDesktopPane panel = new JDesktopPane();
		panel.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 14));
		panel.setBorder(null);
		panel.setBackground(new Color(230, 230, 250));
		panel.setBounds(289, 24, 740, 573);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
	
		
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
		
		JButton btnFolios = new JButton("Folios");
		btnFolios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(getVentanaAbierta() == null){
					FAbFolio formFolios = new FAbFolio();
					setVentanaAbierta(formFolios);
					formFolios.setVentanaAbierta(formFolios);
					formFolios.setVisible(true);
				}
				else{
					getVentanaAbierta().toFront();
				}
			}
		});
		btnFolios.setForeground(Color.BLACK);
		btnFolios.setBounds(71, 212, 131, 23);
		panel_1.add(btnFolios);
		
		JButton btnRevisiones = new JButton("Revisiones");
		btnRevisiones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(getVentanaAbierta() == null){
					FAltaRevision formRevisiones = new FAltaRevision();
					setVentanaAbierta(formRevisiones);
					formRevisiones.setVentanaAbierta(formRevisiones);
					formRevisiones.setVisible(true);
				}
				else{
					getVentanaAbierta().toFront();
				}
			}
		});
		btnRevisiones.setForeground(Color.BLACK);
		btnRevisiones.setBounds(71, 263, 131, 23);
		panel_1.add(btnRevisiones);
		
		JButton btnMasRevisado = new JButton("Mas Revisado");
		btnMasRevisado.setForeground(Color.BLACK);
		btnMasRevisado.setBounds(71, 328, 131, 23);
		panel_1.add(btnMasRevisado);
		
		JButton btnRespaldo = new JButton("Respaldo");
		btnRespaldo.setForeground(Color.BLACK);
		btnRespaldo.setBounds(71, 398, 131, 23);
		panel_1.add(btnRespaldo);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(286, 0, 5, 597);
		panel_1.add(separator);
		separator.setBorder(new MatteBorder(5, 5, 5, 5, (Color) new Color(119, 136, 153)));
		separator.setPreferredSize(new Dimension(0, 1));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(new Color(70, 130, 180));
		separator.setBackground(new Color(70, 130, 180));
		separator.setLayout(null);
		
		JButton btnAgregarFolio = new JButton("Agregar Folio");
		btnAgregarFolio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(getVentanaAbierta() == null){
					FAltaFolio formAltaFolios = new FAltaFolio();
					setVentanaAbierta(formAltaFolios);
					formAltaFolios.setVentanaAbierta(formAltaFolios);
					formAltaFolios.setVisible(true);
				}
				else{
					getVentanaAbierta().toFront();
				}
			}
		});
		btnAgregarFolio.setForeground(Color.BLACK);
		btnAgregarFolio.setBounds(71, 178, 131, 23);
		panel_1.add(btnAgregarFolio);
		//jPanelConFondo.setVisible(true);
		/**/
	}
}
