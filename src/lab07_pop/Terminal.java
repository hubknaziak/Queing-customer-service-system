package lab07_pop;

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Terminal {

	private JFrame frmTerminal;
	static int liczba = 0 ;
	private static TextField textField;
	ICentrala centralaRMI;
	IMonitor monitorRMI;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Terminal window = new Terminal();
					window.frmTerminal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	public void RMI()
	{
		try 
		{
			Registry registry = LocateRegistry.getRegistry(5033); 
			centralaRMI = (ICentrala) registry.lookup("RMI_Centrala");
			monitorRMI = (IMonitor) registry.lookup("RMI_Monitor");
			textField.setText("Polaczenie z centrala nawiazane");
		} 
		catch (NotBoundException | RemoteException e) 
		{
			JOptionPane.showMessageDialog(null, "Blad polaczenia");
			e.printStackTrace();
		}
	}
	
	public Terminal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTerminal = new JFrame();
		frmTerminal.setTitle("Terminal");
		frmTerminal.setBounds(100, 100, 450, 300);
		frmTerminal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTerminal.getContentPane().setLayout(null);
		
		JLabel lblUrzadMiastaWita = new JLabel("Urzad Miasta Wita!");
		lblUrzadMiastaWita.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUrzadMiastaWita.setBounds(133, 11, 175, 25);
		frmTerminal.getContentPane().add(lblUrzadMiastaWita);
		
		JLabel lblWybierzSwjRodzaj = new JLabel("Wybierz sw\u00F3j rodzaj sprawy:");
		lblWybierzSwjRodzaj.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWybierzSwjRodzaj.setBounds(130, 50, 197, 17);
		frmTerminal.getContentPane().add(lblWybierzSwjRodzaj);
		
		JButton btnZlozWniosekO = new JButton("Zloz wniosek o wydanie prawa jazdy");
		btnZlozWniosekO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				int numer = 0;
				Bilet bilet = new Bilet();
				try 
				{
					bilet = centralaRMI.wydajBilet("wniosek", 'i');
				}
				catch (RemoteException e) 
				{
					JOptionPane.showMessageDialog(null, "Blad przy wydawaniu biletu");
				}
				numer = bilet.numer;
				textField.setText("Wydano bilet. Twoj numer to: "+numer);
				
				Info info1 = new Info();
				Info info2 = new Info();
				try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("Info.bin"))) 
				{
					info1 = (Info) input.readObject();
					info2 = (Info) input.readObject();
					input.close();
					if(info1.sprawa.equals("wniosek"))
					{
						info1.obslugiwane.add(bilet);
						info1.oczekujace++;
						monitorRMI.aktualizuj(info1);
				
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Blad przy probie deserializacji lub uzyciu obiektu zdalnego");
					e.printStackTrace();
				}
				try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("Info.bin")))
				{
					 output.writeObject(info1);
					 output.writeObject(info2);
					 output.close();
					
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Blad przy probie serializacji");
					e1.printStackTrace();
				}
								
			}
		});
		btnZlozWniosekO.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnZlozWniosekO.setBounds(88, 89, 269, 23);
		frmTerminal.getContentPane().add(btnZlozWniosekO);
		
		JButton btnOdbierzPrawoJazdy = new JButton("Odbierz prawo jazdy");
		btnOdbierzPrawoJazdy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				int numer = 0;
				Bilet bilet = new Bilet();
				try 
				{
					bilet = centralaRMI.wydajBilet("odbior", 'i');
				} 
				catch (RemoteException e) 
				{
					JOptionPane.showMessageDialog(null, "Blad przy wydawaniu biletu");
				}
				numer = bilet.numer;
				textField.setText("Wydano bilet. Twoj numer to: "+numer);
				
				Info info1 = new Info();
				Info info2 = new Info();
				try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("Info.bin"))) 
				{
					info1 = (Info) input.readObject();
					info2 = (Info) input.readObject();
					input.close();
					if(info2.sprawa.equals("odbior"))
					{
						info2.obslugiwane.add(bilet);
						info2.oczekujace++;
						monitorRMI.aktualizuj(info2);
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Blad przy probie deserializacji lub uzyciu obiektu zdalnego");
					e.printStackTrace();
				}
				try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("Info.bin")))
				{
					 output.writeObject(info1);
					 output.writeObject(info2);
					 output.close();
					
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Blad przy probie serializacji");
					e1.printStackTrace();
				}

			}
		});
		btnOdbierzPrawoJazdy.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnOdbierzPrawoJazdy.setBounds(90, 136, 267, 23);
		frmTerminal.getContentPane().add(btnOdbierzPrawoJazdy);
		
		JLabel lblKomunikat = new JLabel("Komunikat");
		lblKomunikat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblKomunikat.setBounds(184, 187, 85, 14);
		frmTerminal.getContentPane().add(lblKomunikat);
		
		this.textField = new TextField();
		textField.setEditable(false);
		textField.setBounds(10, 217, 414, 22);
		frmTerminal.getContentPane().add(textField);
		RMI();
	}
}