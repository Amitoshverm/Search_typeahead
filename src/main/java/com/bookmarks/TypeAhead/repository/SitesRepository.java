package com.bookmarks.TypeAhead.repository;

import com.bookmarks.TypeAhead.entity.Sites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SitesRepository extends JpaRepository<Sites, Long> {
}
