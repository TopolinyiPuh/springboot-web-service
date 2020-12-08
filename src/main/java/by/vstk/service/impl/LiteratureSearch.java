package by.vstk.service.impl;

import by.vstk.model.Literature;
import by.vstk.model.LiteratureRemoved;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class LiteratureSearch {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    // Spring will inject here the entity manager object
    @PersistenceContext
    private EntityManager entityManager;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * A basic search for the entity User. The search is done by exact match per
     * keywords on fields name, city and email.
     *
     * @param text The query text.
     */
    public Page<Literature> search(Pageable pageable, String text) {

        // get the full text entity manager
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        // create the query using Hibernate Search query DSL
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Literature.class).get();

        // a very basic query by keywords
        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .onFields("title", "author", "speciality.title", "user.FIO")
                        .matching(text)
                        .createQuery();

        // wrap Lucene query in an Hibernate Query object
        org.hibernate.search.jpa.FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Literature.class);

        // execute search and return results (sorted by relevance as default)
        @SuppressWarnings("unchecked")
        List<Literature> results = jpaQuery.getResultList();


        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
//        List<Literature> list;

//        if (results.size() < startItem) {
//            list = Collections.emptyList();
//        } else {
//            int toIndex = Math.min(startItem + pageSize, results.size());
//            list = results.subList(startItem, toIndex);
//        }

        Page<Literature> literaturePage = new PageImpl<Literature>(results, PageRequest.of(currentPage, pageSize), results.size());

        return literaturePage;

    } // method search

    public Page<LiteratureRemoved> searchRemoved(Pageable pageable, String text) {

        // get the full text entity manager
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        // create the query using Hibernate Search query DSL
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(LiteratureRemoved.class).get();

        // a very basic query by keywords
        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .onFields("title", "author", "speciality.title", "user.FIO")
                        .matching(text)
                        .createQuery();

        // wrap Lucene query in an Hibernate Query object
        org.hibernate.search.jpa.FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, LiteratureRemoved.class);

        // execute search and return results (sorted by relevance as default)
        @SuppressWarnings("unchecked")
        List<LiteratureRemoved> results = jpaQuery.getResultList();


        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
//        List<Literature> list;

//        if (results.size() < startItem) {
//            list = Collections.emptyList();
//        } else {
//            int toIndex = Math.min(startItem + pageSize, results.size());
//            list = results.subList(startItem, toIndex);
//        }

        Page<LiteratureRemoved> literaturePage = new PageImpl<LiteratureRemoved>(results, PageRequest.of(currentPage, pageSize), results.size());

        return literaturePage;

    } // method search


} // class} // class UserSearch