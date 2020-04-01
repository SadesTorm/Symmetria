package modelo;

public class Auxiliar {
	String nomeArquivoPDF = "";
	String diretorio = "";

	public Auxiliar(String nomeArquivoPDF) {
		super();
		this.nomeArquivoPDF = nomeArquivoPDF;
	}

	public String getNomeArquivoPDF() {
		return nomeArquivoPDF;
	}

	public void setNomeArquivoPDF(String nomeArquivoPDF) {
		this.nomeArquivoPDF = nomeArquivoPDF;
	}

	public String getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
	}

}
