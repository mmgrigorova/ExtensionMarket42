package com.antman.extensionmarket42.repositories.base;

import com.antman.extensionmarket42.models.extensions.Extension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ExtensionRepository extends PagingAndSortingRepository<Extension,Long> {

    Extension       getById(long id);
    List<Extension> findAllByActiveTrue();
    List<Extension> findAllByActiveTrueAndPendingIs(boolean b);
    List<Extension> findAllByActiveTrueAndFeaturedIs(boolean b);
    List<Extension> getAllByActiveTrueAndFeaturedAndPending(boolean featured, boolean fending);

    List<Extension> getAllByActiveIsFalse();

    List<Extension> findTop5ByActiveTrueAndPendingOrderByDownloadsCountDesc(boolean pending);
    List<Extension> findTop5ByActiveTrueAndPendingOrderByAddedOnDesc(boolean pending);

    List<Extension> getAllByActiveTrueAndPendingFalseAndNameIs(String name);
    List<Extension> getAllByActiveTrueAndUserProfile_UserId(long id);

    List<Extension> getAllByActiveTrueAndPendingFalseAndNameContainingIgnoreCase(String name);
    List<Extension> findByActiveTrueAndPendingFalseAndTags_tagTitle(String tag);
    //Sorting - approved and pending
    List<Extension> findAllByOrderByDownloadsCountDesc();
    List<Extension> findAllByOrderByAddedOnDesc();
    List<Extension> findAllByOrderByLastCommitDesc();
    List<Extension> findAllByOrderByName();
    //Sorting - approved only
    List<Extension> findAllByPendingFalseAndActiveTrueOrderByDownloadsCountDesc();
    List<Extension> findAllByPendingFalseAndActiveTrueOrderByAddedOnDesc();
    List<Extension> findAllByPendingFalseAndActiveTrueOrderByLastCommitDesc();
    List<Extension> findAllByPendingFalseAndActiveTrueOrderByName();
    //Pagination
    Page<Extension> getAllByActiveTrueAndPendingFalseAndNameContainingIgnoreCaseOrderByName(String name, Pageable pageable);
    Page<Extension> findByActiveTrueAndPendingFalseAndTags_tagTitle(String tag,Pageable pageable);
    Page<Extension> findAllByPendingFalseAndActiveTrueOrderByDownloadsCountDesc(Pageable pageable);
    Page<Extension> findAllByPendingFalseAndActiveTrueOrderByLastCommitDesc(Pageable pageable);
    Page<Extension> findAllByPendingFalseAndActiveTrueOrderByAddedOnDesc(Pageable pageable);
    Page<Extension> findAllByPendingFalseAndActiveTrueOrderByName(Pageable pageable);

    Page<Extension> findAllByPendingFalseAndActiveTrueAndNameContainingIgnoreCaseOrderByDownloadsCountDesc(String name, Pageable pageable);
    Page<Extension> findAllByPendingFalseAndActiveTrueAndNameContainsIgnoreCaseOrderByLastCommitDesc(String name,Pageable pageable);
}
