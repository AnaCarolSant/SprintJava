package resources;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import model.bo.VeiculoClienteBO;
import model.beans.VeiculoCliente;

@Path("/veiculo")
public class VeiculoClienteResource {

    private VeiculoClienteBO veiculoClienteBO = new VeiculoClienteBO();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastraResource(VeiculoCliente veiculoCliente, @Context UriInfo uriInfo)
            throws ClassNotFoundException, SQLException {
        veiculoClienteBO.inserirVeiculo(veiculoCliente);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(veiculoCliente.getIdVeiculoCliente()));

        return Response.created(builder.build()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizaResource(VeiculoCliente veiculoCliente, @PathParam("id") int id)
            throws ClassNotFoundException, SQLException {
        veiculoCliente.setIdVeiculoCliente(id); // Define o ID para o objeto que será atualizado
        veiculoClienteBO.atualizarVeiculo(veiculoCliente);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletaResource(@PathParam("id") int id) throws ClassNotFoundException, SQLException {
        veiculoClienteBO.deletarVeiculo(id);

        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVeiculoPorId(@PathParam("id") int id) throws ClassNotFoundException, SQLException {
        VeiculoCliente veiculoCliente = veiculoClienteBO.obterPorId(id);

        if (veiculoCliente != null) {
            return Response.ok(veiculoCliente).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Veículo não encontrado").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<VeiculoCliente> listarResource() throws ClassNotFoundException, SQLException {
        return (ArrayList<VeiculoCliente>) veiculoClienteBO.listar();
    }
}

