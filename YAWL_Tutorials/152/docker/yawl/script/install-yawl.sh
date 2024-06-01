#!/bin/bash
set -e

YAWL_VERSION=$1

if [ -z "$YAWL_VERSION" ]
then
    YAWL_GITHUB_API=`wget -O - https://api.github.com/repos/yawlfoundation/yawl/releases/latest`
else
    YAWL_GITHUB_API=`wget -O - https://api.github.com/repos/yawlfoundation/yawl/releases/tags/$YAWL_VERSION`
fi

TOMCAT_HOME=/usr/local/tomcat
TOMCAT_PROPS_FILE="$TOMCAT_HOME/conf/catalina.properties"


# Download YAWL
YAWL_VERSION=`echo $YAWL_GITHUB_API | jq -r '.tag_name'`

YAWL_DOWNLOAD_URL=`echo $YAWL_GITHUB_API | jq -r '.assets[].browser_download_url' | grep CoreServices`
YAWL_LIB_DOWNLOAD_URL=`echo $YAWL_GITHUB_API | jq -r '.assets[].browser_download_url' | grep yawl-lib`

YAWL_INSTALL_FILE="/root/yawl-CoreServices.zip"
YAWL_LIB_PATH="$TOMCAT_HOME/yawllib/yawl-lib.jar"

mkdir $TOMCAT_HOME/yawllib
mkdir $TOMCAT_HOME/codeletlib
mkdir $TOMCAT_HOME/codelets
mkdir $TOMCAT_HOME/yawlplugins

wget -O $YAWL_INSTALL_FILE $YAWL_DOWNLOAD_URL
wget -O $YAWL_LIB_PATH $YAWL_LIB_DOWNLOAD_URL


# Unzip YAWL services
cd /root
unzip $YAWL_INSTALL_FILE
unzip yawl.war -d /usr/local/tomcat/webapps/yawl
unzip yawlWSInvoker.war -d /usr/local/tomcat/webapps/yawlWSInvoker
unzip workletService.war -d /usr/local/tomcat/webapps/workletService
unzip resourceService.war -d /usr/local/tomcat/webapps/resourceService


# Cleanup
rm $YAWL_INSTALL_FILE
rm yawl.war
rm yawlWSInvoker.war
rm workletService.war
rm resourceService.war


CODELET_LIB="\${catalina.base}/yawllib,\${catalina.base}/yawllib/*.jar,\${catalina.home}/yawllib,\${catalina.home}/yawllib/*.jar,\${catalina.base}/codeletlib,\${catalina.base}/codeletlib/*.jar,\${catalina.home}/codeletlib,\${catalina.home}/codeletlib/*.jar"

sed -i "s#^shared\.loader=.*#&${CODELET_LIB}#" ${TOMCAT_PROPS_FILE}


WEB_XML="/usr/local/tomcat/webapps/resourceService/WEB-INF/web.xml"
xmlstarlet edit --inplace --update "/web-app/context-param/param-name[text() = 'ExternalPluginsPath']/../param-value" --value "/usr/local/tomcat/codelets;/usr/local/tomcat/yawlplugins" $WEB_XML

