package server.api;

import commons.ListOfCards;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.ListRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestListRepository implements ListRepository {
    public final List<ListOfCards> lists = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name){
        calledMethods.add(name);
    }

    @Override
    public List<ListOfCards> findAll() {
        calledMethods.add("findAll");
        return lists;
    }

    @Override
    public List<ListOfCards> findAll(Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<ListOfCards> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ListOfCards> findAllById(Iterable<Long> longs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long count() {
        return lists.size();
    }

    @Override
    public void deleteById(Long id) {
        lists.removeIf(l->l.id==id);
        call("deleteById");
    }

    @Override
    public void delete(ListOfCards entity) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteAll(Iterable<? extends ListOfCards> entities) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteAll() {
        lists.removeAll(lists);
        call("deleteAll");
    }

    @Override
    public <S extends ListOfCards> S save(S entity) {
        call("save");
        entity.id = (long) lists.size();
        lists.add(entity);
        return entity;
    }

    @Override
    public <S extends ListOfCards> List<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<ListOfCards> findById(Long id) {
        return find(id);
    }

    @Override
    public boolean existsById(Long id) {
        call("existsById");
        return find(id).isPresent();
    }

    private Optional<ListOfCards> find(long id){
        return lists.stream().filter(l -> l.id ==id).findFirst();
    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub
    }

    @Override
    public <S extends ListOfCards> S saveAndFlush(S entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends ListOfCards> List<S> saveAllAndFlush(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<ListOfCards> entities) {
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
    public ListOfCards getOne(Long aLong) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ListOfCards getById(Long id) {
        call("getById");
        return find(id).get();
    }

    @Override
    public <S extends ListOfCards> Optional<S> findOne(Example<S> example) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public <S extends ListOfCards> List<S> findAll(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends ListOfCards> List<S> findAll(Example<S> example, Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends ListOfCards> Page<S> findAll(Example<S> example, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends ListOfCards> long count(Example<S> example) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public <S extends ListOfCards> boolean exists(Example<S> example) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <S extends ListOfCards, R> R findBy(Example<S> example,
                                               Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ListOfCards> findByBoardId(long boardId) {
        return null;
    }
}
