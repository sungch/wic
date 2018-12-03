package bettercare.wic.endpoint;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/")
@RestController
public class DefaultController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    @ResponseBody
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        return String.format("<html><body>" +
                                     "<h2>Error Page</h2>" +
                                     "<div>Status code: <b>%s</b></div>" +
                                     "<div>Exception Message: <b>%s</b></div>" +
                                     "<div>Unsupported resource request for current user</div>" +
                                     "</body></html>", statusCode, exception==null? "N/A": exception.getMessage());
    }


}
