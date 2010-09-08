package org.betting.customer.repositoryimpl;

import static org.betting.customer.domain.CustomerStatisticsProperties.averageAmount;
import static org.betting.customer.domain.CustomerStatisticsProperties.customerId;
import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import org.betting.customer.domain.CustomerStatistics;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for CustomerStatistics
 */
@Repository("customerStatisticsRepository")
public class CustomerStatisticsRepositoryImpl extends CustomerStatisticsRepositoryBase {
    public CustomerStatisticsRepositoryImpl() {
    }

    @Override
    public List<CustomerStatistics> findHighAverageCustomers(double limit) {
        List<ConditionalCriteria> criteria = criteriaFor(CustomerStatistics.class)
            .withProperty(averageAmount())
            .greaterThan(limit)
            .orderBy(customerId())
            .build();
        return findByCondition(criteria);
    }
}
