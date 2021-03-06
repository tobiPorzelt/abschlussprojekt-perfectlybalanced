package de.hhu.abschlussprojektverleihplattform.controllers.lending;

import de.hhu.abschlussprojektverleihplattform.model.LendingEntity;
import de.hhu.abschlussprojektverleihplattform.model.Lendingstatus;
import de.hhu.abschlussprojektverleihplattform.model.ProductEntity;
import de.hhu.abschlussprojektverleihplattform.model.UserEntity;
import de.hhu.abschlussprojektverleihplattform.service.LendingService;
import de.hhu.abschlussprojektverleihplattform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductLendingRequestsController {

    private final LendingService lendingService;
    private final UserService userService;

    private static final String lendingRequestsURL="/lendingrequests";
    static final String lendingRequestsAcceptURL=lendingRequestsURL+"/accept";
    static final String lendingRequestsRejectURL =lendingRequestsURL+"/reject";

    @Autowired
    public ProductLendingRequestsController(
        LendingService lendingService,
        UserService userService
    ) {
        this.lendingService = lendingService;
        this.userService = userService;
    }

    @GetMapping("/lendingrequests")
    public String getLendingRequestsOverview(Model model, Authentication auth) {
        UserEntity user = (UserEntity) auth.getPrincipal();
        List<LendingEntity> lendings = lendingService.getAllRequestsForUser(user);
        List<LendingEntity> oldLendings = lendingService.getAllLendingsFromUser(user);
        List<LendingEntity> returnedProducts = lendingService.getReturnedLendingFromUser(user);
        model.addAttribute("lendings", lendings);
        model.addAttribute("oldLendings", oldLendings);
        model.addAttribute("returnedProducts", returnedProducts);
        return "productlendingrequests";
    }

    @PostMapping("/lendingrequests/reject")
    public String handleRejection(
        @RequestParam Long id
    ) throws Exception {
        LendingEntity requestedLending = lendingService.getLendingById(id);
        lendingService.denyLendingRequest(requestedLending);
        return "redirect:/lendingrequests";
    }

    @PostMapping("/lendingrequests/accept")
    public String handleAccept(
        @RequestParam Long id
    ) throws Exception {
        LendingEntity requestedLending = lendingService.getLendingById(id);
        lendingService.acceptLendingRequest(requestedLending);
        return "redirect:/lendingrequests";
    }

    @PostMapping("/lendingrequests/rejectReturn")
    public String handleBadReturn(
        @RequestParam Long id
    ) throws Exception {
        LendingEntity requestedLending = lendingService.getLendingById(id);
        lendingService.denyReturnedProduct(requestedLending);
        return "redirect:/lendingrequests";
    }

    @PostMapping("/lendingrequests/acceptReturn")
    public String handleGoodReturn(
        @RequestParam Long id
    ) throws Exception {
        LendingEntity requestedLending = lendingService.getLendingById(id);
        lendingService.acceptReturnedProduct(requestedLending);
        return "redirect:/lendingrequests";
    }


    // TODO:
    // Get/Post Mappings to create a request
    // GetMapping to show all Products the user has borrowed
    // (Get/)Post Mappings to return Products
    // GetMapping to Check returned Prdoduct
    // (Get/)Post Mappings to accept a retuned product or create a conflict
    // All the Views for the Mappings
}
