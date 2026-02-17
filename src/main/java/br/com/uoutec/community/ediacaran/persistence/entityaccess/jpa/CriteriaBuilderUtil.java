package br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public class CriteriaBuilderUtil {

	public static <Y> void equals(Path<Y> path, Object value, CriteriaBuilder builder, List<Predicate> and) {
		
	    if(value != null) {
	    	and.add(builder.equal(path, value));
	    }
		
	}

	public static <Y> void isNull(Path<Y> path, CriteriaBuilder builder, List<Predicate> and) {
    	and.add(builder.isNull(path));
	}

	public static <Y> void isNotNull(Path<Y> path, CriteriaBuilder builder, List<Predicate> and) {
    	and.add(builder.isNotNull(path));
	}
	
	public static <Y extends Comparable<? super Y>> void between(Expression<? extends Y> path, Y start, Y end, CriteriaBuilder builder, List<Predicate> and) {
		
	    if(start != null || end != null) {
	    	
	    	if(start != null && end != null) {
			    and.add(builder.between(path, start, end));
	    	}
	    	else
	    	if(start != null) {
			    and.add(builder.greaterThanOrEqualTo(path, start));
	    	}
	    	else
	    	if(end != null) {
			    and.add(builder.lessThanOrEqualTo(path, end));
	    	}
	    	
	    }
		
	}
	
}
