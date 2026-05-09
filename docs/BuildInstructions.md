# 🏗️ Build & Deployment Instructions

## Server Dependencies

This project  has been verified on the following server‑side components:

- **Apache Tomcat 11.0.15** — servlet container for running the WAR  
- **MySQL 8.4.8-0ubuntu0.25.10.1 (Ubuntu)** — relational database backend
- **Ubuntu 25.10 (Questing)**

## Install Guides for Dependencies:

### ☕  Installing Java dependencies:


**1. Update System Packages**    

```bash
sudo apt update && sudo apt upgrade -y
```

---

**2. Install Java (Required for Tomcat 11)**  

Tomcat 11 requires **Java 17 or later**.

Install OpenJDK:

```bash
sudo apt install openjdk-17-jdk -y
```

**2.a Verify:**      

```bash
java -version
```

**3.  Set the JRE_HOME:**

   **3.a Get the correct version of java using the  which command**

   ```bash
      which javac
   ```
    **3.b Determine what currently exist in the environment variables.**  

   ```bash
      echo $JAVA_HOME
   ```

   **3.c Backup our .bashrc file**
   ``` bash
       cp ~/.bashrc ~/.bashrc.bak
   ```

   **3.d If nothing is set in environment variables, export the java home settings to bashrc  based on the path retrieved from 3.a do not include the pin folder, this should still provide access to all availble binaries in the folder.**

   ```bash
    echo export JAVA_HOME="/usr/lib/jvm/java-21-openjdk-amd64/

   ```

   **4. Restart your terminal**

   **5. Rerun the java -version command to check that the version reflects what is in the Java_Home environment variable**

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

**1. Copying and unzipping the files**    
```bash
mkdir ~/dev/Environment
cp /tmp/apache-tomcat-11.0.5.tar.gz ~/dev/Environment/
tar -xvf apache-tomcat-11.0.5.tar.gz
```

**2. To test your server install**
```bash
cd apache-tomcat-11.0.5.tar.gz/bin
./startup.sh
```
Then in a web browser navigate to http://localhost:8080/ if you see a tomcat website your tomcat server successfully starts.

**3. Shut down the server after the test using the shutdown script**
```bash
cd apache-tomcat-11.0.5.tar.gz/bin
./startup.sh
```

### Add Tomcat to the Environment variables:

Catalina home needs to be set of for the application to deploy correctly. To set the CATALINA_BASE and CATALINA_HOME directories do the following:

 **1 Backup our .bashrc file**
   ``` bash
       cp ~/.bashrc ~/.bashrc.bak
   ```

   **2. Add lines to export CATALINA_HOME and CATALINA_BASE to your  .bashrc**

   ```bash
    echo export CATALINA_BASE='~/dev/Environment/apache-tomcat-11.0.15/'
    echo export CATALINA_HOME='~/dev/Environment/apache-tomcat-11.0.15/

   ```

   You may choose to replace the ~ with your home directory though bash should resolve it

  **3. Restart your terminal**
  **4. Test the setting by echoing out the variables**

  ```bash
    echo $CATALINA_BASE
    echo $CATALINA_HOME
  ```
---
# 🗄️ MySQL Installation & Configuration (Ubuntu 22.04)

This section installs **MySQL Server 8.x**, configures the **comicapp** service account, and prepares the **comicBook_DB** schema for the application.

---

**1. Install MySQL Server**

```bash
sudo apt update
sudo apt install -y mysql-server
```

**2. Verify the install**

```bash
mysql -version
```

---
 **3. Secure the MySQL Installation**

 First use the secure install scripe to set up a secure install

 ```bash
 sudo mysql_secure_installation
 ```

Responde with the following responses:
    * Validate password plugin: Y
    * Password strength: 2 (strong)
    * Remove anonymous users: Y
    * Disallow remote root login: Y
    * Remove test database: Y
    * Reload privilege tables: Y

---
**4. Configure MySQL as Root**

First login as Root. Use the password from mysql_secure_installation

```bash 
sudo mysql -u root
```

**5. Create an application Database Schema**

Use the following template to create a Database schema to store the application data.

```SQL
CREATE DATABASE comicBook_DB;
```

**6. Create the Application Service Account**

```SQL
CREATE USER 'comicDBService'@'localhost'
  IDENTIFIED BY 'CHANGEME_STRONG_PASSWORD';
```

**6.a Grant the new account priviledges to the comic book database**

  After the account is created use the below SQL to allow the account full priviledges on the Comic Book database only.


```SQL
GRANT ALL PRIVILEGES ON comicBook_DB.* TO 'comicapp'@'localhost';
FLUSH PRIVILEGES;
```

**7. Test logging in as the Service account**

To test the new service account doe the following:

**Exit MySQL:**

```SQL
EXIT;
```

**Test login:**
```bash
mysql -u comicapp -p comicBook_DB
```


**8. (Optional) Test the SQL Scripts**

This project has a few test scripts which preloads the database with a schema to test and can be used to destroy the data after each test.

To run the scripts do the following:

To create the schema do the following
```bash
mysql -u comicDBService -p commicBook_DB < ./sql/create_schema.sql
```

To delete the schema after testing do the following:
```bash
mysql -u comicDBService -p commicBook_DB < ./sql/create_schema.sql
```

**9. Check MySQL Starts on Boot (Server settings)**

Run the following commands to ensure MySQL will start on boot

**NOTE** __MySQL registers the mysql service with systemctl on installation in Ubuntu__

```bash
sudo systemctl enable mysql
sudo systemctl status 
```
 
---

# 🛠️ MAVEN Installation and Building:

**1. Installing Maven**

Run the following to install Maven

```bash
   sudo apt-get update
   sudo apt install -y maven
```

**2. Test the Maven Install**

Check the maven version using the mvn command.

```bash
   mvn --version
```

**3. Go to the project and clean.**

```bash
   cd ./comicpulllist
   mvn clean package
```

This command does the following:
  * Removes previous build artifacts
  * Downloads dependencies
  * Compiles the project
  * Runs tests (if present)
  * Packages the application into a WAR file

If all goes well you should recieve a green
<span style="color: green;">BUILD SUCCESS</span>

---
# 🧪 Setting up secrets for Testing

In order to use this application you should create and set up a properties.config file in 
<span style="color: yellow;">./src/resources/configs/</span> by doing the following

1. Create the file
```bash
   mkdir resources/configs/
   cd resources/configs
   touch properties.config
```

2. In your favorite file editor add the following to the file

```bash
DB_URL="mysql://localhost:3306/comicBook_DB"
DB_USER="comicDBService"
DB_PASSWORD="<STRONG_PASSWORD_HERE>"
```

3. Add a line to the .gitignore to remove this file from git commits:
```bash
 touch .gitignore
 echo "./src/main/resources/configs/*
```

**_Note: Generally most things in the config file can safely be removed from git commits_**

<span style="color: red"><em>IMPORTANT: never commit your properties.config to git</em></span>

---
# Running test with Maven

If you want to build the classes to only test the models, and not run test on Tomcat perform the following:

```bash
 mvn clean compile
```

This should compile everything under /src/main/java without producing a WAR

To compile both the main and test code run
```bash
mvn clean test-compile
```

To then run the unit test only do the following:
```bash
mvn test
```
This runs all the test under /src/test/java

If all the test our good you should see a Test run summary as well as a <span style="color: green;">BUILD SUCCESS</span>

---
# Build and deploy the WAR

To both test on the Tomcat as well as deploy the functional application you need to make a WAR file

Maven will place funcational WAR files in

```code
./target/
```

**1. To build a WAR run the following in the base directory**

```bash
mvn clean package
```

**2. Locate the directory for deploying web applications in Tomcat**

 For the run from Environment folder set up this instructions does the directory should be located in

 <span style="color: yellow"></i>~/dev/Environment/apache-tomcat-11.0.15/webapps/</i></span>

**3. Copy the WAR to the webapps folder**

Use the copy command below:

```bash
sudo cp target/comicPullList.war ~/dev/Environment/apache-tomcat-11.0.15/webapps/
```

To check the copy completed correctly list the directory

```bash
ls -al ~/dev/Environment/apache-tomcat-11.0.15/webapps/
```

**4. Restart your Tomcat installation**

First check if tomcat is running
```bash
ps aux | grep tomcat
```

**4a. If the process is running perform the following scripts**
```bash
~/dev/Environment/apache-tomcat-11.0.15/bin/shutdown.sh
~/dev/Environment/apache-tomcat-11.0.15/bin/startup.sh
```

**4.b If no tomcat process is found simply do the following"**
```bash
~/dev/Environment/apache-tomcat-11.0.15/bin/startup.sh

```
**5. Access the application**
The application can be found at the default tomcat URL if the Server.XML was not modified

http://localhost:8080/comicPullList



---
# 🔧Troubleshooting steps:

**MySQL Troubleshooting steps**

* If you get the error message:
    * Access denied for user   
       Use the below command as root user

      ```SQL
      SHOW GRANTS FOR 'comicDBService'@'localhost'
       ```   


* If MySQL won't start
   User the following commands to restart mysql and check logs using journalctl   

   ```bash
       sudo systemctl restart mysql
       sudo journalctl -u mysql
   ```

* If the schema does not load   
    Ensure you are using the path to ./test/resources/sql-scripts to load and destroy the schema.

* If during a test the resource can not connect make sure the DB_URL matches the url for the mysql server found in
<span style="color: yellow"> /etc/mysql/mysql.conf.d/mysqld.cnf </span>

    _Note: If you have not modified the defaults the value should be bind address 127.0.0.1, and port 3306. This results in a URL of_
   <span style="color: pink"><i>mysql://localhost:3306/comicBook_DB </i></span>

**Maven Troublehsooting steps**

For the following issues use the folloiwng steps
* If Dependencies fail to download

```bash
mvn dependency:purge-local-repository
mvn clean package
```

* If there is a *Java Version Mismatch*:
   Repeat Java install instructions and ensure java points to the correct JDK

* If the Build fails due to missing resources ensure resources are found under

   ./src/main/resources/

   Additionally make sure you create a properties.config file is created in setting up secrets section

**WAR Deployment troubleshooting**  
❗ WAR not deploying
Check Tomcat logs:

```Code
~/dev/Environment/apache-tomcat-11.0.15/logs/catalina.out
```
❗ Permission denied
Ensure the WAR is readable by the tomcat user:

```bash
sudo chown tomcat:tomcat /opt/tomcat/webapps/comicbookapp.war
```









