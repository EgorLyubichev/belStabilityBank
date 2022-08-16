import by.lev.domain.Card;
import by.lev.repository.card.CardRepository;

public class Main {
    public static void main(String[] args) {
        CardRepository cardRepository = new CardRepository();
        Card card = cardRepository.findById(1L);
        System.out.println(card);
    }
}
