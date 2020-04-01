package servicos;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

public class VisualizarPdfMultiPaginas {

	public DefaultListModel VisualizarPdf(String diretorioTemp, int widthImagem, int heightImagem) throws IOException {

		File folder = new File(diretorioTemp);
		File[] listOfFiles = folder.listFiles();
		DefaultListModel<ImageIcon> listModel = new DefaultListModel();
		int count = 0;
		for (int i = 0; i < listOfFiles.length; i++) {
			System.out.println("check path" + listOfFiles[i]);
			String name = listOfFiles[i].toString();
			// load only JPEGs
			if (name.endsWith("jpg")) {

				ImageIcon ii = new ImageIcon(
						ImageIO.read(listOfFiles[i]).getScaledInstance(widthImagem, heightImagem, 100));
				listModel.add(count++, ii);
			}
		}

		return listModel;

	}

}
