package bettercare.wic.service.marshaller;

import bettercare.wic.model.WicOrderRepresentation;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

@Provider
@Produces({"application/wicOrderRepresentation+json"})
public class JasonMarshallerForWicOrderRepresentation extends JsonMarshaller<WicOrderRepresentation> {

}
