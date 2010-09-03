package org.betting.customer.repositoryimpl;

import static org.betting.customer.domain.CustomerBetProperties.totalAmount;
import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import org.betting.customer.domain.CustomerBet;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for CustomerBet
 */
@Repository("customerBetRepository")
public class CustomerBetRepositoryImpl extends CustomerBetRepositoryBase {
    public CustomerBetRepositoryImpl() {
    }

    @Override
    public List<CustomerBet> findHighStakesCustomers(Double limit) {
        List<ConditionalCriteria> criteria = criteriaFor(CustomerBet.class).withProperty(totalAmount()).greaterThan(
                limit).build();
        List<CustomerBet> result = findByCondition(criteria);
        return result;

    }
}
