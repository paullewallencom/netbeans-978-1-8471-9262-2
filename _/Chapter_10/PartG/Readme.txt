PartG
-----
This part shows you how you can process your data stream to do some intelligent 
pre-processing before performing any meaningful action on the data.
In the previous parts, you used a File BC to create an XML file that contained
the itinerary data.
This itinerary data can be converted into a stream and can be passed through
Intelligent Event Processors to project or filter the stream or to restrict
the itinerary count. This part contains one intelligent event processor that
does the following:

a. Processes all itinerary obtained from the stream for the last 3 seconds.
b. Use stream filter to remove itinerary preferences data to make the 
itinerary data set smaller.
c. Contiguous ordering of itinerary data.
d. Stores the filtered itinerary record in database.


NetBeans Projects
-----------------
1. ItineraryIEP - Intelligent Event Processor NetBeans Project.

2. AirAlliance_SA - This project is the Service Assembly that you will
deploy to the Glassfish application server. This SA already has
ItineraryIEP as a JBI module.

IMPORTANT
---------
After opening AirAlliance_SA project in NetBeans, right click on the
project and select 'Resolve Reference Problems' Click Ressolve and select
ItineraryIEP NetBeans project from your file system.

In NetBeans drill down to Services > Servers > Glassfish V[x] > JBI
>Service Engines.
Right click on iep-se and click Start.


How to test
-----------
1. Open TestItineraryIEP Project and run the project.
2. Go to the CurrentItinerary table and check teh records.

For more information, refer the book.