package com.antman.extensionmarket42.repositories.base;

import com.antman.extensionmarket42.models.extensions.Extension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ExtensionRepository extends PagingAndSortingRepository<Extension,Long> {

    Extension       getById(long id);
    List<Extension> findAllByActiveTrueOrderByName();
    List<Extension> findAllByActiveTrueAndPendingIs(boolean b);
    List<Extension> getAllByActiveTrueAndFeaturedAndPending(boolean featured, boolean pending);

    List<Extension> getAllByActiveIsFalse();

    List<Extension> findTop5ByActiveTrueAndPendingOrderByDownloadsCountDesc(boolean pending);
    List<Extension> findTop5ByActiveTrueAndPendingOrderByAddedOnDesc(boolean pending);

    List<Extension> getAllByActiveTrueAndUserProfile_UserId(long id);

    List<Extension> getAllByActiveTrueAndPendingFalseAndNameContainingIgnoreCase(String name);
    List<Extension> findByActiveTrueAndPendingFalseAndTags_tagTitle(String tag);

    //Pagination
    Page<Extension> getAllByActiveTrueAndPendingFalseAndNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Extension> findByActiveTrueAndPendingFalseAndTags_tagTitle(String tag,Pageable pageable);
    Page<Extension> findAllByPendingFalseAndActiveTrueOrderByLastCommitDesc(Pageable pageable);
    Page<Extension> findAllByPendingFalseAndActiveTrueOrderByAddedOnDesc(Pageable pageable);
    Page<Extension> findAllByPendingFalseAndActiveTrueOrderByName(Pageable pageable);

    Page<Extension> findAllByPendingFalseAndActiveTrueAndNameContainingIgnoreCaseOrderByDownloadsCountDesc(String name, Pageable pageable);
    Page<Extension> findAllByPendingFalseAndActiveTrueAndNameContainsIgnoreCaseOrderByLastCommitDesc(String name,Pageable pageable);
    Page<Extension> findAllByPendingFalseAndActiveTrueAndNameContainingIgnoreCaseOrderByAddedOnDesc(String name,Pageable pageable);

}
