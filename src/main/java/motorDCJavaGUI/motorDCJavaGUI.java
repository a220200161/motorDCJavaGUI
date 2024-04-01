package motorDCJavaGUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class motorDCJavaGUI{

	

	public static void main(String[] args) {
		new app().setVisible(true);
	}
}
class app extends JFrame {
	private JLabel lblMotor;
	private JLabel lblDireccion;
	private JButton btnInicio;
	private JButton btnFin;
	private JSlider slrVelocidad;
	private boolean encendido =false;
	private Thread hiloMotor;
	public app() {
		setTitle("Simulacion de motor");
		setSize(400,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel motorPanel = new JPanel();
		lblMotor = new JLabel();
		lblMotor.setHorizontalAlignment(SwingConstants.CENTER);
		lblMotor.setVerticalAlignment(SwingConstants.CENTER);
		
		lblMotor.setIcon(new ImageIcon(new ImageIcon("motor.png").getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH)));
		
		lblDireccion = new JLabel();
		lblDireccion.setHorizontalAlignment(SwingConstants.CENTER);
		lblDireccion.setVerticalAlignment(SwingConstants.CENTER);
		
		lblDireccion.setIcon(new ImageIcon(new ImageIcon("direccion.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH)));
		lblMotor.setLayout(null);
		lblDireccion.setBounds(133, 133, 50, 50);
		lblMotor.add(lblDireccion);
		add(lblMotor,BorderLayout.CENTER);
		
		
		
		JPanel controlPanel = new JPanel();
		btnInicio = new JButton("Inicio");
		btnFin = new JButton("Fin");
		slrVelocidad = new JSlider(0,100);
		slrVelocidad.setMajorTickSpacing(10);
		slrVelocidad.setPaintTicks(true);
		slrVelocidad.setPaintLabels(true);
		
		controlPanel.add(btnInicio);
		controlPanel.add(btnFin);
		controlPanel.add(new JLabel("Velocidad: "));
		controlPanel.add(slrVelocidad);
		add(controlPanel,BorderLayout.SOUTH);
		btnInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!encendido) {
					iniciarMotor();
					encendido=true;
				}
			}
		});
		btnFin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (encendido) {
					detenerMotor();
					encendido=false;
				}
			}
		});
	}
	private void iniciarMotor() {
		int velocidad = slrVelocidad.getValue();
		hiloMotor = new Thread(new Runnable() {
			public void run() {
				while (encendido) {
					rotarMotor();
					try {
						Thread.sleep(110-velocidad);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		hiloMotor.start();
	}
	private void detenerMotor(){
		try {
			hiloMotor.interrupt();
		} finally {
			lblDireccion.setIcon(new ImageIcon(new ImageIcon("direccion.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH)));
		}
		
		
	}
	private void rotarMotor() {
		Icon icono = lblDireccion.getIcon();
		if (icono instanceof ImageIcon) {
			Image img = ((ImageIcon) icono).getImage();
			BufferedImage imgRotada = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = imgRotada.createGraphics();
			g2d.rotate(Math.toDegrees(30),imgRotada.getWidth()/2,imgRotada.getHeight()/2);
			g2d.drawImage(img,0,0,null);
			g2d.dispose();
			lblDireccion.setIcon(new ImageIcon(imgRotada));
			/*img = img.getScaledInstance(img.getWidth(null), img.getHeight(null), Image.SCALE_SMOOTH);
			lblDireccion.setIcon(new ImageIcon(img));*/
		}
	}
}
