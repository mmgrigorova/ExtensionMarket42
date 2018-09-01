package com.antman.extensionmarket42.payload;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.models.extensions.Tag;

import java.util.Set;
import java.util.stream.Collectors;

public class ExtensionRestDtoMapper {
    public static ExtensionRestDto mapExtensionToRestDto(Extension extension){
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
