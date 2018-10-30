package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class FAbFolio2 extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FAbFolio2 frame = new FAbFolio2();
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
	public FAbFolio2() {
		setBounds(100, 100, 450, 300);

	}

}
