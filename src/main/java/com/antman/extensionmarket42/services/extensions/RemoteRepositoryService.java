package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.RepositoryDto;

import java.io.IOException;
import java.text.ParseException;

public interface RemoteRepositoryService {
    RepositoryDto getRepositoryInfoByRepoData(String gitUser, String repoName) throws ParseException, IOException;

    RepositoryDto getRepositoryInfoByRepoLink(String repositoryLink) throws IOException;
}
