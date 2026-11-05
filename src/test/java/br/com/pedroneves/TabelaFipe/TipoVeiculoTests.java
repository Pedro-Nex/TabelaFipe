package br.com.pedroneves.TabelaFipe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TipoVeiculoTests {

	@Test
	void acceptsNumbersAndNamesForVehicleTypes() {
		assertEquals(TipoVeiculo.CARRO, TipoVeiculo.fromInput("1").orElseThrow());
		assertEquals(TipoVeiculo.CARRO, TipoVeiculo.fromInput("Carro").orElseThrow());
		assertEquals(TipoVeiculo.MOTO, TipoVeiculo.fromInput("motos").orElseThrow());
		assertEquals(TipoVeiculo.CAMINHAO, TipoVeiculo.fromInput("Caminhão").orElseThrow());
		assertTrue(TipoVeiculo.fromInput("ônibus").isEmpty());
	}
}
