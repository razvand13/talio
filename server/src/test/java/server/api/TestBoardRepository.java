//package server.api;
//
//import commons.Board;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.repository.query.FluentQuery;
//import server.database.BoardRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Function;
//
//public class TestBoardRepository implements BoardRepository {
//    public final List<Board> boards = new ArrayList<>();
//    public final List<String> calledMethods = new ArrayList<>();
//
//    private void call(String name){
//        calledMethods.add(name);
//    }
//
//    @Override
//    public List<Board> findAll() {
//        call("findAll");
//        return boards;
//    }
//
//    @Override
//    public List<Board> findAll(Sort sort) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public Page<Board> findAll(Pageable pageable) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public List<Board> findAllById(Iterable<Long> longs) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public long count() {
//        return boards.size();
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        boards.removeIf(b -> b.id == id);
//        call("deleteById");
//    }
//
//    @Override
//    public void delete(Board entity) {
//        // TODO Auto-generated method stub
//    }
//
//    @Override
//    public void deleteAllById(Iterable<? extends Long> longs) {
//        // TODO Auto-generated method stub
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends Board> entities) {
//        // TODO Auto-generated method stub
//    }
//
//    @Override
//    public void deleteAll() {
//        boards.removeAll(boards);
//        call("deleteAll");
//    }
//
//    @Override
//    public <S extends Board> S save(S entity) {
//        call("save");
//        entity.id = (long) boards.size();
//        boards.add(entity);
//        return entity;
//    }
//
//    @Override
//    public <S extends Board> List<S> saveAll(Iterable<S> entities) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public Optional<Board> findById(Long id) {
//        return find(id);
//    }
//
//    @Override
//    public boolean existsById(Long id) {
//        call("existsById");
//        return find(id).isPresent();
//    }
//
//    private Optional<Board> find(long id){
//        return boards.stream().filter(b -> b.id ==id).findFirst();
//    }
//
//    @Override
//    public void flush() {
//        // TODO Auto-generated method stub
//    }
//
//    @Override
//    public <S extends Board> S saveAndFlush(S entity) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public <S extends Board> List<S> saveAllAndFlush(Iterable<S> entities) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public void deleteAllInBatch(Iterable<Board> entities) {
//        // TODO Auto-generated method stub
//    }
//
//    @Override
//    public void deleteAllByIdInBatch(Iterable<Long> longs) {
//        // TODO Auto-generated method stub
//    }
//
//    @Override
//    public void deleteAllInBatch() {
//        // TODO Auto-generated method stub
//    }
//
//    @Override
//    public Board getOne(Long aLong) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public Board getById(Long id) {
//        call("getById");
//        return find(id).get();
//    }
//
//    @Override
//    public <S extends Board> Optional<S> findOne(Example<S> example) {
//        // TODO Auto-generated method stub
//        return Optional.empty();
//    }
//
//    @Override
//    public <S extends Board> List<S> findAll(Example<S> example) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public <S extends Board> List<S> findAll(Example<S> example, Sort sort) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public <S extends Board> Page<S> findAll(Example<S> example, Pageable pageable) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public <S extends Board> long count(Example<S> example) {
//        // TODO Auto-generated method stub
//        return 0;
//    }
//
//    @Override
//    public <S extends Board> boolean exists(Example<S> example) {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
//    @Override
//    public <S extends Board, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//}
