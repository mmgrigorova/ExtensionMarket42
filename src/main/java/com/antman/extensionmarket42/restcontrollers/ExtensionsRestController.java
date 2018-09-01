package com.antman.extensionmarket42.restcontrollers;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.models.extensions.Tag;
import com.antman.extensionmarket42.payload.ExtensionRestDto;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/extensions")
public class ExtensionsRestController {
    private ExtensionService extensionService;

    @Autowired
    public ExtensionsRestController(ExtensionService extensionService) {
        this.extensionService = extensionService;
    }


    @GetMapping("/featured")
    public List<ExtensionRestDto> getFeaturedExtensions() {
        return extensionService.getApprovedFeatured(true).stream()
                .map(this::mapExtensionToRestDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/popular")
    public List<ExtensionRestDto> getMostPopularExtensions() {
        return extensionService.getMostPopularApproved().stream()
                .map(this::mapExtensionToRestDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/recent")
    public List<ExtensionRestDto> getRecentExtensions() {
        return extensionService.getRecentlyAdded().stream()
                .map(this::mapExtensionToRestDto)
                .collect(Collectors.toList());
    }

    private ExtensionRestDto mapExtensionToRestDto(Extension extension){
        ExtensionRestDto dto = new ExtensionRestDto();
        dto.setExtensionId(extension.getId());
        dto.setName(extension.getName());
        dto.setOwnerName(extension.getUserProfile().getFirstName(),
                extension.getUserProfile().getLastName());
        dto.setDescription(extension.getDescription());
        dto.setDownloadCount(extension.getDownloadsCount());
        dto.setVersion(extension.getVersion());
        dto.setRepoLink(extension.getRepoLink());
        dto.setOpenIssues(extension.getOpenIssues());
        dto.setPullRequests(extension.getPullRequests());
        dto.setLastCommitDate(String.valueOf(extension.getLastCommit()));
        dto.setFileName(extension.getDownloadLink());
        Set<String> tags = extension.getTags().stream()
                .map(Tag::getTagTitle)
                .collect(Collectors.toSet());
        dto.setTags(tags);
        return dto;
    }
}
