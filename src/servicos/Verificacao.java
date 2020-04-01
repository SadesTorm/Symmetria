package servicos;

import java.io.File;
import java.io.IOException;

public class Verificacao {

	public static boolean verificaArquivoExistente(String diretorio, String nomeArquivo, String sufixo)
			throws IOException {

		// caminho = caminho + "\\" + nome;
		// nome = nome + "_capa.pdf";

		// diretorio = diretorio + "\\" + nomeArquivo;
		nomeArquivo = nomeArquivo + sufixo + ".pdf";

		File pasta = new File(diretorio);
		File[] arq = pasta.listFiles(File::isFile);

		for (File p : arq) {

			if (p.getName().equals(nomeArquivo)) {

				System.out.println("arquivo ja existente");

				return true;

			} else {

				System.out.println(p.getName());

			}
		}

		return false;
	}

	public static boolean verificaDiretorioExistente(String diretorioServidor, String NomeUsuario, String inicial,
			String prefixoPasta) throws IOException {

		// padraPasta = "ARQUIVO "
		diretorioServidor = diretorioServidor + "\\" + prefixoPasta + inicial.toUpperCase();

		File pasta = new File(diretorioServidor);
		File[] arq = pasta.listFiles(File::isDirectory);

		for (File p : arq) {

			if (p.getName().equals(NomeUsuario)) {

				System.out.println("diretorio arquivo ja existente");
				return true;

			} else {

				System.out.println(p.getName());

			}
		}

		return false;

	}
}
