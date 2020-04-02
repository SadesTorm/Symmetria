package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import modelo.Auxiliar;
import modelo.Diretorios;
import servicos.ArquivosDisponiveis;
import servicos.ManipulacaoDeArquivo;
import servicos.SalvarArquivos;
import servicos.VisualizarPdfMultiPaginas;
import threads.UploadArquivoPdf;

public class TelaArquivo extends JFrame {

	private static JPanel contentPane;
	private JComboBox<String> comboBox;
	private JTextField txtNomeUsuario;
	private JPanel jplImagem;
	public JList<ImageIcon> lsm;

	private static Auxiliar nomeArquivo;

	public JCheckBox chckbxFisioterapia;
	private JCheckBox chckbxPsicologia;
	private JCheckBox chckbxRpg;
	private JCheckBox chckbxNutricao;
	private JCheckBox chckbxPilates;
	private JProgressBar progressBar;
	private static JProgressBar progressBar_1;
	private static JProgressBar progressBar_2;

	/**
	 * Create the frame.
	 */
	public TelaArquivo(Vector<String> listaArquivos) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1028, 720);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ImageIcon icon = new ImageIcon(TelaArquivo.class.getResource("/imagens/logotdm.png"));
		icon.setImage(icon.getImage().getScaledInstance(600, 345, 100));
		ImageIcon iconAdd = new ImageIcon(TelaArquivo.class.getResource("/imagens/iconAdd.png"));
		iconAdd.setImage(iconAdd.getImage().getScaledInstance(67, 70, 100));
		ImageIcon iconSave = new ImageIcon(TelaArquivo.class.getResource("/imagens/iconSave.png"));
		iconSave.setImage(iconSave.getImage().getScaledInstance(67, 70, 100));
		ImageIcon iconCancelar = new ImageIcon(TelaArquivo.class.getResource("/imagens/iconCancelar.png"));
		iconCancelar.setImage(iconCancelar.getImage().getScaledInstance(67, 70, 100));
		ImageIcon iconHome = new ImageIcon(TelaArquivo.class.getResource("/imagens/iconSair.png"));
		iconHome.setImage(iconHome.getImage().getScaledInstance(40, 45, 100));

		nomeArquivo = new Auxiliar("Selecione um arquivo");
		// painel Para alocar os componente de interação com usuario
		JPanel panelComponents = new JPanel();
		panelComponents.setBackground(Color.GRAY);
		panelComponents.setBounds(544, 520, 398, 160);
		contentPane.add(panelComponents);

		jplImagem = new JPanel();
		jplImagem.setBorder(null);
		jplImagem.setBackground(Color.GRAY);
		jplImagem.setBounds(10, 25, 485, 640);

		DefaultListModel<ImageIcon> listaImagensTemp = new DefaultListModel<ImageIcon>();

		lsm = new JList<>(listaImagensTemp);
		lsm.setSelectionBackground(Color.LIGHT_GRAY);
		lsm.setSelectionForeground(Color.GRAY);
		lsm.setForeground(Color.GRAY);
		lsm.setBorder(null);
		lsm.setBackground(Color.GRAY);
		lsm.setVisibleRowCount(1);
		JScrollPane scrollPane = new JScrollPane(lsm);
		scrollPane.setViewportBorder(null);
		scrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		scrollPane.setForeground(Color.GRAY);
		scrollPane.setBorder(null);
		scrollPane.setBackground(Color.GRAY);
		jplImagem.add(scrollPane);
		contentPane.add(jplImagem);

		JPanel panel = new JPanel();
		panel.setBounds(544, 225, 398, 445);
		panel.setLayout(null);

		comboBox = new JComboBox<String>(listaArquivos);
		comboBox.setBackground(new Color(204, 204, 204));
		comboBox.setBounds(31, 11, 357, 22);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				// para evitar duplicações
				if (e.getStateChange() == ItemEvent.SELECTED) {

					nomeArquivo = new Auxiliar(comboBox.getSelectedItem().toString());

				}
				/*
				 * // System.out.println("primeiro arquivo --------------> " + //
				 * nomeArquivo.getNomeArquivoPDF()); System.out.println("click ---- " +
				 * listaArquivos.get(comboBox.getSelectedIndex()));
				 * 
				 * System.out.println("Você escolheu a opção " + e.getItem());
				 */
			}
		});
		panelComponents.setLayout(null);
		panelComponents.add(comboBox);

		JButton btnUploadArquivo = new JButton("");
		btnUploadArquivo.setToolTipText("Upload de Arquivo");
		btnUploadArquivo.setBackground(Color.GRAY);
		btnUploadArquivo.setBorderPainted(false);
		btnUploadArquivo.setBounds(179, 75, 67, 70);
		btnUploadArquivo.setIcon(iconAdd);
		btnUploadArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (new ManipulacaoDeArquivo().ApagaArquivosTemp(Diretorios.diretorioTemp) == 1) {

					if ("Selecione um arquivo" == nomeArquivo.getNomeArquivoPDF()) {

						JOptionPane.showMessageDialog(null, "Escolha um arquivo para efetuar o upload");

					} else {

						try {

							// new Thread(threadLoad).start(); // progressBar

							new ManipulacaoDeArquivo().ApagaArquivosTemp(Diretorios.diretorioTemp);
							// diretorio passado de onde o arquivo pdf original se encontra "pasta" para ser
							// transfirmado em jpg

							Runnable excutaThreadUpload = new UploadArquivoPdf(Diretorios.diretorioArquivos + "\\",
									nomeArquivo.getNomeArquivoPDF(), Diretorios.diretorioTemp + "\\");

							Thread threadUpload = new Thread(excutaThreadUpload);

							UpandoArquivoPDF(threadUpload, nomeArquivo.getNomeArquivoPDF());

							// tempo de espera ate a thread ser liberada
							while (threadUpload.isAlive()) {

								threadUpload.sleep(500);

							}

							@SuppressWarnings("unchecked")
							DefaultListModel<ImageIcon> listaImagensTemp = new VisualizarPdfMultiPaginas()
									.VisualizarPdf(Diretorios.diretorioTemp, jplImagem.getWidth(),
											jplImagem.getHeight());
							lsm = new JList<>(listaImagensTemp);
							lsm.addMouseListener(new MouseAdapter() {

								@Override
								public void mouseClicked(MouseEvent e) {

									System.out.println("testandp click da lista " + lsm.getSelectedIndex());
									// paginaSelecionada = lsm.getSelectedIndex();

								}
							});

							lsm.setVisibleRowCount(1);
							jplImagem.removeAll();
							jplImagem.add(new JScrollPane(lsm));
							// lblPagTotal.setText("Pag. Total : " + listaImagensTemp.size());

							jplImagem.setVisible(false);
							jplImagem.setVisible(true);

						} catch (Exception exception) {

							exception.printStackTrace();

						}
					}
				} else {

					System.out.println("+++++++++ERRO++++++++++++* " + nomeArquivo.getNomeArquivoPDF());

				}

				System.out.println("+++++++++++ nome upload ++++++++++++++* " + nomeArquivo.getNomeArquivoPDF());

			}
		});
		panelComponents.add(btnUploadArquivo);

		JButton btnSalvarArquivo = new JButton("");
		btnSalvarArquivo.setToolTipText("Salvar Arquivo");
		btnSalvarArquivo.setBounds(286, 75, 67, 70);
		btnSalvarArquivo.setBackground(Color.GRAY);
		btnSalvarArquivo.setIcon(iconSave);
		btnSalvarArquivo.setBorderPainted(false);
		btnSalvarArquivo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if (txtNomeUsuario.getText().equals("") || txtNomeUsuario.getText().contains("Nome do Arquivo")) {

					JOptionPane.showMessageDialog(null, "Insira o nome do arquivo");

				} else {
					try {

						if (VerificaCheck() == null) {

							JOptionPane.showMessageDialog(null, "Marque a categoria do arquivo");

						} else {
							if ("Selecione um arquivo" == nomeArquivo.getNomeArquivoPDF()) {

								JOptionPane.showMessageDialog(null, "Escolha um arquivo para indexar");

							} else {
								System.out.println(nomeArquivo.getNomeArquivoPDF());
								new SalvarArquivos();
								SalvarArquivos.salvaPdf(txtNomeUsuario.getText(), nomeArquivo.getNomeArquivoPDF(),
										Diretorios.diretorioServidor, Diretorios.diretorioTemp,
										Diretorios.diretorioArquivos, Diretorios.prefixoDiretorio, VerificaCheck());

								txtNomeUsuario.setText("Nome do Arquivo");
								comboBox.setSelectedIndex(0);
								ResetaCheck();
								nomeArquivo.setNomeArquivoPDF("");
								jplImagem.removeAll();
								jplImagem.setVisible(false);

							}
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				new File(Diretorios.diretorioArquivos + "\\" + nomeArquivo.getNomeArquivoPDF()).delete();

				Vector<String> listaArquivos1;
				List<String> listaNomeArquivos = new ArquivosDisponiveis()
						.listaDeArquivos(Diretorios.diretorioArquivos);
				listaArquivos1 = new Vector<String>(listaNomeArquivos.size());
				listaArquivos1.add("Selecione um arquivo");
				int i = 0;
				while (i < listaNomeArquivos.size()) {

					listaArquivos1.add(listaNomeArquivos.get(i));
					i++;
				}

				comboBox = new JComboBox<String>(listaArquivos1);
				comboBox.repaint();

			}
		});
		panelComponents.add(btnSalvarArquivo);

		JButton btnCancelarArquivo = new JButton("");
		btnCancelarArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				txtNomeUsuario.setText("Nome do Arquivo");
				comboBox.setSelectedIndex(0);
				jplImagem.removeAll();
				jplImagem.setVisible(false);

			}
		});
		btnCancelarArquivo.setToolTipText("Cancelar Upload");
		btnCancelarArquivo.setBounds(70, 75, 67, 70);
		btnCancelarArquivo.setBackground(Color.GRAY);
		btnCancelarArquivo.setIcon(iconCancelar);
		btnCancelarArquivo.setBorderPainted(false);
		panelComponents.add(btnCancelarArquivo);

		JButton btnHomeArquivo = new JButton("");
		btnHomeArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				TelaHome.frame.setVisible(true);
			}
		});
		btnHomeArquivo.setToolTipText("Home");
		btnHomeArquivo.setBackground(Color.GRAY);
		btnHomeArquivo.setBounds(962, 11, 40, 45);
		btnHomeArquivo.setBorderPainted(false);
		btnHomeArquivo.setIcon(iconHome);
		contentPane.add(btnHomeArquivo);

		txtNomeUsuario = new JTextField();
		txtNomeUsuario.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtNomeUsuario.setBackground(new Color(204, 204, 204));
		txtNomeUsuario.setBounds(31, 44, 357, 20);
		txtNomeUsuario.setText("Nome do Arquivo");
		txtNomeUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (txtNomeUsuario.getText().contains("Nome do Arquivo")) {

					txtNomeUsuario.setText(null);

				}
			}
		});
		txtNomeUsuario.setToolTipText("Nome Do Arquivo Para Salvar");
		panelComponents.add(txtNomeUsuario);
		txtNomeUsuario.setColumns(10);

		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(475, 45, 527, 354);
		lblLogo.setIcon(icon);
		contentPane.add(lblLogo);

		chckbxFisioterapia = new JCheckBox("FISIOTERAPIA");
		chckbxFisioterapia.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(chckbxFisioterapia.getText());
				chckbxPsicologia.setSelected(false);
				chckbxNutricao.setSelected(false);
				chckbxPilates.setSelected(false);
				chckbxRpg.setSelected(false);
			}
		});
		chckbxFisioterapia.setFont(new Font("Tahoma", Font.BOLD, 12));
		chckbxFisioterapia.setForeground(Color.WHITE);
		chckbxFisioterapia.setBackground(Color.GRAY);
		chckbxFisioterapia.setBounds(572, 460, 120, 23);
		contentPane.add(chckbxFisioterapia);

		chckbxPsicologia = new JCheckBox("PSICOLOGIA");
		chckbxPsicologia.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				chckbxFisioterapia.setSelected(false);
				chckbxNutricao.setSelected(false);
				chckbxPilates.setSelected(false);
				chckbxRpg.setSelected(false);

			}
		});
		chckbxPsicologia.setFont(new Font("Tahoma", Font.BOLD, 12));
		chckbxPsicologia.setForeground(Color.WHITE);
		chckbxPsicologia.setBackground(Color.GRAY);
		chckbxPsicologia.setBounds(572, 500, 120, 23);
		contentPane.add(chckbxPsicologia);

		chckbxNutricao = new JCheckBox("NUTRI\u00C7\u00C3O");
		chckbxNutricao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				chckbxPsicologia.setSelected(false);
				chckbxFisioterapia.setSelected(false);
				chckbxPilates.setSelected(false);
				chckbxRpg.setSelected(false);

			}
		});
		chckbxNutricao.setForeground(Color.WHITE);
		chckbxNutricao.setFont(new Font("Tahoma", Font.BOLD, 12));
		chckbxNutricao.setBackground(Color.GRAY);
		chckbxNutricao.setBounds(733, 460, 97, 23);
		contentPane.add(chckbxNutricao);

		chckbxPilates = new JCheckBox("PILATES");
		chckbxPilates.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				chckbxPsicologia.setSelected(false);
				chckbxNutricao.setSelected(false);
				chckbxFisioterapia.setSelected(false);
				chckbxRpg.setSelected(false);

			}
		});
		chckbxPilates.setForeground(Color.WHITE);
		chckbxPilates.setFont(new Font("Tahoma", Font.BOLD, 12));
		chckbxPilates.setBackground(Color.GRAY);
		chckbxPilates.setBounds(733, 500, 97, 23);
		contentPane.add(chckbxPilates);

		chckbxRpg = new JCheckBox("RPG");
		chckbxRpg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				chckbxPsicologia.setSelected(false);
				chckbxNutricao.setSelected(false);
				chckbxPilates.setSelected(false);
				chckbxFisioterapia.setSelected(false);

			}
		});
		chckbxRpg.setFont(new Font("Tahoma", Font.BOLD, 12));
		chckbxRpg.setForeground(Color.WHITE);
		chckbxRpg.setBackground(Color.GRAY);
		chckbxRpg.setBounds(876, 460, 97, 23);
		contentPane.add(chckbxRpg);

		jplImagem.setVisible(true);
	}

	public static void UpandoArquivoPDF(Thread threadUpload, String nomeDoArquivo) throws IOException {

		threadUpload.start();
		System.out.println("-----------loading... " + nomeDoArquivo);

	}

	public String VerificaCheck() {

		if (chckbxFisioterapia.isSelected()) {

			return "_fisioterapia";
		}
		if (chckbxPsicologia.isSelected()) {

			return "_psicologia";
		}
		if (chckbxNutricao.isSelected()) {

			return "_nutrição";
		}
		if (chckbxPilates.isSelected()) {

			return "_pilates";
		}
		if (chckbxRpg.isSelected()) {

			return "_rpg";
		}

		return null;
	}

	public void ResetaCheck() {

		chckbxFisioterapia.setSelected(false);
		chckbxPsicologia.setSelected(false);
		chckbxNutricao.setSelected(false);
		chckbxPilates.setSelected(false);
		chckbxRpg.setSelected(false);
	}

}
