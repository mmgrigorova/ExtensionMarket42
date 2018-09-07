
# ExtensionMarket42  [![CircleCI](https://camo.githubusercontent.com/351d5280b6269709d06e106baf1132415214823d/68747470733a2f2f636972636c6563692e636f6d2f67682f6d6d677269676f726f76612f457874656e73696f6e4d61726b657434322e7376673f7374796c653d736869656c64)](https://circleci.com/gh/mmgrigorova/ExtensionMarket42)
----------
:eyes:[Website](http://extension42.eu-west-3.elasticbeanstalk.com)
----------
### About

Extension Market 42 is an online application which allows developers to browse, upload and download extesnions for ImageStudioCode*. 

It is the final project for Telerik Academy Alpha in Java - 2018.

----------

## Features

### Public Part
  - [x] Application home page which contain 3 lists of extensions (only extensions approved by administrator): 
	- **Featured** (selected by the admis), 
	- **Most Popular** (the most downloaded extensions)
	- **New** (the newest extensions)
- [x] Extension detail page
- [x] User Registration
- [x] Logon Capability
- [x] Filter extensions by name
- [ ] Sort by:
	- [ ] name
	- [ ] number of downloada
	- [ ] upload date
	- [ ] last commit date
  
### Private Part (Logged users only)

Registered users have private area in the web application.
- [x] They could see all extensions that are owned by the currently logged user. Additionally the registered user must be able to:
- [ ] Delete/update/create extension. 

Once extension is created it is “pending” state until an administrator approves it. 

### Administration Part

System administrators have administrative access to the system and permissions to administer all major information objects in the system.
- [x] Approve extension
- [x] Feature extension on the Home page
- [x] Delete/edit extensions
- [x] Disable user accounts

- [x] Trigger Source data synchronization

#### Source Data Synchronization

Source data (GitHub information) is automatically refreshed by a scheduled job. 
Administrators can also trigger source data refresh manually per extension or trigger it for all extensions.
The schedule interval can be configured from application.properties file.

The information about extension synchronization is available in the Admin panel and includes:
- Last successful synchronization
- Successful and failed extension count

## Rest API

We provide Rest API to the public features of the application.

**Commands**

The commands below return a list of five extensions.

Get featured extensions:

    http://extension42.eu-west-3.elasticbeanstalk.com/api/extensions/featured

Get most popular extensions:

    http://extension42.eu-west-3.elasticbeanstalk.com/api/extensions/popular

Get recently added extensions:

    http://extension42.eu-west-3.elasticbeanstalk.com/api/extensions/recent

Example Response

    [{
        "extensionId": 47,
        "name": "Lerna",
        "description": "A tool for managing JavaScript projects with multiple packages. [https://lernajs.io](https://lernajs.io/)",
        "ownerName": "Daniel Stockman",
        "version": "3.5",
        "downloadCount": 177889,
        "repoLink": "www.github.com/lerna/lerna",
        "openIssues": 262,
        "pullRequests": 0,
        "lastCommitDate": "2018-08-02",
        "fileName": "lerna.exe",
        "tags": [
            "python",
            "java",
            "go"
        ]
    }]
    
Download extension file

	http://extension42.eu-west-3.elasticbeanstalk.com/downloadFile/<extensionId>/<filename.file>
	
## Installation Guide
TODO
---- 

*This IDE does not exist.

> Written with [StackEdit](https://stackedit.io/).
