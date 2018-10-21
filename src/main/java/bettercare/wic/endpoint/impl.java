package bettercare.wic.endpoint;

import bettercare.wic.model.WicOrderRepresentation;
import bettercare.wic.service.SaveWicOrderService;
import bettercare.wic.service.marshaller.WicMediaType;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("/")
public class impl implements api {

    @Override
    public Response list(Request request) {
        System.out.println("list");
        Response
            .ok(null)
            .type(WicMediaType.APPLICATION_WIC_ORDER_JSON)
            .cacheControl(null)
            .header(HttpHeaders.VARY, HttpHeaders.ACCEPT)
            .build();
        return null;
    }

    @Override
    public Response order(Request request, WicOrderRepresentation wicOrderRepresentation) {
        System.out.println("order");
        Response
            .ok(null)
            .type(WicMediaType.APPLICATION_WIC_ORDER_JSON)
            .cacheControl(null)
            .header(HttpHeaders.VARY, HttpHeaders.ACCEPT)
            .build();
        return null;
    }

}
