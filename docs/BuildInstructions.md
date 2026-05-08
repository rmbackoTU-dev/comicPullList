# 🏗️ Build & Deployment Instructions

## Server Dependencies

This project  has been verified on the following server‑side components:

- **Apache Tomcat 11.0.15** — servlet container for running the WAR  
- **MySQL 8.4.8-0ubuntu0.25.10.1 (Ubuntu)** — relational database backend
- **Ubuntu 25.10 (Questing)**

## Install Guides for Dependencies:

### Installing Java dependencies:


** 1. Update System Packages* *    

```bash
sudo apt update && sudo apt upgrade -y
```

---

** 2. Install Java (Required for Tomcat 11)**  

Tomcat 11 requires **Java 17 or later**.

Install OpenJDK:

```bash
sudo apt install openjdk-17-jdk -y
```

** 2.a Verify: **      

```bash
java -version
```

** 3.  Set the JRE_HOME: **

   ** 3.a Get the correct version of java using the  which command **

   ```bash
      which javac
   ```
    ** 3.b Determine what currently exist in the environment variables. **  

   ```bash
      echo $JAVA_HOME
   ```

   ** 3.c Backup our .bashrc file
   ``` bash
       cp ~/.bashrc ~/.bashrc.bak
   ```

   ** 3.d If nothing is set in environment variables, export the java home settings to bashrc  based on the path retrieved from 3.a do not include the pin folder, this should still provide access to all availble binaries in the folder.**

   ```bash
    echo export JAVA_HOME="/usr/lib/jvm/java-21-openjdk-amd64/

   ```

   ** 4. Restart your terminal**

   ** 5. Rerun the java -version command to check that the version reflects what is in the Java_Home environment variable

   ```bash
      java -version
   ```



### Installing Apache Tomcat on Ubuntu:
📦 Apache Tomcat 11.0.5 Installation Guide (Ubuntu)

This guide walks through installing **Apache Tomcat 11.0.5** on Ubuntu. For the purpose of version 0.1.0 of this build Tomcat exist in a development directory. More robust build instructions will be added in a future update.

---


---

**1. Create a Dedicated Tomcat User**

This user will run the Tomcat service.

```bash
sudo useradd -m -U -d /opt/tomcat -s /bin/false tomcat
```

---

**2. Download Apache Tomcat 11.0.5**

Navigate to `/tmp` and download the tarball:

```bash
cd /tmp
wget https://dlcdn.apache.org/tomcat/tomcat-11/v11.0.5/bin/apache-tomcat-11.0.5.tar.gz
```

---

### Making an environment directory and running from environment 

The below test should be only used when running a test build of the comic book database. 

I utilize the a dev folder in my home folder for all test environments. I reccomend using what best fits your needs.

** 1. Copying and unzipping the files**    
```bash
mkdir ~/dev/Environment
cp /tmp/apache-tomcat-11.0.5.tar.gz ~/dev/Environment/
tar -xvf apache-tomcat-11.0.5.tar.gz
```

** 2. To test your server install **
```bash
cd apache-tomcat-11.0.5.tar.gz/bin
./startup.sh
```
Then in a web browser navigate to http://localhost:8080/ if you see a tomcat website your tomcat server successfully starts.

**3. Shut down the server after the test using the shutdown script **
```bash
cd apache-tomcat-11.0.5.tar.gz/bin
./startup.sh
```

### Add Tomcat to the Environment variables:

Catalina home needs to be set of for the application to deploy correctly. To set the CATALINA_BASE and CATALINA_HOME directories do the following:

 ** 1 Backup our .bashrc file
   ``` bash
       cp ~/.bashrc ~/.bashrc.bak
   ```

   ** 2. Add lines to export CATALINA_HOME and CATALINA_BASE to your  .bashrc**

   ```bash
    echo export CATALINA_BASE='~/dev/Environment/apache-tomcat-11.0.15/'
    echo export CATALINA_HOME='~/dev/Environment/apache-tomcat-11.0.15/

   ```

   You may choose to replace the ~ with your home directory though bash should resolve it

  ** 3. Restart your terminal **
  ** 4. Test the setting by echoing out the variables **

  ```bash
    echo $CATALINA_BASE
    echo $CATALINA_HOME
  ```



