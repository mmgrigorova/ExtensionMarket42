package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.RepositoryDto;

import java.io.IOException;

public interface GitHubService {
    RepositoryDto getRepositoryInfo(String repoUrl) throws IOException;
}
