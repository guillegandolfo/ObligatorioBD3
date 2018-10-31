package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.SystemColor;

public class FPrincipal {

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
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 828, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);	
		
		textField = new JTextField();
		frame.getContentPane().add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 14));
		panel.setBorder(null);
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(0, 168, 820, 293);
		frame.getContentPane().add(panel);

		JButton btnAbFolio = new JButton("Folios");
		btnAbFolio.setBackground(SystemColor.menu);
		btnAbFolio.setBorder(null);
		btnAbFolio.setForeground(Color.BLACK);
		//btnAbFolio.setBackground(Color.WHITE);
		btnAbFolio.setBounds(111, 66, 165, 60);
		btnAbFolio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FAbFolio formFolios = new FAbFolio();
				//formFolios.setVisible(true);
			}
		});
		panel.setLayout(null);
		panel.add(btnAbFolio);
		
		JButton btnRevision = new JButton("Revisiones");
		btnRevision.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FAltaRevision formRevisiones = new FAltaRevision();
				formRevisiones.setVisible(true);
			}
		});
		btnRevision.setForeground(Color.BLACK);
		//btnRevision.setBackground(Color.WHITE);
		btnRevision.setBounds(337, 66, 165, 60);
		panel.add(btnRevision);
		
		JButton btnCerrarSesion = new JButton("Cerrar Sesion");
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnCerrarSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCerrarSesion.setBorder(null);
		btnCerrarSesion.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		btnCerrarSesion.setForeground(new Color(255, 255, 255));
		btnCerrarSesion.setBackground(new Color(255, 0, 0));
		btnCerrarSesion.setBounds(523, 417, 165, 34);
		panel.add(btnCerrarSesion);
		
		JButton btnFolioMasRevisado = new JButton("Folio Mas Revisado");
		btnFolioMasRevisado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FListarFolioMasRevisado formMasRevisado = new FListarFolioMasRevisado();
				formMasRevisado.setVisible(true);
			}
		});
		btnFolioMasRevisado.setForeground(Color.BLACK);
		//btnFolioMasRevisado.setBackground(Color.WHITE);
		btnFolioMasRevisado.setBounds(111, 177, 165, 60);
		panel.add(btnFolioMasRevisado);
		
		JButton btnRespaldo = new JButton("Respaldo");
		btnRespaldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//	FRespaldo formRespaldo = new FRespaldo();
				//formRespaldo.setVisible(true);
			}
		});
		btnRespaldo.setForeground(Color.BLACK);
		//btnRespaldo.setBackground(Color.WHITE);
		btnRespaldo.setBounds(337, 177, 165, 60);
		panel.add(btnRespaldo);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setForeground(Color.BLACK);
		//btnSalir.setBackground(Color.GREY);
		btnSalir.setBounds(644, 259, 131, 23);
		panel.add(btnSalir);
		
		/**/
		/*jPanelConFondo jPanelConFondo = new jPanelConFondo();						
		jPanelConFondo.setImagen(Toolkit.getDefaultToolkit().getImage(FPrincipal.class.getResource("/Grafica/imagenes/cheffLogo.jpg")));
		jPanelConFondo.setBounds(21, 153, 288, 296);
		frame.getContentPane().add(jPanelConFondo);*/	
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 820, 167);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Estudio");
		lblNewLabel.setFont(new Font("MingLiU_HKSCS-ExtB", Font.PLAIN, 30));
		lblNewLabel.setBounds(579, 23, 115, 35);
		panel_1.add(lblNewLabel);
		
		JLabel lblJurdico = new JLabel("Jur\u00EDdico");
		lblJurdico.setFont(new Font("MingLiU_HKSCS-ExtB", Font.BOLD, 40));
		lblJurdico.setBounds(521, 69, 191, 46);
		panel_1.add(lblJurdico);
		//jPanelConFondo.setVisible(true);
		/**/
	}
	
	public void setVisible(boolean isVisible) {
		// TODO Auto-generated method stub
		this.frame.setVisible(isVisible);
	}
}
