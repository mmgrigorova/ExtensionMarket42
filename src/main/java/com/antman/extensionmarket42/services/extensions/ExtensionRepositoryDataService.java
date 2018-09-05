package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.payload.RepositorySyncStatistics;
import javassist.NotFoundException;

import java.io.IOException;

public interface ExtensionRepositoryDataService {
    RepositorySyncStatistics refreshRepositoryInfoAllActiveExtensions();
    Extension refreshRepositoryInfoPerExtension(Long id) throws NotFoundException, IOException;
}
