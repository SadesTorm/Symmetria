package servicos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ArquivosDisponiveis {

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