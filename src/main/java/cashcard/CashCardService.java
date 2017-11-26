package cashcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class CashCardService {
    @Autowired
    private CashCardRepository cashCardRepository;

    public CashCard create() {
        final CashCard cashCard = CashCard.builder().balance(BigDecimal.ZERO).build();
        return cashCardRepository.save(cashCard);
    }


    public void load(final long id, final BigDecimal amount) {
        final CashCard cashCard = cashCardRepository.findOne(id);
        final BigDecimal newBalance = cashCard.getBalance().add(amount);
        cashCard.setBalance(newBalance);
    }

    public void spend(final long id, final BigDecimal amount) {
        final CashCard cashCard = cashCardRepository.findOne(id);
        final BigDecimal newBalance = cashCard.getBalance().subtract(amount);
        if(newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficentFundsException();
        } else {
            cashCard.setBalance(newBalance);
        }
    }
}
