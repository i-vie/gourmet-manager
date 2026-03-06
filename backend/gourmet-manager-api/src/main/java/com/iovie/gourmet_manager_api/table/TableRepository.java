package com.iovie.gourmet_manager_api.table;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TableRepository extends JpaRepository<RestaurantTable, Long> {

    Optional<RestaurantTable> findByNumber(Integer number);
    Boolean existsByNumber(Integer number);
    List<RestaurantTable> findByStatus(TableStatus status);
    List<RestaurantTable> findByCapacityGreaterThanEqualAndStatus(Integer capacity, TableStatus status);

}
