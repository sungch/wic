package bettercare.wic.service.marshaller;

import bettercare.wic.model.WicOrderRepresentation;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

@Provider
@Produces({ WicMediaType.APPLICATION_WIC_ORDER_JSON, MediaType.APPLICATION_JSON})
@Consumes({ WicMediaType.APPLICATION_WIC_ORDER_JSON, MediaType.APPLICATION_JSON })
public class JasonMarshallerForWicOrderRepresentation extends JsonMarshaller<WicOrderRepresentation> {

}
