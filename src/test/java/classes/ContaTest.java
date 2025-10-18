package classes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ContaTest {
    private Conta conta;

    @BeforeEach
    void setUp() {
        conta = new Conta(12345);
    }

    @Test
    void testConstrutor() {
        assertEquals(12345, conta.getNumeroConta());
        assertEquals(0.0, conta.getSaldo(), 0.001);
    }

    @Test
    void testDepositarValorPositivo() {
        double valor = 100.0;
        conta.depositar(valor);
        assertEquals(valor, conta.getSaldo(), 0.001);
    }

    @Test
    void testDepositarValorZero() {
        double saldoInicial = conta.getSaldo();
        conta.depositar(0.0);
        assertEquals(saldoInicial, conta.getSaldo(), 0.001);
    }

    @Test
    void testDepositarValorNegativo() {
        double saldoInicial = conta.getSaldo();
        conta.depositar(-50.0);
        assertEquals(saldoInicial, conta.getSaldo(), 0.001);
    }

    @Test
    void testSacarValorPositivoComSaldoSuficiente() {
        conta.depositar(200.0);
        conta.sacar(100.0);
        assertEquals(100.0, conta.getSaldo(), 0.001);
    }

    @Test
    void testSacarValorPositivoSemSaldoSuficiente() {
        conta.depositar(50.0);
        conta.sacar(100.0);
        assertEquals(50.0, conta.getSaldo(), 0.001);
    }

    @Test
    void testSacarValorZero() {
        conta.depositar(100.0);
        double saldoAntes = conta.getSaldo();
        conta.sacar(0.0);
        assertEquals(saldoAntes, conta.getSaldo(), 0.001);
    }

    @Test
    void testSacarValorNegativo() {
        conta.depositar(100.0);
        double saldoAntes = conta.getSaldo();
        conta.sacar(-50.0);
        assertEquals(saldoAntes, conta.getSaldo(), 0.001);
    }

    @Test
    void testSetNumeroConta() {
        int novoNumero = 54321;
        conta.setNumeroConta(novoNumero);
        assertEquals(novoNumero, conta.getNumeroConta());
    }

    @Test
    void testAplicarRendimento() {
        // Teste básico - método deve existir e não lançar exceção
        assertDoesNotThrow(() -> conta.aplicarRendimento());
    }
}
