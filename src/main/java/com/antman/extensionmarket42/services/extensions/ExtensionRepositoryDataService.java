package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.models.repository.DataRefresh;
import javassist.NotFoundException;

import java.io.IOException;

public interface ExtensionRepositoryDataService {
    DataRefresh refreshRepositoryInfoAllActiveExtensions();
    Extension refreshRepositoryInfoPerExtension(Long id) throws NotFoundException, IOException;

    DataRefresh getLastSyncData();
}
