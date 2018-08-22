package com.antman.extensionmarket42.repositories.base;

import com.antman.extensionmarket42.models.extensions.Extension;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExtensionRepository extends CrudRepository<Extension,Long> {

    List<Extension> getAllByPendingIs(boolean b);
    List<Extension> getAllByFeaturedIs(boolean b);
    List<Extension> findTopTenByOrderByDownloadsCountDesc();
    //List<Extension> getAllByOrder
    // findTop10ByOrderByLevelDesc
}
