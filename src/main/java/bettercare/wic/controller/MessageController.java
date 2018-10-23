package bettercare.wic.controller;

import bettercare.wic.dal.entity.WicOrder;
import bettercare.wic.model.WicOrderRepresentation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Controller + @ResponseBody (converter of string to json) === @RestController
 *
 * Somehow, client has to assume that POST Content-Type is application/json via @RequestBody
 * GET Accept via @RestController
 *
 * Workflow: Client -> DispatcherServlet -> @RequestBody JSON to Java -> @Controller java format
 * Workflow Controller -> @ResponseBody Java to JSON -> DispatcherServlet -> client
 *
 * mvn spring-boot:run
 */


@RestController
public class MessageController {

    // GET localhost:8080/message

    @GetMapping("/message")
    WicOrderRepresentation send() {
        WicOrderRepresentation model = new WicOrderRepresentation();
        model.setName("SUNGCH");
        return model;
    }


    // POST localhost:8080/message

    @PostMapping("/message")
    WicOrderRepresentation echo(@RequestBody WicOrderRepresentation model) {
        model.setName("SUNGCH-ECHO");
        return model;
    }


}
