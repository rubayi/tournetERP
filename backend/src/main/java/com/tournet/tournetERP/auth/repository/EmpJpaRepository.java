package com.tournet.tournetERP.auth.repository;

import com.tournet.tournetERP.auth.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmpJpaRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public EmpJpaRepository(EntityManager em) {
        this.em = em;
    }

    public User findByUsername(String username){
        User pssdata = new User();

        pssdata = em.createQuery("select u from User u  where username=:username", User.class)
                  .setParameter("username", username)
                  .getSingleResult();

        return pssdata;
    }

    public Boolean existsByUsername(String username){
        User pssdata = em.find(User.class, username);
        if (pssdata != null) {
            return true;
        } else{
            return false;
        }
    }

    public Boolean existsByEmpEmail(String email){
        User pssdata = em.find(User.class, email);
        if (pssdata != null) {
            return true;
        } else{
            return false;
        }
    }

    public List<User> findAllByOrderByEmpBeginDtDesc(){
        List<User> result=em.createQuery("select m from User m  order by empBeginDt desc", User.class)
                .getResultList();
        return result;
    }


    public List<User> findByEmpStatusOrEmpKorOrEmpEngOrUsernameOrderByModifiedDtDesc(
            long empStatus,
            String empKor,
            String empEng,
            String empUsername) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT u FROM User u WHERE 1=1 ");

        if (empStatus != 0) {
            queryBuilder.append("AND u.empStatus = :empStatus ");
        }
        if (empKor != null) {
            queryBuilder.append("AND u.empKor = :empKor ");
        }
        if (empEng != null) {
            queryBuilder.append("AND u.empEng = :empEng ");
        }
        if (empUsername != null) {
            queryBuilder.append("AND u.username = :empUsername ");
        }

        // Adding the ordering
        queryBuilder.append("ORDER BY u.modifiedDt DESC");

        // Creating the query string
        String queryString = queryBuilder.toString();

        // Creating the query
        TypedQuery<User> query = em.createQuery(queryString, User.class);

        // Setting parameters based on their values
        if (empStatus != 0) {
            query.setParameter("empStatus", empStatus);
        }
        if (empKor != null) {
            query.setParameter("empKor", empKor);
        }
        if (empEng != null) {
            query.setParameter("empEng", empEng);
        }
        if (empUsername != null) {
            query.setParameter("empUsername", empUsername);
        }

        List<User> result = query.getResultList();
            return result;

    }
    public void deleteByEmpUuid(int id){
        User pssdata = em.find(User.class, id);
        em.remove(pssdata);
    }

    public User findByEmpUuid(Long id){
        User pssdata=em.find(User.class,id);
        return pssdata; 
    }

    public User findFirstByEmpUuid(int empUuid){
        User pssdata=em.find(User.class,empUuid);
        return pssdata;
    }

    public User save(User user) {
        em.persist(user);
        return user;
    }
}
