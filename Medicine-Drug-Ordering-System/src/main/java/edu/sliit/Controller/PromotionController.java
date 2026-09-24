package edu.sliit.Controller;

import edu.sliit.dto.Promotion;
import edu.sliit.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/promotion")
public class PromotionController {
    final PromotionService service;

    @GetMapping("/get-all")
    public List<Promotion> getPromotions() {

        return service.getPromotions();
    }

    @GetMapping("/search-by-id/{promotionId}")
    public Promotion searchByPromotionId(
            @PathVariable Integer promotionId) {

        return service.searchByPromotionId(promotionId);
    }

    @GetMapping("/search-by-name/{promotionName}")
    public List<Promotion> searchByPromotionName(
            @PathVariable String promotionName) {

        return service.searchByPromotionName(
                promotionName
        );
    }

    @GetMapping("/search-by-status/{status}")
    public List<Promotion> searchByStatus(
            @PathVariable String status) {

        return service.searchByStatus(status);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPromotion(
            @RequestBody Promotion promotion) {

        service.addPromotion(promotion);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updatePromotion(
            @RequestBody Promotion promotion) {

        service.updatePromotion(promotion);
    }

    @DeleteMapping("/delete/{promotionId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletePromotion(
            @PathVariable Integer promotionId) {

        service.deleteByPromotionId(promotionId);
    }
}
