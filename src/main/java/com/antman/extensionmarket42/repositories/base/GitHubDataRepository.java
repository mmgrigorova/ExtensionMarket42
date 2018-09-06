package com.antman.extensionmarket42.repositories.base;

import com.antman.extensionmarket42.models.repository.DataRefresh;
import org.springframework.data.repository.CrudRepository;

public interface GitHubDataRepository extends CrudRepository<DataRefresh, Integer> {
    DataRefresh findFirstByOrderByLastRefreshDateDesc();
}
