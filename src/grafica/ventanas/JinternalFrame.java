package grafica.ventanas;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.DefaultDesktopManager;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.event.InternalFrameEvent;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.*;

public class JinternalFrame extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//JinternalFrame frame = new JinternalFrame();
					
					
					
					JInternalFrame frame = new JInternalFrame("frame", false, false, false, false);
					frame.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
					frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
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
	public JinternalFrame() {
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		getContentPane().setLayout(null);
		setBounds(0, 0, 740, 573);
		
		//desk.setBounds(0, 0, 740, 573);
		

	}
	

}
