package servicos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

public class ManipulacaoDeArquivo {

	// caminho do diretorio para onde o arquivo ira, nome do arquivo a ser quebrado
	// e diretorio final do arquivo quebrado
	public int DesfragmentarPdf(String diretorioArquivos, String nomeDoArquivo, String diretorioTempUpload)
			throws IOException {

		// carrega um PDF se existente
		File file = new File(diretorioArquivos + "\\" + nomeDoArquivo);
		PDDocument document = PDDocument.load(file);
		int totalPages = document.getNumberOfPages();

		// classe usada para efetuar cortes DE PAGINAS NO PDF
		Splitter splitter = new Splitter();

		// apos cortar todas as ppaginas o mesmo e inserido em uma lista
		List<PDDocument> Pages = splitter.split(document);

		// utilizamos o iterator para fazer o processo de salvar os elementos da lista a
		// cima um por um em formado pdf
		Iterator<PDDocument> iterator = Pages.listIterator();

		// salvando os elementos da lista em pdf's individuis como citado a cima
		int i = 1;
		while (iterator.hasNext()) {
			PDDocument pd = iterator.next();

			// cada arquivo e salvas em uma pasta temporaria apenas para manipulações na
			// hora da execução
			// cada arquivo esta indexado por numero
			pd.save(diretorioTempUpload + "\\" + (i++) + ".pdf");

		}
		System.out.println("Multiple PDF’s created");
		document.close();

		return totalPages;

	}

//	"D:\\Projetos Java\\DigitalizaFacil\\tempUpload\\", "1.pdf",
//	"jpg", "D:\\Projetos Java\\DigitalizaFacil\\tempCapa\\");

	public void GenerateImageFromPDF(String diretorioTempUpload, String nomeArquivo, String extension,
			String diretorioTemp) throws IOException {

		String doc = diretorioTempUpload + nomeArquivo;
		PDDocument document = PDDocument.load(new File(doc));
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		for (int page = 0; page < document.getNumberOfPages(); ++page) {
			BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 150, ImageType.RGB);
			ImageIOUtil.writeImage(bim, String.format(diretorioTemp + "%d.jpg", page + 1), 150);

		}
		document.close();

	}

	public int ApagaArquivosTemp(String path) {

		File pasta = new File(path);

		if (pasta.isDirectory()) {
			File[] listaArquivosDeletar = pasta.listFiles();
			for (File toDelete : listaArquivosDeletar) {
				toDelete.delete();
			}
		}

		new File(path).mkdir();
		return 1;

	}
}
