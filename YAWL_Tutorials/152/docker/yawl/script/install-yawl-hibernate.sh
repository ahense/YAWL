 #!/bin/bash
 
 if [ -z "$DB_URL" ]
then
    echo "ERROR: No DB_URL as environment set!"
    exit 1
fi

echo "Using $DB_URL as JDBC_URL!"
echo "Using $DB_USERNAME as JDBC_USERNAME!"
echo "Using ***** as JDBC_PASSWORD!"


HIBERNATE_FILE="/usr/local/tomcat/webapps/resourceService/WEB-INF/classes/hibernate.properties"
 
sed -i "/hibernate.connection.url\s/ s|\s.*| $DB_URL|" $HIBERNATE_FILE
sed -i "/hibernate.connection.username\s/ s|\s.*| $DB_USERNAME|" $HIBERNATE_FILE
sed -i "/hibernate.connection.password\s/ s|\s.*| $DB_PASSWORD|" $HIBERNATE_FILE

HIBERNATE_FILE="/usr/local/tomcat/webapps/yawl/WEB-INF/classes/hibernate.properties"
 
sed -i "/hibernate.connection.url\s/ s|\s.*| $DB_URL|" $HIBERNATE_FILE
sed -i "/hibernate.connection.username\s/ s|\s.*| $DB_USERNAME|" $HIBERNATE_FILE
sed -i "/hibernate.connection.password\s/ s|\s.*| $DB_PASSWORD|" $HIBERNATE_FILE

HIBERNATE_FILE="/usr/local/tomcat/webapps/workletService/WEB-INF/classes/hibernate.properties"
 
sed -i "/hibernate.connection.url\s/ s|\s.*| $DB_URL|" $HIBERNATE_FILE
sed -i "/hibernate.connection.username\s/ s|\s.*| $DB_USERNAME|" $HIBERNATE_FILE
sed -i "/hibernate.connection.password\s/ s|\s.*| $DB_PASSWORD|" $HIBERNATE_FILE
