#!/bin/bash
cd class
jar cfm Hardis.jar ../src/manifest.txt *.class 
mv Hardis.jar ../
cd ..
chmod a+x Hardis.jar
