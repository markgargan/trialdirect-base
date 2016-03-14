Trial Direct
------------
Download and install Docker
      https://docs.docker.com/engine/installation/
Download and extract Tomcat 
      http://mirrors.whoishostingthis.com/apache/tomcat/tomcat-7/v7.0.68/bin/apache-tomcat-7.0.68.zip
Install Openshift CLI as described in link above. 
      http://openshift.github.io/documentation/oo_cartridge_guide.html#tomcat
git clone the repo at
       Git URL:    ssh://56ddf73b7628e1e18a0001cd@trialdirect-tekenable.rhcloud.com/~/git/trialdirect.git/
open shell and 'cd' to the root of the git clone
       mvn clean install to build the app

Import the project into Intellij 15 by File -> New -> Project from existing sources...
       Select the pom.xml file at the root of the checked out repo.
Run -> Edit Configurations...
Hit the '+' symbol and add an instance of Tomcat Server

Server Tab:
      Application Server -> location of the Tomcat extract.
      Open browser 
           Uncheck 'After Launch'
           http://localhost:8080 
           HTTP port: 8080
           JMX  port: 1099

Deployment Tab:
      Deploy at server startup
      Hit the '+' symbol and select the trialdirect war exploded 
      (Important to pick exploded as it allows Intellij to update changes to classes and resources on the fly)
           
           
MySQL (Docker) installation
----------------------------
Open a shell
      'cd' to the src/main/docker/mysql directory under the codebase
      docker build -t mysql .
      This will install an docker instance of mysql on your machine

      N.B. it will prompt your for a Root password, Enter: tr14ld1r3ct
      
      
Open Kitematic that should install along with Docker. ( Start menu )
      Select mysql...
            In the IP & PORTS section that appears on the right take a note of the 
            DOCKER PORT and the ACCESS URL.
            The Access URL is the IP and Port of the location of the MYSQL db from the perspective of the HOST machine.
            e.g. the 3306/tcp   -   192.168.99.100:32774
            This implies that MySQL is running inside the Docker Container on port 3306 and the transport protocol is tcp
            From the perspective of the host machine i.e. your windows/Mac you can specify the host:port as 192.168.99.100:32774 
            in SQL Developer or Intellij's Database view when creating a new connection and it will connect.
            
In Intellij
      View -> Tool Windows -> Database
      Hit the '+' to create a new connection and specify the host:port username(root) & password (tr14ld1r3ct)
      Note there is no database yet. 
      Once the connection is live open the sql editor and create the trialdirect database
      
      In the SQL Editor:-
          CREATE DATABASE TRIALDIRECT; -- Note the uppercase.
      
Open the context.xml file under the Tomcat installation
      Copy the following xml into the <Context> element,
          substituting in the connection values as per your ACCESS_URL up above
      
      <Resource name="jdbc/MySQLDS"
        url="jdbc:mysql://192.168.99.100:32774/TRIALDIRECT"
        driverClassName="com.mysql.jdbc.Driver"
        username="root"
        password="tr14ld1r3ct"
        auth="Container"
        type="javax.sql.DataSource"
        maxActive="20"
        maxIdle="5"
        maxWait="10000"
        />
  
Click the 'DEBUG' button next to the new Run Configuration to start the Tomcat container. 
This will kick off the Tomcat Container which will configure the MySQL datasource as per the <Resource> in the context.xml
