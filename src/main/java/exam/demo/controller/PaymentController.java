package exam.demo.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final IamportClient api;

    public PaymentController() {
        this.api = new IamportClient("3313487377420551","VVnYKWqcoyLdx2A1bV7iGUG4fXIlNBYBZeYc4p6aP8MG2lBOhzKiZeH38FvD8f01pIb5Xr9ibBjbDYQE");
    }

    @ResponseBody
    @RequestMapping(value="/verifyIamport/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(
            Model model
            , Locale locale
            , HttpSession session
            , @PathVariable(value= "imp_uid") String imp_uid) throws IamportResponseException, IOException
    {
        return api.paymentByImpUid(imp_uid);
    }

}