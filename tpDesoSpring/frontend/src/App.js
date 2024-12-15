import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

const Modal = ({ show, onClose, title, children }) => {
  if (!show) return null;

  return (
    <div className="modal-overlay">
      <div className="modal">
        <div className="modal-header">
          <h3>{title}</h3>
          <button className="close-btn" onClick={onClose}>X</button>
        </div>
        <div className="modal-content">
          {children}
        </div>
      </div>
    </div>
  );
};

const TableSection = ({ title, items, onEdit, onDelete, onCreate, searchTerm, setSearchTerm, filter, handleFilterChange }) => {
  const filteredItems = items.filter((item) => {
    if (title === 'Items Menú') {
      // Filtrar según el tipo de item (comida, bebida o todos)
      const isComida = item.esComida === true || item.esComida === 'true';
      if (filter === 'comida') {
        return isComida;
      } else if (filter === 'bebida') {
        return !isComida;
      }
      return true; // 'todos' o cualquier otro caso
    }

    // Otros casos de filtro para Vendedores, Clientes, Pedidos...
    if (title === 'Vendedores') {
      return item.nombre.toLowerCase().includes(searchTerm.toLowerCase());
    } else if (title === 'Clientes') {
      return item.cuit && item.cuit.toString().toLowerCase().includes(searchTerm.toLowerCase());
    } else if (title === 'Pedidos') {
      return item.id && item.id.toString().toLowerCase().includes(searchTerm.toLowerCase());
    }

    return true;
  });

  return (
    <div>
      <h2>{title}</h2>
      {(title !== 'Items Menú') && (
        <div className="search-bar">
          <input
            type="text"
            placeholder="Buscar..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
      )}
      {title === 'Items Menú' && (
        <div className="filters">
          <label>
            <input
              type="checkbox"
              value="comida"
              checked={filter === 'comida'}
              onChange={handleFilterChange}
            />
            Es comida
          </label>
          <label>
            <input
              type="checkbox"
              value="bebida"
              checked={filter === 'bebida'}
              onChange={handleFilterChange}
            />
            Es bebida
          </label>
          <label>
            <input
              type="checkbox"
              value="todos"
              checked={filter === 'todos'}
              onChange={handleFilterChange}
            />
            Todos
          </label>
        </div>
      )}
      <div className="table-container">
        <table>
          <thead>
            <tr>
              {Object.keys(items[0] || {}).map((key) => (
                key !== "esComida" && (
                  <th key={key}>{key.charAt(0).toUpperCase() + key.slice(1)}</th>
                )
              ))}
              {title === 'Items Menú' && <th>Es Comida</th>} {/* Solo agregar columna "Es Comida" en Items Menú */}
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {filteredItems.map(item => (
              <tr key={item.id}>
                {Object.entries(item).map(([key, value]) => (
                  key !== "esComida" && (
                    <td key={key}>{value}</td>
                  )
                ))}
                {title === 'Items Menú' && <td>{item.esComida ? 'Sí' : 'No'}</td>} {/* Mostrar "Es Comida" solo en Items Menú */}
                <td>
                  <div className='grupoBotones'>
                  <button className="editar" onClick={() => onEdit(item)}>Editar</button>
                  <button className="eliminar" onClick={() => onDelete(item.id)}>Eliminar</button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <button className="btn" onClick={onCreate}>Crear {title.slice(0, -1)}</button>
    </div>
  );
};

function App() {
  const [activeSection, setActiveSection] = useState('vendedores');
  const [showModal, setShowModal] = useState(false);
  const [selectedItemId, setSelectedItemId] = useState(null);
  const [vendedores, setVendedores] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [itemsMenu, setItemsMenu] = useState([]);
  const [pedidos, setPedidos] = useState([]);
  const [formData, setFormData] = useState({});
  const [searchTerm, setSearchTerm] = useState('');
  const [filter, setFilter] = useState('todos');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const responseVendedores = await axios.get('http://localhost:8080/api/vendedores');
        setVendedores(responseVendedores.data);
        const responseClientes = await axios.get('http://localhost:8080/api/clientes');
        setClientes(responseClientes.data);
        const responseItemsMenu = await axios.get('http://localhost:8080/api/itemsMenu');
        setItemsMenu(responseItemsMenu.data);
        const responsePedidos = await axios.get('http://localhost:8080/api/pedidos');
        setPedidos(responsePedidos.data);
      } catch (error) {
        console.error('Error fetching data', error);
      }
    };
    fetchData();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    let response;
    if (selectedItemId) {
      response = await axios.put(`http://localhost:8080/api/${activeSection}/${selectedItemId}`, formData);
      if (activeSection === 'vendedores') {
        setVendedores(vendedores.map(item => item.id === selectedItemId ? response.data : item));
      } else if (activeSection === 'clientes') {
        setClientes(clientes.map(item => item.id === selectedItemId ? response.data : item));
      } else if (activeSection === 'itemsMenu') {
        setItemsMenu(itemsMenu.map(item => item.id === selectedItemId ? response.data : item));
      } else if (activeSection === 'pedidos') {
        setPedidos(pedidos.map(item => item.id === selectedItemId ? response.data : item));
      }
    } else {
      response = await axios.post(`http://localhost:8080/api/${activeSection}`, formData);
      if (activeSection === 'vendedores') {
        setVendedores([...vendedores, response.data]);
      } else if (activeSection === 'clientes') {
        setClientes([...clientes, response.data]);
      } else if (activeSection === 'itemsMenu') {
        setItemsMenu([...itemsMenu, response.data]);
      } else if (activeSection === 'pedidos') {
        setPedidos([...pedidos, response.data]);
      }
    }
    alert(selectedItemId ? 'Elemento actualizado exitosamente' : 'Elemento creado exitosamente');
    handleCloseModal();
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/${activeSection}/${id}`);
      if (activeSection === 'vendedores') {
        setVendedores(vendedores.filter((item) => item.id !== id));
      } else if (activeSection === 'clientes') {
        setClientes(clientes.filter((item) => item.id !== id));
      } else if (activeSection === 'itemsMenu') {
        setItemsMenu(itemsMenu.filter((item) => item.id !== id));
      } else if (activeSection === 'pedidos') {
        setPedidos(pedidos.filter((item) => item.id !== id));
      }
      alert('Elemento eliminado exitosamente');
    } catch (error) {
      console.error('Hubo un error al eliminar el elemento', error);
      alert('Hubo un error al eliminar el elemento');
    }
  };

  const handleOpenModal = (item = null) => {
    setSelectedItemId(item ? item.id : null);
    setFormData(item || {});
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setSelectedItemId(null);
    setFormData({});
  };

  const handleFilterChange = (e) => {
    setFilter(e.target.value);
  };

  return (
    <div className="app">
      <div className="header">
        <h1>Panel de Administración</h1>
      </div>

      <nav className="nav-menu">
        <button className="nav-btn" onClick={() => setActiveSection('vendedores')}>Vendedores</button>
        <button className="nav-btn" onClick={() => setActiveSection('clientes')}>Clientes</button>
        <button className="nav-btn" onClick={() => setActiveSection('itemsMenu')}>Items Menú</button>
        <button className="nav-btn" onClick={() => setActiveSection('pedidos')}>Pedidos</button>
      </nav>

      {activeSection === 'vendedores' && (
        <TableSection
          title="Vendedores"
          items={vendedores}
          onEdit={handleOpenModal}
          onDelete={handleDelete}
          onCreate={() => handleOpenModal()}
          searchTerm={searchTerm}
          setSearchTerm={setSearchTerm}
        />
      )}
      {activeSection === 'clientes' && (
        <TableSection
          title="Clientes"
          items={clientes}
          onEdit={handleOpenModal}
          onDelete={handleDelete}
          onCreate={() => handleOpenModal()}
          searchTerm={searchTerm}
          setSearchTerm={setSearchTerm}
        />
      )}
      {activeSection === 'itemsMenu' && (
        <TableSection
          title="Items Menú"
          items={itemsMenu}
          onEdit={handleOpenModal}
          onDelete={handleDelete}
          onCreate={() => handleOpenModal()}
          filter={filter}
          handleFilterChange={handleFilterChange}
        />
      )}
      {activeSection === 'pedidos' && (
        <TableSection
          title="Pedidos"
          items={pedidos}
          onEdit={handleOpenModal}
          onDelete={handleDelete}
          onCreate={() => handleOpenModal()}
          searchTerm={searchTerm}
          setSearchTerm={setSearchTerm}
        />
      )}

      <Modal show={showModal} onClose={handleCloseModal} title={selectedItemId ? `Editar ${activeSection.slice(0, -1)}` : `Crear ${activeSection.slice(0, -1)}`}>
      <form onSubmit={handleSubmit}>
        {activeSection === 'vendedores' && (
          <>
             <input
              type="text"
              name="nombre"
              placeholder="Nombre"
              value={formData.nombre || ''}
              onChange={handleChange}
            />
            <input
              type="text"
              name="direccion"
              placeholder="Direccion"
              value={formData.direccion || ''}
              onChange={handleChange}
            />
            <input
              type="text"
              name="latitud"
              placeholder="Latitud"
              value={formData.latitud || ''}
              onChange={handleChange}
            />
            <input
              type="text"
              name="longitud"
              placeholder="Longitud"
              value={formData.longitud || ''}
              onChange={handleChange}
            />
          </>
        )}
        {activeSection === 'clientes' && (
          <>
            <input
              type="text"
              name="cuit"
              placeholder="CUIT"
              value={formData.cuit || ''}
              onChange={handleChange}
            />
            <input
              type="email"
              name="email"
              placeholder="Correo electrónico"
              value={formData.email || ''}
              onChange={handleChange}
            />
            <input
              type="text"
              name="latitud"
              placeholder="Latitud"
              value={formData.latitud || ''}
              onChange={handleChange}
            />
            <input
              type="text"
              name="longitud"
              placeholder="Longitud"
              value={formData.longitud || ''}
              onChange={handleChange}
            />
          </>
        )}
        {activeSection === 'itemsMenu' && (
          <>
            <input
              type="text"
              name="nombre"
              placeholder="Nombre"
              value={formData.nombre || ''}
              onChange={handleChange}
            />
            <input
              type="text"
              name="descripcion"
              placeholder="Descripción"
              value={formData.descripcion || ''}
              onChange={handleChange}
            />
            <input
              type="number"
              name="precio"
              placeholder="Precio"
              value={formData.precio || ''}
              onChange={handleChange}
            />
            <input
              type="number"
              name="idCategoria"
              placeholder="ID Categoría"
              value={formData.idCategoria || ''}
              onChange={handleChange}
            />
            <input
              type="number"
              name="idVendedor"
              placeholder="ID Vendedor"
              value={formData.idVendedor || ''}
              onChange={handleChange}
            />
            <div>
              <label>
                <input
                  type="checkbox"
                  name="esComida"
                  checked={!!formData.esComida}
                  onChange={(e) => setFormData({ ...formData, esComida: e.target.checked })}
                />
                ¿Es comida?
              </label>
            </div>
          </>
        )}
        {activeSection === 'pedidos' && (
            <>
              <input
                type="text"
                name="tipoPago"
                placeholder="Tipo de Pago"
                value={formData.tipoPago || ''}
                onChange={handleChange}
              />
              <input
                type="text"
                name="estado"
                placeholder="Estado"
                value={formData.estado || ''}
                onChange={handleChange}
              />
              <input
                type="number"
                name="idCliente"
                placeholder="ID Cliente"
                value={formData.idCliente || ''}
                onChange={handleChange}
              />
            </>
          )}
        
        <button type="submit" className="btn">{selectedItemId ? 'Guardar Cambios' : 'Crear'}</button>
        <button className="btn cancel" type="button" onClick={handleCloseModal}>Cancelar</button>
      </form>
      </Modal>
    </div>
  );
}

export default App;
