package modelo;

public class Diretorios {

	public static String diretorioTemp = "C:\\symmetria\\temp";
	public static String diretorioTempUpload = "C:\\symmetria\\tempUpload";
	public static String diretorioArquivos = "C:\\arquivos";
	public static String diretorioServidor = "C:\\server";
	public static String prefixoDiretorio = "ARQUIVO ";
	public static String sufixoArquivos = "_capa";
	public static String arquivoVisualizacaoTemp = "1.pdf";

	public static String getDiretorioTemp() {
		return diretorioTemp;
	}

	public static void setDiretorioTemp(String diretorioTemp) {
		Diretorios.diretorioTemp = diretorioTemp;
	}

	public static String getDiretorioTempUpload() {
		return diretorioTempUpload;
	}

	public static void setDiretorioTempUpload(String diretorioTempUpload) {
		Diretorios.diretorioTempUpload = diretorioTempUpload;
	}

	public static String getDiretorioArquivos() {
		return diretorioArquivos;
	}

	public static void setDiretorioArquivos(String diretorioArquivos) {
		Diretorios.diretorioArquivos = diretorioArquivos;
	}

	public static String getPrefixoDiretorio() {
		return prefixoDiretorio;
	}

	public static void setPrefixoDiretorio(String prefixoDiretorio) {
		Diretorios.prefixoDiretorio = prefixoDiretorio;
	}

	public static String getSufixoArquivos() {
		return sufixoArquivos;
	}

	public static void setSufixoArquivos(String sufixoArquivos) {
		Diretorios.sufixoArquivos = sufixoArquivos;
	}

}
