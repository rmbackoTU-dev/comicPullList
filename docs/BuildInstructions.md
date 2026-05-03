# 🏗️ Build & Deployment Instructions

## Server Dependencies

This project  has been verified on the following server‑side components:

- **Apache Tomcat 11.0.15** — servlet container for running the WAR  
- **MySQL 8.4.8-0ubuntu0.25.10.1 (Ubuntu)** — relational database backend
- **Ubuntu 25.10 (Questing)**

## __Install Guides for Dependencies__  

### Installing Apache Tomcat on Ubuntu:
📦 Apache Tomcat 11.0.5 Installation Guide (Ubuntu)

This guide walks through installing **Apache Tomcat 11.0.5** on Ubuntu. For the purpose of version 0.1.0 of this build Tomcat exist in a development directory. More robust build instructions will be added in a future update.

---

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

Verify:

```bash
java -version
```

---

**3. Create a Dedicated Tomcat User**

This user will run the Tomcat service.

```bash
sudo useradd -m -U -d /opt/tomcat -s /bin/false tomcat
```

---

**4. Download Apache Tomcat 11.0.5**

Navigate to `/tmp` and download the tarball:

```bash
cd /tmp
wget https://dlcdn.apache.org/tomcat/tomcat-11/v11.0.5/bin/apache-tomcat-11.0.5.tar.gz
```

---

### Making an environment directory and running from environment 

The below test should be only used when running a test build of the comic book database. 

I utilize the a dev folder in my home folder for all test environments. I reccomend using what best fits your needs.

**Copying and unzipping the files**    
```bash
mkdir ~/dev/Environment
cp /tmp/apache-tomcat-11.0.5.tar.gz ~/dev/Environment/
tar -xvf apache-tomcat-11.0.5.tar.gz
```




