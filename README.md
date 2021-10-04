# Seed_Parser
Java application with GUI to get the eToken Pass activation file from the database by the list of numbers

To run the application, JVM must be installed on the PC.

It is necessary to indicate the name of the file containing the list of searched numbers and 
select the types of files that you want to receive. 
.xml for SAM and .dat for SAS or both.

The application requires strict adherence to the folder structure. 
In the shared folder where the SidParser file is located, there should be folders:
/
/data
/in
/out

Data folder must contain subfolders that contain the files alpineXml.xml, importAlpine.dat
These files are provided by the vendor and contain information about keys from the entire batch.
In folder must contain a txt file with a list of all required key numbers.

To start the application, you can use the start.bat file. This replaces the CMD startup procedure.

