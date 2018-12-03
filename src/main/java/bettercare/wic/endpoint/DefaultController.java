package bettercare.wic.endpoint;


import bettercare.wic.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/")
@RestController
public class DefaultController implements ErrorController {

    @Autowired private ResponseService responseService;

    @Override
    @RequestMapping("/error")
    @ResponseBody
    public String getErrorPath() {
        return "Requested resource is unsupported";
    }
}
