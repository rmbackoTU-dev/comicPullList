# 🏗️ Build & Deployment Instructions

## Assumptions

This build instructions assumes you have cloned the repository using git into a development folder such as ~/dev/ComicPullList/ 

_Note: As of this writing **(May, 10th, 2026)** This is not a production build all features may not be fully verified to work._

## Server Dependencies

This project  has been verified on the following server‑side components:

- **Apache Tomcat 11.0.15** — servlet container for running the WAR  
- **MySQL 8.4.8-0ubuntu0.25.10.1 (Ubuntu)** — relational database backend
- **Ubuntu 24.04 (Noble Numbat) or newer**

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

_Note: As of this install openjdk is version 25.0.3-ea you can use the following to get the latest openjdk_

```bash
sudo apt install default-jdk -y
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
    echo export JAVA_HOME="/usr/lib/jvm/java-21-openjdk-amd64/"

   ```

   **4. Restart your terminal**

   You can also choose to run 

   ```bash
      source ~/.bashrc
   ```

   **5. Rerun the java -version and the echo JAVA_HOME command to check that the version reflects what is in the Java_Home environment variable**

   ```bash
      java -version
      echo $JAVA_HOME
   ```



### Installing Apache Tomcat on Ubuntu:
📦 Apache Tomcat 11.0.15 Installation Guide (Ubuntu)

This guide walks through installing **Apache Tomcat 11.0.5** on Ubuntu. For the purpose of version 0.1.0 of this build Tomcat exist in a development directory. More robust build instructions will be added in a future update.

---

**1. Create a dedicated Environment directory to run tomcat out of**

This is a test directory which you can use to run the Tomcat server from.

I like to use a Environment directory in my dev folder

```bash
mkdir ~/dev/Environment
cd ~/dev/Environment
mkdir tomcat
```


**2. (Optional) Create a Dedicated Tomcat User**

This user will run the Tomcat service. Because a user can use the startup.sh and shutdown.sh adding a user adds little value. If you plan on migrating to a more production or outward facing hosting of this application adding a service account can be useful.

```bash
sudo useradd -m -U -d ~/dev/Environment/tomcat -s /bin/false tomcat
```

To test the user exist grep the /etc/passwd file

```bash
 grep 'tomcat:' /etc/passwd
```

---

**3. Download Apache Tomcat 11**

_Note: As of this writing **Tomcat** is verion 11.0.22 the minor version should not effect deployment_

Navigate to `~/dev/Environment` folder and download the tarball:

```bash
cd ~/dev/Environment/
wget https://dlcdn.apache.org/tomcat/tomcat-11/v11.0.22/bin/apache-tomcat-11.0.15.tar.gz
```

**4. Validate the download**

After you download the file download the SHA512 file to make sure the file was not corrupted
```bash
wget hhttps://downloads.apache.org/tomcat/tomcat-11/v11.0.22/bin/apache-tomcat-11.0.22.tar.gz.sha512
sha512sum -c apache-tomcat-11.0.22.tar.gz
```

If the sha512 sum command returns <span style="color: green">apache-tomcat-11.0.22.tar.gz:OK</span> then the file is ok proceed to step 5.  If you recieve a <span style="color: red">apache-tomcat-11.0.22.tar.gz:FAILED</span>. Then the file is corrupt and should be re-downloaded.

**5.  Unpack and  unzipping the files**    
```bash
tar -xvf apache-tomcat-11.0.22.tar.gz
```

**6. To test your server install**
```bash
cd apache-tomcat-11.0.22/bin
./startup.sh
```
Then in a web browser navigate to http://localhost:8080/ if you see a tomcat website your tomcat server successfully starts.

**7. Shut down the server after the test using the shutdown script**
```bash
cd apache-tomcat-11.0.22/bin
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
    echo export CATALINA_BASE='~/dev/Environment/apache-tomcat-11.0.22/'
    echo export CATALINA_HOME='~/dev/Environment/apache-tomcat-11.0.22/

   ```

   You may choose to replace the ~ with your home directory though bash should resolve it

  **3. Restart your terminal**

   You can also choose to run 

   ```bash
      source ~/.bashrc
   ```
  **4. Test the setting by echoing out the variables**

  ```bash
    echo $CATALINA_BASE
    echo $CATALINA_HOME
  ```
---
# 🗄️ MySQL Installation & Configuration (Ubuntu 24.0.4 or higher)

This section installs the latest version of mysql **MySQL Server 8.4.8-0ubuntu1** (as of this writting 5/10/2026), configures the **comicapp** service account, and prepares the **comicBook_DB** schema for the application.

---

**1. Install MySQL Server**

```bash
sudo apt update
sudo apt install -y mysql-server
```

**2. Verify the install**

```bash
sudo mysql -version
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
Note if you set password strength 2 in step 3. you will be required to create a password that has the following:

* **>= 8 charcters**
* **mixed case**
* **One special character a number or symbol**

**6.a Grant the new account priviledges to the comic book database**

  After the account is created use the below SQL to allow the account full priviledges on the Comic Book database only.


```SQL
GRANT ALL PRIVILEGES ON comicBook_DB.* TO 'comicDBService'@'localhost';
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
mysql -u comicDBService -p comicBook_DB
```


**8. (Optional) Test the SQL Scripts**

This project has a few test scripts which preloads the database with a schema to test and can be used to destroy the data after each test.

To run the scripts do the following:

To create the schema do the following
```bash
cd ~/dev/comicPullList/src/test/resources/sql-scripts
mysql -u comicDBService -p comicBook_DB < ./create_schema.sql
```

verify by logging in as comicDBService and issuing the following SQL command
```SQL
USE comicBook_DB
SHOW TABLES;
```

You should see a comic_Issue table

To futher verify use the following
```SQL
  DESCRIBE comic_Issue;
```
There should be 5 fields.

To delete the schema after testing do the following:
```bash
cd ~/dev/comicPullList/src/test/resources/sql-scripts
mysql -u comicDBService -p comicBook_DB < ./create_schema.sql
```

Again to verify login as comicDBService and run the following command
```SQL
USE comicBook_DB
SHOW TABLES;
```

You should no longer see the tables.

**9. Check MySQL Starts on Boot (Server settings)**

Run the following commands to ensure MySQL will start on boot

**NOTE** __MySQL registers the mysql service with systemctl on installation in Ubuntu__

```bash
sudo systemctl enable mysql
sudo systemctl status mysql
```
 
 The status command should show a status of <span style="color: blue">"Server is operational"</span>

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



---
# 🧪 Setting up secrets for Testing

In order to use this application you should create and set up a properties.config file in 
<span style="color: yellow;">./src/resources/configs/</span> by doing the following

1. Create the file
```bash
   mkdir resources/configs/
   cd resources/configs
   touch resources/configs/properties.config
```

2. In your favorite file editor add the following to the file. The DB URL will depend on your Mysqld.conf file. Choose your own password (DB_PASSWORD) as is appropriate for your use case.

```bash
DB_URL="mysql://localhost:3306/comicBook_DB"
DB_USER="comicDBService"
DB_PASSWORD="<STRONG_PASSWORD_HERE>"
```

3. Add a line to the .gitignore to remove this file from git commits:
```bash
 touch .gitignore
 echo "src/main/resources/configs/* > .gitignore
```

**_Note: Generally most things in the config file can safely be removed from git commits_**

<span style="color: red"><em>IMPORTANT: never commit your properties.config to git</em></span>

---
# Building/Running test with Maven

_**Note: implement  the previous Setting up Secrets for Testing first or this section will result in a <span style="color: red">BUILD FAILURE</span>**_

Run your first build:

'''bash
  mvn clean package
'''
This command does the following:

Removes previous build artifacts
Downloads dependencies
Compiles the project
Runs tests (if present)
Packages the application into a WAR file
If all goes well you should recieve a green BUILD SUCCESS

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

❗ If you get the error message:
    * Access denied for user   
       Use the below command as root user

      ```SQL
      SHOW GRANTS FOR 'comicDBService'@'localhost'
       ```   


❗ If MySQL won't start
   User the following commands to restart mysql and check logs using journalctl   

   ```bash
       sudo systemctl restart mysql
       sudo journalctl -u mysql
   ```

❗ If the schema does not load   
    Ensure you are using the path to ./test/resources/sql-scripts to load and destroy the schema.

❗ If during a test the resource can not connect make sure the DB_URL matches the url for the mysql server found in
<span style="color: yellow"> /etc/mysql/mysql.conf.d/mysqld.cnf </span>

    _Note: If you have not modified the defaults the value should be bind address 127.0.0.1, and port 3306. This results in a URL of_
   <span style="color: pink"><i>mysql://localhost:3306/comicBook_DB </i></span>

**Maven Troublehsooting steps**

For the following issues use the folloiwng steps
❗ If Dependencies fail to download

```bash
mvn dependency:purge-local-repository
mvn clean package
```

❗ If there is a *Java Version Mismatch*:
   Repeat Java install instructions and ensure java points to the correct JDK

❗ If the Build fails due to missing resources ensure resources are found under

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
sudo chown tomcat:tomcat ~/dev/Environment/apache-tomcat-11.0.15/webapps/comicPullList.war
```









