package model.bo;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import model.dao.ClienteDAO;
import model.dao.VeiculoClienteDAO;
import model.beans.VeiculoCliente;



public class VeiculoClienteBO {

	
	private VeiculoClienteDAO veiculoClienteDAO;
    private ClienteDAO clienteDAO;
    
    public VeiculoClienteBO(Connection connection) {
        this.veiculoClienteDAO = new VeiculoClienteDAO(connection);
        this.clienteDAO = new ClienteDAO(connection);
    }
    
    //Verificação de Cliente Existente
    public boolean clienteExiste(int idCliente) throws SQLException {
        return clienteDAO.findById(idCliente) != null;
    }
    
    // Validação de Veículo (não duplicado para o mesmo cliente)
    public boolean veiculoDuplicado(int idCliente, String placa) throws SQLException {
        List<VeiculoCliente> veiculos = veiculoClienteDAO.findAll();
        for (VeiculoCliente veiculo : veiculos) {
            if (veiculo.getIdCliente() == idCliente && veiculo.getPlacaVeiculo().equals(placa)) {
                return true;
            }
        }
        return false;
    }
    
    //Atualização de Veículo
    public void atualizarVeiculo(VeiculoCliente veiculoCliente) throws SQLException {
        if (!clienteExiste(veiculoCliente.getIdCliente())) {
            throw new IllegalArgumentException("Cliente não existe.");
        }       
        veiculoClienteDAO.update(veiculoCliente);
    }
    
    // Método para inserir veículo com validações
    public void inserirVeiculo(VeiculoCliente veiculoCliente) throws SQLException {
        if (!clienteExiste(veiculoCliente.getIdCliente())) {
            throw new IllegalArgumentException("Cliente não existe.");
        }
               
        if (veiculoDuplicado(veiculoCliente.getIdCliente(), veiculoCliente.getPlacaVeiculo())) {
            throw new IllegalArgumentException("Veículo já cadastrado para este cliente.");
        }
        
        veiculoClienteDAO.insert(veiculoCliente);
        
        
        }
    
    public void deletarVeiculo(VeiculoCliente veiculoCliente) {
   	 if (!clienteExiste(veiculoCliente.getIdVeiculoCliente())) {
            throw new IllegalArgumentException("Veiculo Cliente não existe.");
        }
    
    }
}
