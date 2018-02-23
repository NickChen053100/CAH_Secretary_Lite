# CAH_Secretary

CAH Secretary is a Discord Bot made for College Admissions Hub, a Discord server for High School Students, College Applicants, Undergrads, Alumni, and more.
It currently supports features such as:
* A Welcome Message within the default channel for each new member, a direct message linking the new member to a short quiz on the rules of the server
* A dynamic .roles command that lists out all of the available roles for self-assigning
* .role and .derole commands that allow users to self-assign and remove roles

## Getting Started

### Prerequisites

What things you need to install the software and how to install them

* Discord

* An IDE that can run Java

* Discord Bot API [Instructions by JDA here](https://github.com/DV8FromTheWorld/JDA/wiki/3\)-Getting-Started)  

## Before Deployment

* In ```CAH_Secretary/src/main/java/cah/secretary/Bot.java```, replace the token within the parenthesis after .setToken with your own, as shown in the Discord Bot Setup shown above.
* In ```CAH_Secretary/src/main/java/cah/secretary/DotRoles.java```, replace the additions to the invalidRolesList with the roles on your server that you don't want users to be able to self assign.
* Make sure that the Bot role that allows CAH_Secretary to assign roles to members is below all the roles that she shouldn't be able to assign(Server Settings > Roles > drag the role to position)

## Deployment
1. Download, unzip, and open this repository. 
2. Follow the Before Deployment instructions above.
3. * OPTION 1: Run Bot.java
   * OPTION 2: Run the shadowJar
   
## Built With

* [JDA](https://github.com/DV8FromTheWorld/JDA) - The API used

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on their code of conduct, and their process for submitting pull requests. I plan to follow their methodology.

## Authors

* **Nick Chen** - *Creation* - [NickChen053100](https://github.com/NickChen053100)

See also the list of [contributors](https://github.com/NickChen053100/CAH_Secretary/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Thanks to [PurpleBooth](https://github.com/PurpleBooth) for providing the READ.ME template
* Thanks to the [Official JDA Discord Guild](https://discord.gg/0hMr4ce0tIl3SLv5) for guiding me through whatever issues I've had
