package server.api;

import commons.Board;
import commons.Card;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.CardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestCardRepositrory implements CardRepository {
    public final List<Card> cards = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name){
        calledMethods.add(name);
    }

    @Override
    public List<Card> findAll() {
        calledMethods.add("findAll");
        return cards;
    }

    @Override
    public List<Card> findAll(Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Card> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Card> findAllById(Iterable<Long> longs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long count() {
        return cards.size();
    }

    @Override
    public void deleteById(Long aLong) {
        // TODO Auto-generated method stub
    }

    @Override
    public void delete(Card entity) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteAll(Iterable<? extends Card> entities) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
    }

    @Override
    public <S extends Card> S save(S entity) {
        call("save");
        entity.id = (long) cards.size();
        cards.add(entity);
        return entity;
    }

    @Override
    public <S extends Card> List<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Card> findById(Long aLong) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        call("existsById");
        return find(id).isPresent();
    }

    private Optional<Card> find(long id){
        return cards.stream().filter(c -> c.id ==id).findFirst();
    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub
    }

    @Override
    public <S extends Card> S saveAndFlush(S entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Card> List<S> saveAllAndFlush(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Card> entities) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteAllInBatch() {
        // TODO Auto-generated method stub
    }

    @Override
    public Card getOne(Long aLong) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Card getById(Long id) {
        call("getById");
        return find(id).get();
    }

    @Override
    public <S extends Card> Optional<S> findOne(Example<S> example) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public <S extends Card> List<S> findAll(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Card> List<S> findAll(Example<S> example, Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Card> Page<S> findAll(Example<S> example, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Card> long count(Example<S> example) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public <S extends Card> boolean exists(Example<S> example) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <S extends Card, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        // TODO Auto-generated method stub
        return null;
    }
}
