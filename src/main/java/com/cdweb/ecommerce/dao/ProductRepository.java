package com.cdweb.ecommerce.dao;

import com.cdweb.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
public interface ProductRepository extends JpaRepository<Product,Long> {
    //gọi phương thức có param
    //  search/findByCategory?id=id
    Page<Product> findByCategoryId(@Param("id") Long id, Pageable pageable);
    //  search/findByNameContaining?name=name
    Page<Product> findByNameContaining(@Param("name") String name, Pageable pageable);
}