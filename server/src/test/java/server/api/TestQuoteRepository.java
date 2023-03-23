/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package server.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import commons.Quote;
import server.database.QuoteRepository;

public class TestQuoteRepository implements QuoteRepository {

    public final List<Quote> quotes = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    /**
     *
     * @return list of Quotes
     */
    @Override
    public List<Quote> findAll() {
        calledMethods.add("findAll");
        return quotes;
    }

    /**
     *
     * @param sort
     * @return list of quotes
     */
    @Override
    public List<Quote> findAll(Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @param ids must not be {@literal null} nor contain any {@literal null} values.
     * @return list of quotes
     */
    @Override
    public List<Quote> findAllById(Iterable<Long> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @param entities must not be {@literal null} nor must it contain {@literal null}.
     * @return null
     * @param <S>
     */
    @Override
    public <S extends Quote> List<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     */
    @Override
    public void flush() {
        // TODO Auto-generated method stub

    }

    /**
     *
     * @param entity entity to be saved. Must not be {@literal null}.
     * @return null
     * @param <S>
     */
    @Override
    public <S extends Quote> S saveAndFlush(S entity) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @param entities entities to be saved. Must not be {@literal null}.
     * @return null
     * @param <S>
     */
    @Override
    public <S extends Quote> List<S> saveAllAndFlush(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @param entities entities to be deleted. Must not be {@literal null}.
     */
    @Override
    public void deleteAllInBatch(Iterable<Quote> entities) {
        // TODO Auto-generated method stub

    }

    /**
     *
     * @param ids the ids of the entities to be deleted. Must not be {@literal null}.
     */
    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
        // TODO Auto-generated method stub

    }

    /**
     *
     */
    @Override
    public void deleteAllInBatch() {
        // TODO Auto-generated method stub

    }

    /**
     *
     * @param id must not be {@literal null}.
     * @return null
     */
    @Override
    public Quote getOne(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @param id must not be {@literal null}.
     * @return Quote
     */
    @Override
    public Quote getById(Long id) {
        call("getById");
        return find(id).get();
    }

    /**
     *
     * @param id
     * @return Quote
     */
    private Optional<Quote> find(Long id) {
        return quotes.stream().filter(q -> q.id == id).findFirst();
    }

    /**
     *
     * @param example must not be {@literal null}.
     * @return null
     * @param <S>
     */
    @Override
    public <S extends Quote> List<S> findAll(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @param example must not be {@literal null}.
     * @param sort the {@link Sort} specification to
     *             sort the results by, must not be {@literal null}.
     * @return null
     * @param <S>
     */
    @Override
    public <S extends Quote> List<S> findAll(Example<S> example, Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @param pageable
     * @return null
     */
    @Override
    public Page<Quote> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @param entity must not be {@literal null}.
     * @return entity
     * @param <S>
     */
    @Override
    public <S extends Quote> S save(S entity) {
        call("save");
        entity.id = (long) quotes.size();
        quotes.add(entity);
        return entity;
    }

    /**
     *
     * @param id must not be {@literal null}.
     * @return null
     */
    @Override
    public Optional<Quote> findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @param id must not be {@literal null}.
     * @return boolean
     */
    @Override
    public boolean existsById(Long id) {
        call("existsById");
        return find(id).isPresent();
    }

    /**
     *
     * @return long
     */
    @Override
    public long count() {
        return quotes.size();
    }

    /**
     *
     * @param id must not be {@literal null}.
     */
    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

    }

    /**
     *
     * @param entity must not be {@literal null}.
     */
    @Override
    public void delete(Quote entity) {
        // TODO Auto-generated method stub

    }

    /**
     *
     * @param ids must not be {@literal null}. Must not contain {@literal null} elements.
     */
    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        // TODO Auto-generated method stub

    }

    /**
     *
     * @param entities must not be {@literal null}. Must not contain {@literal null} elements.
     */
    @Override
    public void deleteAll(Iterable<? extends Quote> entities) {
        // TODO Auto-generated method stub

    }

    /**
     *
     */
    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub

    }

    /**
     *
     * @param example must not be {@literal null}.
     * @returnnull
     * @param <S>
     */
    @Override
    public <S extends Quote> Optional<S> findOne(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @param example must not be {@literal null}.
     * @param pageable can be {@literal null}.
     * @return null
     * @param <S>
     */
    @Override
    public <S extends Quote> Page<S> findAll(Example<S> example, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @param example the {@link Example} to count instances for. Must not be {@literal null}.
     * @return 0
     * @param <S>
     */
    @Override
    public <S extends Quote> long count(Example<S> example) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     *
     * @param example the {@link Example} to use
     *               for the existence check. Must not be {@literal null}.
     * @return false
     * @param <S>
     */
    @Override
    public <S extends Quote> boolean exists(Example<S> example) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     *
      * @param example must not be {@literal null}.
     * @param queryFunction the query function defining projection, sorting, and the result type
     * @return null
     * @param <S>
     * @param <R>
     */
    @Override
    public <S extends Quote, R> R findBy(Example<S> example,
                                         Function<FetchableFluentQuery<S>, R> queryFunction) {
        // TODO Auto-generated method stub
        return null;
    }
}