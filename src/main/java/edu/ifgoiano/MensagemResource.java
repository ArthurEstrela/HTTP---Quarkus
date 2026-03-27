package edu.ifgoiano;

import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/mensagens")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MensagemResource {

    private static List<Mensagem> database = new ArrayList<>();

    @GET
    public List<Mensagem> listar() {
        return database;
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(Long id) {
        return database.stream()
                .filter(m -> m.id.equals(id))
                .findFirst()
                .map(m -> Response.ok(m).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response criar(Mensagem mensagem) {
        mensagem.timestamp = java.time.LocalDateTime.now();
        database.add(mensagem);
        return Response.status(Response.Status.CREATED).entity(mensagem).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(Long id) {
        boolean removido = database.removeIf(m -> m.id.equals(id));
        return removido ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
