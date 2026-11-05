package br.com.pedroneves.TabelaFipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.text.Normalizer;
import java.util.List;
import java.util.Locale;

@Component
public class FipeApiClient {
	private static final ParameterizedTypeReference<List<Marca>> MARCAS_TYPE =
			new ParameterizedTypeReference<>() {};
	private static final ParameterizedTypeReference<List<Ano>> ANOS_TYPE =
			new ParameterizedTypeReference<>() {};

	private final RestClient restClient;

	public FipeApiClient(@Value("${fipe.api.base-url}") String baseUrl) {
		JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory();
		requestFactory.setReadTimeout(Duration.ofSeconds(20));
		this.restClient = RestClient.builder()
				.baseUrl(baseUrl)
				.requestFactory(requestFactory)
				.build();
	}

	public List<Marca> listarMarcas(TipoVeiculo tipo) {
		return restClient.get()
				.uri("/{tipo}/marcas", tipo.caminhoApi())
				.retrieve()
				.body(MARCAS_TYPE);
	}

	public List<Modelo> buscarModelos(TipoVeiculo tipo, int codigoMarca, String nomeBusca) {
		ModelosResponse resposta = restClient.get()
				.uri("/{tipo}/marcas/{marca}/modelos", tipo.caminhoApi(), codigoMarca)
				.retrieve()
				.body(ModelosResponse.class);

		String termo = normalizar(nomeBusca);
		return resposta.modelos().stream()
				.filter(modelo -> normalizar(modelo.nome()).contains(termo))
				.toList();
	}

	public List<Ano> listarAnos(TipoVeiculo tipo, int codigoMarca, int codigoModelo) {
		return restClient.get()
				.uri("/{tipo}/marcas/{marca}/modelos/{modelo}/anos",
						tipo.caminhoApi(), codigoMarca, codigoModelo)
				.retrieve()
				.body(ANOS_TYPE);
	}

	public Veiculo consultarValor(TipoVeiculo tipo, int codigoMarca, int codigoModelo, String codigoAno) {
		return restClient.get()
				.uri("/{tipo}/marcas/{marca}/modelos/{modelo}/anos/{ano}",
						tipo.caminhoApi(), codigoMarca, codigoModelo, codigoAno)
				.retrieve()
				.body(Veiculo.class);
	}

	private String normalizar(String texto) {
		return Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
				.replaceAll("\\p{M}", "")
				.toLowerCase(Locale.ROOT);
	}

	public record Marca(int codigo, String nome) {}

	public record Modelo(int codigo, String nome) {}

	private record ModelosResponse(List<Modelo> modelos) {}

	public record Ano(String codigo, String nome) {}

	public record Veiculo(
			@JsonProperty("Valor") String valor,
			@JsonProperty("Marca") String marca,
			@JsonProperty("Modelo") String modelo,
			@JsonProperty("AnoModelo") int anoModelo,
			@JsonProperty("Combustivel") String combustivel) {}
}
