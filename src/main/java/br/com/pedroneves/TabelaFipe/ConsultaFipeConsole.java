package br.com.pedroneves.TabelaFipe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Scanner;

@Component
@ConditionalOnProperty(name = "fipe.console.enabled", havingValue = "true", matchIfMissing = true)
public class ConsultaFipeConsole implements CommandLineRunner {
	private final FipeApiClient api;

	public ConsultaFipeConsole(FipeApiClient api) {
		this.api = api;
	}

	@Override
	public void run(String... args) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("=== Consulta Tabela FIPE ===");
			boolean continuar = true;
			while (continuar) {
				try {
					consultar(scanner);
				} catch (RestClientException exception) {
					System.out.println("Não foi possível consultar a API da FIPE: " + exception.getMessage());
				}
				System.out.print("\nDeseja fazer outra consulta? (s/n): ");
				continuar = scanner.hasNextLine() && scanner.nextLine().trim().equalsIgnoreCase("s");
			}
			System.out.println("Consulta encerrada.");
		}
	}

	private void consultar(Scanner scanner) {
		TipoVeiculo tipo = escolherTipo(scanner);
		List<FipeApiClient.Marca> marcas = api.listarMarcas(tipo);
		if (marcas.isEmpty()) {
			System.out.println("A API não retornou marcas para esse tipo de veículo.");
			return;
		}

		System.out.println("\nMarcas disponíveis:");
		marcas.forEach(marca -> System.out.printf("%d - %s%n", marca.codigo(), marca.nome()));
		int codigoMarca = lerCodigo(scanner, "Digite o código da marca: ");

		System.out.print("Digite o nome do modelo que deseja consultar (ex.: Palio): ");
		String nomeBusca = scanner.nextLine().trim();
		while (nomeBusca.isEmpty()) {
			System.out.print("O nome não pode ficar vazio. Digite o modelo: ");
			nomeBusca = scanner.nextLine().trim();
		}

		List<FipeApiClient.Modelo> modelos = api.buscarModelos(tipo, codigoMarca, nomeBusca);
		if (modelos.isEmpty()) {
			System.out.println("Nenhum modelo encontrado com esse nome para a marca selecionada.");
			return;
		}

		System.out.println("\nModelos encontrados:");
		modelos.forEach(modelo -> System.out.printf("%d - %s%n", modelo.codigo(), modelo.nome()));
		int codigoModelo = lerCodigo(scanner, "Digite o código do modelo para consultar valores: ");

		List<FipeApiClient.Ano> anos = api.listarAnos(tipo, codigoMarca, codigoModelo);
		if (anos.isEmpty()) {
			System.out.println("Não há anos disponíveis para esse modelo.");
			return;
		}

		System.out.println("\nValores encontrados:");
		for (FipeApiClient.Ano ano : anos) {
			FipeApiClient.Veiculo veiculo = api.consultarValor(
					tipo, codigoMarca, codigoModelo, ano.codigo());
			System.out.printf(
					"Valor: %s | Marca: %s | Modelo: %s | Ano: %d | Combustível: %s%n",
					veiculo.valor(), veiculo.marca(), veiculo.modelo(),
					veiculo.anoModelo(), veiculo.combustivel());
		}
	}

	private TipoVeiculo escolherTipo(Scanner scanner) {
		while (true) {
			System.out.println("\n**** OPÇÕES ****");
			System.out.println("1 - Carro");
			System.out.println("2 - Moto");
			System.out.println("3 - Caminhão");
			System.out.print("Digite uma das opções para consultar valores: ");
			String entrada = scanner.nextLine();
			var tipo = TipoVeiculo.fromInput(entrada);
			if (tipo.isPresent()) {
				return tipo.get();
			}
			System.out.println("Opção inválida. Digite 1, 2 ou 3.");
		}
	}

	private int lerCodigo(Scanner scanner, String mensagem) {
		while (true) {
			System.out.print(mensagem);
			String entrada = scanner.nextLine().trim();
			try {
				int codigo = Integer.parseInt(entrada);
				if (codigo > 0) {
					return codigo;
				}
			} catch (NumberFormatException ignored) {
				// Rejeita entradas que não sejam códigos numéricos.
			}
			System.out.println("Código inválido. Digite um número positivo.");
		}
	}
}
