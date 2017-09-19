# Requirements
##Blinker Android Candidate Interview Project 
1.    Using the JSON file (vehicles.json) included in the project, create a scrolling List that shows each vehicle's `year`, `make`, and `model` (1999 Honda Accord) in each item, along with its mileage.
* The year, make, and model should go in the upper left corner of the cell with 16dp margins on the left and the top.
* The vehicle's mileage should go in the bottom right corner of the cell with 16dp margins on the right and the bottom.
* The vehicles.json file has many duplicate vehicles for the sake of getting a large, populated list. Don't worry about filtering out duplicates.
2. Include a search field at the top of the screen to filter vehicles in the list.
* Should be able to search any combination of year, make, or model. e.g. "1999", "honda accord", "1999 accord", "honda 1999", etc.
3. When an item is tapped, load the image from the URL (`image_url`) included in the JSON file and display it on a new screen with a 4:3 aspect ratio
5. Write all code in Java or Kotlin
6. If you get stuck anywhere along the way, feel free to ask for help.
7. You may use whatever resources you'd like to complete the project including 3rd party libraries, search Stack Overflow, ask for help, whatever. The only rule is that whatever implementation you come up with must be your own.

##Bonus (Not required and will not be counted against you should you choose not to complete this)
1. Split the list into different sections with headers for each year.
Example would be a list header item "2016" followed by all vehicles with the year "2016" and then the header "2015" followed by vehicles for that year.

# About
Application is built around the Green Robot EventBus, all events are handled on background or async threads unless absolutely required by the OS.
 
## Querying
Querying is done through reflection, this is to help facilitate adding new fields to query against in the future.  
For example, if new requirements come in that say the user should be able to query the type of vehicle (SUV, sedan, motorcycle, etc.) you simply need to add the new field name to the `rocks.teagantotally.blinkercarbrowser.datastore.VehicleQuery` class - make sure this field matches the `rocks.teagantotally.blinkercarbrowser.datastore.models.Vehicle` field.

## Services
Services are started when the application is attached to the base context. Services should fire a `ServiceEvent` for start and stop status. This way the application can wait until the services needed are running before trying to perform logic.

## Routing
This application has a single screen, but the router is added for future improvements. The router uses URIs mapped to an activity and a fragment - this allows for a LoggedInActivity (which can listen for UserLoggedOutEvent or similar) and a LoggedOutActivity for example.

## UI
The entire application uses data binding, no exceptions. There is a BindableBottomSheetFragment and a BindableDialogFragment which are instantiated through the use of their appropriate events. Data Binding removes the need for view controllers and facilitates MVVMP architecture.

The recycler view uses a home-built data bound adapter. An improvement I would like to make to this is instead of binding the items through the binding adapters, I'd like to bind the adapter itself - this should allow for using android's default item animation, which currently it does not.

## ViewModels
Since the `ViewScope` is a custom scope, we must handle the lifecycle. The BaseVM class resets the component lifecycle whenever a new view model is built - this prevents Dagger from injecting view models as singletons.

If you notice any sort of screen flicker, you can utilize the `setShouldNotify` method on the `BaseVM` to toggle property change callbacks - set it to `false` to populate your view model and set to `true` when done, and the screen will only update once.

## Data
Data stores are provided through the dependency injection framework (Dagger 2), allowing for ease of interchangeability - currently data comes from a JSON file, but if an HTTP data store were available, simply create a VehicleHttpDataStore that implements VehicleDataStore and inject it in the DataStoreModule.

## Notes
The data provided in `vehicles.json` do not include indices, so the `Vehicle` model creates an artificial index for each model. Since I implemented a `RetrieveVehicleResultEvent` which allows for a sort of **Live Data** feature, allowing any view model that is still registered on the event bus to listen for updated models of itself from the data store.
