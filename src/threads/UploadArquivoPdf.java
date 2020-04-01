package threads;

import java.io.IOException;

import servicos.ManipulacaoDeArquivo;

public class UploadArquivoPdf implements Runnable {
	String diretorio;
	String nomeArquivo;
	String diretorioImagem;

	public UploadArquivoPdf(String diretorio, String nomeArquivo, String diretorioImagem) {
		super();
		this.diretorio = diretorio;
		this.nomeArquivo = nomeArquivo;
		this.diretorioImagem = diretorioImagem;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			new ManipulacaoDeArquivo().GenerateImageFromPDF(diretorio, nomeArquivo, "jpg", diretorioImagem);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
