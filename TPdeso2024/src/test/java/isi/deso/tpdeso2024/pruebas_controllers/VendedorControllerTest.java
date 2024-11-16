package isi.deso.tpdeso2024.pruebas_controllers;

import isi.deso.tpdeso2024.Vendedor;
import isi.deso.tpdeso2024.daos.VendedorDAO;
import isi.deso.tpdeso2024.dtos.CoordenadaDTO;
import isi.deso.tpdeso2024.dtos.VendedorDTO;
import isi.deso.tpdeso2024.excepciones.VendedorNoEncontradoException;
import isi.deso.tpdeso2024.controllers.VendedorController;
import isi.deso.tpdeso2024.Coordenada;
import isi.deso.tpdeso2024.daos.FactoryDAO;
import isi.deso.tpdeso2024.daos.VendedorSQLDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VendedorControllerTest {

    private VendedorController controller;
    private VendedorSQLDAO fakeDao;
// private FakeVendedorDAO fakeDao;

    @BeforeEach
    void setUp() {
        VendedorDAO dao = FactoryDAO.getFactory(FactoryDAO.SQL).getVendedorDAO(); // Usamos la implementación fake del DAO
        controller = VendedorController.getInstance();
    //controller.dao = fakeDao; // Reemplazamos el DAO real por el fake
    }

    @Test
    void testCrear() {
        VendedorDTO vdto = new VendedorDTO(0, "Agustin", "Lavaise 800", new CoordenadaDTO(1, 1), null);

        controller.crear(vdto);
        

        assertEquals(1, fakeDao.listar().size()); 
        Vendedor createdVendedor = fakeDao.listar().get(0); 
        assertEquals("Agustin", createdVendedor.getNombre());
        assertEquals("Lavaise 800", createdVendedor.getDireccion());
    }

    @Test
    void testListar() {
        fakeDao.crear(new Vendedor(1, "Juan", "Calle 123", new Coordenada(10, 20)));
        fakeDao.crear(new Vendedor(2, "Maria", "Avenida 456", new Coordenada(30, 40)));

        List<VendedorDTO> resultado = controller.listar();

        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        assertEquals("Maria", resultado.get(1).getNombre());
    }

    @Test
    void testEliminar() {
        fakeDao.crear(new Vendedor(1, "Juan", "Calle 123", new Coordenada(10, 20)));

        controller.eliminar(1);

        assertTrue(fakeDao.listar().isEmpty());
    }

    @Test
    void testBuscar() {
        fakeDao.crear(new Vendedor(1, "Juan", "Calle 123", new Coordenada(10, 20)));
        fakeDao.crear(new Vendedor(2, "Maria", "Avenida 456", new Coordenada(30, 40)));

        List<VendedorDTO> resultado = controller.buscar("Ju");

        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
    }

    @Test
    void testBuscarPorID_Exitoso() throws VendedorNoEncontradoException {
        fakeDao.crear(new Vendedor(1, "Juan", "Calle 123", new Coordenada(10, 20)));

        VendedorDTO resultado = controller.buscarPorID(1);

        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    void testBuscarPorID_NoEncontrado() {
        assertThrows(VendedorNoEncontradoException.class, () -> controller.buscarPorID(999));
    }

    @Test
    void testActualizar() {
        fakeDao.crear(new Vendedor(1, "Juan", "Calle 123", new Coordenada(10, 20)));
        VendedorDTO vdto = new VendedorDTO(1, "Juan Actualizado", "Calle Actualizada", new CoordenadaDTO(50, 60), null);

        controller.actualizar(vdto);

        Vendedor actualizado = fakeDao.listar().get(0);
        assertEquals("Juan Actualizado", actualizado.getNombre());
        assertEquals("Calle Actualizada", actualizado.getDireccion());
        assertEquals(50, actualizado.getCoordenadas().getLatitud());
        assertEquals(60, actualizado.getCoordenadas().getLongitud());
    }

    /*
    // Fake DAO para simular comportamiento
    public class FakeVendedorDAO implements VendedorDAO {
        private final List<Vendedor> vendedores = new ArrayList<>();

        @Override
        public void crear(Vendedor vendedor) {
            vendedor.setId(vendedores.size() + 1); // Simula el auto-incremento de la base de datos
            vendedores.add(vendedor);
        }

        @Override
        public List<Vendedor> listar() {
            return new ArrayList<>(vendedores);
        }

        @Override
        public void eliminar(int id) {
            vendedores.removeIf(v -> v.getId() == id);
        }

        @Override
        public List<Vendedor> buscar(String nombreSubstring) {
            List<Vendedor> resultado = new ArrayList<>();
            for (Vendedor v : vendedores) {
                if (v.getNombre().toLowerCase().contains(nombreSubstring.toLowerCase())) {
                    resultado.add(v);
                }
            }
            return resultado;
        }

        @Override
        public Vendedor buscarPorID(int id) {
            return vendedores.stream()
                    .filter(v -> v.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new VendedorNoEncontradoException("Vendedor no encontrado con ID: " + id));
        }

        @Override
        public void actualizar(Vendedor vendedor) {
            for (int i = 0; i < vendedores.size(); i++) {
                if (vendedores.get(i).getId() == vendedor.getId()) {
                    vendedores.set(i, vendedor);
                    return;
                }
            }
        }

        // Método auxiliar para acceder a todos los vendedores
        public List<Vendedor> getAll() {
            return vendedores;
        }
    }
*/
}
