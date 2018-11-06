package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JButton;

public class FolioMasRevisado extends JInternalFrame {

	
	private static FolioMasRevisado Ventana = null;
	   
    //singleton
    public static FolioMasRevisado getInstancia() {
        if (Ventana == null) {
        	 Ventana = new FolioMasRevisado();
        }
        return Ventana;
    }
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FolioMasRevisado frame = new FolioMasRevisado();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FolioMasRevisado() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(107, 123, 89, 23);
		getContentPane().add(btnNewButton);

	}
}
