package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import modelo.Diretorios;

public class TelaHome extends JFrame {

	/**
	 * Launch the application.
	 * 
	 */

	public static TelaHome frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new TelaHome();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaHome() {
		getContentPane().setBackground(Color.GRAY);
		setBounds(100, 100, 404, 307);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImageIcon iconLogo = new ImageIcon(TelaHome.class.getResource("/imagens/digitalizaFacil.png"));
		iconLogo.setImage(iconLogo.getImage().getScaledInstance(420, 230, 100));
		ImageIcon iconMultiPage = new ImageIcon(TelaHome.class.getResource("/imagens/iconMultiPage.png"));
		iconMultiPage.setImage(iconMultiPage.getImage().getScaledInstance(74, 79, 100));
		ImageIcon iconUniquePage = new ImageIcon(TelaHome.class.getResource("/imagens/iconUniquePage.png"));
		iconUniquePage.setImage(iconUniquePage.getImage().getScaledInstance(74, 79, 100));
		ImageIcon iconTdm = new ImageIcon(TelaHome.class.getResource("/imagens/logo.png"));
		iconTdm.setImage(iconTdm.getImage().getScaledInstance(60, 30, 150));

		JButton btnMultiPginas = new JButton("");
		btnMultiPginas.setBorderPainted(false);
		btnMultiPginas.setBackground(Color.GRAY);
		btnMultiPginas.setBounds(10, 97, 74, 79);
		btnMultiPginas.setIcon(iconMultiPage);
		btnMultiPginas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Vector<String> listaArquivos;
				List<String> listaNomeArquivos = listaDeArquivos(Diretorios.diretorioArquivos);
				listaArquivos = new Vector<String>(listaNomeArquivos.size());
				listaArquivos.add("Selecione um arquivo");
				int i = 0;
				while (i < listaNomeArquivos.size()) {

					listaArquivos.add(listaNomeArquivos.get(i));
					i++;
				}

				frame.setVisible(false);
				new TelaArquivo(listaArquivos).setVisible(true);

			}
		});
		getContentPane().setLayout(null);
		getContentPane().add(btnMultiPginas);

		JButton btnPaginaUnica = new JButton("");
		btnPaginaUnica.setBackground(Color.GRAY);
		btnPaginaUnica.setBounds(301, 97, 74, 79);
		btnPaginaUnica.setBorderPainted(false);
		btnPaginaUnica.setIcon(iconUniquePage);
		btnPaginaUnica.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				Vector<String> listaArquivos;
				List<String> listaNomeArquivos = listaDeArquivos(Diretorios.diretorioArquivos);
				listaArquivos = new Vector<String>(listaNomeArquivos.size());
				listaArquivos.add("Selecione um arquivo");
				int i = 0;
				while (i < listaNomeArquivos.size()) {

					listaArquivos.add(listaNomeArquivos.get(i));
					i++;
				}

				frame.setVisible(false);
				new TelaArquivoUnico(listaArquivos).setVisible(true);

			}
		});
		getContentPane().add(btnPaginaUnica);

		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(5, 0, 365, 245);
		lblLogo.setIcon(iconLogo);
		getContentPane().add(lblLogo);

		JLabel lblTdm = new JLabel("");
		lblTdm.setBounds(0, 238, 60, 30);
		lblTdm.setIcon(iconTdm);
		getContentPane().add(lblTdm);

	}

	public static List<String> listaDeArquivos(String diretorio) {

		File pasta = new File(diretorio);

		File[] arq = pasta.listFiles(File::isFile);
		List<String> listaArquivos = new ArrayList<String>();
		System.out.println("\nArquivos: " + arq[0] + "\n     Arquivos disponiveis na pasta ");

		for (File p : arq) {
			listaArquivos.add(p.getName());

			System.out.println(p.getName());
		}

		return listaArquivos;
	}
}
