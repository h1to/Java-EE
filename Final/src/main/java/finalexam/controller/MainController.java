package finalexam.controller;

import io.swagger.annotations.Api;
import finalexam.entity.*;
import finalexam.service.*;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Path("/controller")
@PermitAll
@Api(tags={"todo"})
public class MainController {
    @EJB
    private Services services;

    @GET
    @Path("/getAllSales")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getAllSales() {
        if(services.getAllSale() != null)
            return Response.ok(services.getAllSale()).build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @GET
    @Path("/getSaleById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getSaleById(@PathParam("id") int saleId) {
        if(services.getSaleById(saleId) != null)
            return Response.ok(services.getSaleById(saleId)).build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @PUT
    @Path("/updatePercentage/{SaleId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed("ADMIN")
    public Response updateDiscount(
            @PathParam("SaleId") long id,
            @FormParam("percentage") int percentage) {
        if(services.updateSale(new Sale(id, percentage)))
            return Response.ok().entity("updated").build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addSale")
    @RolesAllowed("ADMIN")
    public Response addSale(Sale sale) {
        if(services.addSale(sale).getSaleId() != null)
            return Response.ok(sale).build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @DELETE
    @Path("/deleteSaleById/{saleId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public Response deleteSaleById(
            @PathParam("saleId") int id) {
        if(services.deleteSaleById(id)) {
            return Response.ok().entity("Deleted").build();
        }
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @GET
    @Path("/selectUsers")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response selectUsers() {
        if(services.selectUsers() != null)
            return Response.ok(services.selectUsers()).build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @GET
    @Path("/selectUser/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response selectUser(@PathParam("id") int userId) {
        if(services.selectUser(userId) != null)
            return Response.ok(services.selectUser(userId)).build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @PUT
    @Path("/updateUser/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed("CLIENT")
    public Response updateUser(
            @PathParam("userId") long id,
            @FormParam("userName") String userName,
            @FormParam("userAge") BigDecimal userAge,
            @FormParam("lastSession") Date lastSession,
            @FormParam("saleId") long saleId
    ) {
        List<Sale> sales = services.getAllSale();
        Sale sale = new Sale();
        for (Sale i: sales) {
            if(saleId == i.getSaleId())
                sale = i;
        }
        if(services.updateUser
                (new User(id, userName, userAge, lastSession.toLocalDate(), sale)))
            return Response.ok().entity("updated").build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/insertUser")
    @RolesAllowed("ADMIN")
    public Response insertUser(User user) {
        if(services.insertUser(user).getUserId() != null)
            return Response.ok(user).build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @DELETE
    @Path("/deleteUser/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public Response deleteUser(
            @PathParam("userId") int id) {
        if(services.deleteUser(id))
            return Response.ok().entity("Deleted").build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @GET
    @Path("/selectItems")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("DEALER")
    public Response selectItems() {
        if(services.selectItems() != null) {
            return Response.ok(services.selectItems()).build();
        }
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("/selectItem/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("DEALER")
    public Response selectItem(@PathParam("id") int itemId) {
        if(services.selectItem(itemId) != null) {
            return Response.ok(services.selectItem(itemId)).build();
        }
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/insertItem")
    @RolesAllowed("DEALER")
    public Response insertItem(Item item){
        if(services.insertItem(item) != null)
            return Response.ok(item).build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @PUT
    @Path("/updateItem/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("DEALER")
    public Response updateChain(
            @PathParam("itemId") long id,
            @FormParam("itemName") String itemName,
            @FormParam("price") BigDecimal price) {
        if(services.updateItem(new Item(id, itemName, price)))
            return Response.ok().entity("updated").build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @DELETE
    @Path("/deleteItem/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("DEALER")
    public Response deleteChain(
            @PathParam("itemId") int id) {
        if(services.deleteItem(id))
            return Response.ok().entity("Deleted").build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @POST
    @Path("/addItem/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed("DEALER")
    public Response addItem (@PathParam("productId") long productId,
                             @FormParam("itemId") long itemId ) {
        if (!services.addItem(productId, itemId).equals("Not added!")) {
            return Response.ok().entity("Item ID = " + itemId + " added to product ID = " + productId).build();
        }
        else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
        }
    }

    @GET
    @Path("/selectStatuses")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "DEALER"})
    public Response selectStatuses() {
        if(services.selectStatuses() != null) {
            return Response.ok(services.selectStatuses()).build();
        }
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("/selectStatus/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "DEALER"})
    public Response selectStatus(@PathParam("id") int statusId) {
        if(services.selectStatus(statusId) != null) {
            return Response.ok(services.selectStatus(statusId)).build();
        }
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/insertStatus")
    @DenyAll
    public Response insertStatus(Status status){
        if(services.insertStatus(status) != null)
            return Response.ok(status).build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @PUT
    @Path("/updateStatus/{statusId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed("ADMIN")
    public Response updateChainCategories(
            @PathParam("statusId") long id,
            @FormParam("status") String status) {
        Status statuss = new Status(id, status);
        if(services.updateStatus(statuss))
            return Response.ok().entity("updated").build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @DELETE
    @Path("/deleteStatus/{statusId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public Response deleteStatus(
            @PathParam("statusId") int id) {
        if(services.deleteStatus(id))
            return Response.ok().entity("Deleted").build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @GET
    @Path("/selectProducts")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "DEALER"})
    public Response selectProducts () {
        if(services.selectProducts() != null) {
            return Response.ok(services.selectProducts()).build();
        }
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("/selectProduct/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "DEALER"})
    public Response selectProduct(@PathParam("id") int prodId) {
        if(services.selectProduct(prodId) != null) {
            return Response.ok(services.selectProduct(prodId)).build();
        }
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500! No such product ID or DB connectivity issues").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/insertProduct")
    @RolesAllowed({"DEALER","CLIENT"})
    public Response insertProduct(Product product){
        if(services.insertProduct(product) != null)
            return Response.ok(product).build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @PUT
    @Path("/updateProduct")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("DEALER")
    public Response updateProduct(Product product) {
        if(services.updateProduct(product))
            return Response.ok().entity("updated").build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @DELETE
    @Path("/deleteProduct/{prodId}")
    @Produces(MediaType.APPLICATION_JSON)
    @DenyAll
    public Response deleteProduct(
            @PathParam("prodId") int id) {
        if(services.deleteProduct(id))
            return Response.ok().entity("Deleted").build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error 500").build();
    }

    @POST
    @Path("/jms")
    @PermitAll
    public String sendMessage(String message) {
        return services.sendJmsMessage(message);
    }

    @GET
    @Path("/jms")
    @RolesAllowed("ADMIN")
    public String getMessage() {
        return services.getMessage();
    }
}
