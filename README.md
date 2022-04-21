# SAKAI - Online Meetings tool

Tool for Sakai dedicated to the creation, management and use of virtual meeting rooms based on different online videoconferencing providers.

#### Features

- Create and schedule new meetings.
- Control meeting access by site or group participant selection.
- Add meetings to the Sakai Calendar.
- Optional email notifications to participants.
- Simple, fast and responsive interface.


## Current supported providers
- Microsoft Teams

## Prerequisites
You need:
- A server running Sakai.
- For Microsoft Teams integration:
  - A Microsoft Azure Active Directory application.
  - Azure Active Directory users must have the same email in Sakai to be identified as members of the organization.

## MS Teams configuration
You must create a new application in the  _App Registrations_ section of the Azure Active Directory portal. To grant **Meetings tool** access to yoor registered Azure application, you will need a **client secret**. To obtain this, you can access the _Certificates & secrets_ section within the configuration page of your registered Azure application.

Once the app is created, you need to configure the permissions for your registered Azure App in the _API Permissions_ section. To add a new permission you must click _Add a permission_, then select _Microsoft Graph_ and _Application Permissions_. The permissions to enable are defined in the following table:

```sh
Directory.Read.All
Directory.ReadWrite.All
Group.Create
Group.Read.All
Group.ReadWrite.All
OnlineMeetings.ReadWrite.All
Team.Create
Teamwork.Migrate.All
User.Read
User.Read.All
```

To finish, you must click on the _Grant admin consent_ button for your directory.

## Sakai configuration
You have to configure your server's Sakai properties file so that the Meetings tool can access your Azure App. These are the properties you need to configure:


| PROPERTY | VALUE | DESCRIPTION | 
| ------ | ------ | ------ |
| meetings.msteams.authority | https://login.microsoftonline.com/{tenant}/ | {tenant} is the Tenant ID of your Azure Active Directory |
| meetings.msteams.clientId | {clientId} | {clientId} is the Application (Client) Id from your _App registration_  |
| meetings.msteams.secret | {secret} | This is the secret you created under _Certificates & secrets_ section  |
| meetings.msteams.scope | https://graph.microsoft.com/.default | This is a fixed value |
