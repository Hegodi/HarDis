#!/bin/bash
cd class
jar cfm Bolas.jar ../src/manifest.txt *.class 
mv Bolas.jar ../
cd ..
