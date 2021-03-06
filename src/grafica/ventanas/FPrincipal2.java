package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.DefaultDesktopManager;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.SystemColor;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.border.MatteBorder;

import logica.excepciones.ConfiguracionException;

import java.awt.event.MouseMotionAdapter;
import java.rmi.RemoteException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import grafica.ventanas.FolioMasRevisado;

public class FPrincipal2 {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FPrincipal2 window = new FPrincipal2();
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
	public FPrincipal2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(70, 130, 180));
		frame.setBounds(100, 100, 1045, 636);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);	
		//frame.setUndecorated(true);
		
		textField = new JTextField();
		frame.getContentPane().add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		final JDesktopPane panel = new JDesktopPane();
		panel.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 14));
		panel.setBorder(null);
		panel.setBackground(new Color(230, 230, 250));
		panel.setBounds(289, 0, 756, 636);
		
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		final JDesktopPane desk = new JDesktopPane();
		desk.setBounds(0, 0, 756, 636);
		desk.setDesktopManager( new NoDragDesktopManager() );
		desk.setBackground(new Color(230, 230, 250));
		panel.add(desk);
		
		/**/
		/*jPanelConFondo jPanelConFondo = new jPanelConFondo();						
		jPanelConFondo.setImagen(Toolkit.getDefaultToolkit().getImage(FPrincipal.class.getResource("/Grafica/imagenes/cheffLogo.jpg")));
		jPanelConFondo.setBounds(21, 153, 288, 296);
		frame.getContentPane().add(jPanelConFondo);*/	
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 291, 636);
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
		
		final FAbFolio vfolio = FAbFolio.getInstancia();
		final FolioMasRevisado Vfoliomasrev = FolioMasRevisado.getInstancia();
		vfolio.setLocation(0, 0);
		desk.add(vfolio);
		desk.add(Vfoliomasrev);
		
		
		btnFolios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				vfolio.putClientProperty("dragMode", "fixed");				
				vfolio.show();
				vfolio.listarFolios();
			}
		});
		btnFolios.setForeground(Color.BLACK);
		btnFolios.setBounds(71, 204, 131, 23);
		panel_1.add(btnFolios);
		
		JButton btnRevisiones = new JButton("Revisiones");
		btnRevisiones.setForeground(Color.BLACK);
		btnRevisiones.setBounds(71, 263, 131, 23);
		panel_1.add(btnRevisiones);
		
		JButton btnMasRevisado = new JButton("Mas Revisado");
		btnMasRevisado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Vfoliomasrev.putClientProperty("dragMode", "fixed");				
				Vfoliomasrev.show();
				//Vfoliomasrev.listarFolios();				
			}
		});
		btnMasRevisado.setForeground(Color.BLACK);
		btnMasRevisado.setBounds(71, 328, 131, 23);
		panel_1.add(btnMasRevisado);
		
		final JButton btnRespaldo = new JButton("Respaldo");
		btnRespaldo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnRespaldo.setBackground(new Color(255, 0, 0));
			}
		});
	
		btnRespaldo.setBorder(null);
		btnRespaldo.setBorderPainted(false);
		btnRespaldo.setBackground(new Color(128, 0, 0));		
		btnRespaldo.setForeground(Color.BLACK);
		btnRespaldo.setBounds(0, 385, 286, 49);
		panel_1.add(btnRespaldo);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(286, 0, 5, 633);
		panel_1.add(separator);
		separator.setBorder(new MatteBorder(5, 5, 5, 5, (Color) new Color(119, 136, 153)));
		separator.setPreferredSize(new Dimension(0, 1));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(new Color(70, 130, 180));
		separator.setBackground(new Color(70, 130, 180));
		separator.setLayout(null);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 385, 291, 2);
		panel_1.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(0, 432, 291, 2);
		panel_1.add(separator_2);
<<<<<<< HEAD
		

=======
				
		final prueba v = prueba.getInstancia();
		v.setLocation(0, 0);
		desk.add(v);
		btnRespaldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				v.putClientProperty("dragMode", "fixed");				
				v.show();
				v.listarFolios();
			}
		});
>>>>>>> 22704e1a3a5bd83757b2a49bff8ed5036f66fecb
		//jPanelConFondo.setVisible(true);
		/**/
		
	}
}
