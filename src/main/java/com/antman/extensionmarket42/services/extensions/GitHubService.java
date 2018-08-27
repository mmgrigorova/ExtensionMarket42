package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.RepositoryDto;

import java.io.IOException;
import java.text.ParseException;

public interface GitHubService {
    RepositoryDto getRepositoryInfo(String gitUser, String repoName) throws ParseException, IOException;
}