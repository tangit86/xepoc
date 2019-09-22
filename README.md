# Info

### How to execute

* Build using **Maven**

` mvn clean package`

* Run by opening a terminal in the `/target` directory and type:

`java -jar demo-0.0.1-SNAPSHOT.jar`

### How to manually test

#####Search functions
* Create Search
 
 [GET] http://localhost:8080/create?term=`term`
 
For `term` use words or letters from this hardcoded list: `"BONG", "FOO", "ASDA", "ASDA-FOO", "BEE"`
 
* Fetch Results Page
 
 [GET] http://localhost:8080/fetch?page=1&pageSize=20

* View an Ad
 
 [GET] http://localhost:8080/view?adId=`adId` 
 
 This fetches the contents of an Ad to view. By default this view is recorded as "DIRECT" type of view.
 If you want to be recorded as "CLICKED" in the statistics, you need to add an extra Header to the request, like:
 
 `viewmode:1`
 
 #####Helpers
 
 * View current Statistics
 
 [GET] http://localhost:8080/helper/stats
 
 * View all the Ads returned by the Search
  
  [GET] http://localhost:8080/helper/data
 
-
You can  also use [**Postman**](https://www.getpostman.com/downloads/)  and import the collection found in `xe-poc.postman_collection.json` file.
 

# Service Summary

The service exposes three endpoints performing the requested functionality. These endpoints are found in the `SearchController` class.
Each endpoint performs two underlying actions synchronously:
- The creation/fetching of data , using `SearchService` class
- The storing of an event that includes useful information for the production of Statistics related to the executed action, using `StatGeneratorService` class

In a real life scenario, these events would be handled by an event store type of infrastructure/framework, to ensure consistency and availability.
For now, a queue has been used and the `StatGeneratorService` checks for unprocessed events every 1000 milliseconds.

According of the type and the payload of the event, various metrics are updated. 
The metrics are stored in an in-memory map, for each ad an `AdStatistic` object, holding the requested statistical information.

The `HelperController` exposes some auxiliary endpoints, to check the contents of the in-memory data structures. 
 
# Tools Used

- SpringBoot framework (Web support)
- Kotlin



