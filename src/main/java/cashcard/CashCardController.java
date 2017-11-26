package cashcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@RestController
@Validated
@RequestMapping("/cash-card")
public class CashCardController {
    @Autowired
    private CashCardService cashCardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CashCard create() {
        return cashCardService.create();
    }

    @PostMapping("/{id}/load")
    public void load(
            @PathVariable final long id,
            @RequestBody @Digits(integer=5, fraction=2) final BigDecimal amount) {
        cashCardService.load(id, amount);
    }

    @PostMapping("/{id}/spend")
    public void spend(
            @PathVariable final long id,
            @RequestBody @Digits(integer=5, fraction=2) final BigDecimal amount) {
        cashCardService.spend(id, amount);
    }
}
