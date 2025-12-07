package com.backend.ecommerce.Repository;

import com.backend.ecommerce.Model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {
    Store findByStoreAdmin_Id(Long adminId);
}
