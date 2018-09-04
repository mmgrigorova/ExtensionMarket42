package com.antman.extensionmarket42.repositories.base;

import com.antman.extensionmarket42.models.extensions.Extension;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExtensionRepository extends CrudRepository<Extension,Long> {

    Extension       getById(long id);
    List<Extension> findAllByActiveTrue();
    List<Extension> findAllByActiveTrueAndPendingIs(boolean b);
    List<Extension> findAllByActiveTrueAndFeaturedIs(boolean b);
    List<Extension> getAllByActiveTrueAndFeaturedAndPending(boolean featured, boolean fending);

    List<Extension> getAllByActiveIs(boolean b);

    List<Extension> findTop5ByActiveTrueAndPendingOrderByDownloadsCountDesc(boolean pending);
    List<Extension> findTop5ByActiveTrueAndPendingOrderByAddedOnDesc(boolean pending);

    List<Extension> getAllByActiveTrueAndPendingFalseAndNameIs(String name);
    List<Extension> findByTags_tagTitle(String tag);
    //Sorting - approved and pending
    List<Extension> findAllByOrderByDownloadsCountDesc();
    List<Extension> findAllByOrderByAddedOnDesc();
    List<Extension> findAllByOrderByLastCommitDesc();
    List<Extension> findAllByOrderByName();
    //Sorting - approved only
    List<Extension> findAllByPendingFalseOrderByDownloadsCountDesc();
    List<Extension> findAllByPendingFalseOrderByAddedOnDesc();
    List<Extension> findAllByPendingFalseOrderByLastCommitDesc();
    List<Extension> findAllByPendingFalseOrderByName();
}
