package Extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import java.sql.Date;
import java.util.Calendar;

public class ExtensionTestSetup {

    public static Extension createExtension(String name, String desc, String version, String repoLink,String downloadLink,
                                            int downloadCount, int pullRequests, int openIssues){
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        Extension extension = new Extension();
        extension.setName(name);
        extension.setDescription(desc);
        extension.setVersion(version);
        extension.setRepoLink(repoLink);
        extension.setDownloadLink(downloadLink);
        extension.setDownloadsCount(downloadCount);
        extension.setPullRequests(pullRequests);
        extension.setOpenIssues(openIssues);
        extension.setLastCommit(date);

        return extension;
    }
}
