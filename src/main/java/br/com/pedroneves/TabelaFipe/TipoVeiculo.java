package br.com.pedroneves.TabelaFipe;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Optional;

public enum TipoVeiculo {
	CARRO("carros", "Carro"),
	MOTO("motos", "Moto"),
	CAMINHAO("caminhoes", "Caminhão");

	private final String caminhoApi;
	private final String descricao;

	TipoVeiculo(String caminhoApi, String descricao) {
		this.caminhoApi = caminhoApi;
		this.descricao = descricao;
	}

	public String caminhoApi() {
		return caminhoApi;
	}

	public String descricao() {
		return descricao;
	}

	public static Optional<TipoVeiculo> fromInput(String input) {
		String normalized = Normalizer.normalize(input.trim(), Normalizer.Form.NFD)
				.replaceAll("\\p{M}", "")
				.toLowerCase(Locale.ROOT);

		return switch (normalized) {
			case "1", "carro", "carros" -> Optional.of(CARRO);
			case "2", "moto", "motos" -> Optional.of(MOTO);
			case "3", "caminhao", "caminhoes" -> Optional.of(CAMINHAO);
			default -> Optional.empty();
		};
	}
}
