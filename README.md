# iObjects Security Service

This project is a fully functional security module developed for iObjects. Its functional requirements were based on the Windows Server security system, which should cover some pretty exciting features, like:

- Roles
  - Schedule: days of the week and hours of the day in which the users are allowed to log in.
  - Objects and commands: business objects which the users are allowed to access and the commands which the users are allowed to perform on these objects.
  - Companies: each one of the companies which the users are allowed to access its data in a multi-company enviroment.
- Users
  - Access by user name or e-mail.
  - Account expiration date.
  - Password expiration date.
  - Possibility to deny change of password (demo accounts).
  - Possibility to request to change the password in the next login.
- Many-to-many relationship between roles and users: the users have the sum of the roles' rights in which they are assigned.

![](https://raw.github.com/kleber-maia/iobjects-security-service/master/README.img/1.png)
![](https://raw.github.com/kleber-maia/iobjects-security-service/master/README.img/2.png)
![](https://raw.github.com/kleber-maia/iobjects-security-service/master/README.img/3.png)
![](https://raw.github.com/kleber-maia/iobjects-security-service/master/README.img/4.png)
![](https://raw.github.com/kleber-maia/iobjects-security-service/master/README.img/5.png)
![](https://raw.github.com/kleber-maia/iobjects-security-service/master/README.img/6.png)
![](https://raw.github.com/kleber-maia/iobjects-security-service/master/README.img/7.png)

## About
This project was originally developed as a module for the [Softgroup iManager](http://imanager.com.br). Softgroup is a brazilian company founded by me in 2008 and is still in operation. iManager is an ERP/CRM/BI solution used by hundreds of small retail companies in Brazil.

Although the source code is really well documented, all the comments (and the user interface as well) were published as is: in brazilian portuguese. I apologize for any inconvenience, but I'm afraid I'll not have spare time enough to work on any kind of translation.

## Technical requisites
- [iObjects](https://github.com/kleber-maia/iobjects) framework
- Java 1.6 or higher
- Netbeans 8.0 or higher.

If you want to use Eclipse or another IDE:
- Add **iobjects/build/web/WEB-INF/classes** to the project's compile libraries;
- Make sure to build the project using ".iobjects" extension instead of ".war", ex: securityservice.iobjects;
- Distribute the builded project's archive to iobjects/web.work/extensions directory.

## Users and passwords
The following users will be automatically created on the first run. Additionally, you'll be able to insert any users and roles that you need by using the module's interface.
- Username: **@Super Usuário**, Password: **superusuario**
- Username: **Administrador**, Password: **administrador**
- Username: **Convidado**, Password: **convidado**
