package server.api;

import DataStructures.Card;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.database.CardRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

@RestController
    @RequestMapping("/api/cards")
    public class CardController {

        private final Random random;
        private final CardRepository cardRepo;

        public CardController(Random random, CardRepository cardRepo) {
            this.random = random;
            this.cardRepo = cardRepo;
        }


        @GetMapping(path = { "", "/" })
        public List<Card> getAll() {
            return cardRepo.findAll();
        }

        /**
         * listeners for new cards that maps it using an object
         */
        private Map<Object, Consumer<Card>> listener = new HashMap<>();

        @GetMapping(path = { "/updates" })
        public DeferredResult<ResponseEntity<Card>> getCardUpdates() {

            //what gets returned in case of a timeout -> no data, return noContent
            var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            var res = new DeferredResult<ResponseEntity<Card>>(5000L, noContent); //5 sec timeout

            var key = new Object(); // objects are always unique
            listener.put(key, c -> {
                res.setResult(ResponseEntity.ok(c));
            });

            res.onCompletion(() -> {
                listener.remove(key);
            }); //when the request is completed

            //res.onError(); allows us to react to errors or timeouts with .onTimeout()


            return res;
        }


    @GetMapping("/{id}")
    public ResponseEntity<Card> getById(@PathVariable("id") long id) {
        if (id < 0 || !cardRepo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cardRepo.findById(id).get());
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<Card> add(@RequestBody Card card) {

        if (card.title == null || isNullOrEmpty(card.title)) {
            return ResponseEntity.badRequest().build();
        }

        //when a new quote is added, the listeners are informed who are filled by getUpdate
        listener.forEach((k, l) -> l.accept(card));

        Card saved = cardRepo.save(card);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    @GetMapping("rnd")
    public ResponseEntity<Card> getRandom() {
        var cards = cardRepo.findAll();
        var idx = random.nextInt((int) cardRepo.count());
        return ResponseEntity.ok(cards.get(idx));
    }
}
