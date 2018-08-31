package com.antman.extensionmarket42.repositories.base;

import com.antman.extensionmarket42.models.extensions.Extension;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExtensionRepository extends CrudRepository<Extension,Long> {

    List<Extension> findAll();
    List<Extension> getAllByPendingIs(boolean b);
    List<Extension> getAllByFeaturedIs(boolean b);
    List<Extension> getAllByFeaturedAndPending(boolean featured, boolean fending);
    List<Extension> findTop5ByPendingOrderByDownloadsCountDesc(boolean pending);
    List<Extension> findTop5ByPendingOrderByAddedOnDesc(boolean pending);
    List<Extension> getAllByNameIs(String name);
    List<Extension> findByTags_tagTitle(String tag);
    //Sorting
    List<Extension> findAllByOrderByDownloadsCountDesc();
    List<Extension> findAllByOrderByAddedOnDesc();
    List<Extension> findAllByOrderByLastCommitDesc();
    List<Extension> findAllByOrderByName();


}
