# iObjects Security Service

This project is a fully functional security module developed for iObjects. Its functional requirements were based on the Windows Server security system, which should cover some pretty exciting features, like:

- Roles
  - Schedule: days of the week and hours of the day in which the users are allowed to log in.
  - Objects and commands: business objects which the users are allowed to access and the commands which the users are allowed to perform on these objects.
  - Companies: each company the user has access in a multi-company enviroment.
- Users
  - Many to many relationship with roles.
  - Access by user name or e-mail.
  - Account expiration date.
  - Password expiration date.
  - Possibility to deny change of password (demo accounts).
  - Possibility to request to change the password in the next login.

![](https://raw.github.com/kleber-maia/iobjects-security-service/master/README.img/1.png)
![](https://raw.github.com/kleber-maia/iobjects-security-service/master/README.img/2.png)
![](https://raw.github.com/kleber-maia/iobjects-security-service/master/README.img/3.png)
![](https://raw.github.com/kleber-maia/iobjects-security-service/master/README.img/4.png)
![](https://raw.github.com/kleber-maia/iobjects-security-service/master/README.img/5.png)
![](https://raw.github.com/kleber-maia/iobjects-security-service/master/README.img/6.png)
![](https://raw.github.com/kleber-maia/iobjects-security-service/master/README.img/7.png)

## Technical requisites
- [iObjects](https://github.com/kleber-maia/iobjects) framework
- Java 1.6 or higher
- Netbeans 8.0 or higher.

If you want to use Eclipse or another IDE:
- Add **iobjects/build/web/WEB-INF/classes** to the project's compile libraries;
- Make sure to build the project using ".iobjects" extension instead of ".war", ex: securityservice.iobjects;
- Distribute the builded project's archive to iobjects/web.work/extensions directory.

## Database structure
There is a .sql file on the root of this project which contains all the needed table structure. Although the script was generated from a PostgreSQL database, it should be easy to migrate it to another RDMS of your choice.

## Users and passwords
The following users will be automatically created on the first run. Additionally, you'll be able to insert all users and roles that you need by using the iObjects Security Service module.
- Username: **@Super Usuário**, Password: **superusuario**
- Username: **Administrador**, Password: **administrador**
- Username: **Convidado**, Password: **convidado**
