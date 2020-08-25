package io.github.zahrarsl.exam.model.dao;

import io.github.zahrarsl.exam.model.entity.AcademicUser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public interface AcademicUserSpecifications extends JpaSpecificationExecutor<AcademicUser> {

    static Specification<AcademicUser> findMaxMatch(AcademicUser user){
        return (Specification<AcademicUser>) (root, criteriaQuery, builder) -> {
            CriteriaQuery<AcademicUser> resultCriteria = builder.createQuery(AcademicUser.class);

            List<Predicate> predicates = new ArrayList<Predicate>();
            if(!user.getFirstName().equals("")){
                predicates.add(builder.equal(root.get("firstName"), user.getFirstName()));
            }
            if(!user.getLastName().equals("")){
                predicates.add(builder.equal(root.get("lastName"), user.getLastName()));
            }
            if(!user.getRole().equals("")){
                predicates.add(builder.equal(root.get("role"), user.getRole()));
            }
            if(!user.getEmail().equals("")){
                predicates.add(builder.equal(root.get("email"), user.getEmail()));
            }

            resultCriteria.select(root).where(predicates.toArray(new Predicate[0]));
            return resultCriteria.getRestriction();
        };
    }
}
