#!/bin/bash
#============ get the file name ===========
Folder_A="/Users/Shared/maven-lib/"
for file_a in ${Folder_A}/*; 
do
    temp_file=`basename $file_a`
    temp_file1=`basename $file_a .jar`
    mvn install:install-file -Dfile=/Users/Shared/maven-lib/$temp_file -DgroupId=com.github.xiaoymin -DartifactId=$temp_file1 -Dversion=2.0.9 -Dpackaging=jar
done    