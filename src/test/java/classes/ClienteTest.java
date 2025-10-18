package classes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {
    private classes.Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new classes.Cliente();
    }

    @Test
    void testSetAndGetNome() {
        String nome = "João Silva";
        cliente.setNome(nome);
        assertEquals(nome, cliente.getNome());
    }

    @Test
    void testSetAndGetCpf() {
        String cpf = "123.456.789-00";
        cliente.setCpf(cpf);
        assertEquals(cpf, cliente.getCpf());
    }

    @Test
    void testNomeNulo() {
        cliente.setNome(null);
        assertNull(cliente.getNome());
    }

    @Test
    void testCpfNulo() {
        cliente.setCpf(null);
        assertNull(cliente.getCpf());
    }

    @Test
    void testNomeVazio() {
        cliente.setNome("");
        assertEquals("", cliente.getNome());
    }

    @Test
    void testCpfVazio() {
        cliente.setCpf("");
        assertEquals("", cliente.getCpf());
    }
}
