package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.itextpdf.text.DocumentException;

import modelo.Auxiliar;
import modelo.Diretorios;
import servicos.ManipulacaoDeArquivo;
import servicos.SalvarArquivos;
import servicos.Verificacao;

public class TelaArquivoUnico extends JFrame {

	private JPanel contentPane;

	private JLabel lblImagem;

	private JTextField txtNomeUsuario;

	private int totalPages, controlePaginas;

	private static Auxiliar nomeArquivo;

	private JCheckBox chckbxFisioterapia;
	private JCheckBox chckbxPsicologia;
	private JCheckBox chckbxRpg;
	private JCheckBox chckbxNutricao;
	private JCheckBox chckbxPilates;

	/**
	 * Create the frame.
	 */
	public TelaArquivoUnico(Vector<String> listaArquivos) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1028, 720);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Seta logogmarca da empresa quando nao ah arquivo selecionado
		ImageIcon icon = new ImageIcon(TelaArquivoUnico.class.getResource("/imagens/logotdm.png"));
		icon.setImage(icon.getImage().getScaledInstance(620, 345, 100));
		ImageIcon iconAdd = new ImageIcon(TelaArquivoUnico.class.getResource("/imagens/iconAdd.png"));
		iconAdd.setImage(iconAdd.getImage().getScaledInstance(67, 70, 100));
		ImageIcon iconSave = new ImageIcon(TelaArquivoUnico.class.getResource("/imagens/iconSave.png"));
		iconSave.setImage(iconSave.getImage().getScaledInstance(67, 70, 100));
		ImageIcon iconCancelar = new ImageIcon(TelaArquivoUnico.class.getResource("/imagens/iconCancelar.png"));
		iconCancelar.setImage(iconCancelar.getImage().getScaledInstance(67, 70, 100));
		ImageIcon iconHome = new ImageIcon(TelaArquivoUnico.class.getResource("/imagens/iconSair.png"));
		iconHome.setImage(iconHome.getImage().getScaledInstance(40, 45, 100));
		ImageIcon iconFundo = new ImageIcon(TelaArquivoUnico.class.getResource("/imagens/fundoLsm.png"));
		iconFundo.setImage(iconFundo.getImage().getScaledInstance(40, 45, 100));

		nomeArquivo = new Auxiliar("Selecione um arquivo");

		// painel Para alocar os componente de interação com usuario
		JPanel panelComponents = new JPanel();
		panelComponents.setBackground(Color.GRAY);
		panelComponents.setBounds(531, 404, 471, 276);
		contentPane.add(panelComponents);
		panelComponents.setLayout(null);

		JComboBox comboBox = new JComboBox(listaArquivos);
		comboBox.setBackground(new Color(204, 204, 204));
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
		comboBox.setBounds(72, 79, 327, 22);
		panelComponents.add(comboBox);

		JButton btnHome = new JButton("");
		btnHome.setToolTipText("Home");
		btnHome.setBackground(Color.GRAY);
		btnHome.setBounds(962, 11, 40, 45);
		btnHome.setBorderPainted(false);
		btnHome.setIcon(iconHome);
		btnHome.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					new SalvarArquivos().doMerge();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
				TelaHome.frame.setVisible(true);

			}
		});
		contentPane.add(btnHome);

		txtNomeUsuario = new JTextField();
		txtNomeUsuario.setBorder(new LineBorder(new Color(171, 173, 179)));
		txtNomeUsuario.setText("Nome do Arquivo");
		txtNomeUsuario.setBackground(new Color(204, 204, 204));
		txtNomeUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (txtNomeUsuario.getText().contains("Nome do Arquivo")) {

					txtNomeUsuario.setText(null);

				}
			}
		});
		txtNomeUsuario.setToolTipText("Nome Do Arquivo Para Salvar");
		txtNomeUsuario.setBounds(72, 112, 327, 20);
		panelComponents.add(txtNomeUsuario);
		txtNomeUsuario.setColumns(10);

		JButton btnUploadArquivoUnico = new JButton("");
		btnUploadArquivoUnico.setToolTipText("Upload Arquivo");
		btnUploadArquivoUnico.setBackground(Color.GRAY);
		btnUploadArquivoUnico.setBorderPainted(false);
		btnUploadArquivoUnico.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if ("Selecione um arquivo" == nomeArquivo.getNomeArquivoPDF()) {

					JOptionPane.showMessageDialog(null, "Escolha um arquivo para efetuar o upload");

				} else {

					try {
						if (Verificacao.verificaArquivoExistente(Diretorios.diretorioServidor, "backup", "")) {

							new SalvarArquivos().doMerge();

						} else {

							PDDocument document = new PDDocument();

							document.save(Diretorios.diretorioServidor + "\\backup.pdf");
							System.out.println("PDF created");
							document.close();

							new SalvarArquivos().doMerge();

						}

					} catch (IOException e4) {
						// TODO Auto-generated catch block
						e4.printStackTrace();
					} catch (DocumentException e4) {
						// TODO Auto-generated catch block
						e4.printStackTrace();
					}
					// variavel onde controla quantas paginas existem na pasta para ser salvas
					controlePaginas = 1;

					// chama o metodo salvar arquivo que ainda nao foi finalizado
					// new SalvarArquivos().salvaArquivosImcompletos(Diretorios.diretorioTempUpload,
					// Diretorios.diretorioServidor);

					try {

						// carrega arquivo pdf com varias paginas e chama metodo cortar
						// chama metodo (desfragmentar) onde o mesmo quebra um pdf de varias paginas em
						// paginas unicas na pasta TepmUpload
						totalPages = new ManipulacaoDeArquivo().DesfragmentarPdf(Diretorios.diretorioArquivos,
								nomeArquivo.getNomeArquivoPDF(), Diretorios.diretorioTempUpload);

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {

						// transforma sempre o primeiro arquivo PDF em JPG para visualização na
						// interface
						new ManipulacaoDeArquivo().GenerateImageFromPDF(Diretorios.getDiretorioTempUpload(),
								"\\" + Diretorios.arquivoVisualizacaoTemp, "jpg", Diretorios.diretorioTemp + "\\");

						controlePaginas++;

					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					// abaixo setamamos um jlabel tipo image icon para podermos visualizar o arquivo
					ImageIcon icon = new ImageIcon(Diretorios.diretorioTemp + "\\" + "1.jpg");
					icon.setImage(icon.getImage().getScaledInstance(lblImagem.getWidth(), lblImagem.getHeight(), 10));
					lblImagem.setIcon(icon);
				}

			}
		});
		btnUploadArquivoUnico.setBounds(206, 177, 67, 70);
		btnUploadArquivoUnico.setIcon(iconAdd);
		panelComponents.add(btnUploadArquivoUnico);

		JButton btnSalvarArquivoUnico = new JButton();
		btnSalvarArquivoUnico.setToolTipText("Salvar Aquivo");
		btnSalvarArquivoUnico.setBackground(Color.GRAY);
		btnSalvarArquivoUnico.setBorderPainted(false);
		btnSalvarArquivoUnico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// abaixo verifica se o usuario digitou/escolheu o nome do arquivo a ser salvo
				if (txtNomeUsuario.getText().equals("") || txtNomeUsuario.getText().contains("Nome do Arquivo")) {

					JOptionPane.showMessageDialog(null, "Insira o nome do arquivo");

				} else {

					if (VerificaCheck() == null) {

						JOptionPane.showMessageDialog(null, "Marque a categoria do arquivo");

					} else {

						if ("Selecione um arquivo" == nomeArquivo.getNomeArquivoPDF()) {

							JOptionPane.showMessageDialog(null, "Escolha um arquivo para indexar");

						} else {

							// nomeArquivo recebe nome composto so por numeros, numero referencia-se ao
							// número da pagina
							nomeArquivo.setNomeArquivoPDF((controlePaginas - 1) + ".pdf");

							System.out.println("terceiro arquivo nome referente a ser salvo --------------> "
									+ nomeArquivo.getNomeArquivoPDF());

							try {
								// classe que contem metodos para savar os arquivos
								new SalvarArquivos();

								// salvando arquivo:
								// txtNomeUsuario -> caixa de texto onde contem nome do arquivo para ser salvo
								// nomeArquivo -> nome do arquivo temporario (arquivo desfragmentado de origem)
								// diretorioServidor -> aponta para o servidor onde sera salvo os arquivos
								// diretorioTemp -> aponta para pasta onde ficara arquivo jpg para visualização
								// diretorioTempUpload -> aponta para pasta onde fica os arquivos
								// desfragmentados
								// prefixoDiretorio -> referente ao prefixo da pasta onde ficara o arquivo a ser
								// salvo
								// sufixo -> determina qual sera o tipo do arquivo (regras do cliente)

								SalvarArquivos.salvaPdf(txtNomeUsuario.getText(), nomeArquivo.getNomeArquivoPDF(),
										Diretorios.diretorioServidor, Diretorios.diretorioTemp,
										Diretorios.diretorioTempUpload, Diretorios.prefixoDiretorio, "_capa");

							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							// verificaçoes para visualizar a imagem
							if (!(totalPages - (controlePaginas - 1) == 0)) {
								try {
									// feito a conversao do arquivo desfragmentado no formato PDF em formato JPG
									// para visualização
									new ManipulacaoDeArquivo().GenerateImageFromPDF(
											Diretorios.diretorioTempUpload + "\\", controlePaginas + ".pdf", "jpg",
											Diretorios.diretorioTemp + "\\");
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								controlePaginas++;
								System.out.println("tamanho recuperado do documento salvo =  " + controlePaginas);

								// abaixo setamamos um jlabel tipo image icon para podermos visualizar o arquivo
								ImageIcon icon = new ImageIcon(Diretorios.diretorioTemp + "\\1.jpg");
								icon.setImage(icon.getImage().getScaledInstance(lblImagem.getWidth(),
										lblImagem.getHeight(), 1));
								lblImagem.setIcon(icon);

							} else {
								// abaixo setamamos um logotipo jlabel tipo image informando que esta sem
								// arquivo para salvar.
								ImageIcon icon = new ImageIcon(
										TelaArquivoUnico.class.getResource("/imagens/fundoLsm.png"));
								icon.setImage(icon.getImage().getScaledInstance(400, 320, 150));
								lblImagem.setIcon(icon);
								System.out.println(
										"tamanho recuperado do documento imagem inicial =  " + controlePaginas);

							}

						}
					}
				}
				txtNomeUsuario.setText("Nome do Arquivo");
				System.out.println("tesntantd metodo de apagar arquivo ///////////////////////  "
						+ Diretorios.diretorioTempUpload + "\\" + nomeArquivo.getNomeArquivoPDF());
				new File(Diretorios.diretorioTempUpload + "\\" + nomeArquivo.getNomeArquivoPDF()).delete();

			}
		});
		btnSalvarArquivoUnico.setBounds(311, 177, 67, 70);
		btnSalvarArquivoUnico.setIcon(iconSave);
		panelComponents.add(btnSalvarArquivoUnico);

		JButton btnCancelar = new JButton("");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				txtNomeUsuario.setText("Nome do Arquivo");
				comboBox.setSelectedIndex(0);
				ResetaCheck();
				lblImagem.setIcon(iconFundo);
			}
		});
		btnCancelar.setToolTipText("Cancelar Upload");
		btnCancelar.setBackground(Color.GRAY);
		btnCancelar.setBorderPainted(false);
		btnCancelar.setBounds(95, 177, 67, 70);
		btnCancelar.setIcon(iconCancelar);
		panelComponents.add(btnCancelar);

		chckbxFisioterapia = new JCheckBox("FISIOTERAPIA");
		chckbxFisioterapia.setBounds(69, 7, 120, 23);
		panelComponents.add(chckbxFisioterapia);
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

		chckbxPsicologia = new JCheckBox("PSICOLOGIA");
		chckbxPsicologia.setBounds(69, 49, 120, 23);
		panelComponents.add(chckbxPsicologia);
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

		chckbxNutricao = new JCheckBox("NUTRI\u00C7\u00C3O");
		chckbxNutricao.setBounds(220, 7, 97, 23);
		panelComponents.add(chckbxNutricao);
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

		chckbxPilates = new JCheckBox("PILATES");
		chckbxPilates.setBounds(221, 49, 97, 23);
		panelComponents.add(chckbxPilates);
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

		chckbxRpg = new JCheckBox("RPG");
		chckbxRpg.setBounds(350, 7, 97, 23);
		panelComponents.add(chckbxRpg);
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

		lblImagem = new JLabel("");
		lblImagem.setBounds(10, 25, 480, 640);
		// lblImagem.setIcon(icon);
		contentPane.add(lblImagem);

		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(468, 45, 527, 354);
		lblLogo.setIcon(icon);
		contentPane.add(lblLogo);
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
