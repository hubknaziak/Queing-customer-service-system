package lab07_pop;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.TextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.awt.event.ActionEvent;

public class Stanowisko {

	private JFrame frmStanowisko;
	private JTextField textField;
	ICentrala centralaRMI;
	IMonitor monitorRMI;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stanowisko window = new Stanowisko();
					window.frmStanowisko.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Stanowisko() {
		initialize();
	}

	public void RMI()
	{
		try 
		{
			Registry registry = LocateRegistry.getRegistry(5033); 
			centralaRMI = (ICentrala) registry.lookup("RMI_Centrala");
			monitorRMI = (IMonitor) registry.lookup("RMI_Monitor");
		} 
		catch (NotBoundException | RemoteException e) 
		{
			JOptionPane.showMessageDialog(null, "Blad polaczenia");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmStanowisko = new JFrame();
		frmStanowisko.setTitle("Stanowisko");
		frmStanowisko.setBounds(100, 100, 363, 264);
		frmStanowisko.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStanowisko.getContentPane().setLayout(null);
		
		JLabel lblWybierzOpcje = new JLabel("Wybierz opcje");
		lblWybierzOpcje.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWybierzOpcje.setBounds(128, 11, 94, 17);
		frmStanowisko.getContentPane().add(lblWybierzOpcje);
		
		JButton btnNumerBiletuNastepnego = new JButton("Numer biletu nastepnego klienta");
		btnNumerBiletuNastepnego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if(!textField.getText().equals(""))
				{
					return;
				}
				
				Info info1 = new Info();
				Info info2 = new Info();
				try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("Info.bin"))) 
				{
					info1 = (Info) input.readObject();
					info2 = (Info) input.readObject();
					input.close();
				
				boolean losowa = false;
				Random generator = new Random();
				losowa = generator.nextBoolean();
				if(losowa)
				{
					char s;
					int rozmiar = info1.obslugiwane.size();
					for(int j=0; j<rozmiar; j++) 
					{
						s = info1.obslugiwane.get(j).status;
						if(s=='i' && info1.sprawa.equals("wniosek") && info1.obslugiwane.get(j).kategoria.equals("wniosek"))	
						{
							info1.obslugiwane.get(j).status='c';
							int numer =info1.obslugiwane.get(j).numer;
							textField.setText(Integer.toString(numer));
							monitorRMI.aktualizuj(info1);
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
							break;
						}
					}
				}
				else
				{
					char s;
					int rozmiar = info2.obslugiwane.size();
					for(int j=0; j<rozmiar; j++) 
					{
						s = info2.obslugiwane.get(j).status;
						if(s=='i' && info2.sprawa.equals("odbior") && info2.obslugiwane.get(j).kategoria.equals("odbior"))	
						{
							info2.obslugiwane.get(j).status='c';
							int numer =info2.obslugiwane.get(j).numer;
							textField.setText(Integer.toString(numer));
							monitorRMI.aktualizuj(info2);
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
							break;
						}
					}
				}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Blad przy probie deserializacji lub uzyciu obiektu zdalnego");
					e.printStackTrace();
				}
				
				
			}
		});
		btnNumerBiletuNastepnego.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNumerBiletuNastepnego.setBounds(53, 57, 245, 23);
		frmStanowisko.getContentPane().add(btnNumerBiletuNastepnego);
		
		JButton btnKlientObsluzony = new JButton("Klient obsluzony");
		btnKlientObsluzony.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				String numer = textField.getText();
				int liczba = Integer.parseInt(numer);
				Info info1 = new Info();
				Info info2 = new Info();
				try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("Info.bin"))) 
				{
					info1 = (Info) input.readObject();
					info2 = (Info) input.readObject();
					input.close();
				
					char s;
					int rozmiar = info1.obslugiwane.size();
					for(int j=0; j<rozmiar; j++) 
					{
						s = info1.obslugiwane.get(j).status;
						if(s=='c' && info1.obslugiwane.get(j).numer==liczba && info1.sprawa.equals("wniosek") && info1.obslugiwane.get(j).kategoria.equals("wniosek"))	
						{
							info1.obslugiwane.get(j).status='s';
							textField.setText("");
							monitorRMI.aktualizuj(info1);
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
							break;
						}
					}
					
					rozmiar = info2.obslugiwane.size();
					for(int j=0; j<rozmiar; j++) 
					{
						s = info2.obslugiwane.get(j).status;
						if(s=='c' && info2.obslugiwane.get(j).numer==liczba && info2.sprawa.equals("odbior") && info2.obslugiwane.get(j).kategoria.equals("odbior"))	
						{
							info2.obslugiwane.get(j).status='s';
							textField.setText("");
							monitorRMI.aktualizuj(info2);
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
							break;
						}
					}
					
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Blad przy probie deserializacji lub uzyciu obiektu zdalnego");
					e.printStackTrace();
				}
			}
		});
		btnKlientObsluzony.setBounds(53, 106, 245, 23);
		frmStanowisko.getContentPane().add(btnKlientObsluzony);
		
		JLabel lblNumerAktualnegoKlienta = new JLabel("Numer aktualnego klienta:");
		lblNumerAktualnegoKlienta.setBounds(107, 158, 153, 14);
		frmStanowisko.getContentPane().add(lblNumerAktualnegoKlienta);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(128, 183, 86, 20);
		frmStanowisko.getContentPane().add(textField);
		textField.setColumns(10);
		RMI();
	}
}
