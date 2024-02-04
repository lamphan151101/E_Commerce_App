package com.project.shopapp.repositories;

import com.project.shopapp.models.CateGory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CateGory, Long> {

}
