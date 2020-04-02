package servicos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import modelo.Diretorios;

public class SalvarArquivos {

	////////////////////////// links uteis ///////////////////////////////////
	// https://memorynotfound.com/merge-multiple-pdf-documents-itext-java/
	// https://memorynotfound.com/apache-pdfbox-merge-multiple-pdf-documents-example-java/
	// http://burnignorance.com/java-web-development-tips/java-merging-multiple-pdfs-into-a-single-pdf-using-itext/

	public static void doMerge() throws IOException, DocumentException {

		List<InputStream> list = new ArrayList<InputStream>();

		File pastaTempUpload = new File(Diretorios.diretorioTempUpload);
		File[] arq = pastaTempUpload.listFiles(File::isFile);
		if (arq.length != 0) {
			System.out.println(
					"----------------------- testando arquivos do novo saalvamento incompleto ----------------- ");
			for (File arquivo : arq) {
				System.out.println(arquivo.getName());
				list.add(new FileInputStream(new File(Diretorios.diretorioTempUpload + "\\" + arquivo.getName())));
			}

			// Source pdfs
			// list.add(new FileInputStream(new File("f:/1.pdf")));
			// list.add(new FileInputStream(new File("f:/2.pdf")));

			// Resulting pdf
			OutputStream outputStream = new FileOutputStream(
					new File(Diretorios.diretorioServidor + "\\" + "backupTemp.pdf"));

			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			for (InputStream in : list) {
				PdfReader reader = new PdfReader(in);
				for (int i = 1; i <= reader.getNumberOfPages(); i++) {
					document.newPage();
					// import the page from source pdf
					PdfImportedPage page = writer.getImportedPage(reader, i);
					// add the page to the destination pdf
					cb.addTemplate(page, 0, 0);
				}
			}

			outputStream.flush();
			document.close();
			outputStream.close();
			new SalvarArquivos().salvaArquivosImcompletos(Diretorios.diretorioServidor);
		}

	}

	@SuppressWarnings("deprecation")
	public void salvaArquivosImcompletos(String diretorioArquivo) throws IOException {

		File file1 = new File(diretorioArquivo + "\\backup.pdf");
		File file2 = new File(diretorioArquivo + "\\backupTemp.pdf");

		// Instantiating PDFMergerUtility class
		PDFMergerUtility PDFmerger = new PDFMergerUtility();

		// Setting the destination file
		PDFmerger.setDestinationFileName(diretorioArquivo + "\\backup.pdf");

		// adding the source files
		PDFmerger.addSource(file1);
		PDFmerger.addSource(file2);

		// Merging the two documents
		PDFmerger.mergeDocuments();
		System.out.println("Documents merged");

		// apaga o arquivo existente para nao ser adicionado no arquivo backup
		new ManipulacaoDeArquivo().ApagaArquivosTemp(Diretorios.diretorioTempUpload);

	}

	public static void salvaPdf(String nomeUsuario, String nomeArquivo, String diretorioServidor, String diretorioTemp,
			String diretorioTempUpload, String prefixoDiretorio, String sufixoArquivo) throws IOException {

		// System.out.println("sexto arquivo --------------> " +
		// nomeArquivo.getNomeArquivoPDF());

		System.out.println("setimo arquivo --------------> " + nomeArquivo);

		File doc = new File(diretorioTempUpload + "\\" + nomeArquivo);

		String inicial = null;

		inicial = nomeUsuario.substring(0, 1);

		System.out.println("oitavo arquivo --------------> " + nomeUsuario);

		new Verificacao();
		if (Verificacao.verificaDiretorioExistente(diretorioServidor, nomeUsuario, inicial.toUpperCase(),
				prefixoDiretorio)) {

			System.out.println("diretorio ja existe metodo de salvar ****** ");

			String caminho = diretorioServidor + "\\" + prefixoDiretorio + inicial.toUpperCase();
			System.out.println(caminho);

			if (Verificacao.verificaArquivoExistente(
					diretorioServidor + "\\" + prefixoDiretorio + inicial.toUpperCase() + "\\" + nomeUsuario,
					nomeUsuario, sufixoArquivo)) {

				System.out.println("arquivo ja existe metodo de salvar ****** ");

				int resposta = JOptionPane.showConfirmDialog(null,
						"Deseja renomear o arquivo ?\n" + nomeUsuario + sufixoArquivo, "Arquivo Existente",
						JOptionPane.YES_NO_OPTION);

				// verfica se a resposta é verdadeira
				if (resposta == JOptionPane.YES_OPTION) {

					JFrame JmensageInput = new JFrame("Renomear Arquivo Existente");

					// renomeando nome do arquivo ja existente
					String novoNomeRenomeado = JOptionPane.showInputDialog(JmensageInput, "Nome Do Arquivo");
					System.out.println("testando novo nome ======== " + novoNomeRenomeado);
					new SalvarArquivos();
					// txtNomeUsuario.setText(novoNomeRenomeado);
					// ****************************************** fazer verificaçao do campo nome
					// vazio ****************************************
					SalvarArquivos.salvaPdf(novoNomeRenomeado, nomeArquivo, diretorioServidor, diretorioTemp,
							diretorioTempUpload, prefixoDiretorio, sufixoArquivo);

				} else {

					System.out.println("nao precisaseri renomear o arquivo ok " + nomeArquivo);

					DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
					Date date = new Date();

					PDDocument pd = PDDocument.load(doc);
					pd.save(diretorioServidor + "\\" + prefixoDiretorio + inicial.toUpperCase() + "\\" + nomeUsuario
							+ "\\" + nomeUsuario + sufixoArquivo + dateFormat.format(date) + ".pdf");

					pd.close();
					// txtNomeArquivoCapa.setText("");

				}

			} else {
				System.out.println("arquivo nao existe neste diretorio");

				PDDocument pd = PDDocument.load(doc);
				pd.save(diretorioServidor + "\\" + prefixoDiretorio + inicial.toUpperCase() + "\\" + nomeUsuario + "\\"
						+ nomeUsuario + sufixoArquivo + ".pdf");
				pd.close();
				// txtNomeArquivoCapa.setText("");
			}

		} else {

			new File(diretorioServidor + "\\" + prefixoDiretorio + inicial.toUpperCase() + "\\" + nomeUsuario).mkdir();
			PDDocument pd = PDDocument.load(doc);
			pd.save(diretorioServidor + "\\" + prefixoDiretorio + inicial.toUpperCase() + "\\" + nomeUsuario + "\\"
					+ nomeUsuario + sufixoArquivo + ".pdf");
			pd.close();

		}
	}

}
