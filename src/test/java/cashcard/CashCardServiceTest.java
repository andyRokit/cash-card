package cashcard;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CashCardServiceTest {
    @InjectMocks
    private CashCardService underTest;

    @Mock
    private CashCardRepository cashCardRepository;

    @Captor
    private ArgumentCaptor<CashCard> cashCardCaptor;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void shouldCreate() throws Exception {
        underTest.create();
        verify(cashCardRepository).save(cashCardCaptor.capture());
        final CashCard savedValue = cashCardCaptor.getValue();
        assertThat(savedValue, is(notNullValue()));
        assertThat(savedValue.getBalance(), is(new BigDecimal(0)));
    }

    @Test
    public void shouldLoad() throws Exception {
        final CashCard existingValue = CashCard.builder().balance(new BigDecimal(10.00)).build();
        when(cashCardRepository.findOne(1)).thenReturn(existingValue);
        underTest.load(1, new BigDecimal(10.00));
        verify(cashCardRepository).save(cashCardCaptor.capture());
        final CashCard savedValue = cashCardCaptor.getValue();
        assertThat(savedValue, is(notNullValue()));
        assertThat(savedValue.getBalance(), is(new BigDecimal(20.00)));
    }

    @Test
    public void shouldSpend() throws Exception {
        final CashCard existingValue = CashCard.builder().balance(new BigDecimal(10.00)).build();
        when(cashCardRepository.findOne(1)).thenReturn(existingValue);
        underTest.spend(1, new BigDecimal(5.00));
        verify(cashCardRepository).save(cashCardCaptor.capture());
        final CashCard savedValue = cashCardCaptor.getValue();
        assertThat(savedValue, is(notNullValue()));
        assertThat(savedValue.getBalance(), is(new BigDecimal(5.00)));
    }

    @Test
    public void shouldThrowInsufficentFundsException() throws Exception {
        expectedEx.expect(InsufficentFundsException.class);
        final CashCard existingValue = CashCard.builder().balance(new BigDecimal(10.00)).build();
        when(cashCardRepository.findOne(1)).thenReturn(existingValue);
        underTest.spend(1, new BigDecimal(15.00));
    }

}
