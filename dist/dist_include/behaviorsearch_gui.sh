#!/bin/bash

WD="`pwd`"  #store the current working directory, where this script was called from.

#BehaviorSearch needs to start in the NetLogo base folder, 
# and "behaviorsearch" must be installed in a subfolder of the NetLogo folder.
BSEARCH_DIR="`dirname "$0"`" # the copious quoting is for handling paths with spaces
cd "$BSEARCH_DIR"
BSEARCH_DIR="`pwd`"  #in case it's a relative path, like "."
cd ..

# If you want to use a different version of java, or if "java" is not in your PATH, 
# you can run this script with the BSEARCH_JAVA environment variable pointing 
# to the java executable you want to use. e.g. "/opt/java6/bin/java"
if [ -z "$BSEARCH_JAVA" ]; then 
    BSEARCH_JAVA="java"
fi

JAVA_VERSION=`"$BSEARCH_JAVA" -version 2>&1 | head -n1 | awk '{ print substr($3,2,3) }' | sed -e 's;\.;0;g'`
if [ $JAVA_VERSION -lt 106 ]; then  #106 = java 1.6
	echo "Sorry, this program requires Java Runtime Environment 1.6 or higher"
	exit 1
fi

# If you have enough RAM, up the '1536m' below to '2048m' or more.
# Or you can set the BSEARCH_MAXMEM environment variable when running the script
# More RAM is especially helpful for multiple threads/parallel execution.
if [ -z "$BSEARCH_MAXMEM" ]; then
    BSEARCH_MAXMEM=1536m
fi

"$BSEARCH_JAVA" -Dbsearch.startupfolder="$WD" -Dbsearch.appfolder="$BSEARCH_DIR" -server -Xms256m "-Xmx$BSEARCH_MAXMEM" -jar "$BSEARCH_DIR/behaviorsearch_gui.jar" "$@"

