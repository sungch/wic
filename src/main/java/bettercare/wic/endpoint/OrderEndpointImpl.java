package bettercare.wic.endpoint;

import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.service.marshaller.WicMediaType;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import static bettercare.wic.service.marshaller.WicMediaType.APPLICATION_WIC_ORDER_JSON;

@Component
@Path("/order")
public class OrderEndpointImpl implements OrderEndpoint {

    @Override
    public Response list(Request request) {
        WicOrderRepresentation model = new WicOrderRepresentation();
        model.setName("SUNGCH");
        return Response.ok(model, WicMediaType.APPLICATION_WIC_ORDER_JSON).type(WicMediaType.APPLICATION_WIC_ORDER_JSON)
                .header(HttpHeaders.VARY, HttpHeaders.ACCEPT).build();
    }

//    @Override
//    public Response order(Request request, WicOrderRepresentation wicOrderRepresentation) {
//        Response.ok("{Order}").type(APPLICATION_WIC_ORDER_JSON).cacheControl(null).header(HttpHeaders.VARY, HttpHeaders.ACCEPT).build();
//        return null;
//    }
}
