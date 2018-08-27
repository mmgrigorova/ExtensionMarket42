package com.antman.extensionmarket42.restcontrollers;

import com.antman.extensionmarket42.dtos.RepositoryDto;
import com.antman.extensionmarket42.services.extensions.GitHubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
public class GitHubController {
    private GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("git")
    public RepositoryDto showGitHubInfo() throws IOException {
        String repoURl = "https://api.github.com/repos/circleci/circleci-images";
        try {
            return gitHubService.getRepositoryInfo(repoURl);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
