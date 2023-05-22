package lt.vu.eshop.rest;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/seller")
@Produces(MediaType.APPLICATION_JSON)
public class SellerApi {
    @Inject
    private SellerService sellerService;

    @GET
    @Path("/{id}")
    public SellerDTO getSeller(@PathParam("id")Long id) {
        return sellerService.getById(id);
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public SellerDTO createSeller(SellerDTO sellerDTO) {
        return sellerService.save(sellerDTO);
    }

    @PUT
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public SellerDTO updateSeller(SellerDTO sellerDTO) {
        return sellerService.save(sellerDTO);
    }
}
